package model.element.element_behaviours;

import model.Position;
import model.Room;
import model.element.Element;

import java.util.List;

public interface MoveStrategy {
    List<Position> genMoves(Room r, Element e);
}
