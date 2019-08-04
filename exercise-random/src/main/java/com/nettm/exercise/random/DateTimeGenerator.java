package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateTimeGenerator extends BaseDataGenerator {

	protected BigDecimal minValue = new BigDecimal("-2209017600000");
	protected BigDecimal maxValue = new BigDecimal(System.currentTimeMillis());

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Date generate() {
		return generate(minValue.longValue(), maxValue.longValue());
	}

	public Date generate(String minValue, String maxValue) {
		Date min = null;
		Date max = null;
		try {
			min = DATE_FORMAT.parse(minValue);
			max = DATE_FORMAT.parse(maxValue);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return generate(min.getTime(), max.getTime());
	}

	public Date generate(Date minValue, Date maxValue) {
		return generate(minValue.getTime(), maxValue.getTime());
	}

	public Date generate(long minValue, long maxValue) {
		BigDecimal min = new BigDecimal(minValue);
		BigDecimal max = new BigDecimal(maxValue);

		BigDecimal retValue = null;
		BigDecimal length = max.subtract(min);
		BigDecimal factor = new BigDecimal(RANDOM.nextDouble());
		retValue = length.multiply(factor).add(min);

		return new Date(retValue.toBigInteger().longValue());
	}
}
