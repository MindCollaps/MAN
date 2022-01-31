package table.dataFields;

public enum DataType {

    String("VARCHAR"),
    Number("NUMBER"),
    Boolean("BOOLEAN")
    , Date("DATETIME");

    private final String sqlIndicator;

    DataType (String sqlIndicator){
        this.sqlIndicator = sqlIndicator;
    }

    public String getSqlIndicator(){
        return this.sqlIndicator;
    }
}
