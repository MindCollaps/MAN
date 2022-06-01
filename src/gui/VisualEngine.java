package gui;

import generator.GeneratorSession;
import gui.pages.generatorPage.GeneratorPage;
import gui.pages.settingsPage.SettingsPage;
import table.Table;
import table.dataFields.DataField;
import table.dataFields.fields.DateDF;
import table.dataFields.fields.ForeignKeyDF;
import table.dataFields.fields.IntegerDF;
import table.dataFields.fields.StringDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class VisualEngine extends JFrame {

    Icon icon = new ImageIcon("src/gui/pics/plus.jpg");
    Image windowIcon = Toolkit.getDefaultToolkit().getImage("src/gui/pics/manIcon.PNG");
    Image manIc = Toolkit.getDefaultToolkit().getImage("src/gui/pics/man.PNG");

    public static DataField[] generatorDataFields;
    public static String[] generatorDataFieldsString;
    private final ArrayList<Table> tables = new ArrayList<Table>();
    private final ArrayList<JLabel> tableLabels = new ArrayList<>();
    private final ArrayList<JLabel> tableCountLabels = new ArrayList<>();
    // ui values
    private final int canvasMarginSide = 20;
    private final int canvasMarginTop = 130;
    private final int canvasMarginBottom = 200;
    private final int baseCanvasHeight = 800;
    private final int baseCanvasLenght = 1200;
    private final int tableMargin = 230;
    private final int tableMarginLenght = 230;
    // ui text, only visual
    private final ArrayList<JLabel> tableName_text = new ArrayList<JLabel>();
    private final ArrayList<JLabel> tableCount_text = new ArrayList<JLabel>();
    private JButton addTableButton;
    private JScrollPane scrollPane;
    private JPanel canvas; // tables will be displayed on the canvas,
    private JButton generateButton;
    private JPanel contentPanel;
    private JPanel bottomPanel;

    private JLabel man;
    private JLabel manText;

    //Generator stuff
    private GeneratorSession session;

    private JDialog settingPage;

    public VisualEngine() {
        loadGeneratorFields();
        System.out.println("Loaded " + generatorDataFields.length + " generator Field Classes!");
        buildGui();

    }

    private void buildGui() {
        this.setTitle("MAN - Mad Accessible Network");
        this.setIconImage(windowIcon);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });

        this.setBounds(40, 40, 1900, 1000);

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
        man = new JLabel("", new ImageIcon(manIc), JLabel.CENTER);
        man.setBounds(580, 10, 100, 100);

        manText = new JLabel("MAN");
        manText.setBounds(680, 40, 200, 30);
        manText.setFont(new Font("TeleNeo", Font.PLAIN, 32));

        add(manText);
        add(man);


        Frame frame = this;
        VisualEngine engine = this;
        this.addTableButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               addTable();
            }
        });

        canvas.add(this.addTableButton);

        // scroll bar
        scrollPane = new JScrollPane(canvas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //scrollPane.setBounds(50, 50, baseCanvasLenght +50, baseCanvasHeight +50);
        canvas.setPreferredSize(new Dimension(baseCanvasLenght, baseCanvasHeight));

        bottomPanel = new JPanel();
        BoxLayout bottomLayout = new BoxLayout(bottomPanel, BoxLayout.X_AXIS);
        //bottomPanel.setLayout(bottomLayout);
        bottomPanel.setLayout(null);
        //bottomPanel.add(generateButton);
        // bottomPannel stuff
        int marginY = 0;
        int marginX = 20;

        this.bottomPanel.add(this.generateButton);
        // bottomPannel stuff end


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

    public void addTable(){
        canvas.remove(addTableButton);
        Table newTable = new Table(this, tables.size());

        canvas.add(newTable);
        newTable.setPosition(tableMargin * tables.size() + 50);

        tables.add(newTable);

        // table text
        JLabel newText = new JLabel(newTable.getTableGuiName());
        newTable.setTableNumber(tables.size()-1);
        newText.setBounds(50, (tableMargin * tables.size()) - tableMargin + 30, 50, 20);
        tableName_text.add(newText);
        canvas.add(newText);
        tableLabels.add(newText);


        // count text
        JLabel newCount = new JLabel("Count");
        newTable.setTableNumber(tables.size()-1);
        newCount.setBounds(150, (tableMargin * tables.size()) - tableMargin + 30, 150, 20);
        tableName_text.add(newCount);
        canvas.add(newCount);
        tableCountLabels.add(newCount);


        // table text end

        canvas.add(addTableButton);
        setContentSize();
    }

    public void removeTable(Table thisTable) {
        canvas.remove(addTableButton);
        canvas.remove(thisTable);

        for (int i = 0; i < tables.size(); i++) {
            if(tables.get(i) == thisTable){
                tables.remove(i);
                canvas.remove(tableLabels.get(i));
                canvas.remove(tableCountLabels.get(i));

                tableLabels.remove(i);
                tableCountLabels.remove(i);
                break;
            }
        }

        reSortTables();
        canvas.add(addTableButton);
        setContentSize();
    }

    public void reSortTables(){
        for (int i = 0; i < tables.size(); i++) {
            tables.get(i).setTableNumber(i);
            tables.get(i).setPosition(tableMargin * i + 50);
            tableLabels.get(i).setText("Table " + i);
            tableLabels.get(i).setBounds(50, (tableMargin * (i+1)) - tableMargin +30, 50, 20);

            tableCountLabels.get(i).setText("Table " + i);
            tableCountLabels.get(i).setBounds(50, (tableMargin * (i+1)) - tableMargin +30, 50, 20);
        }
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
        scrollPane.setBounds(canvasMarginSide, canvasMarginTop, x - canvasMarginSide * 2, y - canvasMarginTop - canvasMarginBottom );

    }

    public void setCanvasSize() {

        setAddTableButtonPosition(tableMargin * tables.size() + 50);
        int longestTable = 0; // amount of datafields in the biggest Table, used for scaling and stuff
        for (Table t : tables) {
            longestTable = Math.max(longestTable, t.setLength());
        }

        canvas.setPreferredSize(new Dimension(Math.max(baseCanvasLenght, longestTable + tableMarginLenght), Math.max(baseCanvasHeight, tableMargin * (tables.size() + 1))));


    }

    private void loadGeneratorFields() {
        generatorDataFields = new DataField[]{
                new StringDF(),
                new IntegerDF(),
                new DateDF(),
                new ForeignKeyDF()
        };

        generatorDataFieldsString = new String[generatorDataFields.length];

        for (int i = 0; i < generatorDataFields.length; i++) {
            generatorDataFieldsString[i] = generatorDataFields[i].getFieldName();
        }
    }

    private void setAddTableButtonPosition(int position) {
        this.addTableButton.setBounds(50, position, 100, 100);
    }

    public void openSettings(SettingsPage page) {
        if (page != null) {
            JDialog settingsPage = new JDialog(this, Dialog.ModalityType.APPLICATION_MODAL);
            settingsPage.setResizable(false);
            this.settingPage = settingsPage;
            settingsPage.setContentPane(page);
            settingsPage.setBounds(100, 100, page.getWidth(), page.getHeight());
            settingsPage.repaint();
            settingsPage.revalidate();
            settingsPage.setVisible(true);

        } else
            System.err.println("Can't open settings, because one of the values is null!");
    }

    public void closeSettings() {
        if (this.settingPage != null) {
            this.settingPage.dispose();
        }
    }

    public void generateData() {
        if (this.tables != null)
            if (this.tables.size() > 0){
                session = new GeneratorSession(this.tables.toArray(new Table[]{}));

                GeneratorPage page = new GeneratorPage(session);
                JDialog dialog = new JDialog(this, Dialog.ModalityType.APPLICATION_MODAL);
                dialog.setContentPane(page);
                dialog.setBounds(100, 100, page.getWidth(), page.getHeight());
                dialog.repaint();
                dialog.revalidate();
                dialog.setVisible(true);
            }
    }

    public ArrayList<Table> getTables() {
        return tables;
    }
}
