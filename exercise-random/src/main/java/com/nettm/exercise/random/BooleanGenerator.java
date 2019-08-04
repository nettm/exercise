package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BooleanGenerator extends BaseDataGenerator {

	public boolean generate() {
		int a = randomInt(0, 100);
		return a % 2 == 0 ? true : false;
	}
}
