import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

class ButtonRow extends JPanel {
    final static private int NUMBER_OF_BUTTONS = 4;

    private List<ComponentButton> buttons = new LinkedList<>();
    private boolean imageType;

    /**
     * @param imageType If true, this row consists of Buttons for ImageComponents,
     *                  otherwise for TextComponents
     */
    ButtonRow(boolean imageType) {
        this.imageType = imageType;
    }

    @SuppressWarnings("ConstantConditions")
    void fillUpRow() {
        while (buttons.size() < NUMBER_OF_BUTTONS) {
            Object content;
            ComponentButton b;
            if (imageType) {
                content = MainFrame.randomComponent();
            } else {
                content = MainFrame.randomString();
            }
            if (!contains(content)) {
                if (imageType) {
                    b = new ComponentButton((BufferedImage) content);
                } else {
                    b = new ComponentButton((String) content);
                }
                buttons.add(b);
                add(b);
            }
        }
    }

    private boolean contains(Object content) {
        for (ComponentButton b : buttons) {
            if (b.isImage(content)) {
                return true;
            }
        }
        return false;
    }
}
