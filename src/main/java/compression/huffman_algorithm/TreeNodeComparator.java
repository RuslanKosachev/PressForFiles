package compression.huffman_algorithm;

import java.util.Comparator;

public class TreeNodeComparator implements Comparator<HuffmanTree> {

    public static TreeNodeComparator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final TreeNodeComparator INSTANCE = new TreeNodeComparator();
    }

    public int compare(HuffmanTree o1, HuffmanTree o2) {
        if (o1 == null && o2 != null) {
            return -1;
        } else if (o2 == null && o1 != null) {
            return 1;
        } else if (o1.getFrequencies() < o2.getFrequencies()) {
            return -1;
        } else if (o1.getFrequencies() > o2.getFrequencies()) {
            return 1;
        }
        return 0;
    }
}
