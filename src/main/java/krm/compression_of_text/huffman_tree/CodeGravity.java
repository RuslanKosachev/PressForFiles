package krm.compression_of_text.huffman_tree;


public abstract class CodeGravity implements Comparable<CodeGravity> {

    protected CodeGravity source = null;
    protected int gravity = 0;

    public int getGravity() {
        return gravity;
    }

    protected abstract void visitGravity();

    public int compareTo(CodeGravity codeGravity) {
        if (this.gravity < codeGravity.getGravity()) {
            return -1;
        } else if (this.gravity > codeGravity.getGravity()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(gravity);
    }

}
