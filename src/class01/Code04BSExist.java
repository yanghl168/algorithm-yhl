package class01;

import java.util.Arrays;

/**
 * @author yanghl
 * @Description: 二分查找
 * @date 2022/12/4 23:16
 */
public class Code04BSExist {

    public static boolean exist(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0){
            return false;
        }
        int left = 0;
        int right = sortedArr.length - 1;
        int mid = 0;
        while (left < right){
            mid = left + ((right - left) >> 1);
            if (sortedArr[mid] > num){
                right = mid - 1;
            }else if (sortedArr[mid] < num){
                left = mid + 1;
            }else {
                return true;
            }
        }
        // 此时left == right
        return sortedArr[left] == num;
    }

    // for test
    public static boolean test(int[] sortedArr, int num) {
        for(int cur : sortedArr) {
            if(cur == num) {
                return true;
            }
        }
        return false;
    }


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
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
            if (test(arr, value) != exist(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
