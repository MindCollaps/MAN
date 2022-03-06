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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDF extends DataField {

    //Settings
    private Date minDate;
    private Date maxDate;

    public DateDF() {
        this.maxDate = new Date();
        try {
            this.minDate = parseDate("01.01.1955");
        } catch (Exception ignored) {
        }

    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public FieldData getData(GeneratorSession session) {
        return null;
    }

    @Override
    public SettingsPage getSettingsPage(VisualDataField field) {
        PageField<JTextField> minDateField = new PageField<>("Min Date", new JTextField(), new PageFieldGrabber<JTextField>() {
            @Override
            public String getDataString(PageField<JTextField> page, JTextField jTextField) {
                return jTextField.getText();
            }
        }, new SettingsAction<JTextField>() {
            @Override
            public void onSave(PageField<JTextField> field) {
                try {
                    minDate = parseDate(field.getDataString());
                } catch (Exception e) {
                    //TODO: print error message
                }
            }
        });

        PageField<JTextField> maxDateField = new PageField<>("Max Date", new JTextField(), new PageFieldGrabber<JTextField>() {
            @Override
            public String getDataString(PageField<JTextField> page, JTextField jTextField) {
                return jTextField.getText();
            }
        }, new SettingsAction<JTextField>() {
            @Override
            public void onSave(PageField<JTextField> field) {
                try {
                    maxDate = parseDate(field.getDataString());
                } catch (Exception e) {
                    //TODO: print error message
                }
            }
        });

        return new SettingsPage("Date Field Settings", field, minDateField, maxDateField);
    }

    @Override
    public DataType getDataType() {
        return DataType.Date;
    }

    @Override
    public String getFieldName() {
        return "Date";
    }

    private Date parseDate(String data) throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        return format.parse(data);
    }
}
