package co.edu.uptc.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

// esta clase solo se utiliza para la automatizacion de la creacion del archivo json con la informacion
// de las imagenes
public class DeckJsonCreator {
    private Stack<String> cards;
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
    private static final String[] SUITS = { "H", "D", "C", "S" };
    private static final String[] SUIT_NAMES = { "HEARTS", "DIAMONDS", "CLUBS", "SPADES" };

    public void createDeckJson(int singleDecks, String outputPath) {
        cards = new Stack<>();
        JsonArray imagesArray = new JsonArray();

        for (int i = 0; i < singleDecks; i++) {
            for (int s = 0; s < SUITS.length; s++) {
                String suit = SUITS[s];
                String suitName = SUIT_NAMES[s];
                for (String rank : RANKS) {
                    String cardName = suit + rank;
                    String path = "assets/cards/" + suitName + "/" + suit + rank + ".png";
                    cards.add(cardName);

                    JsonObject cardJson = new JsonObject();
                    cardJson.addProperty("name", cardName);
                    cardJson.addProperty("path", path);
                    imagesArray.add(cardJson);
                }
            }
        }
        saveJson(imagesArray, outputPath);
    }

    private void saveJson(JsonArray imagesArray, String outputPath) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("images", imagesArray);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(outputPath)) {
            gson.toJson(jsonObject, writer);
            System.out.println("JSON file created successfully at: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DeckJsonCreator deckJsonCreator = new DeckJsonCreator();
        deckJsonCreator.createDeckJson(1, "data/images.json");
    }
}
