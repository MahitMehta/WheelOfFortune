import javax.swing.*;
import java.awt.*;

public class GamePanel {
    final int[][] pointRanges = { { 0, 46 }, { 46, 89 }, { 89, 132 }, { 132, 178 }, { 178, 225 }, { 225, 269 },
            { 269, 316 }, { 316, 360 } };
    final int[] pointAmount = { 500, 900, 200, 100, 750, 300, 1000, 100 };
    double rotateAmount;
    int pointIncrement = 0;
    int currentPlayer;

    public GamePanel(double wheelRotateAmount) {
        rotateAmount = wheelRotateAmount;
    }

    public int getPointIncrement() {
        int fitIndex = -1;
        // System.out.println(rotateAmount);

        for (int i = 0; i < pointRanges.length; i++) {
            if (rotateAmount >= pointRanges[i][0] && rotateAmount <= pointRanges[i][1]) {
                fitIndex = i;
            }
        }
        return fitIndex < 0 ? -1 : pointAmount[fitIndex];
    }

    public JPanel init(int inputPointIncrement, int inputCurrentPlayer) {
        pointIncrement = inputPointIncrement;
        currentPlayer = inputCurrentPlayer;

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(renderPointIncrement());
        panel.add(renderPlayer());
        panel.setPreferredSize(new Dimension(200, 600));

        return panel;
    }

    public JLabel renderPointIncrement() {
        JLabel label = new JLabel("Increment: " + (pointIncrement < 0 ? "TBD" : pointIncrement));
        Font bigFont = label.getFont().deriveFont(Font.BOLD, 17.5f);
        label.setFont(bigFont);
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.setForeground(Color.GREEN);
        return label;
    }

    public JLabel renderPlayer() {
        JLabel label = new JLabel("Player: " + (currentPlayer + 1));
        Font bigFont = label.getFont().deriveFont(Font.BOLD, 17.5f);
        label.setFont(bigFont);
        label.setAlignmentX(Component.RIGHT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.setForeground(Color.GREEN);
        return label;
    }
}
