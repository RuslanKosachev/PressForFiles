package krm.gui.handler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class AFileHandler implements ActionListener {

    JTextField pathFileField;
    JLabel messageLabel;

    public AFileHandler(JTextField pathFileField) {
        this.pathFileField = pathFileField;
    }

    public AFileHandler(JTextField pathFileField, JLabel messageLabel) {
        this(pathFileField);
        this.messageLabel = messageLabel;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            File inFile = new File(pathFileField.getText());

            if (!(inFile.exists())) {
                throw new IOException("не является файлом");
            }
            if (!(inFile.canRead())) {
                throw new IOException("нет доступа");
            }

            toHandler(inFile);

            if (Objects.nonNull(messageLabel)) {
                messageLabel.setText("выполненно");
            }
        } catch (Exception ex) {
            if (Objects.nonNull(messageLabel)) {
                messageLabel.setText("ошибка" + " - " + ex.getMessage() + " - " + ex.toString());
            }
            ex.printStackTrace();
        }
    }

    protected abstract void toHandler(File inFile) throws Exception;
}
