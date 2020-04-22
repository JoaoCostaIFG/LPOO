package gui;

import room.Room;
import room.element.Entity;
import room.element.skane.Skane;
import room.element.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface GraphicsDrawer {
    void drawSkane(Skane ska);

    void drawWall(Wall wall);

    void drawCivie(Entity civie);

    void drawMelee(Entity melee);

    void drawImage(List<String> image, int x, int y);

    static List<String> importAnsiImage(String file_name) {
        List<String> image = new ArrayList<>();

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

    void drawRoom(Room map);
}
