package ru.otus.architect;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Решение квадратного уравнения a * x^2 + b * x + c = 0")
public class QuadraticEquationTest {

	@DisplayName("Для a = 1, b = 0, c = 1 не должно быть корней")
	@Test
	public void noRootsTest() {
		Double a = 1d;
		Double b = 0d;
		Double c = 1d;

		Double[] actual = QuadraticEquation.solve(a, b, c);

		assertEquals(0, actual.length);
	}

	@DisplayName("Для a = 1, b = 0, c = -1 должно быть два корня кратности 1 (1, -1)")
	@Test
	public void twoRootsTest() {
		Double a = 1d;
		Double b = 0d;
		Double c = -1d;

		Double[] actual = QuadraticEquation.solve(a, b, c);

		assertEquals(2, actual.length);
		if (isPositive(actual[0])) {
			assertEquals(valueOf(1d), actual[0]);
			assertEquals(valueOf(-1d), actual[1]);
		} else {
			assertEquals(valueOf(-1d), actual[0]);
			assertEquals(valueOf(1d), actual[1]);
		}
	}

	@DisplayName("Для a = 1, b = 2, c = 1 должен быть один корень кратности 2 (-1)")
	@Test
	public void oneRootsTest() {
		Double a = 1d;
		Double b = 2d;
		Double c = 1d;

		Double[] actual = QuadraticEquation.solve(a, b, c);

		assertEquals(2, actual.length);
		assertEquals(valueOf(-1d), actual[0]);
		assertEquals(actual[0], actual[1]);
	}

	@DisplayName("Для a = 0 и любых b и c должно выбрасываться исключение IllegalArgumentException")
	@Test
	public void illegalAValueTest() {
		Random random = new Random();
		Double a = 0d;
		Double b = random.nextDouble();
		Double c = random.nextDouble();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> QuadraticEquation.solve(a, b, c));

		assertEquals("a must be not null or 0", exception.getMessage());
	}

	@DisplayName("Для a = 0.0001, b = 0.001, c = 0.002 дискриминант должен быть эквивалентен нулю и должен быть один корень кратности 2 (-5)")
	@Test
	public void oneRootWhenDiscriminantEqualZeroTest() {
		Double a = 0.0001d;
		Double b = 0.001d;
		Double c = 0.002d;

		Double[] actual = QuadraticEquation.solve(a, b, c);

		assertEquals(2, actual.length);
		assertEquals(valueOf(-5d), actual[0]);
		assertEquals(actual[0], actual[1]);
	}

	@DisplayName("Для любого коэффициента равного NaN или бесконечности должно выбрасываться исключение IllegalArgumentException")
	@ParameterizedTest
	@MethodSource("generateCoefficientValues")
	public void illegalCoefficientTest(final Double a, final Double b, final Double c) {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> QuadraticEquation.solve(a, b, c));
		assert (exception.getMessage().contains("must be not"));
	}

	private boolean isPositive(Double value) {
		return value.compareTo(valueOf(0d)) > 0 ? true : false;
	}

	private static Stream<Arguments> generateCoefficientValues() {
		Random random = new Random();
		Double[] aValues = {random.nextDouble(), NaN, NEGATIVE_INFINITY, POSITIVE_INFINITY};
		Double[] bValues = {random.nextDouble(), NaN, NEGATIVE_INFINITY, POSITIVE_INFINITY};
		Double[] cValues = {NaN, NEGATIVE_INFINITY, POSITIVE_INFINITY};

		return Arrays.stream(aValues)
				.flatMap(a -> Arrays.stream(bValues)
						.flatMap(b -> Arrays.stream(cValues)
								.map(c -> Arguments.of(a, b, c))
						)
				);
	}
}
