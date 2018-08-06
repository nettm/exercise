package com.nettm.exercise.leetcode;

public class Question35 {

    public static void main(String[] args) {
        int[] nums = { 1, 3, 5, 6 };
        int target = 2;
        searchInsert(nums, target);
    }

    public static int searchInsert(int[] nums, int target) {
        int mid = nums.length / 2;
        return compare(nums, mid, target);
    }

    private static int compare(int[] nums, int mid, int target) {
        if (mid == 0) {
            return 0;
        }

        if (mid >= nums.length) {
            return nums.length;
        }

        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] > target) {
            mid = mid / 2;
            return compare(nums, mid, target);
        } else {
            mid = mid / 2 + mid;
            return compare(nums, mid, target);
        }
    }

}
