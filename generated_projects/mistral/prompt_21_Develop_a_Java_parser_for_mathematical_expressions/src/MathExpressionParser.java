import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

public class MathExpressionParser {
    private static final Map<String, Integer> OPERATOR_PRECEDENCE = new HashMap<>();
    static {
        OPERATOR_PRECEDENCE.put("+", 1);
        OPERATOR_PRECEDENCE.put("-", 1);
        OPERATOR_PRECEDENCE.put("*", 2);
        OPERATOR_PRECEDENCE.put("/", 2);
        OPERATOR_PRECEDENCE.put("^", 3);
    }

    public static double evaluate(String expression) throws Exception {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    private static String infixToPostfix(String infix) throws Exception {
        StringBuilder output = new StringBuilder();
        Stack<String> operatorStack = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                output.append(c);
            } else if (c == '(') {
                operatorStack.push(String.valueOf(c));
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    output.append(" ").append(operatorStack.pop());
                }
                if (operatorStack.isEmpty()) {
                    throw new Exception("Mismatched parentheses");
                }
                operatorStack.pop(); // Remove the '('
            } else {
                // Operator
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") &&
                       OPERATOR_PRECEDENCE.getOrDefault(String.valueOf(c), 0) <=
                       OPERATOR_PRECEDENCE.getOrDefault(operatorStack.peek(), 0)) {
                    output.append(" ").append(operatorStack.pop());
                }
                operatorStack.push(String.valueOf(c));
            }
        }

        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().equals("(")) {
                throw new Exception("Mismatched parentheses");
            }
            output.append(" ").append(operatorStack.pop());
        }

        return output.toString().trim();
    }

    private static double evaluatePostfix(String postfix) throws Exception {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                if (stack.size() < 2) {
                    throw new Exception("Invalid expression");
                }

                double b = stack.pop();
                double a = stack.pop();
                double result = applyOperator(a, b, token);
                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new Exception("Invalid expression");
        }

        return stack.pop();
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double applyOperator(double a, double b, String operator) throws Exception {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) throw new Exception("Division by zero");
                return a / b;
            case "^": return Math.pow(a, b);
            default: throw new Exception("Unknown operator: " + operator);
        }
    }

    public static void main(String[] args) {
        try {
            String expression = "3 + 4 * 2 / (1 - 5)^2";
            double result = evaluate(expression);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}