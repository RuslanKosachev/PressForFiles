package krm.compression_of_text.huffman_algorithm;


import org.junit.Test;
import static org.junit.Assert.*;

public class FactoryHuffmanCodeTest {

    @Test
    public void getCodesTest() throws Exception {
        FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());

        String testDataIn = "eeeeeeekppppppprrruuiiiiiiiiiiiiiiiiiiii";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            factoryHuffmanCode.addWordGravity(c);
        }

        assertEquals("{e=[true, false], i=[false], k=[true, true, false, true, false], p=[true, true, true], r=[true, true, false, false], u=[true, true, false, true, true]}",
                    factoryHuffmanCode.getCodes().toString());
    }
}