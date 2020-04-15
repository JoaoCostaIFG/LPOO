package gui;

import arena.element.Room;
import arena.element.Skane;
import arena.element.Wall;

public interface GraphicsDrawer {
    void drawSkane(Skane ska);
    void drawWall(Wall wall);
    void drawMap(Room map);
}
