package krm.compression_of_text.huffman_algorithm;


import org.junit.Test;

public class FactoryHuffmanCodeTest {

    @Test
    public void getCodesTest() throws Exception {
        FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(TreeNodeComparator.getInstance());

        String testDataIn = "eeeeeeekppppppprrruuiiiiiiiiiiiiiiiiiiii";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            factoryHuffmanCode.addSignification(c);
        }

        assertEquals("{e=[true, false], i=[false], k=[true, true, false, true, false], p=[true, true, true], r=[true, true, false, false], u=[true, true, false, true, true]}",
                    factoryHuffmanCode.getCodes().toString());
    }
}