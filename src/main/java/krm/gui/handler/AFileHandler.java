package krm.gui.handler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class AFileHandler implements ActionListener {

    private JTextField pathFileField;
    private JTextField messageLabel = null;
    private String message = "";

    public AFileHandler(JTextField pathFileField) {
        this.pathFileField = pathFileField;
    }

    public AFileHandler(JTextField pathFileField, JTextField messageLabel) {
        this(pathFileField);
        this.messageLabel = messageLabel;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            File inFile = new File(pathFileField.getText());

            if (!(inFile.exists())) {
                throw new IOException("неправильный путь");
            }
            if (!(inFile.canRead())) {
                throw new IOException("нет доступа");
            }

            toHandler(inFile);

            this.message = "выполненно";

        } catch (Exception ex) {
            this.message = "ошибка" + " - " + ex.getMessage() + " - " + ex.toString();
            ex.printStackTrace();
        } finally {
            if (Objects.nonNull(messageLabel)) {
                messageLabel.setText(this.message);
            }
        }
    }

    protected abstract void toHandler(File inFile) throws Exception;
}
