package krm.compression_of_text.huffman_algorithm;


import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class CodeGravityComparatorTest {

    @Test
    public void compareTest() {
        List<ITreeGravity> listArr = new ArrayList<>();
        listArr.add(new CharacterHuffmanTree('r', 10));
        listArr.add(new CharacterHuffmanTree('r', 5));
        listArr.add(new CharacterHuffmanTree('r', 7));
        listArr.add(new CharacterHuffmanTree('r', 84));
        listArr.add(new CharacterHuffmanTree('r', 1));
        Collections.sort(listArr, TreeNodeComparator.getInstance());
        assertEquals("[Leaf->1=r, Leaf->5=r, Leaf->7=r, Leaf->10=r, Leaf->84=r]", listArr.toString());
    }
}
