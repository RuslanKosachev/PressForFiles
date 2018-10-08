package krm.gui;

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
    JTextComponent dropLabel;
    JLabel messageLabel;

    public FileDropHandler(JTextComponent dropLabel) {
        super();
        this.dropLabel = dropLabel;
    }

    public FileDropHandler(JTextComponent dropLabel, JLabel messageLabel) {
        this(dropLabel);
        this.messageLabel = messageLabel;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        //support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
        for (DataFlavor flavor : support.getDataFlavors()) {
            if (flavor.isFlavorJavaFileListType()) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean importData(TransferSupport support) {
        if (Objects.nonNull(messageLabel)) {
            messageLabel.setText("");
        }

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
            if (Objects.nonNull(messageLabel)) {
                messageLabel.setText(e.getMessage());
            }
            e.printStackTrace();
        }

        return super.importData(support);
    }
}