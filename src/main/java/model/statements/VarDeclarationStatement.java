package model.statements;
import exception.MyException;
import exception.StatementException;
import model.adt.IMyMap;
import model.state.ProgramState;
import model.types.IType;

import java.util.Properties;

public class VarDeclarationStatement implements IStatement {
    private String name;
    private IType type;

    public VarDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(state.getSymbolTable().contains(name)) {
            throw new StatementException("Variable '" + name + "' is already declared!");
        }
        else {
            state.getSymbolTable().insert(name, type.defaultValue());
        }
        return null;
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        typeEnv.insert(name, type);
        return typeEnv;
    }
}
