package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockStatement implements IStatement {

    private String variable;
    private Lock lock;

    public LockStatement(String variable) {
        this.variable = variable;
        this.lock = new ReentrantLock();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if(state.getSymbolTable().contains(variable)) {
            IValue res = state.getSymbolTable().get(variable);
            if(res.getType().equals(new IntType())) {
                int position = ((IntValue) res).getValue();
                lock.lock();
                if(state.getSymbolTable().contains(variable)) {
                    int holder = state.getLockTable().get(position);
                    if(holder==-1) {
                        state.getLockTable().update(position, state.getId());
                    }
                    else {
                        state.getExecutionStack().push(new LockStatement(variable));
                    }
                }
                else
                    throw new MyException("Variable '" + variable + "' is not a valid index in the lock table!");
                lock.unlock();
            }
            else
                throw new MyException("The variable" + variable + "does not have an int type!");
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
            throw new MyException("Lock: variable '" + variable + "' is not of type Int");
//        return null;
    }

    public String toString() {
        return "lock(" + variable + ")";
    }
}
