package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFactoryHuffmanTree {

    @Test
    public void testAddWordGravity() {
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
        f.addWordGravity('u');
        f.addWordGravity('u');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        //{r=2, e=5, u=2, i=2}
        assertEquals(2, (int)f.gravityLeafs.get('r'));
        assertEquals(5, (int)f.gravityLeafs.get('e'));
        assertEquals(4, (int)f.gravityLeafs.get('u'));
        assertEquals(3, (int)f.gravityLeafs.get('i'));
    }

    @Test
    public void testGetRootNode() {
        FactoryHuffmanTree f = new FactoryHuffmanTree(CodeGravityComparator.getInstance());
        f.addWordGravity('i');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('r');
        f.addWordGravity('r');
        f.addWordGravity('u');
        f.addWordGravity('u');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        f.addWordGravity('o');
        assertEquals(26, f.getRootNode().getGravity());

        f.toStringNode(f.getRootNode(), 0);
    }

}
