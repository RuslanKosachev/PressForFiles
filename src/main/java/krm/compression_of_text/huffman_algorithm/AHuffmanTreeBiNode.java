package krm.compression_of_text.huffman_algorithm;


import java.io.Serializable;
import java.util.Comparator;

public abstract class AHuffmanTreeBiNode<T extends ITreeGravity> extends ATreeGravity implements ITreeBiNode, Serializable {

    protected T leftSink = null;
    protected T rightSink = null;

    public AHuffmanTreeBiNode(T leftSink, T rightSink, Comparator<ITreeGravity> comparator) {
        super(leftSink.getGravity() + rightSink.getGravity());

        if (comparator.compare(leftSink, rightSink) <= 0) {
            this.leftSink =  leftSink;
            this.rightSink =  rightSink;
        } else {
            this.leftSink =  rightSink;
            this.rightSink =  leftSink;
        }
    }

    public T getLeftSink() {
        return this.leftSink;
    }

    public T getRightSink() {
        return this.rightSink;
    }

}
