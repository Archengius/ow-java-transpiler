package me.archen.owtranspiler.transpiler;

import com.github.javaparser.resolution.types.ResolvedType;
import com.google.common.base.Preconditions;
import me.archen.owtranspiler.workshop.expression.ConstantExpression;
import me.archen.owtranspiler.workshop.expression.GlobalVariableExpression;
import me.archen.owtranspiler.workshop.expression.IExpression;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Context {

    private final int methodIndex;
    private final Map<String, LocalVarInfo> localVarMap = new HashMap<>();
    private final ActionVisitor actionVisitor;
    private int nextVariableIndex = 0;
    private Deque<LoopInfo> loopInfos = new ArrayDeque<>();

    public Context(int methodIndex) {
        this.methodIndex = methodIndex;
        this.actionVisitor = new ActionVisitor();
    }

    private Context(Context parent) {
        this.methodIndex = parent.methodIndex;
        this.localVarMap.putAll(parent.localVarMap);
        this.nextVariableIndex = parent.nextVariableIndex;
        this.actionVisitor = parent.actionVisitor;
    }

    public Context forkContext() {
        return new Context(this);
    }

    public int getMethodIndex() {
        return methodIndex;
    }

    public void pushLoop(LoopInfo loopInfo) {
        this.loopInfos.push(loopInfo);
    }

    public LoopInfo getLoopByLabel(String label) {
        if(label == null) {
            return loopInfos.peek();
        }
        for(LoopInfo loopInfo : loopInfos) {
            if(loopInfo.getLoopLabel().equals(label)) {
                return loopInfo;
            }
        }
        return null;
    }

    public void popLoop() {
        this.loopInfos.pop();
    }

    public ActionVisitor visitor() {
        return this.actionVisitor;
    }

    public void visitSetLocalVar(String variableName, ResolvedType expressionType, IExpression newValue) {
        LocalVarInfo localVarInfo = getLocalVar(variableName);
        Preconditions.checkNotNull(localVarInfo, "Local variable not found: %s", variableName);
        checkTypesAssignable(localVarInfo.variableType, expressionType);
        localVarInfo.checkAssignment(variableName);
        visitor().visitGlobalVariableSetAtIndex(ActionVisitor.LOCAL_VARIABLES_TABLE_VAR_INDEX, new ConstantExpression(localVarInfo.variableIndex), newValue);
    }

    public IExpression newLocalVarAccessExpression(String variableName) {
        LocalVarInfo localVarInfo = getLocalVar(variableName);
        Preconditions.checkNotNull(localVarInfo, "Local variable not found: %s", variableName);
        localVarInfo.checkInitialized();
        GlobalVariableExpression expression = new GlobalVariableExpression(ActionVisitor.LOCAL_VARIABLES_TABLE_VAR_INDEX);
        return Expressions.getValueInArray(expression, new ConstantExpression(localVarInfo.variableIndex));
    }

    private static void checkTypesAssignable(ResolvedType varType, ResolvedType expectedType) {
        Preconditions.checkArgument(expectedType.isAssignableBy(varType), "Cannot cast local variable of type %s to %s", varType, expectedType);
    }

    public LocalVarInfo getLocalVar(String variableName) {
        return localVarMap.get(variableName);
    }

    public LocalVarInfo declareLocalVar(String variableName, ResolvedType variableType, boolean isFinalVariable) {
        if(localVarMap.containsKey(variableName)) {
            throw new IllegalArgumentException("Variable is already defined in this scope: " + variableName);
        }
        LocalVarInfo localVarInfo = new LocalVarInfo(variableType, nextVariableIndex++, isFinalVariable);
        this.localVarMap.put(variableName, localVarInfo);
        return localVarInfo;
    }

    public static class LocalVarInfo {
        public final ResolvedType variableType;
        private final int variableIndex;
        private boolean initialized = false;
        private boolean finalModifier;

        public LocalVarInfo(ResolvedType variableType, int variableIndex, boolean finalModifier) {
            this.variableType = variableType;
            this.variableIndex = variableIndex;
            this.finalModifier = finalModifier;
        }

        private void checkAssignment(String variableName) {
            if (initialized && finalModifier) {
                throw new RuntimeException("Attempt to reassign final local variable " + variableName);
            }
            setInitialized();
        }

        private void checkInitialized() {
            Preconditions.checkState(initialized, "Attempt to access uninitialized local variable");
        }

        private void setInitialized() {
            this.initialized = true;
        }
    }

}
