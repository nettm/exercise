package com.nettm.exercise.lombok;

import lombok.experimental.UtilityClass;

/**
 * 描述
 *
 * @author mu.tian
 * @date 2019-03-03 14:41
 */
@UtilityClass
public class UtilityClassExample {

    private final int CONSTANT = 5;

    public int addSomething(int in) {
        return in + CONSTANT;
    }

    public static void main(String[] args) {
        int a = UtilityClassExample.addSomething(3);
        System.out.println(a);
    }

}
