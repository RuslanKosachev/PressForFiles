package krm.compression_of_text.compressor;

import junitx.framework.FileAssert;
import krm.compression_of_text.huffman_algorithm.BuilderHuffmanTree;
import krm.compression_of_text.huffman_algorithm.FileExpanderByCharacters;

import krm.compression_of_text.huffman_algorithm.TreeNodeComparator;
import krm.exception.CompressionException;
import krm.exception.ErrorCodeCompression;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileExpanderByCharactersTest {

   @Test
    public void performTest() throws Exception {
        File inFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderIn.krm.huffman.bin");

        File outExpectedFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderExpected.txt");
        File outActualFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testExpanderActual.txt");

        FileExpanderByCharacters expander = new FileExpanderByCharacters(inFile,
                new BuilderHuffmanTree<Character>(TreeNodeComparator.getInstance()));
        outActualFile.delete();
        expander.setOutFile(outActualFile);
        expander.perform();

        FileAssert.assertEquals(outExpectedFile, outActualFile);
    }

    @Test
    @SuppressWarnings("unused")
    public void ErrorCodeCompressionTest() {
        try {
            File inFile = new File("tesT.txt");
            FileExpanderByCharacters expander = new FileExpanderByCharacters(inFile,
                    new BuilderHuffmanTree<Character>(TreeNodeComparator.getInstance()));
            fail();
        } catch (CompressionException ex) {
            assertEquals(ErrorCodeCompression.PATH_ERROR, ex.getErrorCode());
        }
    }
}