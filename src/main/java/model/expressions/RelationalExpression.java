package model.expressions;

import exception.MyException;
import model.adt.IHeap;
import model.adt.IMyMap;
import model.types.*;
import model.values.*;

public class RelationalExpression implements IExpression {
    IExpression e1;
    IExpression e2;
    String operation;

    public RelationalExpression(IExpression e1, IExpression e2, String operation) {
        this.e1 = e1;
        this.e2 = e2;
        this.operation = operation;
    }

    public String toString() {
        return e1.toString() + " " + operation + " " + e2.toString();
    }

    public IValue evaluate(IMyMap<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue res1 = e1.evaluate(symbolTable, heap);
        IValue res2 = e2.evaluate(symbolTable, heap);
        IntValue v1, v2;
        if(res1.getType() instanceof IntType) {
            if(res2.getType() instanceof IntType) {
                v1 = (IntValue) res1;
                v2 = (IntValue) res2;
            }
            else
                throw new MyException("The second expression does not evaluate to an integer!");
        }
        else
            throw new MyException("The first expression does not evaluate to an integer!");
        switch (operation) {
            case ">":
                return new BoolValue(v1.getValue() > v2.getValue());
            case "<":
                return new BoolValue(v1.getValue() < v2.getValue());
            case ">=":
                return new BoolValue(v1.getValue() >= v2.getValue());
            case "<=":
                return new BoolValue(v1.getValue() <= v2.getValue());
            case "==":
                return new BoolValue(v1.getValue() == v2.getValue());
            case "!=":
                return new BoolValue(v1.getValue() != v2.getValue());
        }
        return new BoolType().defaultValue();
    }

    public IType typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType t1 = e1.typecheck(typeEnv);
        IType t2 = e2.typecheck(typeEnv);
        if(operation.equals("==") || operation.equals("!=") || operation.equals(">") ||
                operation.equals("<") || operation.equals("<=") || operation.equals(">="))
        {
            if(t1.equals(new IntType())) {
                if(t2.equals(new IntType())) {
                    return new BoolType();
                }
                else
                    throw new MyException("The second operand should be an integer!");
            }
            else
                throw new MyException("The first operand should be an integer!");
        }
        else
            throw new MyException("The operation" + operation + " is not supported!");
    }
}
