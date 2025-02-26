package view;

import javafx.beans.property.SimpleStringProperty;

public class semaphoreView {
    private SimpleStringProperty location;
    private SimpleStringProperty nr1;
    private SimpleStringProperty nr2;
    private SimpleStringProperty programStates;


    public semaphoreView(int location, int nr1, int nr2, String programStates) {
        this.location = new SimpleStringProperty(String.valueOf(location));
        this.nr1 = new SimpleStringProperty(String.valueOf(nr1));
        this.nr2 = new SimpleStringProperty(String.valueOf(nr2));
        this.programStates = new SimpleStringProperty(programStates);
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

    public String getNr1() {
        return nr1.get();
    }

    public SimpleStringProperty nr1Property() {
        return nr1;
    }

    public void setNr1(String nr1) {
        this.nr1.set(nr1);
    }

    public String getNr2() {
        return nr2.get();
    }

    public SimpleStringProperty nr2Property() {
        return nr2;
    }

    public void setNr2(String nr2) {
        this.nr2.set(nr2);
    }

    public String getProgramStates() {
        //return programStates.get();
        return programStates.get();
    }

    public SimpleStringProperty programStatesProperty() {
        return programStates;
    }

    public void setProgramStates(String programStates) {
        this.programStates.set(programStates);
    }
}
