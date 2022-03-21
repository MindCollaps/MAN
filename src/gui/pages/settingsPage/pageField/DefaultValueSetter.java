package gui.pages.settingsPage.pageField;

import java.awt.*;

/**
 * This class ensures that a Component of type T always has a default value set when loaded
 * @param <T> The Component that is used to fetch data from the gui, for example JTextField
 */
public interface DefaultValueSetter <T extends Component> {

    public void setDefaultData(T component);
}
