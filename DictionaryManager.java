package test;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private Map<String, Dictionary> dictionaries  = new HashMap<>();
    private static DictionaryManager instance = null;

    public boolean query(String... args) {
        String word = args[args.length - 1].replaceAll("[\r\n\t]", "");
        boolean result = false;
        for (int i = 0; i < args.length - 1; i++) {
            String book = args[i];
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

    public boolean challenge(String... args) {
        boolean result = false;
        String word = args[args.length - 1].replaceAll("[\r\n\t]", "");
        for (int i = 0; i < args.length - 1; i++) {
            String book = args[i];
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
