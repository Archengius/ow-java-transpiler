package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;
import me.archen.owtranspiler.workshop.constants.Hero;
import me.archen.owtranspiler.workshop.constants.Team;

public class ConstantExpression implements IExpression {

    public static final String HERO_METHOD_NAME = "Hero";
    public static final String TEAM_METHOD_NAME = "Team";
    public static final String BOOLEAN_FALSE_CONSTANT = "FALSE";
    public static final String BOOLEAN_TRUE_CONSTANT = "TRUE";

    private final Object constantValue;

    public ConstantExpression(double constantValue) {
        this.constantValue = constantValue;
    }

    public ConstantExpression(String constantValue) {
        this.constantValue = constantValue;
    }

    public ConstantExpression(boolean value) {
        this.constantValue = value;
    }

    public ConstantExpression(Hero value) {
        this.constantValue = value;
    }

    public ConstantExpression(Team value) {
        this.constantValue = value;
    }

    public Object getConstantValue() {
        return constantValue;
    }

    @Override
    public void writeToGenerator(CodeGenerator codeGenerator) {
        if (constantValue instanceof String) {
            codeGenerator.appendStringLdc((String) constantValue);
        } else if (constantValue instanceof Number) {
            codeGenerator.appendNumber(((Number) constantValue).doubleValue());
        } else if (constantValue instanceof Boolean) {
            boolean booleanValue = (boolean) constantValue;
            codeGenerator.appendIdentifier(booleanValue ? BOOLEAN_TRUE_CONSTANT : BOOLEAN_FALSE_CONSTANT);
        } else if (constantValue instanceof Hero) {
            codeGenerator.appendIdentifier(HERO_METHOD_NAME);
            codeGenerator.appendCallStart();
            codeGenerator.appendIdentifier(((Hero) constantValue).getName());
            codeGenerator.appendCallEnd();
        } else if (constantValue instanceof Team) {
            codeGenerator.appendIdentifier(TEAM_METHOD_NAME);
            codeGenerator.appendCallStart();
            codeGenerator.appendIdentifier(((Team) constantValue).getName());
            codeGenerator.appendCallEnd();
        }
    }
}
