package table;

import gui.VisualEngine;
import gui.settingsPage.SettingsPage;
import gui.settingsPage.VisualDataField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class creates and manages a created Table in the GUI. It holds all the DataFields and manages major events like open- and closeSettings
 */
public class Table extends JPanel {

    Icon icon = new ImageIcon("Pictures/options2.jpg");
    Icon icon2 = new ImageIcon("Pictures/plus.jpg");

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
    private VisualEngine gui; // table needs a reference to the gui because everytime a datafields gets added to this table the gui needs to know if its large enought to update the canvas lenght
    public Table() {
        this.dataFields = new ArrayList();

        // table gui
        //this.setBackground(Color.white);
        //BoxLayout panelLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // only for test
        this.setLayout(null);
        // table gui end

        // text field gui
        this.tableName = new JTextField();
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
        this.add(tableOptionsButton);
        // option button end

        // fieldpannel
        this.fieldPanel = new JPanel();
        //BoxLayout fieldPanelLayout = new BoxLayout(this.fieldPannel, BoxLayout.X_AXIS);
        this.fieldPanel.setLayout(null);
        this.fieldPanel.setBounds(00, 70 -dataFieldUpDown, 700, 100+ dataFieldUpDown*2);
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

    public void setGui(VisualEngine gui) {
        this.gui = gui;
    }

    public void addDataField() {
        dataFields.add(new VisualDataField(this));
    }

    public void removeDataField(VisualDataField field) {
        dataFields.remove(field);
    }

    // when field gets added or something
    public void generateGui() {
        this.fieldPanel.removeAll();

        int i = 0;
        for (VisualDataField dataField : this.dataFields) {
            this.fieldPanel.add(dataField);
            dataField.setBounds(i * dataFieldLength + dataFieldLeftRightMargin, dataFieldUpDown, 100, dataFieldHeight);

            i++;
            dataField.SetDataFieldNumber(tableNumber,i);
        }

        this.fieldPanel.add(addFieldButton);

        setAddButtonPosition();
        sescaleTable();
        gui.setCanvasSize(); // kinda unoptimized to update the entire gui everytime something gets added but this program is small enough that it shouldn't matter
        this.repaint();
        this.revalidate();
    }

    public void setPosition(int position) {
        this.setBounds(50, position, Math.max(minTableLength, dataFieldLength * (dataFields.size() + 1)), tableHeight);
        this.repaint();
        this.revalidate();
    }

    public void sescaleTable() {
        this.setBounds(this.getBounds().x, this.getBounds().y, Math.max(minTableLength, dataFieldLength * (dataFields.size() + 1)) + dataFieldLeftRightMargin*2, tableHeight);
        this.fieldPanel.setBounds(00, 70-dataFieldUpDown, Math.max(minTableLength, dataFieldLength * (dataFields.size() + 1)) + dataFieldLeftRightMargin*2, dataFieldHeight + 2*dataFieldUpDown);
        this.repaint();
        this.revalidate();
    }

    public int setLenght() {
        return Math.max(minTableLength, dataFieldLength * (dataFields.size() + 1));
    }

    public void setAddButtonPosition() {
        this.addFieldButton.setBounds(dataFields.size() * dataFieldLength + dataFieldLeftRightMargin, dataFieldUpDown, 100, 100);
    }

    public void openSettings(SettingsPage page, VisualDataField field) {
        gui.openSettings(page, field, this);
    }

    public void closeSettings() {
        gui.closeSettings();
    }


    public void SetTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ArrayList<VisualDataField> getDataFields() {
        return dataFields;
    }

    public int getTableCount(){
        try {
            return Integer.parseInt(tableCount.getText());
        } catch (Exception e){
            //TODO: Print error message
        }
        return 0;
    }

    public String getTableName(){
        return tableName.getText();
    }
}
