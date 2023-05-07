package class11;

/**
 * @author yanghl
 * @Description: 问题描述：
 *                  把一段纸条竖放在桌子上，然后从纸条的下边向上方对折一次，压出折痕后展开。此时折痕是凹下去的。
 *                  如果从纸条的下边向上方连续对折两次，压出折痕后展开，此时有三条折痕，从上到下依次是凹、凹、凸。
 *
 *                  给定一个参数N，代表纸条从下到上连续对折N次。请从上到下打印所有折痕的方向。
 *                  eg：N = 1，打印down； N = 2，打印down、down、up。
 *
 *               问题分析: 经实践折痕，分析得出：打印结果为二叉树的中序遍历结果，
 *                       且此二叉树的头结点为凹， 任何左子树的头都是凹的，任何右子树得到头都是凸的
 * @date 2023/4/5 10:29
 */
public class Code07PaperFolding {
    public static void printAllFolds(int N){
        // 因为任何左子树的头都是凹的，所以中序遍历第一个被打印的记为true
        process(1, N, true);
        System.out.println();
    }

    /**
     * 当前这个节点在第i层，一共有N层，N固定不变的
     * down==true 凹, down==false 凸,
     * 函数的功能：当前你来了一个节点，脑海中想象的！中序打印以你想象的节点为头的整棵
     * @param i
     * @param N
     * @param down
     */
    public static void process(int i, int N, boolean down){
        if (i > N){
            return;
        }
        process(i + 1, N, true);
        System.out.print(down ? "凹 " : "凸 ");
        process(i + 1, N, false);
    }

    public static void main(String[] args) {
        int N = 3;
        printAllFolds(N);
    }

}
