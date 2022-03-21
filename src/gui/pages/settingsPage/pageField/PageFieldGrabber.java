package gui.pages.settingsPage.pageField;

import java.awt.*;

/**
 * This class is for getting data from the gui and converting data from for example a JTextField to a String or Boolean
 * @param <T> The Component that is used to fetch data from the gui, for example JTextField
 */
public interface PageFieldGrabber<T extends Component> {

    default String getDataString(PageField<T> page, T t) {
        System.err.println("The getDataString() action is not implemented for " + page.getFieldName());
        return null;
    }

    default int getDataInt(PageField<T> page, T t) {
        System.err.println("The getDataInt() action is not implemented for " + page.getFieldName());
        return 0;
    }

    default boolean getBoolean(PageField<T> page, T t) {
        System.err.println("The getBoolean() action is not implemented for " + page.getFieldName());
        return false;
    }
}
