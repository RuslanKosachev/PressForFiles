package krm.compression_of_text.huffman_algorithm;


public class TreeLeaf extends AbstractTreeLeaf implements IBiTree, ITreeHuffman {

    public TreeLeaf(char signification, int gravity) {
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

        TreeLeaf leaf = (TreeLeaf) o;

        return this.signification == leaf.getSignification();
    }

    @Override
    public String toString() {
        return String.valueOf("Leaf->" + this.getGravity() + "=" + this.getSignification());
    }
}
