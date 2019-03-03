package com.nettm.exercise.lombok;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述
 *
 * @author mu.tian
 * @date 2019-03-03 14:21
 */
public class Bird {

    @Getter(lazy = true)
    private final String name = sName();

    @Setter
    int age;

    private String sName() {
        return "abc";
    }
}
