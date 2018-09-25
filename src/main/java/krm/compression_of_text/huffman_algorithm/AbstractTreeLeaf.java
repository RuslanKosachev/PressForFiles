package krm.compression_of_text.huffman_algorithm;


public abstract class AbstractTreeLeaf extends AbstractTreeGravity implements ITreeLeaf {

    protected char unit;

    public AbstractTreeLeaf(char unit, int gravity) {
        super(gravity);
        this.unit = unit;
    }

    public char getUnit() {
        return this.unit;
    }
}
