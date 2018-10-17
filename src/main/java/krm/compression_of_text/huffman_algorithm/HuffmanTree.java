package krm.compression_of_text.huffman_algorithm;

import java.io.Serializable;
import java.util.Comparator;

public class HuffmanTree<T> implements Serializable{

    private static final long serialVersionUID = 8683452581122892188L;

    protected T signification;
    protected transient int frequencies;

    protected HuffmanTree leftNode;
    protected HuffmanTree rightNode;

    public HuffmanTree(int gravity, T signification) {
        this.frequencies = gravity;
        this.signification = signification;
    }

    public HuffmanTree(HuffmanTree<T> leftSink, HuffmanTree<T> rightSink,
                       Comparator<HuffmanTree> comparator) {
        this.frequencies = leftSink.getFrequencies() + rightSink.getFrequencies();

        if (comparator.compare(leftSink, rightSink) <= 0) {
            this.leftNode =  leftSink;
            this.rightNode =  rightSink;
        } else {
            this.leftNode =  rightSink;
            this.rightNode =  leftSink;
        }
    }

    public int getFrequencies() {
        return this.frequencies;
    }

    public HuffmanTree getLeftNode() {
        return this.leftNode;
    }

    public HuffmanTree getRightNode() {
        return this.rightNode;
    }

    public T getSignification() {
        return this.signification;
    }
}
