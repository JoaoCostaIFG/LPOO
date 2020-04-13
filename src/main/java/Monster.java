import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    private int move_cd = 0;

    public Monster(int x, int y) {
        super(x, y, "M");
    }

    public void draw(TextGraphics gra) {
        gra.setForegroundColor(TextColor.Factory.fromString("#E0254C"));
        gra.putString(new TerminalPosition(super.getX(), super.getY()), super.getMe());
    }

    public void monsterAttack() {
        this.move_cd = 4;
    }

    public boolean canMonsterAttack() {
        return (this.move_cd == 0);
    }

    public Position move() {
        if (this.move_cd > 0) {
            --this.move_cd;
            return new Position(super.getX(), super.getY());
        }

        Random random = new Random();
        if (random.nextInt(2) == 1) {
            return new Position(super.getX(), super.getY() + random.nextInt(3) - 1);
        } else {
            return new Position(super.getX() + random.nextInt(3) - 1, super.getY());
        }
    }
}
