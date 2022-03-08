// Implemented and modified from https://unnikked.ga/the-shunting-yard-algorithm-36191ea795d9

package parser;

import exceptions.ParenthesisMismatchException;
import exceptions.UnknownFunctionException;

import static utils.Utility.isNumber;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

public class ShuntingYard {
    private StringBuilder output;
    private Stack<String> stack;
    private String expression;

    private Lexer lexer;

    private HashMap<String, int[]> proprerties = new HashMap<String, int[]>(5);
    private HashSet<String> functions = new HashSet<String>();

    private enum Operator {
        ADD(3, Operator.LEFT),
        SUBTRACT(3, Operator.LEFT),
        UNARY_MINUS(3, Operator.LEFT),
        MULTIPLY(4, Operator.LEFT),
        DIVIDE(4, Operator.LEFT),
        POWER(5, Operator.RIGHT);
        static final int LEFT = 0;
        static final int RIGHT = 1;
        final int precedence;
        final int associativity;
        Operator(int p, int a) { precedence = p; associativity = a; }
    }

    private Map<String, Operator> ops = new HashMap<String, Operator>() {{
        put("+", Operator.ADD);
        put("-", Operator.SUBTRACT);
        put("~", Operator.UNARY_MINUS);
        put("*", Operator.MULTIPLY);
        put("/", Operator.DIVIDE);
        put("^", Operator.POWER);
    }};

    public ShuntingYard(String expression) {
        this.output = new StringBuilder();
        this.stack = new Stack<String>();
        this.expression = expression;
        lexer = new Lexer(new ByteArrayInputStream(this.expression.getBytes()));
    }

    /**
     * The algorithm in detail
     *
     *     While there are tokens to be read:
     *        Read a token.
     *            If the token is a number, then add it to the output queue.
     *            If the token is a function token, then push it onto the stack.
     *            If the token is a function argument separator (e.g., a comma):
     *                Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue. If no left parentheses are encountered, either the separator was misplaced or parentheses were mismatched.
     *            If the token is an operator, o1, then:
     *                while there is an operator token, o2, at the top of the stack, and
     *                        either o1 is left-associative and its precedence is equal to that of o2,
     *                        or o1 has precedence less than that of o2,
     *                    pop o2 off the stack, onto the output queue;
     *                push o1 onto the stack.
     *            If the token is a left parenthesis, then push it onto the stack.
     *            If the token is a right parenthesis:
     *                 Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue.
     *                 Pop the left parenthesis from the stack, but not onto the output queue.
     *                 If the token at the top of the stack is a function token, pop it onto the output queue.
     *                 If the stack runs out without finding a left parenthesis, then there are mismatched parentheses.
     *
     *    When there are no more tokens to read:
     *        While there are still operator tokens in the stack:
     *            If the operator token on the top of the stack is a parenthesis, then there are mismatched parentheses.
     *            Pop the operator onto the output queue.
     *     Exit.
     *
     * 	@return Parsed operation in postfix notation
     */
    public String parse() throws ParenthesisMismatchException, UnknownFunctionException {
        int id;
        String token;

        while((id = lexer.nextSymbol()) != Lexer.EOF ) {
            token = lexer.stringify(id);
            if (isNumber(token)) {
                output.append(token).append(' ');
            } else if (isFunction(token)) {
                stack.push(token);
            } else if (isFunctionSeparator(token)) {
                while(stack.peek().charAt(0) != '(') {
                    output.append(stack.pop()).append(' ');
                }
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && isOperator(stack.peek()) && isHigherPrecedence(token)) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(token);
            } else if (token.charAt(0) == '(') {
                stack.push(token);
            } else if (token.charAt(0) == ')') {
                try {
                    while (!stack.isEmpty() && stack.peek().charAt(0) != '(') {
                        output.append(stack.pop()).append(' ');
                    }
                } catch (Exception e) {
                    throw new ParenthesisMismatchException("Mismatched parentheses! Are you missing a parenthesis?");
                }

                if (stack.isEmpty())
                    throw new ParenthesisMismatchException("Mismatched parentheses! Do you have too many parentheses?");
                stack.pop();
            } else {
                throw new UnknownFunctionException("Unknown function: \"" + token + "\"");
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().charAt(0) == ')' || stack.peek().charAt(0) == '(') {
                throw new ParenthesisMismatchException("Mismatched parentheses! Are you missing a parenthesis?");
            }

            output.append(stack.pop()).append(' ');
        }

        return output.toString();
    }

    private boolean isHigherPrecedence(String token) {
        return isLeftAssociative(token) && precedence(token) <= precedence(stack.peek()) || isRightAssociative(token) && precedence(token) < precedence(stack.peek());
    }

    private int precedence(String token) {
        return (ops.get(token)!= null) ? ops.get(token).precedence : -1;
    }

    private boolean isOperator(String token) {
        return ops.containsKey(token);
    }

    private boolean isLeftAssociative(String token) {
        return ops.get(token)!= null && ops.get(token).associativity == Operator.LEFT;
    }

    private boolean isRightAssociative(String token) {
        return ops.get(token)!= null && ops.get(token).associativity == Operator.RIGHT;
    }

    {
        functions.add("e");
        functions.add("pi");
        functions.add("sinh");
        functions.add("exp");
        functions.add("arccos");
        functions.add("sd");
        functions.add("gamma");
        functions.add("mad");
        functions.add("log");
    }

    private boolean isFunction(String token) {
        return functions.contains(token);
    }

    private boolean isFunctionSeparator(String token) { return token.equals(","); }

    public static void main(String[] args) {
        String expression = "3.4+4*2/(1-5)^2^3";
        expression = "5 + 7 / 1 * 4 + 2 - 7 * 8 * 7 / 1";
//        expression ="4+5*(5+2)";
//        expression ="2^3";
//        System.out.println(new ShuntingYard(expression).parse());
    }
}
