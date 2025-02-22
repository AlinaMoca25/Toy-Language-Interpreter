package model.expressions;
import exception.ExpressionException;
import exception.MyException;
import model.adt.IHeap;
import model.adt.IMyMap;
import model.types.IType;
import model.types.RefType;
import model.values.*;
import exception.MyException;

public class HeapReading implements IExpression {

    IExpression expression;

    public HeapReading(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(IMyMap<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue res = expression.evaluate(symbolTable, heap);
        if (res instanceof RefValue) {
            int address = ((RefValue) res).getAddress();
            if(heap.contains(address)) {
                return heap.get(address);
            }
            else
                throw new ExpressionException(address + " is not a valid address!");
        }
        else
            throw new ExpressionException("The expression does not evaluate to a RefValue!");
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }

    public IType typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType t = expression.typecheck(typeEnv);
        if(t instanceof RefType) {
            RefType refT = (RefType) t;
            return refT.getInner();
        }
        else
            throw new MyException("The expression does not evaluate to a RefType!");
    }

}
