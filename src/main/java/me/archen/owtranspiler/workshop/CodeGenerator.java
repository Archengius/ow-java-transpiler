package me.archen.owtranspiler.workshop;

import me.archen.owtranspiler.workshop.Tokens.Identifier;
import me.archen.owtranspiler.workshop.Tokens.Number;
import me.archen.owtranspiler.workshop.Tokens.StringConstant;
import me.archen.owtranspiler.workshop.Tokens.Token;

public class CodeGenerator {

    private final StringBuilder builder;

    public CodeGenerator() {
        this.builder = new StringBuilder();
    }

    public void appendToken(IToken token) {
        token.append(this.builder);
    }

    public void appendIdentifier(String identifier) {
        appendToken(new Identifier(identifier));
    }

    public void appendNumber(double number) {
        appendToken(new Number(number));
    }

    public void appendStringLdc(String string) {
        appendToken(new StringConstant(string));
    }

    public void appendCallStart() {
        appendToken(Token.LBRACKET);
    }

    public void appendCallEnd() {
        appendToken(Token.RBRACKET);
    }

    public void appendBlockStart() {
        appendToken(Token.LCBRACKET);
    }

    public void appendBlockEnd() {
        appendToken(Token.RCBRACKET);
    }

    public void appendComma() {
        appendToken(Token.COMMA);
    }

    public void appendSemicolon() {
        appendToken(Token.SEMICOLON);
    }

    public String build() {
        return builder.toString();
    }
}
