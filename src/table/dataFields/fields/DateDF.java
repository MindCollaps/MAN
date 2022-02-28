package table.dataFields.fields;

import generator.GeneratorSession;
import gui.SettingsPage;
import table.dataFields.DataField;
import table.dataFields.DataType;
import table.dataFields.FieldData;

public class DateDF extends DataField {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public FieldData getData(GeneratorSession session) {
        return null;
    }

    @Override
    public SettingsPage getSettingsPage() {
        return null;
    }

    @Override
    public DataType getDataType() {
        return DataType.Date;
    }

    @Override
    public String getFieldName() {
        return "Date";
    }
}
