package gui;

import generator.GeneratorSession;
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

    Icon icon = new ImageIcon("Pictures/plus.jpg");

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
    private JButton addTableButton;
    private JScrollPane scrollPane;
    private JPanel canvas; // tables will be displayed on the canvas,
    private JButton generateButton;
    private JPanel contentPanel;
    private JPanel bottomPanel;
    //Settings page
    private JDialog settingsPage;

    //Generator stuff
    private GeneratorSession session;

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

        this.setLayout(null);

        canvas = new JPanel();
        canvas.setLayout(null);
        //canvas.setBounds(50, 50, 980, 680);

        this.generateButton = new JButton("Generate");
        this.generateButton.setBounds(0, 0, 100, 50);
        this.generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                generateData();
            }
        });

        //add table button gui stuff
        this.addTableButton = new JButton(icon);
        canvas.add(this.addTableButton);
        setAddTableButtonPosition(tableMargin * tables.size());
        //add table button gui end


        Frame frame = this;
        this.addTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                canvas.remove(addTableButton);
                Table newTable = new Table();
                addGUIReferenceToTable(newTable);

                canvas.add(newTable);
                newTable.setPosition(tableMargin * tables.size() + 50);

                tables.add(newTable);

                // table text
                JTextArea newText = new JTextArea("Table " + tables.size());
                newTable.SetTableNumber(tables.size()); 
                newText.setEditable(false);
                newText.setBounds(50, (tableMargin * tables.size()) - tableMargin + 30, 50, 20);
                tableName_text.add(newText);
                canvas.add(newText);
                // table text end

                canvas.add(addTableButton);
                setContentSize();
            }
        });

        canvas.add(this.addTableButton);

        // scroll bar
        scrollPane = new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //scrollPane.setBounds(50, 50, baseCanvasLenght +50, baseCanvasHeight +50);
        canvas.setPreferredSize(new Dimension(baseCanvasLenght, baseCanvasHeight));

        bottomPanel = new JPanel();
        BoxLayout bottomLayout = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
        bottomPanel.setLayout(bottomLayout);
        bottomPanel.add(generateButton);
        bottomPanel.setVisible(true);

        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.add(scrollPane);
        contentPanel.add(bottomPanel);
        contentPanel.setVisible(true);

        this.add(contentPanel);

        // canvase scaling when chanching window size
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setContentSize();
            }
        });

        this.setVisible(true);
        setContentSize();
    }

    private void setContentSize() {
        this.contentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        setCanvasSize();
        setCanvasBorders();

        setBottomBarSize();

        this.repaint();
    }

    private void setBottomBarSize() {
        int x = this.getWidth();
        bottomPanel.setBounds(20, scrollPane.getY() + scrollPane.getHeight() + 30, x - 40, 100);
    }

    private void setCanvasBorders() {
        int x = this.getWidth();
        int y = this.getHeight();
        scrollPane.setBounds(canvasMarginSide, canvasMarginTop, x - canvasMarginSide * 2, y - canvasMarginTop - canvasMarginBottom);
    }

    public void setCanvasSize() {
        setAddTableButtonPosition(tableMargin * tables.size() + 50);
        int longestTable = 0; // amount of datafields in the biggest Table, used for scaling and stuff
        for (Table t : tables) {
            longestTable = Math.max(longestTable, t.setLenght());
        }
        canvas.setSize(new Dimension(Math.max(baseCanvasLenght, longestTable + tableMarginLenght), Math.max(baseCanvasHeight, tableMargin * (tables.size() + 1))));
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

    private void setAddTableButtonPosition(int position) {
        this.addTableButton.setBounds(50, position, 100, 100);
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

    public void generateData() {
        if (this.tables != null)
            if (this.tables.size() > 0)
                session = new GeneratorSession(this.tables.toArray(new Table[]{}));
    }
}
