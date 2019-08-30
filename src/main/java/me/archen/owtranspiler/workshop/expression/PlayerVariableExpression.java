package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;

public class PlayerVariableExpression implements IExpression {

    public static final String PLAYER_VARIABLE_METHOD_NAME = "PlayerVariable";

    private final IExpression playerExpression;
    private final int variableName;

    public PlayerVariableExpression(IExpression playerExpression, int variableName) {
        this.playerExpression = playerExpression;
        this.variableName = variableName;
        GlobalVariableExpression.validateVariableName(variableName);
    }

    public IExpression getPlayerExpression() {
        return playerExpression;
    }

    public int getVariableName() {
        return variableName;
    }

    @Override
    public void writeToGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(PLAYER_VARIABLE_METHOD_NAME);
        codeGenerator.appendCallStart();
        this.playerExpression.writeToGenerator(codeGenerator);
        codeGenerator.appendComma();
        codeGenerator.appendIdentifier(GlobalVariableExpression.getVariableIdentifierFromName(variableName));
        codeGenerator.appendCallEnd();
    }
}
