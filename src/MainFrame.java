import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private MainFrame() {
       super("Maimais gegen die Menschlichkeit");
       setSize(1000, 800);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLayout(null);

       //add(new TemplatePanel());
        try {
            add(new ComponentImage(ImageIO.read(new File("./Bilder/Templates/Unbenannt.PNG"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
