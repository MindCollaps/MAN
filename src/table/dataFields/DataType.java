package table.dataFields;

public enum DataType {

    String("varchar(255)", false),
    Number("int", true),
    Boolean("boolean", true),
    Date("datetime", false);

    private final String sqlIndicator;
    //Give information if value needs to be surrounded by ""
    private final boolean isValuePlain;

    DataType(String sqlIndicator, boolean isValuePlain) {
        this.sqlIndicator = sqlIndicator;
        this.isValuePlain = isValuePlain;
    }

    public String getSqlIndicator() {
        return this.sqlIndicator;
    }

    public boolean isValuePlain() {
        return isValuePlain;
    }
}
