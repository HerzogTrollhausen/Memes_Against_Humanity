import javax.swing.*;
import java.awt.*;

public class BlankComponent extends MemeComponent {

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
