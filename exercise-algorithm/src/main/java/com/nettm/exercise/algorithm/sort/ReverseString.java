package com.nettm.exercise.algorithm.sort;

public class ReverseString {

    public static void main(String[] args) {
        System.out.println(reverse("abcdef"));
        System.out.println(reverse("a"));
        System.out.println(reverse(""));
        System.out.println(reverse("123456789"));
    }

    private static String reverse(String val) {
        char[] cs = val.toCharArray();
        int l = 0;
        int h = val.length() - 1;
        while (l < h) {
            char cl = val.charAt(l);
            char ch = val.charAt(h);
            cs[l] = ch;
            cs[h] = cl;
            l++;
            h--;
        }
        return new String(cs);
    }
}
