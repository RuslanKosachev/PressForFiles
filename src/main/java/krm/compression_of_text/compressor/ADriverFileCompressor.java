package krm.compression_of_text.compressor;

import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;
import sun.misc.Cleaner;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

public abstract class ADriverFileCompressor {

    public static final String CHARSET_NAME = "UTF-8";
    // размер в битах буфера для записи закодированного текста
    public static final int  UNIT_BUFFER_SIZE_IN_BITS = 8;
    // размер в байтах для хранения величины сериализованного дерева Хаффмана (расположение в начале файла)
    public static final byte LENGTH_SERIALIZABLE_IN_BYTE = 4;

    protected FactoryHuffmanCode factoryHuffman;

    ADriverFileCompressor(FactoryHuffmanCode factoryHuffman) {
        this.factoryHuffman = factoryHuffman;
    }

    protected void initFactoryHuffman(File sourceFile) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile), CHARSET_NAME))) {
            while (in.ready()) {
                factoryHuffman.addWordGravity((char)in.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected void writeObject(Object rootNode, File compressedFile) throws IOException {
        try (RandomAccessFile out = new RandomAccessFile(compressedFile, "rw")) {
            // сериализуем rootNode в массив байт
            ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
            ObjectOutputStream outputObject = new ObjectOutputStream(byteArrayOutput);
            outputObject.writeObject(rootNode);
            byte[] arrayByteObject = byteArrayOutput.toByteArray();

            out.writeInt(arrayByteObject.length); // размер объекта в байтах
            out.write(arrayByteObject); // запись со смещением 4 (LENGTH_SERIALIZABLE_IN_BYTE = int)
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected void compressor(File sourceFile, File compressedFile, Map<Character, List<Boolean>> codes)
            throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile), CHARSET_NAME));
            RandomAccessFile outRand = new RandomAccessFile(compressedFile, "rw");
            BufferedOutputStream outBuffRand = new BufferedOutputStream(new FileOutputStream(outRand.getFD()))) {
            // учитываем уже записанныей обьект
            outRand.seek(outRand.readInt() + ADriverFileCompressor.LENGTH_SERIALIZABLE_IN_BYTE);

            List<Boolean> code;
            byte buffer = 0;
            byte bit;
            byte currentBit = 0;
            while (in.ready()) {
                code = codes.get((char) in.read());
                for (boolean item : code) {
                    bit = (byte) ((item) ? 1 : 0);
                    buffer = (byte) (buffer | (bit << (UNIT_BUFFER_SIZE_IN_BITS - 1 - currentBit)));
                    currentBit++;
                    if (currentBit == UNIT_BUFFER_SIZE_IN_BITS) {
                        outBuffRand.write(buffer);
                        currentBit = 0;
                        buffer = 0;
                    }
                }
            }
            // дозапишем байт с последними битами кодированного текста
            outBuffRand.write(buffer);
            // запишем количетво не пустых бит в последнем байте(buffer) закодированного текста
            outBuffRand.write(currentBit);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
