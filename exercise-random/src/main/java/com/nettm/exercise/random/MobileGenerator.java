package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class MobileGenerator extends BaseDataGenerator {

	protected final static String[] PREFIX = { "130", "131", "132", "134", "135", "136", "137", "138", "139", "155",
			"156", "157", "158", "186", "188" };

	public String generate() {
		int length = PREFIX.length;
		StringBuffer s = new StringBuffer();
		Random random = new Random();
		s.append(PREFIX[random.nextInt(length)]);
		for (int i = 0; i < 8; i++) {
			s.append(random.nextInt(10));
		}
		return s.toString();
	}
}
