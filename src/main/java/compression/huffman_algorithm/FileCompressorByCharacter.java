package compression.huffman_algorithm;

import compression.exception.CompressionException;
import compression.exception.ErrorCodeCompression;

import java.io.*;
import java.util.*;

public class FileCompressorByCharacter {

    // размер в битах буфера для записи закодированного текста
    private static final int  UNIT_BUFFER_SIZE_IN_BITS = 8;
    // размер в байтах для хранения величины сериализованного дерева Хаффмана (расположение в начале файла)
    public static final byte LENGTH_SERIALIZABLE_IN_BYTE = 4;

    public static final String PREFIX_BIN = ".huffman.bin";
    public static final String PREFIX_TXT = ".txt";
    public static final String CHARSET_NAME = "UTF-8";

    private File inFile;
    private File outFile;
    private BuilderHuffmanTree<Character> builderHuffmanTree;

    public FileCompressorByCharacter(File inFile, BuilderHuffmanTree<Character> factoryHuffman)
            throws CompressionException {
        try {
            if (!(inFile.exists())) {
                throw new CompressionException(ErrorCodeCompression.PATH_ERROR);
            }
            if (!(inFile.canRead())) {
                throw new CompressionException(ErrorCodeCompression.NO_ACCESS);
            }
            this.inFile = inFile;
            this.outFile = createCompressedFile(inFile);
            this.builderHuffmanTree = factoryHuffman;
        } catch (CompressionException e) {
            throw e;
        }
    }

    public File getInFile() {
        return inFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    public void perform()
            throws CompressionException {
        if (Objects.nonNull(outFile)) {
            initBuilderHuffman(inFile);
            writeObject(builderHuffmanTree.getSignificationFrequency(), outFile);
            compressor(inFile, outFile, builderHuffmanTree.getCodes());
        }
    }

    protected void initBuilderHuffman(File sourceFile)
            throws CompressionException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile), CHARSET_NAME))) {
            while (in.ready()) {
                builderHuffmanTree.addSignification((char)in.read());
            }
        } catch (IOException e) {
            throw new CompressionException(ErrorCodeCompression.COMPRESSION_ERROR, e);
        }
    }

    protected void writeObject(Object obj, File compressedFile)
            throws CompressionException {
        try (RandomAccessFile out = new RandomAccessFile(compressedFile, "rw")) {
            // сериализуем rootNode в массив байт
            ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
            ObjectOutputStream outputObject = new ObjectOutputStream(byteArrayOutput);
            outputObject.writeObject(obj);
            byte[] arrayByteObject = byteArrayOutput.toByteArray();

            out.writeInt(arrayByteObject.length); // размер объекта в байтах
            out.write(arrayByteObject); // запись со смещением 4 (LENGTH_SERIALIZABLE_IN_BYTE = int)
        } catch (IOException e) {
            throw new CompressionException(ErrorCodeCompression.COMPRESSION_ERROR, e);
        }
    }

    protected void compressor(File sourceFile, File compressedFile, Map<Character, List<Boolean>> codes)
            throws CompressionException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile), CHARSET_NAME));
            RandomAccessFile outRand = new RandomAccessFile(compressedFile, "rw");
            BufferedOutputStream outBuffRand = new BufferedOutputStream(new FileOutputStream(outRand.getFD()))) {
            // учитываем уже записанныей обьект
            outRand.seek(outRand.readInt() + FileCompressorByCharacter.LENGTH_SERIALIZABLE_IN_BYTE);

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
            throw new CompressionException(ErrorCodeCompression.COMPRESSION_ERROR, e);
        }
    }

    private File createCompressedFile(File sourceFile) {
        StringBuilder nameFile = new StringBuilder(sourceFile.getName());
        // устанавливаем бинарное расширение - PREFIX_BIN
        int indexPrefix = nameFile.lastIndexOf(PREFIX_TXT);
        if (indexPrefix != -1) {
            nameFile.delete(indexPrefix, nameFile.length());
        }
        nameFile.append(PREFIX_BIN);
        // полный путь до файла
        StringBuilder fullPath = new StringBuilder(sourceFile.getParent());
        fullPath.append("\\");
        fullPath.append(nameFile);

        File compressedFile = new File(String.valueOf(fullPath));
        compressedFile.delete();

        return compressedFile;
    }
}
