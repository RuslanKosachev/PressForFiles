package krm.compression_of_text.huffman_algorithm;


public class TreeLeaf extends AbstractTreeLeaf implements ITreeLeaf {

    protected char unit = 0;

    public char getUnit() {
        return this.unit;
    }

    public TreeLeaf(char unit, int gravity) {
        super(gravity);
        this.unit = unit;
    }

    public ITreeGravity getLeftSink() {
        return null;
    }

    public ITreeGravity getRightSink() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeLeaf leaf = (TreeLeaf) o;

        return this.unit == leaf.getUnit();
    }

    @Override
    public String toString() {
        return String.valueOf("Leaf->" + this.getGravity() + "=" + this.getUnit());
    }

}
