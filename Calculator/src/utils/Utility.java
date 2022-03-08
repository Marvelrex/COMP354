// Implemented and modified from https://unnikked.ga/the-shunting-yard-algorithm-36191ea795d9

package utils;

import parser.Lexer;

public final class Utility {

    /**
     * Check if expression is a number
     * @param str expression
     * @return If expression is number or not
     */
    public static boolean isNumber(String str) {
        return str.matches(Lexer.CONST);
    }

    /**
     * Simplify decimals to required digits
     *
     * @param inputNumber Double Number with lots of digits
     * @param digits      Number of digits
     * @return Double number with required digits
     */
    public static double round(double inputNumber, int digits) {
        int coefficient = 10;

        for (int i = digits; i > 1; i--) {
            coefficient *= 10;
        }

        inputNumber *= coefficient;
        long newInputNumber = (inputNumber % 1 > 0.5) ? (long) inputNumber + 1 : (long) inputNumber;

        return (double) newInputNumber / coefficient;
    }
}
