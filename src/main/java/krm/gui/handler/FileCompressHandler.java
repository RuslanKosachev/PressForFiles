package krm.gui.handler;

import krm.compression_of_text.compressor.FileCompressor;
import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileCompressHandler extends AFileHandler implements ActionListener {

    private JTextArea textArea;

    public FileCompressHandler(JTextField pathFileField) {
        super(pathFileField);
    }

    public FileCompressHandler(JTextField pathFileField, JLabel messageLabel) {
       super(pathFileField, messageLabel);
    }

    public FileCompressHandler(JTextField pathFileField, JLabel messageLabel, JTextArea textArea1) {
        this(pathFileField, messageLabel);
        this.textArea = textArea1;
    }

    protected void toHandler(File inFile) throws IOException {
        FactoryHuffmanCode f = new FactoryHuffmanCode(CodeGravityComparator.getInstance());

        FileCompressor compressor = new FileCompressor(inFile, f);
        compressor.start();

        textArea.setText(f.toStringGravityLeafs());
    }
}
