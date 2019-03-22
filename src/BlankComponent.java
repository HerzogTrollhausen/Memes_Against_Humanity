import java.awt.*;

public class BlankComponent extends MemeComponent {

    private Color c;

    BlankComponent(Color c) {
        this.c = c;
    }

    public void paintComponent(Graphics g) {
        g.setColor(c);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
