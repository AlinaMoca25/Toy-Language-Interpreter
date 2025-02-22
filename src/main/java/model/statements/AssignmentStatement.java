package model.statements;
import exception.MyException;
import exception.StatementException;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.adt.*;
import model.values.IValue;
import model.types.IType;

//import javax.swing.plaf.nimbus.State;

public class AssignmentStatement implements IStatement {
    String id;
    IExpression expression;

    public AssignmentStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    public String toString() {
        return id + "=" + expression.toString();
    }

    public ProgramState execute(ProgramState state) throws MyException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyMap<String, IValue> symbolTable = state.getSymbolTable();
        if(symbolTable.contains(id)) {
            IValue value = this.expression.evaluate(symbolTable, state.getHeap());
            symbolTable.update(id, value);
        }
        else
            throw new StatementException("Variable "+id+" not found!");
        return null;
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType varType = typeEnv.get(id);
        IType exprType = expression.typecheck(typeEnv);
        if(varType.equals(exprType)) {
            return typeEnv;
        }
        else
            throw new MyException("Assignment: right side and left side have different types!");
    }

}
