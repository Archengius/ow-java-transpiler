package me.archen.owtranspiler.transpiler;

import me.archen.owtranspiler.workshop.ActionList;
import me.archen.owtranspiler.workshop.expression.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionVisitor {

    public static final int LOCAL_VARIABLES_TABLE_VAR_INDEX = 1;
    public static final int FIELDS_VAR_INDEX = 2;
    public static final int INSTRUCTION_POINTER_VAR_INDEX = 3;
    public static final int LOCAL_VAR_SAVE_INDEX = 4;

    public static final String JUMP_METHOD_NAME = "SKIP";
    private static final String CONDITIONAL_JUMP_METHOD_NAME = "SKIP IF";
    private static final String LOOP_METHOD_NAME = "LOOP";


    private List<IAction> actions = new ArrayList<>();

    public void visitAction(IAction action) {
        this.actions.add(action);
    }

    public void visitActions(ActionVisitor visitor) {
        this.actions.addAll(visitor.actions);
    }

    public int getInstructionsCount() {
        return actions.size();
    }

    public int getInstructionNumber(IAction action) {
        return actions.indexOf(action) + 1;
    }

    public void visitGlobalVariableSet(int variableIndex, IExpression newValue) {
        this.actions.add(new SetGlobalVariableExpression(variableIndex, newValue));
    }

    public void visitGlobalVariableSetAtIndex(int variableIndex, IExpression index, IExpression value) {
        this.actions.add(new SetGlobalVariableAtIndexExpression(variableIndex, index, value));
    }

    public void visitMethodCall(String methodName, IExpression... parameters) {
        this.actions.add(new MethodCallExpression(methodName, Arrays.asList(parameters)));
    }

    public void visitJumpInsn(IExpression insToSkip) {
        visitMethodCall(JUMP_METHOD_NAME, insToSkip);
    }

    public void visitConditionalJumpInsn(IExpression conditional, IExpression insToSkip) {
        visitMethodCall(CONDITIONAL_JUMP_METHOD_NAME, conditional, insToSkip);
    }

    public void visitLoopInsn() {
        visitMethodCall(LOOP_METHOD_NAME);
    }

    public ActionList buildList() {
        return new ActionList(actions);
    }
}
