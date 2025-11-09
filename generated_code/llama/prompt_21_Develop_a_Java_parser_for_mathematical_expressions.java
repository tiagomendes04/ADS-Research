```java
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Token {
    String type;
    double value;
    int line;
    int col;

    public Token(String type, double value, int line, int col) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.col = col;
    }
}

class Lexer {
    String[] tokens;
    int pos;

    public Lexer(String expression) {
        this.tokens = expression.split("\\s+");
        this.pos = 0;
    }

    public Token nextToken() {
        if (pos >= tokens.length) {
            return null;
        }
        String token = tokens[pos++];
        if (token.equals("+")) {
            return new Token("PLUS", 0, 0, 0);
        } else if (token.equals("-")) {
            return new Token("MINUS", 0, 0, 0);
        } else if (token.equals("*")) {
            return new Token("MUL", 0, 0, 0);
        } else if (token.equals("/")) {
            return new Token("DIV", 0, 0, 0);
        } else if (token.equals("(")) {
            return new Token("LPAREN", 0, 0, 0);
        } else if (token.equals(")")) {
            return new Token("RPAREN", 0, 0, 0);
        } else {
            try {
                return new Token("NUMBER", Double.parseDouble(token), 0, 0);
            } catch (NumberFormatException e) {
                return new Token("UNKNOWN", 0, 0, 0);
            }
        }
    }
}

class Parser {
    Lexer lexer;
    Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.nextToken();
    }

    public double parse() {
        return expression();
    }

    private double expression() {
        double result = term();
        while (currentToken.type.equals("PLUS")) {
            advance();
            result += term();
        }
        return result;
    }

    private double term() {
        double result = factor();
        while (currentToken.type.equals("MUL")) {
            advance();
            result *= factor();
        }
        return result;
    }

    private double factor() {
        if (currentToken.type.equals("NUMBER")) {
            double value = currentToken.value;
            advance();
            return value;
        } else if (currentToken.type.equals("LPAREN")) {
            advance();
            double result = expression();
            advance();
            return result;
        } else if (currentToken.type.equals("MINUS")) {
            advance();
            return -factor();
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken.type);
        }
    }

    private void advance() {
        currentToken = lexer.nextToken();
    }
}

public class Main {
    public static void main(String[] args) {
        String expression = "2 + 3 * 4";
        Lexer lexer = new Lexer(expression);
        Parser parser = new Parser(lexer);
        System.out.println(parser.parse());
    }
}
```