import jdk.nashorn.internal.scripts.JO;

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
    private static ArrayList<String> componentStrings;
    private static ArrayList<BufferedImage> componentImages;
    private static ArrayList<BufferedImage> templateImages;
    private ButtonRow topRow;
    private ButtonRow bottomRow;

    private MainFrame(String contentFolder) throws IOException, InsufficientFilesException {
        super("Maimais gegen die Menschlichkeit");
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
        setTemplate(new TemplateImage((BufferedImage) randomArrayListElement(templateImages)));
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

    private static Object randomArrayListElement(ArrayList a) {
        if (a != null && a.size() > 0) {
            return a.get(((int) (Math.random() * a.size())));
        }
        return null;
    }

    private static ArrayList<String> contentOfFile(File f) throws IOException,
            InsufficientFilesException.EmptyTextFileException {

        FileReader fileReader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Stream<String> result = bufferedReader.lines();
        if (result != null) {
            Object[] obs = result.toArray();
            bufferedReader.close();
            fileReader.close();
            if (obs.length > 0) {
                ArrayList<String> strings = new ArrayList<>(obs.length);
                for (Object ob : obs) {
                    strings.add((String) ob);
                }
                return strings;
            } else {
                throw new InsufficientFilesException.EmptyTextFileException();
            }
        } else {
            throw new IllegalStateException("result is null");
        }
    }

    @SuppressWarnings("unchecked")
    static ArrayList<String> getComponentStrings() {
        return (ArrayList<String>) componentStrings.clone();
    }

    @SuppressWarnings("unchecked")
    static ArrayList<BufferedImage> getComponentImages() {
        return (ArrayList<BufferedImage>) componentImages.clone();
    }


    private static ArrayList<BufferedImage> imagesInFolder(File folder) throws IOException,
            InsufficientFilesException.MissingImagesException {
        ArrayList<BufferedImage> images = new ArrayList<>();
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files == null) {
                throw new IllegalArgumentException("folder.listFiles returns null");
            }
            if (files.length > 0) {
                for (File f : files) {
                    images.add(ImageIO.read(f));
                }
            } else {
                throw new InsufficientFilesException.MissingImagesException();
            }
        }
        return images;
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
            try {
                new MainFrame(chooser.getSelectedFile().getPath() + "/");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Der Ordner hat nicht die korrekte Struktur! "
                                + "Im Ordner müssen sich "
                                + System.lineSeparator()
                                + "- ein Ordner namens 'Templates', "
                                + System.lineSeparator()
                                + "- ein Ordner namens 'Components' und "
                                + System.lineSeparator()
                                + "- eine Datei namens 'Texte.txt' befinden.");
            } catch (InsufficientFilesException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }
}
