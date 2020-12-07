//感觉视频讲解有漏洞，此版废弃，最新版本见LineSegmentTree2
//1、数组长度不需要为2的n次方
//2、sum数组初始索引为0或者为1不需要改动输入数组，仅仅是rt的问题
//3、4N耗费空间
/**
 * @author xdwang
 * @create 2020-12-03 21:02
 */
//自己手写实现下，并添加笔记感想
//时间复杂度是变成了logn，但是感觉费了很多空间，而且原始数组空间没排上用场
//虽然sum 最底层的叶子相当于原数组
//此类不提供错误检查，使用此类时，务必再外层进行空指针或越界检查
public class LineSegmentTree {

    public static class SegmentTree {
        //orgin[] 为输入的数组，arr[]为和orgin[]内容几乎一致的数组
        //仅有的区别为orgin从索引0开始  arr从索引1开始，arr[i] = orgin[i-1]
        //从后面可以看到，arr仅build处有用，可以直接用索引转换，使用原数组，不需要再复制一遍
        private int[] arr;//索引从1开始数组
        private int[] sum;//线段树区域和
        private int[] lazy;//add标记
        private int[] change; //更新的值     int[] 默认为0
        private boolean[] update;//是否进行更新 默认为false

        public SegmentTree(int orgin[]) {
            int MAXN = orgin.length + 1; ////数组原始长度加1
            arr = new int[MAXN];
            for (int i = 1; i < arr.length; i++) {
                arr[i] = orgin[i - 1];
            }
            sum = new int[MAXN << 2]; //原视频用4N感觉浪费空间
            lazy = new int[MAXN << 2];//(2exp([log(N-1)]+1)) 这样应该好点
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
        }

        //构造sum数组
        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        //向上返回左右子树的求和信息
        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        //此方法和add方法最好再经过一次包装，调用另一个函数递归，这样就不会
        //出现rt,l,r等令人迷惑的参数
        //L R为更新的范围， l r为线段的范围，初始为1  数组长度
        public void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && R >= r) { //其实直接写等于也行，正确操作下不可能出去L < l,R > r的现象
                update[rt] = true;
                change[rt] = C;
                sum[rt] = C * (r - l + 1);
                lazy[rt] = 0;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid + 1 - l);
            if (mid < L) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            } else if (R <= mid) {
                update(L, R, C, l, mid, rt << 1);
            } else {
                update(L, mid, C, l, mid, rt << 1);
                update(mid + 1, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public void add(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                lazy[rt] += C;
                sum[rt] += C * (r - l + 1);
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid + 1 - l);
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid + 1 - l);
            long ans = 0;

            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

        //下发add或者update信息, n 表示左子树或右子树的个数，左右子树个数应相等
        //不知道为什么视频中要写两个
        //add 的下发并汇总sum是为query做铺垫，否则query就要耗费很多时间
        //但是update存在的话，必须下发处理，否则就失去了顺序
        private void pushDown(int rt, int n) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                update[rt] = false;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                sum[rt << 1] = change[rt] * n;
                sum[rt << 1 | 1] = change[rt] * n;
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
            }
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * n;
                sum[rt << 1 | 1] += lazy[rt] * n;
                lazy[rt] = 0;
            }
        }
        public void info() {
            System.out.print("[");
            for (int i = 1; i < sum.length - 1; i++) {
                System.out.print(sum[i]+",");
            }
            System.out.print(sum[sum.length-1]+"]");
            System.out.println();
        }
    }

    public static class Right{
        public int[] arr;
        public Right(int[] orgin){
            arr = new int[orgin.length+1];
            for (int i = 0; i < orgin.length; i++) {
                arr[i+1] = orgin[i];
            }
        }

        public void info() {
            System.out.print("[");
            for (int i = 1; i < arr.length - 1; i++) {
                System.out.print(arr[i]+",");
            }
            System.out.print(arr[arr.length-1]+"]");
            System.out.println();
        }

        public void update(int L, int R, int C){
            for(int i=L;i<=R;i++){
                arr[i] = C;
            }
        }
        public void add(int L,int R,int C){
            for(int i=L;i<=R;i++){
                arr[i] += C;
            }
        }
        public long query(int L,int R){
            long ans = 0;
            for(int i=L;i<=R;i++){
                ans += arr[i];
            }
            return ans;
        }
        public static int[] generateRandomArray(int maxSize,int maxValue){
            int[] arr = new int[(int) ((maxSize+1)*Math.random())] ;
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int) (Math.random()*(maxValue+1)) -  (int) (Math.random()*maxValue);
            }
            return  arr;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{1,2,3,4,5};
        SegmentTree st1 = new SegmentTree(array);

        st1.build(1,5,1);
        st1.info();
    }
}
