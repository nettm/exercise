package com.nettm.exercise.base.guava;

import com.google.common.base.Joiner;

public class JoinerDemo {

    private static final Joiner JOINER = Joiner.on("|").skipNulls();

    public static void main(String[] args) {
        String a = JOINER.join("a", "b", null, "c");
        System.out.println(a);
    }

}
