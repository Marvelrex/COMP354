// Implemented and modified from https://unnikked.ga/the-shunting-yard-algorithm-36191ea795d9

package parser;

import static utils.Utility.isNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import exceptions.InvalidArgumentException;
import exceptions.InvalidDomainException;
import exceptions.UnknownFunctionException;
import functions.MathFunctions;

public class ReversePolishNotationEvaluator {

    private LinkedList<String> stack = new LinkedList<String>();
    private String bytecode;

    // Helper to map an enum value to the string token associated with the operation
    // TODO Add the rest of the operations.
    private enum Operations {
        PLUS("+"), MINUS("-"), UNARY_MINUS("~"), MULTIPLY("*"), DIVIDE("/"), POWER("^"), EULER("e"), PI("pi"), SINH("sinh"), ARCCOS("arccos"), SD("sd"), GAMMA("gamma"), MAD("mad"), LOG("log");

        private String operationName;

        Operations(String op) {
            this.operationName = op;
        }

        @Override
        public String toString() {
            return operationName;
        }
    }

    public ReversePolishNotationEvaluator(String expression) {
        this.bytecode = expression;
    }

    public HashMap<String, Double> context = new HashMap<>();

    // TODO Add the rest of the operations.
    public Double evaluate() throws InvalidDomainException, UnknownFunctionException, Exception {
        String code[] = bytecode.split(" ");
        String instruction;
        double firstOperand, secondOperand;
        int instructionPointer = 0; //instruction pointer
        int numOfArgs = 0;

        while (instructionPointer < code.length && instructionPointer > -1) {
            instruction = code[instructionPointer++];

            // TODO Make things look nicer because this is disgusting
            if (isNumber(instruction)) {
                stack.push(instruction);
                numOfArgs++;
            } else if (instruction.equals(Operations.EULER.toString())) {
                stack.push(Double.toString(MathFunctions.EULER));
                numOfArgs++;
            } else if (instruction.equals(Operations.PI.toString())) {
                stack.push(Double.toString(MathFunctions.PI));
                numOfArgs++;
            } else if (instruction.equals(Operations.SINH.toString())) {
                if (numOfArgs != 1) {
                    throw new InvalidArgumentException("The sinh function takes 1 argument.");
                }
                firstOperand = cast(stack.pop());
                numOfArgs = 0;
                stack.push(String.valueOf(MathFunctions.sinh(firstOperand)));
            } else if (instruction.equals(Operations.ARCCOS.toString())) {
                if (numOfArgs != 1) {
                    throw new InvalidArgumentException("The arccos function takes 1 argument.");
                }

                firstOperand = cast(stack.pop());
                numOfArgs = 0;
                stack.push(String.valueOf(MathFunctions.arcCos(firstOperand)));
            } else if (instruction.equals(Operations.SD.toString())) {
                if (numOfArgs == 0) {
                    throw new InvalidArgumentException("The standard deviation functions needs at least 1 argument.");
                }

                var operands = new ArrayList<Double>();

                for(int i = 0; i < numOfArgs; i++) {
                    operands.add(cast(stack.pop()));
                }

                numOfArgs = 0;
                stack.push(String.valueOf(MathFunctions.standardDeviation(operands)));
            } else if (instruction.equals(Operations.MAD.toString())) {
                if (numOfArgs == 0) {
                    throw new InvalidArgumentException("The standard deviation functions needs at least 1 argument.");
                }

                var operands = new ArrayList<Double>();

                for(int i = 0; i < numOfArgs; i++) {
                    operands.add(cast(stack.pop()));
                }

                numOfArgs = 0;
                stack.push(String.valueOf(MathFunctions.mad(operands)));
                numOfArgs++;
            } else if (instruction.equals(Operations.POWER.toString())) {
                secondOperand = cast(stack.pop());
                firstOperand = cast(stack.pop());
                numOfArgs = 0;

                if (firstOperand == 10) {
                    stack.push(String.valueOf(MathFunctions.tenExponential(secondOperand)));
                } else if (firstOperand == MathFunctions.EULER) {
                    stack.push(String.valueOf(MathFunctions.eulerExponential(secondOperand)));
                } else if (firstOperand == MathFunctions.PI) {
                    stack.push(String.valueOf(MathFunctions.piExponential(secondOperand)));
                } else {
                    stack.push(String.valueOf(MathFunctions.power(firstOperand, secondOperand)));
                }
            } else if (instruction.equals(Operations.GAMMA.toString())) {
                firstOperand = cast(stack.pop());
                numOfArgs = 0;
                stack.push(String.valueOf(MathFunctions.gamma(firstOperand)));
            } else if (instruction.equals(Operations.LOG.toString())) {
                if (numOfArgs != 2) {
                    throw new InvalidArgumentException("The log function needs 2 arguments.");
                }

                secondOperand = cast(stack.pop());
                firstOperand = cast(stack.pop());
                numOfArgs = 0;

                if (firstOperand == 10) {
                    stack.push(String.valueOf(MathFunctions.commonLog(secondOperand)));
                } else if (firstOperand == 2) {
                    stack.push(String.valueOf(MathFunctions.binaryLog(secondOperand)));
                } else if (firstOperand == MathFunctions.EULER) {
                stack.push(String.valueOf(MathFunctions.naturalLog(secondOperand)));
                }  else {
                    stack.push(String.valueOf(MathFunctions.log(firstOperand, secondOperand)));
                }
            } else if (instruction.equals(Operations.DIVIDE.toString())) {
                secondOperand = cast(stack.pop());
                firstOperand = cast(stack.pop());
                numOfArgs = 0;
                stack.push(String.valueOf(MathFunctions.divide(firstOperand, secondOperand)));
//                numOfArgs++;
            } else if (instruction.equals(Operations.MULTIPLY.toString())) {
                secondOperand = cast(stack.pop());
                firstOperand = cast(stack.pop());
                numOfArgs = 0;
                stack.push(String.valueOf(MathFunctions.multiply(firstOperand, secondOperand)));
            } else if (instruction.equals(Operations.PLUS.toString())) {
                secondOperand = cast(stack.pop());
                firstOperand = cast(stack.pop());
                numOfArgs = 0;
                stack.push(String.valueOf(MathFunctions.add(firstOperand, secondOperand)));
            } else if (instruction.equals(Operations.MINUS.toString())) {
                secondOperand = cast(stack.pop());

                if(stack.peek() != null && isNumber(stack.peek())) {
                    firstOperand = cast(stack.pop());
                    stack.push(String.valueOf(MathFunctions.subtract(firstOperand, secondOperand)));
                }
//                else { // unary minus
//                    stack.push(String.valueOf(-secondOperand));
//                }
                numOfArgs = 0;
//                numOfArgs++;
            } else if (instruction.equals(Operations.UNARY_MINUS.toString())) {
                firstOperand = cast(stack.pop());
                stack.push(String.valueOf(-firstOperand));
            } else {
                throw new UnknownFunctionException("Unknown function: [" + instruction + "]");
            }
        }

        return cast(stack.pop());
    }

    private Double cast(String token) {
        return Double.parseDouble(token);
    }
}
