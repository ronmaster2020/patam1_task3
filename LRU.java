package test;

import java.util.LinkedList;
import java.util.Queue;

public class LRU implements CacheReplacementPolicy {
    private Queue<String> queue = new LinkedList<>();

    @Override
    public void add(String word) {
        if (queue.contains(word)) {
            queue.removeIf(w -> w.equals(word));
        }
        queue.add(word);
    }

    @Override
    public String remove() {
        String word = queue.remove();
        return word;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LRU lru = (LRU) obj;
        return queue.equals(lru.queue);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}