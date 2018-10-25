package gui.handler;

import compression.exception.CompressionException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public abstract class FileHandlerAbstract implements ActionListener {

    private JTextField pathFileField;
    private JTextField messageLabel;
    private String message = "";

    public FileHandlerAbstract(JTextField pathFileField) {
        this.pathFileField = pathFileField;
    }

    public FileHandlerAbstract(JTextField pathFileField, JTextField messageLabel) {
        this(pathFileField);
        this.messageLabel = messageLabel;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            File inFile = new File(pathFileField.getText());

            toHandler(inFile);

            this.message = "выполненно";

        } catch (CompressionException ex) {
            this.message = "ошибка" + " - " + ex.getErrorCode().getErrorMessage();
            ex.getCause().printStackTrace();
        } finally {
            if (Objects.nonNull(messageLabel)) {
                messageLabel.setText(message);
            }
        }
    }

    protected abstract void toHandler(File inFile) throws CompressionException;
}
