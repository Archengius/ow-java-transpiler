package me.archen.owtranspiler.workshop;

public class Tokens {

    public enum Token implements IToken {

        LCBRACKET('{'),
        RCBRACKET('}'),
        LBRACKET('('),
        RBRACKET(')'),
        SEMICOLON(';'),
        COMMA(',');

        private final char symbol;

        Token(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }

        @Override
        public void append(StringBuilder builder) {
            builder.append(symbol);
        }
    }

    public enum CompareOperatorType implements IToken {

        EQUALS("=="),
        NOT_EQUALS("!="),
        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL("<="),
        GREATER_THAN(">"),
        GREATER_THAN_OR_EQUAL(">=");

        private final String name;

        CompareOperatorType(String name) {
            this.name = name;
        }

        @Override
        public void append(StringBuilder builder) {
            builder.append(name);
        }
    }

    public static class Identifier implements IToken {

        private final String identifier;

        public Identifier(String identifier) {
            this.identifier = identifier;
        }

        public String getIdentifier() {
            return identifier;
        }

        @Override
        public void append(StringBuilder builder) {
            builder.append(identifier);
        }
    }

    public static class Number implements IToken {

        private final double value;

        public Number(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public void append(StringBuilder builder) {
            if(value % 1 == 0) {
                //write integer-alike values without fractional part
                builder.append((int) value);
            } else {
                builder.append(value);
            }
        }
    }

    public static class StringConstant implements IToken {

        private final String string;

        public StringConstant(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }

        @Override
        public void append(StringBuilder builder) {
            builder.append('"').append(string).append('"');
        }
    }

}
