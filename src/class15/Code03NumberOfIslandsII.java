package class15;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: yanghl
 * @description: https://leetcode.com/problems/number-of-islands-ii/
 *      岛升级问题，给你设定n,m表示有m*n的矩阵，再给你一个position[][]的数组，里面包含会变成陆地的位置（i，j）,输出每次空降（i，j）时的岛的数量
 * @date: 2023/6/1 9:30
 */
public class Code03NumberOfIslandsII {
    public static List<Integer> numIslandsII(int m, int n, int[][] positions) {
        ArrayList<Integer> ans = new ArrayList<>();
        UnionFind unionFind = new UnionFind(m, n);
        for (int[] position: positions) {
            int connectResult = unionFind.connect(position[0], position[1]);
            ans.add(connectResult);
        }
        return ans;
    }

    public static class UnionFind{
        public int parent[];
        public int size[];
        public int help[];
        public int col;
        public int row;
        public int sets;

        public UnionFind(int m, int n){
            col = m;
            row = n;
            sets = 0;
            int len = m * n;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        // 获取矩阵中在并查集数组的位置
        public int index(int i, int j){
            return i * col + j;
        }

        // 查找i位置的代表结点
        public int find(int i){
            int helpIndex = 0;
            while (i != parent[i]){
                help[helpIndex++] = i;
                i = parent[i];
            }
            // 扁平化
            for (int j = 0; j < helpIndex; j++) {
                parent[help[j]] = i;
            }
            return i;
        }

        // 合并
        public void union(int i1, int j1, int i2, int j2){
            // 边界条件
            if (i1 < 0 || i2 < 0 || j1 < 0 || j2 < 0 || i1 == col || i2 == col || j1 == row || j2 == row){
                return;
            }
            int aIndex = index(i1, j1);
            int bIndex = index(i2, j2);
            // 是0则不用合并
            if (size[aIndex] == 0 || size[bIndex] == 0){
                return;
            }
            int aParent = find(aIndex);
            int bParent = find(bIndex);
            if (aParent != bParent){
                if (size[aParent] >= size[bParent]){
                    parent[bParent] = aParent;
                    size[aParent] += size[bParent];
                }else {
                    parent[aParent] = bParent;
                    size[bParent] += size[aParent];
                }
                sets--;
            }
        }

        // 空降（i， j）位置，返回此时的陆地的数量
        public int connect(int i, int j){
            int curIndex =  index(i, j);
            // 没被记录过为陆地时才做后续操作，被记录过了则不用管
            if (size[curIndex] == 0){
                parent[curIndex] = curIndex;
                size[curIndex] = 1;
                sets++;
                // 合并
                union(i, j, i - 1, j);
                union(i, j, i + 1, j);
                union(i, j, i, j - 1);
                union(i, j, i , j + 1);
            }
            return sets;
        }
    }
}

