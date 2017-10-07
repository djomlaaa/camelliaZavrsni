package camellia.brankovic.mladen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FFunctionDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private ByteArrayDisplay beforeFFunction;
    private ByteArrayDisplay afterFFunction;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton sBox1;
    private JButton button6;
    private JButton button7;
    private JButton button8;

    public FFunctionDialog(int index) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        sBox1.addActionListener(e -> {
           JDialog jd = new byteArrayDialog(CamelliaAlgorithm.getSbox11110());
           jd.pack();
            Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
            jd.setBounds(screenDimension.width / 2 - jd.getWidth()/2,screenDimension.height/2-jd.getHeight(),jd.getWidth(),jd.getHeight());
        jd.setVisible(true);
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    }
