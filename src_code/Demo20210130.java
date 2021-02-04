package com.xdwang;

/**
 * @author xdwang
 * @create 2021-01-30 14:42
 */
public class Demo20210130 {
    //13、调整数组顺序使奇数位于偶数前面
    public void reOrderArray1(int[] array) {
        int[] array2 = array.clone();
        int count = 0;
        for (int i = 0; i < array2.length; i++) {
            if ((array2[i] & 1) == 1) array[count++] = array2[i];
        }
        for (int i = 0; i < array2.length; i++) {
            if ((array2[i] & 1) == 0) array[count++] = array2[i];
        }
    }

    public void reOrderArray2(int[] array) {
        if (array == null || array.length == 0) return;
        int[] array2 = new int[array.length];
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) == 1) {
                array[count1++] = array[i];
            } else {
                array2[count2++] = array[i];
            }
        }
        for (int i = 0; i < count2; i++) {
            array[count1 + i] = array2[i];
        }
    }

    public void reOrderArray3(int[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) == 1) {
                for (int j = i; j > count; j--) {
                    int temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
                count++;
            }
        }
    }
}