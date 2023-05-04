package table.dataFields.fields;

import dataset.StringDataSet;
import generator.DataFieldData;
import generator.FieldData;
import generator.GeneratorSession;
import gui.pages.settingsPage.SettingsPage;
import gui.pages.settingsPage.pageField.DefaultValueSetter;
import gui.pages.settingsPage.pageField.PageField;
import gui.pages.settingsPage.pageField.PageFieldAction;
import gui.pages.settingsPage.pageField.PageFieldGrabber;
import table.dataFields.DataField;
import table.dataFields.DataType;
import table.dataFields.VisualDataField;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class StringDF extends DataField {

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase();
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;

    //Settings
    private final String[] typeValues = {"random", "Name", "Surname", "Animal", "City", "Country", "Username", "E-Mail"};
    private String type = typeValues[0];
    private int length = 25;

    public StringDF() {
    }

    public StringDF(int index) {
        super(index);
        switch (this.index) {
            case 0:
                this.type = "Name";
                break;
            case 1:
                this.type = "Surname";
                break;
            case 2:
                this.type = "E-Mail";
                break;
            case 4:
                this.type = "Country";
                break;
            case 5:
                this.type = "City";
                break;
            case 6:
                this.type = "Username";
                break;
            case 7:
                this.type = "random";
                break;
        }
    }

    @Override
    public int getPriority() {
        if (type.equals("E-Mail"))
            return 1;
        return 0;
    }

    @Override
    public FieldData getData(GeneratorSession session) {
        switch (type.toLowerCase()) {
            default:
            case "random":
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    sb.append(alphanum.charAt(ThreadLocalRandom.current().nextInt(0, alphanum.length())));
                }
                return new FieldData(sb.toString());

            case "name":
                return new FieldData(randomString(StringDataSet.names));

            case "surname":
                return new FieldData(randomString(StringDataSet.surname));

            case "animal":
                return new FieldData(randomString(StringDataSet.animals));

            case "city":
                return new FieldData(randomString(StringDataSet.cityNames));

            case "country":
                return new FieldData(randomString(StringDataSet.country));

            case "username":
                return new FieldData(randomString(StringDataSet.pseudonyms));

            case "e-mail":
                String name = null;
                String surname = null;
                for (DataFieldData dataFieldDatum : session.getCurrentTableData().getDataFieldData()) {
                    if (dataFieldDatum == null)
                        continue;
                    if (dataFieldDatum.getField().getDataField() instanceof StringDF) {
                        StringDF df = (StringDF) dataFieldDatum.getField().getDataField();
                        if (df.type.equalsIgnoreCase("name")) {
                            name = dataFieldDatum.getFieldData()[session.getCurrentTableData().getCurrentDataFieldData().getCurrentIndex()].getValuePlain();
                        } else if (df.type.equalsIgnoreCase("surname")) {
                            surname = dataFieldDatum.getFieldData()[session.getCurrentTableData().getCurrentDataFieldData().getCurrentIndex()].getValuePlain();
                        }
                        if (name != null && surname != null)
                            break;
                    }
                }
                if (name == null && surname == null)
                    return new FieldData(randomString(StringDataSet.pseudonyms) + "@" + randomString(StringDataSet.emailDomains) + randomString(StringDataSet.emailDomainEndings));
                else if (name == null)
                    return new FieldData(surname + "@" + randomString(StringDataSet.emailDomains) + randomString(StringDataSet.emailDomainEndings));
                else if (surname == null)
                    return new FieldData(name + "@" + randomString(StringDataSet.emailDomains) + randomString(StringDataSet.emailDomainEndings));
                else
                    return new FieldData(name + "." + surname + "@" + randomString(StringDataSet.emailDomains) + randomString(StringDataSet.emailDomainEndings));
        }
    }

    private String randomString(String[] ar) {
        return ar[ThreadLocalRandom.current().nextInt(0, ar.length - 1)];
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
            public void setDefaultData(JComboBox<String> component, SettingsPage page) {
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
                } catch (Exception ignored) {
                }
            }
        }, new DefaultValueSetter<JTextField>() {
            @Override
            public void setDefaultData(JTextField component, SettingsPage page) {
                component.setText(String.valueOf(length));
            }
        });

        if (!type.equals("random"))
            return new SettingsPage("String Field Settings", this, field, typeSelector);
        else
            return new SettingsPage("String Field Settings", this, field, typeSelector, lengthField);
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
