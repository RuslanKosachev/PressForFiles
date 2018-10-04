package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import java.io.*;
import java.util.Objects;

public class FileCompressor extends ADriverCompressor {

    public static final byte BYTE_LENGTH_SERIALIZABLE = 4; // размер в байтах для хранения величины сериализованного дерева Хаффмана (расположение в начале файла)
    public static final byte BYTE_LENGTH_LAST_NULL_BITS = 1; // размер в байтах для хранения значения количества не пустых бит кодированного текста в последнем байте  (расположение в конце файла)

    protected File inFile;
    protected File outFile;

    public FileCompressor(File sourceFile, FactoryHuffmanCode factoryHuffman) throws IOException {
        super(factoryHuffman);

        if (!sourceFile.exists()) {
            throw new IOException("фаил отсуцтвует " + sourceFile.getPath());
        }
        else if (!sourceFile.canRead()) {
            throw new SecurityException("фаил защище от чтения: " + sourceFile.getAbsolutePath());
        }

        this.inFile = sourceFile;
        this.outFile = createCompressedFile(sourceFile);
    }

    public File getOutFile() {
        return outFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
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

        File compressedFile = new File(String.valueOf(fullName));
        if (compressedFile.exists()) {
            compressedFile.delete();
            compressedFile.createNewFile();
        }

        return compressedFile;
    }



    public void start() throws IOException, ClassNotFoundException {
        initFactoryHuffman(inFile);

        if (Objects.nonNull(outFile)) {
            initFactoryHuffman(inFile);
            writeObject(this.factoryHuffman.getRootNode(), outFile);
            compressor(inFile, outFile, this.factoryHuffman.getCodes());
        }
    }

    public static void main(String args[]) throws IOException {
        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\" +
                "main\\java\\krm\\compression_of_text\\compressor\\sourceFile.txt");


        try {
            FileCompressor compressor = new FileCompressor(sourceFile, new FactoryHuffmanCode(CodeGravityComparator.getInstance()));
            compressor.start();
        } catch (IOException e) {
            throw  e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
