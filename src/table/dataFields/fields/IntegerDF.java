package table.dataFields.fields;

import generator.GeneratorSession;
import gui.settingsPage.SettingsAction;
import gui.settingsPage.SettingsPage;
import gui.settingsPage.VisualDataField;
import gui.settingsPage.pageField.PageField;
import gui.settingsPage.pageField.PageFieldGrabber;
import table.dataFields.DataField;
import table.dataFields.DataType;
import table.dataFields.FieldData;

import javax.swing.*;

public class IntegerDF extends DataField {

    //Settings
    private int maxInt = 50000;
    private int minInt = 0;

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public FieldData getData(GeneratorSession session) {
        return null;
    }

    @Override
    public SettingsPage getSettingsPage(VisualDataField field) {
        PageField<JTextField> minIntField = new PageField<>("Min Value", new JTextField(minInt), new PageFieldGrabber<JTextField>() {
            @Override
            public String getDataString(PageField<JTextField> page, JTextField jTextField) {
                return jTextField.getText();
            }

            @Override
            public int getDataInt(PageField<JTextField> page, JTextField jTextField) {
                try {
                    return Integer.parseInt(jTextField.getText());
                } catch (Exception e) {
                    //TODO: Print error message
                }
                return 0;
            }
        }, new SettingsAction<JTextField>() {
            @Override
            public void onSave(PageField<JTextField> field) {
                try {
                    minInt = Integer.parseInt(field.getDataString());
                } catch (Exception e) {
                    //TODO: Print error message
                }
            }
        });

        PageField<JTextField> maxIntField = new PageField<>("Max Value", new JTextField(minInt), new PageFieldGrabber<JTextField>() {
            @Override
            public String getDataString(PageField<JTextField> page, JTextField jTextField) {
                return jTextField.getText();
            }

            @Override
            public int getDataInt(PageField<JTextField> page, JTextField jTextField) {
                try {
                    return Integer.parseInt(jTextField.getText());
                } catch (Exception e) {
                    //TODO: Print error message
                }
                return 0;
            }
        }, new SettingsAction<JTextField>() {
            @Override
            public void onSave(PageField<JTextField> field) {
                try {
                    maxInt = Integer.parseInt(field.getDataString());
                } catch (Exception e) {
                    //TODO: Print error message
                }
            }
        });

        return new SettingsPage("Integer Field Settings", field, minIntField, maxIntField);
    }

    @Override
    public DataType getDataType() {
        return DataType.String;
    }

    @Override
    public String getFieldName() {
        return "Int";
    }
}
