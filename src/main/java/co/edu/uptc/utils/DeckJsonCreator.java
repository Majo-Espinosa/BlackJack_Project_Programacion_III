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
    private static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final String[] SUIT_NAMES = { "C", "D", "H", "P" };
    private static final String[] RANKS_AUX = {"ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};
    private static final String[] SUIT_NAMES_AUX = { "CLUBS", "DIAMONDS", "HEARTS", "SPADES" };

    public void createDeckJson(int singleDecks, String outputPath) {
        cards = new Stack<>();
        JsonArray imagesArray = new JsonArray();
        String cardPathName = "/images/cards/light/";
        for (int i = 0; i < singleDecks; i++) {
            for (int s = 0; s < SUIT_NAMES.length; s++) {
                String suit = SUIT_NAMES[s];
                for (int r = 0; r < RANKS.length; r++) {
                    String cardName = RANKS[r] + "-" + suit;
                    String cardNameAux = RANKS_AUX[r] + " of " + SUIT_NAMES_AUX[s];
                    cards.add(cardName);
                    JsonObject cardJson = new JsonObject();
                    cardJson.addProperty("name", cardNameAux);
                    cardJson.addProperty("path", cardPathName + cardName + ".png");
                    imagesArray.add(cardJson);
                }
            }
        }
        JsonObject cardJson = new JsonObject();
        cardJson.addProperty("name", "CARTA OCULTA");
        cardJson.addProperty("path", "/images/cards/card_back.png");
        imagesArray.add(cardJson);
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
        deckJsonCreator.createDeckJson(1, "src/main/resources/json/deck.json");
    }
}
