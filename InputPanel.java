import javax.swing.*;
import java.awt.*;

class InputPanel {
    JButton button;
    JTextField textField;
    Boolean showSpin;
    Boolean showGuess;

    public JPanel init(JButton inputButton, JTextField inputTextField, String message, Boolean messageRed,
            JButton spinButton, Boolean inputShowSpin, Boolean inputShowGuess, JButton restartButton,
            Boolean showRestart) {
        button = inputButton;
        textField = inputTextField;
        showSpin = inputShowSpin;
        showGuess = inputShowGuess;

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(750, 125));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(2, 1)); // Two Rows

        panel.add(renderTopPanel(message, messageRed, spinButton, restartButton, showRestart), BorderLayout.SOUTH);
        panel.add(renderBottomPanel(), BorderLayout.SOUTH);

        return panel;
    }

    public JButton renderSpinButton() {
        JButton spinButton = new JButton("Spin!");
        spinButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        return spinButton;
    }

    public JButton renderButton() {
        JButton button = new JButton("Guess!");
        return button;
    }

    public JButton renderRestartButton() {
        JButton button = new JButton("Restart");
        return button;
    }

    public JTextField renderTextField(String guessText) {
        JTextField textField = new JTextField(10);
        textField.setText(guessText);
        return textField;
    }

    public JPanel renderTopPanel(String gameMessage, Boolean messageRed, JButton spinButton, JButton restartButton,
            Boolean showRestart) {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(gameMessage);
        label.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        label.setForeground(messageRed ? Color.RED : Color.GREEN);

        spinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (showRestart) {
            topPanel.add(restartButton);
        }

        if (showSpin)
            topPanel.add(spinButton);
        topPanel.add(label);

        return topPanel;
    }

    public JPanel renderBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Guess a Single Character: ");

        bottomPanel.add(label);
        bottomPanel.add(textField);

        if (showGuess)
            bottomPanel.add(button);

        return bottomPanel;
    }
}