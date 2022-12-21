package ru.otus.architect;

import static java.lang.Double.valueOf;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public final class QuadraticEquation
{
    private static final Double[] NO_ROOTS = new Double[0];

    private static final Double EPSILON = 0.000001;

    private QuadraticEquation() {}

    static Double[] solve(final Double a, final Double b, final Double c) {
        validateValue(a, "a");
        validateValue(b, "b");
        validateValue(c, "b");

        if (isEqualToZero(a)) {
            throw new IllegalArgumentException("a must be not null or 0");
        }

        Double[] roots = new Double[2];
        Double firstRoot;
        Double secondRoot;
        Double discriminant = b * b - 4 * a * c;

        if (isEqualToZero(discriminant)) {
            discriminant = 0d;
        }

        if (discriminant.compareTo(valueOf(0d)) < 0) {
            return NO_ROOTS;
        } else {
            firstRoot = (-b + sqrt(discriminant)) / (2 * a);
            secondRoot = (-b - sqrt(discriminant)) / (2 * a);
        }

        roots[0] = firstRoot;
        roots[1] = secondRoot;

        return roots;
    }

    private static boolean isEqualToZero(final Double value) {
        return abs(value) < EPSILON;
    }

    private static void validateValue(final Double value, final String name) {
        String coefficientName = name == null ? "coefficient" : name;

        if (value == null) {
            throw new IllegalArgumentException(String.format("%s must be not null\n", coefficientName));
        }
        if (value.isNaN()) {
            throw new IllegalArgumentException(String.format("%s must be not NaN\n", coefficientName));
        }
        if (value.isInfinite()) {
            throw new IllegalArgumentException(String.format("%s must be not infinite\n", coefficientName));
        }
    }
}
