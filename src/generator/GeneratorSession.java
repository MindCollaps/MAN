package generator;

import table.Table;

public class GeneratorSession {

    private Table[] tables;

    private TableData[] tableData;

    public GeneratorSession(Table[] tables) {
        this.tables = tables;

        gatherInformation();
        System.out.println(getCreateStatement());
        System.out.println(getInsert());

    }

    public void gatherInformation() {
        tableData = new TableData[tables.length];
        for (int i = 0; i < tables.length; i++) {
            tableData[i] = new TableData(this, tables[i]);
        }

        for (TableData tableDatum : tableData) {
            System.out.println(tableDatum);
        }
    }

    public String getInsert() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tableData.length; i++) {
            TableData data = tableData[i];
            //Skip if nothing was created
            if(data.getDataFieldData().length == 0)
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
}
