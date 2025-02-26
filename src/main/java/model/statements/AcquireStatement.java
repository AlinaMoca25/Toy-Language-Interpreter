package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.adt.SemaphoreData;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements IStatement {

    private StringValue variable;
    private Lock lock = new ReentrantLock();

    public AcquireStatement(StringValue variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IMyMap<String, IValue> symbolTable = state.getSymbolTable();
        if(symbolTable.contains(variable.getValue())) {
            if(symbolTable.get(variable.getValue()).getType().equals(new IntType())) {
                IValue value = symbolTable.get(variable.getValue());
                int val = ((IntValue) value).getValue();
                lock.lock();
                if (state.getSemaphoreTable().contains(val)) {
                    SemaphoreData res = state.getSemaphoreTable().get(val);
                    int listLength = res.getProgramStates().size();
                    //if(abs(res.getNr2() - res.getNr1()) > listLength) {
                    if((res.getNr2() - res.getNr1()) > listLength) {
                        if (!(res.getProgramStates().contains(state.getId()))) {
                            res.getProgramStates().add(state.getId());
                        }
                    }
                    else {
                        state.getExecutionStack().push(new AcquireStatement(variable));
                    }
                }
                else
                    throw new MyException("That value is not in the semaphore table anymore!");
                lock.unlock();
            }
            else
                throw new MyException(variable.getValue() + " is not an int");
        }
        else
            throw new MyException("Variable '" + variable.getValue() + "' not found");
        return null;
    }

    @Override
    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        return null;
    }

    public String toString() {
        return "acquire(" + variable.getValue() + ")";
    }
}
