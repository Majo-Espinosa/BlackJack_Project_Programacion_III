package co.edu.uptc.view.game.draw;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class CardJsonReader {

    private static List<CardData> cards;

    static {
        try {
            InputStreamReader reader = new InputStreamReader(
                    CardJsonReader.class.getResourceAsStream("/json/deck.json")
            );
            InputStream is = CardJsonReader.class.getResourceAsStream("/json/deck.json");
            System.out.println("is null? " + (is == null));
            if (reader == null) {
                throw new RuntimeException("No se encontró el archivo /json/deck.json");
            }

            Type cardListType = new TypeToken<List<CardData>>() {}.getType();
            cards = new Gson().fromJson(reader, cardListType);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer cards.json", e);
        }
    }

    public static String getPathByName(String name) {
        for (CardData card : cards) {
            if (card.name.equalsIgnoreCase(name)) {
                return card.path;
            }
        }
        throw new RuntimeException("No se encontró la carta con nombre: " + name);
    }
}

