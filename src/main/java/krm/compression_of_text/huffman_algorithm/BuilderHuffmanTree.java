package krm.compression_of_text.huffman_algorithm;

import java.util.*;

public class BuilderHuffmanTree<T> {

    protected Map<T, Integer> significationFrequency = new HashMap();
    protected ArrayList<HuffmanTree<T>> nodes = new ArrayList<>();
    protected Comparator<HuffmanTree> comparatorCodeGravity;
    protected HuffmanTree<T> rootNode = null;
    protected Map<T, List<Boolean>> codes = new TreeMap<>();

    BuilderHuffmanTree(Comparator<HuffmanTree> comparatorCodeGravity) {
        this.comparatorCodeGravity = comparatorCodeGravity;
    }

    public Map<T, Integer> getSignificationFrequency() {
        return significationFrequency;
    }

    public void addSignification(T signification) {
        int gravity = 1;
        if (significationFrequency.containsKey(signification)) {
            gravity += significationFrequency.get(signification);
        }
        significationFrequency.put(signification, gravity);
    }

    public HuffmanTree<T> getRootNode() {
        if (Objects.isNull(this.rootNode)) {
            resetHuffmanTree();
        }
        return this.rootNode;
    }

    public void resetHuffmanTree() {
        initCollectionOfLeaf();
        generateHuffmanTree();
    }

    public Map<T, List<Boolean>> getCodes() {
        if (codes.isEmpty()) {
            resetCodes();
        }
        return this.codes;
    }

    public void resetCodes() {
        this.initCodes(getRootNode());
    }

    protected void initCollectionOfLeaf() {
        nodes.clear();

        for (Map.Entry item : significationFrequency.entrySet()) {
            T key = (T) item.getKey();
            Integer value = (Integer) item.getValue();

            nodes.add(new HuffmanTree(value.intValue(), key));
        }
    }

    protected void generateHuffmanTree() {
        HuffmanTree newNode;
        while (nodes.size() > 1) {
            nodes.sort(this.comparatorCodeGravity);
            // новый узел из двух начальных элементов с удалением использованных элемементов
            newNode = new HuffmanTree<T>(nodes.get(0), nodes.get(1), comparatorCodeGravity);
            nodes.set(1, newNode);
            nodes.remove(0);
        }
        this.rootNode = ((nodes.isEmpty() && (nodes.get(0) == null)))
                        ? null
                        : nodes.get(0);
    }

    protected void initCodes(HuffmanTree<T> rootNode) {
        List<Boolean> code = new LinkedList<>();

        if (Objects.nonNull(rootNode.getLeftNode())) {
            code.add(false);
            initCodes(rootNode.getLeftNode());
        }
        if (Objects.nonNull(rootNode.getRightNode())) {
            code.add(true);
            initCodes(rootNode.getRightNode());
        }
        if (Objects.isNull(rootNode.getLeftNode()) && Objects.isNull(rootNode.getRightNode())) {
            this.codes.put(rootNode.getSignification(), new LinkedList<Boolean>(code));
        }
        if (!code.isEmpty()) {
            code.remove(code.size() - 1);
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

    public static int toPrintRootNode(HuffmanTree node, int shift) {
        if (node != null) {
            System.out.print(toPrintRootNode( node.getLeftNode(), shift + 10));

            for (int i = 0; i < shift; i++) {
                System.out.print(" ");
            }
            System.out.print(node.toString() + '\n');

            System.out.print(toPrintRootNode( node.getRightNode(), shift + 10));
        }
        return 1;
    }

    public void printSignificationFrequency() {
        System.out.println(significationFrequency.toString());
    }

    public void printRootNode() {
        toPrintRootNode(rootNode, 0);
    }

    public void printCodes() {
        System.out.println(codes.toString());
    }
}
