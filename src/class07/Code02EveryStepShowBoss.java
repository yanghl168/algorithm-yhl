package class07;

import java.util.*;

/**
 * @author yanghl
 * @Description: 给定一个整型数组，int[] arr；和一个布尔类型数组，boolean[] op
 * 两个数组一定等长，假设长度为N，arr[i]表示客户编号，op[i]表示客户操作
 * arr = [ 3   ,   3   ,   1   ,  2,      1,      2,      5…
 * op = [ T   ,   T,      T,     T,      F,      T,       F…
 * 依次表示：3用户购买了一件商品，3用户购买了一件商品，1用户购买了一件商品，2用户购买了一件商品，1用户退货了一件商品，2用户购买了一件商品，5用户退货了一件商品…
 *
 * 一对arr[i]和op[i]就代表一个事件：
 * 用户号为arr[i]，op[i] == T就代表这个用户购买了一件商品
 * op[i] == F就代表这个用户退货了一件商品
 * 现在你作为电商平台负责人，你想在每一个事件到来的时候，
 * 都给购买次数最多的前K名用户颁奖。
 * 所以每个事件发生后，你都需要一个得奖名单（得奖区）。
 *
 * 得奖系统的规则：
 * 1.如果某个用户购买商品数为0，但是又发生了退货事件，
 *      则认为该事件无效，得奖名单和上一个事件发生后一致，例子中的5用户
 * 2.某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
 * 3.每次都是最多K个用户得奖，K也为传入的参数
 *       如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果
 * 4.得奖系统分为得奖区和候选区，任何用户只要购买数>0，
 *       一定在这两个区域中的一个
 * 5.购买数最大的前K名用户进入得奖区，
 *       在最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区
 * 6.如果购买数不足以进入得奖区的用户，进入候选区
 * 7.如果候选区购买数最多的用户，已经足以进入得奖区，
 *      该用户就会替换得奖区中购买数最少的用户（大于才能替换），
 *      如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户
 *      如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户
 * 8.候选区和得奖区是两套时间，
 *      因用户只会在其中一个区域，所以只会有一个区域的时间，另一个没有
 *      从得奖区出来进入候选区的用户，得奖区时间删除，
 *      进入候选区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 *      从候选区出来进入得奖区的用户，候选区时间删除，
 *      进入得奖区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 * 9.如果某用户购买数==0，不管在哪个区域都离开，区域时间删除，
 *      离开是指彻底离开，哪个区域也不会找到该用户
 *      如果下次该用户又发生购买行为，产生>0的购买数，
 *      会再次根据之前规则回到某个区域中，进入区域的时间重记
 *
 *  请遍历arr数组和op数组，遍历每一步输出一个得奖名单
 *  public List<List<Integer>>  topK (int[] arr, boolean[] op, int k)
 *
 *
 * @date 2023/2/27 22:39
 */
public class Code02EveryStepShowBoss {


    public static class Customer{
        private int id;
        private int buy;
        private int enterTime;

        public Customer(int id, int buy, int enterTime){
            this.id = id;
            this.buy = buy;
            this.enterTime = enterTime;
        }
    }

    /**
     * 候选区排序：购买数量大的放前面，相同时，enterTime 小的放前面
     */
    public static class CandidateComparator implements Comparator<Customer>{
        @Override
        public int compare(Customer o1, Customer o2) {
            return (o1.buy != o2.buy) ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    /**
     * 得奖区排序：购买数量小的放前面，相同时，enterTime 小的放前面
     */
    public static class DaddyComparator implements Comparator<Customer>{

        @Override
        public int compare(Customer o1, Customer o2) {
            return (o1.buy != o2.buy) ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    /**
     * 干完所有的事，模拟，不优化 暴力法
     * @param arr
     * @param op
     * @param k
     * @return
     */
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> candidate = new ArrayList<>();
        ArrayList<Customer> daddy = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            // 当前购买数为0，且发生退货
            if (!buyOrRefund && !map.containsKey(id)){
                ans.add(getCurAns(daddy));
                continue;
            }
            // 当前购买数为=0，且发生购买行为
            // 当前购买数为>0，且发生购买行为
            // 当前购买数为>0，且发生退货行为
            if (!map.containsKey(id)){
                map.put(id, new Customer(id, 0, 0));
            }
            // 买、卖
            Customer customer = map.get(id);
            if (buyOrRefund){
                customer.buy++;
            }else {
                customer.buy--;
            }
            // 不允许有当前购买数=0的客户存在候选区和得奖区
            if (customer.buy == 0){
                map.remove(id);
            }
            // 判断适合放在哪一个区
            if (!candidate.contains(customer) && !daddy.contains(customer)){
                customer.enterTime = i;
                if (daddy.size() < k){
                    daddy.add(customer);
                }else {
                    candidate.add(customer);
                }
            }
            // 清除购买数为0的用户
            cleanZeroBuy(candidate);
            cleanZeroBuy(daddy);
            // 排序
            candidate.sort(new CandidateComparator());
            daddy.sort(new DaddyComparator());
            // 调整此时的候选区和得奖区
            move(candidate, daddy, k, i);
            // 把当前的得奖用户加结果集进去
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    /**
     * 获取当前的得奖用户
     * @param daddy
     * @return
     */
    public static List<Integer> getCurAns(ArrayList<Customer> daddy){
        List<Integer> ans = new ArrayList<>();
        for (Customer cu: daddy) {
            ans.add(cu.id);
        }
        return ans;
    }

    /**
     * 剔除购买数为0的用户
     * @param arr
     */
    public static void cleanZeroBuy(ArrayList<Customer> arr){
        arr.removeIf(cu -> cu.buy == 0);
    }

    /**
     * 调整此时的候选区和得奖区
     * @param candidate
     * @param daddy
     * @param k
     * @param time
     */
    public static void move(ArrayList<Customer> candidate, ArrayList<Customer> daddy, int k, int time){
        if (candidate.size() == 0){
            return;
        }
        if (daddy.size() < k){
            Customer customer = candidate.get(0);
            candidate.remove(customer);
            customer.enterTime = time;
            daddy.add(customer);
        }else {
            if (candidate.get(0).buy > daddy.get(0).buy){
                Customer oldDaddy = daddy.get(0);
                Customer newDaddy = candidate.get(0);
                candidate.remove(newDaddy);
                daddy.remove(oldDaddy);
                oldDaddy.enterTime = time;
                newDaddy.enterTime = time;
                candidate.add(oldDaddy);
                daddy.add(newDaddy);
            }
        }
    }


    /**
     * 用自己手写的增强堆实现
     */
    public static class WhosYourDaddy{
        private HashMap<Integer, Customer> customers;
        private HeapGreater<Customer> candidatesHeap;
        private HeapGreater<Customer> daddyHeap;
        private int limitDaddy;

        public WhosYourDaddy(int limitDaddy){
            customers = new HashMap<>();
            candidatesHeap = new HeapGreater<>(new CandidateComparator());
            daddyHeap = new HeapGreater<>(new DaddyComparator());
            this.limitDaddy = limitDaddy;
        }

        public void operator(int time, int id, boolean buyOrRefund){
            if (!buyOrRefund && !customers.containsKey(id)){
                return;
            }
            // 当前购买数为0, 发生购买行为
            // 当前购买数>0, 发生购买行为
            // 当前购买数>0, 发生退货行为
            if (!customers.containsKey(id)){
                customers.put(id, new Customer(id, 0, 0));
            }
            Customer cur = customers.get(id);
            if (buyOrRefund){
                cur.buy++;
            }else {
                cur.buy--;
            }
            if (cur.buy == 0){
                customers.remove(id);
            }
            if (!candidatesHeap.contains(cur) && !daddyHeap.contains(cur)){
                cur.enterTime = time;
                if (daddyHeap.size() < limitDaddy){
                    daddyHeap.push(cur);
                }else {
                    candidatesHeap.push(cur);
                }
            }else if (candidatesHeap.contains(cur)){
                if (cur.buy == 0){
                    candidatesHeap.remove(cur);
                }else {
                    candidatesHeap.resign(cur);
                }
            }else {
                if (cur.buy == 0){
                    daddyHeap.remove(cur);
                }else {
                    daddyHeap.resign(cur);
                }
            }
            daddyMove(time);
        }


        public List<Integer> getDaddy(){
            ArrayList<Integer> ans = new ArrayList<>();
            List<Customer> daddy = daddyHeap.getAllElements();
            for (Customer cu: daddy) {
                ans.add(cu.id);
            }
            return ans;
        }

        public void daddyMove(int time){
            if (candidatesHeap.isEmpty()){
                return;
            }
            if (daddyHeap.size() < limitDaddy){
                Customer pop = candidatesHeap.pop();
                pop.enterTime = time;
                daddyHeap.push(pop);
            }else {
                Customer candidate = candidatesHeap.peek();
                Customer daddy = daddyHeap.peek();
                if (candidate.buy > daddy.buy){
                    candidate.enterTime = time;
                    daddy.enterTime = time;
                    daddyHeap.remove(daddy);
                    daddyHeap.push(candidate);
                    candidatesHeap.remove(candidate);
                    candidatesHeap.push(daddy);
                }
            }
        }
    }

    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhosYourDaddy whosYourDaddy = new WhosYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whosYourDaddy.operator(i, arr[i], op[i]);
            ans.add(whosYourDaddy.getDaddy());
        }
        return ans;

    }

    // 为了测试
    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort((a, b) -> a - b);
            cur2.sort((a, b) -> a - b);
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " , " + op[j]);
                }
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
