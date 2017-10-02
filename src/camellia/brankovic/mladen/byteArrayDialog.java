package camellia.brankovic.mladen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class byteArrayDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private ByteArrayDisplay byteArrayDisplay;

    public byteArrayDialog(byte[] bytes) {
        this();
        byteArrayDisplay.SetByteArray(bytes);
    }
    public byteArrayDialog(int[] ints) {
        this();
        byteArrayDisplay.SetIntArray(ints);
    }
    public byteArrayDialog(boolean[] bits) {
        this();
        byteArrayDisplay.SetBitArray(bits);
    }
    private byteArrayDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
