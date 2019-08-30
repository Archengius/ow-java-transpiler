package me.archen.owtranspiler.transpiler;

import me.archen.owtranspiler.workshop.expression.MethodCallExpression;

import java.util.ArrayList;
import java.util.List;

public class LoopInfo {

    private final String loopLabel;
    private final List<MethodCallExpression> continueStatements = new ArrayList<>();
    private final List<MethodCallExpression> breakStatements = new ArrayList<>();

    public LoopInfo(String loopLabel) {
        this.loopLabel = loopLabel;
    }

    public String getLoopLabel() {
        return loopLabel;
    }

    public List<MethodCallExpression> getContinueStatements() {
        return continueStatements;
    }

    public List<MethodCallExpression> getBreakStatements() {
        return breakStatements;
    }
}
