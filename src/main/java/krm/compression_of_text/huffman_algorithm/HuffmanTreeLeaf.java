package krm.compression_of_text.huffman_algorithm;


import java.io.Serializable;

public class HuffmanTreeLeaf extends AHuffmanTreeLeaf implements IHuffmanTree, Serializable {

    public HuffmanTreeLeaf(char signification, int gravity) {
        super(signification, gravity);
    }

    public IBiTree getLeftSink() {
        return null;
    }

    public IBiTree getRightSink() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HuffmanTreeLeaf leaf = (HuffmanTreeLeaf) o;

        return this.signification == leaf.getSignification();
    }

    @Override
    public String toString() {
        return String.valueOf("Leaf->" + this.getGravity() + "=" + this.getSignification());
    }
}
