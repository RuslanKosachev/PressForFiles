package krm.gui.handler;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class FileDropHandler extends TransferHandler {

    private JTextComponent dropLabel;
    private JTextField messageLabel;
    private String message = "";

    public FileDropHandler(JTextComponent dropLabel) {
        super();
        this.dropLabel = dropLabel;
    }

    public FileDropHandler(JTextComponent dropLabel, JTextField messageLabel) {
        this(dropLabel);
        this.messageLabel = messageLabel;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (support.getDataFlavors().length > 1) {
            return false;
        }
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
                || support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean importData(TransferSupport support) {

        Transferable t = support.getTransferable();

        try {
            if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                dropLabel.setText(String.valueOf(files.get(0)));
            } else if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String str = t.getTransferData(DataFlavor.stringFlavor).toString();
                dropLabel.setText(str);

                if (!(new File(str).exists())) {
                    throw new IOException("не является файлом");
                }
            }
        } catch (UnsupportedFlavorException | IOException e) {
            this.message = e.getMessage();
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(messageLabel)) {
                messageLabel.setText(this.message);
            }
        }

        return super.importData(support);
    }
}