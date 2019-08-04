package com.nettm.exercise.random;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumGenerator extends BaseDataGenerator {

    public <T extends Enum> T generate(Class<T> clazz) {
        T[] ts = clazz.getEnumConstants();
        if (ts.length == 0) {
            return null;
        }

        int random = randomInt(0, ts.length - 1);
        return ts[random];
    }

    public <T extends Enum> T generate(T... ts) {
        int random = randomInt(0, ts.length - 1);
        return ts[random];
    }

}
