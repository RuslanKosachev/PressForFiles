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
    public void testGetUnit() {
        TreeLeaf l1 = new TreeLeaf('t', 1);
        assertEquals('t', l1.getUnit());
    }

}
