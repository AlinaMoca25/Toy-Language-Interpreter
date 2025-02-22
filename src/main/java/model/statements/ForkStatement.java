package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.adt.MyMap;
import model.adt.MyStack;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.IValue;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyStack<IStatement> newStack = new MyStack<>();
        newStack.push(statement);

        IMyMap<String, IValue> newSymTable = new MyMap<>();
        newSymTable = state.getSymbolTable().deepCopy();
        ProgramState newState = new ProgramState(newStack, newSymTable, state.getOutput(),
                state.getFileTable(), state.getHeap());
        return newState;
        //return state;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
