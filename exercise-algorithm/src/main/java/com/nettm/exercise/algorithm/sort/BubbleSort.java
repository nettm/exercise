package com.nettm.exercise.algorithm.sort;

public class BubbleSort implements Sort {

	@Override
	public int[] sort(int[] args) {
		for (int i = args.length - 1; i >= 0; i--) {
			for (int k = (i - 1); k >= 0; k--) {
				if (args[i] < args[k]) {
					int a = args[k];
					args[k] = args[i];
					args[i] = a;
				}
			}
		}

		return args;
	}

}
