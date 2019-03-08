import java.awt.*;
import java.util.ArrayList;

public class TextComponent extends MemeComponent {
    private String text;
    private static Font font = new Font("Impact", Font.BOLD, 20);

    TextComponent(String text) {
        this.text = text;
        //minWidth = font.getSize()*2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(font);
        FontMetrics m = g.getFontMetrics();
        ArrayList<String> lines = new ArrayList<>();
        char[] ca = text.toCharArray();
        int i = 0;
        outer:
        while (i < ca.length) {
            StringBuilder line = new StringBuilder();
            while (m.stringWidth(line.append(ca[i]).toString()) < getWidth()) {
                i++;
                if (i == ca.length) {
                    lines.add(line.toString());
                    break outer;
                }
            }
            if (line.length() > 1) {
                line.deleteCharAt(line.length() - 1);
            } else {
                i++;
            }
            lines.add(line.toString());
        }
        for (int j = 0; j < lines.size(); j++) {
            g.drawString(lines.get(j), 0, (j + 1) * m.getHeight());
        }
    }
}
