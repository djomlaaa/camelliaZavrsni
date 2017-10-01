package camellia.brankovic.mladen;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CamelliaVisualisation implements ByteArrayDisplay.MoseHoverOnByteListener {

    private JPanel MainPanel;
    private ByteArrayDisplay initialKey;
    private JButton previousRound;
    private JLabel roundLabel;
    private JButton nextRound;
    private JTabbedPane tabbedPane;
    private ByteArrayDisplay rotatingKey;

    private int currentOperation;
private static final int OPERATION_COUNT = 2;
    private int[] stepCount = new int[]{4,10};
    private int[] roundIndex = new int[OPERATION_COUNT];


    public CamelliaVisualisation() {
        initialKey.SetByteArray(CamelliaAlgorithm.initialKey);
rotatingKey.AddMoseHoverOnByteListener(this);
initialKey.AddMoseHoverOnByteListener(this);
        //UpdateRound();

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                currentOperation = tabbedPane.getSelectedIndex();
                UpdateRound();
            }
        });

        previousRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roundIndex[currentOperation] > 0) {
                    roundIndex[currentOperation]--;
                    UpdateRound();
                }
            }
        });
        nextRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roundIndex[currentOperation] < stepCount[currentOperation]-1) {
                    roundIndex[currentOperation]++;
                    UpdateRound();
                }
            }
        });

    }

    private void UpdateRound() {
        roundLabel.setText("Runda: " + String.valueOf(roundIndex[currentOperation] +1));
        switch (currentOperation) {
            case 0:
                initialKey.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.xoringKeyA[roundIndex[currentOperation]]));
                break;
            case 1:
                rotatingKey.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.rotatingKey[roundIndex[currentOperation]]));
                break;
        }
    }
    private byte[] IntArrayToByteArray(int[] intArray) {
        byte[] byteArray = new byte[intArray.length];
        for (int i = 0; i < intArray.length;i++) {
            byteArray[i] = (byte)intArray[i];
        }
        return byteArray;
    }


    public static void main(String[] args) {
        byte[] plain = {(byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67,(byte) 0x89, (byte)0xab, (byte)0xcd, (byte)0xef, (byte)0xfe,(byte) 0xdc, (byte) 0xba,(byte) 0x98,(byte) 0x76,(byte) 0x54,(byte) 0x32,(byte) 0x10};
        byte[] key = {(byte)0x01, (byte)0x23, (byte)0x45, (byte)0x67,(byte) 0x89, (byte)0xab, (byte)0xcd, (byte)0xef, (byte)0xfe,(byte) 0xdc, (byte) 0xba,(byte) 0x98,(byte) 0x76,(byte) 0x54,(byte) 0x32,(byte) 0x10};

        CamelliaAlgorithm ca = new CamelliaAlgorithm();

        ca.init(true,key);

        JFrame frame = new JFrame("CamelliaVisualisation");
        frame.setContentPane(new CamelliaVisualisation().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void HoverOn(int index, ByteArrayDisplay source) {
if (source == initialKey) {
    initialKey.SetByteBackgroundColor(index, Color.BLUE);
}
        if (source == rotatingKey) {
            rotatingKey.SetByteBackgroundColor(index, Color.RED);
        }
    }

    @Override
    public void HoverOff(int index, ByteArrayDisplay source) {
        source.SetByteBackgroundColor(index, null);
    }
}
