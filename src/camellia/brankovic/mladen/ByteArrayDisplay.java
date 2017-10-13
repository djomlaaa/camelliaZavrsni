package camellia.brankovic.mladen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ByteArrayDisplay extends JPanel {

    private byte[] displayBytes;
    private int[] displayInts;
    private boolean[] displayBits;
    private int displaySize;
    private Color defaultColor;
    private ArrayList<MoseHoverOnByteListener> listeners = new ArrayList<>();

    public void AddMoseHoverOnByteListener(MoseHoverOnByteListener listener) {
        listeners.add(listener);
    }

    public void SetByteBackgroundColor(int index, Color color) {
        LabelFields[index].setBackground(color == null ? defaultColor : color);
    }

    public void SetByteArray(byte[] displayBytes) {
this.displayBytes = displayBytes;
displayInts = null;
displayBits = null;
        if (displaySize != displayBytes.length) {
            displaySize = displayBytes.length;
            ReCreateTextFields();
        }
UpdateTextFields();
    }
    public void SetIntArray(int[] displayInts) {
        displayBytes = null;
        this.displayInts = displayInts;
        displayBits = null;

        if (displaySize != displayInts.length) {
            displaySize = displayInts.length;
            ReCreateTextFields();
        }

        UpdateTextFields();
    }
    public void SetBitArray(boolean[] displayBits) {
        displayBytes = null;
        displayInts = null;
        this.displayBits = displayBits;

        if (displaySize != displayBits.length) {
            displaySize = displayBits.length;
            ReCreateTextFields();
        }

        UpdateTextFields();
    }
    private void ReCreateTextFields() {


    int columnCount = 8;
    int rowCount = 8;
    switch (displaySize) {
        case 8:
            columnCount = 4;
            rowCount = 2;
            break;
        case 16:
            columnCount = 4;
            rowCount = 4;
            break;
        case 64:
            columnCount = 8;
            rowCount = 8;
            break;
        case 128:
            columnCount = 16;
            rowCount = 8;
            break;
        case 256:
            columnCount = 16;
            rowCount = 16;
            break;
        default:
            //throw new Exception("Niste izabrali za display size " + displaySize);
    }
    GridLayout gridLayout = new GridLayout(rowCount, columnCount);
    gridLayout.setHgap(3);
    gridLayout.setVgap(3);

    setLayout(gridLayout);
    removeAll();
    Dimension dimension = new Dimension();
    dimension.setSize(15, 15);
    LabelFields = new JLabel[displaySize];
    Border border = BorderFactory.createLineBorder(Color.gray, 1);
        for (int i = 0; i < displaySize; i++) {

            LabelFields[i] = new JLabel();
            LabelFields[i].setMinimumSize(dimension);
//            LabelFields[i].setPreferredSize(dimension);
//            LabelFields[i].setMaximumSize(dimension);
            LabelFields[i].setFont(LabelFields[i].getFont().deriveFont(12f));
            LabelFields[i].setHorizontalAlignment(SwingConstants.CENTER);
            LabelFields[i].setVerticalAlignment(SwingConstants.CENTER);
            LabelFields[i].setBorder(border);
            LabelFields[i].setOpaque(true);
            LabelFields[i].setForeground(Color.black);
            int finalI = i;
            LabelFields[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    RaiseMouseHoverEvent(finalI,true);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseEntered(e);
                    RaiseMouseHoverEvent(finalI,false);
                }
            });
            add(LabelFields[i]);
        }
        defaultColor = LabelFields[0].getBackground();
    }

    private void RaiseMouseHoverEvent(int index, boolean entered) {
        for (int i = 0; i < listeners.size(); i++) {
            if (entered) {
                listeners.get(i).HoverOn(index,this);
            }
            else {
                listeners.get(i).HoverOff(index,this);
            }

        }
    }
    private void UpdateTextFields() {
        for (int i = 0; i < displaySize; i++) {
            if (displayBytes != null) {
                LabelFields[i].setText(String.format("%02X ", displayBytes[i]));
            }
            else if (displayInts != null) {
                LabelFields[i].setText((((Integer)displayInts[i]).toString()));

            }
            else if (displayBits != null){
                LabelFields[i].setText(displayBits[i] ? "1" : "0");

            }
            else {
                //WHAT DO YOU WANT FROM ME????
            }
            }
    }

    private JLabel[] LabelFields = new JLabel[64];
    public ByteArrayDisplay() {


        SetBitArray(new boolean[64]);
    }

    public interface MoseHoverOnByteListener {
        void HoverOn(int index, ByteArrayDisplay source);
        void HoverOff(int index, ByteArrayDisplay source);
    }

    public static boolean[] ByteArrayToBitArray(byte[] bytes) {

        boolean[] bools = new boolean[bytes.length *8];
        for (int i =0; i < bytes.length; i++) {
            bools[i*8+0] = ((bytes[i] & 1) != 0);
            bools[i*8+1] = ((bytes[i] & 2) != 0);
            bools[i*8+2] = ((bytes[i] & 4) != 0);
            bools[i*8+3] = ((bytes[i] & 8) != 0);
            bools[i*8+4] = ((bytes[i] & 16) != 0);
            bools[i*8+5] = ((bytes[i] & 32) != 0);
            bools[i*8+6] = ((bytes[i] & 64) != 0);
            bools[i*8+7] = ((bytes[i] & 128) != 0);

        }
        return bools;
    }
    public static byte[] ConcatenateByteArrays(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;
        byte[] c= new byte[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
