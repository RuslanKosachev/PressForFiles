package compression.huffman_algorithm;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HuffmanTreeTest {

    @Test
    public void HuffmanTreeTest1() {
        HuffmanTree<Character> l1 = new HuffmanTree<>(1, 'a');
        HuffmanTree<Character> l2 = new HuffmanTree<>(3, 'a');
        HuffmanTree<Character> l3 = new HuffmanTree<>(5, 'a');

        assertEquals(1, l1.getFrequencies());
        assertEquals(3, l2.getFrequencies());
        assertEquals(5, l3.getFrequencies());
    }

    @Test
    public void HuffmanTreeTest2() {
        HuffmanTree<Character> l1 = new HuffmanTree<>(1, 'a');
        HuffmanTree<Character> l2 = new HuffmanTree<>(3, 't');
        HuffmanTree<Character> l3 = new HuffmanTree<>(5, 'u');
        HuffmanTree<Character> l4 = new HuffmanTree<>(8, 'l');

        HuffmanTree<Character> n1 = new HuffmanTree<>(l1, l2, TreeNodeComparator.getInstance());//1 + 3
        HuffmanTree<Character> n2 = new HuffmanTree<>(l3, n1, TreeNodeComparator.getInstance());//4 + 5
        HuffmanTree<Character> n3 = new HuffmanTree<>(n2, l4, TreeNodeComparator.getInstance());//8 + 9

        assertEquals(17, n3.getFrequencies());
        assertEquals(8, n3.getLeftNode().getFrequencies());
        assertEquals('l', n3.getLeftNode().getSignification());
        assertEquals(9, n3.getRightNode().getFrequencies());

        assertEquals(4, n3.getRightNode().getLeftNode().getFrequencies());
        assertEquals(5, n3.getRightNode().getRightNode().getFrequencies());
        assertEquals('u', n3.getRightNode().getRightNode().getSignification());

        assertEquals(1, n3.getRightNode().getLeftNode().getLeftNode().getFrequencies());
        assertEquals('a', n3.getRightNode().getLeftNode().getLeftNode().getSignification());
        assertEquals(3, n3.getRightNode().getLeftNode().getRightNode().getFrequencies());
        assertEquals('t', n3.getRightNode().getLeftNode().getRightNode().getSignification());
    }
}