package krm.compression_of_text.compressor;


import junitx.framework.FileAssert;
import org.junit.Test;

import java.io.File;
//import org.junit.Assert.*;

public class FileExpanderTest {

    @Test
    public void startTest() throws Exception {
        File inFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderIn.krm.huffman.bin");

        File outExpectedFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderExpected.txt");
        File outActualFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderActual.txt");

        FileExpander expander = new FileExpander(inFile);
        outActualFile.delete();
        expander.setOutFile(outActualFile);
        expander.start();

        FileAssert.assertEquals(outExpectedFile, outActualFile);
    }
}