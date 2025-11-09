import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class ExpressionParser {

    private static final Map<String, Integer> precedence = new HashMap<>();

    static {
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("^", 3);
    }

    public static double evaluate(String expression) {
        return evaluatePostfix(infixToPostfix(expression));
    }

    private static String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Deque<String> stack = new ArrayDeque<>();

        String[] tokens = expression.replaceAll("\\s+", "").split("(?<=[-+*/^()])|(?=[-+*/^()])");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (Character.isDigit(token.charAt(0)) || (token.length() > 1 && token.charAt(0) == '-' && Character.isDigit(token.charAt(1)))) {
                postfix.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.pop(); // Remove "("
            } else if (precedence.containsKey(token)) {
                while (!stack.isEmpty() && !stack.peek().equals("(") && precedence.get(token) <= precedence.getOrDefault(stack.peek(), 0)) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    private static double evaluatePostfix(String postfix) {
        Deque<Double> stack = new ArrayDeque<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (Character.isDigit(token.charAt(0)) || (token.length() > 1 && token.charAt(0) == '-' && Character.isDigit(token.charAt(1)))) {
                stack.push(Double.parseDouble(token));
            } else if (precedence.containsKey(token)) {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result;

                switch (token) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        result = operand1 / operand2;
                        break;
                    case "^":
                        result = Math.pow(operand1, operand2);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + token);
                }
                stack.push(result);
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        String expression1 = "2 + 3 * 4";
        String expression2 = "(2 + 3) * 4";
        String expression3 = "2 ^ 3 + 4";
        String expression4 = "2 + 3 ^ 4";
        String expression5 = "2 * (3 + 4) ^ 2 - 5 / 2";
        String expression6 = "-2 + 3";
        String expression7 = "2 * -3";
        String expression8 = "10 / 2 - 3 + 1";
        String expression9 = "1 + 2 * (3 / (4 - 5)) ^ 2";

        System.out.println(expression1 + " = " + evaluate(expression1));
        System.out.println(expression2 + " = " + evaluate(expression2));
        System.out.println(expression3 + " = " + evaluate(expression3));
        System.out.println(expression4 + " = " + evaluate(expression4));
        System.out.println(expression5 + " = " + evaluate(expression5));
        System.out.println(expression6 + " = " + evaluate(expression6));
        System.out.println(expression7 + " = " + evaluate(expression7));
        System.out.println(expression8 + " = " + evaluate(expression8));
        System.out.println(expression9 + " = " + evaluate(expression9));

    }
}