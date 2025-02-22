package view;

import javafx.beans.property.SimpleStringProperty;
import model.values.IValue;

public class symTableView {

    private SimpleStringProperty name;
    private SimpleStringProperty value;

    public symTableView(String name, IValue value) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value.toString());
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

}
