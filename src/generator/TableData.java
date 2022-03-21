package generator;

import gui.pages.settingsPage.VisualDataField;
import table.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TableData {

    private ArrayList<String> errors;

    private final String tableName;
    private final DataFieldData[] dataFieldData;

    private final String guiName;

    public TableData(GeneratorSession session, Table table) {
        this.errors = new ArrayList<>();

        guiName = table.getTableGuiName();

        if (table.getTableName().length() == 0) {
            errors.add("Table " + table.getTableGuiName() + " has no valid name!");
            session.noticedError();
        }

        if (table.getTableCount() < 0) {
            errors.add("Table " + table.getTableGuiName() + " has no valid count!");
            session.noticedError();
        }

        this.tableName = table.getTableName();

        dataFieldData = new DataFieldData[table.getDataFields().size()];

        table.getDataFields().sort(new Comparator<VisualDataField>() {
            @Override
            public int compare(VisualDataField o1, VisualDataField o2) {
                if (o1.getDataField().getPriority() < o2.getDataField().getPriority())
                    return 1;
                else
                    return -1;
            }
        });

        for (int i = 0; i < table.getDataFields().size(); i++) {
            VisualDataField dataField = table.getDataFields().get(i);

            if (dataField.getFieldName().length() == 0) {
                errors.add("DataField" + dataField.getDataFieldNumber() + " in " + table.getTableGuiName() + " has no valid field name!");
                session.noticedError();
            }


            dataFieldData[i] = new DataFieldData(session, dataField, table.getTableCount());
        }

        for (int i = 0; i < dataFieldData.length; i++) {
            if(dataFieldData[i].getDataFieldName().length() == 0)
                continue;

            for (int j = i+1; j < dataFieldData.length; j++) {
                if(dataFieldData[i].getDataFieldName().equals(dataFieldData[j].getDataFieldName())){
                    errors.add("DataField " + dataFieldData[i].getDataFieldName() + " has the same name as " + dataFieldData[j].getDataFieldName() + "!");
                }
            }
        }
    }

    public DataFieldData[] getDataFieldData() {
        return dataFieldData;
    }

    public String getTableName() {
        return tableName;
    }

    /**
     * @return Returns a SQL statement that can't be used to insert data into a existing table
     */
    public String getInsertString() {
        StringBuilder data = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < dataFieldData.length; i++) {
            data.append(dataFieldData[i].getDataFieldName());
            if (i + 1 != dataFieldData.length)
                data.append(", ");
        }

        data.append(") VALUES ");
        for (int i = 0; i < dataFieldData[0].getFieldData().length; i++) {
            for (int j = 0; j < dataFieldData.length; j++) {
                if (j == 0)
                    data.append("(");

                data.append(dataFieldData[j].getFieldData()[i].getData(dataFieldData[j].getDataType()));
                if (j + 1 != dataFieldData.length)
                    data.append(", ");
            }

            if (i + 1 != dataFieldData[0].getFieldData().length)
                data.append("), ");
        }
        data.append(")");

        return data.toString();
    }

    /**
     * @return Returns a SQL statement which creates a table with its fields and Datatypes
     */
    public String getCreateStatement() {
        StringBuilder data = new StringBuilder("CREATE TABLE " + tableName + " (");
        for (int i = 0; i < dataFieldData.length; i++) {
            data.append(dataFieldData[i].getDataFieldName() + " " + dataFieldData[i].getDataType().getSqlIndicator());
            if (i + 1 != dataFieldData.length)
                data.append(", ");
        }

        data.append(")");

        return data.toString();
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public String getGuiName() {
        return guiName;
    }

    @Override
    public String toString() {
        return "TableData{" +
                "tableName='" + tableName + '\'' +
                ", dataFieldData=" + Arrays.toString(dataFieldData) +
                '}';
    }
}
