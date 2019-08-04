package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

/**
 * 描述
 *
 * @author mu.tian
 * @date 2019-07-09 16:54
 */
@UtilityClass
public class RandomGenerator extends BaseDataGenerator {

    public long generateLong(long ... vals) {
        int length = vals.length;
        int random = randomInt(0, length - 1);
        return vals[random];
    }

}
