package krm.compression_of_text.compressor;

import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import java.io.*;
import java.util.Objects;

public class FileCompressor extends ADriverFileCompressor {

    public static final String PREFIX_BIN = ".krm.huffman.bin";
    public static final String PREFIX_TXT = ".txt";

    protected File inFile;
    protected File outFile;

    public FileCompressor(File inFile, FactoryHuffmanCode factoryHuffman)
            throws IOException, SecurityException {
        super(factoryHuffman);

        this.inFile = inFile;
        this.outFile = createCompressedFile(inFile);
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

    protected final File createCompressedFile(File sourceFile)
            throws IOException, SecurityException {
        StringBuilder nameFile = new StringBuilder(sourceFile.getName());
        // устанавливаем бинарное расширение - PREFIX_BIN
        int indexPrefix = nameFile.lastIndexOf(FileCompressor.PREFIX_TXT);
        if (indexPrefix != -1) {
            nameFile.delete(indexPrefix, nameFile.length());
        }
        nameFile.append(FileCompressor.PREFIX_BIN);
        // полный путь до файла
        StringBuilder fullPath = new StringBuilder(sourceFile.getParent());
        fullPath.append("\\");
        fullPath.append(nameFile);

        File compressedFile = new File(String.valueOf(fullPath));
        compressedFile.delete();

        return compressedFile;
    }

    public void start() throws IOException {
        if (Objects.nonNull(outFile)) {
            initFactoryHuffman(inFile);
            writeObject(super.factoryHuffman.getRootNode(), outFile);
            compressor(inFile, outFile, super.factoryHuffman.getCodes());
        }
    }
}
