package table;

import gui.DataField;
import gui.VisualEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Table extends JPanel {

    VisualEngine gui; // table needs a reference to the gui because everytime a datafields gets added to this table the gui needs to know if its large enought to update the canvas lenght
    public void setGui(VisualEngine gui)
    {
        this.gui = gui;
    }

    private ArrayList<DataField> dataFields;
    private JTextField tableName;
    private JTextField tableCount; // not done yet
    private JButton tableOptionsButton; // notDoneYet
    private JButton addFieldButton;
    private JPanel fieldPannel;

    // ui scale values
    private int minTableLenght = 300;
    private int TableHeight = 200;
    private int dataFieldLenght =120;
    private int dataFieldHeight =100;


    public Table() {
        this.dataFields = new ArrayList();

        // table gui
        this.setBackground(Color.RED);
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
        this.tableOptionsButton = new JButton("option");
        this.tableOptionsButton.setBounds(150, 0, 50, 50);
        this.add(tableOptionsButton);
        // option button end

        // fieldpannel
        this.fieldPannel = new JPanel();
        //BoxLayout fieldPanelLayout = new BoxLayout(this.fieldPannel, BoxLayout.X_AXIS);
        this.fieldPannel.setLayout(null);
        this.fieldPannel.setBounds(00, 100, 700, 100);
        this.add(fieldPannel);
        // fieldpannel end

        // add field button
        this.addFieldButton = new JButton("Add Field");
        setAddButtonPosition();
        fieldPannel.add(addFieldButton);
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
        dataFields.add(new DataField());
    }

    public void removeDataField(DataField field) {
        dataFields.remove(field);
    }

    // when field gets added or something
    public void generateGui() {
        this.fieldPannel.removeAll();

        int i =0;
        for (DataField dataField : this.dataFields) {
            this.fieldPannel.add(dataField);
            dataField.setBounds(i * dataFieldLenght,000,100,100);

            i++;
        }

        this.fieldPannel.add(addFieldButton);

        setAddButtonPosition();
        sescaleTable();
        gui.setCanvasSize(); // kinda unoptimized to update the entire gui everytime something gets added but this program is small enough that it shouldn't matter
        this.repaint();
        this.revalidate();
    }

    public void setPosition(int position)
    {
        this.setBounds(50, position, Math.max(minTableLenght,dataFieldLenght * (dataFields.size() +1)), 200);
        this.repaint();
        this.revalidate();
    }

    public void sescaleTable()
    {
        this.setBounds(this.getBounds().x,this.getBounds().y,Math.max(minTableLenght,dataFieldLenght * (dataFields.size() +1)), TableHeight);
        this.fieldPannel.setBounds(00, 100, Math.max(minTableLenght,dataFieldLenght * (dataFields.size() +1)), TableHeight);
        this.repaint();
        this.revalidate();
    }

    public int setLenght()
    {
        return Math.max(minTableLenght,dataFieldLenght * (dataFields.size() +1));
    }

    public void setAddButtonPosition()
    {
        this.addFieldButton.setBounds(dataFields.size() *dataFieldLenght, 000, 100, 100);
    }
}
