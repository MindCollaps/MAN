package table.dataFields.fields;

import dataset.StringDataSet;
import generator.FieldData;
import generator.GeneratorSession;
import gui.pages.settingsPage.SettingsPage;
import gui.pages.settingsPage.VisualDataField;
import gui.pages.settingsPage.pageField.DefaultValueSetter;
import gui.pages.settingsPage.pageField.PageField;
import gui.pages.settingsPage.pageField.PageFieldAction;
import gui.pages.settingsPage.pageField.PageFieldGrabber;
import table.dataFields.DataField;
import table.dataFields.DataType;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class StringDF extends DataField {

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase();
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;

    //Settings
    private final String[] typeValues = {"random", "name", "surname", "animal", "city", "country"};
    private String type = typeValues[0];
    private int length = 10;

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public FieldData getData(GeneratorSession session) {
        switch (type) {
            default:
            case "random":
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    sb.append(alphanum.charAt(ThreadLocalRandom.current().nextInt(0, alphanum.length())));
                }
                return new FieldData(sb.toString());

            case "name":
                return new FieldData(StringDataSet.names[ThreadLocalRandom.current().nextInt(0, StringDataSet.names.length - 1)]);

            case "surname":
                return new FieldData(StringDataSet.surname[ThreadLocalRandom.current().nextInt(0, StringDataSet.surname.length - 1)]);

            case "animal":
                return new FieldData(StringDataSet.animals[ThreadLocalRandom.current().nextInt(0, StringDataSet.animals.length - 1)]);

            case "city":
                return new FieldData(StringDataSet.cityNames[ThreadLocalRandom.current().nextInt(0, StringDataSet.cityNames.length - 1)]);

            case "country":
                return new FieldData(StringDataSet.country[ThreadLocalRandom.current().nextInt(0, StringDataSet.country.length - 1)]);
        }
    }

    @Override
    public SettingsPage getSettingsPage(VisualDataField field) {
        PageField<JComboBox<String>> typeSelector = new PageField<>("Generator type", new JComboBox<String>(typeValues), new PageFieldGrabber<JComboBox<String>>() {
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
                for (int i = 0; i < typeValues.length; i++) {
                    if (type.equals(typeValues[i])) {
                        component.setSelectedIndex(i);
                        break;
                    }
                }
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

        return new SettingsPage("String Field Settings", field, typeSelector, lengthField);
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
