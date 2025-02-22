package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.expressions.BoolRelationalExpr;
import model.expressions.IExpression;
import model.expressions.ValueExpression;
import model.state.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class RepeatUntilStatement implements IStatement {
    IStatement statement;
    IExpression expression;

    public RepeatUntilStatement(IStatement statement, IExpression expression) {
        this.statement = statement;
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        IValue result = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if ((!((BoolValue) result).getValue())) { //repeat while the condition is not true
            state.getExecutionStack().push(new WhileStatement(
                    new BoolRelationalExpr(expression, new ValueExpression(new BoolValue(false)), "=="), statement));
            //state.getExecutionStack().push(statement);
        }
        return null;
    }

    public String toString() {
        return "repeat (" + statement.toString() + ") \nuntil (" + expression.toString() + ")";
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType expressionType = expression.typecheck(typeEnv);
        if(expressionType instanceof BoolType) {
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The repeat..until condition does not evaluate to a boolean value!");
    }

}
