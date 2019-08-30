package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;

public class SetGlobalVariableAtIndexExpression implements IExpression, IAction {

    public static final String SET_GLOBAL_VARIABLE_AT_INDEX_METHOD_NAME = "SetGlobalVariableAtIndex";

    private final int variableName;
    private final IExpression indexExpression;
    private final IExpression valueExpression;

    public SetGlobalVariableAtIndexExpression(int variableName, IExpression indexExpression, IExpression valueExpression) {
        this.variableName = variableName;
        this.indexExpression = indexExpression;
        this.valueExpression = valueExpression;
        GlobalVariableExpression.validateVariableName(variableName);
    }

    public int getVariableName() {
        return variableName;
    }

    public IExpression getIndexExpression() {
        return indexExpression;
    }

    public IExpression getValueExpression() {
        return valueExpression;
    }

    @Override
    public void writeToGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(SET_GLOBAL_VARIABLE_AT_INDEX_METHOD_NAME);
        codeGenerator.appendCallStart();
        codeGenerator.appendIdentifier(GlobalVariableExpression.getVariableIdentifierFromName(variableName));
        codeGenerator.appendComma();
        this.indexExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendComma();
        this.valueExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendCallEnd();
    }
}
