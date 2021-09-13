import java.awt.*;
import javax.swing.*;

public class BoardPanel {
    String phrase;
    String rawPhrase;
    String letters;
    ImageIcon arrowIMG;

    public BoardPanel(String inputPhrase, String inputRawPhrase, String inputLetters, ImageIcon inputArrowIMG) {
        phrase = inputPhrase;
        rawPhrase = inputRawPhrase;
        letters = inputLetters;
        arrowIMG = inputArrowIMG;
    }

    public JPanel init() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);

        for (int i = 0; i < phrase.length(); i++) {
            JTextField tfLetter = new JTextField(1);
            String currentLetter = phrase.split("")[i];
            tfLetter.setBackground(Color.WHITE);

            Font bigFont = tfLetter.getFont().deriveFont(Font.PLAIN, 20f);
            tfLetter.setFont(bigFont);
            tfLetter.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

            if (letters.contains(currentLetter)) {
                tfLetter.setText(rawPhrase.split("")[i]);
            }

            if (i != 0 && phrase.split("")[i].equals(" ")) {
                tfLetter.setBackground(Color.WHITE);
                tfLetter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            }

            tfLetter.setEditable(false);
            topPanel.add(tfLetter);
            panel.add(topPanel);

        }

        JLabel arrowIMGLabel = new JLabel(arrowIMG);
        arrowIMGLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bottomPanel.add(arrowIMGLabel);
        panel.add(bottomPanel);

        return panel;
    }
}
