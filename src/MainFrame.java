import javax.imageio.ImageIO;
import javax.swing.*;
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
            MemeComponent component1 = new ImageComponent(ImageIO.read(new File("./Bilder/Templates/Unbenannt.PNG")));
            component1.setLocation(100, 100);
            MemeComponent component2 = new BlankComponent();
            component2.setLocation(0, 0);
            add(component2);
            add(component1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
