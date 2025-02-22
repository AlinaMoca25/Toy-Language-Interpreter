package model.statements;

import exception.MyException;
import model.adt.IMyList;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.IType;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression exp) {
        this.expression = exp;
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //pop from the executionStack
        //update the symbol table?
        var result = this.expression.evaluate(state.getSymbolTable(), state.getHeap());
        state.getOutput().add(result);
        return null;
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }
}
