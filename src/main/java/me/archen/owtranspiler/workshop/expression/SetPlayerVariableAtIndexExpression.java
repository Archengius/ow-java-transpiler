package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;

public class SetPlayerVariableAtIndexExpression implements IExpression, IAction {

    public static final String SET_PLAYER_VARIABLE_METHOD_NAME = "SetPlayerVariable";

    private final IExpression playerExpression;
    private final int variableName;
    private final IExpression indexExpression;
    private final IExpression valueExpression;

    public SetPlayerVariableAtIndexExpression(IExpression playerExpression, int variableName, IExpression indexExpression, IExpression valueExpression) {
        this.playerExpression = playerExpression;
        this.variableName = variableName;
        this.indexExpression = indexExpression;
        this.valueExpression = valueExpression;
        GlobalVariableExpression.validateVariableName(variableName);
    }

    public IExpression getPlayerExpression() {
        return playerExpression;
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
        codeGenerator.appendIdentifier(SET_PLAYER_VARIABLE_METHOD_NAME);
        codeGenerator.appendCallStart();
        this.playerExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendComma();
        codeGenerator.appendIdentifier(GlobalVariableExpression.getVariableIdentifierFromName(variableName));
        codeGenerator.appendComma();
        this.indexExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendComma();
        this.valueExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendCallEnd();
    }
}
