package org.g73.skanedweller.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class KeyHandler {
    
    public EVENT processKey(KeyStroke key) {
        EVENT event = EVENT.NullEvent;
        
        if (key == null)
            return event;

        if (key.getKeyType() == KeyType.Character) {
            switch (key.getCharacter()) {
                case 'a':
                case 'A':
                    event = EVENT.MoveLeft;
                    break;
                case 'd':
                case 'D':
                    event = EVENT.MoveRight;
                    break;
                case 'w':
                case 'W':
                    event = EVENT.MoveUp;
                    break;
                case 's':
                case 'S':
                    event = EVENT.MoveDown;
                    break;
                case 'r':
                case 'R':
                    event = EVENT.RestartGame;
                    break;
                case 'q':
                case 'Q':
                    event = EVENT.QuitGame;
                    break;
                case ' ':
                    event = EVENT.Bury;
                default:
                    break;
            }
        }

        switch (key.getKeyType()) {
            case ArrowLeft:
                event = EVENT.MoveLeft;
                break;
            case ArrowRight:
                event = EVENT.MoveRight;
                break;
            case ArrowUp:
                event = EVENT.MoveUp;
                break;
            case ArrowDown:
                event = EVENT.MoveDown;
                break;
            case Escape:
            case EOF:
                event = EVENT.QuitGame;
                break;
            default:
                break;
        }
        return event;
    }
}
