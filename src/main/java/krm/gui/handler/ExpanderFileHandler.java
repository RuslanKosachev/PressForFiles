package krm.gui.handler;

import krm.compression_of_text.compressor.FileExpander;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class ExpanderFileHandler extends AFileHandler implements ActionListener {

    public ExpanderFileHandler(JTextField pathFileField) {
        super(pathFileField);
    }

    public ExpanderFileHandler(JTextField pathFileField, JLabel messageLabel) {
       super(pathFileField, messageLabel);
    }

    protected void toHandler(File inFile) throws IOException, ClassNotFoundException {
        FileExpander expander = new FileExpander(inFile);
        expander.start();
    }
}
