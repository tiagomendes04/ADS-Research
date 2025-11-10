```java
import java.util.Stack;

public class MathParser {

    static class Token {
        char type;
        String value;

        Token(char type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    static class Expression {
        String value;

        Expression(String value) {
            this.value = value;
        }
    }

    static String parse(String expression) {
        Stack<Expression> stack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == ' ') continue;
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                // Add operator to stack
                stack.push(new Expression(expression.substring(i, i + 1)));
            } else if (Character.isDigit(c)) {
                // Add number to stack
                String number = "";
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    number += expression.charAt(i);
                    i++;
                }
                stack.push(new Expression(number));
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String expression = "1 + 2 * 3 - 4";
        Expression result = parse(expression);
        System.out.println(result.value);
    }
}
```