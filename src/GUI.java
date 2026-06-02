import javax.swing.*; //used for GUI components like JPanel
import java.awt.*; //used for drawing colors,fonts
import java.io.*;
import java.util.ArrayList; //to store dynamic data skills
import java.util.List;  //provides flexible way to store collections of data
import java.util.Set;
import java.util.HashSet;

public class GUI {
    static JFrame graphFrame;
    public static void main(String[] args) throws Exception {

        JFrame f = new JFrame("Job Recommender System");
        f.setSize(650, 550);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        f.setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel top = new JPanel(new BorderLayout());
        top.setLayout(new GridLayout(3, 1, 10, 10));
        top.setBackground(new Color(255, 200, 150 ));

        JTextField skillsField = new JTextField();
        String[] jobNames = loadJobsFromDataset();
        String[] jobOptions = new String[jobNames.length + 1];
        jobOptions[0] = "Select Job Role";

        for (int i = 0; i < jobNames.length; i++) {
            jobOptions[i + 1] = jobNames[i];
        }
        JComboBox<String> goalDropdown = new JComboBox<>(jobOptions);
        goalDropdown.setPreferredSize(new Dimension(200, 30));
        JButton btn = new JButton("Get Recommendation");

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // ===== ROW 1 =====
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row1.setBackground(new Color(240,240,240 ));

        JLabel skillsLabel = new JLabel("  Enter your skills:");
        skillsLabel.setFont(labelFont);

        skillsField.setFont(fieldFont);
        skillsField.setPreferredSize(new Dimension(300, 30));

        row1.add(skillsLabel);
        row1.add(skillsField);

        // ===== ROW 2 =====
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row2.setBackground(new Color(240,240,240 ));

        JLabel goalLabel = new JLabel("  Target job (optional):");
        goalLabel.setFont(labelFont);

        goalDropdown.setFont(fieldFont);
        goalDropdown.setPreferredSize(new Dimension(300, 30));

        row2.add(goalLabel);
        row2.add(goalDropdown);

        // ===== ROW 3 =====
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row3.setBackground(new Color(240,240,240   ));

        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(220, 40));
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        row3.add(btn);

        top.add(row1);
        top.add(row2);
        top.add(row3);

        // ===== OUTPUT AREA =====
        JTextArea output = new JTextArea();
        GraphPanel graphPanel=new GraphPanel(null);
        AccuracyPanel accPanel=new AccuracyPanel();
        accPanel.setBackground(new Color(255,239,180));
        output.setFont(new Font("Consolas", Font.BOLD, 18   ));
        output.setEditable(false);
        output.setBackground(new Color(255,228,225));
        output.setMargin(new Insets(10, 10, 10, 10));

        f.add(top, BorderLayout.NORTH);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(700); // left big, right small
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255,239,180));

        JLabel accLabel = new JLabel("Accuracy Comparison");
        accLabel.setFont(new Font("Arial", Font.BOLD, 18));

        bottomPanel.add(accLabel);

// LEFT SIDE → text
        JScrollPane textScroll = new JScrollPane(output);
        textScroll.setBorder(BorderFactory.createEmptyBorder(0,20,0,10));
        JPanel leftPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        leftPanel.add(textScroll);
        leftPanel.add(graphPanel);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(accPanel);
// add to frame
        f.add(splitPane, BorderLayout.CENTER);

        Color bg = new Color(255,239,180);
        top.setBackground(bg);
        output.setBackground(bg);

        f.getContentPane().setBackground(Color.yellow);

        // ===== BUTTON ACTION =====
        btn.addActionListener(e -> {
            try {

                String resume = skillsField.getText().toLowerCase();
                String goal = ((String)goalDropdown.getSelectedItem()).trim().toLowerCase();
                if (goal.equals("select job role")) {
                    goal = "";
                }
                BufferedReader br = new BufferedReader(new FileReader("dataset.csv"));
                br.readLine();

                List<Job> jobs = new ArrayList<>();
                String line;

                while ((line = br.readLine()) != null) {
                    String[] p = line.split(",");
                    Job job = new Job(p[0]);

                    for (int i = 1; i < p.length; i += 2) {
                        job.addSkill(p[i], Integer.parseInt(p[i + 1]));
                    }

                    jobs.add(job);
                }
                br.close();

                StringBuilder text = new StringBuilder();
                java.util.Map<String, Integer>graphData=new java.util.HashMap<>();
                List<String> results = new ArrayList<>();

                double best = 0;
                String bestJob = "";

                for (Job job : jobs) {
                    var r = RecommenderEngine.analyze(resume, job);

                    results.add(job.getTitle() + " - " + r.matchScore);

                    if (r.matchScore > best) {
                        best = r.matchScore;
                        bestJob = job.getTitle();
                    }
                }

                results.sort((a, b) ->
                        Double.compare(
                                Double.parseDouble(b.split(" - ")[1]),
                                Double.parseDouble(a.split(" - ")[1])
                        )
                );

                if (goal.isEmpty()) {
                    text.append("Based on your skills:\n");
                    text.append("Recommended Job: ").append(bestJob)
                            .append("\nMatch Score: ")
                            .append(String.format("%.2f", best)).append("%\n\n");
                    text.append("Top 3 Jobs:\n");
                    for (int i = 0; i < 3 && i < results.size(); i++) {
                        String[] p = results.get(i).split(" - ");
                        text.append((i + 1)).append(". ")
                                .append(p[0]).append(" - ")
                                .append(String.format("%.2f", Double.parseDouble(p[1])))
                                .append("%\n");
                    }
                    accPanel.setAccuracy(best);
                    accPanel.setPaperAccuracy(85);
                    Job bestTarget = null;

                    for (Job j : jobs) {
                        if (j.getTitle().equalsIgnoreCase(bestJob)) {
                            bestTarget = j;
                            break;
                        }
                    }

                    if (bestTarget != null) {
                        var r = RecommenderEngine.analyze(resume, bestTarget);

                        int total = 0;
                        for (String skill : r.matchedSkills) {
                            total += bestTarget.getWeightForSkill(skill);
                        }

                        for (String skill : r.matchedSkills) {
                            int weight = bestTarget.getWeightForSkill(skill);
                            int percent = (int)((weight * 100.0) / total);
                            graphData.put(skill, percent);
                        }
                    }
                } else {

                    Job target = null;

                    for (Job j : jobs) {
                        if (j.getTitle().toLowerCase().contains(goal)) {
                            target = j;
                            break;
                        }
                    }
                    if (target == null) {
                        text.append("Job not found in dataset!");
                    } else {

                        var r = RecommenderEngine.analyze(resume, target);
                        accPanel.setAccuracy(r.matchScore);
                        accPanel.setPaperAccuracy(85); // important!
                        int total = 0;

                        for (String skill : r.matchedSkills) {
                            total += target.getWeightForSkill(skill);
                        }

                        for (String skill : r.matchedSkills) {
                            int weight = target.getWeightForSkill(skill);
                            int percent = (int)((weight * 100.0) / total);
                            graphData.put(skill, percent);
                        }

                        text.append("Goal-based Analysis:\n");
                        text.append("Target Job: ").append(target.getTitle())
                                .append("\nMatch Score: ")
                                .append(String.format("%.2f", r.matchScore)).append("%\n\n");

                        text.append("Missing Skills:\n");
                        for (String s : r.missingSkills) {
                            text.append("- ").append(s).append("\n");
                        }
                        text.append("\nTop Matching Jobs For You:\n");

                        for (int i = 0; i < 3 && i < results.size(); i++) {
                            String[] p = results.get(i).split(" - ");
                            text.append((i + 1)).append(". ")
                                    .append(p[0]).append(" - ")
                                    .append(String.format("%.2f", Double.parseDouble(p[1])))
                                    .append("%\n");
                        }
                    }
                }

                output.setText(text.toString());
                graphPanel.setData(graphData);
                graphPanel.repaint();
                accPanel.setAccuracy(best);

            } catch (Exception ex) {
                ex.printStackTrace();
                output.setText("Error occurred!");
            }
        });

        f.setVisible(true);
    }
    public static String[] loadJobsFromDataset() {
        Set<String> jobSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader("dataset.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    jobSet.add(parts[0].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobSet.toArray(new String[0]);
    }

}
