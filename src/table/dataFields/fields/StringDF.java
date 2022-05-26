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
    private String[] typeValues = {"random", "name", "animal", "city", "country"};
    private String type = typeValues[0];
    private int length = 10;

    //lists
    private String[] name = {"Noah", "Elias"};
    private String[] surname = {};
    private String[] animals = {};
    private String[] citys = {};
    private String[] countrys = {};

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public FieldData getData(GeneratorSession session) {
        switch (type){
            default:
            case "random":
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(alphanum.charAt(ThreadLocalRandom.current().nextInt(0, alphanum.length())));
            }
            return new FieldData(sb.toString());

            case "name":
                return new FieldData(name[ThreadLocalRandom.current().nextInt(0, name.length)]);

            case "animal":
                return new FieldData("Hund");
                //TODO: make random

            case "city":
                return new FieldData("Ohio");
                //TODO: make random

            case "country":
                return new FieldData("Amerika");
                //TODO: make random
        }
    }

    @Override
    public SettingsPage getSettingsPage(VisualDataField field) {
        PageField<JComboBox<String>> type = new PageField<>("Generator type", new JComboBox<String>(typeValues), new PageFieldGrabber<JComboBox<String>>() {
            @Override
            public String getDataString(PageField<JComboBox<String>> page, JComboBox<String> jCheckBox) {
                return typeValues[jCheckBox.getSelectedIndex()];
            }
        }, new PageFieldAction<JComboBox<String>>() {
            @Override
            public void onSave(PageField<JComboBox<String>> pageField) {
                StringDF.this.type = pageField.getDataString();
            }
        }, new DefaultValueSetter<JComboBox<String>>() {
            @Override
            public void setDefaultData(JComboBox<String> component) {
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

        return new SettingsPage("String Field Settings", field, type, lengthField);
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
