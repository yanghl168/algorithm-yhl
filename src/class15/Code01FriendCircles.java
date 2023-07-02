package class15;
/**
 * @author: yanghl
 * @description: https://leetcode.cn/problems/number-of-provinces/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
 *      有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，
 *      那么城市 a 与城市 c 间接相连。
 *      省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 *      给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，
 *      而 isConnected[i][j] = 0 表示二者不直接相连。
 *      返回矩阵中 省份 的数量。
 * @date: 2023/5/29 9:09
 */
public class Code01FriendCircles {

    public int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        UnionFind unionFind = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (isConnected[i][j] == 1){
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets;

    }

    public static class UnionFind{
        // parent[i] = a : i 的父结点是a
        public int[] parent;
        // size[i] = b : 如果i为代表结点，i所在的集合的大小为b
        public int[] size;
        // 辅助数组
        public int[] help;
        // 集合的数量
        public int sets;

        public UnionFind(int N){
            parent = new int[N];
            size = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        // 从i开始一直往上找到代表结点
        private int find(int i){
            int helpIndex = 0;
            while (i != parent[i]){
                help[helpIndex++] = i;
                i = parent[i];
            }
            // 扁平化
            for (int j = 0; j < helpIndex; j++) {
                parent[help[i]] = i;
            }
            return i;
        }

        // 把i所在的集合与j所在的集合合并
        public void union(int i, int j){
            int iParent = find(i);
            int jParent = find(j);
            if (iParent != jParent){
                int iSize = size[iParent];
                int jSize = size[jParent];
                if (iSize > jSize){
                    parent[jParent] = iParent;
                    size[iParent] += size[jParent];
                }else {
                    parent[iParent] = jParent;
                    size[jParent] += size[iParent];
                }
                sets--;
            }
        }

        public int sets(){
            return sets;
        }

    }



}

