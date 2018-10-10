package krm.compression_of_text.huffman_algorithm;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FactoryHuffmanTreeTest {

    @Test
    public void addWordGravityTest() {
        FactoryHuffmanTree f = new FactoryHuffmanTree(CodeGravityComparator.getInstance());

        String testDataIn = "eeerereuuuuiii";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            f.addWordGravity(c);
        }
        //{r=2, e=5, u=2, i=2}
        assertEquals(2, (int)f.gravityLeafs.get('r'));
        assertEquals(5, (int)f.gravityLeafs.get('e'));
        assertEquals(4, (int)f.gravityLeafs.get('u'));
        assertEquals(3, (int)f.gravityLeafs.get('i'));
    }

    @Test
    public void getRootNodeTest() {
        FactoryHuffmanTree f = new FactoryHuffmanTree(CodeGravityComparator.getInstance());

        String testDataIn = "ieeeeerruuoooooooooooooooo";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            f.addWordGravity(c);
        }

        assertEquals(26, f.getRootNode().getGravity());
        System.out.println(f.toStringGravityLeafs());
    }
}
