package table.dataFields.fields;

import generator.GeneratorSession;
import gui.pages.settingsPage.pageField.PageFieldAction;
import gui.pages.settingsPage.SettingsPage;
import gui.pages.settingsPage.VisualDataField;
import gui.pages.settingsPage.pageField.DefaultValueSetter;
import gui.pages.settingsPage.pageField.PageField;
import gui.pages.settingsPage.pageField.PageFieldGrabber;
import table.dataFields.DataField;
import table.dataFields.DataType;
import generator.FieldData;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class StringDF extends DataField {

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase();
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;

    //Settings
    private boolean isName = false;
    private int length = 10;

    private String[] vornamen = {"Noah", "Elias"};

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public FieldData getData(GeneratorSession session) {
        if(isName){
            return new FieldData(vornamen[ThreadLocalRandom.current().nextInt(0, vornamen.length)]);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(alphanum.charAt(ThreadLocalRandom.current().nextInt(0, alphanum.length())));
            }
            return new FieldData(sb.toString());
        }
    }

    @Override
    public SettingsPage getSettingsPage(VisualDataField field) {
        PageField<JCheckBox> isNameField = new PageField<>("Is Name Field", new JCheckBox(), new PageFieldGrabber<JCheckBox>() {
            @Override
            public boolean getBoolean(PageField<JCheckBox> page, JCheckBox jCheckBox) {
                return jCheckBox.isSelected();
            }
        }, new PageFieldAction() {
            @Override
            public void onSave(PageField pageField) {
                isName = pageField.getBoolean();
            }
        }, new DefaultValueSetter<JCheckBox>() {
            @Override
            public void setDefaultData(JCheckBox component) {
                component.setSelected(isName);
            }
        });

        PageField<JTextField> lengthField = new PageField<>("String length", new JTextField(), new PageFieldGrabber<JTextField>() {
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
                    length = Integer.parseInt(pageField.getDataString());
                } catch (Exception e) {
                    //TODO: Print error message
                }
            }
        }, new DefaultValueSetter<JTextField>() {
            @Override
            public void setDefaultData(JTextField component) {
                component.setText(String.valueOf(length));
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
