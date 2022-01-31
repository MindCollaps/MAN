package table.dataFields;

import generator.GeneratorSession;
import gui.SettingsPage;
import gui.VisualEngine;

import javax.swing.*;

public abstract class DataField {

    private JComboBox<String> comboBox;

    /**
     * Priority 0 is the highest value, everything higher than 0 will come after 0
     * @return Returns  priority value
     */
    public abstract int getPriority();
    public abstract FieldData getData(GeneratorSession session);
    public abstract SettingsPage getSettingsPage();
    public abstract DataType getDataType();
    public abstract String getFieldName();
}
