package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import exception.StatementException;
import model.types.IType;
import model.types.RefType;
import model.types.StringType;
import model.values.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;


public class OpenRFileStatement implements IStatement {
    private final IExpression expression;

    public OpenRFileStatement(IExpression exp) {
        this.expression = exp;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        IValue fileName = this.expression.evaluate(state.getSymbolTable(), state.getHeap()); //can only be value expression?
        if(fileName.getType().equals(new StringType()))
        {
            var fileTable = state.getFileTable();
            if(!fileTable.contains((StringValue)fileName)) {
                try {
                    Reader reader = new FileReader(fileName.toString());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    fileTable.insert((StringValue) fileName, bufferedReader);
                } catch (FileNotFoundException e) {
                    throw new MyException(e.getMessage());
                }
            }
            else {
                throw new StatementException("File already exists!");
            }
        }
        else {
            throw new StatementException("The expression provided as a file name is not a string!");
        }
        return null;
    }

    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType exprType = expression.typecheck(typeEnv);
        if(exprType instanceof StringType) {
            return typeEnv;
        }
        else
            throw new MyException("Open File: The expression provided as a file name is not a string!");
    }
}
