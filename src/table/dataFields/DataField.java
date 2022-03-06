package table.dataFields;

import generator.FieldData;
import generator.GeneratorSession;
import gui.settingsPage.SettingsPage;
import gui.settingsPage.VisualDataField;

import javax.swing.*;

/**
 * The DataField gets extended by DataFields to ensure functionality to them
 */
public abstract class DataField {

    private JComboBox<String> comboBox;

    /**
     * Priority 0 is the highest value, everything higher than 0 will come after 0
     *
     * @return Returns  priority value
     */
    public abstract int getPriority();

    /**
     * This method creates the data in the end
     * @param session The generator session that's used to create the data
     * @return Returns the FieldData of this DataField
     */
    public abstract FieldData getData(GeneratorSession session);

    /**
     * This method generates a SettingsPage so that the user can change settings of a DataField
     * @param field The VisualDataField that holds the DataField
     * @return Returns the settings page of the DataField
     */
    public abstract SettingsPage getSettingsPage(VisualDataField field);

    /**
     * This method gives the DataType which is important for SQL Text generation
     * @return Returns the DataType of the DataField, that a valid SQL text can be created
     */
    public abstract DataType getDataType();

    public abstract String getFieldName();
}
