package gui.settingsPage.pageField;

import java.awt.*;

public interface DefaultValueSetter <T extends Component> {

    public void setDefaultData(T component);
}
