package com.nettm.exercise.base.guava;

import com.google.common.base.Splitter;

public class SplitterDemo {

    private static final Splitter SPLITTER = Splitter.on("|").trimResults().omitEmptyStrings();

    public static void main(String[] args) {
        Iterable<String> iterable = SPLITTER.split("||aa|bb||cc");
        iterable.forEach(System.out::println);
    }
}
