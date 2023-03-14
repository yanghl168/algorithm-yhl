package class08;

import java.util.Arrays;

/**
 * @author yanghl
 * @Description: 基数排序
 * @date 2023/3/7 21:46
 */
public class Code04RadixSort {
    public static void radixSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    /**
     * 获取数组的最大值的十进制位数
     * @param arr
     * @return
     */
    public static int maxBits(int[] arr){
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
        }
        int ret = 0;
        while (max != 0){
            max  /= 10;
            ret++;
        }
        return ret;
    }

    /**
     * 把数组arr的L...R位置排好序
     * @param arr
     * @param L
     * @param R
     * @param digit 数组的最大值十进制位数
     */
    public static void radixSort(int[] arr, int L, int R, int digit){
        // 0...9
        final int radix = 10;
        int i = 0, j = 0;
        int[] help = new int[R - L + 1];
        // 有多少位就进出几次
        for (int d = 1; d <= digit; d++) {
            // 每次需要新生成的计数辅助数组
            int[] counts = new int[radix];
            // 10个空间
            // count[0] 当前位(d位)<=0的数字有多少个
            // count[1] 当前位(d位)<=1的数字有多少个
            // count[2] 当前位(d位)<=2的数字有多少个
            // count[i] 当前位(d位)<=i的数字有多少个
            for (i = L; i <= R ; i++) {
                // 找到 arr[i] 第 d 位的数字
                j = getDigit(arr[i], d);
                counts[j]++;
            }
            for (i = 1; i < radix; i++) {
                counts[i] = counts[i] + counts[i - 1];
            }

            for (i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                help[counts[j] - 1] = arr[i];
                counts[j]--;
            }
            for (i = L, j = 0; i <= R ; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    /**
     * 获取 x 的十进制的从右往左数的第d个位置的数
     * @param x
     * @param d
     * @return
     */
    public static int getDigit(int x, int d){
        return (x / (int)(Math.pow(10, (d - 1)))) % 10;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
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
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);

    }
}
