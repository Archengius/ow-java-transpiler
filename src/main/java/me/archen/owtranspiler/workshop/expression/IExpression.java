package me.archen.owtranspiler.workshop.expression;

import me.archen.owtranspiler.workshop.CodeGenerator;

public interface IExpression {

    void writeToGenerator(CodeGenerator codeGenerator);
}
