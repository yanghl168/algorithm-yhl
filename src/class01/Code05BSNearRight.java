package class01;

import java.util.Arrays;

/**
 * @author yanghl
 * @Description:
 * @date 2022/12/5 22:36
 */
public class Code05BSNearRight {

    /**
     * 在arr上，找满足 <=value 的最右位置
     * @param arr
     * @param value
     * @return
     */
    public static int nearestIndex(int[] arr, int value) {
        if (arr == null || arr.length == 0){
            return -1;
        }
        int left = 0;
        int right = arr.length - 1;
        int maxRight = -1;
        int mid;
        while (left <= right){
            mid = left + ((right -left) >> 1);
            if (arr[mid] <= value){
                maxRight = mid;
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return maxRight;
    }

    // for test
    public static int test(int[] arr, int value) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] <= value) {
                return i;
            }
        }
        return -1;
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
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != nearestIndex(arr, value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(nearestIndex(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
