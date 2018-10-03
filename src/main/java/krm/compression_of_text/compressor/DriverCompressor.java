package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;
import krm.compression_of_text.huffman_algorithm.IHuffmanTree;
import java.io.*;
import java.util.*;

public class DriverCompressor {

    public static final byte BYTE_LENGTH_SERIALIZABLE = 4; // размер в байтах для хранения величины сериализованного дерева Хаффмана (расположение в начале файла)
    public static final byte BYTE_LENGTH_LAST_NULL_BITS = 1; // размер в байтах для хранения значения количества не пустых бит кодированного текста в последнем байте  (расположение в конце файла)

    protected Map<Character, List<Boolean>> codes = null;
    protected File sourceFile;
    protected File compressedFile;
    protected FactoryHuffmanCode factoryHuffman;

    public DriverCompressor(File sourceFile, FactoryHuffmanCode factoryHuffman) throws IOException {
        this.sourceFile = sourceFile;
        this.compressedFile = createCompressedFile(sourceFile);
        this.factoryHuffman = factoryHuffman;
    }

    public File getCompressedFile() {
        return compressedFile;
    }

    public void setCompressedFile(File compressedFile) {
        this.compressedFile = compressedFile;
    }

    protected File createCompressedFile(File sourceFile) throws  SecurityException, IOException {
        StringBuilder nameBin = new StringBuilder(sourceFile.getName());
        int index = nameBin.lastIndexOf(".txt");
        if (index != -1) {
            nameBin.delete(index, nameBin.length());
        }
        nameBin.append(".bin");
        StringBuilder fullName = new StringBuilder(sourceFile.getParent());
        fullName.append("\\");
        fullName.append("compressed_");
        fullName.append(nameBin);

        try {
            File compressedFile = new File(String.valueOf(fullName));
            if (compressedFile.exists()) {
                compressedFile.delete();
                compressedFile.createNewFile();
            }
            return compressedFile;
        } catch (IOException | SecurityException e) {
            System.out.println("невозможно создать или покючиться к файлу: " + fullName);
            throw e;
        }
    }

    protected void initFactoryHuffman(File sourceFile) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(sourceFile))) {
            int symbol;
            while ((symbol = in.read()) != -1) {
                this.factoryHuffman.addWordGravity((char)symbol);
            }
        } catch (IOException e) {
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
            System.out.println("outByteObject " + outArrayByteObject.length); //todo test размер объекта в байтах
            out.writeInt(outArrayByteObject.length); // размер объекта в байтах
            out.write(outArrayByteObject); // запись со смещением 4 (BYTE_LENGTH_SERIALIZABLE = int)
        } catch (IOException e) {
            throw e;
        }
    }

    private void compressor(File sourceFile, File compressedFile, Map<Character, List<Boolean>> codes) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(sourceFile));
            RandomAccessFile out = new RandomAccessFile(compressedFile, "rw")) {

            out.seek(out.readInt() + DriverCompressor.BYTE_LENGTH_SERIALIZABLE);

            List<Boolean> code;
            int key = 0;
            byte buffer = 0;
            byte bit = 0;
            int count = 0;
            while ((key = in.read()) != -1) {
                code = codes.get((char) key);
                for (boolean item : code) {
                    bit = (byte) ((item) ? 1 : 0);
                    buffer = (byte) (buffer | (bit << (7 - count)));
                    count++;
                    if (count == 8) {
                        count = 0;
                        out.writeByte(buffer);
                        buffer = 0;
                    }
                }
            }
            // если есть биты кодированного текста в последнем байте то дозапишем байт
            if (count > 0) { out.write(buffer); }
            // количетво не пустых бит в последнем байте закодированного текста
            out.writeByte(count);
        } catch (IOException e) {
            throw e;
        }
    }

    private void testCompressor(File sourceFile, File compressedFile, Map<Character, List<Boolean>> codes) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(sourceFile));
             RandomAccessFile out = new RandomAccessFile(compressedFile, "rw")) {

            out.seek(0);

            List<Boolean> code;
            int key = 0;
            byte buffer = 0;
            byte bit = 0;
            int count = 0;
            while ((key = in.read()) != -1) {
                code = codes.get((char) key);
                for (boolean item : code) {
                    bit = (byte) ((item) ? 1 : 0);
                    buffer = (byte) (buffer | (bit << (7 - count)));
                    count++;
                    if (count == 8) {
                        out.writeByte(buffer);
                        count = 0;
                        buffer = 0;
                    }
                }
            }
            // если есть биты кодированного текста в последнем байте то дозапишем байт
            if (count > 0) { out.write(buffer); }
            // количетво не пустых бит в последнем байте закодированного текста
            out.writeByte(count);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void main(String args[]) throws IOException {
        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "sourceFile.txt");

        File sourceFileObject = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "object.bin");
        File compressedTextBinFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "compressedText.bin");


        try (
             Reader inBuffR = new BufferedReader(new FileReader(sourceFile));
        ) {
            // test Huffman tree
            FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());
            DriverCompressor com = new DriverCompressor(sourceFile, factoryHuffmanCode);
            com.initFactoryHuffman(sourceFile);
            com.writeObject(factoryHuffmanCode.getRootNode(), com.getCompressedFile());
            FactoryHuffmanCode.toPrintRoot(factoryHuffmanCode.getRootNode(), 0);
            com.compressor(sourceFile, com.getCompressedFile(), factoryHuffmanCode.getCodes());

            // test object.bin
            com.writeObject(factoryHuffmanCode.getRootNode(), sourceFileObject);
            /////////
            // test compressedText.bin
            com.testCompressor(sourceFile, compressedTextBinFile, factoryHuffmanCode.getCodes());
            ////
        } catch (IOException e) {
            /*throw*/  e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
