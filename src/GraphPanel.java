import javax.swing.*; //used for GUI components like JPanel
import java.awt.*; //used for drawing colors,fonts
import java.util.Map; //store skill data with values(java-45,sql-36)

public class GraphPanel extends JPanel {

    private Map<String, Integer> data;  //stores skill names and values

    public GraphPanel(Map<String, Integer> data) {
        this.data = data;
        setBorder(null);
        setBackground(new Color(255,239,180));
    }
    public void setData(Map<String, Integer> data) {
        this.data = data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.size() == 0) return;

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Matching Skill Distribution", 60, 20);

        int total = 0;
        for (int value : data.values()) {
            total += value;
        }

        int startAngle = 0;

        int x = 60;
        int y = 60;
        int width = 220;
        int height = 220;

        // Colors for slices
        Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA};

        int i = 0;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {

            int value = entry.getValue();
            int angle = (int) Math.round((value * 360.0) / total); //converting % into angle

            g.setColor(colors[i % colors.length]); //for each slice different colors
            g.fillArc(x, y, width, height, startAngle, angle); //to draw the each slice of the chart

            startAngle += angle;
            i++;
        }

        // Legend (labels)
        int legendY = 340;
        i = 0;

        for (Map.Entry<String, Integer> entry : data.entrySet()) { //to display the legend labels for each skill
            g.setColor(colors[i % colors.length]);
            g.fillRect(50, legendY, 15, 15); //draw filled solid box

            g.setColor(Color.BLACK);
            g.drawString(entry.getKey() + " - " + entry.getValue() + "%", 70, legendY + 12);

            legendY += 25;
            i++;
        }
    }
}