package gui;

import gui.settingsPage.SettingsPage;
import gui.settingsPage.VisualDataField;
import table.Table;
import table.dataFields.DataField;
import table.dataFields.fields.DateDF;
import table.dataFields.fields.IntegerDF;
import table.dataFields.fields.StringDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VisualEngine extends JFrame {

    public static DataField[] generatorDataFields;
    public static String[] generatorDataFieldsString;
    private final ArrayList<Table> tables = new ArrayList<Table>();
    // ui values
    private final int canvasMarginSide = 20;
    private final int canvasMarginTop = 20;
    private final int canvasMarginBottom = 200;
    private final int baseCanvasHeight = 800;
    private final int baseCanvasLenght = 1200;
    private final int tableMargin = 230;
    private final int tableMarginLenght = 230;
    // ui text, only visual
    private final ArrayList<JTextArea> tableName_text = new ArrayList<JTextArea>();
    private Button addTableButton;
    private JScrollPane scrollPane;
    private JPanel canvas; // tables will be displayed on the canvas,
    //Settings page
    private JDialog settingsPage;

    public VisualEngine() {
        loadGeneratorFields();
        System.out.println("Loaded " + generatorDataFields.length + " generator Field Classes!");
        buildGui();
    }

    private void buildGui() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });


        this.setBounds(40, 40, 1500, 700);


        //BoxLayout panelLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // only for test
        this.setLayout(null);

        canvas = new JPanel();
        canvas.setLayout(null);
        //canvas.setBounds(50, 50, 980, 680);


        //add table button gui stuff
        this.addTableButton = new Button("Add Table");
        canvas.add(this.addTableButton);
        setAddTableButtonPosition(tableMargin * tables.size());
        //add table button gui end


        Frame frame = this;
        JPanel page = canvas;
        this.addTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                page.remove(addTableButton);
                Table newTable = new Table();
                addGUIReferenceToTable(newTable);

                page.add(newTable);
                newTable.setPosition(tableMargin * tables.size() + 50);


                tables.add(newTable);
                setCanvasSize();

                // table text
                JTextArea newText = new JTextArea("Table " + tables.size());
                newTable.SetTableNumber(tables.size()); 
                newText.setEditable(false);
                newText.setBounds(50, (tableMargin * tables.size()) - tableMargin + 30, 50, 20);
                tableName_text.add(newText);
                page.add(newText);
                // table text end

                page.add(addTableButton);
                page.repaint();
                page.revalidate();
                frame.repaint();
                frame.revalidate();
            }
        });

        canvas.add(this.addTableButton);

        // scroll bar
        scrollPane = new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //scrollPane.setBounds(50, 50, baseCanvasLenght +50, baseCanvasHeight +50);
        canvas.setPreferredSize(new Dimension(baseCanvasLenght, baseCanvasHeight));


        this.add(scrollPane);
        //this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);


        // canvase scaling when chanching window size
        setCanvasBorders();
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setCanvasSize();
                setCanvasBorders();
            }
        });

        this.repaint();
        this.revalidate();
    }

    private void loadGeneratorFields() {
        generatorDataFields = new DataField[]{
                new StringDF(),
                new IntegerDF(),
                new DateDF()
        };

        generatorDataFieldsString = new String[generatorDataFields.length];

        for (int i = 0; i < generatorDataFields.length; i++) {
            generatorDataFieldsString[i] = generatorDataFields[i].getFieldName();

        }
    }

    public void addGUIReferenceToTable(Table table) {
        table.setGui(this);
    }

    public void setCanvasSize() {
        setAddTableButtonPosition(tableMargin * tables.size() + 50);
        int longestTable = 0; // amount of datafields in the biggest Table, used for scaling and stuff
        for (Table t : tables) {
            longestTable = Math.max(longestTable, t.setLenght());
        }
        canvas.setPreferredSize(new Dimension(Math.max(baseCanvasLenght, longestTable + tableMarginLenght), Math.max(baseCanvasHeight, tableMargin * (tables.size() + 1))));
    }


    public void setAddTableButtonPosition(int position) {
        this.addTableButton.setBounds(50, position, 100, 100);
    }

    public void setCanvasBorders() {
        int x = this.getWidth();
        int y = this.getHeight();
        scrollPane.setBounds(canvasMarginSide, canvasMarginTop, x - canvasMarginSide * 2, y - canvasMarginTop - canvasMarginBottom);

    }

    public void openSettings(SettingsPage page, VisualDataField field, Table table) {
        if (page != null && field != null && table != null) {
            settingsPage = new JDialog(this, Dialog.ModalityType.APPLICATION_MODAL);
            settingsPage.setContentPane(page);
            settingsPage.setBounds(100, 100, page.getWidth(), page.getHeight());
            settingsPage.repaint();
            settingsPage.revalidate();
            settingsPage.setVisible(true);
        } else
            System.err.println("Can't open settings, because one of the values is null!");
    }

    public void closeSettings() {
        if (this.settingsPage != null) {
            this.settingsPage.dispose();
        }
    }
}
