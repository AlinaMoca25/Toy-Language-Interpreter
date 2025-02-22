package model.statements;

import exception.MyException;
import exception.StatementException;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.state.ProgramState;
import model.types.*;
import model.values.*;

//import javax.swing.plaf.nimbus.State;

public class IfStatement implements IStatement {
    IExpression condition;
    IStatement ifStatement;
    IStatement elseStatement;

    public IfStatement(IExpression exp, IStatement ifS, IStatement elseS) {
        this.condition = exp;
        this.ifStatement = ifS;
        this.elseStatement = elseS;
    }

    @Override
    public String toString() {
        return "If(" + condition.toString() + ") then{ " + ifStatement.toString() + " } else{ " + elseStatement.toString() + " }";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IValue result = condition.evaluate(state.getSymbolTable(), state.getHeap());
        if(result.getType() instanceof BoolType) {
            if(((BoolValue) result).getValue()) {
                state.getExecutionStack().push(ifStatement);
            }
            else {
                state.getExecutionStack().push(elseStatement);
            }
        }
        else
            throw new StatementException("The condition does not evaluate to a boolean value!");
        return null;
    }

    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType conditionType = condition.typecheck(typeEnv);
        if(conditionType instanceof BoolType) {
            ifStatement.typecheck(typeEnv.deepCopy());
            elseStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The IF condition does not evaluate to a boolean value!");
    }
    //????????
}
