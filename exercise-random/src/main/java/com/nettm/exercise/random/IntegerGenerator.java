package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IntegerGenerator extends BaseDataGenerator {

	protected int minValue = Integer.MIN_VALUE;

	protected int maxValue = Integer.MAX_VALUE;

	public int generate() {
		return generate(minValue, maxValue);
	}

	public int generate(int minValue, int maxValue) {
		return randomInt(minValue, maxValue);
	}

}
