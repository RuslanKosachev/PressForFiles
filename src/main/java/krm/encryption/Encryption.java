package krm.encryption;

import krm.compression_of_text.huffman_algorithm.FileCompressorByCharacter;
import krm.exception.CompressionException;
import krm.exception.ErrorCodeCompression;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class Encryption {

    // размер в битах буфера для записи закодированного текста
    private static final int  UNIT_BUFFER_SIZE_IN_BITS = 8;
    // размер в байтах для хранения величины сериализованного дерева Хаффмана (расположение в начале файла)
    public static final byte LENGTH_SERIALIZABLE_IN_BYTE = 4;

    private File inFile;
    private File outFile;

    public static final String CHARSET_NAME = "UTF-8";

    public Encryption(File inFile)
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

           // code(inFile, outFile));
        }
    }

    protected void code(File sourceFile, File compressedFile, String key)
            throws CompressionException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile), CHARSET_NAME));
             BufferedWriter out = new BufferedWriter(
                         new OutputStreamWriter(
                         new FileOutputStream(compressedFile), FileCompressorByCharacter.CHARSET_NAME))
        ) {


        } catch (IOException e) {
            throw new CompressionException(ErrorCodeCompression.COMPRESSION_ERROR, e);
        }
    }

    private File createCompressedFile(File sourceFile) {
        StringBuilder nameFile = new StringBuilder(sourceFile.getName());
        // UUID
        int index = nameFile.indexOf(".");
        if (index == -1) {
            index = nameFile.length();
        }
        nameFile.insert(index, "_" + UUID.randomUUID().toString());
        //  полный путь
        StringBuilder fullPath = new StringBuilder(sourceFile.getParent());
        fullPath.append("\\");
        fullPath.append(nameFile);

        File expanderFile = new File(String.valueOf(fullPath));

        return expanderFile;
    }
}
