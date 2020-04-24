package controller;

import gui.EVENT;
import room.Room;

public interface PlayerController extends Controller {
    void setEvent(EVENT event);
    void handleEvent(EVENT event, Room room);
}
