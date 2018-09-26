package krm.compression_of_text.huffman_algorithm;


import java.util.Comparator;

public class TreeBiNode extends AbstractTreeBiNode implements IBiTree {

    public TreeBiNode(ITreeGravity leftSink, ITreeGravity rightSink, Comparator<ITreeGravity> comparator) {
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
