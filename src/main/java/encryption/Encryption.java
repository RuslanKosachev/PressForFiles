package encryption;

import compression.exception.CompressionException;
import compression.exception.ErrorCodeCompression;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class Encryption {

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
            this.outFile = createOutFile(inFile);

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

    public void perform(String key, boolean decryption) throws IOException {
        if (Objects.nonNull(outFile)) {
            System.out.println(outFile);
            code(inFile, outFile, key, decryption);
        }
    }

    protected void code(File sourceFile, File compressedFile, String key, boolean decryption) throws IOException {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(sourceFile), CHARSET_NAME));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(compressedFile),
                                                                            CHARSET_NAME))
        ) {
            char[] vector = new char[key.length()];
            char[] block = new char[key.length()];
            char[] keyArr = key.toCharArray();

            int n = in.read(block, 0, key.length());

            while (n != -1) {
                // в цикле по полученным битам выполняем поиск символа по дереву
                for (int i = 0; i < block.length;  i++) {
                    char code = Util.crypt(block[i], keyArr[i], vector[i]);

                    if (decryption) {
                        vector[i] = block[i];
                    } else {
                        vector[i] = code;
                    }

                    out.write((char) code);
                }
                n = in.read(block, 0, key.length());
            }
        } catch (IOException e) {
           throw  e;
        }
    }

    private File createOutFile(File sourceFile) {
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
