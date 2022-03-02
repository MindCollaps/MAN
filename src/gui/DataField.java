package gui;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DataField extends JPanel {

    private table.dataFields.DataField dataField;

    private JTextField name;  // has no function yet
    private JButton options; // has no function yet
    private JComboBox<String> comboBox;
    // gaming
    public DataField() {

        this.setLayout(null);

        this.name = new JTextField("name");
        name.setBounds(0,0,100,50);

        this.comboBox = new JComboBox<>(VisualEngine.generatorDataFieldsString);
        comboBox.setBounds(0,50,60,40);

        this.options = new JButton("options"); // text will be replaced with picture
        options.setBounds(60,50,40,40);

        this.comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    dataField = VisualEngine.generatorDataFields[comboBox.getSelectedIndex()].getClass().newInstance();
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }
        });
        this.add(name);
        this.add(comboBox);
        this.add(options);
    }
}
