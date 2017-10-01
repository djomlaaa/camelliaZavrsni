package camellia.brankovic.mladen;

import javax.swing.*;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ByteArrayDisplay extends JPanel {

    private byte[] displayBytes;
    private Color defaultColor;
    private ArrayList<MoseHoverOnByteListener> listeners = new ArrayList<>();

    public void AddMoseHoverOnByteListener(MoseHoverOnByteListener listener) {
        listeners.add(listener);
    }

    public void SetByteBackgroundColor(int index, Color color) {
        TextFields[index].setBackground(color == null ? defaultColor : color);
    }

    public void SetByteArray(byte[] displayBytes) {
this.displayBytes = displayBytes;
ReCreateTextFields();
UpdateTextFields();
    }

    private void ReCreateTextFields() {

        GridLayout gridLayout = new GridLayout((int)Math.sqrt(displayBytes.length),(int)Math.sqrt(displayBytes.length));

        setLayout(gridLayout);
        removeAll();
        Dimension dimension = new Dimension();
        dimension.setSize(25,25);
        TextFields = new TextField[displayBytes.length];
        for (int i = 0; i < displayBytes.length; i++) {

            TextFields[i] = new TextField();
            TextFields[i].setEditable(false);
            TextFields[i].setMinimumSize(dimension);
            TextFields[i].setPreferredSize(dimension);
            TextFields[i].setMaximumSize(dimension);
            int finalI = i;
            TextFields[i].addMouseListener(new MouseAdapter() {
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
            add(TextFields[i]);
        }
        defaultColor = TextFields[0].getBackground();
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
        for (int i = 0; i < displayBytes.length; i++) {
        TextFields[i].setText(String.format("%02X ", displayBytes[i]));
    }
    }

    private TextField[] TextFields = new TextField[16];
    public ByteArrayDisplay() {


        SetByteArray(new byte[16]);
    }

    public interface MoseHoverOnByteListener {
        void HoverOn(int index, ByteArrayDisplay source);
        void HoverOff(int index, ByteArrayDisplay source);
    }
}
