package co.edu.uptc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {
    private static final Gson compactGson = new Gson();
    private static final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object object) {
        return compactGson.toJson(object);
    }

    public static String toPrettyJson(Object object) {
        return prettyGson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }

        try {
            return compactGson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing JSON: " + json);
            System.err.println("Exception: " + e.getMessage());
            return null;
        }
    }

    public static boolean isValidJson(String json) {
        if (json == null || json.trim().isEmpty()) {
            return false;
        }

        try {
            compactGson.fromJson(json, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}