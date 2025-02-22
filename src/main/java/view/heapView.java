package view;

import javafx.beans.property.SimpleStringProperty;
import model.values.IValue;

public class heapView {
    private SimpleStringProperty address;
    private SimpleStringProperty value;


    public heapView(int address, IValue value) {
        this.address = new SimpleStringProperty(String.valueOf(address));
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

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }


}
