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
displaySize = displayBytes.length;
ReCreateTextFields();
UpdateTextFields();
    }
    public void SetIntArray(int[] displayInts) {
        displayBytes = null;
        this.displayInts = displayInts;
        displayBits = null;
        displaySize = displayInts.length;
        ReCreateTextFields();
        UpdateTextFields();
    }
    public void SetBitArray(boolean[] displayBits) {
        displayBytes = null;
        displayInts = null;
        this.displayBits = displayBits;
        displaySize = displayBits.length;
        ReCreateTextFields();
        UpdateTextFields();
    }
    private void ReCreateTextFields() {

        GridLayout gridLayout = new GridLayout((int)Math.sqrt(displaySize),(int)Math.sqrt(displaySize));
        gridLayout.setHgap(3);
        gridLayout.setVgap(3);

        setLayout(gridLayout);
        removeAll();
        Dimension dimension = new Dimension();
        dimension.setSize(25,25);
        LabelFields = new JLabel[displaySize];
        Border border = BorderFactory.createLineBorder(Color.black,2);
        for (int i = 0; i < displaySize; i++) {

            LabelFields[i] = new JLabel();
            LabelFields[i].setMinimumSize(dimension);
//            LabelFields[i].setPreferredSize(dimension);
//            LabelFields[i].setMaximumSize(dimension);
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

    private JLabel[] LabelFields = new JLabel[16];
    public ByteArrayDisplay() {


        SetByteArray(new byte[16]);
    }

    public interface MoseHoverOnByteListener {
        void HoverOn(int index, ByteArrayDisplay source);
        void HoverOff(int index, ByteArrayDisplay source);
    }
}
