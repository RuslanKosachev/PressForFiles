package krm.compression_of_text.huffman_algorithm;


import java.util.Comparator;

    public class HuffmanTreeBiNode<T extends ITreeGravity> extends AHuffmanTreeBiNode implements IHuffmanTree {

    public HuffmanTreeBiNode(T leftSink, T rightSink, Comparator<ITreeGravity> comparator) {
        super(leftSink, rightSink, comparator);

        if (comparator.compare(leftSink, rightSink) < 0) {
            this.leftSink = leftSink;
            this.rightSink = rightSink;
        } else {
            this.leftSink = rightSink;
            this.rightSink = leftSink;
        }
    }

    @Override
    public String toString() {
        return String.valueOf("Node->" + super.getGravity());
    }

    public Character getSignification() {
        return null;
    }
}
