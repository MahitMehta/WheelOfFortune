import java.awt.*;
import javax.swing.*;

public class SettingsPanel {
    String secret;
    int tryCount;
    int playerCount;

    JTextField secretTextField;
    JTextField playerCountTextField;
    JTextField tryCountTextField;

    public SettingsPanel(int playerCount, int tryCount, String secret) {
        this.playerCount = playerCount;
        this.tryCount = tryCount;
        this.secret = secret;
    }

    public JPanel init(JButton settingsButton) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JPanelSettings secretPanel = new JPanelSettings();
        secretPanel.setDefault();
        JLabel secretLabel = new JLabel("What is the Puzzle? ");
        secretTextField = new JTextField();
        secretTextField.setText(this.secret);
        secretTextField.setPreferredSize(new Dimension(125, 25));
        secretPanel.add(secretLabel);
        secretPanel.add(secretTextField);

        JPanelSettings playerCountPanel = new JPanelSettings();
        playerCountPanel.setDefault();
        JLabel playerCountLabel = new JLabel("Number of Players: ");
        playerCountTextField = new JTextField();
        playerCountTextField.setText(Integer.toString(this.playerCount));
        playerCountTextField.setPreferredSize(new Dimension(125, 25));
        playerCountPanel.add(playerCountLabel);
        playerCountPanel.add(playerCountTextField);

        JPanelSettings tryCountPanel = new JPanelSettings();
        tryCountPanel.setDefault();
        JLabel tryCountLabel = new JLabel("Number of Tries: ");
        tryCountTextField = new JTextField();
        tryCountTextField.setPreferredSize(new Dimension(125, 25));
        tryCountTextField.setText(Integer.toString(this.tryCount));
        tryCountPanel.add(tryCountLabel);
        tryCountPanel.add(tryCountTextField);

        panel.add(secretPanel);
        panel.add(playerCountPanel);
        panel.add(tryCountPanel);
        panel.add(settingsButton);
        // panel.setPreferredSize(new Dimension(300, 400));

        return panel;
    }

    public JButton renderSettingsButton() {
        JButton button = new JButton("Start Game!");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}
