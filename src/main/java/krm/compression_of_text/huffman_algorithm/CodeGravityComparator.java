package krm.compression_of_text.huffman_algorithm;

import java.util.Comparator;

public class CodeGravityComparator implements Comparator<ITreeGravity> {
    public int compare(ITreeGravity o1, ITreeGravity o2) {
        if (o1 == null && o2 != null) {
            return -1;
        } else if (o2 == null && o1 != null) {
            return 1;
        } else if (o1.getGravity() < o2.getGravity()) {
            return -1;
        } else if (o1.getGravity() > o2.getGravity()) {
            return 1;
        }
        return 0;
    }


}
