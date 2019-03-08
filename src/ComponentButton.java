import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ComponentButton extends JToggleButton {
    private MemeComponent component;
    private BufferedImage img;

    ComponentButton(BufferedImage img) {
        ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        this.img = img;

        setIcon(imageIcon);
        addActionListener(e -> {
            if(notAlreadyPresent()) {
                component = new ImageComponent(img);
                MainFrame.addComponent(component);
            }
        });
    }

    boolean isImage(Object content) {
        return content.equals(img) || content.equals(getText());
    }

    boolean isActive() {
        return component != null;
    }

    ComponentButton(String text) {
        setText(text);
        addActionListener(e -> {
            if(notAlreadyPresent()) {
                component = new TextComponent(text);
                MainFrame.addComponent(component);
            }
        });
    }

    ComponentButton() {
        setBackground(Color.BLACK);
        addActionListener(e -> {
            if(notAlreadyPresent()) {
                component = new BlankComponent();
                MainFrame.addComponent(component);
            }
        });
    }

    private boolean notAlreadyPresent() {
        if(component == null) {
            return true;
        } else {
            MainFrame.removeComponent(component);
            component = null;
            return false;
        }
    }
}
