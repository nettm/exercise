package com.nettm.exercise.algorithm.sort;

public class InsertSort implements Sort {

    @Override
    public int[] sort(int[] args) {
        for(int i = 1; i < args.length; i++) {
            int a = i;
            for(int k = i - 1; k >= 0; k--) {
                if (args[a] < args[k]) {
                    int tmp = args[k];
                    args[k] = args[a];
                    args[a] = tmp;
                    a--;
                } else {
                    continue;
                }
            }
        }
        
        return args;
    }

}
