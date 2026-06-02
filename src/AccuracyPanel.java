import javax.swing.*; //used for GUI components like JPanel
import java.awt.*; //used for drawing colors,fonts

public class AccuracyPanel extends JPanel {

    private double yourAccuracy = -1; //-1 bec indicates accuracy is not yet calculated
    private double paperAccuracy = -1; // fixed paper value (you can change)

    public void setAccuracy(double yourAcc) {
        this.yourAccuracy = yourAcc;
        repaint(); //redraw panel
    }
    public void setPaperAccuracy(double paperAcc) {
        this.paperAccuracy = paperAcc;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Accuracy Comparison", 120, 20);
        if(yourAccuracy<0||paperAccuracy<0) {
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Click 'Get Recommendation'", 120, 200);
            return;
        }

        int baseY = 400;

        // YOUR MODEL BAR (Blue)
        int yourHeight = (int) (yourAccuracy * 3);
        g.setColor(Color.magenta);
        g.fillRect(140, baseY - yourHeight, 50, yourHeight);
        g.drawString((int)yourAccuracy+"%",145,baseY-yourHeight-10);
        g.setColor(Color.BLACK);

        // PAPER MODEL BAR (Red)
        int paperHeight = (int) (paperAccuracy * 3);
        g.setColor(new Color(0,150,136));
        g.fillRect(260, baseY - paperHeight, 50, paperHeight);
        g.drawString((int)paperAccuracy+"%",265,baseY-paperHeight-10);
        g.setColor(Color.BLACK);
        int textY = baseY + 60;

// Pink box (Your Model)
        g.setColor(Color.magenta);
        g.fillRect(140, textY - 12, 15, 15);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("Weighted Skill Match - " + (int)yourAccuracy + "%", 160, textY);

// Blue box (Paper Model)
        g.setColor(new Color(0, 150, 136));
        g.fillRect(140, textY + 25 - 12, 15, 15);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("Paper Model (Content-Based) - " + (int)paperAccuracy + "%", 160, textY + 25);


    }
}