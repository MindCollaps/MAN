package table.dataFields;

import generator.FieldData;
import generator.GeneratorSession;
import gui.pages.settingsPage.SettingsPage;

import javax.swing.*;

/**
 * The DataField gets extended by DataFields to ensure functionality to them
 */
public abstract class DataField {

    private JComboBox<String> comboBox;
    protected int index;

    public DataField(){
        index = -1;
    }

    public DataField(int index) {
        this.index = index;
    }

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

    /**
     *
     * @return Returns the name of the Field which is shown in the drop-down menu on the GUI
     */
    public abstract String getFieldName();
}
