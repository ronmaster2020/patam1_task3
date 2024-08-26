package test;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private Map<String, Dictionary> dictionaries  = new HashMap<>();
    private static DictionaryManager instance = null;

    public boolean query(String word, String... args) {
        boolean result = false;
        for (String book : args) {
            if (dictionaries.containsKey(book)) {
                if (dictionaries.get(book).query(word)) {
                    result = true;
                }
            } else {
                Dictionary dictionary = new Dictionary(book);
                dictionaries.put(book, dictionary);
                if (dictionary.query(word)) {
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean challenge(String word, String... args) {
        boolean result = false;
        for (String book : args) {
            if (dictionaries.containsKey(book)) {
                if (dictionaries.get(book).challenge(word)) {
                    result = true;
                }
            } else {
                Dictionary dictionary = new Dictionary(book);
                dictionaries.put(book, dictionary);
                if (dictionary.challenge(word)) {
                    result = true;
                }
            }
        }
        return result;
    }

    public int getSize() {
        return dictionaries.size();
    }

    public static DictionaryManager get() {
        if (instance == null) {
            instance = new DictionaryManager();
        }
        return instance;
    }
}
