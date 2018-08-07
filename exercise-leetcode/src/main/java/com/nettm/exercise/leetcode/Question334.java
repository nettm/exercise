package com.nettm.exercise.leetcode;

public class Question334 {

    public static void main(String[] args) {
        String s = "hello";
        System.out.println(reverseString(s));
    }

    public static String reverseString(String s) {
        if (s.length() <= 1) {
            return s;
        }

        char[] cs = s.toCharArray();
        int pos = cs.length / 2;
        for (int i = 0; i < pos; i++) {
            char tmp = cs[i];
            cs[i] = cs[cs.length - 1 - i];
            cs[cs.length - 1 - i] = tmp;
        }

        return new String(cs);
    }

}
