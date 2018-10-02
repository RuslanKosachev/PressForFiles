package krm.compression_of_text.compressor;


import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;
import krm.compression_of_text.huffman_algorithm.IHuffmanTree;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Decompressor {

    protected IHuffmanTree                  rootNode = null;
    public    InputStream                   in     = null;
    public    Writer                        output = null;

    public Decompressor(InputStream in, Writer out, IHuffmanTree rootNode) throws IOException {
        this.in = in;
        this.output = out;
        this.rootNode = rootNode;
    }

    public void setRootNode(IHuffmanTree rootNode) {
        this.rootNode = rootNode;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public void setOut(Writer out) {
        this.output = out;
    }


    public void decompressor() throws IOException {
        IHuffmanTree root = this.rootNode;
        boolean bit;
        int count = 0;
        int inByte = this.in.read();
        while (inByte != -1) {
            bit = ((inByte & (1 << (7-count))) > 0) ? true : false;
            if (bit) {
                root = (IHuffmanTree) root.getRightSink();
            } else {
                root = (IHuffmanTree) root.getLeftSink();
            }
            if (root.getRightSink()== null && root.getLeftSink() == null) {
                output.write(root.getSignification());
                root = this.rootNode;
            }
            count++;
            if (count == 8) {
                count = 0;
                inByte = this.in.read();
            }
        }
        this.output.flush();
        this.output.close();
    }

    public static void main(String args[]) throws IOException {
        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\sourceFile.txt");
        File codeFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\compressed.bin");
        File decodeFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\decompressed.txt");

        try (Reader inBuffR = new BufferedReader(new FileReader(sourceFile));
             Writer buffOut = new BufferedWriter(new FileWriter(decodeFile));
             BufferedInputStream inBuffR2 = new BufferedInputStream(new FileInputStream(codeFile));)
        {
            // test Huffman tree
            FactoryHuffmanCode factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());
            int symbol;
            while ((symbol = inBuffR.read()) != -1) {
                factoryHuffmanCode.addWordGravity((char)symbol);
            }

            // test compressor
            Decompressor decompressor = new Decompressor(inBuffR2, buffOut, factoryHuffmanCode.getRootNode());
            decompressor.decompressor();

            factoryHuffmanCode.printRoot();
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}
