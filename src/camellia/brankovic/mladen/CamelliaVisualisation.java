package camellia.brankovic.mladen;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CamelliaVisualisation implements ByteArrayDisplay.MoseHoverOnByteListener {

    private JPanel MainPanel;
    private ByteArrayDisplay displayKL;
    private JButton previousRound;
    private JLabel roundLabel;
    private JButton nextRound;
    private JTabbedPane tabbedPane;
    private ByteArrayDisplay rotatingKey;
    private ByteArrayDisplay displayKA;
    private JButton showFFunction;

    private int currentOperation;
private static final int OPERATION_COUNT = 2;
    private int[] stepCount = new int[]{5,26};
    private int[] roundIndex = new int[OPERATION_COUNT];


    public CamelliaVisualisation() {
rotatingKey.AddMoseHoverOnByteListener(this);
displayKL.AddMoseHoverOnByteListener(this);
        UpdateRound();

        tabbedPane.addChangeListener(e -> {
            currentOperation = tabbedPane.getSelectedIndex();
            UpdateRound();
        });

        previousRound.addActionListener(e -> {
            if (roundIndex[currentOperation] > 0) {
                roundIndex[currentOperation]--;
                UpdateRound();
            }
        });
        nextRound.addActionListener(e -> {
            if (roundIndex[currentOperation] < stepCount[currentOperation]-1) {
                roundIndex[currentOperation]++;
                UpdateRound();
            }
        });

        showFFunction.addActionListener(e -> {
            JDialog jd = new FFunctionDialog(roundIndex[currentOperation]);
            jd.setVisible(true);

        });
    }

    private void UpdateRound() {
        roundLabel.setText("Runda: " + String.valueOf(roundIndex[currentOperation]));
        switch (currentOperation) {
            case 0:
                if (roundIndex[currentOperation] == 0) {
                displayKL.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.initialKey));
                displayKA.setVisible(false);
                showFFunction.setVisible(false);
                }
                else if(roundIndex[currentOperation] == 1) {
                    displayKL.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.initialKey));
                    displayKA.setVisible(true);
                    displayKA.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.formingKA[0]));
                    showFFunction.setVisible(false);

                }
                else {
                    displayKL.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.formingKA[roundIndex[currentOperation]-2]));
                    displayKA.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.formingKA[roundIndex[currentOperation]-1]));
                    displayKA.setVisible(true);

                    showFFunction.setVisible(roundIndex[currentOperation] == 4 || roundIndex[currentOperation] == 2 );

                }
                break;
            case 1:
                rotatingKey.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.outputK[roundIndex[currentOperation]]));
                break;
        }
    }
    public static byte[] IntArrayToByteArray(int[] intArray) {
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
if (source == displayKL) {
    displayKL.SetByteBackgroundColor(index, Color.BLUE);
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
