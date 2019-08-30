package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;

public class CompareExpression implements IExpression {

    public static final String COMPARE_METHOD_NAME = "Compare";

    private final CompareOperator operator;
    private final IExpression leftExpression;
    private final IExpression rightExpression;

    public CompareExpression(CompareOperator operator, IExpression leftExpression, IExpression rightExpression) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public CompareOperator getOperator() {
        return operator;
    }

    public IExpression getLeftExpression() {
        return leftExpression;
    }

    public IExpression getRightExpression() {
        return rightExpression;
    }

    @Override
    public void writeToGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(COMPARE_METHOD_NAME);
        codeGenerator.appendCallStart();
        this.leftExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendComma();
        codeGenerator.appendToken(operator.getTypeToken());
        codeGenerator.appendComma();
        this.rightExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendCallEnd();

    }
}
