package test;
import java.security.MessageDigest;
import java.util.BitSet;
import java.math.BigInteger;

public class BloomFilter {
    private BitSet set;
    private MessageDigest[] hashAlgorithms;


    public BloomFilter(int size, String... hashAlgorithms) {
        set = new BitSet(size);
        this.hashAlgorithms = new MessageDigest[hashAlgorithms.length];
        for (int i = 0; i < hashAlgorithms.length; i++) {
            try {
                this.hashAlgorithms[i] = MessageDigest.getInstance(hashAlgorithms[i]);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    public void add(String word) {
        for (MessageDigest hashAlg : hashAlgorithms) {
            byte[] digest = hashAlg.digest(word.getBytes());
            BigInteger bigInt = new BigInteger(1, digest);
            int index = bigInt.intValue() % set.size();
            index = index < 0 ? -index : index;
            set.set(index);
        }
    }
    public boolean contains(String word) {
        for (MessageDigest hashAlg : hashAlgorithms) {
            hashAlg.update(word.getBytes());
            byte[] digest = hashAlg.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            int index = bigInt.intValue() % set.size();
            index = index < 0 ? -index : index;
            if (!set.get(index)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < set.size(); i++) {
            sb.append(set.get(i) ? "1" : "0");
        }
        //remove trailing zeros from the string
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '0') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
