package com.nettm.exercise.lombok;

import lombok.Builder;
import lombok.Synchronized;

/**
 * 描述
 *
 * @author mu.tian
 * @date 2019-03-03 14:20
 */
@Builder
public class Dog {

    private String name;

    private int age;

    @Synchronized
    public static void main(String[] args) {
        System.out.println("ok");
    }

}
