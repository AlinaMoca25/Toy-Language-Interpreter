package model.expressions;
import exception.ExpressionException;
import model.adt.IHeap;
import model.values.IValue;
import model.adt.IMyMap;
import exception.MyException;
import model.types.IType;

public interface IExpression {
    public IValue evaluate(IMyMap<String, IValue> map, IHeap<Integer, IValue> heap) throws MyException;
    public String toString();
    IType typecheck(IMyMap<String, IType> typeEnv) throws MyException;
    //IExpression negate();
}
