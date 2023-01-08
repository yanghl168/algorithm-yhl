package class03;

/**
 * @author yanghl
 * @Description: 在数组中求最大值（递归）
 * @date 2023/1/8 16:04
 */
public class Code08GetMax {
    public static int getMax(int[] arr){
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int L, int R){
        // L...R 上只有一个数，最大值直接返回
        if(L == R){
            return arr[L];
        }
        int mid = L + ((R - L) >> 1);
        int leftMax = process(arr, L, mid);
        int rightMax = process(arr, mid + 1, R);
        // 数组中最大值为左半数组的最大值和右半数组的最大值中的最大值
        return Math.max(leftMax, rightMax);
    }

    public static void main(String[] args) {
        int[] arr = {1,6,8,3,5,2,77,99,44,64,8,3,83};
        int max = getMax(arr);
        System.out.println(max);
    }
}
