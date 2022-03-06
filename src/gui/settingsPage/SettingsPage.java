package gui.settingsPage;

import gui.settingsPage.pageField.PageField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class creates and manages a settings page where the user can change parameters of DataField's
 */
public class SettingsPage extends JPanel {

    private final String settingsName;
    private final ArrayList<PageField> fields;

    private final Label settingsNameLabel;
    private final JButton cancelButton;
    private final JButton saveButton;

    private final int width = 400;
    private final int heightPerField = 80;

    private final VisualDataField field;

    public SettingsPage(String settingsName, VisualDataField dataField, PageField ... fields) {
        this.field = dataField;
        this.settingsName = settingsName;
        this.fields = new ArrayList<>();

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        this.settingsNameLabel = new Label(settingsName);
        this.add(this.settingsNameLabel);

        int height = 10;

        for (PageField field : fields) {
            height += heightPerField;

            this.fields.add(field);
            JPanel panel = new JPanel();
            BoxLayout panelLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
            panel.setLayout(panelLayout);

            Label fieldName = new Label(field.getFieldName());
            panel.add(fieldName);
            panel.add(field.getComponent());

            this.add(panel);
        }
        this.setBounds(0, 0, width, height);

        this.cancelButton = new JButton("Cancel");
        this.saveButton = new JButton("Save");

        SettingsPage sPage = this;

        this.cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closeSettings();
            }
        });


        this.saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (PageField field : fields) {
                    field.getAction().onSave(field);
                }
                closeSettings();
            }
        });

        JPanel panel = new JPanel();
        BoxLayout panelLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(panelLayout);
        panel.add(saveButton);
        panel.add(cancelButton);

        for (PageField field : this.fields) {
            field.getSetter().setDefaultData(field.getComponent());
        }

        this.add(panel);
    }

    public String getSettingsNameLabel() {
        return settingsName;
    }

    public void closeSettings() {
        field.closeSettings();
    }
}
