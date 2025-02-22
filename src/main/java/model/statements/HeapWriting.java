package model.statements;

import exception.*;
import model.adt.IMyMap;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.types.RefType;
import model.values.*;
import model.expressions.*;

public class HeapWriting implements IStatement {

    StringValue variableName;
    IExpression expression;

    public HeapWriting(StringValue variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        if(symbolTable.contains(variableName.getValue())) {
            if(symbolTable.get(variableName.getValue()).getType() instanceof RefType) {  // or the value is a refValue
                if(state.getHeap().contains(((RefValue)symbolTable.get(variableName.getValue())).getAddress())) {
                    IValue result = expression.evaluate(symbolTable, state.getHeap());
                    if(result.getType().equals(((RefValue)symbolTable.get(variableName.getValue())).getAddressType())) {
                        int address = ((RefValue)symbolTable.get(variableName.getValue())).getAddress();
                        state.getHeap().update(address, result);
                    }
                    else
                        throw new StatementException("The expression does not evaluate the type of the reference variable.");
                }
                else
                    throw new StatementException("The variable '" + variableName.getValue() + "' is not in the heap!");
            }
            else
                throw new StatementException("Variable '" + variableName.getValue() + "' was not declared as a reference!");
        }
        else
            throw new StatementException("Variable '" + variableName.getValue() + "' was not defined!");
        return null;
    }

    public String toString() {
        return "wH(" + variableName.toString() + "," + expression.toString() + ")";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.get(variableName.getValue());
        IType exprType = expression.typecheck(typeEnv);
        if(varType instanceof RefType) {
            if(exprType.equals(((RefType) varType).getInner())) {
                return typeEnv;
            }
            else
                throw new MyException("Heap writing: the inner type of the variable does not match with the type of the expression!");
        }
        else
            throw new MyException("Heap writing: variable is not a reference type!");
    }

}
