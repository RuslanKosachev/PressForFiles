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

    protected File createCompressedFile(File sourceFile) throws IOException {
        StringBuilder nameBin = new StringBuilder(sourceFile.getName());
        int index = nameBin.lastIndexOf(".");
        nameBin.delete(index + 1, nameBin.length());
        nameBin.append("bin");
        StringBuilder fullName = new StringBuilder(sourceFile.getParent());
        fullName.append("\\");
        fullName.append("compressed_");
        fullName.append(nameBin);

        try {
            File compressedFile = new File(String.valueOf(fullName));
            compressedFile.delete();
            compressedFile.createNewFile();
            return compressedFile;
        } catch (IOException e) {
            System.out.println("невозможно создать или покючиться к файлу: " + fullName);
            throw e;
        }
    }

    protected void initFactoryHuffman(File sourceFile) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(sourceFile))) {
            int symbol;
            while ((symbol = in.read()) != -1) {
                factoryHuffman.addWordGravity((char)symbol);
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

            out.seek(out.readInt());

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
                        out.write(buffer);
                        buffer = 0;
                    }
                }
            }
            // если есть биты кодированного текста в последнем байте то дозапишем байт
            if (count > 0) { out.write(buffer); }
            // количетво не пустых бит в последнем байте закодированного текста
            out.write(count);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void main(String args[]) throws IOException {
        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\sourceFile.txt");
        File compressedFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\compressed.bin");

        try (
             Reader inBuffR = new BufferedReader(new FileReader(sourceFile));
        ) {
            // test Huffman tree
            FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());
            DriverCompressor com = new DriverCompressor(sourceFile, factoryHuffmanCode);
            com.initFactoryHuffman(sourceFile);
            com.writeObject(factoryHuffmanCode.getRootNode(), com.getCompressedFile());
            com.compressor(sourceFile, com.getCompressedFile(), factoryHuffmanCode.getCodes());

            // Test читаем байты и восстанавливаем объект
            RandomAccessFile rand = new RandomAccessFile(com.getCompressedFile(), "rw");
            int len = rand.readInt();
            byte[] arrFully = new byte[(int) len];
            rand.seek(BYTE_LENGTH_SERIALIZABLE); //  смещение 4 (INT)
            rand.readFully(arrFully); //считаем байты объекта
            ByteArrayInputStream streamArrayByte = new ByteArrayInputStream(arrFully);
            ObjectInputStream inputstreamObject = new ObjectInputStream(streamArrayByte);
            IHuffmanTree rootNodeIn = (IHuffmanTree) inputstreamObject.readObject();
            factoryHuffmanCode.toPrintRoot(rootNodeIn, 0);
        } catch (IOException | ClassNotFoundException e) {
            /*throw*/  e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
