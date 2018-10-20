package krm.gui.handler;

import krm.compression_of_text.huffman_algorithm.FileExpanderByCharacters;
import krm.exception.CompressionException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

public class FileExpanderHandler extends AFileHandler implements ActionListener {

    public FileExpanderHandler(JTextField pathFileField) {
        super(pathFileField);
    }

    public FileExpanderHandler(JTextField pathFileField, JTextField messageLabel) {
       super(pathFileField, messageLabel);
    }

    protected void toHandler(File inFile) throws CompressionException {
        FileExpanderByCharacters expander = new FileExpanderByCharacters(inFile);
        expander.perform();
    }
}
