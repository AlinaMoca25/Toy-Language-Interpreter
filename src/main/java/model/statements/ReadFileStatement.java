package model.statements;

import exception.*;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.*;
import model.values.*;

//import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String variableName;
    //reads a line and stores it into a variable

    public ReadFileStatement(IExpression exp, String varName) {
        this.expression = exp;
        this.variableName = varName;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymbolTable();
        if(symbolTable.contains(variableName)) {
            if(symbolTable.get(variableName).getType().equals(new IntType())) {
                IValue res = expression.evaluate(state.getSymbolTable(), state.getHeap());
                if (res.getType().equals(new StringType())) {
                    BufferedReader reader = state.getFileTable().get((StringValue)res);
                    try {
                        String readResult = reader.readLine();
                        int result;
                        if(readResult != null) {
                            result = Integer.parseInt(readResult);
                        }
                        else {
                            result = 0;
                        }
                        symbolTable.update(variableName, new IntValue(result));
                    } catch (IOException e) {
                        throw new StatementException(e.getMessage());
                    }
                }
                else
                    throw new StatementException("The variable name given is not a String!");
            }
            else
                throw new StatementException("Type of '" + variableName + "' is not int!");
        }
        else
            throw new StatementException(variableName + " does not exist");
        return null;
    }

    public String toString() {
        return "readFile(" + expression.toString() + ")";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType exprType = expression.typecheck(typeEnv);
        IType idType = typeEnv.get(variableName);
        if (exprType instanceof StringType) {
            if(idType instanceof IntType) {
                return typeEnv;
            }
            else
                throw new MyException("The type of the given variable is not int!");
        }
        else
            throw new MyException("Type of '" + variableName + "' is not a String!");
    }

}
