package gui.settingsPage;

import gui.VisualEngine;
import table.Table;
import table.dataFields.DataField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The VisualDataField is the actual DataField that gets displayed on the SettingsPage, its the manager for the DataField and ensures that it looks and works for the user
 */
public class VisualDataField extends JPanel {

    Icon icon = new ImageIcon("Pictures/options.jpg");
    private final Table table;
    private final JTextArea panelNumber;
    private final JTextField name;
    private final JButton options;
    private final JComboBox<String> comboBox;
    private DataField dataField;

    // ui values
    public int upMargin=20;

    public void SetDataFieldNumber(int table, int field)
    {
        //panelNumber.setText("Data field "+ i);
        //panelNumber.setText("");
        panelNumber.setText(table+"."+field);
    }

    public VisualDataField(Table table) {
        this.table = table;

        this.setLayout(null);
        this.panelNumber = new JTextArea("Data field 1");
        this.panelNumber.setEditable(false);
        this.panelNumber.setBounds(0, 0, 80, 20);

        this.name = new JTextField("name");
        this.name.setBounds(0, upMargin, 100, 50);
        this.setBackground(Color.white);
        this.comboBox = new JComboBox<>(VisualEngine.generatorDataFieldsString);
        this.comboBox.setBounds(0, 50 +upMargin, 60, 40);

        this.options = new JButton(icon); // text will be replaced with picture
        this.options.setBounds(60, 50+upMargin, 40, 40);
        this.options.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openSettings();
            }
        });


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

        //This insures that the index ß generator data field will be loaded, else it would be null which could cause issues
        try {
            dataField = VisualEngine.generatorDataFields[0].getClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.add(name);
        this.add(comboBox);
        this.add(options);
        this.add(panelNumber);
    }

    private void openSettings() {
        if (dataField != null) {
            SettingsPage page = dataField.getSettingsPage(this);
            if (page == null) {
                System.err.println("Settings page of " + dataField.getFieldName() + " is null!");
                return;
            }
            this.table.openSettings(page, this);
        }
    }

    public void closeSettings() {
        table.closeSettings();
    }

    public DataField getDataField() {
        return dataField;
    }

    public String getFieldName(){
        return name.getText();
    }
}
