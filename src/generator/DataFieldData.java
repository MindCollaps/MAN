package generator;

import table.dataFields.VisualDataField;
import table.dataFields.DataType;

import java.util.Arrays;

public class DataFieldData {

    private final GeneratorSession session;

    private final String dataFieldName;
    private FieldData[] fieldData;
    private final DataType dataType;
    private final String guiName;
    private final int amount;

    private int currentIndex;

    private final VisualDataField field;

    public DataFieldData(GeneratorSession session, VisualDataField field, int amount) {
        this.field = field;
        this.dataFieldName = field.getFieldName();
        this.dataType = field.getDataField().getDataType();
        this.guiName = field.getDataFieldString();
        this.amount = amount;
        this.session = session;
    }

    public void gatherInformation(){
        if(amount == -1){
            fieldData = new FieldData[0];
            return;
        }
        fieldData = new FieldData[amount];
        for (int i = 0; i < amount; i++) {
            currentIndex = i;
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

    public VisualDataField getField() {
        return field;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public String toString() {
        return "\nDataFieldData{" +
                "dataFieldName='" + dataFieldName + '\'' +
                ", fieldData=" + Arrays.toString(fieldData) +
                ", dataType=" + dataType +
                '}';
    }
}
