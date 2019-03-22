import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class MainFrame extends JFrame {
    private static JLayeredPane interactiveLayeredPane;
    private static final String IMAGE_COMPONENT_FOLDER = "Components";
    private static final String TEMPLATE_FOLDER = "Templates";
    private static final String STRING_COMPONENTS_FILE = "Texte.txt";
    private static final int MAX_EDGE_HEIGHT = 500;
    private static final Dimension MEME_SIZE = new Dimension(600, 600);
    private static ArrayList<String> componentStrings;
    private static ArrayList<BufferedImage> componentImages;
    private static ArrayList<BufferedImage> templateImages;
    private ButtonRow topRow;
    private ButtonRow bottomRow;
    private TemplateImage templateImage;
    private int topEdgeHeight;
    private int bottomEdgeHeight;

    private MainFrame(String contentFolder) throws IOException, InsufficientFilesException {
        super("Maimais gegen die Menschlichkeit");
        componentStrings = contentOfFile(new File(contentFolder + STRING_COMPONENTS_FILE));
        componentImages = imagesInFolder(new File(contentFolder + IMAGE_COMPONENT_FOLDER));
        templateImages = imagesInFolder(new File(contentFolder + TEMPLATE_FOLDER));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel oben = new JPanel();

        interactiveLayeredPane = new JLayeredPane();
        interactiveLayeredPane.setPreferredSize(MEME_SIZE);
        interactiveLayeredPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        interactiveLayeredPane.setOpaque(true);
        interactiveLayeredPane.setBackground(Color.WHITE);
        interactiveLayeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshInteractivePane();
            }
        });

        newTemplate();

        oben.add(interactiveLayeredPane);

        JPanel spinnerPanel = new JPanel();
        spinnerPanel.setLayout(new BoxLayout(spinnerPanel, BoxLayout.PAGE_AXIS));
        SpinnerNumberModel topModel = new SpinnerNumberModel(10, 0, MAX_EDGE_HEIGHT, 5);
        JSpinner topSpinner = new JSpinner(topModel);
        SpinnerNumberModel bottomModel = new SpinnerNumberModel(10, 0, MAX_EDGE_HEIGHT, 5);
        JSpinner bottomSpinner = new JSpinner(bottomModel);
        topSpinner.addChangeListener(e -> changeEdgeHeight((int) topSpinner.getValue(), -1));
        bottomSpinner.addChangeListener(e -> changeEdgeHeight(-1, (int) bottomSpinner.getValue()));
        spinnerPanel.add(topSpinner);
        spinnerPanel.add(bottomSpinner);
        oben.add(spinnerPanel);

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

        JPanel colorButtonRow = new JPanel();
        colorButtonRow.add(new ComponentButton(Color.BLACK));
        colorButtonRow.add(new ComponentButton(Color.WHITE));
        unten.add(colorButtonRow);

        getContentPane().add(unten);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);

    }

    /**
     * Change the height of the white bars above and below the template
     *
     * @param topValue    The new height of the top edge. -1 for no change
     * @param bottomValue The new height of the bottom edge. -1 for no change
     */
    private void changeEdgeHeight(int topValue, int bottomValue) {
        if (topValue != -1) {
            topEdgeHeight = topValue;
        }
        if (bottomValue != -1) {
            bottomEdgeHeight = bottomValue;
        }
        refreshInteractivePane();
    }

    private void newTemplate() {
        if (templateImage != null) {
            interactiveLayeredPane.remove(templateImage);
        }
        if (topRow != null && bottomRow != null) {
            topRow.cleanUpUsedButtons();
            bottomRow.cleanUpUsedButtons();
            topRow.fillUpRow();
            bottomRow.fillUpRow();
        }
        templateImage = new TemplateImage((BufferedImage) randomArrayListElement(templateImages));
        interactiveLayeredPane.add(templateImage);
        interactiveLayeredPane.setLayer(templateImage, Integer.MIN_VALUE);
        refreshInteractivePane();
        repaint();
    }

    private void refreshInteractivePane() {
        double desiredRelation = templateImage.getImageHeight() / templateImage.getImageWidth();
        int availableWidth = interactiveLayeredPane.getWidth();
        int availableHeight = interactiveLayeredPane.getHeight()
                - topEdgeHeight - bottomEdgeHeight;
        if (templateImage.getImageHeight() > templateImage.getImageWidth()) {
            templateImage.setSize((int) (availableHeight / desiredRelation),
                    availableHeight);
        } else {
            if (availableWidth * desiredRelation < availableHeight) {
                templateImage.setSize(availableWidth,
                        (int) (availableWidth * desiredRelation));
            } else {
                templateImage.setSize((int) (availableHeight / desiredRelation),
                        availableHeight);
            }
        }
        templateImage.setLocation(0, topEdgeHeight);
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
        if (args.length == 0) {
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
        } else {
            try {
                new MainFrame(args[0]);
            } catch (IOException | InsufficientFilesException e) {
                e.printStackTrace();
            }
        }
    }
}
