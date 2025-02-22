package model.statements;

import exception.MyException;
import exception.StatementException;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.IValue;
import model.values.BoolValue;

public class WhileStatement implements IStatement {

    IExpression condition;
    IStatement body;

    public WhileStatement(IExpression condition, IStatement body) {
        this.condition = condition;
        this.body = body;
    }

    public ProgramState execute(ProgramState state) throws MyException {

        IValue result = condition.evaluate(state.getSymbolTable(), state.getHeap());

        if(result instanceof BoolValue) {
            if(((BoolValue) result).getValue()) {
                state.getExecutionStack().push(new WhileStatement(condition, body));
                state.getExecutionStack().push(body);
            }
            //if false nothing?
        }
        else
            throw new StatementException("The condition " + condition.toString() + " is not a boolean!");

        return null;
    }

    public String toString() {
        return "while(" + condition.toString() + ")\n" + "{ " + body.toString() + " }";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType conditionType = condition.typecheck(typeEnv);
        if(conditionType instanceof BoolType) {
            body.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The WHILE condition does not evaluate to a boolean value!");
    }
}
