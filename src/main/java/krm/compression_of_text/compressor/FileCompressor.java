package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import java.io.*;
import java.util.Objects;

public class FileCompressor extends ADriverFileCompressor {

    public static final String PREFIX_BIN = ".krm.huffman.bin";
    public static final String PREFIX_TXT = ".txt";

    protected File inFile;
    protected File outFile;
    private File sourceFile;

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

    public File getInFile() {
        return inFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    protected final File createCompressedFile(File sourceFile) throws  SecurityException, IOException {
        this.sourceFile = sourceFile;
        StringBuilder nameFile = new StringBuilder(sourceFile.getName());
        // бинарное расширение
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
        if (compressedFile.exists()) {
            compressedFile.delete();
            compressedFile.createNewFile();
        }

        return compressedFile;
    }

    public void start() throws IOException, ClassNotFoundException {
        if (Objects.nonNull(outFile)) {
            initFactoryHuffman(inFile);
            writeObject(super.factoryHuffman.getRootNode(), outFile);
            compressor(inFile, outFile, super.factoryHuffman.getCodes());
        }
    }
}
