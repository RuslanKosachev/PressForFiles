package krm.gui.handler;

import krm.compression_of_text.compressor.FileCompressor;
import krm.compression_of_text.huffman_algorithm.CodeGravityComparator;
import krm.compression_of_text.huffman_algorithm.FactoryHuffmanCode;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class CompressFileHandler extends AFileHandler implements ActionListener {

    public CompressFileHandler(JTextField pathFileField) {
        super(pathFileField);
    }

    public CompressFileHandler(JTextField pathFileField, JLabel messageLabel) {
       super(pathFileField, messageLabel);
    }

    protected void toHandler(File inFile) throws IOException {
        FileCompressor compressor = new FileCompressor(inFile,
                new FactoryHuffmanCode(CodeGravityComparator.getInstance()));
        compressor.start();
    }
}
