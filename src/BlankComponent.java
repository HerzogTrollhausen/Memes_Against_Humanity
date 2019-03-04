import java.awt.*;

public class BlankComponent extends MemeComponent {

    BlankComponent() {
        setSize(100, 100);
    }

    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
