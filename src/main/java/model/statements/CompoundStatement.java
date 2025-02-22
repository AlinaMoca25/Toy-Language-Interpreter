package model.statements;

import exception.MyException;
import model.state.*;
import model.adt.*;
import model.types.IType;

public class CompoundStatement implements IStatement {

    private IStatement statement1;
    private IStatement statement2;

    public CompoundStatement(IStatement statement1, IStatement statement2) {
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        IMyStack<IStatement> exeStack = state.getExecutionStack();

        exeStack.push(statement2);
        exeStack.push(statement1);
        return null;
    }

    @Override
    public String toString() {
        if(statement2 instanceof CompoundStatement) {
            return statement1.toString() + "; " + statement2.toString();
        }
        return statement1.toString() + "; " + statement2.toString() + ";";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        return statement2.typecheck(statement1.typecheck(typeEnv));
    }
}
