```java
import java.util.Stack;

public class ExpressionEvaluator {

    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    private static double applyOp(double a, double b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case '^':
                return Math.pow(a, b);
        }
        return 0;
    }

    public static double evaluate(String tokens) {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length(); i++) {
            char c = tokens.charAt(i);

            if (c == ' ') continue;

            if (Character.isDigit(c)) {
                StringBuilder sbuf = new StringBuilder();
                while (i < tokens.length() && (Character.isDigit(tokens.charAt(i)) || tokens.charAt(i) == '.')) {
                    sbuf.append(tokens.charAt(i++));
                }
                values.push(Double.parseDouble(sbuf.toString()));
                i--;
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    values.push(applyOp(values.pop(), values.pop(), ops.pop()));
                }
                if (!ops.isEmpty() && ops.peek() != '(') return 0;
                else ops.pop();
            } else {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    values.push(applyOp(values.pop(), values.pop(), ops.pop()));
                }
                ops.push(c);
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOp(values.pop(), values.pop(), ops.pop()));
        }

        return values.pop();
    }

    public static void main(String[] args) {
        System.out.println(evaluate("10 + 2 * 6"));
        System.out.println(evaluate("100 * 2 + 12"));
        System.out.println(evaluate("100 * ( 2 + 12 )"));
        System.out.println(evaluate("100 * ( 2 + 12 ) / 14"));
        System.out.println(evaluate("10 + 2 * 6 / (12 - 4)"));
    }
}
```