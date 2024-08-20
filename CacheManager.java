package test;
import java.util.HashSet;


public class CacheManager {
    private CacheReplacementPolicy crp;
    private int size;
    private HashSet<String> cache = new HashSet<>();

    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.size = size;
        this.crp = crp;
    }

    public boolean query(String word) {
        return cache.contains(word);
    }

    public void add(String word) {
        crp.add(word);
        cache.add(word);
        if (cache.size() > size) {
            cache.remove(crp.remove());
        }
    }
}
