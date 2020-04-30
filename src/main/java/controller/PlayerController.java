package controller;

import view.EVENT;
import model.Room;

public interface PlayerController extends Controller {
    void setEvent(EVENT event);
    void handleEvent(EVENT event, Room room);
}
