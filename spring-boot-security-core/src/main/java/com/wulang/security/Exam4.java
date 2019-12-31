package com.wulang.security;

import java.util.Arrays;

/**
 * @author wulang
 * @create 2019/12/8 0008/10:07
 */
public class Exam4 {
    public Exam4() {
    }

    public static void main(String[] args) {
        int i = 1;
        String str = "hello";
        Integer num = 200;
        int[] arr = new int[]{1, 2, 3, 4, 5};
        MyData my = new MyData();
        change(i, str, num, arr, my);
        System.out.println("i = " + i);
        System.out.println("str = " + str);
        System.out.println("num = " + num);
        System.out.println("arr = " + Arrays.toString(arr));
        System.out.println("my.a = " + my.a);
    }

    public static void change(int j, String s, Integer n, int[] a, MyData m) {
        ++j;
        s = s + "world";
        n = n + 1;
        int var10002 = a[0]++;
        ++m.a;
    }
}
