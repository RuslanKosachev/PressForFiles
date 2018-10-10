package krm.compression_of_text.huffman_algorithm;

import java.util.*;

public class FactoryHuffmanTree {

    protected Map<Character, Integer> gravityLeafs = new HashMap();
    protected ArrayList<IHuffmanTree> nodes = new ArrayList<>();
    protected Comparator<ITreeGravity> comparatorCodeGravity = null;
    protected IHuffmanTree rootNode = null;

    FactoryHuffmanTree(Comparator<ITreeGravity> comparatorCodeGravity) {
        this.comparatorCodeGravity = comparatorCodeGravity;
    }

    public Map<Character, Integer> getGravityLeafs() {
        return gravityLeafs;
    }

    public void addWordGravity(char signification) {
        int gravity = 1;
        if (gravityLeafs.containsKey(signification)) {
            gravity += gravityLeafs.get(signification);
        }
        gravityLeafs.put(signification, gravity);
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

    protected void initCollectionOfLeaf() {
        for (Map.Entry item : gravityLeafs.entrySet()) {
            Character key = (Character) item.getKey();
            Integer value = (Integer) item.getValue();

            nodes.add(new HuffmanTreeLeaf(key.charValue(), value.intValue()));
        }
    }

    protected void generateHuffmanTree() {
        IHuffmanTree newNode;
        while (nodes.size() > 1) {
            nodes.sort(this.comparatorCodeGravity);
            // новый узел из двух начальных элементов с удалением использованных элемементов
            newNode = new HuffmanTreeBiNode<IHuffmanTree>(nodes.get(0), nodes.get(1), comparatorCodeGravity);
            nodes.set(1, newNode);
            nodes.remove(0);
        }
        this.rootNode = ((nodes.isEmpty() && (nodes.get(0) == null)))
                        ? null
                        : nodes.get(0);
    }

    public static int toPrintRoot(ITreeBiNode node, int shift) {
        if (node != null) {
            System.out.print(toPrintRoot((ITreeBiNode) node.getLeftSink(), shift + 10));

            for (int i = 0; i < shift; i++) {
                System.out.print(" ");
            }
            System.out.print(node.toString() + '\n');

            System.out.print(toPrintRoot((ITreeBiNode) node.getRightSink(), shift + 10));
        }
        return 1;
    }

    public void printRoot() {
        toPrintRoot(rootNode, 0);
    }

    public void printGravityLeafs() {
        System.out.println(gravityLeafs.toString());
    }

    public String toStringGravityLeafs() {
        StringBuilder result = new StringBuilder("symbol frequencies: \n");
        int sum = 0;

        for (Map.Entry item : gravityLeafs.entrySet()) {
            Character key = (Character) item.getKey();
            Integer value = (Integer) item.getValue();

            result.append("{" + key.charValue() + "} = " + value.intValue() + "\n");

            sum += value.intValue();
        }

        result.append("total characters: ");
        result.append( sum);

        return String.valueOf(result);
    }
}
