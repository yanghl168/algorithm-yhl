package class14;

import java.util.PriorityQueue;

/**
 * @author: yanghl
 * @description:
 *      题目描述
 *      给定一个正数数组arr，arr的累加和代表金条的总长度，arr的每个数代表金条要分成的长度。
 *      规定长度为k的金条分成两块，费用为k个铜板。返回分割的最小代价。
 *      例如：
 *      给定数组[10,20,30]，代表一共三个人，整块金条长度为60，金条要分成10,20,30三个部分。
 *      如果先把长度为60的金条分成10和50，花费60；再把长度为50的金条分成20和30，花费50；一共需要花费110个铜板。
 *      但是如果先把长度为60的金条分成30和30，花费60;再把30的金条分成10和20,花费30；一共花费90个铜板。
 * @date: 2023/5/19 9:16
 */
public class Code02LessMoneySplitGold {

    // 纯暴力
    public static int lessMoney1(int[] arr) {
        if(arr ==  null || arr.length == 0){
            return 0;
        }
        return process1(arr, 0);
    }

    /**
     * @param arr: 等待合并的数组
     * @param pre: 目前产生的总代价
     * @return int
     * @author yanghl
     * @description arr中只剩下一个数的时候停止合并，返回最小总代价
     */
    public static int process1(int[] arr, int pre){
        if (arr.length == 1){
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int[] next = mergeTwo(arr, i, j);
                ans = Math.min(ans, process1(next, pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    /**
     * @author yanghl
     * @description 把arr[]的index1和index2位置的元素加起来合并到一个数，返回新的数组
     */
    public static int[] mergeTwo(int[] arr, int index1, int index2){
        int N = arr.length;
        int[] ans = new int[N - 1];
        int ansIndex = 0;
        for (int i = 0; i < N; i++) {
            if (i != index1 && i != index2){
                ans[ansIndex++] = arr[i];
            }
        }
        ans[ansIndex] = arr[index1] + arr[index2];
        return ans;
    }

    /**
     * @param arr:
     * @return int
     * @description 使用堆，贪心法
     *  最小堆
     *  本题要求合理的拆分数组，使得最终变为所要求的每个长度，反过来思考：
     *  将最终的数字，两两合并，不断的两两合并最终变为一个数字
     *  总的开销：每合并一次，开销为两者之和，不断的两两合并
     *  由此可见，整个拆分过程如同二叉树展开。叶子节点就是每个分割的大小。
     *  原理是哈夫曼树
     *
     *  实现：
     *  1.准备一个小根堆
     *  2.把所有数放到小根堆中
     *  3.然后依次弹出两个数，求和
     *  4.把和扔到小根堆中
     *  循环3、4步骤
     */
    public static int lessMoney2(int[] arr) {
        if(arr ==  null || arr.length == 0){
            return 0;
        }
        return process(arr);
    }

    public static int process(int[] arr){
        PriorityQueue<Integer> smallHeap = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            smallHeap.add(arr[i]);
        }
        int sum = 0;
        int cur = 0;
        while (smallHeap.size() > 1){
            cur = smallHeap.poll() + smallHeap.poll();
            sum += cur;
            smallHeap.add(cur);
        }
        return sum;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}

