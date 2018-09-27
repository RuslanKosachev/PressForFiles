package krm.compression_of_text.huffman_algorithm;


import java.util.Comparator;

public abstract class AbstractTreeBiNode<T extends ITreeGravity> extends AbstractTreeGravity implements ITreeBiNode {

    protected T leftSink = null;
    protected T rightSink = null;

    public AbstractTreeBiNode(T leftSink, T rightSink, Comparator<ITreeGravity> comparator) {
        super(leftSink.getGravity() + rightSink.getGravity());

        if (comparator.compare(leftSink, rightSink) < 0) {
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
