package org.g73.skanedweller.view;

import com.googlecode.lanterna.TextColor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.googlecode.lanterna.TextColor.Factory.fromString;

public class Colors {
    private Map<String, TextColor> colors;
    private TextColor fallBackColor;

    public Colors(String colorsResName) throws IOException {
        this(colorsResName, fromString("#000000"));
    }

    public Colors(String colorsResName, TextColor fallBackColor) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(colorsResName);
        if (is == null)
            throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        this.fallBackColor = fallBackColor;
        this.colors = new HashMap<String, TextColor>();
        importColors(br);
    }

    private void importColors(BufferedReader br) throws IOException {
        // example:
        // private final TextColor bg = fromString("#313742");

        String line = br.readLine();
        do {
            String[] colorRead = line.split(" ");
            colors.put(colorRead[0], fromString(colorRead[1]));
            line = br.readLine();
        } while (line != null);
    }

    public TextColor getColor(String colorName) {
        if (this.colors.containsKey(colorName))
            return this.colors.get(colorName);

        return this.fallBackColor;
    }
}
