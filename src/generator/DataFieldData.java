package generator;

import gui.settingsPage.VisualDataField;
import table.dataFields.DataField;
import table.dataFields.DataType;

import java.util.Arrays;

public class DataFieldData {

    private final String dataFieldName;
    private final FieldData[] fieldData;
    private final DataType dataType;

    public DataFieldData(GeneratorSession session, VisualDataField field, int amount) {
        this.dataFieldName = field.getFieldName();
        this.dataType = field.getDataField().getDataType();

        fieldData = new FieldData[amount];
        for (int i = 0; i < amount; i++) {
            fieldData[i] = field.getDataField().getData(session);
        }
    }

    public FieldData[] getFieldData() {
        return fieldData;
    }

    public DataType getDataType() {
        return dataType;
    }

    public String getDataFieldName() {
        return dataFieldName;
    }

    @Override
    public String toString() {
        return "DataFieldData{" +
                "dataFieldName='" + dataFieldName + '\'' +
                ", fieldData=" + Arrays.toString(fieldData) +
                ", dataType=" + dataType +
                '}';
    }
}
