package gui;

import gui.pageField.PageField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SettingsPage extends JPanel {

    private Label settingName;
    private ArrayList<PageField> fields;

    public SettingsPage(String settingsName, ArrayList<PageField> fields) {
        this.settingName = new Label(settingsName);

        this.add(this.settingName);

        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(layout);

        for (PageField field : fields) {
            JPanel panel = new JPanel();
            BoxLayout panelLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(panelLayout);

            Label fieldName = new Label(field.getFieldName());
            panel.add(fieldName);
            panel.add(field.getComponent());

            this.add(panel);
        }
    }
}
