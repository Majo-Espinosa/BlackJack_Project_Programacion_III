package co.edu.uptc.utils;

import java.awt.Image;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ImgManager {

    private ImgManager() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    private static Map<String, ImageIcon> imageMap = new HashMap<>();

    static {
        loadImagesFromJson(PropertiesManager.getImgPath());
    }

    public static ImageIcon getImageIcon(String imageName) {
        return imageMap.get(imageName);
    }

    public static Image getImage(String imageName) {
        return imageMap.get(imageName).getImage();
    }

    private static void loadImagesFromJson(String jsonFilePath) {
        try (FileReader reader = new FileReader(jsonFilePath)) {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(reader).getAsJsonObject();
            JsonArray images = jsonObject.getAsJsonArray("images");

            for (var imageElement : images) {
                JsonObject image = imageElement.getAsJsonObject();
                String name = image.get("name").getAsString();
                String path = image.get("path").getAsString();

                ImageIcon imgIcon = new ImageIcon(ImageIO.read(new File(path)));
                imageMap.put(name, imgIcon);
                System.out.println("Loaded image: " + name + " : " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon changeIcon(Icon icon, int width, int height) {
        ImageIcon ic = (ImageIcon) icon;
        Image image = ic.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

}
