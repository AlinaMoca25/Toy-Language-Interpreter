package view;

import javafx.beans.property.SimpleStringProperty;
import model.values.IValue;

public class lockView {
    private SimpleStringProperty location;
    private SimpleStringProperty value;


    public lockView(int location, int value) {
        this.location = new SimpleStringProperty(String.valueOf(location));
        this.value = new SimpleStringProperty(String.valueOf(value));
    }


    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }
}
