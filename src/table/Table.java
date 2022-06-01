package table;

import gui.VisualEngine;
import gui.pages.settingsPage.SettingsPage;
import gui.pages.settingsPage.pageField.DefaultValueSetter;
import gui.pages.settingsPage.pageField.PageField;
import gui.pages.settingsPage.pageField.PageFieldAction;
import gui.pages.settingsPage.pageField.PageFieldGrabber;
import table.dataFields.VisualDataField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class creates and manages a created Table in the GUI. It holds all the DataFields and manages major events like open- and closeSettings
 */
public class Table extends JPanel {

    private final Icon icon = new ImageIcon("src/gui/pics/options2.jpg");
    private final Icon icon2 = new ImageIcon("src/gui/pics/plus.jpg");

    private final VisualEngine gui; // table needs a reference to the gui because everytime a datafields gets added to this table the gui needs to know if its large enought to update the canvas lenght

    private final ArrayList<VisualDataField> dataFields;
    private final JTextField tableName;
    private final JTextField tableCount;
    private final JButton tableOptionsButton;
    private final JButton addFieldButton;
    private final JPanel fieldPanel;
    // ui scale values
    private final int minTableLength = 300;
    private final int tableHeight = 200;
    private final int dataFieldHeight = 200;
    private final int dataFieldLength = 120;
    private final int dataFieldLeftRightMargin = 20;
    private final int dataFieldUpDown = 20;
    private int tableNumber = 0;

    private String primaryKeyName;

    public Table(VisualEngine gui, int tableGuiName) {
        tableNumber = tableGuiName;
        this.gui = gui;
        this.dataFields = new ArrayList();

        // table gui
        //this.setBackground(Color.white);
        //BoxLayout panelLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // only for test
        this.setLayout(null);
        // table gui end

        // text field gui
        this.tableName = new JTextField("");
        if(tableGuiName == 0)
            this.tableName.setText("User");
        this.tableName.setBounds(00, 0, 100, 50);
        this.add(tableName);
        // text field gui end

        // count field gui
        this.tableCount = new JTextField();
        this.tableCount.setBounds(100, 0, 50, 50);
        this.add(tableCount);
        // count field gui end

        // option button
        this.tableOptionsButton = new JButton(icon);
        this.tableOptionsButton.setBounds(150, 0, 50, 50);
        Table thisTable = this;
        this.tableOptionsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PageField<JTextField> primaryKeyNameSelector = new PageField<>("Primary Key Name", new JTextField(),
                        new PageFieldGrabber<JTextField>() {
                            @Override
                            public String getDataString(PageField<JTextField> page, JTextField jTextField) {
                                return jTextField.getText();
                            }
                        },
                        new PageFieldAction<JTextField>() {
                            @Override
                            public void onSave(PageField<JTextField> pageField) {
                                if (pageField.getDataString() == null)
                                    primaryKeyName = null;
                                else if (pageField.getDataString().length() == 0)
                                    primaryKeyName = null;
                                else
                                    primaryKeyName = pageField.getDataString();
                            }
                        },
                        new DefaultValueSetter<JTextField>() {
                            @Override
                            public void setDefaultData(JTextField component, SettingsPage page) {
                                if (primaryKeyName == null)
                                    component.setText("");
                                else
                                    component.setText(primaryKeyName);
                            }
                        });

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
                            public void setDefaultData(JButton component, SettingsPage page) {

                            }
                        });

                SettingsPage page = new SettingsPage(getTableName(), null, new VisualDataField(thisTable, 0), primaryKeyNameSelector, deleteSelector);
                deleteButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gui.removeTable(thisTable);
                        page.closeSettings();
                    }
                });
                gui.openSettings(page);
            }
        });
        this.add(tableOptionsButton);
        // option button end

        // fieldpannel
        this.fieldPanel = new JPanel();
        //BoxLayout fieldPanelLayout = new BoxLayout(this.fieldPannel, BoxLayout.X_AXIS);
        this.fieldPanel.setLayout(null);
        this.fieldPanel.setBounds(00, 70 - dataFieldUpDown, 700, 100 + dataFieldUpDown * 2);
        this.fieldPanel.setBackground(Color.white);
        this.add(fieldPanel);
        // fieldpannel end

        // add field button
        this.addFieldButton = new JButton(icon2);
        setAddButtonPosition();
        fieldPanel.add(addFieldButton);
        // add field button end


        this.addFieldButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDataField();
                generateGui();
            }
        });
    }

    public void addDataField() {
        dataFields.add(new VisualDataField(this, dataFields.size()));
    }

    public void removeField(VisualDataField thisField) {
        dataFields.remove(thisField);
        renameDataFields();
        generateGui();
    }

    public void removeDataField(VisualDataField field) {
        dataFields.remove(field);
    }

    // when field gets added or something
    private void generateGui() {
        this.fieldPanel.removeAll();

        int i = 0;
        for (VisualDataField dataField : this.dataFields) {
            this.fieldPanel.add(dataField);
            dataField.setBounds(i * dataFieldLength + dataFieldLeftRightMargin, dataFieldUpDown, 100, dataFieldHeight);

            i++;
            dataField.setDataFieldNumber(tableNumber, i);
        }

        this.fieldPanel.add(addFieldButton);

        setAddButtonPosition();
        scaleTable();
        gui.setCanvasSize(); // kinda unoptimized to update the entire gui everytime something gets added but this program is small enough that it shouldn't matter
        this.repaint();
        this.revalidate();
    }

    public void setPosition(int position) {
        this.setBounds(50, position, Math.max(minTableLength, dataFieldLength * (dataFields.size() + 1)), tableHeight);
        this.repaint();
        this.revalidate();
    }

    public void scaleTable() {
        this.setBounds(this.getBounds().x, this.getBounds().y, Math.max(minTableLength, dataFieldLength * (dataFields.size() + 1)) + dataFieldLeftRightMargin * 2, tableHeight);
        this.fieldPanel.setBounds(00, 70 - dataFieldUpDown, Math.max(minTableLength, dataFieldLength * (dataFields.size() + 1)) + dataFieldLeftRightMargin * 2, dataFieldHeight + 2 * dataFieldUpDown);
        this.repaint();
        this.revalidate();
    }

    public int setLength() {
        return Math.max(minTableLength, dataFieldLength * (dataFields.size() + 1));
    }

    public void setAddButtonPosition() {
        this.addFieldButton.setBounds(dataFields.size() * dataFieldLength + dataFieldLeftRightMargin, dataFieldUpDown, 100, 100);
    }

    public void openSettings(SettingsPage page) {
        gui.openSettings(page);
    }

    public void closeSettings() {
        gui.closeSettings();
    }


    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        renameDataFields();
    }

    public ArrayList<VisualDataField> getDataFields() {
        return dataFields;
    }

    public int getTableCount() {
        try {
            return Integer.parseInt(tableCount.getText());
        } catch (Exception e) {
        }
        return -1;
    }

    public String getTableName() {
        return tableName.getText();
    }

    public String getTableGuiName() {
        return "Table " + tableNumber;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public ArrayList<Table> getAllTables() {
        return gui.getTables();
    }

    public void renameDataFields() {
        for (int i = 0; i < dataFields.size(); i++) {
            dataFields.get(i).setDataFieldNumber(tableNumber, i);
        }
    }
}
