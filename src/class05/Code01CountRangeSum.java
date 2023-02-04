package class05;

/**
 * @author yanghl
 * @Description: https://leetcode.cn/problems/count-of-range-sum/
 *      给你一个整数数组nums 以及两个整数lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含lower和upper）之内的 区间和的个数 。
 *      区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
 *
 *      示例 1：
 *      输入：nums = [-2,5,-1], lower = -2, upper = 2
 *      输出：3
 *      解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 *
 *      示例 2：
 *      输入：nums = [0], lower = 0, upper = 0
 *      输出：1
 * @date 2023/2/4 22:11
 */
public class Code01CountRangeSum {

    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length < 1){
            return 0;
        }
        // 从0...i的累加和数组
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    public static int process(long[] sum, int L, int R, int lower, int upper){
        if (L == R){
            if (sum[L] >= lower && sum[L] <= upper){
                return 1;
            }else {
                return 0;
            }
        }
        int mid = L + ((R - L) >> 1);
        int countLeft = process(sum, L, mid, lower, upper);
        int countRight = process(sum, mid + 1, R, lower, upper);
        int countMerge = merge(sum, L, mid, R, lower, upper);
        return countLeft + countRight + countMerge;
    }

    public static int merge(long[] sum, int L, int mid, int R, int lower, int upper){
        int ans = 0;
        int windowL = L;
        int windowR = L;
        // 遍历累加计算以j结尾符合条件的子数组的数量  [windowL, windowR)
        for (int i = mid + 1; i <= R; i++) {
            // 转化成在累加和数组中的左组
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            while (windowR <= mid && sum[windowR] <= max){
                windowR++;
            }
            while (windowL <= mid && sum[windowL] < min){
                windowL++;
            }
            ans += windowR - windowL;
        }

        // 归并排序
        long[] helps = new long[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= R){
            helps[i++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
        }
        while (p1 <= mid){
            helps[i++] = sum[p1++];
        }
        while (p2 <= R){
            helps[i++] = sum[p2++];
        }
        for (int j = 0; j < helps.length; j++) {
            sum[L + j] = helps[j];
        }
        return ans;
    }



























}
