package com.nettm.exercise.base.unsafe;

import sun.misc.Unsafe;

public class UnsafeDemo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Unsafe unsafe = OffHeapArray.getUnsafe();
        System.out.println(unsafe);

        unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe);
    }

}
