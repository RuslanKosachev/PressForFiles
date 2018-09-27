package krm.compression_of_text.huffman_algorithm;


public abstract class AbstractTreeGravity implements ITreeGravity {

    protected int gravity = 0;

    protected AbstractTreeGravity() {
    }

    public int getGravity() {
        return this.gravity;
    }

    public AbstractTreeGravity(int gravity) {
        this.gravity = gravity;
    }
}
