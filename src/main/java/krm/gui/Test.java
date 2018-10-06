package krm.gui;

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

                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
                        || support.isDataFlavorSupported(DataFlavor.stringFlavor);
            }

            @Override
            @SuppressWarnings("unchecked")
            public boolean importData(TransferSupport support) {

                Transferable t = support.getTransferable();
                try {

                    if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {

                        Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

                        List<File> files = (List<File>) o;


                        StringBuilder sb = new StringBuilder("<ul>");
                        for (File file : files)
                            sb.append("<li>" + file);

                        label.setText("<html>" + files.size() +
                                " files dropped<br>" + sb);
                    }
                    else if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {

                        Object o = t.getTransferData(DataFlavor.stringFlavor);
                        String str = o.toString();

                        label.setText(
                                "<html>Hell now how many files you trying to drop..<br>" +
                                        str);
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