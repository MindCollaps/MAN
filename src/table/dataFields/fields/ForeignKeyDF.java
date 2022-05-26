package table.dataFields.fields;

import generator.DataFieldData;
import generator.FieldData;
import generator.GeneratorSession;
import generator.TableData;
import gui.pages.settingsPage.SettingsPage;
import gui.pages.settingsPage.VisualDataField;
import gui.pages.settingsPage.pageField.DefaultValueSetter;
import gui.pages.settingsPage.pageField.PageField;
import gui.pages.settingsPage.pageField.PageFieldAction;
import gui.pages.settingsPage.pageField.PageFieldGrabber;
import table.Table;
import table.dataFields.DataField;
import table.dataFields.DataType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ForeignKeyDF extends DataField {

    private final boolean isPrimaryKey = true;
    private Table foreignTable;

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public FieldData getData(GeneratorSession session) {
        Table[] tables = session.getTables();

        TableData data = null;
        for (int i = 0; i < tables.length; i++) {
            if(tables[i] == foreignTable){
                data = session.getTableData()[i];
                break;
            }
        }
        if(data != null){
            for (DataFieldData dataFieldDatum : data.getDataFieldData()) {
                if(dataFieldDatum.getDataType() == DataType.PrimaryKey){
                    return dataFieldDatum.getFieldData()[ThreadLocalRandom.current().nextInt(0, dataFieldDatum.getFieldData().length-1)];
                }
            }
        }
        return new FieldData("0");
    }

    @Override
    public SettingsPage getSettingsPage(VisualDataField field) {
        ArrayList<Table> tableArrayList = field.getTable().getAllTables();
        String[] tableNames = new String[tableArrayList.size()];
        for (int i = 0; i < tableArrayList.size(); i++) {
            tableNames[i] = tableArrayList.get(i).getTableName();
        }

        PageField<JComboBox<String>> foreignTableSelector = new PageField<>("Foreign Table", new JComboBox<>(tableNames)
                , new PageFieldGrabber<JComboBox<String>>() {
            @Override
            public int getDataInt(PageField<JComboBox<String>> page, JComboBox<String> stringJComboBox) {
                return stringJComboBox.getSelectedIndex();
            }
        }
                , new PageFieldAction<JComboBox<String>>() {
            @Override
            public void onSave(PageField<JComboBox<String>> pageField) {
                foreignTable = tableArrayList.get(pageField.getDataInt());
            }
        }, new DefaultValueSetter<JComboBox<String>>() {
            @Override
            public void setDefaultData(JComboBox<String> component) {
                if(foreignTable == null)
                    return;
                for (int i = 0; i < tableNames.length; i++) {
                    if(foreignTable.getTableName().equals(tableNames[i])){
                        component.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        return new SettingsPage("Foreign Key Settings", field, foreignTableSelector);
    }

    @Override
    public DataType getDataType() {
        return DataType.ForeignKey;
    }

    @Override
    public String getFieldName() {
        return "ForeignKey";
    }
}
