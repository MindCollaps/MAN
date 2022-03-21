package gui.pages.generatorPage;

import generator.GeneratorSession;

import javax.swing.*;
import java.awt.*;

public class GeneratorPage extends JPanel {

    private final GeneratorSession session;
    private JCheckBox generate_tables;
    private JCheckBox generate_inserts;
    private JButton printText;
    private JButton saveText;
    private JLabel generate_tables_text;
    private JLabel generate_insert_text;
    private final int marginY = 10;
    private final int marginX = 20;


    public GeneratorPage(GeneratorSession session) {
        this.session = session;

        setBounds(0, 0, 180, 230);
        setLayout(null);

        generate_tables = new JCheckBox();
        generate_tables.setBounds(marginX,marginY,30,30);
        add(generate_tables);

        generate_inserts = new JCheckBox();
        generate_inserts.setBounds(marginX,marginY+30,30,30);
        add(generate_inserts);

        generate_tables_text = new JLabel("create generate_tables");
        generate_tables_text.setBounds(marginX + 30,marginY,1000,30);
        add(generate_tables_text);

        generate_insert_text = new JLabel("create generate_inserts");
        generate_insert_text.setBounds(marginX + 30,marginY+30,1000,30);
        add(generate_insert_text);

        printText = new JButton("Print Text");
        printText.setBounds(marginX,marginY+30*2,100,40);
        add(printText);

        saveText = new JButton("Save Text");
        saveText.setBounds(marginX,marginY +30*2 +50,100,40);
        add(saveText);



    }
}
