package krm.compression_of_text.huffman_algorithm;

import java.io.IOException;
import java.util.*;

public class FactoryHuffmanTree {

    protected Map<Character, Integer> gravityLeafs = new HashMap();
    protected List<IHuffmanTree> nodes = new ArrayList();

    Comparator<ITreeGravity> comparatorCodeGravity = null;

    IHuffmanTree rootNode = null;

    FactoryHuffmanTree(Comparator<ITreeGravity> comparatorCodeGravity) {
        this.comparatorCodeGravity = comparatorCodeGravity;
    }

    public void addWordGravity(char word) {
        int gravity = 1;
        if (gravityLeafs.containsKey(word)) {
            gravity += gravityLeafs.get(word);
        }
        gravityLeafs.put(word, gravity);
    }

    protected void initCollectionOfLeaf() {
        for (Map.Entry item : gravityLeafs.entrySet()) {
            Character key = (Character) item.getKey();
            Integer value = (Integer) item.getValue();

            nodes.add(new HuffmanTreeLeaf(key.charValue(), value.intValue()));
        }
    }

    protected void generateHuffmanTree() {
        while (nodes.size() > 1) {
            Collections.sort(nodes, this.comparatorCodeGravity);
            //System.outFile.println(nodes.toString() + " -before"); // todo test
            nodes.set(1, new HuffmanTreeBiNode<IHuffmanTree>( nodes.get(0), nodes.get(1), comparatorCodeGravity));
            nodes.remove(0);
            //System.outFile.println(nodes.toString() + " -after"); // todo test
        }
        this.rootNode = ((nodes.isEmpty() && (nodes.get(0) == null)))
                        ? null
                        : nodes.get(0);
    }

    public IHuffmanTree getRootNode() {
        if (this.rootNode == null) {
            resetHuffmanTree();
        }
        return this.rootNode;
    }

    public void resetHuffmanTree() {
        initCollectionOfLeaf();
        generateHuffmanTree();
    }

    public static String toPrintRoot(ITreeBiNode node, int shift) {
        String out = "";
        if (node != null) {
            //outFile += toStringNode((ITreeBiNode) node.getLeftSink(), shift + 10);
            System.out.print(toPrintRoot((ITreeBiNode) node.getLeftSink(), shift + 10));

            for (int i = 0; i < shift; i++) {
                System.out.print(" ");
                //outFile += " ";
            }
            System.out.print(node.toString() + '\n');
            //outFile += node.toString() + '\n';

            //outFile += toStringNode((ITreeBiNode) node.getRightSink(), shift + 10);
            System.out.print(toPrintRoot((ITreeBiNode) node.getRightSink(), shift + 10));
        }
        return out;
    }

    public void printRoot() {
        toPrintRoot(rootNode, 0);
    }

    public void printGravityLeafs() {
        System.out.println(gravityLeafs.toString());
    }

}
