package model.statements;

import exception.MyException;
import model.state.*;
import model.types.IType;
import model.adt.IMyMap;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException;
}
