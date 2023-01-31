package class04;

/**
 * @author yanghl
 * @Description: 归并排序
 * @date 2023/1/31 21:13
 */
public class Code01MergeSort {
    // 递归实现
    public static void mergeSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int L, int R){
        if (L == R){
            return ;
        }
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    public static void merge(int[] arr, int L, int mid, int R){
        int[] ret = new int[R - L + 1];
        int p1 = L, p2 = mid + 1, i = 0;
        while (p1 <= mid && p2 <= R){
            ret[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid){
            ret[i++] = arr[p1++];
        }
        while (p2 <= R){
            ret[i++] = arr[p2++];
        }
        for (int j = 0; j < ret.length; j++) {
            arr[L] = ret[j];
            L++;
        }
    }


    // 非递归实现
    public static void mergeSort02(int [] arr){
        int N = arr.length;
        if (N < 2){
            return;
        }
        // 步长
        int stepSize = 1;
        while (stepSize < N){
            // 当前左组的第一个位置
            int L = 0;
            while (L < N){
                if (stepSize >= N - L){
                    break;
                }
                int mid = L + stepSize - 1;
                int R = Math.min(mid + stepSize, N -1);
                merge(arr, L, mid, R);
                L = R + 1;
            }
            // 防止溢出
            if (stepSize > N / 2){
                break;
            }
            stepSize <<= 1;
        }
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
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort(arr1);
            mergeSort02(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
