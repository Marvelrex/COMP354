package tests;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.InvalidDomainException;
import functions.MathFunctions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import java.util.ArrayList;
import java.util.*;

import java.lang.Math;

class MathFunctionTests {

    @Test
    @DisplayName("Testing accuracy for MAD")
    void madAccuracyTest() throws Exception {
        ArrayList<Double> a = new ArrayList<>(Arrays.asList(3., 8., 10., 17., 24., 27.));
        ArrayList<Double> b = new ArrayList<>(Arrays.asList(0., 0., 0., 0., 0.));
        ArrayList<Double> c = new ArrayList<>(Arrays.asList(1.1, 1.5, 99.4, 14.6));

        assertEquals(7.833333333333332, MathFunctions.mad(a), 0.00001);
        assertEquals(0, MathFunctions.mad(b), 0.00001);
        assertEquals(35.125, MathFunctions.mad(c), 0.00001);
    }

    @Test
    @DisplayName("Testing accuracy for arccos")
    void arccosAccuracyTest() throws InvalidDomainException {
        assertEquals(Math.acos(0.5), MathFunctions.arcCos(0.5), 0.00001);
        assertEquals(Math.acos(0), MathFunctions.arcCos(0), 0.00001);
        assertEquals(Math.acos(1), MathFunctions.arcCos(1), 0.00001);

        assertThrows(InvalidDomainException.class, () -> {
            MathFunctions.arcCos(-2);
        });

        assertThrows(InvalidDomainException.class, () -> {
            MathFunctions.arcCos(2);
        });
    }

    @Test
    @DisplayName("Testing accuracy for power")
    void powerAccuracyTest() {
        assertEquals(Math.pow(3, 5), MathFunctions.power(3,5),0.00001);
        assertEquals(Math.pow(3.5, 5), MathFunctions.power(3.5,5), 0.00001);
        assertEquals(Math.pow(0, 5), MathFunctions.power(0,5), 0.00001);
        assertEquals(Math.pow(4, 0.5), MathFunctions.power(4, 0.5), 0.00001);
        assertEquals(Math.pow(2, 1.11), MathFunctions.power(2, 1.11), 0.00001);
        assertEquals(Math.pow(2, -4), MathFunctions.power(2, -4), 0.00001);
    }

    @Test
    @DisplayName("Testing accuracy for factorial")
    void factorialAccuracyTest() {
        assertEquals(1, MathFunctions.factorial(1));
        assertEquals(2, MathFunctions.factorial(2));
        assertEquals(6, MathFunctions.factorial(3));
        assertEquals(3628800, MathFunctions.factorial(10));
    }

    @Test
    @DisplayName("Testing accuracy for sinh")
    public void testSinHAccuracy() {
        assertEquals(Math.sinh(0), MathFunctions.sinh(0), "Trivial result");
        assertEquals(Math.sinh(1), MathFunctions.sinh(1), 0.0000001);
        assertEquals(Math.sinh(5), MathFunctions.sinh(5), 0.0000001);
        assertEquals(Math.sinh(-10), MathFunctions.sinh(-10), 0.0000001);
    }

    @Test
    @DisplayName("Testing accuracy for standard deviation")
    public void standardDeviationAccuracyTest() {
        ArrayList<Double> valuesList = new ArrayList<>();
        valuesList.add(10.0);
        valuesList.add(12.0);
        valuesList.add(23.0);
        valuesList.add(31.0);
        assertEquals(8.5146931829632, MathFunctions.standardDeviation(valuesList));
    }

    @Test
    @DisplayName("Testing accuracy of gamma")
    public void gammaAccuracyTest() throws InvalidDomainException {
        assertEquals(1.329340388179137020474, MathFunctions.gamma(2.5), 0.000001);
        assertEquals(24, MathFunctions.gamma(5), 0.000001);

        assertThrows(InvalidDomainException.class, () -> {
            MathFunctions.gamma(-1);
        });
    }

    @Test
    @DisplayName("Testing accuracy for log")
    public void logAccuracyTest() throws InvalidDomainException {
        assertEquals(Math.log10(1), MathFunctions.log(10, 1));
        assertEquals(Math.log10(100), MathFunctions.log(10, 100), 0.000001);
        assertEquals(Math.log(1), MathFunctions.log(Math.E, 1));
        assertEquals(Math.log(Math.pow(Math.E, 2)), MathFunctions.log(Math.E, Math.pow(Math.E, 2)), 0.000001);
        assertEquals(1.46497352, MathFunctions.log(3, 5), 0.000001);

        assertThrows(InvalidDomainException.class, () -> {
            MathFunctions.log(10, -1);
        });
    }
}