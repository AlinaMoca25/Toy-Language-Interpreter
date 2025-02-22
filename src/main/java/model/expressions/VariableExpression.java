package model.expressions;
import exception.ExpressionException;
import model.adt.IHeap;
import model.types.IType;
import model.values.IValue;
import model.adt.IMyMap;
import exception.MyException;

public class VariableExpression implements IExpression {
    String name;
    //IExpression expression;

    public VariableExpression(String name) {
        this.name = name;
        //this.expression = expression;
    }

    public String toString() {
        return name;
    }

    public IValue evaluate(IMyMap<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        if(symbolTable.contains(name)) {
            return symbolTable.get(name);
        }
        else
            throw new ExpressionException("Variable '" + name + "' not found!");
    }

    public IType typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        return typeEnv.get(this.name);
    }
}
