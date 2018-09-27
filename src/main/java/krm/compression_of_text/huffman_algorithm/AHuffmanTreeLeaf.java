package krm.compression_of_text.huffman_algorithm;


public abstract class AHuffmanTreeLeaf extends ATreeGravity implements ITreeLeaf {

    protected char signification;

    public AHuffmanTreeLeaf(char signification, int gravity) {
        super(gravity);
        this.signification = signification;
    }

    public Character getSignification() {
        return this.signification;
    }
}
