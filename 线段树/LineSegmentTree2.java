/**
 * @author xdwang
 * @create 2020-12-05 15:53
 */
//sum数组初始索引为0或者为1不需要改动输入数组orgin，仅仅是rt的问题，详情可见方法build build2
//若sum索引以1开始，辅助数组空间刚好（第一为0），索引以0开始，空间多1（最后一为0）（不严谨）.详情见构造器SegmentTree
//本例中，让sum索引为1开始存储，这不影响外部的输入方式。

//至于orgin查询问题，java输入的数组索引0开始，但是如果要求认为输入数组最小索引为1，和java不同，
//为了查询时方便，可以把orgin全部后移一位，这样和java的索引就对上了。
//但是这样是丝毫没有必要的
//orgin索引问题仅影响build，而build是不需要暴露的方法
//add  update  query
//比如，update(int L, int R, int C, int l, int r, int rt);L R为更新的范围， l r为线段的范围，C改成的数，rt 树节点
//如果数组为{1,2,3,4,5,6,7,8}，我们想把第四个，第五个数变成0
//update(3,4,0,0,7,1);但是这个方法也可以这样update(4,5,0,1,8,1) ，L R l r范围不是索引，build后，仅有长度的概念，相对的概念
//不应该把L R l r局限的想象成索引 update(4,5,0,1,8,1) = update(103,104,0,100,107,1)也是相等的，毕竟方法里有重新计算L R等的
//重新定位
public class LineSegmentTree2 {
    public static class SegmentTree {
        private int maxn;
        private int[] arr;
        private int[] sum;
        private int[] change;
        private boolean[] update;
        private int[] add;

        public SegmentTree(int[] orgin) {
            if (orgin == null || orgin.length == 0) {
                System.out.println("数组为空，重新创建");
                arr = new int[]{0};
            }else{
                arr = orgin;
            }
                maxn = arr.length;
                int lev = log(maxn) + 1;
                int len = 1 << lev;
                sum = new int[len];
                change = new int[len];
                update = new boolean[len];
                add = new int[len];
                // build(0,maxn-1,0);
                build2(0, maxn - 1, 1); //rt = 1即使用索引从1开始的，节省一步加法运算

        }

        //以2为底N的对数，向上取整,N为正整数
        private int log(int N) {
            N = N - 1;
            int res = 0;
            while (N != 0) {
                N = N >> 1;
                res++;
            }
            return res;
        }

        //sum的索引从0开始用build
        private void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = l + ((r - l) >> 1);
            build(l, mid, (rt << 1) + 1);
            build(mid + 1, r, (rt + 1) << 1);
            pushUp(rt);
        }

        //sum的索引从1开始用build2
        private void build2(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = l + ((r - l) >> 1);
            build2(l, mid, rt << 1);
            build2(mid + 1, r, rt << 1 | 1);
            pushUp2(rt);
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1 | 1] + sum[(rt + 1) << 1];
        }

        private void pushUp2(int rt) {
            sum[rt] = sum[rt << 1 | 1] + sum[rt << 1];
        }

        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                update[rt] = false;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                sum[rt << 1] = ln * change[rt];
                sum[rt << 1 | 1] = rn * change[rt];
                add[rt << 1] = 0;
                add[rt << 1 | 1] = 0;
            }
            if (add[rt] != 0) {
                add[rt << 1] += add[rt];
                add[rt << 1 | 1] += add[rt];
                sum[rt << 1] += add[rt] * ln;
                sum[rt << 1 | 1] += add[rt] * rn;
                add[rt] = 0;
            }
        }

        public void add(int l, int r, int c) {
            if (l < 0 || r >= maxn) {
                System.out.println("检查索引范围");
                return;
            }
            add(l, r, c, 0, maxn - 1, 1);
        }

        private void add(int l, int r, int c, int L, int R, int rt) {
            if (l <= L && r >= R) {
                add[rt] += c;
                sum[rt] += c * (R - L + 1);
                return;
            }
            int mid = L + ((R - L) >> 1);
            pushDown(rt, mid - L+1, R - mid);
            if (l <= mid) {
                add(l, r, c, L, mid, rt << 1);
            }
            if (r > mid) {
                add(l, r, c, mid + 1, R, rt << 1 | 1);
            }
            pushUp2(rt);
        }

        public void update(int l, int r, int c) {
            if (l < 0 || r >= maxn) {
                System.out.println("检查索引范围");
                return;
            }
            update(l, r, c, 0, maxn - 1, 1);
        }

        private void update(int l, int r, int c, int L, int R, int rt) {
            if (l <= L && r >= R) {
                change[rt] = c;
                update[rt] = true;
                add[rt] = 0;
                sum[rt] = c * (R - L + 1);
                return;
            }
            int mid = L + ((R - L) >> 1);
            pushDown(rt, mid - L + 1, R - mid);
            if (l <= mid) {
                update(l, r, c, L, mid, rt << 1);
            }
            if (r > mid) {
                update(l, r, c, mid + 1, R, rt << 1 | 1);
            }
            pushUp2(rt);
        }

        public long query(int l, int r) {
            if (l < 0 || r >= maxn) {
                System.out.println("检查索引范围");
                return 0;
            }
            return query(l, r, 0, maxn - 1, 1);
        }

        private long query(int l, int r,int L, int R, int rt) {
            if (l <= L && r >= R) {
                return sum[rt];
            }
            int mid = L + ((R - L) >> 1);
            pushDown(rt, mid - L+1, R - mid);
            long ans = 0;
            if (l <= mid) {
                ans += query(l, r,  L, mid, rt << 1);
            }
            if (r > mid) {
                ans += query(l, r, mid + 1, R, rt << 1 | 1);
            }
            return ans;
        }

        public void printSum() {
            int line = 1;
            int cout = line;
            for (int i = 0; i < sum.length; i++) {
                System.out.print(sum[i] + "\t");
                line--;
                if (line == 0) {
                    System.out.println();
                    cout = cout << 1;
                    line = cout;
                }
            }
        }

        public void printSum2() {
            int line = 1;
            int cout = line;
            for (int i = 1; i < sum.length; i++) {
                System.out.print(sum[i] + "\t");
                line--;
                if (line == 0) {
                    System.out.println();
                    cout = cout << 1;
                    line = cout;
                }
            }
        }
    }

    public static class Right {
        public int[] arr;

        public Right(int[] orgin) {
            if (orgin.length == 0) {
                arr = new int[]{0};
            } else {
                arr = orgin;
            }
        }

        public void info() {
            System.out.print("[");
            for (int i = 0; i < arr.length - 1; i++) {
                System.out.print(arr[i] + ",");
            }
            System.out.print(arr[arr.length - 1] + "]");
            System.out.println();
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

        public static int[] generateRandomArray(int maxSize, int maxValue) {
            int[] arr = new int[(int) ((maxSize+1) * Math.random())];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * maxValue);
            }
            return arr;
        }

        public static void test() {
            int len = 100;
            int max = 1000;
            int testTimes = 5000;
            int addOrUpdateTimes = 1000;
            int queryTimes = 500;
            for (int i = 0; i < testTimes; i++) {
                int[] orgin = generateRandomArray(len, max);
                SegmentTree seg = new SegmentTree(orgin);
                Right rig = new Right(orgin);
                for (int j = 0; j < addOrUpdateTimes; j++) {
                    int num1 = (int) (Math.random()*orgin.length);
                    int num2 = (int) (Math.random()*orgin.length);
                    int l = Math.min(num1,num2);
                    int r = Math.max(num1,num2);
                    int c =(int)(Math.random()*max);
                    if (Math.random()<0.5){
                        seg.add(l,r,c);
                        rig.add(l,r,c);
                    }else{
                        seg.update(l,r,c);
                        rig.update(l,r,c);
                    }
                }
                for (int k = 0; k < queryTimes; k++) {
                    int num1 = (int) (Math.random()*orgin.length);
                    int num2 = (int) (Math.random()*orgin.length);
                    int l = Math.min(num1,num2);
                    int r = Math.max(num1,num2);
                    long ans1 = seg.query(l,r);
                    long ans2 = rig.query(l,r);
                    if (ans1!=ans2){
                        System.out.println("false");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Right.test();
    }
}
