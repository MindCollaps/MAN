package gui.pages.settingsPage.pageField;

import java.awt.*;

/**
 * This class is used to convert the data in PageField to a variable in one of the DataField's, so that settings can be stored
 * @param <T> The Component that is used to fetch data from the gui, for example JTextField
 */
public interface PageFieldAction<T extends Component> {

    void onSave(PageField<T> pageField);
}
