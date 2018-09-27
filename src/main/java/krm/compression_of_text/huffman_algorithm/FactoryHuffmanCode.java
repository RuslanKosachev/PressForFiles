package krm.compression_of_text.huffman_algorithm;


import java.util.*;

public class FactoryHuffmanCode extends FactoryHuffmanTree {

    protected List<Boolean> code = new LinkedList();
    protected Map<Character, List<Boolean>> codes = new TreeMap();

    FactoryHuffmanCode(Comparator<ITreeGravity> comparatorCodeGravity) {
        super(comparatorCodeGravity);
    }

    public Map getCodes() {
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
        if (code.isEmpty()) {
            int i = code.size() - 1;
            this.code.remove(i);
        }
    }



    // todo test
    public static void main(String[] args) {
        FactoryHuffmanTree f = new FactoryHuffmanTree(CodeGravityComparator.getInstance());
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('e');
        f.addWordGravity('k');
        f.addWordGravity('p');
        f.addWordGravity('p');
        f.addWordGravity('p');
        f.addWordGravity('p');
        f.addWordGravity('p');
        f.addWordGravity('p');
        f.addWordGravity('p');
        f.addWordGravity('r');
        f.addWordGravity('r');
        f.addWordGravity('r');
        f.addWordGravity('u');
        f.addWordGravity('u');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');
        f.addWordGravity('i');

        f.toStringNode(f.getRootNode(), 0);

        FactoryHuffmanCode  factoryHuffmanCode = new FactoryHuffmanCode(CodeGravityComparator.getInstance());

        System.out.println(f.toString());
        System.out.println("------------------- initCodes ---------------------------");
        factoryHuffmanCode.initCodes(f.getRootNode());
        System.out.println("------------------------ codes ----------------------");
        System.out.println(factoryHuffmanCode.codes);

    }
}
