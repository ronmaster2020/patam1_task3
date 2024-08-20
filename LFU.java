package test;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LFU implements CacheReplacementPolicy {
    private Map<String, Integer> frequencyMap = new HashMap<>();
    private PriorityQueue<String> minHeap = new PriorityQueue<>((a, b) -> frequencyMap.get(a) - frequencyMap.get(b));

    @Override
    public void add(String word) {
        frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        minHeap.remove(word);
        minHeap.add(word);
    }

    @Override
    public String remove() {
        String leastFrequent = minHeap.poll();
        if (leastFrequent != null) {
            frequencyMap.remove(leastFrequent);
        }
        return leastFrequent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LFU lfu = (LFU) obj;
        return frequencyMap.equals(lfu.frequencyMap) && minHeap.equals(lfu.minHeap);
    }

    @Override
    public int hashCode() {
        return frequencyMap.hashCode() * minHeap.hashCode();
    }
}
