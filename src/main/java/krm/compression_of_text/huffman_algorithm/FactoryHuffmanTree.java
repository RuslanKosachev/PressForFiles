package krm.compression_of_text.huffman_algorithm;

import java.util.*;

public class FactoryHuffmanTree {

    protected Map<Character, Integer> gravityMap = new HashMap();
    protected List<ITreeGravity> leafList = new ArrayList();

    Comparator<ITreeGravity> comparatorCodeGravity = null;

    ITreeBiNode rootNode = null;

    FactoryHuffmanTree(Comparator<ITreeGravity> comparatorCodeGravity) {
        this.comparatorCodeGravity = comparatorCodeGravity;
    }

    public void addWordGravity(char word) {
        int gravity = 1;
        if (gravityMap.containsKey(word)) {
            gravity += gravityMap.get(word);
        }
        gravityMap.put(word, gravity);
    }

    protected void initCollectionOfLeaf() {
        for (Map.Entry item : gravityMap.entrySet()) {
            Character key = (Character) item.getKey();
            Integer value = (Integer) item.getValue();

            leafList.add(new TreeLeaf(key.charValue(), value.intValue()));
        }
    }

    public void generateHuffmanTree() {
        TreeBiNode newNode = null;
        while (leafList.size() > 1) {
            Collections.sort(leafList, this.comparatorCodeGravity);
            newNode = new TreeBiNode(leafList.get(0), leafList.get(1), comparatorCodeGravity);
            leafList.remove(0);
            leafList.remove(0);
            leafList.add(newNode);
            System.out.println(leafList); // todo test
        }

        this.rootNode = (ITreeBiNode) leafList.get(0);
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

        System.out.println(f.gravityMap);

        f.initCollectionOfLeaf();

        System.out.println(f.leafList);

        TreeLeaf l1 = new TreeLeaf('u', 11111);
        System.out.println(f.leafList.contains(l1));

        TreeBiNode n1 = new TreeBiNode(new TreeLeaf('t', 6445), new TreeLeaf('p', 879), CodeGravityComparator.getInstance());
        System.out.println(f.leafList.contains(n1));

        f.leafList.add(new TreeBiNode(new TreeLeaf('t', 6445), new TreeLeaf('p', 879), CodeGravityComparator.getInstance()));

        Collections.sort(f.leafList, compCodeGravity);
        System.out.println(f.leafList);

        TreeBiNode n = (TreeBiNode) f.leafList.get(4);
        System.out.println(n.getLeftSink());

        TreeLeaf leaf = (TreeLeaf) f.leafList.get(0);
        System.out.println(leaf.getUnit());

        System.out.println("///////////////////////");

        ITreeBiNode node = f.getRoot();

        System.out.println("////////////////////////////////////////////////////////");
        f.toStringNode(f.getRoot(), 0);

    }
}
