import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class TemplatePanel extends JPanel {
    private BufferedImage img = null;
    TemplatePanel() {
        try {
            img = ImageIO.read(new File("./Bilder/Templates/Unbenannt.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g){
        System.out.println("Hallo");
        g.drawImage(img, 100, 200, null);
    }
}
