package krm.compression_of_text.huffman_algorithm;


import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TreeNodeComparatorTest {

    @Test
    public void compareTest() {
        List<HuffmanTree<Character>> actualList = new ArrayList<>();
        actualList.add(new HuffmanTree<Character>(10, 'r'));
        actualList.add(new HuffmanTree<Character>(5, 'r'));
        actualList.add(new HuffmanTree<Character>(7, 'r'));
        actualList.add(new HuffmanTree<Character>(84, 'r'));
        actualList.add(new HuffmanTree<Character>(1, 'r'));

        List<HuffmanTree<Character>> expectedList = new ArrayList<>();
        expectedList.add(new HuffmanTree<Character>(1, 'r'));
        expectedList.add(new HuffmanTree<Character>(5, 'r'));
        expectedList.add(new HuffmanTree<Character>(7, 'r'));
        expectedList.add(new HuffmanTree<Character>(10, 'r'));
        expectedList.add(new HuffmanTree<Character>(84, 'r'));

        Collections.sort(actualList, TreeNodeComparator.getInstance());

        assertEquals(expectedList, actualList);

        /*System.out.println("-- compareTest()  --");
        System.out.println(actualList);
        System.out.println(expectedList);
        System.out.println(actualList.equals(expectedList));*/
    }
}
