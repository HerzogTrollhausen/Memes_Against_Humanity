import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageComponent extends MemeComponent {
    private final BufferedImage img;

    ImageComponent(BufferedImage img, JLayeredPane parent) {
        super(parent);
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
