package gui;

import arena.Map;
import arena.element.Skane;
import arena.element.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public interface GraphicsDrawer {
    void drawSkane(Skane ska);

    void drawWall(Wall wall);

    void drawMap(Map map);

    void drawImage(int x, int y, List<String> image);

    static List<String> importAnsiImage(String file_name) {
        List<String> image = new LinkedList<>();

        try {
            File image_file = new File(file_name);
            Scanner reader = new Scanner(image_file);
            while (reader.hasNextLine())
                image.add(reader.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("couldn't read file");
            e.printStackTrace();
        }

        return image;
    }
}
