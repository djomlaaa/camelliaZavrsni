package camellia.brankovic.mladen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CamelliaVisualisation {

    private JPanel MainPanel;
    private ByteArrayDisplay initialKey;
    private JButton kaPreviousRound;
    private JLabel kaRoundLabel;
    private JButton kaNextRound;

    private int kaRoundIndex = 0;


    public CamelliaVisualisation() {
        initialKey.SetByteArray(CamelliaAlgorithm.initialKey);

        //UpdateRound();



        kaPreviousRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kaRoundIndex > 0) {
                    kaRoundIndex--;
                    UpdateRound();
                }
            }
        });
        kaNextRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kaRoundIndex < 3) {
                    kaRoundIndex++;
                    UpdateRound();
                }
            }
        });
    }

    private void UpdateRound() {
        kaRoundLabel.setText(String.valueOf(kaRoundIndex+1));
        initialKey.SetByteArray(IntArrayToByteArray(CamelliaAlgorithm.xoringKeyA[kaRoundIndex]));
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
}
