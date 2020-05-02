package org.g73.skanedweller.controller;

import org.g73.skanedweller.model.Room;
import org.g73.skanedweller.view.EVENT;

public interface PlayerController extends Controller {
    void setEvent(EVENT event);
    void handleEvent(EVENT event, Room room);
}
