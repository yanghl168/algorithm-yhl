package class13;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanghl
 * @Description:  最大快乐值问题
 *      员工信息的定义如下:
 *      public static class Employee {
 *          public int happy; // 这名员工可以带来的快乐值
 *          public List<Employee> subordinates; // 这名员工有哪些直接下级
 *
 *          public Employee(int h) {
 *              happy = h;
 *              subordinates = new ArrayList<>();
 *          }
 *      }
 *          公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、没有环的多叉树。树的头节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。
 *      叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级。这个公司现在要办 party，你可以决定哪些员工来，哪些员工不来，
 *      规则：
 *      1.如果某个员工来了，那么这个员工的所有直接下级都不能来
 *      2.派对的整体快乐值是所有到场员工快乐值的累加
 *      3.你的目标是让派对的整体快乐值尽量大 给定一棵多叉树的头节点 boss，请返回派对的最大快乐值。
 *
 * @date 2023/5/11
 */
public class Code04MaxHappy {
    public static class Employee {
        public int happy;                      // 这名员工可以带来的快乐值
        public List<Employee> subordinates;    // 这名员工有哪些直接下级

        public Employee(int h) {
            happy = h;
            subordinates = new ArrayList<>();
        }
    }

    /**
     * @return int
     * @description 普通实现
     * @date 2023/5/11
     */
    public static int maxHappy1(Employee boss){
        if (boss == null){
            return 0;
        }
        return process1(boss, false);
    }

    /**
     * @description :
     *      当前来到的节点叫cur，up表示cur的上级是否来。
     *      如果up为true, 表示当前节点的上级来的情况下，整棵树的最大快乐值是多少
     *      如果up为false, 表示当前节点的上级不来的情况下，整棵树的最大快乐值是多少
     */
    public static int process1(Employee cur, boolean up){
        if (up){
            // 当前节点的上级来
            int ans = 0;
            for (Employee sub: cur.subordinates) {
                // 当前节点的上级来，所以当前节点不能来
                ans += process1(sub, false);
            }
            return ans;
        }else {
            // 当前节点的上级不来
            // 当前节点来
            int p1 = cur.happy;
            // 当前节点不来
            int p2 = 0;
            for (Employee sub: cur.subordinates) {
                p1 += process1(sub, true);
                p2 += process1(sub, false);
            }
            return Math.max(p1, p2);
        }
    }

    /**
      * @return int
     * @description 递归套路实现
     * @date 2023/5/11
     */
    public static int maxHappy2(Employee head){
        Info allInfo = process2(head);
        return Math.max(allInfo.no, allInfo.yes);
    }

    public static class Info{
        // 当前员工不来的时候，整体最大快乐值
        public int no;
        // 当前员工来的时候，整体最大快乐值
        public int yes;

        public Info(int no, int yes){
            this.no = no;
            this.yes = yes;
        }
    }

    public static Info process2(Employee e){
        if (e == null){
            return new Info(0,0);
        }
        int no = 0;
        int yes = e.happy;
        for (Employee sub: e.subordinates) {
            Info info = process2(sub);
            no += Math.max(info.no, info.yes);
            yes += info.no;
        }
        return new Info(no, yes);
    }

    // for test
    public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    // for test
    public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            e.subordinates.add(next);
            genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
