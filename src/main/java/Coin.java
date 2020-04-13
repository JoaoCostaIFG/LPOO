import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin extends Element {
    public Coin(int x, int y) {
        super(x, y, "C");
    }

    public void draw(TextGraphics gra) {
        gra.setForegroundColor(TextColor.Factory.fromString("#E0CD24"));
        gra.enableModifiers(SGR.BLINK);
        gra.putString(new TerminalPosition(super.getX(), super.getY()), super.getMe());
        gra.disableModifiers(SGR.BLINK);
    }
}
