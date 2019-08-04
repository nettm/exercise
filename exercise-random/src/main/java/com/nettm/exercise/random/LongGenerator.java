package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class LongGenerator extends BaseDataGenerator {

	protected long minValue = Long.MIN_VALUE;

	protected long maxValue = Long.MAX_VALUE;

	public long generate() {
		return generate(minValue, maxValue);
	}

	public long generate(long minValue, long maxValue) {
		BigDecimal min = new BigDecimal(minValue);
		BigDecimal max = new BigDecimal(maxValue);
		BigDecimal a = max.subtract(min).add(new BigDecimal(1)).multiply(new BigDecimal(Math.random())).add(min);
		return a.longValue();
	}

}
