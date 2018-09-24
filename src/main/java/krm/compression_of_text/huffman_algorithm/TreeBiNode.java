package krm.compression_of_text.huffman_algorithm;


import java.util.Comparator;

public class TreeBiNode extends AbstractTreeLeaf implements ITreeBiNode {

    protected ITreeGravity leftSink = null;
    protected ITreeGravity rightSink = null;

    public ITreeGravity getLeftSink() {
        return this.leftSink;
    }

    public ITreeGravity getRightSink() {
        return this.rightSink;
    }

    public TreeBiNode(ITreeGravity leftSink, ITreeGravity rightSink, Comparator<ITreeGravity> comparator) {

        super(leftSink.getGravity() + rightSink.getGravity());

        if (comparator.compare(leftSink,rightSink) < 0) {
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

}
