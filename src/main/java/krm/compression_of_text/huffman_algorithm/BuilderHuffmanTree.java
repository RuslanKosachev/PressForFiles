package krm.compression_of_text.huffman_algorithm;

import java.util.*;

public class BuilderHuffmanTree<T> {

    private static final int INCREMENT = 1;

    private Comparator<HuffmanTree> comparatorCodeGravity;

    private Map<T, Integer> significationFrequency = new HashMap();
    private ArrayList<HuffmanTree<T>> nodes = new ArrayList<>();
    private HuffmanTree<T> rootNode;

    private Map<T, List<Boolean>> codes = new TreeMap<>();
    private LinkedList<Boolean> code = new LinkedList<>();

    public BuilderHuffmanTree(Comparator<HuffmanTree> comparatorCodeGravity) {
        this.comparatorCodeGravity = comparatorCodeGravity;
    }

    public void setSignificationFrequency(Map<T, Integer> significationFrequency) {
        this.significationFrequency = significationFrequency;
    }

    public Map<T, Integer> getSignificationFrequency() {
        return significationFrequency;
    }

    public void addSignification(T signification) {
        int frequency = INCREMENT;
        if (significationFrequency.containsKey(signification)) {
            frequency += significationFrequency.get(signification);
        }
        significationFrequency.put(signification, frequency);
    }

    public HuffmanTree<T> getRootNode() {
        if (Objects.isNull(rootNode)) {
            resetHuffmanTree();
        }
        return rootNode;
    }

    public void resetHuffmanTree() {
        initCollectionOfLeaf();
        generateHuffmanTree();
    }

    public Map<T, List<Boolean>> getCodes() {
        if (codes.isEmpty()) {
            resetCodes();
        }
        return codes;
    }

    public void resetCodes() {
        initCodes(getRootNode());
    }

    private void initCollectionOfLeaf() {
        nodes.clear();

        for (Map.Entry item : significationFrequency.entrySet()) {
            T key = (T) item.getKey();
            Integer value = (Integer) item.getValue();

            nodes.add(new HuffmanTree(value.intValue(), key));
        }
    }

    private void generateHuffmanTree() {
        HuffmanTree newNode;
        while (nodes.size() > 1) {
            nodes.sort(comparatorCodeGravity);
            // новый узел из двух начальных элементов с удалением использованных элемементов
            newNode = new HuffmanTree<>(nodes.get(0), nodes.get(1), comparatorCodeGravity);
            nodes.set(1, newNode);
            nodes.remove(0);
        }
        rootNode = ((nodes.isEmpty() && (Objects.isNull(nodes.get(0)))))
                ? null
                : nodes.get(0);
    }

    private void initCodes(HuffmanTree<T> rootNode) {
        if (Objects.nonNull(rootNode.getLeftNode())) {
            code.add(false);
            initCodes(rootNode.getLeftNode());
        }
        if (Objects.nonNull(rootNode.getRightNode())) {
            code.add(true);
            initCodes(rootNode.getRightNode());
        }
        if (Objects.isNull(rootNode.getLeftNode()) && Objects.isNull(rootNode.getRightNode())) {
            codes.put(rootNode.getSignification(), (List<Boolean>) code.clone());
        }
        if (!code.isEmpty()) {
            code.removeLast();
        }
    }

    public String toStringSignificationFrequency() {
        StringBuilder result = new StringBuilder("symbol frequencies: \n");
        int sum = 0;

        for (Map.Entry item : significationFrequency.entrySet()) {
            Character key = (Character) item.getKey();
            Integer value = (Integer) item.getValue();

            result.append("{" + key.charValue() + "} = " + value.intValue() + "\n");

            sum += value.intValue();
        }

        result.append("total characters: ");
        result.append( sum);

        return String.valueOf(result);
    }

    public static void toPrintRootNode(HuffmanTree node, int shift) {
        if (node != null) {
            toPrintRootNode( node.getLeftNode(), shift + 4);

            for (int i = 0; i < shift; i++) {
                System.out.print(" ");
            }
            System.out.println(node.toString());

           toPrintRootNode( node.getRightNode(), shift + 4);
        }
    }

    public void printSignificationFrequency() {
        System.out.println(significationFrequency.toString());
    }

    public void printRootNode() {
        toPrintRootNode(rootNode, 0);
    }

    public void printCodes() {
        System.out.println(codes);
    }
}
