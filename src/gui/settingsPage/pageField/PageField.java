package gui.settingsPage.pageField;

import java.awt.*;

/**
 * This class is a field in the settings page and manages the user input
 * @param <T> The Component that is used to fetch data from the gui, for example JTextField
 */
public class PageField<T extends Component> {

    private final String fieldName;
    private final PageFieldGrabber<T> grabber;
    private final T component;
    private final PageFieldAction action;
    private final DefaultValueSetter setter;

    /**
     * @param fieldName The name of the field that can be given a data
     * @param component The component where the data can be input
     */
    public PageField(String fieldName, T component, PageFieldGrabber<T> grabber, PageFieldAction<T> action, DefaultValueSetter<T> setter) {
        this.action = action;
        this.fieldName = fieldName;
        this.component = component;
        this.grabber = grabber;
        this.setter = setter;
    }

    public PageFieldAction getAction() {
        return action;
    }

    public PageFieldGrabber<T> getGrabber() {
        return grabber;
    }

    public T getComponent() {
        return component;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getDataString() {
        return grabber.getDataString(this, component);
    }

    public int getDataInt() {
        return grabber.getDataInt(this, component);
    }

    public boolean getBoolean() {
        return grabber.getBoolean(this, component);
    }

    public DefaultValueSetter getSetter() {
        return setter;
    }
}
