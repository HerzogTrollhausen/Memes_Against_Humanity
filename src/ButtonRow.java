import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class ButtonRow extends JPanel {
    final static private int NUMBER_OF_BUTTONS = 10;

    private List<ComponentButton> buttons = new ArrayList<>();
    private boolean imageType;

    /**
     * @param imageType If true, this row consists of Buttons for ImageComponents,
     *                  otherwise for TextComponents
     */
    ButtonRow(boolean imageType) {
        this.imageType = imageType;
    }

    void fillUpRow() {
        ArrayList availableContent = imageType
                ? MainFrame.getComponentImages()
                : MainFrame.getComponentStrings();
        while (buttons.size() < NUMBER_OF_BUTTONS && !availableContent.isEmpty()) {
            Object content;
            ComponentButton b;
            content = availableContent.remove((int) (Math.random() * availableContent.size()));
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

    void cleanUpUsedButtons() {
        int i = 0;
        while (i < buttons.size()) {
            ComponentButton b = buttons.get(i);
            if (b.isActive()) {
                remove(b);
                buttons.remove(b);
            } else {
                i++;
            }
        }
    }

    private boolean contains(Object content) {
        for (ComponentButton b : buttons) {
            if (b.hasContent(content)) {
                return true;
            }
        }
        return false;
    }
}
