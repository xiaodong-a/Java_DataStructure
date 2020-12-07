//根 数据结构
//第一版，第一次实现，细节处理不好，废弃
//后写一简易版   排序下面的 HeapSort
import java.util.ArrayList;
import java.util.Comparator;
//index 0 处不存储
//这个搞得很不好，导致处理数组用到了复制，后面想想怎么解决，第一次先实现吧
//应该把 根 和 根排序分开好了，不然就直接传入的index 0 不参与。不太行
//细节处理太差，空数组，空指针，index
/**
 * @author xdwang
 * @create 2020-12-07 21:32
 */

public class Heap01 {
    public static class MyHead<T> {
        private ArrayList<T> heap;//index 0 处不存储
        private int heapSize;
        private Comparator<? super T> comparator;

        public MyHead(Comparator<? super T> comparator) {
            this.comparator = comparator;
            heap = new ArrayList<>();
            heapSize = 0;
        }

        public MyHead(Comparator<? super T> comparator, ArrayList<T> list) {
            this.comparator = comparator;
            heap = list;
            heapSize = heap.size()-1;
//            for (int i = heapSize; i >=1 ; i--) {
//                T t = heap.get(i-1);
//                heap.set(i,t);
//            }
            for (int i = heapSize; i >= 1; i--) {
                heapify(i);
            }
        }
//        public ArrayList<T> heapSort(){
        public void heapSort() {
            while (heapSize > 0) {
                pop();
            }
//            ArrayList<T> ans = new ArrayList<T>();
//            for (int i = 1; i <= heapSize; i++) {
//                ans.add(heap.get(i));
//            }
//            heap = new ArrayList<T>();
//            return ans;

        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public void push(T value) {
            if (heap.size()==0) heap.add(value);  //为了0不存储，改的好蠢
            heap.add(value);//index 0 处不存储
            heapInsert(++heapSize);
        }

        private void heapInsert(int index) {
            while (index > 1 && comparator.compare(heap.get(index), heap.get(index >> 1)) > 0) {
                swap(index, index >> 1);
                index = index >> 1;
            }
        }

        private void swap(int i, int j) {
            T ti = heap.get(i);
            T tj = heap.get(j);
            heap.set(i, tj);
            heap.set(j, ti);
        }

        public T pop() {
            if (!isEmpty()) {
                T ans = heap.get(1);
                swap(1, heapSize--);
                heapify(1);
                return ans;
            } else {
                return null;
            }
        }

        public void heapify(int index) {
            int left = index << 1;
            while (left <= heapSize) {
                int largest = left < heapSize && comparator.compare(heap.get(left), heap.get(left + 1)) < 0 ? left + 1 : left;
                if (comparator.compare(heap.get(largest), heap.get(index)) <= 0) {
                    break;
                }
                swap(index, largest);
                index = largest;
                left = index << 1;
            }
        }
    }


    public static void main(String[] args) {
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
  //      ArrayList<Integer> list = new ArrayList<>();
        MyHead<Integer> list = new MyHead<>(com);
        list.push(1);
        list.push(16);
        list.push(25);
        list.push(6);
        list.push(545);
        list.push(365);
        list.push(21);
        list.push(0);
        list.push(69);
  //      MyHead<Integer> integerMyHead = new MyHead<>(com, list);
  //      integerMyHead.heapSort();
  //      list.heapSort();
        while (!list.isEmpty()){

            System.out.print(list.pop()+ " ");
        }
    }
//0,6,16,21,25,69,365,545,
}
