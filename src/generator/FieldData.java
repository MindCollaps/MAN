package generator;

import table.dataFields.DataType;

/**
 * This class give the basis for the SQL Text generation
 */
public record FieldData(String data) {

    public String getData(DataType type) {
        if (type.isValuePlain())
            return data;
        else
            return "'" + data + "'";
    }

    public String getValuePlain(){
        return data;
    }

    @Override
    public String toString() {
        return "FieldData{" +
                "data='" + data + '\'' +
                '}';
    }
}
