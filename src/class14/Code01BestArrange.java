package class14;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: yanghl
 * @description: 会议安排问题
 *    学校的小礼堂每天都会有许多活动，这些活动的计划时间会发生冲突，需要选择出一些活动进行举办。
 *    小刘的工作就是安排学校小礼堂的活动，每个时间最多安排一个活动。现在小刘有一些活动计划的时间表，
 *    请问他该如何安排才能使得安排的会议数量最多。
 * @date: 2023/5/17 9:14
 */
public class Code01BestArrange {
    public static class Program{
        int start;
        int end;

        public Program(int start, int end){
            this.start = start;
            this.end = end;
        }
    }

    /**
     * @description 暴力法
     */
    public static int bestArrange1(Program[] programs) {
        if (programs.length == 0){
            return 0;
        }
        return process(programs, 0, 0);
    }

    /**
     * @param programs: 剩下的会议
     * @param done:     已经安排了多少个会议
     * @param timeLine: 当前来到的时间点
     * @description 返回能安排的最多的会议数
     */
    public static int process(Program[] programs, int done, int timeLine){
        // 所有会议都已安排完
        if (programs.length == 0){
            return done;
        }
        // 还有会议剩
        int max = done;
        // 当前安排的会议是什么会，都枚举一次
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start){
                Program[] next = removeIndexProgram(programs, i);
                int curDone = process(next, done + 1, programs[i].end);
                max = Math.max(max, curDone);
            }
        }
        return max;
    }

    /**
     * @param programs:
     * @param index:
     * @description 移除第index位置的会议
     */
    public static Program[] removeIndexProgram(Program[] programs, int index){
        int N = programs.length;
        Program[] ans = new Program[N - 1];
        int ansIndex = 0;
        for (int i = 0; i < programs.length; i++) {
            if (i != index){
                ans[ansIndex++] = programs[i];
            }
        }
        return ans;
    }

    /**
     * @description 贪心法
     */
    public static int bestArrange2(Program[] programs) {
        int ret = 0;
        int timeLine = 0;
        Arrays.sort(programs, new MyComparator());
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start){
                ret++;
                timeLine = programs[i].end;
            }
        }
        return ret;
    }

    /**
     * @description 根据会议的结束时间进行排序
     */
    public static class MyComparator implements Comparator<Program>{
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}

