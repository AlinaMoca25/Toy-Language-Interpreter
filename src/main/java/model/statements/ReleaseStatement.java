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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStatement implements IStatement {

    private StringValue variable;
    private Lock lock = new ReentrantLock();

    public ReleaseStatement(StringValue variable) {
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
                    if(res.getProgramStates().contains(state.getId())) {
                        List<Integer> newRes = new ArrayList<>();
                        for (int p : res.getProgramStates()) {
                            if (p != state.getId()) {
                                newRes.add(p);
                            }
                        }
                        state.getSemaphoreTable().get(val).setProgramStates(newRes);
                        //res.getProgramStates().remove(state.getId());
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

    @Override
    public String toString() {
        return "release(" + variable.toString() + ")";
    }
}
