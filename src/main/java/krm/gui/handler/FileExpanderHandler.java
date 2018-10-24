package krm.gui.handler;

import krm.compression_of_text.huffman_algorithm.BuilderHuffmanTree;
import krm.compression_of_text.huffman_algorithm.FileCompressorByCharacter;
import krm.compression_of_text.huffman_algorithm.FileExpanderByCharacters;
import krm.compression_of_text.huffman_algorithm.TreeNodeComparator;
import krm.exception.CompressionException;

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
        BuilderHuffmanTree<Character> builderTree = new BuilderHuffmanTree<>(TreeNodeComparator.getInstance());
        FileExpanderByCharacters expander = new FileExpanderByCharacters(inFile, builderTree);
        expander.perform();
    }
}
