package com.xdwang;

import org.junit.Test;

/**
 * @author xdwang
 * @create 2021-01-29 16:55
 */
public class Demo20210129 {
    //7、斐波那契数列  第0项为0，第1项是1

    //方法一 ： 递归
    public int Fibonacci1(int n) {
        if (n <= 1) return n;
        return Fibonacci1(n - 1) + Fibonacci1(n - 2);
    }

    //方法二：动态规划
    public int Fibonacci2(int n) {
        if (n <= 1) return n;
        int a = 0;
        int b = 1;
        while (n > 0) {
            b = a + b;
            a = b - a;
            n--;
        }
        return a;
    }

    //方法三：矩阵乘法
    public int Fibonacci3(int n) {
        if (n <= 1) return n;
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = PowMatrix(base, n - 1);
        return res[0][0];
    }

    public int[][] PowMatrix(int[][] base, int n) {
        int[][] temp = base;
        int[][] res = new int[base.length][base[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        for (; n > 0; n >>= 1) {
            if ((n & 1) != 0) res = MuliMatrix(res, temp);
            temp = MuliMatrix(temp, temp);
        }
        return res;
    }

    public int[][] MuliMatrix(int[][] num1, int[][] num2) {
        int rows = num1.length;
        int cols = num2[0].length;
        int[][] res = new int[rows][cols];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                for (int k = 0; k < num1[0].length; k++) {
                    res[i][j] += num1[i][k] * num2[k][j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Demo20210129 demo = new Demo20210129();
        int NUM = 50;
        int[] arr = new int[NUM];
        long startTime;
        long endTime;

        System.out.println("数据为:");
        for (int i = 0; i < NUM; i++) {
            arr[i] = (int) (Math.random() * NUM);
            System.out.print(arr[i] + "\t");
        }
        System.out.println();

        System.out.println("方法一结果为：");
        for (int i = 0; i < NUM; i++) {
            System.out.print(demo.Fibonacci4(arr[i]) + "\t");
        }
        System.out.println();

        startTime = System.currentTimeMillis();
        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < NUM; i++) {
                demo.Fibonacci4(arr[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("一千次循环耗时为：" + (endTime - startTime) + "ms");

        System.out.println("方法二结果为：");
        for (int i = 0; i < NUM; i++) {
            System.out.print(demo.Fibonacci2(arr[i]) + "\t");
        }
        System.out.println();

        startTime = System.currentTimeMillis();
        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < NUM; i++) {
                demo.Fibonacci2(arr[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("一千次循环耗时为：" + (endTime - startTime) + "ms");

        System.out.println("方法三结果为：");
        for (int i = 0; i < NUM; i++) {
            System.out.print(demo.Fibonacci3(arr[i]) + "\t");
        }
        System.out.println();

        startTime = System.currentTimeMillis();
        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < NUM; i++) {
                demo.Fibonacci3(arr[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("一千次循环耗时为：" + (endTime - startTime) + "ms");
    }

    //方法四：通项公式
    public int Fibonacci4(int n) {
        if (n <= 1) return n;
        double s5 = Math.sqrt(5.0);
        return (int) ((powNum((s5 + 1) / 2, n) - powNum((1 - s5) / 2, n)) / s5 + 0.5);
    }

    public double powNum(double num, int e) {
        double res = 1;
        for (; e > 0; e >>= 1) {
            if ((e & 1) == 1) res = res * num;
            num = num * num;
        }
        return res;
    }

    @Test
    public void test1() {
        int NUM = 100000;
        long startTime;
        long endTime;
        System.out.println(Fibonacci2(NUM));
        startTime = System.currentTimeMillis();
        for (int j = 0; j < 10000; j++) {
            Fibonacci4(NUM);
        }
        endTime = System.currentTimeMillis();
        System.out.println("一千次循环耗时为：" + (endTime - startTime) + "ms");
    }

    // 8、跳台阶
    public int JumpFloor(int target) {
        if (target <= 1) return target;
        int a = 1;
        int b = 1;
        for (; target > 0; target--) {
            b = a + b;
            a = b - a;
        }
        return a;
    }


    //9、变态跳台阶
    /*
第一次跳1 第一次跳2 第一次跳3 .... 第一次跳n
f(n) = 1 + f(1) + f(2) + ... + f(n-1)
f(n-1) = 1 + f(1) + f(2) + ... + f(n-2)
f(n) = f(n-1)*2
f(1) = 1
f(n) = 2^(n-1)
*/
    public int JumpFloorII(int target) {
        if (target <= 0) return 0;
        return 1 << (--target);
    }

    //10、矩形覆盖
    public int rectCover(int target) {
        if (target <= 0) return 0;
        int a = 1;
        int b = 1;
        for (; target > 0; target--) {
            b = a + b;
            a = b - a;
        }
        return a;
    }

    // 11、二进制中1的个数
    // 方法一使用无符号右移
    public int NumberOf11(int n) {
        int res = 0;
        while (n != 0) {
            res += n & 1;
            n >>>= 1;
        }
        return res;
    }

    //方法二：
    public int NumberOf12(int n) {
        int res = 0;
        while (n != 0) {
            n = ((~n + 1) ^ n) & n;
            res++;
        }
        return res;
    }

    //方法三：
    public int NumberOf13(int n) {
        int res = 0;
        while (n != 0) {
            n = (n - 1) & n;
            res++;
        }
        return res;
    }

    //方法四：有符号的右移，当时不知道无符号右移
    public int NumberOf14(int n) {
        int res = 0;
        if (n >= 0) {
            while (n != 0) {
                res += (n & 1);
                n = n >> 1;
            }
            return res;
        } else {
            n = (-n) - 1;
            while (n != 0) {
                res += (n & 1);
                n = n >> 1;
            }
            return 32 - res;
        }
    }

    //方法五：转成字符串
    public int NumberOf15(int n) {
        String number = Integer.toBinaryString(n);
        int count = 0;
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == '1') count++;
        }
        return count;
    }

    @Test
    public void Test2() {
        int NUM = 5000;
        int[] arr = new int[NUM];
        int CIRCU = 100000;
        long startTime;
        long endTime;

        System.out.println("数据为:");
        for (int i = 0; i < NUM; i++) {
            arr[i] = (int) (Math.random() * NUM - Math.random() * NUM);
            //   System.out.print(arr[i] + "\t");
        }
        System.out.println();
//1
        startTime = System.currentTimeMillis();
        for (int j = 0; j < CIRCU; j++) {
            for (int i = 0; i < NUM; i++) {
                NumberOf11(arr[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("无符号右移一千次循环耗时为：" + (endTime - startTime) + "ms");
//2
        startTime = System.currentTimeMillis();
        for (int j = 0; j < CIRCU; j++) {
            for (int i = 0; i < NUM; i++) {
                NumberOf12(arr[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("瞎搞的异或一千次循环耗时为：" + (endTime - startTime) + "ms");
//3
        startTime = System.currentTimeMillis();
        for (int j = 0; j < CIRCU; j++) {
            for (int i = 0; i < NUM; i++) {
                NumberOf13(arr[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("正常与做法一千次循环耗时为：" + (endTime - startTime) + "ms");
//4
        startTime = System.currentTimeMillis();
        for (int j = 0; j < CIRCU; j++) {
            for (int i = 0; i < NUM; i++) {
                NumberOf14(arr[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("有符号右移一千次循环耗时为：" + (endTime - startTime) + "ms");
//5
        startTime = System.currentTimeMillis();
        for (int j = 0; j < CIRCU; j++) {
            for (int i = 0; i < NUM; i++) {
                NumberOf15(arr[i]);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("字符串做法一千次循环耗时为：" + (endTime - startTime) + "ms");
    }

    //12、数值的整数次方
    public double Power1(double base, int exponent) {
        if (base == 0) return 0;
        if (exponent == 0) return 1;
        //     int e = exponent > 0 ? exponent : -exponent;
        if (exponent < 0) {
            exponent = -exponent;
            base = 1 / base;
        }
        double res = 1;
        for (; exponent > 0; exponent >>= 1) {
            if ((exponent & 1) == 1) res = res * base;
            base = base * base;
        }
        return res;
    }

    public double Power2(double base, int exponent) {
        if (exponent == 0) return 1;
        if (base == 0) return 0;
        if (exponent == 1) return base;
        if (exponent > 0) {
            if ((exponent & 1) == 1) {
                return Power2(base, (exponent + 1) / 2) * Power2(base, (exponent - 1) / 2);
            } else {
                double res = Power2(base, exponent / 2);
                return res * res;
            }
        } else {
            exponent = -exponent;
            if ((exponent & 1) == 1) {
                return 1 / (Power2(base, (exponent + 1) / 2) * Power2(base, (exponent - 1) / 2));
            } else {
                double res = Power2(base, exponent / 2);
                return 1 / (res * res);
            }
        }
    }

    public double Power2_m(double base, int exponent) {
        if (exponent == 0) return 1;
        if (base == 0) return 0;
        if (exponent == 1) return base;
        if (exponent < 0) {
            exponent = -exponent;
            base = 1 / base;
        }
        if ((exponent & 1) == 1) {
            double res = Power2_m(base, exponent / 2);
            return res * res * base;
        } else {
            double res = Power2_m(base, exponent / 2);
            return res * res;
        }
    }

    //方法三
    public double Power3(double base, int exponent) {
        if (base == 0) return 1;
        if (exponent == 0) return 1;
        if (exponent < 0) {
            exponent = -exponent;
            base = 1 / base;
        }
        return q_Power(base, exponent);
    }

    public double q_Power(double base, int exponent) {
        if (exponent == 0) return 1;
        double res = q_Power(base, exponent / 2);
        if ((exponent & 1) == 1) {
            return res * res * base;
        } else {
            return res * res;
        }
    }

    @Test
    public void Test3() {
        int NUM = 1000;
        double[] arr = new double[NUM];
        int CIRCU = 10000;
        long startTime;
        long endTime;

        System.out.println("数据为:");
        for (int i = 0; i < NUM; i++) {
            arr[i] = (Math.random() * NUM - Math.random() * NUM);
        }
        System.out.println();


        startTime = System.currentTimeMillis();
        for (int j = 0; j < CIRCU; j++) {
            for (int i = 0; i < NUM; i++) {
                Power2_m(arr[i], 5132);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("一千次循环耗时为：" + (endTime - startTime) + "ms");


        startTime = System.currentTimeMillis();
        for (int j = 0; j < CIRCU; j++) {
            for (int i = 0; i < NUM; i++) {
                Power3(arr[i], 5132);
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("一千次循环耗时为：" + (endTime - startTime) + "ms");
    }
}
