package krm.compression_of_text.huffman_algorithm;


public abstract class AbstractTreeBiNode extends AbstractTreeGravity implements ITreeBiNode {
    public AbstractTreeBiNode(int gravity) {
        super(gravity);
    }

    public ITreeGravity getLeftSink() {
        return null;
    }

    public ITreeGravity getRightSink() {
        return null;
    }

}
