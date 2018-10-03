package krm.compression_of_text.compressor;


import com.sun.deploy.util.ArrayUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javafx.beans.binding.StringBinding;
import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;
import krm.compression_of_text.huffman_algorithm.IHuffmanTree;

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
        List<Boolean> code;
        int key = 0;
        byte buffer = 0;
        byte bit = 0;
        int count = 0;
        while ((key = this.in.read()) != -1) {
            code = codes.get((char) key);
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
        // если есть биты кодированного текста в последнем байте то дозапишем байт
        if (count > 0) { this.output.write(buffer); }

        this.output.flush();
    }

    public void setStopChar() {

    }

    public static void main(String args[]) throws IOException {
        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\sourceFile.txt");
        File compressedFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\compressed.bin");

        try (//BufferedOutputStream buffOut = new BufferedOutputStream(new FileOutputStream(compressedFile));
             ObjectOutputStream outputObject = new ObjectOutputStream(new FileOutputStream(compressedFile));
             Reader inBuffR = new BufferedReader(new FileReader(sourceFile));
             Reader inBuffR2 = new BufferedReader(new FileReader(sourceFile));
             //ObjectInputStream inputObject = new ObjectInputStream(new FileInputStream(compressedFile))
        ) {
            // test Huffman tree
            FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());
            int symbol;
            while ((symbol = inBuffR.read()) != -1) {
                factoryHuffmanCode.addWordGravity((char)symbol);
            }
            // test serializable output
            IHuffmanTree rootNode = null;
            rootNode = factoryHuffmanCode.getRootNode();
            //outputObject.writeObject(rootNode);

            // test compressor
            //Compressor compressor = new Compressor(inBuffR2, buffOut, factoryHuffmanCode.getCodes());
            //compressor.compressor();

            // test serializable inFile
            //rootNode = null;
            //rootNode = (IHuffmanTree) inputObject.readObject();
            //factoryHuffmanCode.toPrintRoot(rootNode, 0);

            // test serializable outFile affordable RandomAccessFile
            // сериализуем в массив байт
            ByteArrayOutputStream byteArrayOutput2 = new ByteArrayOutputStream();
            ObjectOutputStream outputObject2 = new ObjectOutputStream(byteArrayOutput2);
            outputObject2.writeObject(rootNode);
            byte[] outByteObject = byteArrayOutput2.toByteArray();
            System.out.println("outByteObject " + outByteObject.length); // размер объекта в байтах

            FileOutputStream f =new FileOutputStream(compressedFile);
            compressedFile.canRead();

            // test serializable input affordable RandomAccessFile
            // запишем размер обьекта и сам объект
            RandomAccessFile rand = new RandomAccessFile(compressedFile, "rw");
            rand.writeInt(outByteObject.length); // размер объекта в байтах
            rand.write(outByteObject); // запись со смещением 4 (INT)

            System.out.println("length " + rand.length());

            // читаем байты и восстанавливаем объект
            byte[] arrFully = new byte[(int) outByteObject.length];
            System.out.println("arrFully " + arrFully.length);
            rand.seek(4); //  смещение 4 (INT)
            rand.readFully(arrFully); //считаем байты объекта
            ByteArrayInputStream streamByte = new ByteArrayInputStream(arrFully);
            ObjectInputStream inputObject = new ObjectInputStream(streamByte);
            rootNode = null;
            rootNode = (IHuffmanTree) inputObject.readObject();
            factoryHuffmanCode.toPrintRoot(rootNode, 0);
        } catch (IOException e) {
            throw  e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
