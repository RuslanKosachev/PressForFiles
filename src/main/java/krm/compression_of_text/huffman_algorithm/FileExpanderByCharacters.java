package krm.compression_of_text.huffman_algorithm;

import krm.exception.CompressionException;
import krm.exception.ErrorCodeCompression;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class FileExpanderByCharacters {

    // размер в битах буфера для чтения закодированного текста
    private static final int UNIT_BUFFER_SIZE_IN_BITS = 8;

    private File inFile;
    private File outFile;
    private HuffmanTree rootNode;

    public FileExpanderByCharacters(File inFile)
            throws CompressionException {
        try {
            if (!(inFile.exists())) {
                throw new CompressionException(ErrorCodeCompression.PATH_ERROR);
            }
            if (!(inFile.canRead())) {
                throw new CompressionException(ErrorCodeCompression.NO_ACCESS);
            }
            this.inFile = inFile;
            this.outFile = createExpanderFile(inFile);
        } catch (CompressionException e) {
            throw e;
        }
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

    public void perform() throws CompressionException {
        if (Objects.nonNull(outFile)) {
            if (readObject(inFile)) {
                expander(inFile, outFile, rootNode);
            }
        }
    }

    private boolean readObject(File compressedFile)
            throws CompressionException {
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
        } catch (ClassNotFoundException | NegativeArraySizeException e) {
            throw new CompressionException(ErrorCodeCompression.DESERIALIZATION_ERROR, e);
        } catch (IOException e) {
            throw new CompressionException(ErrorCodeCompression.EXPANDER_ERROR, e);
        }
    }

    private void expander(File compressedFile, File decompressedFile, HuffmanTree root)
            throws CompressionException {
        try (RandomAccessFile inRand = new RandomAccessFile(compressedFile, "r");
             BufferedInputStream inBuffRand = new BufferedInputStream(new FileInputStream(inRand.getFD()));
             Writer out = new BufferedWriter(
                     new OutputStreamWriter(
                             new FileOutputStream(decompressedFile), FileCompressorByCharacter.CHARSET_NAME))) {
            //переходим на первый байт закодированного текста
            inRand.seek(inRand.readInt() + FileCompressorByCharacter.LENGTH_SERIALIZABLE_IN_BYTE);

            HuffmanTree rootItem = root;
            boolean bit;
            int currentBit = 0;
            int countBits = UNIT_BUFFER_SIZE_IN_BITS;
            byte[] buffer = new byte[3];

            int n = inBuffRand.read(buffer, 0, 3);
            int a = n;
            while (n != -1) {
                // в цикле по полученным битам выполняем поиск символа по дереву
                for (currentBit = 0; currentBit < countBits; currentBit++) {
                    bit = ((buffer[0] & (1 << (UNIT_BUFFER_SIZE_IN_BITS - 1 - currentBit))) != 0)
                            ? true
                            : false;
                    if (bit) {
                        rootItem = rootItem.getRightNode();
                    } else {
                        rootItem = rootItem.getLeftNode();
                    }
                    if (Objects.isNull(rootItem.getRightNode()) && Objects.isNull(rootItem.getLeftNode())) {
                        out.write((char) rootItem.getSignification());
                        rootItem = root;
                    }
                }
                n = a;
                /* сдвиг байтов в лево и заполнение последней ячейке новым байтом, если не окончен поток */
                buffer[0] = buffer[1];
                buffer[1] = buffer[2];
                a = inBuffRand.read(buffer, 2, 1);
                /* если конец потока - последнийбайт(bufferArr[1]) это значение количества значащих бит
                   кодированного текста в предпоследнем байте потока(bufferArr[0]) */
                if (a == -1) {
                    countBits = buffer[1];
                }
            }
        } catch (IOException e) {
            throw new CompressionException(ErrorCodeCompression.EXPANDER_ERROR, e);
        }
    }

    private File createExpanderFile(File compressedFil) {
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

        return expanderFile;
    }
}
