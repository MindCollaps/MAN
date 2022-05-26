package generator;

import gui.pages.settingsPage.VisualDataField;
import table.dataFields.DataType;

import java.util.Arrays;

public class DataFieldData {

    private final String dataFieldName;
    private final FieldData[] fieldData;
    private final DataType dataType;
    private final String guiName;

    public DataFieldData(GeneratorSession session, VisualDataField field, int amount) {
        this.dataFieldName = field.getFieldName();
        this.dataType = field.getDataField().getDataType();
        this.guiName = field.getDataFieldNumber();

        if(amount == -1){
            fieldData = new FieldData[0];
            return;
        }
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

    public String getGuiName() {
        return guiName;
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
