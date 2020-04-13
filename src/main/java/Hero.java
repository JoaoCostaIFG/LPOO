import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Hero extends Element {
    private int hp;

    public Hero(int x, int y) {
        super(x, y, "H");
        this.hp = 3;
    }

    public void draw(TextGraphics gra) {
        gra.setForegroundColor(TextColor.Factory.fromString("#76A15D"));
        gra.enableModifiers(SGR.BOLD);
        gra.putString(new TerminalPosition(super.getX(), super.getY()), super.getMe());
        gra.disableModifiers(SGR.BOLD);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isAlive() {
        return (this.hp > 0);
    }

    /* movement */
    public Position moveUp() {
        return new Position(super.getX(), super.getY() - 1);
    }

    public Position moveUp(int y) {
        return new Position(super.getX(), super.getY() - y);
    }

    public Position moveDown() {
        return new Position(super.getX(), super.getY() + 1);
    }

    public Position moveDown(int y) {
        return new Position(super.getX(), super.getY() + y);
    }

    public Position moveLeft() {
        return new Position(super.getX() - 1, super.getY());
    }

    public Position moveLeft(int x) {
        return new Position(super.getX() - x, super.getY());
    }

    public Position moveRight() {
        return new Position(super.getX() + 1, super.getY());
    }

    public Position moveRight(int x) {
        return new Position(super.getX() + x, super.getY());
    }
}
