package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BinaryGenerator extends BaseDataGenerator {

	protected int minLength = 0;

	protected int maxLength = 1024;

	public byte[] generate() {
		return generate(minLength, maxLength);
	}

	public byte[] generate(int minLength, int maxLength) {
		int length = randomInt(minLength, maxLength);
		byte[] bytes = new byte[length];
		RANDOM.nextBytes(bytes);
		return bytes;
	}

}
