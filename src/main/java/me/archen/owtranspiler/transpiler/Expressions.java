package me.archen.owtranspiler.transpiler;

import me.archen.owtranspiler.workshop.expression.ConstantExpression;
import me.archen.owtranspiler.workshop.expression.FieldValueExpression;
import me.archen.owtranspiler.workshop.expression.IExpression;
import me.archen.owtranspiler.workshop.expression.MethodCallExpression;

public class Expressions {

    private static final String VALUE_IN_ARRAY_METHOD_NAME = "VALUE IN ARRAY";
    private static final String APPEND_TO_ARRAY_METHOD_NAME = "APPEND TO ARRAY";
    private static final String EMPTY_ARRAY_CONSTANT_NAME = "EMPTY ARRAY";
    private static final String NEGATE_METHOD_NAME = "NEGATE";
    private static final String COMPARE_METHOD_NAME = "COMPARE";
    private static final String AND_METHOD_NAME = "AND";
    private static final String OR_METHOD_NAME = "OR";
    private static final String ADD_METHOD_NAME = "ADD";
    private static final String SUBTRACT_METHOD_NAME = "SUBTRACT";
    private static final String DIVIDE_METHOD_NAME = "DIVIDE";
    private static final String MULTIPLY_METHOD_NAME = "MULTIPLY";
    private static final String REMAINDER_METHOD_NAME = "MODULO";

    public static IExpression stringConstant(String value) {
        return new ConstantExpression(value);
    }

    public static IExpression numberConstant(double value) {
        return new ConstantExpression(value);
    }

    public static IExpression booleanConstant(boolean value) {
        return new ConstantExpression(value);
    }

    public static IExpression conditionalExpression(IExpression condition, IExpression trueExpression, IExpression falseExpression) {
        return getValueInArray(appendToArray(appendToArray(emptyArray(), falseExpression), trueExpression), condition);
    }

    public static IExpression emptyArray() {
        return new FieldValueExpression(EMPTY_ARRAY_CONSTANT_NAME);
    }

    public static IExpression appendToArray(IExpression array, IExpression element) {
        return new MethodCallExpression(APPEND_TO_ARRAY_METHOD_NAME, array, element);
    }

    public static IExpression getValueInArray(IExpression array, IExpression index) {
        return new MethodCallExpression(VALUE_IN_ARRAY_METHOD_NAME, array, index);
    }

    public static IExpression negate(IExpression expression) {
        return new MethodCallExpression(NEGATE_METHOD_NAME, expression);
    }

    public static IExpression and(IExpression first, IExpression second) {
        return new MethodCallExpression(AND_METHOD_NAME, first, second);
    }

    public static IExpression or(IExpression first, IExpression second) {
        return new MethodCallExpression(OR_METHOD_NAME, first, second);
    }

    public static IExpression compare(IExpression leftSide, CompareOperator operator, IExpression rightSide) {
        return new MethodCallExpression(COMPARE_METHOD_NAME, leftSide, new FieldValueExpression(operator.name), rightSide);
    }

    public static IExpression add(IExpression leftSide, IExpression rightSide) {
        return new MethodCallExpression(ADD_METHOD_NAME, leftSide, rightSide);
    }

    public static IExpression subtract(IExpression leftSide, IExpression rightSide) {
        return new MethodCallExpression(SUBTRACT_METHOD_NAME, leftSide, rightSide);
    }

    public static IExpression multiply(IExpression leftSide, IExpression rightSide) {
        return new MethodCallExpression(MULTIPLY_METHOD_NAME, leftSide, rightSide);
    }

    public static IExpression divide(IExpression leftSide, IExpression rightSide) {
        return new MethodCallExpression(DIVIDE_METHOD_NAME, leftSide, rightSide);
    }

    public static IExpression remainder(IExpression leftSide, IExpression rightSide) {
        return new MethodCallExpression(REMAINDER_METHOD_NAME, leftSide, rightSide);
    }

    public enum CompareOperator {
        EQUALS("=="),
        NOT_EQUALS("!="),
        LESS("<"),
        LESS_OR_EQUALS("<="),
        GREATER(">"),
        GREATER_OR_EQUALS(">=");

        private final String name;

        CompareOperator(String name) {
            this.name = name;
        }
    }
}
