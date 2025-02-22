package repository;

import java.util.ArrayList;
import java.util.List;

import exception.MyException;
import model.state.ProgramState;

import java.io.*;
import java.util.Scanner;

public class Repository implements IRepository {
    private List<ProgramState> states;
    private int currentStatePosition;
    private String logFilePath = "test.out";
    //private String logFilePath;
    //This field is initialized by a string read from the keyboard using Scanner class.??

    public Repository() {
        states = new ArrayList<ProgramState>();
        currentStatePosition = 0;
    }

    public Repository(String filePath) {
        states = new ArrayList<ProgramState>();
        currentStatePosition = 0;
        this.logFilePath = filePath;
    }

    public void add(ProgramState state) {
        states.add(state);
    }

    public void setStatePosition(int position) {
        currentStatePosition = position;
    }

//    @Override
//    public ProgramState getCurrentProgram() {
//        return states.get(currentStatePosition);
//    }

    public void logProgramStateExec(ProgramState state) throws MyException, IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true))); //true = append mode
        logFile.print(state.toString());
        logFile.close();
    }

    @Override
    public List<ProgramState> getProgramStates() {
        return states;
    }

    @Override
    public void setProgramStatesList(List<ProgramState> programStates) {
        this.states = programStates;
    }

}
