package krm.compression_of_text.huffman_algorithm;


import krm.compression_of_text.compressor.Compressor;

import java.io.*;
import java.util.*;

public class FactoryHuffmanCode extends FactoryHuffmanTree {

    protected List<Boolean> code = new LinkedList<>();
    protected Map<Character, List<Boolean>> codes = new TreeMap<>();

    public FactoryHuffmanCode(Comparator<ITreeGravity> comparatorCodeGravity) {
        super(comparatorCodeGravity);
    }

    public Map<Character, List<Boolean>> getCodes() {
        if (codes.isEmpty()) {
            resetCodes();
        }
        return this.codes;
    }

    public void resetCodes() {
        this.initCodes(super.getRootNode());
    }


    protected void initCodes(IHuffmanTree node) {
        if (node.getLeftSink() != null) {
            this.code.add(false);
            initCodes((IHuffmanTree) node.getLeftSink());
        }
        if (node.getRightSink() != null) {
            this.code.add(true);
            initCodes((IHuffmanTree) node.getRightSink());
        }
        if ((node.getLeftSink() == null) && (node.getRightSink() == null)) {
            this.codes.put(node.getSignification(), new LinkedList<Boolean>(code));
        }
        if (!code.isEmpty()) {
            int i = code.size() - 1;
            this.code.remove(i);
        }
    }

    public void printCodes() {
        System.out.println(codes.toString());
    }

    // todo test
    public static void main(String[] args) throws IOException {

        File sourceFile = new File("E:\\DATA\\архив\\проекты\\project_java\\project\\PressForFiles\\src\\main\\java\\krm\\compression_of_text\\compressor\\sourceFile.txt");
        Reader inBuffR = new BufferedReader(new FileReader(sourceFile));

        // test Huffman tree
        FactoryHuffmanCode  factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());
        int symbol;
        while ((symbol = inBuffR.read()) != -1) {
            factoryHuffmanCode.addWordGravity((char)symbol);
        }

        factoryHuffmanCode.getCodes();
        factoryHuffmanCode.printRoot();
        factoryHuffmanCode.printGravityLeafs();
        factoryHuffmanCode.printCodes();


    }
}
