package me.archen.owtranspiler.transpiler;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.declarations.ResolvedClassDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.google.common.base.Preconditions;
import me.archen.owtranspiler.transpiler.Expressions.CompareOperator;
import me.archen.owtranspiler.workshop.expression.IExpression;
import me.archen.owtranspiler.workshop.expression.MethodCallExpression;

import java.util.List;

public class ExpressionMapper {

    public static void translateTopLevelExpression(Expression expression, Context context) {
        if (expression instanceof VariableDeclarationExpr) {
            translateVariableDeclarationExpression((VariableDeclarationExpr) expression, context);
        } else if (expression instanceof AssignExpr) {
            translateAssignExpression((AssignExpr) expression, context);
        }
    }

    public static IExpression mapExpression(Expression expression) {
        if(expression instanceof ArrayAccessExpr) {
            return mapArrayAccessExpression((ArrayAccessExpr) expression);
        } else if (expression instanceof ArrayCreationExpr) {
            throw new UnsupportedOperationException("Unsupported yet");
        } else if (expression instanceof ArrayInitializerExpr) {
            throw new UnsupportedOperationException("Unsupported yet");
        } else if (expression instanceof BinaryExpr) {
            return mapBinaryExpression((BinaryExpr) expression);
        } else if (expression instanceof CastExpr) {
            return mapCastExpression((CastExpr) expression);
        } else if (expression instanceof CharLiteralExpr) {
            throw new UnsupportedOperationException("Char literals are not supported");
        } else if (expression instanceof ClassExpr) {
            throw new UnsupportedOperationException("Class literals and reflection are not supported");
        } else if (expression instanceof BooleanLiteralExpr) {
            BooleanLiteralExpr expr = (BooleanLiteralExpr) expression;
            return Expressions.booleanConstant(expr.getValue());
        } else if (expression instanceof IntegerLiteralExpr) {
            IntegerLiteralExpr expr = (IntegerLiteralExpr) expression;
            return Expressions.numberConstant(expr.asInt());
        } else if (expression instanceof LongLiteralExpr) {
            LongLiteralExpr expr = (LongLiteralExpr) expression;
            return Expressions.numberConstant(expr.asLong());
        } else if (expression instanceof DoubleLiteralExpr) {
            DoubleLiteralExpr expr = (DoubleLiteralExpr) expression;
            return Expressions.numberConstant(expr.asDouble());
        } else if (expression instanceof StringLiteralExpr) {
            StringLiteralExpr expr = (StringLiteralExpr) expression;
            return Expressions.stringConstant(expr.getValue());
        } else if (expression instanceof NullLiteralExpr) {
            return Expressions.numberConstant(0);
        } else if (expression instanceof EnclosedExpr) {
            return mapExpression(((EnclosedExpr) expression).getInner());
        } else if (expression instanceof InstanceOfExpr) {
            throw new UnsupportedOperationException("Instanceof checks are not supported");
        } else if (expression instanceof ConditionalExpr) {
            return mapConditionalExpression((ConditionalExpr) expression);
        } else if (expression instanceof LambdaExpr ||
                    expression instanceof MethodReferenceExpr) {
            throw new UnsupportedOperationException("Lambdas are not supported yet");
        } else if (expression instanceof UnaryExpr) {
            return mapUnaryExpression((UnaryExpr) expression);
        } else if (expression instanceof ObjectCreationExpr) {
            throw new UnsupportedOperationException("Object instantiations are not supported yet");
        } else if (expression instanceof FieldAccessExpr) {
            ((FieldAccessExpr) expression).resolve()

        } else if (expression instanceof MethodCallExpr) {

        } else {
            throw new UnsupportedOperationException("Unsupported expression " + expression);
        }
    }

    public static void translateVariableDeclarationExpression(VariableDeclarationExpr expr, Context context) {
        List<Modifier> modifiers = expr.getModifiers();
        boolean isFinalVariable = false;
        if (modifiers.size() == 1 && !modifiers.contains(Modifier.finalModifier())) {
            throw new RuntimeException("Unsupported modifiers for local variable " + expr.toString() + "; expected final");
        } else if (!modifiers.isEmpty()) {
            isFinalVariable = true;
        }
        for (VariableDeclarator declarator : expr.getVariables()) {
            String variableName = declarator.getName().getIdentifier();
            ResolvedType variableType = declarator.resolve().getType();
            context.declareLocalVar(variableName, variableType, isFinalVariable);
            declarator.getInitializer().ifPresent(initializer -> {
                IExpression initializerExpression = mapExpression(initializer);
                ResolvedType expressionType = resolveExpressionType(initializer).resolve();
                context.visitSetLocalVar(variableName, expressionType, initializerExpression);
            });
        }
    }

    public static void translateAssignExpression(AssignExpr assign, Context context) {
        Expression targetExpression = assign.getTarget();
        if (!(targetExpression instanceof NameExpr)) {
            throw new IllegalArgumentException("Left side operand of assignment is not a name expression");
        }
        NameExpr nameExpr = (NameExpr) targetExpression;
        ResolvedValueDeclaration valueDeclaration = nameExpr.resolve();
        if (valueDeclaration instanceof ResolvedFieldDeclaration) {
            //field assign expression
            ResolvedFieldDeclaration fieldDeclaration = (ResolvedFieldDeclaration) valueDeclaration;
            if (fieldDeclaration.isStatic()) {

            }
        }


        if (context.getLocalVar(variableName) != null) {
            Expression valueExpression = assign.getValue();
            ResolvedType resultType;
            IExpression resultExpression;
            if (assign.getOperator() == AssignExpr.Operator.ASSIGN) {
                resultType = resolveExpressionType(valueExpression).resolve();
                resultExpression = mapExpression(valueExpression);
            } else {
                Pair<IExpression, ResolvedType> result = applyOperator(nameExpr, valueExpression, assign.getOperator().toBinaryOperator().get());
                resultType = result.second;
                resultExpression = result.first;
            }
            context.visitSetLocalVar(variableName, resultType, resultExpression);
            return;
        }
        throw new UnsupportedOperationException("Local symbols are not supported yet");
    }

    public static Pair<IExpression, ResolvedType> applyOperator(Expression leftSide, Expression rightSide, BinaryExpr.Operator operator) {
        IExpression firstResult = applyComparisonOperator(leftSide, rightSide, operator);
        if (firstResult != null) {
            ResolvedPrimitiveType primitiveType = ResolvedPrimitiveType.BOOLEAN;
            return Pair.of(firstResult, primitiveType);
        }
        IExpression secondResult = applyBooleanOperator(leftSide, rightSide, operator);
        if (secondResult != null) {
            ResolvedPrimitiveType primitiveType = ResolvedPrimitiveType.BOOLEAN;
            return Pair.of(secondResult, primitiveType);
        }
        IExpression thirdResult = applyArithmeticOperator(leftSide, rightSide, operator);
        if (thirdResult != null) {
            ResolvedPrimitiveType primitiveType = resolveNumericType(leftSide, rightSide);
            return Pair.of(thirdResult, primitiveType);
        }
        throw new UnsupportedOperationException("Unsupported binary operator " + operator);
    }

    private static IExpression applyArithmeticOperator(Expression leftSide, Expression rightSide, Operator operator) {
        ensureNumericType(leftSide);
        ensureNumericType(rightSide);
        IExpression leftSideMapped = mapExpression(leftSide);
        IExpression rightSideMapped = mapExpression(rightSide);
        switch (operator) {
            case PLUS: return Expressions.add(leftSideMapped, rightSideMapped);
            case MINUS: return Expressions.subtract(leftSideMapped, rightSideMapped);
            case MULTIPLY: return Expressions.multiply(leftSideMapped, rightSideMapped);
            case DIVIDE: return Expressions.divide(leftSideMapped, rightSideMapped);
            case REMAINDER: return Expressions.remainder(leftSideMapped, rightSideMapped);
            default: return null;
        }
    }

    private static IExpression applyBooleanOperator(Expression leftSide, Expression rightSide, Operator operator) {
        if (operator == Operator.OR) {
            ensureBooleanType(leftSide);
            ensureBooleanType(rightSide);
            return Expressions.or(mapExpression(leftSide), mapExpression(rightSide));
        } else if (operator == Operator.AND) {
            ensureBooleanType(leftSide);
            ensureBooleanType(rightSide);
            return Expressions.and(mapExpression(leftSide), mapExpression(rightSide));
        } else {
            return null;
        }
    }

    private static IExpression applyComparisonOperator(Expression leftSide, Expression rightSide, Operator operator) {
        CompareOperator compareOperator;
        switch (operator) {
            case EQUALS: compareOperator = CompareOperator.EQUALS; break;
            case NOT_EQUALS: compareOperator = CompareOperator.NOT_EQUALS; break;
            case LESS: compareOperator = CompareOperator.LESS; break;
            case LESS_EQUALS: compareOperator = CompareOperator.LESS_OR_EQUALS; break;
            case GREATER: compareOperator = CompareOperator.GREATER; break;
            case GREATER_EQUALS: compareOperator = CompareOperator.GREATER_OR_EQUALS; break;
            default: return null;
        }
        ensureNumericType(leftSide);
        ensureNumericType(rightSide);

        IExpression mappedLeftSide = mapExpression(leftSide);
        IExpression mappedRightSide = mapExpression(rightSide);
        return Expressions.compare(mappedLeftSide, compareOperator, mappedRightSide);
    }

    public static IExpression mapConditionalExpression(ConditionalExpr expr) {
        return Expressions.conditionalExpression(mapExpression(expr.getCondition()),
                mapExpression(expr.getThenExpr()),
                mapExpression(expr.getElseExpr()));
    }

    public static IExpression mapCastExpression(CastExpr castExpr) {
        ResolvedType expressionType = resolveExpressionType(castExpr.getExpression()).resolve();
        ResolvedType castedType = castExpr.getType().resolve();
        Preconditions.checkArgument(expressionType.isAssignableBy(castedType), "Cannot cast incompatible types");
        return mapExpression(castExpr.getExpression());
    }

    public static IExpression mapBinaryExpression(BinaryExpr expression) {
        return applyOperator(expression.getLeft(), expression.getRight(), expression.getOperator()).first;
    }

    public static IExpression mapUnaryExpression(UnaryExpr expr) {
        switch (expr.getOperator()) {
            case PLUS: return mapExpression(expr.getExpression());
            case MINUS: return Expressions.negate(mapExpression(expr.getExpression()));
            case LOGICAL_COMPLEMENT: return Expressions.negate(mapExpression(expr.getExpression()));
            default: throw new UnsupportedOperationException("Unary operation " + expr.getOperator() + " is not supported inside of expressions");
        }
    }

    public static IExpression mapArrayAccessExpression(ArrayAccessExpr expression) {
        IExpression arrayExpression = mapExpression(expression.getName());
        IExpression indexExpression = mapExpression(expression.getIndex());
        return Expressions.getValueInArray(arrayExpression, indexExpression);
    }

    public static void ensureNumericType(Expression expression) {
        ResolvedType type = resolveExpressionType(expression).resolve();
        Preconditions.checkArgument(type.isPrimitive(), expression + " is not a primitive type; expected numeric type");
        Preconditions.checkArgument(type.asPrimitive().isNumeric(), expression + " is not a numeric type");
    }

    public static void ensureBooleanType(Expression expression) {
        ResolvedType type = resolveExpressionType(expression).resolve();
        Preconditions.checkArgument(type.isPrimitive(), expression + " is not a primitive type; expected boolean");
        Preconditions.checkArgument(type.asPrimitive() == ResolvedPrimitiveType.BOOLEAN, expression + " is not a boolean");
    }

    public static ResolvedPrimitiveType resolveNumericType(Expression rightSide, Expression leftSide) {
        ResolvedPrimitiveType first = resolveExpressionType(rightSide).resolve().asPrimitive();
        ResolvedPrimitiveType second = resolveExpressionType(leftSide).resolve().asPrimitive();
        return getTypeWithBiggerPriority(first, second);
    }

    private static ResolvedPrimitiveType getTypeWithBiggerPriority(ResolvedPrimitiveType first, ResolvedPrimitiveType second) {
        int firstPriority = first.ordinal();
        int secondPriority = second.ordinal();
        if (firstPriority > secondPriority) {
            return first;
        } else {
            return second;
        }
    }

    public static Type resolveExpressionType(Expression expression) {
        if(expression instanceof ArrayAccessExpr) {
            Type arrayType = resolveExpressionType(((ArrayAccessExpr) expression).getName());
            return arrayType.getElementType();
        }
    }

}
