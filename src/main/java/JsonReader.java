import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonReader {
    private String word_form;
    private String lemma;
    private Object[] features_keys = null;
    private Object[] features_values = null;

    public JsonReader(String str, String word) {
        word = word.toLowerCase();
        str = str.toLowerCase();

        str = str.replaceAll("[\\!.\\,\\?]", ""); // Strips the String of !?., characters
        JSONParser parser = new JSONParser();
        try (Reader JsonReader = new FileReader("data.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(JsonReader);

            JSONArray outerArray = (JSONArray) jsonObject.get("sentences");
            JSONArray innerArray = (JSONArray) outerArray.get(0);

            if (!containsWord(str, word)) {
                throw new IllegalStateException("Sentence does not contain word");
            }

            int index = findIndex(str, word);

            setupToString(innerArray, index);
            System.out.println(this.toString()); // Temp line (?)

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean containsWord(String str, String word) {
        String regex = "\\b" + word + "\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    private int findIndex(String str, String word) {
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        for (int i = 1; stringTokenizer.hasMoreTokens(); i++) {
            if (stringTokenizer.nextToken().equals(word)) {
                return i - 1;
            }
        }
        throw new IllegalStateException("This should not happen");
    }

    private void setupToString(JSONArray innerArray, int arrayIndex) {
        JSONObject obj = (JSONObject) innerArray.get(arrayIndex);
        word_form = (String) obj.get("word_form");
        lemma = (String) obj.get("lemma");
        JSONObject ud_tags = (JSONObject) obj.get("ud_tags");
        JSONObject features = (JSONObject) ud_tags.get("features");

        setupFeatureObject(features);
    }

    private void setupFeatureObject(JSONObject features) {
        try {
            features_keys = features.keySet().toArray();
            features_values = features.values().toArray();
        } catch (NullPointerException e) {
            features_keys = new Object[1];
            features_values = new Object[1];
            features_keys[0] = "features";
            features_values[0] = "null";
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("word_form: ").append(word_form).append("\n").append("lemma: ").append(lemma).append("\n");

        for (int i = features_keys.length - 1; i >= 0; i--) {
            stringBuilder.append(features_keys[i].toString().toLowerCase())
                    .append(": ").append(features_values[i].toString().toLowerCase()).append("\n");
        }

        return stringBuilder.toString();
    }
}
