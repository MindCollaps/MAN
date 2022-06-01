package generator;

import table.Table;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The generator session handles anything that is related to generating data and SQL statements
 */
public class GeneratorSession {

    private boolean hasErrors;

    private ArrayList<String> errors;

    private final Table[] tables;

    private TableData[] tableData;

    private Table currentTable;
    private TableData currentTableData;

    public GeneratorSession(Table[] tables) {
        this.tables = tables;
        hasErrors = false;

        gatherInformation();
    }

    public void gatherInformation() {
        errors = new ArrayList<>();
        tableData = new TableData[tables.length];
        for (int i = 0; i < tables.length; i++) {
            tableData[i] = new TableData(this, tables[i]);
            currentTable = tables[i];
            currentTableData = tableData[i];
            tableData[i].gatherInformation();

            if (tableData[i].getErrors().size() != 0)
                errors.addAll(tableData[i].getErrors());
        }

        currentTable = null;
        currentTableData = null;

        for (int i = 0; i < tableData.length; i++) {
            if (tableData[i].getTableName().length() == 0)
                continue;

            for (int j = i + 1; j < tableData.length; j++) {
                if (tableData[j].getTableName().length() == 0)
                    continue;
                if (tableData[i].getTableName().equals(tableData[j].getTableName())) {
                    errors.add(tableData[i].getGuiName() + " has the same name as " + tableData[j].getGuiName());
                }
            }
        }

        for (TableData tableDatum : tableData) {
            System.out.println(tableDatum);
        }

        System.out.println("Errors: ");
        System.out.println(getErrors());
    }

    public String getInsert() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tableData.length; i++) {
            TableData data = tableData[i];
            //Skip if nothing was created
            if (data.getDataFieldData().length == 0)
                continue;

            builder.append(data.getInsertString());
            if (i + 1 != tableData.length)
                builder.append("\n");
        }
        return builder.toString();
    }

    public String getCreateStatement() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tableData.length; i++) {
            builder.append(tableData[i].getCreateStatement());
            if (i + 1 != tableData.length)
                builder.append("\n");
        }
        return builder.toString();
    }

    public void noticedError() {
        hasErrors = true;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public TableData[] getTableData() {
        return Arrays.copyOf(tableData, tableData.length);
    }

    public Table[] getTables() {
        return Arrays.copyOf(tables, tables.length);
    }

    public Table getCurrentTable() {
        return currentTable;
    }

    public TableData getCurrentTableData() {
        return currentTableData;
    }

    public String getErrors() {
        StringBuilder builder = new StringBuilder();
        for (String error : errors) {
            builder.append(error).append("\n");
        }
        return builder.toString();
    }
}
