import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WheelPanel {
    double rotateAmount = 0.0d;
    ImageIcon wheelIMG;
    BufferedImage bi;
    Boolean spin;
    double increment = Math.ceil(Math.random() * 5) + 5;
    int round = 0;
    double dx = 0.2;
    int roundFreq = 5;
    int wheelRepaintFreq = 25;

    public WheelPanel(ImageIcon inputWheelIMG, Boolean inputSpin, double wheelRotateAmount) {
        wheelIMG = inputWheelIMG;
        spin = inputSpin;
        rotateAmount = wheelRotateAmount;

    }

    public JPanel init() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        bi = new BufferedImage(wheelIMG.getIconWidth(), wheelIMG.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        wheelIMG.paintIcon(null, g, 0, 0);
        g.dispose();

        BufferedImage wheelRotated = rotate(bi, 0.0d);
        wheelIMG = new ImageIcon(wheelRotated);

        JLabel wheelLabel = new JLabel(wheelIMG);
        panel.add(wheelLabel, BorderLayout.CENTER);

        if (spin) {
            Runnable intervalFunc = new Runnable() {
                public void run() {
                    if (round % roundFreq == 0 && increment - dx >= 0) {
                        increment -= dx;
                    }

                    spin(panel, wheelLabel, true);
                    // System.out.println(rotateAmount);
                    round++;
                }
            };
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(intervalFunc, 0, wheelRepaintFreq, TimeUnit.MILLISECONDS);
        } else {
            spin(panel, wheelLabel, false);
        }

        return panel;
    }

    public void spin(JPanel panel, JLabel wheelLabel, Boolean incrementRotate) {
        if (incrementRotate) {
            if (rotateAmount + increment > 360) {
                double surplus = rotateAmount + increment - 360;
                rotateAmount = surplus;
            } else {
                rotateAmount += increment;
            }
        }

        BufferedImage wheelRotated = rotate(bi, rotateAmount);
        wheelIMG = new ImageIcon(wheelRotated);
        JLabel wheelLabelUpdated = new JLabel(wheelIMG);

        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        panel.add(wheelLabelUpdated, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    public BufferedImage rotate(BufferedImage image, Double degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();

        int x = (newWidth - image.getWidth()) / 2;
        int y = (-250 + image.getHeight()) / 2;

        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);

        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotate;
    }
}
