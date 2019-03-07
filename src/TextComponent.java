import javax.swing.*;
import java.awt.*;

public class TextComponent extends MemeComponent {
    private String text;
    TextComponent(String text) {
        this.text = text;
    }

    private static Font impact() {
        for(Font f : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
            if(f.getFontName().equals("Impact")) {
                return f;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(new Font("Impact", Font.BOLD, 20));
        g.drawString(text, 0, getHeight());
        g.fillOval(0, 0, 20, 20);
    }
}
