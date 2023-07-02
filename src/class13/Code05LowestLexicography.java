package class13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author: yanghl
 * @description:
 *        给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有拼接结果中，字典序最小的结果。
 *        字典序定义：Java中字符串的排序方式，比如 “abc” 和 “bce”相比，“abc” 第一位a的ASCII码小于 “bce” 第一位b的ASCII码，
 *        所以 “abc” < “bce”，所以“abc”的字典序更小。对于位数不等的字符串，“abc” 和 “be”，从各自的第一位开始比较，
 *        “abc”的a小于“be”的b，所以“abc” < “be”。
 * @date: 2023/5/16 9:12
 */
public class Code05LowestLexicography {
    /**
     * @description 暴力解法
     */
    public static String lowestString1(String[] arr) {
        if (arr == null || arr.length == 0){
            return "";
        }
        TreeSet<String> ans = process(arr);
        return ans.size() == 0 ? "" : ans.first();
    }

    /**
     * @description arr中所有字符串全排列，返回所有可能的结果，并封装在TreeSet里
     */
    public static TreeSet<String> process(String[] arr){
        TreeSet<String> ans = new TreeSet<>();
        if (arr == null || arr.length == 0){
            ans.add("");
            return ans;
        }
        for (int i = 0; i < arr.length; i++) {
            String first = arr[i];
            String[] removeResult = removeIndex(arr, i);
            TreeSet<String> next = process(removeResult);
            for (String str : next) {
                ans.add(first + str);
            }
        }
        return ans;
    }

    /**
     * @description 移除arr的index位置的元素
     */
    public static String[] removeIndex(String[] arr, int index){
        int N = arr.length;
        int ansIndex = 0;
        String[] ans = new String[N - 1];
        for (int i = 0; i < N; i++) {
            if (i != index){
                ans[ansIndex++] = arr[i];
            }
        }
        return ans;
    }

    /**
     * @description 贪心法
     */
    public static String lowestString2(String[] arr) {
        if (arr == null || arr.length == 0){
            return "";
        }
        String ans = "";
        Arrays.sort(arr, new MyComparator());
        for (int i = 0; i < arr.length; i++) {
            ans += arr[i];
        }
        return ans;
    }

    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    // for test
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString1(arr1).equals(lowestString2(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}

