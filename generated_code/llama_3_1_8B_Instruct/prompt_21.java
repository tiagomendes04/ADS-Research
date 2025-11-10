```java
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathExpressionParser {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+(\\.\\d+)?");
    private static final Pattern OPERATOR_PATTERN = Pattern.compile("[+\\-*/]");
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("[a-zA-Z]+");
    private static final Pattern PARENTHESIS_PATTERN = Pattern.compile("\\([^\\)]+\\)");

    public static class Token {
        public String type;
        public String value;

        public Token(String type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    public static class Node {
        public String type;
        public double value;
        public Node left;
        public Node right;

        public Node(String type, double value) {
            this.type = type;
            this.value = value;
        }

        public Node(String type, Node left, Node right) {
            this.type = type;
            this.left = left;
            this.right = right;
        }
    }

    public static Token[] tokenize(String expression) {
        Token[] tokens = new Token[expression.length()];
        int index = 0;

        Matcher numberMatcher = NUMBER_PATTERN.matcher(expression);
        while (numberMatcher.find()) {
            tokens[index++] = new Token("NUMBER", numberMatcher.group());
        }

        Matcher operatorMatcher = OPERATOR_PATTERN.matcher(expression);
        while (operatorMatcher.find()) {
            tokens[index++] = new Token("OPERATOR", operatorMatcher.group());
        }

        Matcher variableMatcher = VARIABLE_PATTERN.matcher(expression);
        while (variableMatcher.find()) {
            tokens[index++] = new Token("VARIABLE", variableMatcher.group());
        }

        Matcher parenthesisMatcher = PARENTHESIS_PATTERN.matcher(expression);
        while (parenthesisMatcher.find()) {
            tokens[index++] = new Token("PARENTHESIS", parenthesisMatcher.group());
        }

        return tokens;
    }

    public static Node parse(Token[] tokens) {
        Map<String, Node> variables = new HashMap<>();
        for (Token token : tokens) {
            if (token.type.equals("VARIABLE")) {
                variables.put(token.value, new Node("VARIABLE", 0));
            }
        }

        return parseExpression(tokens, variables);
    }

    private static Node parseExpression(Token[] tokens, Map<String, Node> variables) {
        int index = 0;
        Node left = parseTerm(tokens, variables, index);
        while (index < tokens.length && (tokens[index].type.equals("OPERATOR") && tokens[index].value.equals("+") || tokens[index].type.equals("OPERATOR") && tokens[index].value.equals("-"))) {
            index++;
            Node right = parseTerm(tokens, variables, index);
            left = new Node("OPERATION", left, right);
        }
        return left;
    }

    private static Node parseTerm(Token[] tokens, Map<String, Node> variables, int index) {
        Node left = parseFactor(tokens, variables, index);
        while (index < tokens.length && (tokens[index].type.equals("OPERATOR") && tokens[index].value.equals("*") || tokens[index].type.equals("OPERATOR") && tokens[index].value.equals("/"))) {
            index++;
            Node right = parseFactor(tokens, variables, index);
            left = new Node("OPERATION", left, right);
        }
        return left;
    }

    private static Node parseFactor(Token[] tokens, Map<String, Node> variables, int index) {
        if (tokens[index].type.equals("NUMBER")) {
            return new Node("NUMBER", Double.parseDouble(tokens[index].value));
        } else if (tokens[index].type.equals("VARIABLE")) {
            return variables.get(tokens[index].value);
        } else if (tokens[index].type.equals("PARENTHESIS")) {
            index++;
            Node expression = parseExpression(tokens, variables);
            index++; // Consume closing parenthesis
            return expression;
        }
        return null;
    }

    public static double evaluate(Node node) {
        if (node.type.equals("NUMBER")) {
            return node.value;
        } else if (node.type.equals("VARIABLE")) {
            // Replace with actual variable value
            return 0;
        } else if (node.type.equals("OPERATION")) {
            double leftValue = evaluate(node.left);
            double rightValue = evaluate(node.right);
            switch (node.left.type) {
                case "OPERATOR":
                    if (node.left.value.equals("+")) {
                        return leftValue + rightValue;
                    } else if (node.left.value.equals("-")) {
                        return leftValue - rightValue;
                    } else if (node.left.value.equals("*")) {
                        return leftValue * rightValue;
                    } else if (node.left.value.equals("/")) {
                        return leftValue / rightValue;
                    }
            }
            return 0; // Default
        }
        return 0; // Default
    }

    public static void main(String[] args) {
        String expression = "2 + 3 * 4";
        Token[] tokens = tokenize(expression);
        Node node = parse(tokens);
        System.out.println(evaluate(node));
    }
}
```