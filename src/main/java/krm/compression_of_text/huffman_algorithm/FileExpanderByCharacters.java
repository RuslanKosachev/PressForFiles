package krm.compression_of_text.huffman_algorithm;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public abstract class FileExpanderByCharacters {

    // размер в битах буфера для чтения закодированного текста
    public static final int UNIT_BUFFER_SIZE_IN_BITS = 8;

    private File inFile;
    private File outFile;

    private HuffmanTree rootNode;

    public FileExpanderByCharacters(File sourceFile) throws IOException {
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

    public void perform() throws Exception {
        if (Objects.nonNull(outFile)) {
            if (readObject(inFile)) {
                expander(inFile, outFile, rootNode);
            }
        }
    }

    protected boolean readObject(File compressedFile)
            throws IOException, ClassNotFoundException, NegativeArraySizeException {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r")) {
            // десериализуем rootNode
            int lengthArrObject = in.readInt();
            byte[] objectArr = new byte[lengthArrObject];

            in.seek(FileCompressorByCharacter.LENGTH_SERIALIZABLE_IN_BYTE); // смещение 4 (INT)

            in.readFully(objectArr); //считаем байты объекта
            ByteArrayInputStream streamArrayByte = new ByteArrayInputStream(objectArr);
            ObjectInputStream streamObject = new ObjectInputStream(streamArrayByte);

            rootNode = (HuffmanTree<Character>) streamObject.readObject();

            return true;
        } catch (ClassNotFoundException | IOException | NegativeArraySizeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected void expander(File compressedFile, File decompressedFile, HuffmanTree root)
            throws IOException {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r");
             Writer out = new BufferedWriter(
                     new OutputStreamWriter(
                             new FileOutputStream(decompressedFile), FileCompressorByCharacter.CHARSET_NAME))) {
            //переходим на первый байт закодированного текста
            in.seek(in.readInt() + FileCompressorByCharacter.LENGTH_SERIALIZABLE_IN_BYTE);

            HuffmanTree rootIn = root;
            boolean bit;
            int currentBit = 0;
            int countBits = UNIT_BUFFER_SIZE_IN_BITS;
            byte[] buffer = new byte[3];

            int n = in.read(buffer, 0, 3);
            int a = n;
            while (n != -1) {
                // в цикле по полученным битам выполняем поиск символа по дереву
                for (currentBit = 0; currentBit < countBits; currentBit++) {
                    bit = ((buffer[0] & (1 << (UNIT_BUFFER_SIZE_IN_BITS - 1 - currentBit))) != 0)
                            ? true
                            : false;
                    if (bit) {
                        rootIn = rootIn.getRightNode();
                    } else {
                        rootIn = rootIn.getLeftNode();
                    }
                    if (Objects.isNull(rootIn.getRightNode()) && Objects.isNull(rootIn.getLeftNode())) {
                        out.write((char) rootIn.getSignification());
                        rootIn = root;
                    }
                }
                n = a;

                /* сдвиг байтов в лево и заполнение последней ячейке новым байтом, если не окончен поток */
                buffer[0] = buffer[1];
                buffer[1] = buffer[2];
                a = in.read(buffer, 2, 1);
                /* если конец потока - последнийбайт(bufferArr[1]) это значение количества значащих бит
                   кодированного текста в предпоследнем байте потока(bufferArr[0]) */
                if (a == -1) {
                    countBits = buffer[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public final File createExpanderFile(File compressedFil) throws SecurityException, IOException {
        StringBuilder nameFile = new StringBuilder(compressedFil.getName());
        // устанавливаем текстовое расширение - FileCompressor.PREFIX_TXT
        int indexPrefix = nameFile.lastIndexOf(FileCompressorByCharacter.PREFIX_BIN);
        if (indexPrefix != -1) {
            nameFile.delete(indexPrefix, nameFile.length());
        }
        nameFile.append(FileCompressorByCharacter.PREFIX_TXT);
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
}
