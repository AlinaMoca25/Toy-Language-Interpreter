package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.adt.SemaphoreData;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newSemaphoreStatement implements IStatement {

    private StringValue variable;
    private IExpression expression1, expression2;
    private Lock lock = new ReentrantLock();

    public newSemaphoreStatement(StringValue variable, IExpression expression1, IExpression expression2) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IValue r1 = expression1.evaluate(state.getSymbolTable(), state.getHeap());
        IValue r2 = expression2.evaluate(state.getSymbolTable(), state.getHeap());
        if (!(r1 instanceof IntValue && r2 instanceof IntValue)) {
            throw new MyException("the results of the semaphore expressions are not integers!");
        }
        SemaphoreData semData = new SemaphoreData(r1, r2, new ArrayList<>());
        lock.lock();
        int location = state.getSemaphoreTable().allocate(semData);
        lock.unlock();
        if(state.getSymbolTable().contains(variable.getValue())) {
            if (state.getSymbolTable().get(variable.getValue()).getType() instanceof IntType) {
                state.getSymbolTable().update(variable.getValue(), new IntValue(location));
            }
            else
                throw new MyException("the variable " + variable.getValue() + " is not an int!");
        }
        else
            throw new MyException("the variable " + variable.getValue() + " is not in the symbol table!");
        return null;
    }

    @Override
    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        return null;
    }

    public String toString() {
        return "newSemaphore(" + variable.toString() + ", " + expression1.toString() + ", " + expression2.toString() + ")";
    }
}
