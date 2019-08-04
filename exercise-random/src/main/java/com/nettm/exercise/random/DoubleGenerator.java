package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class DoubleGenerator extends BaseDataGenerator {

	protected double minValue = Double.MIN_VALUE;

	protected double maxValue = Double.MAX_VALUE;

	public double generate() {
		return generate(minValue, maxValue);
	}

	public double generate(double minValue, double maxValue) {
		double i = minValue;
		double a = maxValue;
		return (i + (a - i) * Math.random());
	}

	public double generate(int minPrecision, int maxPrecision) {
		double d = generate();

		BigDecimal b = new BigDecimal(d);
		int precision = randomInt(minPrecision, maxPrecision);
		return b.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public double generate(double minValue, double maxValue, int minPrecision, int maxPrecision) {
		double i = minValue;
		double a = maxValue;
		double d = i + (a - i) * Math.random();

		BigDecimal b = new BigDecimal(d);
		int precision = randomInt(minPrecision, maxPrecision);
		return b.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
