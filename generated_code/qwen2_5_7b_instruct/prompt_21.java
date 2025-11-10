```java
import java.util.Stack;

public class MathExpressionParser {

    public static int parse(String expression) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        String[] tokens = expression.split(" ");
        
        for (String token : tokens) {
            if (token.matches("\\d+")) {
                values.push(Integer.parseInt(token));
            } else if (token.equals("(")) {
                operators.push(token.charAt(0));
            } else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                while (!operators.isEmpty() && hasPrecedence(token, operators.peek())) {
                    applyOperation(values, operators);
                }
                operators.push(token.charAt(0));
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    applyOperation(values, operators);
                }
                operators.pop();
            }
        }
        
        while (!operators.isEmpty()) {
            applyOperation(values, operators);
        }
        
        return values.pop();
    }
    
    private static boolean hasPrecedence(String op1, char op2) {
        if ((op1 == "*" || op1 == "/") && (op2 == "+" || op2 == "-")) {
            return false;
        } else {
            return true;
        }
    }
    
    private static void applyOperation(Stack<Integer> values, Stack<Character> operators) {
        int val2 = values.pop();
        int val1 = values.pop();
        char op = operators.pop();
        
        switch (op) {
            case '+':
                values.push(val1 + val2);
                break;
            case '-':
                values.push(val1 - val2);
                break;
            case '*':
                values.push(val1 * val2);
                break;
            case '/':
                values.push(val1 / val2);
                break;
        }
    }
    
    public static void main(String[] args) {
        String expression = "3 + 5 * (10 / (12 / (3 + 1) - 1))";
        int result = parse(expression);
        System.out.println("Result: " + result);
    }
}
```
```