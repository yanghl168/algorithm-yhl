package class02;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author yanghl
 * @Description: 输入一定能够保证，数组中所有的数都出现了M次，只有一种数出现了K次
 *               1 <= K < M
 *               返回这种数
 * @date 2022/12/13 22:49
 */
public class Code03KM {

    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int num : arr) {
            if (hashMap.containsKey(num)){
                hashMap.put(num, hashMap.get(num) + 1);
            }else {
                hashMap.put(num, 1);
            }
        }
        int ans = 0;
        for (int num : arr) {
            if (hashMap.get(num) == k){
                ans = num;
                break;
            }
        }
        return ans;
    }

    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int kNum = randomNumber(range);

        int times = k;

        int kinds = (int) (Math.random() * maxKinds) + 2;

        int[] arr = new int[times + (kinds - 1) * m];

        int index = 0;
        for (; index < times; index++) {
            arr[index] = kNum;
        }

        kinds --;
        HashSet<Integer> set = new HashSet<>();
        set.add(kNum);
        while (kinds != 0 ){
            int num = 0;
            do {
                num = randomNumber(range);
            }while (set.contains(num));
            kinds --;
            for (int i = 0; i < m; i++) {
                arr[index] = num;
                index++;
            }
        }
        // 此时arr里面的数已经准备好，接下来进行打乱
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        return arr;
    }

    // 为了测试
    // [-range, +range]
    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1)) - (int)(Math.random() * (range + 1));
    }

    public static int onlyKTimes(int[] arr, int k, int m) {
        int[] help = new int[32];
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                // 给num第i位是1的位进行累加
                if (((num >> i) & 1) == 1){
                    help[i]++;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < help.length; i++) {
            // 找出出现k次的数的含1的位
            if (help[i] % m != 0){
                ans |= (1 << i);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
//        int[] arr = {1,1,2,4,5,6,1,2,4,4,5,6,5,6};
//        int ans = onlyKTimes(arr, 2, 3);
//        System.out.println(ans);

        int kinds = 5;
        int range = 30;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = onlyKTimes(arr, k, m);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");

    }
}
