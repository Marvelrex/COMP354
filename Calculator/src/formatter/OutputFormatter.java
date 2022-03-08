package formatter;

import exceptions.InvalidDomainException;
import functions.MathFunctions;
import utils.Utility;

public class OutputFormatter {

    /**
     * Format the output with specified accuracy
     * @param output Output to be formatted
     * @param accuracy Specified accuracy
     * @return Formatted output
     */
    public static double formatOutput(double output, int accuracy) throws InvalidDomainException {
        if (accuracy < 0 || accuracy > 20) {
            throw new InvalidDomainException("The accuracy is invalid. Accuracy should be between [0, 20].");
        }

        return Utility.round(output, accuracy);
    }

    public static void main(String[] args) throws InvalidDomainException {
        System.out.println(formatOutput(MathFunctions.sinh(2), 3));
        System.out.println(formatOutput(MathFunctions.sinh(2), 29));
    }
}
