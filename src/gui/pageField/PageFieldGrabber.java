package gui.pageField;

import table.dataFields.DataType;

public abstract class PageFieldGrabber {

    private final DataType type;

    public PageFieldGrabber(DataType type) {
        this.type = type;
    }

    public String getDataString(){
        return null;
    }

    public int getDataInt(){
        return 0;
    }

    public boolean getBoolean(){
        return false;
    }

    public DataType getType() {
        return type;
    }
}
