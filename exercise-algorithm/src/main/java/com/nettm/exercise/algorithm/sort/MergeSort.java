package com.nettm.exercise.algorithm.sort;

public class MergeSort implements Sort {

    @Override
    public int[] sort(int[] args) {
        int[] tmp = new int[args.length];
        // TODO
        return tmp;
    }
    
    private int[] sort(int[] a, int[] b) {
        int index = 0, i = 0, j = 0;
        int[] tmp = new int[a.length + b.length];
        for (; i < a.length && j < b.length;) {
            if (a[i] > b[j]) {
                tmp[index] = b[j];
                j++;
            } else {
                tmp[index] = a[i];
                i++;
            }
            index++;
        }

        return tmp;
    }

}
