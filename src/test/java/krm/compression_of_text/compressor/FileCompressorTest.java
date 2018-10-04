package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import java.io.File;
import org.junit.Test;
//import org.junit.Assert.*;
import junitx.framework.*;

public class FileCompressorTest {

    @Test
    public void start() throws Exception {
        File inFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\" +
                "test\\java\\krm\\compression_of_text\\compressor\\test_files\\testCompressorIn.txt");

        File outExpectedFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\" +
                "test\\java\\krm\\compression_of_text\\compressor\\test_files\\testCompressorExpected.krm.huffman.bin");
        File outActualFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\" +
                "test\\java\\krm\\compression_of_text\\compressor\\test_files\\testCompressorActual.krm.huffman.bin");

        FileCompressor compressor = new FileCompressor(inFile,
                                                       new FactoryHuffmanCode(CodeGravityComparator.getInstance()));
        compressor.setOutFile(outActualFile);
        compressor.start();

        FileAssert.assertEquals(outExpectedFile, outActualFile);
    }
}