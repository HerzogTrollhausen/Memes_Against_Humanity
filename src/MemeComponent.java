import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

abstract class MemeComponent extends JComponent {
    private int resizingSpeed = 1;

    private int pressedX;
    private int pressedY;

    MemeComponent() {
        super();
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
                        if (getWidth() - resizingSpeed > 0) {
                            setSize(getWidth() - resizingSpeed, getHeight());
                        }
                        break;
                    }
                    case (KeyEvent.VK_RIGHT): {
                        setSize(getWidth() + resizingSpeed, getHeight());
                        break;
                    }
                    case (KeyEvent.VK_UP): {
                        if (getHeight() - resizingSpeed > 0) {
                            setSize(getWidth(), getHeight() - resizingSpeed);
                        }
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
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    resizingSpeed = 1;
                }
            }
        });

    }
}