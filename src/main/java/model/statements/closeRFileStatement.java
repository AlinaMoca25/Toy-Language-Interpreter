package model.statements;

import exception.*;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.types.StringType;
import model.values.*;
import java.io.BufferedReader;
import java.io.IOException;

public class closeRFileStatement implements IStatement {
    IExpression expression;

    public closeRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        IValue result = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if(result.getType().equals(new StringType())) {
            var fileTable = state.getFileTable();
            if(fileTable.contains((StringValue)result)) {
                BufferedReader reader = fileTable.get((StringValue)result);
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new StatementException(e.getMessage());
                }
                fileTable.remove((StringValue)result);
            }
            else
                throw new StatementException("File name not found!");
        }
        else
            throw new StatementException("Invalid file name given!");
        return null;
    }

    public String toString() {
        return "closeFile(" + expression.toString() + ")";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;
    }
}
