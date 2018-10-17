package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.TreeNodeComparator;

import java.io.File;
import org.junit.Test;
import junitx.framework.*;
//import org.junit.Assert.*;

public class FileCompressorTest {

    @Test
    public void startTest() throws Exception {
        File inFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testCompressorIn.txt");

        File outExpectedFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testCompressorExpected.krm.huffman.bin");
        File outActualFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testCompressorActual.krm.huffman.bin");

        FileCompressor compressor = new FileCompressor(inFile,
            new FactoryHuffmanCode(TreeNodeComparator.getInstance()));

        outActualFile.delete();
        compressor.setOutFile(outActualFile);
        compressor.start();

        FileAssert.assertEquals(outExpectedFile, outActualFile);
    }
}