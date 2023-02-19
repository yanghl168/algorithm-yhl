package class06;

/**
 * @author yanghl
 * @Description: 大根堆
 * @date 2023/2/16 22:50
 */
public class Code02Heap {
    public static class MyMaxHeap {
        private int[] heap;
        private int heapSize;
        private int limit;

        public MyMaxHeap(int limit) {
            this.limit = limit;
            heap = new int[limit];
            heapSize = 0;
        }


        public static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        /**
         * 新加进来了一个元素在堆底，需要向上调整堆
         *
         * @param arr
         * @param index
         */
        public static void heapInsert(int[] arr, int index) {
            int parentIndex = (index - 1) / 2;
            // 若比父结点大，继续往上浮
            while (arr[index] > arr[parentIndex]) {
                swap(arr, parentIndex, index);
                index = parentIndex;
                parentIndex = (index - 1) / 2;
            }
        }

        /**
         * 从 index 位置开始向下调整堆
         *
         * @param arr
         * @param index
         */
        public static void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                // 找出 index以及它的左右孩子中最大的元素
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                largest = arr[index] > arr[largest] ? index : largest;
                if (index == largest) {
                    break;
                }
                swap(arr, index, largest);
                index = largest;
                left = index * 2 + 1;

            }
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull(){
            return heapSize == limit;
        }

        public void push(int value){
            if (heapSize == limit){
                throw new RuntimeException("Heap is full !");
            }
            // heapSize 是堆的大小(长度)的值, 会比当前的 index 索引大 1
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        public int pop(){
            if (heapSize == 0){
                throw new RuntimeException("Heap is null !");
            }
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }
    }


    public static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }

    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
