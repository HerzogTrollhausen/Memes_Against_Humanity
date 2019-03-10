import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class MainFrame extends JFrame {
    private static JLayeredPane interactiveLayeredPane;
    private static final String IMAGE_COMPONENT_FOLDER = "Components";
    private static final String TEMPLATE_FOLDER = "Templates";
    private static final String STRING_COMPONENTS_FILE = "Texte.txt";
    private static String[] componentStrings;
    private static BufferedImage[] componentImages;
    private static BufferedImage[] templateImages;
    private ButtonRow topRow;
    private ButtonRow bottomRow;

    private MainFrame(String contentFolder) {
        super("Maimais gegen die Menschlichkeit");
        System.out.println(contentFolder);
        componentStrings = contentOfFile(new File(contentFolder + STRING_COMPONENTS_FILE));
        componentImages = imagesInFolder(new File(contentFolder + IMAGE_COMPONENT_FOLDER));
        templateImages = imagesInFolder(new File(contentFolder + TEMPLATE_FOLDER));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        //getContentPane().setLayout(new GridBagLayout());
        //GridBagConstraints c = new GridBagConstraints();

        JPanel oben = new JPanel();

        interactiveLayeredPane = new JLayeredPane();
        interactiveLayeredPane.setPreferredSize(new Dimension(400, 400));
        interactiveLayeredPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newTemplate();
        oben.add(interactiveLayeredPane);

        JButton next = new JButton("Weiter");
        next.addActionListener(e -> newTemplate());
        oben.add(next);

        getContentPane().add(oben);

        JPanel unten = new JPanel();
        unten.setLayout(new BoxLayout(unten, BoxLayout.PAGE_AXIS));

        topRow = new ButtonRow(true);
        topRow.fillUpRow();
        unten.add(topRow);

        bottomRow = new ButtonRow(false);
        bottomRow.fillUpRow();
        unten.add(bottomRow);

        unten.add(new ComponentButton());

        getContentPane().add(unten);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);

    }

    private void newTemplate() {
        interactiveLayeredPane.removeAll();
        if (topRow != null && bottomRow != null) {
            topRow.cleanUpUsedButtons();
            bottomRow.cleanUpUsedButtons();
            topRow.fillUpRow();
            bottomRow.fillUpRow();
        }
        setTemplate(new TemplateImage((BufferedImage) randomArrayElement(templateImages)));
        repaint();
        revalidate();
    }

    private void setTemplate(TemplateImage template) {
        interactiveLayeredPane.add(template);
        template.setSize(interactiveLayeredPane.getPreferredSize());
        interactiveLayeredPane.setLayer(template, Integer.MIN_VALUE);
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
        return (BufferedImage) randomArrayElement(componentImages);
    }

    private static Object randomArrayElement(Object[] a) {
        if (a != null && a.length > 0) {
            return a[((int) (Math.random() * a.length))];
        }
        return null;
    }

    static String randomString() {
        return (String) randomArrayElement(componentStrings);
    }

    private BufferedImage loadImage(String path) {
        try {
            System.out.println(path);
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    private static String[] contentOfFile(File f) {
        try {
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Stream<String> result = bufferedReader.lines();
            if (result != null) {
                Object[] obs = result.toArray();
                bufferedReader.close();
                fileReader.close();
                String[] strings = new String[obs.length];
                for (int i = 0; i < obs.length; i++) {
                    strings[i] = (String) obs[i];
                }
                return strings;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
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
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = images.get(i);
        }
        return tmp;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {

        }
        JFileChooser chooser = new JFileChooser("C:\\Users\\User\\IdeaProjects\\Meme");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            new MainFrame(chooser.getSelectedFile().getPath() + "/");
        }
    }
}
