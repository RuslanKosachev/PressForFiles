package compression.huffman_algorithm;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;

public class BuilderHuffmanTreeTest {

    private StringBuilder codeStrTest = new StringBuilder();
    private StringBuilder actualCodesStr = new StringBuilder();

    @Test
    public void addSignificationTest() {
        BuilderHuffmanTree builder = new BuilderHuffmanTree(TreeNodeComparator.getInstance());

        String testDataIn = "eeerereuuuuiii";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            builder.addSignification(c);
        }
        //{r=2, e=5, u=2, i=2}
        Map<Character, Integer> map = builder.getSignificationFrequency();
        assertEquals(2, (int)map.get('r'));
        assertEquals(5, (int)map.get('e'));
        assertEquals(4, (int)map.get('u'));
        assertEquals(3, (int)map.get('i'));
    }

    @Test
    public void getRootNodeTest() {
        String expectedCodesStr = "u000i0010r0011e01o1";
        BuilderHuffmanTree builder = new BuilderHuffmanTree(TreeNodeComparator.getInstance());

        String testDataIn = "ieeeeerruuoooooooooooooooo";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            builder.addSignification(c);
        }

        initCodesStrTest(builder.getRootNode());
        assertEquals(expectedCodesStr, String.valueOf(actualCodesStr));
    }

    @Test
    public void getCodesTest() {
        Map<Character, List<Boolean>> expectedCodes = new TreeMap<>();
        expectedCodes.put('i', new LinkedList<Boolean>(Arrays.asList(false, false, true, false)));
        expectedCodes.put('e', new LinkedList<Boolean>(Arrays.asList(false, true)));
        expectedCodes.put('o', new LinkedList<Boolean>(Arrays.asList(true)));
        expectedCodes.put('r', new LinkedList<Boolean>(Arrays.asList(false, false, true, true)));
        expectedCodes.put('u', new LinkedList<Boolean>(Arrays.asList(false, false, false)));

        BuilderHuffmanTree builder = new BuilderHuffmanTree(TreeNodeComparator.getInstance());

        String testDataIn = "ieeeeerruuoooooooooooooooo";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            builder.addSignification(c);
        }

        assertEquals(expectedCodes, builder.getCodes());
    }

    private void initCodesStrTest(HuffmanTree<Character> rootNode) {
        if (Objects.nonNull(rootNode.getLeftNode())) {
            codeStrTest.append("0");
            initCodesStrTest(rootNode.getLeftNode());
        }
        if (Objects.nonNull(rootNode.getRightNode())) {
            codeStrTest.append("1");
            initCodesStrTest(rootNode.getRightNode());
        }
        if (Objects.isNull(rootNode.getLeftNode()) && Objects.isNull(rootNode.getRightNode())) {
            actualCodesStr.append(rootNode.getSignification().toString() + new StringBuilder(codeStrTest));
        }
        if (codeStrTest.length() > 0) {
            codeStrTest = codeStrTest.deleteCharAt(codeStrTest.length() - 1);
        }
    }
}
