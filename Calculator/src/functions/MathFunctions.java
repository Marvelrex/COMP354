package functions;

import exceptions.InvalidArgumentException;
import exceptions.InvalidDomainException;

import java.util.ArrayList;

public class MathFunctions {

    // Might have to rework the accuracy, or even compute the value using the series.
    public static final double EULER = euler(1);
    public static final double PI = pi();
    // Precision for power and findNthRoot functions
    public static double THRESHOLD = 0.0000001;

    /**
     * Add two numbers
     *
     * @param a First argument of addition
     * @param b Second argument of addition
     * @return Sum of the two provided arguments
     */
    public static double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtract two numbers
     *
     * @param a First argument of subtraction
     * @param b Second argument of subtraction
     * @return Difference of the two provided arguments
     */
    public static double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Multiply two numbers
     *
     * @param a First argument of multiplication
     * @param b Second argument of multiplication
     * @return Product of the two provided arguments
     */
    public static double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Divide two numbers
     *
     * @param a Dividend
     * @param b Divisor
     * @return Quotient of the two provided arguments
     */
    public static double divide(double a, double b) {
        return a / b;
    }

    /**
     * use mathematical to define the euler number e
     *
     * @param euler number e
     * @return the value of e
     */
    public static double euler(double euler) {
        double epsilon = 1e-14;
        double e = 1.0;
        double factorial = 1.0;
        int n = 1;

        // e = 1 + (1/1!) + (1/2!) + (1/3!) + … + (1/n!)
        for (e = 1; 1 / factorial >= epsilon; n++) {

            factorial *= n;
            e += 1 / factorial;
        }
        return e;
    }

    /**
     * use mathematical to define the the value of pi
     *
     * @return the value of pi
     */
    public static double pi() {

        // Leibniz formula for pi: pi/4 = 1- (1/3) +(1/5)-(1/7)+(1/9)....
        int i = 1, sign = 1;
        double term, PI = 0;

        do {
            term = sign * 1.0 / i;
            PI += term;
            i += 2;
            sign = -sign;
        } while (Math.abs(term) > 1.0E-8);

        PI = 4 * PI;

        //return the pi number with 7 decimal which is 3.1415926
        return Math.floor(PI * 10000000d) / 10000000d;
    }

    /**
     * Compute exponentiation by continuously performing square root of base and multiplication of them to get an approximate result
     * Ex: x^0.5 = sqrt(x), x^0.25 = sqrt(sqrt(x)), x^0.125 = sqrt(sqrt(sqrt(x)))
     * -> x^0.625 = x^0.5 * x^0.125 = sqrt(x) * sqrt(sqrt(sqrt(x)))
     * References:
     * 1. https://stackoverflow.com/questions/39114317/power-function-to-find-the-power-where-exponent-is-in-decimal-and-less-than-1
     * 2. https://stackoverflow.com/questions/3518973/floating-point-exponentiation-without-power-function/7710097#7710097
     *
     * @param base     Base of power
     * @param exponent Exponent of power
     * @return Computed value of the power function (Infinity if out of bound)
     */
    public static double power(double base, double exponent) {
        double result = 1.0;
        // if exponent is an integer
        if (exponent % 1 == 0) {
            result = simplePower(base, (int) exponent);
        }
        // exponent < 0
        else if (exponent < 0) {
            result = 1 / power(base, -exponent);
        }
        // exponent >= 1
        else if (exponent >= 1) {
            double temp = power(base, exponent / 2);
            result = temp * temp;
        }
        // 0 < exponent < 1
        else {
            double high = 1.0;
            double mid = high / 2;
            double low = 0;

            double sqrt = findNthRoot(base, 2);
            result = sqrt;

            while (abs(mid - exponent) > THRESHOLD) {
                sqrt = findNthRoot(sqrt, 2);
                if (mid <= exponent) {
                    low = mid;
                    result *= sqrt;
                } else {
                    high = mid;
                    result *= (1 / sqrt);
                }
                mid = (low + high) / 2;
            }
        }
        // if exponent is 0, return 1
        return result;
    }

    /**
     * Computes the absolute value of a given double
     *
     * @param val Double as argument
     * @return Absolute value of val
     */
    public static double abs(double val) {
        // If val is negative, return -val. Else return original value
        return (val < 0) ? -val : val;
    }

    /**
     * Computes the hyperbolic sine of a given argument
     *
     * @param hyperbolicAngle Argument of a hyperbolic trigonometric function
     * @return Computer value of the hyperbolic sine
     */
    public static double sinh(double hyperbolicAngle) {
        // Approximation through the exponential function
        // (e^x - e^(-x)) / 2
        return (eulerExponential(hyperbolicAngle) - eulerExponential(-hyperbolicAngle)) / 2;
    }

    /**
     * calculateSD() will execute the steps to calculate the standard deviation.
     *
     * @return standard deviation.
     */
    public static double standardDeviation(ArrayList<Double> valuesList) {
        double SD, mean, summation = 0;
        int listSize;

        // listSize represents the number of values in the set.
        listSize = valuesList.size();

        // Calculating the mean
        mean = calculateMean(valuesList);

        // Does the summation of the square of each value minus the mean
        for (Double value : valuesList) {
            summation += square(value - mean);
        }

        // Square root of summation divided by listSize
        SD = sqrt(summation / listSize);

        return SD;
    }

    /**
     * Calculates the mean.
     *
     * @param list List of values provided by user.
     * @return mean of the list of values.
     */
    public static double calculateMean(ArrayList<Double> list) {
        double mean, sum = 0;
        int quantity = list.size();

        for (Double value : list) {
            sum += value;
        }

        mean = sum / quantity;

        return mean;
    }

    /**
     * Finds square root of a number.
     *
     * @param originalNumber the original number passed into function.
     * @return square root.
     */
    public static double sqrt(double originalNumber) {
        double squareRoot = originalNumber / 2;
        double temp;

        if (originalNumber == 0) {
            return 0;
        }

        do {
            temp = squareRoot;
            squareRoot = (temp + (originalNumber / temp)) / 2;
        } while ((temp - squareRoot) != 0);

        return squareRoot;
    }

    /**
     * Finds square of a number.
     *
     * @param num Indicates the number that was passed into the function.
     * @return squared number.
     */
    public static double square(double num) {
        return num * num;
    }

    /**
     * Compute the power with an integer exponent
     *
     * @param base     base
     * @param exponent exponent
     * @return exponentiation
     */
    private static double simplePower(double base, int exponent) {
        double result = 1.0;
        // exponent < 0
        if (exponent < 0) {
            result = 1 / simplePower(base, -exponent);
        }
        // exponent >= 0
        else {
            for (int i = 0; i < exponent; i++) {
                result *= base;
            }
        }
        return result;
    }

    /**
     * Calculate Arccos result
     *
     * @param radian represents the radians of angle in range [-1.0,1.0]
     * @return The double value of the angle.
     */
    public static double arcCos(double radian) throws InvalidDomainException {
        if (radian > 1.0 || radian < -1.0) {
            throw new InvalidDomainException("The domain of arccos is [-1.0, 1.0].");
        }

        double angle = pi() / 2;
        double left;
        double right;
        try {
            int accuracy = 8;
            if (radian == 1.0) {
                return 0;
            } else if (radian == -1.0) {
                return pi();
            }

            for (int i = 0; i < accuracy; i++) {
                left = (factorial(2 * i)) / (power(2, 2 * i) * power(factorial(i), 2));
                right = power(radian, (2 * i) + 1) / (2 * i + 1);
                angle -= (left * right);

            }
        } catch (IllegalArgumentException e) {
            System.out.println("The type of argument must be double");
        }
        return angle;
    }

    /**
     * Use mean function to calculate mean
     * For each loop to perform subtraction for all data points (data - mean) and sum those up
     * Divide sum by mean
     *
     * @param range Array of numbers to be used in MAD
     * @return result of MAD
     */
    public static double mad(ArrayList<Double> range) {
        double mean = MathFunctions.calculateMean(range), sumOfDevs = 0, mad;

        for (Double value : range) {
            sumOfDevs += abs(value - mean);
        }

        mad = sumOfDevs / range.size();

        return mad;
    }

    /**
     * To calculate the factorial of the integer.
     * ex:5!=5*4*3*2*1=120
     *
     * @param inputNumber int Number
     * @return Integer value
     */
    public static int factorial(int inputNumber) {
        if (inputNumber == 0) {
            return 1;
        }

        return (inputNumber * factorial(inputNumber - 1));
    }

    /**
     * Helper function to calculate sin(x)
     * Use Taylor series sin(x) = sum((-1)^n*x^(2n+1)/(2n+1)!)
     *
     * @param radian Angle in radians
     * @return double result of sin
     */
    public static double sin(double radian) {
        double value = 0;

        for (int n = 0; n < 100; n++) {
            value += power(-1.0, n) * power(radian, 2 * n + 1) / factorial(2 * n + 1);
        }

        return value;
    }

    /**
     * Gamma Function of a positive integer input
     *
     * @param x positive integer
     * @return int gamma result of the positive integer x
     */
    public static int intGamma(int x) throws InvalidDomainException {
        if (x < 0) {
            throw new InvalidDomainException("The domain of the gamma function is [0.0, pos_infinity[.");
        }

        int result = 1;

        for (int ii = 1; ii < x; ii++) {

            result = result * ii;
        }

        return result;
    }

    /**
     * Gamma Function of a positive double input
     * reference: https:// rosettacode.org/wiki/Gamma_function
     *
     * @param x positive double
     * @return double gamma result of the positive double x
     */
    public static double gamma(double x) throws InvalidDomainException {
        if (x < 0) {
            throw new InvalidDomainException("The domain of the gamma function is ]0.0, pos_infinity[.");
        }

        if (x == (int) x) {
            return intGamma((int) x);
        }

        if (x < 0.5) {
            return PI / (sin(PI * x) * gamma(1 - x));
        }

        double[] p = {0.99999999999980993, 676.5203681218851, -1259.1392167224028,
                771.32342877765313, -176.61502916214059, 12.507343278686905,
                -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7};

        x -= 1;

        int g = 7;
        double a = p[0];
        double exp = x + g + 0.5;

        for (int ii = 1; ii < p.length; ii++) {

            a += p[ii] / (x + ii);
        }

        return sqrt(2 * PI) * power(exp, x + 0.5) * eulerExponential(-exp) * a;
    }

    /**
     * Compute nth root of a double using Newton's method
     * References:
     * 1. https://www.geeksforgeeks.org/n-th-root-number/
     * 2. https://www.geeksforgeeks.org/program-for-newton-raphson-method/
     *
     * @param d a double
     * @param n an integer
     * @return nth root of d
     */
    public static double findNthRoot(double d, int n) {
        // initially guessing a random number between 0 and 9
        double guess = Math.random() % 10;
        double newGuess = (guess + d / guess) /n;
        while (abs(guess - newGuess) > THRESHOLD) {
            guess = newGuess;
            newGuess = (guess + d / guess) / n;
        }

        return newGuess;
    }

    /**
     * Function to easily compute e^x
     * @param exponent exponent
     * @return e^x
     */
    public static double eulerExponential(double exponent) {
        double a = 1;
        double b = euler(1);
        return a * power(b, exponent);
    }

    /**
     * Function to easily compute 10^x
     * @param exponent exponent
     * @return 10^x
     */
    public static double tenExponential(double exponent) {
        double a = 1;
        double b = 10;
        return a * power(b, exponent);
    }

    /**
     * Function to easily compute pi^x
     * @param exponent exponent
     * @return pi^x
     */
    public static double piExponential(double exponent) {
        double a = 1;
        double b = pi();
        return a * power(b, exponent);
    }

    /**
     * Computes the result of a base raised to a given exponent
     * @param base Base of logarithm
     * @param argument Argument of logarithm
     * @return Computed value of the logarithmic function
     */
    public static double log(double base, double argument) throws InvalidDomainException {
        if (base <= 1) {
            throw new InvalidDomainException("The domain of the base of the log function is [2, pos_infinity[.");
        }

        if (argument <= 0) {
            throw new InvalidDomainException("The domain of the log function is [0, pos_infinity[.");
        }

        return Math.log(argument) / Math.log(base);
    }

    /**
     * log_2(x)
     * @param argument Argument of log
     * @return Exponent
     * @throws InvalidDomainException Incorrect domain for log
     */
    public static double binaryLog(double argument) throws InvalidDomainException {
        double b = 2;
        return log(b,argument);
    }

    /**
     * ln(x)
     * @param argument Argument of log
     * @return Exponent
     * @throws InvalidDomainException Incorrect domain for log
     */
    public static double naturalLog(double argument) throws InvalidDomainException {
        double b = euler(1);
        return log(b, argument);
    }

    /**
     * log_10(x)
     * @param argument Argument of log
     * @return Exponent
     * @throws InvalidDomainException Incorrect domain for log
     */
    public static double commonLog(double argument) throws InvalidDomainException {
        double b = 10;
        return log(b, argument);
    }
}
