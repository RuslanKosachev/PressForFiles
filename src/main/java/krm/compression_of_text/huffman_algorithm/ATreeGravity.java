package krm.compression_of_text.huffman_algorithm;


public abstract class ATreeGravity implements ITreeGravity {

    protected int gravity = 0;

    protected ATreeGravity() {
    }

    public int getGravity() {
        return this.gravity;
    }

    public ATreeGravity(int gravity) {
        this.gravity = gravity;
    }
}
