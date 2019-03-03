package com.nettm.exercise.lombok;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.Wither;

/**
 * 描述
 *
 * @author mu.tian
 * @date 2019-03-03 14:18
 */
@Value
public class Cat {

    @Wither(AccessLevel.PUBLIC)
    String name;

    @NonFinal
    int age;

    long id;
}
