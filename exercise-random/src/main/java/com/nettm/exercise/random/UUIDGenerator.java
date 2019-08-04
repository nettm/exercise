package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UUIDGenerator extends BaseDataGenerator {

	public String generate() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
