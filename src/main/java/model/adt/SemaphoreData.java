package model.adt;

import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.List;

public class SemaphoreData {
    private IValue nr1, nr2;
    private List<Integer> programStates = new ArrayList<Integer>();

    public SemaphoreData(IValue nr2, IValue nr1, List<Integer> programStates) {
        this.nr2 = nr2;
        this.nr1 = nr1;
        this.programStates = programStates;
    }

    public int getNr1() {
        return ((IntValue) nr1).getValue();
    }

    public int getNr2() {
        return ((IntValue) nr2).getValue();
    }

    public List<Integer> getProgramStates() {
        return programStates;
    }

    public void setProgramStates(List<Integer> programStates) {
        this.programStates = programStates;
    }

    @Override
    public String toString() {
        return nr1.toString() + ", " + programStates.toString() + ", " + nr2.toString();
    }
}
