package krm.compression_of_text.huffman_tree;


public class Node extends CodeGravity {

    protected CodeGravity leftSink = null;
    protected CodeGravity rightSink = null;

    public CodeGravity getLeftSink() {
        return this.leftSink;
    }

    public CodeGravity getRightSink() {
        return this.rightSink;
    }

    protected void visitGravity() {
        updateGravity();
        if (source != null) {
            source.visitGravity();
        }
    }

    public Node() {}

    public Node(CodeGravity leftSink, CodeGravity rightSink) {
        updateGravity(leftSink, rightSink);
        this.leftSink = leftSink;
        this.rightSink = rightSink;
        leftSink.source = this;
        rightSink.source = this;
    }

    private void updateGravity(CodeGravity leftSink, CodeGravity rightSink) {
        super.gravity = leftSink.getGravity() + rightSink.getGravity();
    }

    private void updateGravity() {
        updateGravity(this.leftSink, this.rightSink);
    }

    // test Node
    public static void main(String[] args) {
        Leaf l1 = new Leaf();
        l1.setGravity(1);
        System.out.println("l1 - " + l1);

        Leaf l2 = new Leaf();
        l2.setGravity(3);
        System.out.println("l2 - " + l2);

        Leaf l3 = new Leaf();
        l3.setGravity(5);
        System.out.println("l3 - " + l3);

        Node n1 = new Node(l1, l2);
        System.out.println("n1 - " + n1);

        Node n2 = new Node(n1, l3);
        System.out.println("n2 - " + n2);

        l3.setGravity(6);
        System.out.println("l3 - " + l3);
        System.out.println("n2 - " + n2);
    }
}
