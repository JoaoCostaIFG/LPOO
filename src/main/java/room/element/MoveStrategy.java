package room.element;

import room.Position;

import java.util.List;

public interface MoveStrategy {
    List<Position> execute(Entity e);
}
