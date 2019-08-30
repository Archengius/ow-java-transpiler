package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.Tokens.CompareOperatorType;

public enum CompareOperator {

    EQUALS(CompareOperatorType.EQUALS),
    NOT_EQUALS(CompareOperatorType.NOT_EQUALS),
    LESS_THAN(CompareOperatorType.LESS_THAN),
    LESS_THAN_OR_EQUAL(CompareOperatorType.LESS_THAN_OR_EQUAL),
    GREATER_THAN(CompareOperatorType.GREATER_THAN),
    GREATER_THAN_OR_EQUAL(CompareOperatorType.GREATER_THAN_OR_EQUAL);

    private final CompareOperatorType typeToken;

    CompareOperator(CompareOperatorType typeToken) {
        this.typeToken = typeToken;
    }

    public CompareOperatorType getTypeToken() {
        return typeToken;
    }
}
