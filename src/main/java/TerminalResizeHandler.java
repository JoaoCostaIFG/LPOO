import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

public class TerminalResizeHandler implements TerminalResizeListener {
    private TerminalSize tsize;
    private Boolean has_resized;

    public TerminalResizeHandler(TerminalSize current_size) {
        this.has_resized = false;
        this.tsize = current_size;
    }

    public Boolean hasResized() {
        if (has_resized) {
            this.has_resized = false;
            return true;
        }

        return false;
    }

    public TerminalSize getLastKnownSize() {
        return tsize;
    }

    public void onResized(Terminal terminal, TerminalSize newSize) {
        this.has_resized = true;
        this.tsize = newSize;
    }
}
