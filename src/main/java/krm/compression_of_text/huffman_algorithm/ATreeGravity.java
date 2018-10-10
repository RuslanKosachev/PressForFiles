package krm.compression_of_text.huffman_algorithm;

import java.io.Serializable;

public abstract class ATreeGravity implements ITreeGravity, Serializable{

    private static final long serialVersionUID = 8683452581122892188L;

    protected int gravity = 0;

    public int getGravity() {
        return this.gravity;
    }

    public ATreeGravity(int gravity) {
        this.gravity = gravity;
    }

}
