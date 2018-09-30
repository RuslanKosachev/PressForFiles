package krm.compression_of_text.compressor;


import com.sun.deploy.util.ArrayUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javafx.beans.binding.StringBinding;
import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.channels.GatheringByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Compressor {

    protected Map<Character, List<Boolean>> codes = null;
    public Reader in = null;
    public OutputStream output = null;

    public Compressor(Reader in, OutputStream out, Map<Character, List<Boolean>> codes) throws IOException {
        this.in = in;
        this.output = out;
        this.codes = codes;
    }

    public void setIn(Reader in) {
        this.in = in;
    }

    public void setOut(OutputStream out) {
        this.output = out;
    }

    public void setCodes(Map<Character, List<Boolean>> codes) {
        this.codes = codes;
    }


    public void compressor() throws IOException {
        String test = "";
        List<Boolean> code = null;
        int k = 0;
        byte buffer = 0;
        byte bit = 0;
        int count = 0;
        while ((k = this.in.read()) != -1) {
            code = codes.get((char)k);
            for (boolean item : code) {
                bit = (byte) ((item) ? 1 : 0);
                buffer = (byte) (buffer | (bit << (7 - count)));
                count++;
                if (count == 8) {
                    count = 0;
                    this.output.write(buffer);
                    buffer = 0;
                }
            }
        }
        if (buffer > 0) { this.output.write(buffer); }
        this.output.flush();
    }

    public static void main(String args[]) throws IOException {
        //File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\test\\java\\krm\\compression_of_text\\compressor\\sourceFile.txt");
        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\sourceFile.txt");
        File codeFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\compressed.bin");


        try (BufferedOutputStream buffOut = new BufferedOutputStream(new FileOutputStream(codeFile));
            /* test Reader - тестовый входной поток */
             Reader inBuffR = new BufferedReader(new FileReader(sourceFile));
             Reader inBuffR2 = new BufferedReader(new FileReader(sourceFile));)
        {
            // test Huffman tree
            FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());
            int symbol;
            while ((symbol = inBuffR.read()) != -1) {
                factoryHuffmanCode.addWordGravity((char)symbol);
            }
            // test compressor
            Compressor compressor = new Compressor(inBuffR2, buffOut, factoryHuffmanCode.getCodes());
            compressor.compressor();

            factoryHuffmanCode.printRoot();
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}
