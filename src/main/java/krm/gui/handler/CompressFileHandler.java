package krm.gui.handler;

import krm.compression_of_text.compressor.FileCompressor;
import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CompressFileHandler extends AFileHandler implements ActionListener {

    private JTextArea textArea1;

    public CompressFileHandler(JTextField pathFileField) {
        super(pathFileField);
    }

    public CompressFileHandler(JTextField pathFileField, JLabel messageLabel) {
       super(pathFileField, messageLabel);
    }

    public CompressFileHandler(JTextField pathFileField, JLabel messageLabel, JTextArea textArea1) {
        this(pathFileField, messageLabel);
        this.textArea1 = textArea1;
    }

    protected void toHandler(File inFile) throws IOException {
        FactoryHuffmanCode f = new FactoryHuffmanCode(CodeGravityComparator.getInstance());

        FileCompressor compressor = new FileCompressor(inFile, f);
        compressor.start();

        textArea1.setText(f.toStringGravityLeafs());
    }
}
