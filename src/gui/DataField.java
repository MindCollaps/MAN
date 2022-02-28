package gui;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DataField extends JPanel {

    private table.dataFields.DataField dataField;

    private JComboBox<String> comboBox;

    public DataField() {
        this.comboBox = new JComboBox<>(VisualEngine.generatorDataFieldsString);

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

        this.add(comboBox);
    }
}
