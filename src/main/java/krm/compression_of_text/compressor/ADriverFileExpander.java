package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.IHuffmanTree;

import java.io.*;
import java.util.Objects;

public abstract class ADriverFileExpander {

    // размер в битах буфера для чтения закодированного текста
    public static final int UNIT_BUFFER_SIZE_IN_BITS = 8;

    protected IHuffmanTree rootNode;

    protected Object readObject(File compressedFile) throws Exception {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r")) {
            // десериализуем rootNode
            int lengthArrObject = in.readInt();
            byte[] objectArr = new byte[lengthArrObject];
            in.seek(ADriverFileCompressor.LENGTH_SERIALIZABLE_IN_BYTE); // смещение 4 (INT)
            in.readFully(objectArr); //считаем байты объекта
            ByteArrayInputStream streamArrayByte = new ByteArrayInputStream(objectArr);
            ObjectInputStream streamObject = new ObjectInputStream(streamArrayByte);
            IHuffmanTree rootNodeIn = (IHuffmanTree) streamObject.readObject();
            return rootNodeIn;
        } catch (ClassNotFoundException | IOException | NegativeArraySizeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    protected void expander(File compressedFile, File decompressedFile, IHuffmanTree root) throws IOException {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r");
             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(
                             new FileOutputStream(decompressedFile), ADriverFileCompressor.CHARSET_NAME))) {
            //переходим на первый байт закодированного текста
            in.seek(in.readInt() + ADriverFileCompressor.LENGTH_SERIALIZABLE_IN_BYTE);

            IHuffmanTree rootIn = root;
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
                        rootIn = (IHuffmanTree) rootIn.getRightSink();
                    } else {
                        rootIn = (IHuffmanTree) rootIn.getLeftSink();
                    }
                    if (Objects.isNull(rootIn.getRightSink()) && Objects.isNull(rootIn.getLeftSink())) {
                        out.write(rootIn.getSignification());
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
}
