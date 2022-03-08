package tests;

import exceptions.InvalidDomainException;
import exceptions.ParenthesisMismatchException;
import exceptions.UnknownFunctionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parser.ReversePolishNotationEvaluator;
import parser.ShuntingYard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EternityTests {

    @Test
    @DisplayName("Test combination of functions")
    public void testFunctionCombination() throws ParenthesisMismatchException, UnknownFunctionException, InvalidDomainException, Exception {
        String expression = "(sinh(0.5))+2^4*5.4+2-10";
        var result = new ReversePolishNotationEvaluator(new ShuntingYard(expression).parse()).evaluate();
        var actualResult = Math.sinh(0.5) + Math.pow(2, 4) * 5.4 + 2 - 10;
        assertEquals(actualResult, result, 0.00001);

        expression = "2 * 5^4 - (sinh(2)) + 200.5 - 1232";
        result = new ReversePolishNotationEvaluator(new ShuntingYard(expression).parse()).evaluate();
        actualResult = 2 * Math.pow(5, 4) - Math.sinh(2) + 200.5 - 1232;
        assertEquals(actualResult, result, 0.00001);

        expression = "((arccos(~0.4))+(sinh(0.4))+3*4*5)/2";
        result = new ReversePolishNotationEvaluator(new ShuntingYard(expression).parse()).evaluate();
        actualResult = (Math.acos(-0.4)+Math.sinh(0.4)+3*4*5)/2;
        assertEquals(actualResult,result,0.00001);

        expression = "(arccos(0.5))*10-4*3+2-10";
        result = new ReversePolishNotationEvaluator(new ShuntingYard(expression).parse()).evaluate();
        actualResult = Math.acos(0.5)*10-4*3+2-10;
        assertEquals(actualResult,result,0.00001);
    }
}
