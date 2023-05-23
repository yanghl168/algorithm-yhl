package class14;

import java.util.HashSet;

/**
 * @author: yanghl
 * @description: 贪心-用最少的灯照亮所有街道
 *      给定一个字符串str，只由’X’和’.'两种字符构成 ‘X’表示墙，不能放灯，也不需要点亮；
 *      ’.'表示街道，可以放灯，需要点亮 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
 *      返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 * @date: 2023/5/23 9:53
 */
public class Code04Light {
    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // str[index....]位置，自由选择放灯还是不放灯
    // str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
    // 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        if (index == str.length) { // 结束的时候
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'X') { // 当前位置是点的话
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else { // str还没结束
            // i X .
            int no = process(str, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(no, yes);
        }
    }


    /**
     * @param road:
     * @description: 纯贪心，直接遍历
     *  eg: X   .    .    X/.
     *          i    i+1  i+2
     */

    public static int minLight2(String road) {
        char[] chars = road.toCharArray();
        int i = 0;
        int light  = 0;
        while (i < chars.length){
            // i是墙X
            if (chars[i] == 'X'){
                i++;
            }else {
                // i是街道.
                light++;
                if (i + 1 == chars.length) {
                    return light;
                }else {
                    // i+1 是墙X，结束本轮，下一轮从i+2开始
                    if (chars[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        // i+1 是街道.，不用考虑i+2是街道还是墙， 结束本轮，下一轮从i+3开始
                        i = i + 3;
                    }
                }
            }
        }
        return light;
    }


    /**
     * @description 更简洁的做法，两个X之间，数一下.的数量，然后除以3，向上取整，把灯数累加
     *
     */
    public static int minLight3(String road) {
        char[] chars = road.toCharArray();
        // 当前两个X之间有多少个.
        int curPoints = 0;
        int lights = 0;
        for (char cur: chars) {
            if (cur == 'X'){
                // 由于只要curPoints有一个就需要加一盏灯, 所以要加2再除3
                lights += (curPoints + 2) /3;
                // 置0
                curPoints = 0;
            }else {
                curPoints++;
            }
        }
        lights += (curPoints + 2) /3;
        return lights;
    }

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = minLight1(test);
            int ans2 = minLight2(test);
            int ans3 = minLight3(test);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }

}

