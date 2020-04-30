package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnsiImageImporter {
    List<String> importAnsiImage(String file_name) {
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
}
