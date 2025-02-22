package repository;

import model.state.ProgramState;
import exception.MyException;
import java.io.IOException;
import java.util.List;

public interface IRepository {
    void add(ProgramState state);
    //ProgramState getCurrentProgram();
    public void setStatePosition(int position);
    //void logProgramStateExec() throws MyException, IOException;
    void logProgramStateExec(ProgramState state) throws MyException, IOException;

    List<ProgramState> getProgramStates();
    void setProgramStatesList(List<ProgramState> newStatesList);
    //List<ProgramState> getProgramStates();
    //void setProgramList(List<ProgramState> list);
}
