package krm.compression_of_text.compressor;

import krm.compression_of_text.huffman_algorithm.BuilderHuffmanTree;
import krm.compression_of_text.huffman_algorithm.FileCompressorByCharacter;
import krm.compression_of_text.huffman_algorithm.TreeNodeComparator;

import java.io.File;
import org.junit.Test;
import junitx.framework.*;
//import org.junit.Assert.*;

public class FileCompressorByCharacterTest {

    @Test
    public void performTest() throws Exception {
        File inFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testCompressorIn.txt");

        File outExpectedFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testCompressorExpected.krm.huffman.bin");
        File outActualFile = new File("src\\test\\java\\krm\\compression_of_text\\compressor\\test_files\\" +
                "testCompressorActual.krm.huffman.bin");

        FileCompressorByCharacter compressor =
                new FileCompressorByCharacter(inFile,
                        new BuilderHuffmanTree<Character>(TreeNodeComparator.getInstance()));

        outActualFile.delete();
        compressor.setOutFile(outActualFile);
        compressor.perform();

        FileAssert.assertEquals(outExpectedFile, outActualFile);
    }
}