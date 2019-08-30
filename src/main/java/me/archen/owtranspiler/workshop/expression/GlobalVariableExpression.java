package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;

public class GlobalVariableExpression implements IExpression {

    public static final String GLOBAL_VARIABLE_METHOD_NAME = "GlobalVariable";
    public static final char VARIABLE_START_LETTER = 'A';
    private final int variableName;

    public GlobalVariableExpression(int variableName) {
        this.variableName = variableName;
        validateVariableName(variableName);
    }

    public int getVariableName() {
        return variableName;
    }

    @Override
    public void writeToGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(GLOBAL_VARIABLE_METHOD_NAME);
        codeGenerator.appendCallStart();
        codeGenerator.appendIdentifier(getVariableIdentifierFromName(variableName));
        codeGenerator.appendCallEnd();
    }

    public static void validateVariableName(int variableName) {
        if(!(variableName >= 0 && variableName < 26)) {
            throw new IllegalArgumentException("VariableName invalid: " + variableName);
        }
    }

    public static String getVariableIdentifierFromName(int variableName) {
        return Character.toString((char) (VARIABLE_START_LETTER + variableName));
    }
}
