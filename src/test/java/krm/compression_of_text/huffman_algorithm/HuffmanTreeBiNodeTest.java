package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HuffmanTreeBiNodeTest {

    @Test
    public void testGetGravity() {
        HuffmanTreeLeaf l1 = new HuffmanTreeLeaf('a', 1);
        HuffmanTreeLeaf l2 = new HuffmanTreeLeaf('a', 3);
        HuffmanTreeLeaf l3 = new HuffmanTreeLeaf('a', 5);

        HuffmanTreeBiNode n1 = new HuffmanTreeBiNode(l1, l2, CodeGravityComparator.getInstance());
        assertEquals(4, n1.getGravity());

        HuffmanTreeBiNode n2 = new HuffmanTreeBiNode(n1, l3, CodeGravityComparator.getInstance());
        assertEquals(9, n2.getGravity());
    }

    @Test
    public void testGetLeftSink() {
        HuffmanTreeBiNode treeNode = new HuffmanTreeBiNode(new HuffmanTreeLeaf('t', 6445),
                                            new HuffmanTreeLeaf('p', 879),
                                            CodeGravityComparator.getInstance());
        assertEquals(879, treeNode.getLeftSink().getGravity());
    }

    @Test
    public void testGetRightSink() {
        HuffmanTreeBiNode treeNode = new HuffmanTreeBiNode(new HuffmanTreeLeaf('t', 879),
                                            new HuffmanTreeLeaf('p', 6445),
                                            CodeGravityComparator.getInstance());
        assertEquals(6445, treeNode.getRightSink().getGravity());
    }

}
