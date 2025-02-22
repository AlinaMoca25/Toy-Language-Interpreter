package model.expressions;

import exception.MyException;
import model.adt.IHeap;
import model.adt.IMyMap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class BoolRelationalExpr implements IExpression {

    IExpression e1;
    IExpression e2;
    String operation;

    public BoolRelationalExpr(IExpression e1, IExpression e2, String operation) {
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
        BoolValue v1, v2;
        v1 = (BoolValue) res1;
        v2 = (BoolValue) res2;
        switch (operation) {
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
        if(operation.equals("==") || operation.equals("!="))
        {
            if(t1.equals(new BoolType())) {
                if(t2.equals(new BoolType())) {
                    return new BoolType();
                }
                else
                    throw new MyException("The second operand should be a boolean!");
            }
            else
                throw new MyException("The first operand should be a boolean!");
        }
        else
            throw new MyException("The operation" + operation + " is not supported!");
    }
}
