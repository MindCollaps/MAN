package gui.pageField;

import table.dataFields.DataType;

import java.awt.*;

public class PageField {

    private String fieldName;
    private PageFieldGrabber grabber;
    private Component component;

    public PageField(String fieldName) {
        this.fieldName = fieldName;
    }

    public PageFieldGrabber getGrabber() {
        return grabber;
    }

    public Component getComponent() {
        return component;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getDataString(){
        return grabber.getDataString();
    }

    public int getDataInt(){
        return grabber.getDataInt();
    }

    public boolean getBoolean(){
        return grabber.getBoolean();
    }

    public DataType getType() {
        return grabber.getType();
    }
}
