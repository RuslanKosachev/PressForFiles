package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HuffmanTreeLeafTest {

    @Test
    public void testGetGravity() {
        HuffmanTreeLeaf l1 = new HuffmanTreeLeaf('a', 23);
        assertEquals(23, l1.getGravity());
    }

    @Test
    public void testGetSignification() {
        HuffmanTreeLeaf l1 = new HuffmanTreeLeaf('t', 1);
        assertEquals((Character) 't', l1.getSignification());
    }

    @Test
    public void testEquals() {
        FactoryHuffmanTree f = new FactoryHuffmanTree(CodeGravityComparator.getInstance());

        String testDataIn = "eeerereuuii";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            f.addWordGravity(c);
        }
        f.initCollectionOfLeaf();
        HuffmanTreeLeaf leaf = new HuffmanTreeLeaf('u', 4652);
        assertEquals(true, f.nodes.contains(leaf));
    }
}