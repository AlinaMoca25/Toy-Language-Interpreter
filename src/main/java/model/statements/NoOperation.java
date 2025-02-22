package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;

public class NoOperation implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }
    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    public String toString() {
        return "NoOperation";
    }
}
