package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HuffmanTreeLeafTest {

    @Test
    public void getGravityTest() {
        CharacterHuffmanTree l1 = new CharacterHuffmanTree('a', 23);
        assertEquals(23, l1.getFrequencies());
    }

    @Test
    public void getSignificationTest() {
        CharacterHuffmanTree l1 = new CharacterHuffmanTree('t', 1);
        assertEquals((Character) 't', l1.getSignification());
    }

    @Test
    public void equalsTest() {
        BuilderHuffmanTree f = new BuilderHuffmanTree(TreeNodeComparator.getInstance());

        String testDataIn = "eeerereuuii";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            f.addSignification(c);
        }
        f.initCollectionOfLeaf();
        CharacterHuffmanTree leaf = new CharacterHuffmanTree('u', 4652);
        assertEquals(true, f.nodes.contains(leaf));
    }
}