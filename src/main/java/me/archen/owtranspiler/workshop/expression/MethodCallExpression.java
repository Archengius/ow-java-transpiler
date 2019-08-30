package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodCallExpression implements IExpression, IAction {

    private final String name;
    private final List<IExpression> arguments;

    public MethodCallExpression(String name, IExpression... arguments) {
        this.name = name;
        this.arguments = new ArrayList<>(Arrays.asList(arguments));
    }

    public MethodCallExpression(String name, List<IExpression> arguments) {
        this.name = name;
        this.arguments = new ArrayList<>(arguments);
    }

    public String getName() {
        return name;
    }

    public List<IExpression> getArguments() {
        return arguments;
    }

    @Override
    public void writeToGenerator(CodeGenerator codeGenerator) {
        codeGenerator.appendIdentifier(name);
        codeGenerator.appendCallStart();
        int lastIndex = arguments.size() - 1;
        for(int i = 0; i <= lastIndex; i++) {
            arguments.get(i).writeToGenerator(codeGenerator);
            if(i != lastIndex) {
                codeGenerator.appendComma();
            }
        }
        codeGenerator.appendCallEnd();
    }

}
