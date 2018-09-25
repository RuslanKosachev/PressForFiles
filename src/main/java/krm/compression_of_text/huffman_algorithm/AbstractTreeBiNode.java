package krm.compression_of_text.huffman_algorithm;


import java.util.Comparator;

public abstract class AbstractTreeBiNode extends AbstractTreeGravity implements ITreeBiNode {

    protected ITreeGravity leftSink = null;
    protected ITreeGravity rightSink = null;

    public AbstractTreeBiNode(ITreeGravity leftSink, ITreeGravity rightSink, Comparator<ITreeGravity> comparator) {
        super(leftSink.getGravity() + rightSink.getGravity());

        if (comparator.compare(leftSink, rightSink) < 0) {
            this.leftSink = leftSink;
            this.rightSink = rightSink;
        } else {
            this.leftSink = rightSink;
            this.rightSink = leftSink;
        }
    }

    public ITreeGravity getLeftSink() {
        return this.leftSink;
    }

    public ITreeGravity getRightSink() {
        return this.rightSink;
    }

}
