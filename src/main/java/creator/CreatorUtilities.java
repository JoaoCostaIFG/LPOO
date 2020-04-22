package creator;

import room.Position;

import java.util.HashSet;
import java.util.Random;

public class CreatorUtilities {
    private final Random random;
    private HashSet<Position> filled_pos;

    public CreatorUtilities() {
        this.random = new Random();
        this.filled_pos = new HashSet<>(100);
    }

    public CreatorUtilities(Random random) {
        this.random = random;
        this.filled_pos = new HashSet<>(100);
    }

    public Position getRandomPos(int startx, int endx, int starty, int endy) {
        /* we generate random postions until we find one that hasn't been taken yet */
        boolean repeated;
        Position position;
        do {
            position = new Position(random.nextInt(endx - 1) + startx,
                    random.nextInt(endy - 1) + starty);

            repeated = false;
            for (Position p : filled_pos) {
                if (p.equals(position)) {
                    repeated = true;
                    break;
                }
            }
        } while (repeated);

        return position;
    }

    public Position getRdmPosRoom(int width, int heigth) {
        /* get random inbound postion */
        return getRandomPos(1, width - 1, 1, heigth - 1);
    }

    public void regPos(Position p) {
        filled_pos.add(p);
    }
}
