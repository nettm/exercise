package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringGenerator extends BaseDataGenerator {

	protected final static int MIN_LENGTH = 0;

	protected final static int MAX_LENGTH = 255;

	protected final static char[] DICTIONARY = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public String generate() {
		return generate(1, 100);
	}

	public String generate(int minLength, int maxLength) {
		int min = minLength;
		int max = maxLength;
		
		if (min == 0 && max == 0) {
			min = MIN_LENGTH;
			max = MAX_LENGTH;
		}
		
		int length = randomInt(minLength, maxLength);
		int k = 0;
		char s;
		StringBuilder buff = new StringBuilder(length);
		while (k < length) {
			s = DICTIONARY[RANDOM.nextInt(DICTIONARY.length)];
			buff.append(s);
			k++;
		}
		return buff.toString();
	}
}
