package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;
import krm.compression_of_text.huffman_algorithm.IHuffmanTree;

import java.io.*;

public class DriverExpander {

    public static final int UNIT_BUFFER_SIZE_IN_BITS = 8;
    public static final String PREFIX_BIN = ".bin";
    public static final String PREFIX_TXT = ".txt";

    protected IHuffmanTree rootNode;
    protected File inFile;
    protected File outFile;

    public DriverExpander(File in) throws IOException {
        this.inFile = in;
    }

    public void setInFile(File inFile) {
        this.inFile = inFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public File getInFile() {
        return inFile;
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

        try {
            File expanderFile = new File(String.valueOf(fullPath));
            if (expanderFile.exists() && !expanderFile.canWrite()) {
                throw new SecurityException("невозможно открыть фаил на запись: " + fullPath);
            }
            return expanderFile;
        } catch (SecurityException e) {
            throw e;
        }
    }

    protected Object readObject(File compressedFile) throws IOException, ClassNotFoundException {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r")) {
            // десериализуем rootNode
            int lengthArrObject = in.readInt();
            byte[] objectArr = new byte[lengthArrObject];
            in.seek(DriverCompressor.BYTE_LENGTH_SERIALIZABLE); // смещение 4 (INT)
            in.readFully(objectArr); //считаем байты объекта
            ByteArrayInputStream streamArrayByte = new ByteArrayInputStream(objectArr);
            ObjectInputStream streamObject = new ObjectInputStream(streamArrayByte);
            IHuffmanTree rootNodeIn = (IHuffmanTree) streamObject.readObject();
            this.rootNode = rootNodeIn;
            return rootNodeIn;
        } catch (IOException | ClassNotFoundException e) {
            throw e;
        }
    }

    public void expander(File compressedFile, File decompressedFile, IHuffmanTree root) throws IOException {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r");
             BufferedWriter out = new BufferedWriter(new FileWriter(decompressedFile))) {
            //переходим на первый байт закодированного текста
            in.seek(in.readInt() + DriverCompressor.BYTE_LENGTH_SERIALIZABLE);

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

/*    public void testDecompressor(File compressedFile, File decompressedFile, IHuffmanTree root) throws IOException {
        try (RandomAccessFile in = new RandomAccessFile(compressedFile, "r");
             BufferedWriter out = new BufferedWriter(new FileWriter(decompressedFile))) {

            in.seek(0);

            IHuffmanTree rootIn = root;
            int n = 0;
            int a = 0;
            boolean bit;
            int current = 0;
            int count = UNIT_BUFFER_SIZE_IN_BITS;
            byte[] arr = new byte[3];

            n = in.read(arr, 0, 3);
            while (n != -1) {
                for (current = 0; current < count; current++) {
                    bit = ((arr[0] & (1 << ((UNIT_BUFFER_SIZE_IN_BITS - 1) - current))) != 0) ? true : false;
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

                arr[0] = arr[1];
                arr[1] = arr[2];
                a = in.read(arr, 2, 1);

                if (a == -1) {
                    count = arr[1];
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }*/

    public static void main(String args[]) throws IOException {
        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "compressed_sourceFile.bin");
        File decodeTxtFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "compressed_sourceFile.txt");

        File sourceFileObject = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "object.bin");
        File compressedTextBinFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "compressedText.bin");
        File compressedTextTxtFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "compressedText.txt");

        File sourceFileTest = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\" +
                "sourceFile.txt");

        try (Reader inBuffR = new BufferedReader(new FileReader(sourceFile));
             //Writer buffOut = new BufferedWriter(new FileWriter(decodeFile));
        ) {
            // test compressor
            DriverExpander expander = new DriverExpander(sourceFile);

            expander.setOutFile(expander.createExpanderFile(sourceFile));

            expander.readObject(sourceFile);

            if (expander.rootNode != null) {
                FactoryHuffmanCode.toPrintRoot(expander.rootNode, 0);
            }

            expander.expander(sourceFile, expander.getOutFile(), expander.rootNode);

            ////////////////////////     test object       /////////////////////
            System.out.println("------------------  test object -----------------");
            expander.readObject(sourceFileObject);
            //FactoryHuffmanCode.toPrintRoot((IHuffmanTree)expander.readObject(sourceFileObject), 0);
            //////
            // testDecompressor()
            FactoryHuffmanCode factoryHuffmanCodeTest = new FactoryHuffmanCode(CodeGravityComparator.getInstance());
            DriverCompressor com = new DriverCompressor(sourceFileTest, factoryHuffmanCodeTest);
            com.initFactoryHuffman(sourceFileTest);
            System.out.println("------------------  test factoryHuffmanCode.getRootNode() -----------------");
            //FactoryHuffmanCode.toPrintRoot(factoryHuffmanCodeTest.getRootNode(), 0 );
            /*decompressor.testDecompressor(compressedTextBinFile, compressedTextTxtFile,
                    factoryHuffmanCodeTest.getRootNode());*/
            ////////////////////////////////////////////////////////////////
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            System.out.print("дессириализация объекта не удалась");
            e.printStackTrace();
        }
    }
}
