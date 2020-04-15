package Creator;

import java.util.Random;

import arena.Map;
import arena.element.Skane;
import arena.element.Wall;
import com.googlecode.lanterna.TerminalSize;

public class ArenaCreator {
    private final Random random;

    public ArenaCreator() {
        this.random = new Random();
    }

    public Map createArena(TerminalSize size) {
        Map arena = new Map(size);

        /* spawn skane at random pos (inside bounds) */
        Skane skane = new Skane(random.nextInt(size.getColumns() - 2) + 1,
                random.nextInt(size.getRows() - 2) + 1);
        arena.addElement(skane);

        for (int c = 0; c < size.getColumns(); ++c) {
            arena.addElement(new Wall(c, 0));
            arena.addElement(new Wall(c, size.getRows() - 1));
        }

        for (int r = 1; r < size.getRows() - 1; ++r) {
            arena.addElement(new Wall(0, r));
            arena.addElement(new Wall(size.getColumns() - 1, r));
        }

        return arena;
    }
}
