package model.expressions;
import exception.ExpressionException;
import model.adt.IHeap;
import model.adt.IMyMap;
import model.types.IType;
import model.types.IntType;
import model.values.*;
import model.types.BoolType;
import exception.MyException;
import model.adt.IMyMap;

public class LogicalExpression implements IExpression {
    IExpression e1;
    IExpression e2;
    String operation;

    public LogicalExpression(IExpression e1, IExpression e2, String operation) {
        this.e1 = e1;
        this.e2 = e2;
        this.operation = operation;
    }

    public String toString() {
        return e1.toString() + " " + operation + " " + e2.toString() + ";";
    }

    public IValue evaluate(IMyMap<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue r1 = e1.evaluate(symbolTable, heap);
        IValue r2 = e2.evaluate(symbolTable, heap);
        if(r1.getType() instanceof BoolType) {
            if(r2.getType() instanceof BoolType) {
                BoolValue v1 = (BoolValue) r1;
                BoolValue v2 = (BoolValue) r2;
                if(operation.equals("and")) {
                    return new BoolValue(v1.getValue() && v2.getValue());
                }
                else if(operation.equals("or")) {
                    return new BoolValue(v1.getValue() || v2.getValue());
                }
            }
            else
                throw new ExpressionException("The second expression does not evaluate to a bool type!");
        }
        else
            throw new ExpressionException("The first expression does not evaluate to a bool type!");
        return new BoolType().defaultValue();
    }

    public IType typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType t1 = e1.typecheck(typeEnv);
        IType t2 = e2.typecheck(typeEnv);
        if(operation.equals("and") || operation.equals("or")) {
            if(t1.equals(new BoolType())) {
                if(t2.equals(new BoolType())) {
                    return new BoolType();
                }
                else
                    throw new MyException("The second operand should be a bool type!");
            }
            else
                throw new MyException("The first operand should be a bool type!");
        }
        else
            throw new MyException("Operation not supported!");
    }
}
