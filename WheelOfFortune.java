import javax.swing.*;

public class WheelOfFortune {
    final static int WIDTH = 750;
    final static int HEIGHT = 600;
    final static Boolean SHOW = true;

    final static String secret = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wheel of Fortune");

        GameEngine engine = new GameEngine(frame, secret);
        engine.assemble();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(SHOW);
    }
}
