package krm.compression_of_text.huffman_algorithm;

import org.junit.Test;

import static org.junit.Assert.*;


public class FactoryHuffmanCodeTest {

    @Test
    public void getCodes() throws Exception {
        FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());
        factoryHuffmanCode.addWordGravity('e');
        factoryHuffmanCode.addWordGravity('e');
        factoryHuffmanCode.addWordGravity('e');
        factoryHuffmanCode.addWordGravity('e');
        factoryHuffmanCode.addWordGravity('e');
        factoryHuffmanCode.addWordGravity('e');
        factoryHuffmanCode.addWordGravity('e');
        factoryHuffmanCode.addWordGravity('k');
        factoryHuffmanCode.addWordGravity('p');
        factoryHuffmanCode.addWordGravity('p');
        factoryHuffmanCode.addWordGravity('p');
        factoryHuffmanCode.addWordGravity('p');
        factoryHuffmanCode.addWordGravity('p');
        factoryHuffmanCode.addWordGravity('p');
        factoryHuffmanCode.addWordGravity('p');
        factoryHuffmanCode.addWordGravity('r');
        factoryHuffmanCode.addWordGravity('r');
        factoryHuffmanCode.addWordGravity('r');
        factoryHuffmanCode.addWordGravity('u');
        factoryHuffmanCode.addWordGravity('u');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');
        factoryHuffmanCode.addWordGravity('i');

        //f.toStringNode(f.getRoot(), 0);

        assertEquals("{e=[false, true, false], i=[false], k=[false, true, false, true, false, false, true, false], p=[false, true, false, true, false, false, true, false, true, true], r=[false, true, false, true, false, false], u=[false, true, false, true, false, false, true, false, true]}",
                    factoryHuffmanCode.getCodes().toString());



    }

}