import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

class ComponentButton extends JToggleButton {
    private MemeComponent component;
    private BufferedImage img;
    private Color[] textColors = {Color.BLACK, Color.WHITE, Color.RED, Color.YELLOW};

    ComponentButton(BufferedImage img) {
        ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        this.img = img;

        setIcon(imageIcon);
        addActionListener(e -> {
            if (notAlreadyPresent()) {
                component = new ImageComponent(img);
                MainFrame.addComponent(component);
            }
        });
    }

    boolean hasContent(Object content) {
        return content.equals(img) || content.equals(getText());
    }

    boolean isActive() {
        return component != null;
    }

    ComponentButton(String text) {
        setText(text);
        setToolTipText(text);
        setPreferredSize(new Dimension(100, 30));
        addActionListener(e -> {
            if (notAlreadyPresent()) {
                component = new TextComponent(text);
                MainFrame.addComponent(component);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenu submenu = new JMenu("Farbe ausw\u00e4hlen");
                    for (Color c : textColors) {
                        JMenuItem colorItem = new JMenuItem(c.toString());
                        colorItem.setForeground(c);
                        colorItem.addActionListener(v -> {
                            component.setColor(c);
                            component.repaint();
                        });
                        submenu.add(colorItem);
                    }
                    menu.add(submenu);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    ComponentButton() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(100, 50));
        addActionListener(e -> {
            component = new BlankComponent();
            MainFrame.addComponent(component);
            setSelected(false);
        });
    }

    private boolean notAlreadyPresent() {
        if (component == null) {
            return true;
        } else {
            MainFrame.removeComponent(component);
            component = null;
            return false;
        }
    }
}
