import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ComponentImage extends JComponent {
    private final BufferedImage img;
    private int resizingSpeed = 1;

    private int pressedX;
    private int pressedY;

    ComponentImage(BufferedImage img) {
        super();
        setSize(new Dimension(100, 100));
        setLocation(100, 100);
        this.img = img;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressedX = e.getX();
                pressedY = e.getY();
                requestFocus();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getX() + e.getX() - pressedX,
                        getY() + e.getY() - pressedY);

            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case (KeyEvent.VK_SHIFT): {
                        resizingSpeed = 5;
                        break;
                    }
                    case (KeyEvent.VK_LEFT): {
                        setSize(getWidth() - resizingSpeed, getHeight());
                        break;
                    }
                    case (KeyEvent.VK_RIGHT): {
                        setSize(getWidth() + resizingSpeed, getHeight());
                        break;
                    }
                    case (KeyEvent.VK_UP): {
                        setSize(getWidth(), getHeight() - resizingSpeed);
                        break;
                    }
                    case (KeyEvent.VK_DOWN): {
                        setSize(getWidth(), getHeight() + resizingSpeed);
                        break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    resizingSpeed = 1;
                }
            }
        });

    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        paintBorder(g);
    }
}
