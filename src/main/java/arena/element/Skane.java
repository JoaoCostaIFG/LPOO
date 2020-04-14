package arena.element;

import arena.Position;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Skane extends Element implements DrawableElement, MortalElement, MovableElement{
    private Integer hp;

    public Skane(int x, int y) {
        super(x, y, "S");
        this.hp = DFLT_HP;
    }

    public void draw(TextGraphics gra) {
        gra.setForegroundColor(TextColor.Factory.fromString("#76A15D"));
        gra.enableModifiers(SGR.BOLD);
        gra.putString(new TerminalPosition(super.getX(), super.getY()), super.getMe());
        gra.disableModifiers(SGR.BOLD);
    }

    public Integer getHp() {
        return hp;
    }

    @Override
    public void setHp(Integer hp) {
        this.hp = hp;
    }

    @Override
    public Boolean damage(Integer dmg) {
        return null;
    }

    public boolean isAlive() {
        return (this.hp > 0);
    }

    /* movement */
    @Override
    public Position moveUp() {
        return new Position(super.getX(), super.getY() - 1);
    }

    @Override
    public Position moveUp(int y) {
        return new Position(super.getX(), super.getY() - y);
    }

    @Override
    public Position moveDown() {
        return new Position(super.getX(), super.getY() + 1);
    }

    @Override
    public Position moveDown(int y) {
        return new Position(super.getX(), super.getY() + y);
    }

    @Override
    public Position moveLeft() {
        return new Position(super.getX() - 1, super.getY());
    }

    @Override
    public Position moveLeft(int x) {
        return new Position(super.getX() - x, super.getY());
    }

    @Override
    public Position moveRight() {
        return new Position(super.getX() + 1, super.getY());
    }

    @Override
    public Position moveRight(int x) {
        return new Position(super.getX() + x, super.getY());
    }
}
