package compression.huffman_algorithm;

import java.util.Comparator;

public class NodeComparator implements Comparator<HuffmanTree> {

    private static volatile NodeComparator instance;

    private NodeComparator() {}

    public static NodeComparator getInstance() {
        if (instance == null) {
            synchronized (NodeComparator.class) {
                if (instance == null) {
                    instance = new NodeComparator();
                }
            }
        }
        return instance;
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
