package krm.compression_of_text.huffman_algorithm;


import java.io.Serializable;

public abstract class AHuffmanTreeLeaf extends ATreeGravity implements ITreeLeaf, Serializable {

    protected char signification;

    public AHuffmanTreeLeaf(char signification, int gravity) {
        super(gravity);
        this.signification = signification;
    }

    public Character getSignification() {
        return this.signification;
    }
}
