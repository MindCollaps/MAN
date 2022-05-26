package gui.pages.settingsPage;

import gui.VisualEngine;
import gui.pages.settingsPage.pageField.DefaultValueSetter;
import gui.pages.settingsPage.pageField.PageField;
import gui.pages.settingsPage.pageField.PageFieldAction;
import gui.pages.settingsPage.pageField.PageFieldGrabber;
import table.Table;
import table.dataFields.DataField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

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
    public int upMargin = 20;

    private String dataFieldNumber;

    public void setDataFieldNumber(int table, int field) {
        //panelNumber.setText("Data field "+ i);
        //panelNumber.setText("");
        this.dataFieldNumber = table + "." + field;
        panelNumber.setText(this.dataFieldNumber);
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
        this.comboBox.setBounds(0, 50 + upMargin, 60, 40);

        this.options = new JButton(icon); // text will be replaced with picture
        this.options.setBounds(60, 50 + upMargin, 40, 40);
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
            SettingsPage dataFieldPage = dataField.getSettingsPage(this);

            if (dataFieldPage == null) {
                System.err.println("Settings page of " + dataField.getFieldName() + " is null!");
                return;
            }

            VisualDataField thisField = this;

            JButton deleteButton = new JButton("Delete");

            PageField<JButton> deleteSelector = new PageField<>("Delete Table", deleteButton,
                    new PageFieldGrabber<JButton>() {
                        @Override
                        public String getDataString(PageField<JButton> page, JButton jButton) {
                            return null;
                        }
                    },
                    new PageFieldAction<JButton>() {
                        @Override
                        public void onSave(PageField<JButton> pageField) {

                        }
                    },
                    new DefaultValueSetter<JButton>() {
                        @Override
                        public void setDefaultData(JButton component) {

                        }
                    });

            PageField[] fields = dataFieldPage.getFields().toArray(new PageField[dataFieldPage.getFields().size() + 1]);
            fields[fields.length -1] = deleteSelector;

            SettingsPage page = new SettingsPage(dataFieldPage.getSettingsNameLabel(), this, fields);
            deleteButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table.removeField(thisField);
                    page.closeSettings();
                }
            });
            this.table.openSettings(page);
        }
    }

    public void closeSettings() {
        table.closeSettings();
    }

    public DataField getDataField() {
        return dataField;
    }

    public String getFieldName() {
        return name.getText();
    }

    public String getDataFieldNumber() {
        return dataFieldNumber;
    }

    public Table getTable() {
        return table;
    }
}
