package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;

public class SetGlobalVariableExpression implements IExpression, IAction {

    public static final String SET_GLOBAL_VARIABLE_METHOD_NAME = "SetGlobalVariable";

    private final int variableName;
    private final IExpression valueExpression;

    public SetGlobalVariableExpression(int variableName, IExpression valueExpression) {
        this.variableName = variableName;
        this.valueExpression = valueExpression;
        GlobalVariableExpression.validateVariableName(variableName);
    }

    public int getVariableName() {
        return variableName;
    }

    public IExpression getValueExpression() {
        return valueExpression;
    }

    @Override
    public void writeToGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(SET_GLOBAL_VARIABLE_METHOD_NAME);
        codeGenerator.appendCallStart();
        codeGenerator.appendIdentifier(GlobalVariableExpression.getVariableIdentifierFromName(variableName));
        codeGenerator.appendComma();
        this.valueExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendCallEnd();
    }
}
