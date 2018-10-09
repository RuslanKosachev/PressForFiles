package krm.gui.test;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

public class Test {

    public static void main(String[] args) throws Exception {
        // JTextField textField1;
        final JTextField label = new JTextField("Drop stuff here", JLabel.CENTER);
        label.setTransferHandler(new TransferHandler(null) {

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
                        label.setText(String.valueOf(files.get(0)));
                    }
                    else if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        String str = t.getTransferData(DataFlavor.stringFlavor).toString();
                        label.setText(str);

                        File file = new File(str);

                    }
                } catch (UnsupportedFlavorException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return super.importData(support);
            }
        });

        JFrame f = new JFrame("DnD :: files");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.add(label);
        f.setVisible(true);
    }
}