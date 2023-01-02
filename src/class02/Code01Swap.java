package class02;

import java.util.Arrays;

/**
 * @author yanghl
 * @Description: 不申请空间，原地交换两个数
 * @date 2022/12/8 20:40
 */
public class Code01Swap {

    // i和j不能相等， 否则出错
    public static void swap(int[] arr, int i, int j){
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        int a = 2;
        int b = 5;
        System.out.println("a: " + a);
        System.out.println("a: " + b);
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a: " + a);
        System.out.println("a: " + b);

        int[] arr = {1,2,3};
        swap(arr, 0,1);
        System.out.println(Arrays.toString(arr));
    }
}
