package krm.compression_of_text.huffman_algorithm;

import java.util.*;

public class FactoryHuffmanTree {

    protected Map<Character, Integer> gravityLeafsMap = new HashMap();
    protected List<ITreeGravity> nodeList = new ArrayList();

    Comparator<ITreeGravity> comparatorCodeGravity = null;

    ITreeBiNode rootNode = null;

    FactoryHuffmanTree(Comparator<ITreeGravity> comparatorCodeGravity) {
        this.comparatorCodeGravity = comparatorCodeGravity;
    }

    public void addWordGravity(char word) {
        int gravity = 1;
        if (gravityLeafsMap.containsKey(word)) {
            gravity += gravityLeafsMap.get(word);
        }
        gravityLeafsMap.put(word, gravity);
    }

    protected void initCollectionOfLeaf() {
        for (Map.Entry item : gravityLeafsMap.entrySet()) {
            Character key = (Character) item.getKey();
            Integer value = (Integer) item.getValue();

            nodeList.add(new TreeLeaf(key.charValue(), value.intValue()));
        }
    }

    protected void generateHuffmanTree() {
        while (nodeList.size() > 1) {
            Collections.sort(nodeList, this.comparatorCodeGravity);
            nodeList.set(1, new TreeBiNode(nodeList.get(0), nodeList.get(1), comparatorCodeGravity));
            nodeList.remove(0);
            //System.out.println(nodeList); // todo test
        }
        this.rootNode = ((nodeList.isEmpty() && (nodeList.get(0) == null)))
                        ? null
                        : (ITreeBiNode) nodeList.get(0);
    }

    public ITreeBiNode getRoot() {
        if (this.rootNode == null) {
            reset();
        }
        return this.rootNode;
    }

    public void reset() {
        initCollectionOfLeaf();
        generateHuffmanTree();
    }

    public void toStringNode(ITreeBiNode node, int shift) {
        if (node != null) {
            toStringNode((ITreeBiNode) node.getLeftSink(), shift + 10);

            for (int i = 0; i < shift; i++) {
                System.out.print(" ");
            }

            System.out.println(node.toString());

            toStringNode((ITreeBiNode) node.getRightSink(), shift + 10);
        }
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

        System.out.println(f.gravityLeafsMap);

        f.initCollectionOfLeaf();

        System.out.println(f.nodeList);

        TreeLeaf l1 = new TreeLeaf('u', 11111);
        System.out.println(f.nodeList.contains(l1));

        TreeBiNode n1 = new TreeBiNode(new TreeLeaf('t', 6445), new TreeLeaf('p', 879), CodeGravityComparator.getInstance());
        System.out.println(f.nodeList.contains(n1));

        f.nodeList.add(new TreeBiNode(new TreeLeaf('t', 6445), new TreeLeaf('p', 879), CodeGravityComparator.getInstance()));

        Collections.sort(f.nodeList, compCodeGravity);
        System.out.println(f.nodeList);

        TreeBiNode n = (TreeBiNode) f.nodeList.get(4);
        System.out.println(n.getLeftSink());

        TreeLeaf leaf = (TreeLeaf) f.nodeList.get(0);
        System.out.println(leaf.getSignification());

        System.out.println("///////////////////////");

        ITreeBiNode node = f.getRoot();

        System.out.println("////////////////////////////////////////////////////////");
        f.toStringNode(f.getRoot(), 0);

    }
}
