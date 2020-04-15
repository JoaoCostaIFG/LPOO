package gui;

import arena.Map;
import arena.element.Skane;
import arena.element.Wall;

public interface GraphicsDrawer {
    void drawSkane(Skane ska);
    void drawWall(Wall wall);
    void drawMap(Map map);
}
