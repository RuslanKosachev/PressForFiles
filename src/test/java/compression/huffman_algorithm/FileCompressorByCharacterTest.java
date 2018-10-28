package compression.huffman_algorithm;

import compression.exception.CompressionException;
import compression.exception.ErrorCodeCompression;

import java.io.File;
import org.junit.Test;
import junitx.framework.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FileCompressorByCharacterTest {

    @Test
    public void performTest() throws Exception {
        File inFile = new File("src\\test\\java\\compression\\huffman_algorithm\\test_files\\" +
                "testCompressorIn.txt");

        File outExpectedFile = new File("src\\test\\java\\compression\\huffman_algorithm\\test_files\\" +
                "testCompressorExpected.huffman.bin");
        File outActualFile = new File("src\\test\\java\\compression\\huffman_algorithm\\test_files\\" +
                "testCompressorActual.huffman.bin");

        FileCompressorByCharacter compressor =
                new FileCompressorByCharacter(inFile,
                        new BuilderHuffmanTree<Character>(NodeComparator.getInstance()));

        outActualFile.delete();
        compressor.setOutFile(outActualFile);
        compressor.perform();

        FileAssert.assertEquals(outExpectedFile, outActualFile);
    }

    @Test
    @SuppressWarnings("unused")
    public void ErrorCodeCompressionTest() {
        try {
            File inFile = new File("doesNotExist.txt");
            FileCompressorByCharacter compressor =
                    new FileCompressorByCharacter(inFile,
                            new BuilderHuffmanTree<Character>(NodeComparator.getInstance()));
            fail();
        } catch (CompressionException ex) {
            assertEquals(ErrorCodeCompression.PATH_ERROR, ex.getErrorCode());
        }
    }
}