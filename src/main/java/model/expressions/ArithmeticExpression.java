package model.expressions;
import exception.ExpressionException;
import exception.MyException;
import model.adt.IHeap;
import model.adt.IMyMap;
import model.types.IType;
import model.types.IntType;
import model.values.*;

public class ArithmeticExpression implements IExpression {
    IExpression e1;
    IExpression e2;
    char operation; //1=plus, 2=minus, 3=multiplication, 4=division

    public ArithmeticExpression(IExpression e1, IExpression e2, char op) {
        this.e1 = e1;
        this.e2 = e2;
        this.operation = op;
    }

    public String toString() {
        return switch (operation) {
            case '+' -> e1.toString() + "+" + e2.toString();
            case '-' -> e1.toString() + "-" + e2.toString();
            case '*' -> e1.toString() + "*" + e2.toString();
            case '/' -> e1.toString() + "/" + e2.toString();
            default -> "";
        };
    }

    public IValue evaluate(IMyMap<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue v1 = e1.evaluate(symbolTable, heap);
        IValue v2 = e2.evaluate(symbolTable, heap);
        if(v1.getType().equals(new IntType())) {
            if(v2.getType().equals(new IntType())) {
                int n1 = ((IntValue) v1).getValue();
                int n2 = ((IntValue) v2).getValue();
                switch (operation) {
                    case '+':
                        return new IntValue(n1 + n2);
                    case '-':
                        return new IntValue(n1 - n2);
                    case '*':
                        return new IntValue(n1 * n2);
                    case '/':
                        if(n2 == 0)
                            throw new ExpressionException("Cannot divide by zero!");
                        else
                            return new IntValue(n1 / n2);
                    default:
                       return new IntType().defaultValue();
                }
            }
            else
                throw new ExpressionException("The second operand should be an integer!");
        }
        else
            throw new ExpressionException("The first operand should be an integer!");
    }

    public IType typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType t1 = e1.typecheck(typeEnv);
        IType t2 = e2.typecheck(typeEnv);
        if(operation != '+' && operation != '-' && operation != '*' && operation != '/') {
            throw new MyException("Invalid operation: " + operation);
        }
        else if(t1.equals(new IntType())) {
            if(t2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw new MyException("The second operand should be an integer!");
        }
        else
            throw new MyException("The first operand should be an integer!");
    }

//    public IExpression negate() {
//        //IExpression expr1 = e1.negate();
//        //IExpression expr2 = e2.negate();
//
//        switch (operation) {
//            case '+':
//                return new ArithmeticExpression(e1, e2, '-');
//            case '-':
//                return new ArithmeticExpression(e1, e2, '+');
//            case '*':
//                return new ArithmeticExpression(e1, e2, '*');
//            case '/':
//                return new ArithmeticExpression(e1, e2, '/');
//        }
//        return new ArithmeticExpression(e1, e2, '+');
//    }
}
