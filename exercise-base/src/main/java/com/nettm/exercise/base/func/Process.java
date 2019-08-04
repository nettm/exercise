package com.nettm.exercise.base.func;

@FunctionalInterface
public interface Process<I, O> {

    O execute(I param);

    default void test() {
        System.out.println();
    }

}
