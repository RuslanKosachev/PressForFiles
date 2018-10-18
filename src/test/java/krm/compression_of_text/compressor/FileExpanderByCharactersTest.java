package krm.compression_of_text.compressor;

import junitx.framework.FileAssert;
import krm.compression_of_text.huffman_algorithm.FileExpanderByCharacters;

import org.junit.Test;
import java.io.File;
//import org.junit.Assert.*;

public class FileExpanderByCharactersTest {

    @Test
    public void performTest() throws Exception {
        File inFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderIn.krm.huffman.bin");

        File outExpectedFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderExpected.txt");
        File outActualFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderActual.txt");

        FileExpanderByCharacters expander = new FileExpanderByCharacters(inFile);
        outActualFile.delete();
        expander.setOutFile(outActualFile);
        expander.perform();

        FileAssert.assertEquals(outExpectedFile, outActualFile);
    }
}