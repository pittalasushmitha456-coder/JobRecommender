import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // Load dataset
        List<Job> jobs = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("dataset.csv"));
        String line;
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");

            String title = parts[0];
            Job job = new Job(title);

            for (int i = 1; i < parts.length; i += 2) {
                String skill = parts[i];
                int weight = Integer.parseInt(parts[i + 1]);
                job.addSkill(skill, weight);
            }

            jobs.add(job);
        }
        br.close();

        // User input
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your resume text:");
        String resume = sc.nextLine();

        double bestScore = 0;
        String bestJob = "";

        // Store all job results
        List<String> jobResults = new ArrayList<>();

        // Compare with all jobs
        for (Job job : jobs) {

            RecommenderEngine.Result result =
                    RecommenderEngine.analyze(resume, job);

            if (result.matchScore > 0) {

                System.out.println("\nChecking for: " + job.getTitle());
                System.out.printf("Match Score: %.2f%%\n", result.matchScore);
                System.out.println("Matched Skills: " + result.matchedSkills);

                // Save result for TOP 3
                jobResults.add(job.getTitle() + " - " + String.format("%.2f",result.matchScore));

                // Find best job
                if (result.matchScore > bestScore) {
                    bestScore = result.matchScore;
                    bestJob = job.getTitle();
                }
            }
        }

        // Sort jobs by score (descending)
        Collections.sort(jobResults, (a, b) -> {
            double scoreA = Double.parseDouble(a.split(" - ")[1]);
            double scoreB = Double.parseDouble(b.split(" - ")[1]);
            return Double.compare(scoreB, scoreA);
        });

        // Final best result
        System.out.println("\n===== FINAL RESULT =====");
        System.out.println("BEST JOB FOR YOU: " + bestJob);
        System.out.printf("Best Match Score: %.2f%%\n", bestScore);

        // Top 3 jobs
        System.out.println("\n===== TOP 3 JOBS =====");
        for (int i = 0; i < Math.min(3, jobResults.size()); i++) {
            System.out.println(jobResults.get(i));
        }
    }
}