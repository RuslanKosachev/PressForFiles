package krm.gui.handler;

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

    protected void toHandler(File inFile) throws Exception {
        FileExpander expander = new FileExpander(inFile);
        expander.start();
    }
}
