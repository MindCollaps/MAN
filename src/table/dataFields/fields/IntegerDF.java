package table.dataFields.fields;

import generator.FieldData;
import generator.GeneratorSession;
import gui.pages.settingsPage.SettingsPage;
import table.dataFields.VisualDataField;
import gui.pages.settingsPage.pageField.DefaultValueSetter;
import gui.pages.settingsPage.pageField.PageField;
import gui.pages.settingsPage.pageField.PageFieldAction;
import gui.pages.settingsPage.pageField.PageFieldGrabber;
import table.dataFields.DataField;
import table.dataFields.DataType;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

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
        return new FieldData(Integer.toString(ThreadLocalRandom.current().nextInt(minInt, maxInt)));
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
        }, new PageFieldAction<JTextField>() {
            @Override
            public void onSave(PageField<JTextField> pageField) {
                try {
                    minInt = Integer.parseInt(pageField.getDataString());
                } catch (Exception e) {
                    //TODO: Print error message
                }
            }
        }, new DefaultValueSetter<JTextField>() {
            @Override
            public void setDefaultData(JTextField component, SettingsPage page) {
                component.setText(String.valueOf(minInt));
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
        }, new PageFieldAction<JTextField>() {
            @Override
            public void onSave(PageField<JTextField> pageField) {
                try {
                    maxInt = Integer.parseInt(pageField.getDataString());
                } catch (Exception e) {
                    //TODO: Print error message
                }
            }
        }, new DefaultValueSetter<JTextField>() {
            @Override
            public void setDefaultData(JTextField component, SettingsPage page) {
                component.setText(String.valueOf(maxInt));
            }
        });

        return new SettingsPage("Integer Field Settings", this, field, minIntField, maxIntField);
    }

    @Override
    public DataType getDataType() {
        return DataType.Number;
    }

    @Override
    public String getFieldName() {
        return "int";
    }
}
