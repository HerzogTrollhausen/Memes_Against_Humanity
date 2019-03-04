import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class MainFrame extends JFrame {
    private MainFrame() {
        super("Maimais gegen die Menschlichkeit");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        //setLayout(null);

        JPanel oben = new JPanel();

        JLayeredPane interactiveLayeredPane = new JLayeredPane();
        //interactiveLayeredPane.add(new TemplatePanel(), 0);
        interactiveLayeredPane.setBackground(Color.RED);
        interactiveLayeredPane.setPreferredSize(new Dimension(400, 400));
        interactiveLayeredPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        interactiveLayeredPane.add(new BlankComponent(interactiveLayeredPane));
        oben.add(interactiveLayeredPane);
        add(oben);

        JPanel unten = new JPanel();
        unten.setBackground(Color.GREEN);
        add(unten);
        setVisible(true);
        pack();

        System.out.println(interactiveLayeredPane.getSize());
    }

    private LinkedList<BufferedImage> imagesInFolder(String path) {
        File folder = new File(path);
        LinkedList<BufferedImage> images = new LinkedList<>();
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File f : files) {
                    try {
                        images.add(ImageIO.read(f));
                    } catch (IOException ignored) {

                    }
                }
            }
        }
        return images;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
