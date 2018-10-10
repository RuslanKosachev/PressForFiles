package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.IHuffmanTree;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class FileExpander extends ADriverFileExpander {

    public static final String PREFIX_TXT = ".txt";

    protected File inFile;
    protected File outFile;

    public FileExpander(File sourceFile) throws IOException {
        super();

        if (!sourceFile.exists()) {
            throw new IOException("фаил отсуцтвует " + sourceFile.getPath());
        }
        else if (!sourceFile.canRead()) {
            throw new SecurityException("фаил защище от чтения: " + sourceFile.getAbsolutePath());
        }

        this.inFile = sourceFile;
        this.outFile = createExpanderFile(sourceFile);
    }

    public File getInFile() {
        return inFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public final File createExpanderFile(File compressedFil) throws SecurityException, IOException {
        StringBuilder nameFile = new StringBuilder(compressedFil.getName());
        // txt
        int indexPrefix = nameFile.lastIndexOf(FileCompressor.PREFIX_BIN);
        if (indexPrefix != -1) {
            nameFile.delete(indexPrefix, nameFile.length());
        }
        nameFile.append(PREFIX_TXT);
        // UUID
        int index = nameFile.indexOf(".");
        if (index == -1) {
            index = nameFile.length();
        }
        nameFile.insert(index, "_" + UUID.randomUUID().toString());
        //  полный путь
        StringBuilder fullPath = new StringBuilder(compressedFil.getParent());
        fullPath.append("\\");
        fullPath.append(nameFile);

        File expanderFile = new File(String.valueOf(fullPath));
        if (expanderFile.exists() && !expanderFile.canWrite()) {
            throw new SecurityException("фаил защищен от записи: " + fullPath);
        }

        return expanderFile;
    }

    public void start() throws Exception {
        if (Objects.nonNull(outFile)) {
            super.rootNode = (IHuffmanTree) readObject(inFile);
            readObject(inFile);
            expander(inFile, outFile, super.rootNode);
        }
    }
}