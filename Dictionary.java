package test;

import java.io.FileReader;

public class Dictionary {
    private CacheManager existCache = new CacheManager(400, new LRU());
    private CacheManager notExistCache = new CacheManager(100, new LFU());
    private BloomFilter bloomFilter = new BloomFilter(256, "MD5", "SHA1");
    private String[] fileNames;

    public Dictionary(String... fileNames) {
        this.fileNames = fileNames;
        // add the words to the bloom filter
        for (String fileName : fileNames) {
            try {
                // open the file for reading
                FileReader fileReader = new FileReader(fileName);
                int data = fileReader.read();
                StringBuilder word = new StringBuilder();
                // iterate over the file
                while (data != -1) {
                    char ch = (char) data;
                    if (ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t') {
                        if (word.length() > 0) {
                            bloomFilter.add(word.toString());
                            word = new StringBuilder();
                        }
                    } else {
                        word.append(ch);
                    }
                    // read the next character
                    data = fileReader.read();
                }
                // close the file
                fileReader.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public boolean query(String word) {
        if (existCache.query(word)) {
            return true;
        }
        if (notExistCache.query(word)) {
            return false;
        }
        if (!bloomFilter.contains(word)) {
            this.notExistCache.add(word);
            return false;
        }
        return true;
    }

    public boolean challenge(String word) {
        if (IOSearcher.search(word, this.fileNames)) {
            this.existCache.add(word);
            return true;
        } else {
            this.notExistCache.add(word);
            return false;
        }
    }
}
