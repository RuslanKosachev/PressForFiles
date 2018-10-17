package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HuffmanTreeBiNodeTest {

    @Test
    public void getGravityTest() {
        CharacterHuffmanTree l1 = new CharacterHuffmanTree('a', 1);
        CharacterHuffmanTree l2 = new CharacterHuffmanTree('a', 3);
        CharacterHuffmanTree l3 = new CharacterHuffmanTree('a', 5);

        HuffmanTreeBiNode n1 = new HuffmanTreeBiNode(l1, l2, TreeNodeComparator.getInstance());
        assertEquals(4, n1.getGravity());

        HuffmanTreeBiNode n2 = new HuffmanTreeBiNode(n1, l3, TreeNodeComparator.getInstance());
        assertEquals(9, n2.getGravity());
    }

    @Test
    public void getLeftSinkTest() {
        HuffmanTreeBiNode treeNode = new HuffmanTreeBiNode(new CharacterHuffmanTree('t', 6445),
                                            new CharacterHuffmanTree('p', 879),
                                            TreeNodeComparator.getInstance());
        assertEquals(879, treeNode.getLeftSink().getGravity());
    }

    @Test
    public void getRightSinkTest() {
        HuffmanTreeBiNode treeNode = new HuffmanTreeBiNode(new CharacterHuffmanTree('t', 879),
                                            new CharacterHuffmanTree('p', 6445),
                                            TreeNodeComparator.getInstance());
        assertEquals(6445, treeNode.getRightSink().getGravity());
    }

}
