package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;
import krm.compression_of_text.huffman_algorithm.IHuffmanTree;

import java.io.*;
import java.util.Objects;

public class FileExpander extends ADriverExpander {

    public static final String PREFIX_BIN = ".bin";
    public static final String PREFIX_TXT = ".txt";

    protected IHuffmanTree rootNode;
    private File inFile;
    private File outFile;

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

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public final File createExpanderFile(File compressedFil) throws SecurityException, IOException {
        StringBuilder nameFile = new StringBuilder(compressedFil.getName());
        int indexPrefix = nameFile.lastIndexOf(PREFIX_BIN);
        if (indexPrefix != -1) {
            nameFile.delete(indexPrefix, nameFile.length());
        }
        nameFile.append(PREFIX_TXT);
        //todo реализовать -> убирать подстроку "compressed_" если есть
        StringBuilder fullPath = new StringBuilder(compressedFil.getParent());
        fullPath.append("\\");
        fullPath.append(nameFile);

        File expanderFile = new File(String.valueOf(fullPath));
        if (expanderFile.exists() && !expanderFile.canWrite()) {
            throw new SecurityException("фаил защище от записи: " + fullPath);
        }

        return expanderFile;
    }


    public void start() throws IOException, ClassNotFoundException {
        this.rootNode = (IHuffmanTree) readObject(inFile);

        if (Objects.nonNull(outFile)) {
            readObject(inFile);
            expander(this.inFile, outFile, this.rootNode);
        }
    }
    public static void main(String args[]) throws IOException {
        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\" +
                "main\\java\\krm\\compression_of_text\\compressor\\compressed_sourceFile.bin");

        try {
            // test compressor
            FileExpander expander = new FileExpander(sourceFile);
            expander.start();

            if (expander.rootNode != null) {
                FactoryHuffmanCode.toPrintRoot(expander.rootNode, 0);
            }

        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            System.out.print("дессириализация объекта не удалась");
            e.printStackTrace();
        }
    }
}
