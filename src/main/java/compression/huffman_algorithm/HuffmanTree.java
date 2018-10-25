package compression.huffman_algorithm;

import java.util.Comparator;
import java.util.Objects;

public class HuffmanTree<T> {

    private T signification;
    private int frequencies;

    private HuffmanTree leftNode;
    private HuffmanTree rightNode;

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

    @Override
    public String toString(){
        if (Objects.nonNull(signification)) {
            return  signification.toString() + "=" + frequencies;
        }
        return String.valueOf(frequencies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HuffmanTree<T> that = (HuffmanTree<T>) o;

        if (frequencies != that.frequencies) return false;
        return signification.equals(that.signification);
    }
}
