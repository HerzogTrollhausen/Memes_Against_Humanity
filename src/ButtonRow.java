import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

class ButtonRow extends JPanel {
    final static private int NUMBER_OF_BUTTONS = 4;

    private List<ComponentButton> buttons = new LinkedList<>();

    void fillUpRow() {
        while (buttons.size() < NUMBER_OF_BUTTONS) {
            BufferedImage img = MainFrame.randomComponent();
            if (!contains(img)) {
                ComponentButton b = new ComponentButton(img);
                buttons.add(b);
                add(b);
            }
        }
    }

    private boolean contains(BufferedImage img) {
        for (ComponentButton b : buttons) {
            if (b.isImage(img)) {
                return true;
            }
        }
        return false;
    }
}
