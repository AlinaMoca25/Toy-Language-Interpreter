package model.statements;

import exception.MyException;
import model.adt.IMyMap;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.expressions.VariableExpression;
import model.state.ProgramState;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.StringValue;

public class ForStatement implements IStatement {

    private IValue variable;
    private IExpression expression1, expression2, expression3;
    private IStatement statement;

    public ForStatement(IValue variable, IExpression expression1, IExpression expression2, IExpression expression3, IStatement statement) {
        this.variable = variable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        String varName = ((StringValue) variable).getValue();
        IStatement newStatement = new CompoundStatement(new VarDeclarationStatement(varName, new IntType()),
            new CompoundStatement(new AssignmentStatement(varName, expression1),
                    new WhileStatement(new RelationalExpression(new VariableExpression(varName), expression2, "<"),
                            new CompoundStatement(statement, new AssignmentStatement(varName, expression3)))));
        state.getExecutionStack().push(newStatement);
        return null;
    }

    @Override
    public IMyMap<String, IType> typecheck(IMyMap<String, IType> typeEnv) throws MyException {
        IType exp1Type = this.expression1.typecheck(typeEnv);
        IType exp2Type = this.expression2.typecheck(typeEnv);
        IType exp3Type = this.expression3.typecheck(typeEnv);
        if (exp1Type instanceof IntType && exp2Type instanceof IntType && exp3Type instanceof IntType) {
            return typeEnv;
        }
        else
            throw new MyException("The expressions should return an IntType to be inside a for!");
    }

    public String toString() {
        return "for(" + ((StringValue) variable).getValue() + "=" + expression1.toString() +
                "; " + ((StringValue) variable).getValue() + "<" + expression2.toString() +
                "; " + ((StringValue) variable).getValue() + "=" + expression3.toString() + ")";
    }
}
