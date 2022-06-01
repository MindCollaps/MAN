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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

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
        long startSeconds = minDate.getTime();
        long endSeconds = maxDate.getTime();
        long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        try {
            return new FieldData(parseString(new Date(random)));
        } catch (Exception ignored) {
        }
        return new FieldData("01.01.2002");
    }

    @Override
    public SettingsPage getSettingsPage(VisualDataField field) {
        PageField<JTextField> minDateField = new PageField<>("Min Date", new JTextField(), new PageFieldGrabber<JTextField>() {
            @Override
            public String getDataString(PageField<JTextField> page, JTextField jTextField) {
                return jTextField.getText();
            }
        }, new PageFieldAction<JTextField>() {
            @Override
            public void onSave(PageField<JTextField> field) {
                try {
                    minDate = parseDate(field.getDataString());
                } catch (Exception e) {
                    //TODO: print error message
                }
            }
        }, new DefaultValueSetter<JTextField>() {
            @Override
            public void setDefaultData(JTextField component, SettingsPage page) {
                try {
                    component.setText(parseString(minDate));
                } catch (Exception ignored) {
                }
            }
        });

        PageField<JTextField> maxDateField = new PageField<>("Max Date", new JTextField(), new PageFieldGrabber<JTextField>() {
            @Override
            public String getDataString(PageField<JTextField> page, JTextField jTextField) {
                return jTextField.getText();
            }
        }, new PageFieldAction<JTextField>() {
            @Override
            public void onSave(PageField<JTextField> pageField) {
                try {
                    maxDate = parseDate(pageField.getDataString());
                } catch (Exception e) {
                    //TODO: print error message
                }
            }
        }, new DefaultValueSetter<JTextField>() {
            @Override
            public void setDefaultData(JTextField component, SettingsPage page) {
                try {
                    component.setText(parseString(maxDate));
                } catch (Exception ignored) {

                }
            }
        });

        return new SettingsPage("Date Field Settings", this, field, minDateField, maxDateField);
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
        return format.parse(data);
    }

    private String parseString(Date data) throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(data);
    }
}
