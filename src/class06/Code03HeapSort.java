package class06;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author yanghl
 * @Description: 堆排序
 * @date 2023/2/19 21:12
 */
public class Code03HeapSort {


    /**
     * 堆排序, 额外空间复杂度O(1)
     * @param arr
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2){
            return;
        }

        // O(N*logN) 设置成大根堆
//        for (int i = 0; i < arr.length; i++) {
//            heapInsert(arr, i + 1);
//        }

        // O(N)
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        // 每次都把堆顶位置与堆底位置互换，最大的就在堆底了，然后堆的大小减1
        // 再从 0 位置开始向下调整堆
        int heapSize = arr.length;
        while (heapSize > 0){
            swap(arr, --heapSize, 0);
            heapify(arr, 0, heapSize);
        }
    }

    /**
     * 刚刚在堆的最下面插入一个元素，需要向上调整堆
     * @param arr
     */
    public static void heapInsert(int[] arr, int heapSize){
        int curIndex = heapSize - 1;
        int parent = (curIndex - 1) / 2;
        while (parent >= 0 && arr[curIndex] > arr[parent]){
            swap(arr, curIndex, parent);
            curIndex = parent;
            parent = (curIndex - 1) / 2;
        }
    }

    /**
     * 在堆的 index 位置的值变小了，需要向下调整堆
     * @param arr
     * @param index
     * @param heapSize
     */
    public static void heapify(int[] arr, int index, int heapSize){
        int left  = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[index] > arr[largest] ? index : largest;
            if (largest == index){
                break;
            }
            swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {

        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(6);
        heap.add(8);
        heap.add(0);
        heap.add(2);
        heap.add(9);
        heap.add(1);

        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        heapSort(arr);
        printArray(arr);
    }
}
