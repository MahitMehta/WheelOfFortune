import javax.swing.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.util.concurrent.*;

public class GameEngine {
    JFrame frame;
    String secret;
    String secretLower;
    JTextField textField;
    String letters = "";

    Boolean showSettings = true;
    JButton settingsButton;
    SettingsPanel settingsPanel;
    Boolean showRestartButton = false;
    JButton restartButton;

    String message = "Welcome to Wheel of Fortune!";
    Boolean messageRed = false;
    String guessText = "";

    int playerCount = 1;
    Player[] players;
    int currentPlayer = 0;
    int tryCountInit = 5;
    int tryCount = tryCountInit;

    JButton button;
    JButton spinButton;
    Boolean showGuess = false;
    Boolean showSpin = true;

    WheelPanel wheelPanelInstance;
    ImageIcon wheelResizedImage = resizeImage(new ImageIcon("./wheel.png"), 275, 275);
    ImageIcon arrowResizedImage = resizeImage(new ImageIcon("./arrow.png"), 25, 25);
    double wheelRotateAmount = 0;
    Boolean spin = false;
    int inputPointIncrement;

    public GameEngine(JFrame display, String secretInput) {
        frame = display;
        secret = secretInput;
        secretLower = secret.toLowerCase();
        adjustPlayers(0, true);
    }

    public void adjustPlayers(int pointsEarned, Boolean reset) {
        Player[] playersTemp = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            if (players != null && players.length - 1 >= i) {
                playersTemp[i] = players[i];
            } else {
                playersTemp[i] = new Player(i + 1, 0);
            }
            if (reset) {
                playersTemp[i].points = 0;
            }
        }
        playersTemp[currentPlayer].points += pointsEarned;
        players = playersTemp;
    }

    public ImageIcon resizeImage(ImageIcon imageInput, int w, int h) {
        Image image = imageInput.getImage();
        Image resizedWheel = image.getScaledInstance(w, h, java.awt.Image.SCALE_AREA_AVERAGING);
        return new ImageIcon(resizedWheel);
    }

    public void assemble() {
        frame.getContentPane().removeAll();
        frame.repaint();

        if (showSettings) {
            frame.getContentPane().add(renderSettingsPanel(), BorderLayout.CENTER);
        } else {
            frame.getContentPane().add(renderWheelPanel(), BorderLayout.CENTER);
            frame.getContentPane().add(renderInputPanel(), BorderLayout.SOUTH);
            frame.getContentPane().add(renderBoardPanel(), BorderLayout.NORTH);
            frame.getContentPane().add(renderGameBoardPanel(), BorderLayout.EAST);
            frame.getContentPane().add(renderGamePlayers(), BorderLayout.WEST);
        }

        frame.revalidate();
        frame.repaint();
    }

    public JPanel renderSettingsPanel() {
        settingsPanel = new SettingsPanel(playerCount, tryCount, secret);
        settingsButton = settingsPanel.renderSettingsButton();
        handleSettingsClick();

        JPanel panel = settingsPanel.init(settingsButton);
        return panel;
    }

    public void handleSettingsError() {
        // Display Settings Error Message
    }

    public void handleSettingsClick() {
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int inputPlayerCount = Integer.parseInt(settingsPanel.playerCountTextField.getText());
                    int inputTryCount = Integer.parseInt(settingsPanel.tryCountTextField.getText());
                    String inputSecret = settingsPanel.secretTextField.getText();
                    String inputSecretLower = inputSecret.toLowerCase();

                    if (inputSecretLower.length() == 0) {
                        handleSettingsError();
                    } else {
                        playerCount = inputPlayerCount;
                        tryCountInit = inputTryCount;
                        tryCount = tryCountInit;
                        secret = inputSecret;
                        secretLower = inputSecretLower;

                        showSettings = false;
                        adjustPlayers(0, true);
                        assemble();
                    }
                } catch (Exception err) {
                    handleSettingsError();
                }
            }
        });
    }

    public JPanel renderBoardPanel() {
        BoardPanel boardPanel = new BoardPanel(secretLower, secret, letters, arrowResizedImage);

        JPanel panel = boardPanel.init();
        return panel;
    }

    public JPanel renderWheelPanel() {
        WheelPanel wheelPanel = new WheelPanel(wheelResizedImage, spin, wheelRotateAmount);
        wheelPanelInstance = wheelPanel;
        JPanel panel = wheelPanel.init();
        return panel;
    }

    public JPanel renderGamePlayers() {
        GamePlayers gamePlayers = new GamePlayers();
        JPanel panel = gamePlayers.init(players, currentPlayer, tryCount);

        return panel;
    }

    public JPanel renderGameBoardPanel() {
        // System.out.println(wheelRotateAmount);
        GamePanel gamePanel = new GamePanel(wheelRotateAmount);
        JPanel panel = gamePanel.init(inputPointIncrement, currentPlayer);
        return panel;
    }

    public JPanel renderInputPanel() {
        InputPanel inputPanel = new InputPanel();

        JButton inputButton = inputPanel.renderButton();
        JTextField inputTextField = inputPanel.renderTextField(guessText);
        JButton inputSpinButton = inputPanel.renderSpinButton();
        JButton inputRestartButton = inputPanel.renderRestartButton();

        button = inputButton;
        textField = inputTextField;
        spinButton = inputSpinButton;
        restartButton = inputRestartButton;

        handleClick();
        handleSpin();
        handleRestart();

        JPanel panel = inputPanel.init(button, textField, message, messageRed, inputSpinButton, showSpin, showGuess,
                restartButton, showRestartButton);

        return panel;
    }

    public void handleRestart() {
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSettings = true;
                showRestartButton = false;
                letters = "";
                currentPlayer = 0;
                inputPointIncrement = -1;
                tryCount = tryCountInit;
                showSpin = true;
                message = "Welcome to Wheel of Fortune!";
                // players = new Player[playerCount];
                assemble();
            }
        });
    }

    public Boolean processGuess(String guess) {
        int guessLen = guess.length();
        return guessLen == 1;
    }

    public int validateGuess(String phrase, String guess) {
        int tally = 0;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.split("")[i].equals(guess))
                tally++;
        }
        return tally;
    }

    public void handleSpin() {
        spinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guessText = textField.getText();

                showSpin = false;
                spin = true;
                messageRed = false;
                message = "Hope for Something Good!";
                assemble();

                Runnable stopSpin = new Runnable() {
                    public void run() {
                        wheelRotateAmount = wheelPanelInstance.rotateAmount;
                        GamePanel gamePanel = new GamePanel(wheelRotateAmount);
                        inputPointIncrement = gamePanel.getPointIncrement();
                        spin = false;
                        showGuess = true;
                        messageRed = false;
                        message = "You Got " + inputPointIncrement + "!";
                        assemble();

                    }
                };

                int roundCount = (int) (wheelPanelInstance.increment / wheelPanelInstance.dx);
                int roundDur = (wheelPanelInstance.roundFreq * wheelPanelInstance.wheelRepaintFreq);
                int dur = roundCount * roundDur;

                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(stopSpin, dur, TimeUnit.MILLISECONDS);
            }
        });
    }

    public Boolean guessedPuzzle() {
        Boolean allCorrect = true;
        String[] secretSplit = secretLower.replaceAll(" ", "").split("");
        for (int i = 0; i < secretSplit.length; i++)
            if (!letters.contains(secretSplit[i]))
                allCorrect = false;
        // System.out.println(Arrays.toString(secretSplit) + " " + letters);
        return allCorrect;
    }

    public void handleClick() {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                if (processGuess(input) && !letters.contains(input)) {
                    textField.setText("");
                    guessText = "";
                    int tally = validateGuess(secretLower, input);
                    if (tally > 0) {
                        letters += input;
                        adjustPlayers(inputPointIncrement * tally, false);

                        if (guessedPuzzle()) {
                            showRestartButton = true;
                            message = "Hooray! You Guessed the Puzzle :)";
                            showGuess = false;
                        } else {
                            message = "Yay! You Guessed Correctly.";
                        }
                        messageRed = false;
                    } else {
                        tryCount--;
                        messageRed = true;
                        showGuess = false;

                        if (tryCount == 0) {
                            showRestartButton = true;
                            message = "You Ran Out of Tries :(";
                        } else {
                            message = "Incorrect! Better Luck Next Time :(";
                            showSpin = true;
                            inputPointIncrement = -1;
                            if (currentPlayer + 1 < playerCount) {
                                currentPlayer++;
                            } else {
                                currentPlayer = 0;
                            }
                        }
                    }
                    assemble();
                } else {
                    if (secretLower.equals(input.toLowerCase())) {
                        showRestartButton = true;
                        message = "Hooray! You Guessed the Puzzle :)";
                        showGuess = false;
                    } else {
                        message = "Invalid Input!";
                        messageRed = true;
                    }
                    assemble();
                }
            }
        });
    }
}
