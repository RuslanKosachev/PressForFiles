package krm.gui.handler;

import krm.compression_of_text.huffman_algorithm.BuilderHuffmanTree;
import krm.compression_of_text.huffman_algorithm.FileCompressorByCharacter;
import krm.compression_of_text.huffman_algorithm.TreeNodeComparator;
import krm.exception.CompressionException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class FileCompressHandler extends AFileHandler implements ActionListener {

    private JTextArea textArea;

    public FileCompressHandler(JTextField pathFileField) {
        super(pathFileField);
    }

    public FileCompressHandler(JTextField pathFileField, JTextField messageLabel) {
       super(pathFileField, messageLabel);
    }

    public FileCompressHandler(JTextField pathFileField, JTextField messageLabel, JTextArea textArea) {
        this(pathFileField, messageLabel);
        this.textArea = textArea;
    }

    protected void toHandler(File inFile) throws CompressionException {
        BuilderHuffmanTree<Character> builder = new BuilderHuffmanTree<>(TreeNodeComparator.getInstance());
        FileCompressorByCharacter compressor = new FileCompressorByCharacter(inFile, builder);
        compressor.perform();

        if (Objects.nonNull(textArea)) {
            textArea.setText(builder.toStringSignificationFrequency());
        }
    }
}
