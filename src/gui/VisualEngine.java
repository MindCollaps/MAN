package gui;

import table.Table;
import table.dataFields.DataField;
import table.dataFields.fields.IntegerDF;
import table.dataFields.fields.StringDF;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class VisualEngine extends Frame {

    private Button addTableButton;

    public static DataField[] generatorDataFields;
    public static String[] generatorDataFieldsString;

    public VisualEngine() {
        loadGeneratorFields();
        System.out.println("Loaded " + generatorDataFields.length + " generator Field Classes!");
        buildGui();
    }

    private void buildGui(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
        this.setBounds(40, 40, 1000, 700);

        BoxLayout panelLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(panelLayout);

        this.addTableButton = new Button("Add Table");
        Frame page = this;
        this.addTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                page.remove(addTableButton);
                page.add(new Table());
                page.add(addTableButton);
                page.repaint();
                page.revalidate();
            }
        });

        this.add(this.addTableButton);

        this.setVisible(true);
    }

    private void loadGeneratorFields() {
        generatorDataFields = new DataField[]{
                new StringDF(),
                new IntegerDF()
        };

        generatorDataFieldsString = new String[generatorDataFields.length];

        for (int i = 0; i < generatorDataFields.length; i++) {
            generatorDataFieldsString[i] = generatorDataFields[i].getFieldName();
        }
    }
}
