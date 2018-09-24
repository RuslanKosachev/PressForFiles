package krm.compression_of_text.huffman_algorithm;


public abstract class  AbstractTreeLeaf extends AbstractTreeBiNode implements ITreeLeaf {
    public AbstractTreeLeaf(int gravity) {
        super(gravity);
    }

    public char getUnit() {
        return 0;
    }

}
