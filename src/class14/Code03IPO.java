package class14;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author: yanghl
 * @description: TODO
 *      IPO问题
 *      输入: 参数1: 正数数组 costs
 *           参数2: 正数数组 profits
 *           参数3: 正数 k
 *           参数4: 正数 m
 *      costs[i]表示i号项目的花费 profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 *      k表示你不能并行、只能串行的最多做k个项目  m表示你初始的资金
 *      说明：你每做完一个项目，马上获得的收益，可以支持你去做下一个项目。
 *      输出： 你最后获得的最大钱数。
 * @date: 2023/5/22 10:07
 */
public class Code03IPO {
    public static class Program{
        public int profit;
        public int cost;

        public Program(int profit, int cost){
            this.profit = profit;
            this.cost = cost;
        }
    }

    public static class MInCostComparator implements Comparator<Program>{

        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }

    public static class MaxProfitComparator implements Comparator<Program>{

        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o2.profit;
        }
    }

    /**
     * @param costs:
     * @param profits:
     * @param K:
     * @param M:
     * @description
     *      1.把所有项目按花费放进一个小根堆 costHeap
     *      2.遍历costHeap,把花费比目前所拥有的资金小的放进收益的大根堆里 profitHeap
     *      3.若大根堆里没有数据，表明资金不足以做项目，直接返回当前的M
     *      4.若大根堆里有数据，弹出一个项目， M += 弹出的项目的收益
     */
    public static int findMaxProfit(int[] costs, int[] profits, int K, int M) {
        PriorityQueue<Program> costHeap = new PriorityQueue<>(new MInCostComparator());
        PriorityQueue<Program> profitHeap = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < costs.length; i++) {
            costHeap.add(new Program(costs[i], profits[i]));
        }
        while (K > 0) {
            while (!costHeap.isEmpty() && costHeap.peek().cost <= M) {
                profitHeap.add(costHeap.poll());
            }
            if (profitHeap.isEmpty()) {
                return M;
            }
            M += profitHeap.poll().profit;
            K--;
        }
        return M;
    }
}

