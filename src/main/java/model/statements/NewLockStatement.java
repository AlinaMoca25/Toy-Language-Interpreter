package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStatement implements IStatement {

    private String variable;
    private Lock lock;

    public NewLockStatement(String value) {
        this.variable = value;
        this.lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        int pos;
        if(state.getSymbolTable().contains(variable)) {
            if(state.getSymbolTable().get(variable).getType().equals(new IntType())) {
                lock.lock();
                pos = state.getLockTable().allocate(-1);
                state.getSymbolTable().update(variable, new IntValue(pos));
                lock.unlock();
            }
            else
                throw new MyException("Variable '" + variable + "' is not int");
        }
        else
            throw new MyException("Variable '" + variable + "' does not exist");
        return null;
    }

    @Override
    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.get(variable);
        if(varType.equals(new IntType())) {
            return typeEnv;
        }
        else
            throw new MyException("NewLock: variable '" + variable + "' is not of type Int");
//        return null;
    }

    public String toString() {
        return "newLock(" + variable + ")";
    }
}
