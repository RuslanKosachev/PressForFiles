package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestTreeLeaf {

    @Test
    public void testGetGravity() {
        TreeLeaf l1 = new TreeLeaf('a', 23);
        assertEquals(23, l1.getGravity());
    }

    @Test
    public void testGetSignification() {
        TreeLeaf l1 = new TreeLeaf('t', 1);
        assertEquals((Character) 't', l1.getSignification());
    }

    @Test
    public void testEquals() {
        FactoryHuffmanTree f = new FactoryHuffmanTree(CodeGravityComparator.getInstance());
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('r');
        f.addWordGravity('e');
        f.addWordGravity('r');
        f.addWordGravity('e');
        f.addWordGravity('u');
        f.addWordGravity('u');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.initCollectionOfLeaf();
        TreeLeaf leaf = new TreeLeaf('u', 4652);
        assertEquals(true, f.nodeList.contains(leaf));
    }
}
