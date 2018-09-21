package krm.compression_of_text.huffman_tree;


public class Leaf extends CodeGravity {

    public char unit = 0;

    public void setGravity(int gravity) {
        this.gravity = gravity;
        this.visitGravity();
    }

    public void addGravity(int gravity) {
        setGravity(this.gravity + gravity);
        this.visitGravity();
    }

    protected void visitGravity() {
        if (source != null) {
            source.visitGravity();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Leaf leaf = (Leaf) o;

        return unit == leaf.unit;
    }

    @Override
    public int hashCode() {
        return (int) unit;
    }
}
