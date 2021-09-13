import javax.swing.*;
import java.awt.*;

public class GamePlayers {
    Player[] players;
    int currentPlayer;

    public JPanel init(Player[] players, int currentPlayer, int tryCount) {
        this.players = players;
        this.currentPlayer = currentPlayer;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(200, 600));
        panel.add(renderTryCount(tryCount));

        for (int i = 0; i < players.length; i++)
            panel.add(renderPlayer(i));

        return panel;
    }

    public JLabel renderTryCount(int errorsRemaining) {
        JLabel label = new JLabel("Errors Remaining: " + errorsRemaining);
        Font bigFont = label.getFont().deriveFont(Font.BOLD, 17.5f);
        label.setFont(bigFont);
        label.setForeground(Color.RED);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    public JLabel renderPlayer(int playerIdx) {
        Player player = players[playerIdx];
        int playerId = player.playerId;
        int playerPoints = player.points;

        JLabel label = new JLabel("Player " + playerId + ": " + playerPoints + " points");
        Font bigFont = label.getFont().deriveFont(Font.BOLD, 17.5f);
        label.setFont(bigFont);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.setForeground(playerIdx == currentPlayer ? Color.GREEN : Color.GRAY);

        return label;
    }
}
