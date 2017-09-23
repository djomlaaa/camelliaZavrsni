package camellia.brankovic.mladen;

import javax.swing.*;
import java.awt.*;

public class ByteArrayDisplay extends JPanel {
    private byte[] displayBytes;
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
            add(TextFields[i]);
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
}
