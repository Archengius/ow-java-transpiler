package me.archen.owtranspiler.workshop;

import me.archen.owtranspiler.workshop.expression.IAction;
import me.archen.owtranspiler.workshop.expression.MethodCallExpression;

import java.util.ArrayList;
import java.util.List;

public class ActionList {

    public static final String ACTION_LIST_IDENTIFIER = "actions";

    private final List<IAction> methodCallList;

    public ActionList(List<IAction> methodCallList) {
        this.methodCallList = new ArrayList<>(methodCallList);
    }

    public List<IAction> getMethodCallList() {
        return methodCallList;
    }

    public void writeToGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(ACTION_LIST_IDENTIFIER);
        codeGenerator.appendBlockStart();
        for(IAction expression : methodCallList) {
            expression.writeToGenerator(codeGenerator);
            codeGenerator.appendSemicolon();
        }
        codeGenerator.appendBlockEnd();
    }
}
