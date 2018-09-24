package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCodeGravityComparator {

    @Test
    public void testCompare() {
        List<ITreeGravity> listArr = new ArrayList();
        listArr.add(new TreeLeaf('r', 10));
        listArr.add(new TreeLeaf('r', 5));
        listArr.add(new TreeLeaf('r', 7));
        listArr.add(new TreeLeaf('r', 84));
        listArr.add(new TreeLeaf('r', 1));
        Collections.sort(listArr, new CodeGravityComparator());
        assertEquals("[Leaf->1, Leaf->5, Leaf->7, Leaf->10, Leaf->84]", listArr.toString());
    }

}
