package gui.handler;

import compression.huffman_algorithm.BuilderHuffmanTree;
import compression.huffman_algorithm.FileExpanderByCharacters;
import compression.huffman_algorithm.NodeComparator;
import compression.exception.CompressionException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

public class FileExpanderHandler extends FileHandlerAbstract implements ActionListener {

    public FileExpanderHandler(JTextField pathFileField) {
        super(pathFileField);
    }

    public FileExpanderHandler(JTextField pathFileField, JTextField messageLabel) {
       super(pathFileField, messageLabel);
    }

    protected void toHandler(File inFile) throws CompressionException {
        BuilderHuffmanTree<Character> builderTree = new BuilderHuffmanTree<>(NodeComparator.getInstance());
        FileExpanderByCharacters expander = new FileExpanderByCharacters(inFile, builderTree);
        expander.perform();
    }
}
