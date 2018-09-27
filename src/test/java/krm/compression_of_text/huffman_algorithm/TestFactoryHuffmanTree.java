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
        f.addWordGravity('i');
        f.addWordGravity('i');
        //{r=2, e=5, u=2, i=2}
        assertEquals(2, (int)f.gravityLeafs.get('r'));
        assertEquals(5, (int)f.gravityLeafs.get('e'));
        assertEquals(2, (int)f.gravityLeafs.get('u'));
        assertEquals(2, (int)f.gravityLeafs.get('i'));
    }

    @Test
    public void testGetRoot() {
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
        assertEquals("Node->11", f.getRoot().toString());

        f.toStringNode(f.getRoot(), 0);
    }

}
