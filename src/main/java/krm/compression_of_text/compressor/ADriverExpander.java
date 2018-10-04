package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.IHuffmanTree;

import java.io.*;

public abstract class ADriverExpander {

    public static final int UNIT_BUFFER_SIZE_IN_BITS = 8;

    protected Object readObject(File compressedFile) throws IOException, ClassNotFoundException {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r")) {
            // десериализуем rootNode
            int lengthArrObject = in.readInt();
            byte[] objectArr = new byte[lengthArrObject];
            in.seek(ADriverCompressor.BYTE_LENGTH_SERIALIZABLE); // смещение 4 (INT)
            in.readFully(objectArr); //считаем байты объекта
            ByteArrayInputStream streamArrayByte = new ByteArrayInputStream(objectArr);
            ObjectInputStream streamObject = new ObjectInputStream(streamArrayByte);
            IHuffmanTree rootNodeIn = (IHuffmanTree) streamObject.readObject();
            return rootNodeIn;
        } catch (IOException | ClassNotFoundException e) {
            throw e;
        }
    }

    protected void expander(File compressedFile, File decompressedFile, IHuffmanTree root) throws IOException {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r");
             BufferedWriter out = new BufferedWriter(new FileWriter(decompressedFile))) {
            //переходим на первый байт закодированного текста
            in.seek(in.readInt() + ADriverCompressor.BYTE_LENGTH_SERIALIZABLE);

            IHuffmanTree rootIn = root;
            boolean bit;
            int currentBit = 0;
            int countBits = UNIT_BUFFER_SIZE_IN_BITS;
            byte[] bufferArr = new byte[3];

            int n = in.read(bufferArr, 0, 3);
            int a = n;
            while (n != -1) {
                for (currentBit = 0; currentBit < countBits; currentBit++) {
                    bit = ((bufferArr[0] & (1 << ((UNIT_BUFFER_SIZE_IN_BITS - 1) - currentBit))) != 0)
                            ? true
                            : false;
                    if (bit) {
                        rootIn = (IHuffmanTree) rootIn.getRightSink();
                    } else {
                        rootIn = (IHuffmanTree) rootIn.getLeftSink();
                    }
                    if (rootIn.getRightSink() == null && rootIn.getLeftSink() == null) {
                        out.write(rootIn.getSignification());
                        rootIn = root;
                    }
                }
                n = a;

                bufferArr[0] = bufferArr[1];
                bufferArr[1] = bufferArr[2];
                a = in.read(bufferArr, 2, 1);

                if (a == -1) {
                    countBits = bufferArr[1];
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
