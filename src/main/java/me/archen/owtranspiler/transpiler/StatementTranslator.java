package me.archen.owtranspiler.transpiler;

import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.PrimitiveType.Primitive;
import com.github.javaparser.ast.type.Type;
import com.google.common.base.Preconditions;
import me.archen.owtranspiler.workshop.expression.*;

public class StatementTranslator {

    private static final DataKey<String> STATEMENT_LABEL_KEY = new DataKey<String>() {};

    public void translateStatement(Statement statement, Context context) {
        if (statement instanceof ExpressionStmt) {
            translateExpressionStatement((ExpressionStmt) statement, context);
        } else if (statement instanceof BlockStmt) {
            translateBlockStatement((BlockStmt) statement, context);
        } else if (statement instanceof BreakStmt) {
            translateBreakStatement((BreakStmt) statement, context);
        } else if (statement instanceof ContinueStmt) {
            translateContinueStatement((ContinueStmt) statement, context);
        } else if (statement instanceof LabeledStmt) {
            translateLabeledStatement((LabeledStmt) statement, context);
        } else if (statement instanceof ForEachStmt) {
            translateForEachLoop((ForEachStmt) statement, context);
        } else if (statement instanceof ForStmt) {
            translateForLoop((ForStmt) statement, context);
        } else if (statement instanceof WhileStmt) {
            translateWhileLoop((WhileStmt) statement, context);
        } else if (statement instanceof IfStmt) {
            translateIfStatement((IfStmt) statement, context);
        } else {
            throw new RuntimeException("Unsupported statement: " + statement);
        }
    }

    public void visitBodyStart(Context context) {
        ActionVisitor loadFromPointerBody = new ActionVisitor();
        ConstantExpression methodIndex = new ConstantExpression(context.getMethodIndex());

        GlobalVariableExpression varSaveStore = new GlobalVariableExpression(ActionVisitor.LOCAL_VAR_SAVE_INDEX);
        IExpression dumpPositionExpr = Expressions.getValueInArray(varSaveStore, methodIndex);
        loadFromPointerBody.visitGlobalVariableSet(ActionVisitor.LOCAL_VARIABLES_TABLE_VAR_INDEX, dumpPositionExpr);

        GlobalVariableExpression instrPointerStore = new GlobalVariableExpression(ActionVisitor.INSTRUCTION_POINTER_VAR_INDEX);
        IExpression instrPointerExpr = Expressions.getValueInArray(instrPointerStore, methodIndex);
        loadFromPointerBody.visitJumpInsn(instrPointerExpr);

        IExpression compareExpr = new CompareExpression(CompareOperator.EQUALS, instrPointerExpr, new ConstantExpression(0));
        context.visitor().visitConditionalJumpInsn(compareExpr, new ConstantExpression(loadFromPointerBody.getInstructionsCount()));
        context.visitor().visitActions(loadFromPointerBody);
    }

    public void visitBodyEnd(Context context) {
        //clear instruction pointer on normal return
        ConstantExpression methodIndex = new ConstantExpression(context.getMethodIndex());
        ConstantExpression instructionPointerExpr = new ConstantExpression(0);
        context.visitor().visitGlobalVariableSetAtIndex(ActionVisitor.INSTRUCTION_POINTER_VAR_INDEX, methodIndex, instructionPointerExpr);
    }

    public void translateExpressionStatement(ExpressionStmt statement, Context context) {
        ExpressionMapper.translateTopLevelExpression(statement.getExpression(), context);
    }

    public void translateBreakStatement(BreakStmt statement, Context context) {
        String loopLabelName = statement.getValue().map(it -> it.asNameExpr().getName().getIdentifier()).orElse(null);
        LoopInfo loopInfo = context.getLoopByLabel(loopLabelName);
        Preconditions.checkState(loopInfo != null, "break statement outside of the loop");
        MethodCallExpression expression = new MethodCallExpression(ActionVisitor.JUMP_METHOD_NAME, new ConstantExpression(0));
        context.visitor().visitAction(expression);
        loopInfo.getBreakStatements().add(expression);
    }

    public void translateContinueStatement(ContinueStmt statement, Context context) {
        String loopLabelName = statement.getLabel().map(SimpleName::getIdentifier).orElse(null);
        LoopInfo loopInfo = context.getLoopByLabel(loopLabelName);
        Preconditions.checkState(loopInfo != null, "continue statement outside of the loop");
        MethodCallExpression expression = new MethodCallExpression(ActionVisitor.JUMP_METHOD_NAME, new ConstantExpression(0));
        context.visitor().visitAction(expression);
        loopInfo.getContinueStatements().add(expression);
    }

    public void translateLabeledStatement(LabeledStmt statement, Context context) {
        Statement wrappedStatement = statement.getStatement();
        wrappedStatement.setData(STATEMENT_LABEL_KEY, statement.getLabel().getIdentifier());
        translateStatement(wrappedStatement, context);
    }

    //makes simple array-alike loop out of foreach loop
    public void translateForEachLoop(ForEachStmt statement, Context context) {
        BlockStmt wrappedBlockStmt = new BlockStmt();

        Expression initialArrayExpr = statement.getIterable();
        String arrayVariableName = String.format("$array$%s", statement.getVariableDeclarator().getName());
        Type arrayType = ExpressionMapper.resolveExpressionType(initialArrayExpr);
        VariableDeclarationExpr variableDeclaration = new VariableDeclarationExpr(arrayType, arrayVariableName);
        variableDeclaration.getVariable(0).setInitializer(initialArrayExpr);
        NameExpr arrayVarAccessExpr = new NameExpr(arrayVariableName);
        wrappedBlockStmt.addStatement(variableDeclaration);

        String indexVariableName = String.format("$index$%s", statement.getVariableDeclarator().getName());
        VariableDeclarationExpr indexDeclaration = new VariableDeclarationExpr(new PrimitiveType(Primitive.INT), indexVariableName);
        indexDeclaration.getVariable(0).setInitializer(new IntegerLiteralExpr(0));
        NameExpr indexVariableGetExpr = new NameExpr(indexVariableName);

        MethodCallExpr arrayLengthExpr = new MethodCallExpr(arrayVarAccessExpr, "length");
        BinaryExpr conditionExpr = new BinaryExpr(indexVariableGetExpr, arrayLengthExpr, Operator.LESS);

        UnaryExpr incrementExpr = new UnaryExpr(indexVariableGetExpr, UnaryExpr.Operator.POSTFIX_INCREMENT);

        VariableDeclarationExpr valueDeclaration = statement.getVariable();
        valueDeclaration.getVariable(0).setInitializer(new ArrayAccessExpr(arrayVarAccessExpr, indexVariableGetExpr));

        BlockStmt bodyStatement = new BlockStmt();
        bodyStatement.addStatement(valueDeclaration);
        bodyStatement.addStatement(statement.getBody());

        ForStmt forStmt = new ForStmt();
        statement.getRange().ifPresent(forStmt::setRange);
        forStmt.setData(STATEMENT_LABEL_KEY, statement.getData(STATEMENT_LABEL_KEY));

        forStmt.setInitialization(new NodeList<Expression>().addLast(indexDeclaration));
        forStmt.setCompare(conditionExpr);
        forStmt.setUpdate(new NodeList<Expression>().addLast(incrementExpr));
        forStmt.setBody(bodyStatement);

        wrappedBlockStmt.addStatement(forStmt);

        translateBlockStatement(wrappedBlockStmt, context);
    }

    public void translateForLoop(ForStmt statement, Context context) {
        BlockStmt wrapBody = new BlockStmt();
        for(Expression expression : statement.getInitialization()) {
            wrapBody.addStatement(expression);
        }
        WhileStmt whileStmt = new WhileStmt();
        whileStmt.setData(STATEMENT_LABEL_KEY, statement.getData(STATEMENT_LABEL_KEY));
        Expression conditionExpr = statement.getCompare().orElse(new BooleanLiteralExpr(true));
        whileStmt.setCondition(conditionExpr);

        BlockStmt loopBody = new BlockStmt();
        loopBody.addStatement(statement.getBody());
        int firstIndex = 0;
        for(Expression expression : statement.getUpdate()) {
            if(expression instanceof UnaryExpr &&
                expression.asUnaryExpr().isPrefix()) {
                //prefix unary expressions go in the start of body
                loopBody.addStatement(firstIndex++, expression);
            } else {
                //otherwise, it goes to the end of the body
                loopBody.addStatement(expression);
            }
        }
        whileStmt.setBody(loopBody);
        statement.getRange().ifPresent(whileStmt::setRange);
        wrapBody.addStatement(whileStmt);
        translateBlockStatement(wrapBody, context);
    }

    public void translateWhileLoop(WhileStmt statement, Context context) {
        int statementsInBody = countStatementsUnrollingLoops(statement.getBody());
        int timesToRepeatBody = statementsInBody < 16 ? 16 : 1;
        int instructionPointer = context.visitor().getInstructionsCount();
        String loopLabelName = statement.getData(STATEMENT_LABEL_KEY);

        LoopInfo loopInfo = new LoopInfo(loopLabelName);
        context.pushLoop(loopInfo);

        for(int i = 0; i < timesToRepeatBody; i++) {
            IfStmt ifStmt = new IfStmt(statement.getCondition(), statement.getBody(), null);
            translateIfStatement(ifStmt, context);

            //continue statements lead to next statement
            loopInfo.getContinueStatements().forEach(continueMethod -> {
                int instructionsCount = context.visitor().getInstructionsCount() - context.visitor().getInstructionNumber(continueMethod);
                continueMethod.getArguments().set(0, new ConstantExpression(instructionsCount));
            });
            loopInfo.getContinueStatements().clear();
        }

        ConstantExpression methodIndex = new ConstantExpression(context.getMethodIndex());
        ConstantExpression instructionPointerExpr = new ConstantExpression(instructionPointer);

        ActionVisitor saveBlockVisitor = new ActionVisitor();
        IExpression localVariablesTable = new GlobalVariableExpression(ActionVisitor.LOCAL_VARIABLES_TABLE_VAR_INDEX);
        saveBlockVisitor.visitGlobalVariableSetAtIndex(ActionVisitor.LOCAL_VAR_SAVE_INDEX, methodIndex, localVariablesTable);
        context.visitor().visitGlobalVariableSetAtIndex(ActionVisitor.INSTRUCTION_POINTER_VAR_INDEX, methodIndex, instructionPointerExpr);

        IExpression jumpCondition = Expressions.negate(ExpressionMapper.mapExpression(statement.getCondition()));
        context.visitor().visitConditionalJumpInsn(jumpCondition, new ConstantExpression(saveBlockVisitor.getInstructionsCount()));
        context.visitor().visitActions(saveBlockVisitor);

        //break statements lead to the end of the loop
        loopInfo.getBreakStatements().forEach(breakStatement -> {
            int instructionsCount = context.visitor().getInstructionsCount() - context.visitor().getInstructionNumber(breakStatement);
            breakStatement.getArguments().set(0, new ConstantExpression(instructionsCount));
        });
        context.popLoop();
    }

    public void translateBlockStatement(BlockStmt blockStmt, Context context) {
        Context blockContext = context.forkContext();
        for(Statement statement : blockStmt.getStatements()) {
            translateStatement(statement, blockContext);
        }
    }

    public void translateIfStatement(IfStmt statement, Context context) {
        Context elseStatementContext = null;
        if(statement.hasElseBlock()) {
            elseStatementContext = context.forkContext();
            Statement elseStatement = statement.getElseStmt().get();
            translateStatement(elseStatement, elseStatementContext);
        }
        Context ifStatementContext = context.forkContext();
        translateStatement(statement.getThenStmt(), ifStatementContext);
        if(elseStatementContext != null) {
            IExpression numToSkip = new ConstantExpression(elseStatementContext.visitor().getInstructionsCount());
            ifStatementContext.visitor().visitJumpInsn(numToSkip);
        }
        IExpression jumpCondition = Expressions.negate(ExpressionMapper.mapExpression(statement.getCondition(), context));
        IExpression numToSkip = new ConstantExpression(ifStatementContext.visitor().getInstructionsCount());

        context.visitor().visitConditionalJumpInsn(jumpCondition, numToSkip);
        context.visitor().visitActions(ifStatementContext.visitor());
        if(elseStatementContext != null) {
            context.visitor().visitActions(elseStatementContext.visitor());
        }
    }

    private static int countStatementsUnrollingLoops(Statement statement) {
        if(statement instanceof WhileStmt ||
            statement instanceof ForStmt ||
            statement instanceof ForEachStmt) {
            return Integer.MAX_VALUE;
        } else if(statement instanceof IfStmt) {
            IfStmt ifStmt = statement.asIfStmt();
            int a = countStatementsUnrollingLoops(ifStmt.getThenStmt());
            int b = ifStmt.getElseStmt().map(StatementTranslator::countStatementsUnrollingLoops).orElse(0);
            return a + b;
        } else if (statement instanceof SwitchStmt) {
            SwitchStmt switchStmt = statement.asSwitchStmt();
            return switchStmt.getEntries().stream()
                    .flatMap(it -> it.getStatements().stream())
                    .mapToInt(StatementTranslator::countStatementsUnrollingLoops).sum();
        } else if (statement instanceof BlockStmt) {
            BlockStmt blockStmt = statement.asBlockStmt();
            return blockStmt.getStatements().stream()
                    .mapToInt(StatementTranslator::countStatementsUnrollingLoops).sum();
        } else return 1;
    }

}
