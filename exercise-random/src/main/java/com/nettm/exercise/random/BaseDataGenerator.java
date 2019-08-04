package com.nettm.exercise.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BaseDataGenerator {

	protected static final Random RANDOM = ThreadLocalRandom.current();

	protected static int randomInt(int min, int max) {
		return (int) (min + (max - min + 1) * Math.random());
	}

}
