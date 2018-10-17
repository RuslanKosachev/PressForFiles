package krm.compression_of_text.huffman_algorithm;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FactoryHuffmanTreeTest {

    @Test
    public void addWordGravityTest() {
        BuilderHuffmanTree f = new BuilderHuffmanTree(TreeNodeComparator.getInstance());

        String testDataIn = "eeerereuuuuiii";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            f.addSignification(c);
        }
        //{r=2, e=5, u=2, i=2}
        assertEquals(2, (int)f.significationFrequency.get('r'));
        assertEquals(5, (int)f.significationFrequency.get('e'));
        assertEquals(4, (int)f.significationFrequency.get('u'));
        assertEquals(3, (int)f.significationFrequency.get('i'));
    }

    @Test
    public void getRootNodeTest() {
        BuilderHuffmanTree f = new BuilderHuffmanTree(TreeNodeComparator.getInstance());

        String testDataIn = "ieeeeerruuoooooooooooooooo";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            f.addSignification(c);
        }

        assertEquals(26, f.getRootNode().getFrequencies());
        System.out.println(f.toStringSignificationFrequency());
    }
}
