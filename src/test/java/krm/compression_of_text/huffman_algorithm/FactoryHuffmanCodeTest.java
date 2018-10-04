package krm.compression_of_text.huffman_algorithm;


import org.junit.Test;
import static org.junit.Assert.*;

public class FactoryHuffmanCodeTest {

    @Test
    public void getCodes() throws Exception {
        FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());

        String testDataIn = "eeeeeeekppppppprrruuiiiiiiiiiiiiiiiiiiii";
        char[] testStream = testDataIn.toCharArray();
        for (char c : testStream) {
            factoryHuffmanCode.addWordGravity(c);
        }

        assertEquals("{e=[false, true, false], i=[false], k=[false, true, false, true, false, false, true, false], p=[false, true, false, true, false, false, true, false, true, true], r=[false, true, false, true, false, false], u=[false, true, false, true, false, false, true, false, true]}",
                    factoryHuffmanCode.getCodes().toString());
    }
}