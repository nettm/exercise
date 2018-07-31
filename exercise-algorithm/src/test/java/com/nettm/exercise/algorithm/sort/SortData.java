package com.nettm.exercise.algorithm.sort;

import java.util.Arrays;

public class SortData {

    public static final int[] num1 = { 4, 3, 98, 12, 32, 44, 0, 32, 74 };

    public static final int[] num1_sort = { 0, 3, 4, 12, 32, 32, 44, 74, 98 };

    public static final int[] num2 = { 0, 3, 4, 12, 32, 32, 44, 74, 98 };

    public static final int[] num2_sort = { 0, 3, 4, 12, 32, 32, 44, 74, 98 };

    public static final int[] num3 = { 98, 74, 44, 32, 32, 12, 4, 3, 0 };

    public static final int[] num3_sort = { 0, 3, 4, 12, 32, 32, 44, 74, 98 };

    public static final int[] num4 = { 98, 74 };

    public static final int[] num4_sort = { 74, 98 };

    public static final int[] num5 = { 74 };

    public static final int[] num5_sort = { 74 };

    public static final int[] num6 = {};

    public static final int[] num6_sort = {};

    public static void printArrays(int[] args) {
        System.out.println(Arrays.toString(args));
    }
}
