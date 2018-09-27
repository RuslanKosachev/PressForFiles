package krm.compression_of_text.huffman_algorithm;

import java.util.*;

public class FactoryHuffmanTree {

    protected Map<Character, Integer> gravityLeafs = new HashMap();
    protected List<IHuffmanTree> nodeList = new ArrayList();

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

            nodeList.add(new HuffmanTreeLeaf(key.charValue(), value.intValue()));
        }
    }

    protected void generateHuffmanTree() {
        while (nodeList.size() > 1) {
            Collections.sort(nodeList, this.comparatorCodeGravity);
            nodeList.set(1, new HuffmanTreeBiNode<IHuffmanTree>( nodeList.get(0), nodeList.get(1), comparatorCodeGravity));
            nodeList.remove(0);
            //System.out.println(nodeList); // todo test
        }
        this.rootNode = ((nodeList.isEmpty() && (nodeList.get(0) == null)))
                        ? null
                        : nodeList.get(0);
    }

    public IHuffmanTree getRoot() {
        if (this.rootNode == null) {
            reset();
        }
        return this.rootNode;
    }

    public void reset() {
        initCollectionOfLeaf();
        generateHuffmanTree();
    }

    public String toStringNode(ITreeBiNode node, int shift) {
        String out = "";
        if (node != null) {
            out += toStringNode((ITreeBiNode) node.getLeftSink(), shift + 10);

            for (int i = 0; i < shift; i++) {
                //System.out.print(" ");
                out += " ";
            }

            //System.out.println(node.toString());
            out += node.toString() + '\n';

            out += toStringNode((ITreeBiNode) node.getRightSink(), shift + 10);
        }
        return out;
    }

    public String toString() {
        return toStringNode(rootNode, 0);
    }



    // todo test
    public static void main(String[] args) {

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

        System.out.println(f.nodeList);

        HuffmanTreeLeaf l1 = new HuffmanTreeLeaf('u', 11111);
        System.out.println(f.nodeList.contains(l1));

        HuffmanTreeBiNode n1 = new HuffmanTreeBiNode<IHuffmanTree>(new HuffmanTreeLeaf('t', 6445), new HuffmanTreeLeaf('p', 879), CodeGravityComparator.getInstance());
        System.out.println(f.nodeList.contains(n1));

        f.nodeList.add(new HuffmanTreeBiNode<IHuffmanTree>(new HuffmanTreeLeaf('t', 6445), new HuffmanTreeLeaf('p', 879), CodeGravityComparator.getInstance()));

        Collections.sort(f.nodeList, compCodeGravity);
        System.out.println(f.nodeList);

        HuffmanTreeBiNode n = (HuffmanTreeBiNode) f.nodeList.get(4);
        System.out.println(n.getLeftSink());

        HuffmanTreeLeaf leaf = (HuffmanTreeLeaf) f.nodeList.get(0);
        System.out.println(leaf.getSignification());

        System.out.println("///////////////////////");

        ITreeBiNode node = f.getRoot();

        System.out.println("#############################################################");

        System.out.println(f.toString());
    }
}
