package gui.pages.generatorPage;

import generator.GeneratorSession;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GeneratorPage extends JPanel {

    private final GeneratorSession session;
    private final JCheckBox generate_tables;
    private final JCheckBox generate_inserts;
    private final JButton printText;
    private final JButton saveText;
    private final JButton generate;
    private final JLabel generate_tables_text;
    private final JLabel generate_insert_text;
    private final JTextPane sqlText;
    private final int marginY = 10;
    private final int marginX = 20;

    private final boolean generated = false;
    private String rawSql;


    public GeneratorPage(GeneratorSession session) {
        this.session = session;

        setBounds(0, 0, 600, 400);
        setLayout(null);

        generate_tables = new JCheckBox();
        generate_tables.setSelected(true);
        generate_tables.setBounds(marginX,marginY,30,30);
        add(generate_tables);

        generate_inserts = new JCheckBox();
        generate_inserts.setSelected(true);
        generate_inserts.setBounds(marginX,marginY+30,30,30);
        add(generate_inserts);

        generate_tables_text = new JLabel("create generate_tables");
        generate_tables_text.setBounds(marginX + 30,marginY,1000,30);
        add(generate_tables_text);

        generate_insert_text = new JLabel("create generate_inserts");
        generate_insert_text.setBounds(marginX + 30,marginY+30,1000,30);
        add(generate_insert_text);

        generate = new JButton("Generate");
        generate.setBounds(marginX + 30,marginY+30*5,100,40);
        generate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                generate();
            }
        });
        add(generate);

        printText = new JButton("Print Text");
        printText.setBounds(marginX + 30,marginY+30*8,100,40);
        printText.setVisible(false);
        add(printText);

        saveText = new JButton("Save Text");
        saveText.setBounds(marginX + 30,marginY +30*8 +50,100,40);
        saveText.setVisible(false);
        add(saveText);

        sqlText = new JTextPane();
        sqlText.setBounds(marginX + 200, marginY, 360, 340);
        add(sqlText);

        if(session.hasErrors()){
            sqlText.setBorder(BorderFactory.createLineBorder( Color.RED));
            StyleContext.NamedStyle centerStyle = StyleContext.getDefaultStyleContext().new NamedStyle();
            StyleConstants.setAlignment(centerStyle,StyleConstants.ALIGN_CENTER);
            sqlText.setLogicalStyle(centerStyle);
            sqlText.setText(session.getErrors());
        } else {
            sqlText.setText("Press Generate to generate your SQL code");
        }
    }

    private void generate(){
        if(session.hasErrors())
            return;
        StringBuilder builder = new StringBuilder();
        if(generate_tables.isSelected()){
            builder.append(session.getCreateStatement()).append("\n");
        }
        if(generate_inserts.isSelected()){
            builder.append(session.getInsert()).append("\n");
        }
        rawSql = builder.toString();
        sqlText.setText(rawSql);
    }
}
