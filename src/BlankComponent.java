import javax.swing.*;
import java.awt.*;

public class BlankComponent extends MemeComponent {
    BlankComponent(JLayeredPane parent) {
        super(parent);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
