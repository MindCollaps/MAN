package table;


import gui.DataField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Table extends JPanel {

    private ArrayList<DataField> dataFields;
    private JTextField tableName;
    private JButton addFieldButton;
    private JPanel fieldPannel;

    public Table() {
        this.dataFields = new ArrayList();
        this.setBounds(0,0, 1000, 200);
        this.setBackground(Color.RED);
        BoxLayout panelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        this.setLayout(panelLayout);

        this.tableName = new JTextField();
        this.add(tableName);

        this.fieldPannel = new JPanel();
        BoxLayout fieldPannelLayout = new BoxLayout(this.fieldPannel, BoxLayout.X_AXIS);
        this.fieldPannel.setLayout(fieldPannelLayout);
        this.add(fieldPannel);

        this.addFieldButton = new JButton("Add Field");
        fieldPannel.add(addFieldButton);

        this.addFieldButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addDataField();
                generateGui();
            }
        });
    }

    public void addDataField(){
        dataFields.add(new DataField());
    }

    public void removeDataField(DataField field){
        dataFields.remove(field);
    }

    public void generateGui(){
        this.fieldPannel.removeAll();

        for (DataField dataField : this.dataFields) {
            this.fieldPannel.add(dataField);
        }

        this.fieldPannel.add(addFieldButton);

        this.repaint();
        this.revalidate();
    }
}
