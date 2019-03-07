import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private static JLayeredPane interactiveLayeredPane;
    private static BufferedImage[] components;
    private static File componentFolder = new File("./Bilder/Components");
    private MainFrame() {
        super("Maimais gegen die Menschlichkeit");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        components = imagesInFolder(componentFolder);

        JPanel oben = new JPanel();

        interactiveLayeredPane = new JLayeredPane();
        interactiveLayeredPane.setBackground(Color.RED);
        interactiveLayeredPane.setPreferredSize(new Dimension(400, 400));
        interactiveLayeredPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JComponent template = new TemplateImage(loadImage("./Bilder/Templates/Unbenannt.PNG"));
        interactiveLayeredPane.add(template);
        template.setSize(interactiveLayeredPane.getPreferredSize());
        interactiveLayeredPane.setLayer(template, Integer.MIN_VALUE);
        oben.add(interactiveLayeredPane);
        add(oben);

        JPanel unten = new JPanel();
        unten.setBackground(Color.GREEN);
        ButtonRow topRow = new ButtonRow();
        topRow.fillUpRow();
        unten.add(topRow);
        add(unten);
        setVisible(true);

    }

    static void removeComponent(JComponent component) {
        interactiveLayeredPane.remove(component);
        interactiveLayeredPane.repaint();
    }

    static void increaseDepth(JComponent component, int change) {
        interactiveLayeredPane.setLayer(component, JLayeredPane.getLayer(component) + change);
    }

    static void addComponent(MemeComponent component) {
        interactiveLayeredPane.add(component);
    }

    static BufferedImage randomComponent() {
        return (BufferedImage)randomArrayElement(components);
    }

    private static Object randomArrayElement(Object[] a) {
        if (a != null && a.length > 0) {
            return a[((int) (Math.random() * a.length))];
        }
        return null;
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    private static BufferedImage[] imagesInFolder(File folder) {
        ArrayList<BufferedImage> images = new ArrayList<>();
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
        BufferedImage[] tmp = new BufferedImage[images.size()];
        for(int i = 0; i < tmp.length; i++) {
            tmp[i] = images.get(i);
        }
        return tmp;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
