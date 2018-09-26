package krm.compression_of_text.huffman_algorithm;


public abstract class AbstractTreeLeaf extends AbstractTreeGravity implements ITreeLeaf {

    protected char signification;

    public AbstractTreeLeaf(char signification, int gravity) {
        super(gravity);
        this.signification = signification;
    }

    public Character getSignification() {
        return this.signification;
    }
}
