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





    // todo test
    public static void main(String[] args) throws IOException {

        CodeGravityComparator compCodeGravity = CodeGravityComparator.getInstance();

        FactoryHuffmanTree f = new FactoryHuffmanTree(compCodeGravity);
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('r');
        f.addWordGravity('e');
        f.addWordGravity('r');
        f.addWordGravity('e');
        f.addWordGravity('u');
        f.addWordGravity('u');
        f.addWordGravity('i');
        f.addWordGravity('i');

        System.out.println(f.gravityLeafs);

        f.initCollectionOfLeaf();

        System.out.println(f.nodes);

        HuffmanTreeLeaf l1 = new HuffmanTreeLeaf('u', 11111);
        System.out.println(f.nodes.contains(l1));

        HuffmanTreeBiNode n1 = new HuffmanTreeBiNode<IHuffmanTree>(new HuffmanTreeLeaf('t', 6445), new HuffmanTreeLeaf('p', 879), CodeGravityComparator.getInstance());
        System.out.println(f.nodes.contains(n1));

        f.nodes.add(new HuffmanTreeBiNode<IHuffmanTree>(new HuffmanTreeLeaf('t', 6445), new HuffmanTreeLeaf('p', 879), CodeGravityComparator.getInstance()));

        Collections.sort(f.nodes, compCodeGravity);
        System.out.println(f.nodes);

        HuffmanTreeBiNode n = (HuffmanTreeBiNode) f.nodes.get(4);
        System.out.println(n.getLeftSink());

        HuffmanTreeLeaf leaf = (HuffmanTreeLeaf) f.nodes.get(0);
        System.out.println(leaf.getSignification());

        System.out.println("///////////////////////");

        ITreeBiNode node = f.getRootNode();

        System.out.println("#############################################################");

        System.out.println(f.toString());
    }
}
