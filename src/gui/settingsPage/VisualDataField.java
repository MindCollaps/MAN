package gui.settingsPage;

import gui.VisualEngine;
import table.Table;
import table.dataFields.DataField;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The VisualDataField is the actual DataField that gets displayed on the SettingsPage, its the manager for the DataField and ensures that it looks and works for the user
 */
public class VisualDataField extends JPanel {

    private final Table table;
    private final JTextField name;  // has no function yet
    private final JButton options; // has no function yet
    private final JComboBox<String> comboBox;
    private DataField dataField;

    public VisualDataField(Table table) {
        this.table = table;

        this.setLayout(null);

        this.name = new JTextField("name");
        this.name.setBounds(0, 0, 100, 50);

        this.comboBox = new JComboBox<>(VisualEngine.generatorDataFieldsString);
        this.comboBox.setBounds(0, 50, 60, 40);

        this.options = new JButton("options"); // text will be replaced with picture
        this.options.setBounds(60, 50, 40, 40);
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
}
