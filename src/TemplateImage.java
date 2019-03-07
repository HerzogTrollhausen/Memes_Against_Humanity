import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class TemplateImage extends JComponent {
    private BufferedImage img;

    TemplateImage(BufferedImage img) {
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}
