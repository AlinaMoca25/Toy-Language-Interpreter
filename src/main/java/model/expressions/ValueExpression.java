package model.expressions;
import exception.MyException;
import model.adt.IHeap;
import model.adt.IMyMap;
import model.values.IValue;
import model.types.IType;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue val) {
        this.value = val;
    }

    public String toString() {
        return value.toString();
    }

//    public IValue evaluate(IMyMap<String, IValue> symbolTable) throws MyException {
//        IValue res = this.value.evaluate(symbolTable);
//        if(value.getType().equals(res.getType())) {
//            return value;
//        }
//        else
//            throw new MyException("The types of the expression and the result do not match!");
//    }

    public IValue evaluate(IMyMap<String, IValue> symbolTable, IHeap<Integer, IValue> heap) {
        return value;
    }

    public IType typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        return this.value.getType();
    }
}
