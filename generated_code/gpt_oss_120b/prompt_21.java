```java
import java.util.*;

public class ExpressionEvaluator {

    private static final Map<String, Integer> PRECEDENCE = Map.of(
        "+", 1,
        "-", 1,
        "*", 2,
        "/", 2,
        "^", 3
    );

    private static final Set<String> RIGHT_ASSOCIATIVE = Set.of("^");

    public double evaluate(String expr) {
        List<String> tokens = tokenize(expr);
        List<String> rpn = toRPN(tokens);
        return evalRPN(rpn);
    }

    private List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }
            if (isOperatorChar(c) || c == '(' || c == ')') {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                tokens.add(String.valueOf(c));
                i++;
            } else if (Character.isDigit(c) || c == '.') {
                sb.append(c);
                i++;
                while (i < expr.length()) {
                    char nc = expr.charAt(i);
                    if (Character.isDigit(nc) || nc == '.') {
                        sb.append(nc);
                        i++;
                    } else {
                        break;
                    }
                }
                tokens.add(sb.toString());
                sb.setLength(0);
            } else if (Character.isLetter(c)) {
                sb.append(c);
                i++;
                while (i < expr.length() && Character.isLetterOrDigit(expr.charAt(i))) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                tokens.add(sb.toString());
                sb.setLength(0);
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }
        if (sb.length() > 0) tokens.add(sb.toString());
        return tokens;
    }

    private boolean isOperatorChar(char c) {
        return "+-*/^".indexOf(c) >= 0;
    }

    private List<String> toRPN(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (isNumber(token) || isVariable(token)) {
                output.add(token);
            } else if (isFunction(token)) {
                stack.push(token);
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek())) {
                    String top = stack.peek();
                    if ((RIGHT_ASSOCIATIVE.contains(token) && PRECEDENCE.get(token) < PRECEDENCE.get(top)) ||
                        (!RIGHT_ASSOCIATIVE.contains(token) && PRECEDENCE.get(token) <= PRECEDENCE.get(top))) {
                        output.add(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(token);
            } else if ("(".equals(token)) {
                stack.push(token);
            } else if (")".equals(token)) {
                while (!stack.isEmpty() && !"(".equals(stack.peek())) {
                    output.add(stack.pop());
                }
                if (stack.isEmpty()) throw new IllegalArgumentException("Mismatched parentheses");
                stack.pop(); // pop '('
                if (!stack.isEmpty() && isFunction(stack.peek())) {
                    output.add(stack.pop());
                }
            } else {
                throw new IllegalArgumentException("Unknown token: " + token);
            }
        }

        while (!stack.isEmpty()) {
            String top = stack.pop();
            if ("(".equals(top) || ")".equals(top))
                throw new IllegalArgumentException("Mismatched parentheses");
            output.add(top);
        }
        return output;
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isVariable(String token) {
        return token.matches("[a-zA-Z][a-zA-Z0-9_]*") && !isFunction(token);
    }

    private boolean isFunction(String token) {
        return Set.of("sin", "cos", "tan", "log", "ln", "sqrt", "abs", "exp").contains(token.toLowerCase());
    }

    private boolean isOperator(String token) {
        return PRECEDENCE.containsKey(token);
    }

    private double evalRPN(List<String> rpn) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String token : rpn) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isVariable(token)) {
                throw new IllegalArgumentException("Undefined variable: " + token);
            } else if (isOperator(token)) {
                double b = stack.pop();
                double a = stack.pop();
                double res = applyOperator(token, a, b);
                stack.push(res);
            } else if (isFunction(token)) {
                double a = stack.pop();
                double res = applyFunction(token, a);
                stack.push(res);
            } else {
                throw new IllegalArgumentException("Invalid token in RPN: " + token);
            }
        }
        if (stack.size() != 1) throw new IllegalStateException("Invalid expression");
        return stack.pop();
    }

    private double applyOperator(String op, double a, double b) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            case "^" -> Math.pow(a, b);
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }

    private double applyFunction(String func, double a) {
        return switch (func.toLowerCase()) {
            case "sin" -> Math.sin(a);
            case "cos" -> Math.cos(a);
            case "tan" -> Math.tan(a);
            case "log" -> Math.log10(a);
            case "ln" -> Math.log(a);
            case "sqrt" -> Math.sqrt(a);
            case "abs" -> Math.abs(a);
            case "exp" -> Math.exp(a);
            default -> throw new IllegalArgumentException("Unknown function: " + func);
        };
    }

    // Simple test harness
    public static void main(String[] args) {
        ExpressionEvaluator ev = new ExpressionEvaluator();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter expressions (empty line to quit):");
        while (true) {
            String line = sc.nextLine();
            if (line == null || line.trim().isEmpty()) break;
            try {
                double result = ev.evaluate(line);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
```