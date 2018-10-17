package krm.compression_of_text.huffman_algorithm;

import java.io.*;
import java.util.*;

public abstract class FileCompressorByCharacter {

    public static final String PREFIX_BIN = ".krm.huffman.bin";
    public static final String PREFIX_TXT = ".txt";
    public static final String CHARSET_NAME = "UTF-8";

    // размер в битах буфера для записи закодированного текста
    public static final int  UNIT_BUFFER_SIZE_IN_BITS = 8;
    // размер в байтах для хранения величины сериализованного дерева Хаффмана (расположение в начале файла)
    public static final byte LENGTH_SERIALIZABLE_IN_BYTE = 4;

    private File inFile;
    private File outFile;

    private BuilderHuffmanTree<Character> factoryHuffman;

    FileCompressorByCharacter(File inFile, BuilderHuffmanTree<Character> factoryHuffman)
            throws IOException, SecurityException {
        this.inFile = inFile;
        this.outFile = createCompressedFile(inFile);
        this.factoryHuffman = factoryHuffman;
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

    public void perform() throws IOException {
        if (Objects.nonNull(outFile)) {
            initBuilderHuffman(inFile);
            writeObject(factoryHuffman.getRootNode(), outFile);
            compressor(inFile, outFile, factoryHuffman.getCodes());
        }
    }

    protected void initBuilderHuffman(File sourceFile) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile), CHARSET_NAME))) {
            int symbol;
            while ((symbol = in.read()) != -1) {
                factoryHuffman.addSignification((char)symbol);
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

            out.seek(out.readInt() + FileCompressorByCharacter.LENGTH_SERIALIZABLE_IN_BYTE);

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

    protected final File createCompressedFile(File sourceFile)
            throws IOException, SecurityException {
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
