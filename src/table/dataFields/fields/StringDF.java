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

public class StringDF extends DataField {

    //Settings
    private boolean isName = false;
    private int length = 10;

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
        PageField<JCheckBox> isNameField = new PageField<>("Is Name Field", new JCheckBox(), new PageFieldGrabber<JCheckBox>() {
            @Override
            public boolean getBoolean(PageField<JCheckBox> page, JCheckBox jCheckBox) {
                return jCheckBox.isSelected();
            }
        }, new SettingsAction() {
            @Override
            public void onSave(PageField field) {
                isName = field.getBoolean();
            }
        });

        PageField<JTextField> lengthField = new PageField<>("Is Name Field", new JTextField(), new PageFieldGrabber<JTextField>() {
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
                    length = Integer.parseInt(field.getDataString());
                } catch (Exception e) {
                    //TODO: Print error message
                }
            }
        });

        return new SettingsPage("String Field Settings", field, lengthField, isNameField);
    }

    @Override
    public DataType getDataType() {
        return DataType.String;
    }

    @Override
    public String getFieldName() {
        return "String";
    }
}
