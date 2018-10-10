package krm.compression_of_text.compressor;

import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import java.io.*;
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
            int symbol;
            while ((symbol = in.read()) != -1) {
                this.factoryHuffman.addWordGravity((char)symbol);
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

            byte[] outArrayByteObject = byteArrayOutput.toByteArray();
            out.writeInt(outArrayByteObject.length); // размер объекта в байтах
            out.write(outArrayByteObject); // запись со смещением 4 (LENGTH_SERIALIZABLE_IN_BYTE = int)
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected void compressor(File sourceFile, File compressedFile, Map<Character, List<Boolean>> codes)
            throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile), CHARSET_NAME));
            RandomAccessFile out = new RandomAccessFile(compressedFile, "rw")) {

            out.seek(out.readInt() + ADriverFileCompressor.LENGTH_SERIALIZABLE_IN_BYTE);

            List<Boolean> code;
            int key;
            byte buffer = 0;
            byte bit;
            int currentBit = 0;
            while ((key = in.read()) != -1) {
                code = codes.get((char) key);
                for (boolean item : code) {
                    bit = (byte) ((item) ? 1 : 0);
                    buffer = (byte) (buffer | (bit << (UNIT_BUFFER_SIZE_IN_BITS - 1 - currentBit)));
                    currentBit++;
                    if (currentBit == UNIT_BUFFER_SIZE_IN_BITS) {
                        out.writeByte(buffer);
                        currentBit = 0;
                        buffer = 0;
                    }
                }
            }
            // дозапишем байт с последними битами кодированного текста
            out.writeByte(buffer);
            // запишем количетво не пустых бит в последнем байте(buffer) закодированного текста
            out.writeByte(currentBit);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
