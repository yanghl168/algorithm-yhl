package class07;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author yanghl
 * @Description:  最大线段重合问题
 * 题目描述
 *      给定很多线段，每条线段都有两个数组 [start, end]，表示线段的开始位置和结束位置，左右都是闭区间。规定：
 *      线段开始和结束位置一定都是整数值；
 *      线段重合区域的长度必须 >=1 （比如(1,3) 和 (3,5) 不算重合）
 *      返回线段最多重合区域中，包含了几条线段。
 * @date 2023/2/21 22:03
 */
public class Code01CoverMax {

    /**
     * 暴力法
     * @param lines
     */
    public static int maxCover1(int[][] lines){
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(lines[i][0], min);
            max = Math.max(lines[i][1], max);
        }
        int count = 0;
        for (double i = 0.5 + min; i < max; i++) {
            int curAmount = 0;
            for (int j = 0; j < lines.length; j++) {
                if (lines[j][0] < i && lines[j][1] > i){
                    curAmount++;
                }
            }
            count = Math.max(count, curAmount);
        }
        return count;
    }

    /**
     * 借助小根堆
     * @param lines
     * @return
     */
    public static int maxCover2(int[][] lines){
        Line [] linesList = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            Line line = new Line(lines[i][0], lines[i][1]);
            linesList[i] = line;
        }
        Arrays.sort(linesList, new MyComparator());
        int count = 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < linesList.length; i++) {
            while (!heap.isEmpty() && (heap.peek() <= linesList[i].start)){
                heap.poll();
            }
            heap.add(linesList[i].end);
            count = Math.max(count, heap.size());
        }
        return count;
    }

    public static class Line{
        public int start;
        public int end;

        public Line(int start, int end){
            this.start = start;
            this.end = end;
        }
    }

    public static class MyComparator implements Comparator<Line>{

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    /**
     * 与maxCover2过程一直，只不过不使用额外数组，代码更简洁
     * @param lines
     * @return
     */
    public static int maxCover3(int[][] lines){
        int count = 0;
        Arrays.sort(lines, (a, b) -> (a[0] - b[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i][0]){
                heap.poll();
            }
            heap.add(lines[i][1]);
            count = Math.max(count, heap.size());
        }
        return count;
    }

    // for test
    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }


    public static void main(String[] args) {

        Line l1 = new Line(4, 9);
        Line l2 = new Line(1, 4);
        Line l3 = new Line(7, 15);
        Line l4 = new Line(2, 4);
        Line l5 = new Line(4, 6);
        Line l6 = new Line(3, 7);

        // 底层堆结构，heap
        PriorityQueue<Line> heap = new PriorityQueue<>(new MyComparator());
        heap.add(l1);
        heap.add(l2);
        heap.add(l3);
        heap.add(l4);
        heap.add(l5);
        heap.add(l6);

        while (!heap.isEmpty()) {
            Line cur = heap.poll();
            System.out.println(cur.start + "," + cur.end);
        }

        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            int ans3 = maxCover3(lines);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }


}
