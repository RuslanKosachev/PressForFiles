package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestNode {

    @Test
    public void testGetGravity() {
        TreeLeaf l1 = new TreeLeaf('a', 1);
        TreeLeaf l2 = new TreeLeaf('a', 3);
        TreeLeaf l3 = new TreeLeaf('a', 5);

        TreeBiNode n1 = new TreeBiNode(l1, l2, CodeGravityComparator.getInstance());
        assertEquals(4, n1.getGravity());

        TreeBiNode n2 = new TreeBiNode(n1, l3, CodeGravityComparator.getInstance());
        assertEquals(9, n2.getGravity());
    }

    @Test
    public void testGetLeftSink() {
        TreeBiNode treeNode = new TreeBiNode(new TreeLeaf('t', 6445),
                                            new TreeLeaf('p', 879),
                                            CodeGravityComparator.getInstance());
        assertEquals(879, treeNode.getLeftSink().getGravity());
    }

    @Test
    public void testGetRightSink() {
        TreeBiNode treeNode = new TreeBiNode(new TreeLeaf('t', 879),
                                            new TreeLeaf('p', 6445),
                                            CodeGravityComparator.getInstance());
        assertEquals(6445, treeNode.getRightSink().getGravity());
    }

}
