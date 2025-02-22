package model.statements;

import exception.MyException;
import exception.StatementException;
import model.adt.IMyMap;
import model.adt.MyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.types.RefType;
import model.values.*;

public class HeapAllocation implements IStatement {
    private StringValue variableName;
    private IExpression expression;

    public HeapAllocation(StringValue varName, IExpression expr) {
        this.variableName = varName;
        this.expression = expr;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        if (symbolTable.contains(variableName.toString())) {
            if(symbolTable.get(variableName.getValue()) instanceof RefValue) {
                IValue res = expression.evaluate(symbolTable, state.getHeap());
                IValue varValue = symbolTable.get(variableName.getValue());
                //if(res.getType().equals(varValue.getType())) {
                if(res.getType().equals(((RefValue)varValue).getAddressType())) {
                    int newAddress = state.getHeap().allocate(res);
//                    if (!((RefValue)symbolTable.get(variableName.getValue()) instanceof RefValue)) {
//                        ((RefValue)symbolTable.get(variableName.getValue())).setAddress(newAddress);
//                    }
                    ((RefValue)symbolTable.get(variableName.getValue())).setAddress(newAddress);
                }
                else
                    throw new StatementException("The types of the variable and of the expression are not equal!");
            }
            else
                throw new StatementException("Variable '" + variableName + "' is not a reference type!");
        }
        else
            throw new StatementException("Variable '" + variableName + "' was not declared!");
        return null;
    }

    public String toString() {
        return "new(" + variableName + ", " + expression.toString() + ")";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.get(variableName.getValue());
        IType exprType = expression.typecheck(typeEnv);
        if(varType instanceof RefType) {
            if(exprType.equals(((RefType) varType).getInner())) {
                return typeEnv;
            }
            else
                throw new MyException("Heap allocation: the inner type of the variable does not match with the type of the expression!");
        }
        else
            throw new MyException("Heap allocation: variable is not a reference type!");
    }
}
