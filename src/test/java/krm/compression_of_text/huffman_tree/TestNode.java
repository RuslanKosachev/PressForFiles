package krm.compression_of_text.huffman_tree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestNode {

    @Test
    public void testSetGravity() {
        Leaf l1 = new Leaf();
        l1.setGravity(1);
        assertEquals(1, l1.getGravity());

        Leaf l2 = new Leaf();
        l2.setGravity(3);
        assertEquals(3, l2.getGravity());

        Leaf l3 = new Leaf();
        l3.setGravity(5);
        assertEquals(5, l3.getGravity());

        Node n1 = new Node(l1, l2);
        assertEquals(4, n1.getGravity());

        Node n2 = new Node(n1, l3);
        assertEquals(9, n2.getGravity());

        l3.setGravity(6);
        assertEquals(6, l3.getGravity());

        assertEquals(10, n2.getGravity());
    }

    @Test
    public void testAddGravity() {
        Leaf l1 = new Leaf();
        Leaf l2 = new Leaf();
        Leaf l3 = new Leaf();
        l1.setGravity(1);
        l2.setGravity(3);
        l3.setGravity(5);

        Node n1 = new Node(l1, l2);
        Node n2 = new Node(n1, l3);

        l3.addGravity(6);
        assertEquals(11, l3.getGravity());

        assertEquals(15, n2.getGravity());
    }
}
