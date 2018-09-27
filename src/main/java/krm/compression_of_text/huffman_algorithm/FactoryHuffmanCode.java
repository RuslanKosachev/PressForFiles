package krm.compression_of_text.huffman_algorithm;


import java.util.*;

public class FactoryHuffmanCode extends FactoryHuffmanTree {

    protected List<Boolean> code = new LinkedList();
    protected Map<Character, List<Boolean>> codes = new TreeMap();

    FactoryHuffmanCode(Comparator<ITreeGravity> comparatorCodeGravity) {
        super(comparatorCodeGravity);
    }

    public Map getCodes() {
        if (this.codes == null) {
            reset();
        }
        return this.codes;
    }

    //public void reset() {
    //    initCodes();
    //}

    /*
    vector<bool> code;
    map<char,vector<bool> > table;

    void BuildTable(Node *root)
    {
        if (root->left!=NULL) {
                code.push_back(0);
                BuildTable(root->left);
        }
        if (root->right!=NULL) {
                code.push_back(1);
                BuildTable(root->right);
        }
        if (root->left==NULL && root->right==NULL)  {
                table[root->c]=code;
            }
        code.pop_back();
    }*/
    protected void initCodes(IBiTree node) {
        if (node.getLeftSink() != null) {
            this.code.add(false);
            initCodes((IBiTree) node.getLeftSink());
        }
        if (node.getRightSink() != null) {
            this.code.add(true);
            initCodes((IBiTree) node.getRightSink());
        }
        if ((node.getLeftSink() != null) && (node.getRightSink() != null)) {
            this.codes.put(node.getSignification(), code);
        }
        this.code.remove(code.size() - 1);
    }
}
