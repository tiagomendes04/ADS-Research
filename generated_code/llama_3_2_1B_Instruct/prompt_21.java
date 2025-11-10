```java
import java.util.Stack;

class Token {
    String type;
    String value;

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }
}

class Parser {
    Stack<Token> stack;
    Stack<Operator> operators;

    public Parser() {
        stack = new Stack<>();
        operators = new Stack<>();
    }

    public Token parse() {
        while (!stack.isEmpty()) {
            if (stack.peek().type.equals("NUMBER")) {
                return stack.pop();
            } else if (stack.peek().type.equals("+") || stack.peek().type.equals("-") || stack.peek().type.equals("*") || stack.peek().type.equals("/")) {
                while (!stack.isEmpty() && stack.peek().type.equals("+") || stack.peek().type.equals("-")) {
                    Token operator = stack.pop();
                    Token operand = stack.pop();
                    if (operator.type.equals("+")) {
                        stack.push(new Token("NUMBER", (int) operand.value + (int) operand.value));
                    } else if (operator.type.equals("-")) {
                        stack.push(new Token("NUMBER", (int) operand.value - (int) operand.value));
                    } else if (operator.type.equals("*")) {
                        stack.push(new Token("NUMBER", (int) operand.value * (int) operand.value));
                    } else if (operator.type.equals("/")) {
                        stack.push(new Token("NUMBER", (int) operand.value / (int) operand.value));
                    }
                }
                return stack.pop();
            } else if (stack.peek().type.equals("OPERATOR")) {
                while (!stack.isEmpty() && stack.peek().type.equals("OPERATOR")) {
                    operators.push(stack.pop());
                }
            } else {
                throw new RuntimeException("Unexpected token: " + stack.peek().value);
            }
        }
        throw new RuntimeException("Unexpected end of input");
    }
}

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Token result = parser.parse();
        System.out.println("Result: " + result.value);
    }
}
```