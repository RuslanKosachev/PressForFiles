package krm.compression_of_text.huffman_algorithm;


public abstract class AbstractTreeGravity implements ITreeGravity {

    protected int gravity = 0;

    public int getGravity() {
        return gravity;
    }

    public AbstractTreeGravity(int gravity) {
        this.gravity = gravity;
    }

    /*@Override
    public String toString() {
        return String.valueOf("CodeGravity->" + this.gravity);
    }*/

}
