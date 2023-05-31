package class15;

/**
 * @author: yanghl
 * @description: https://leetcode.cn/problems/number-of-islands/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
 *      给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 *      岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 *      此外，你可以假设该网格的四条边均被水包围。
 *
 * @date: 2023/5/31 9:22
 */
public class Code02NumberOfIslands {
    public static int numIslands(char[][] board) {
        int island = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1'){
                    island++;
                    infect(board, i, j);
                }
            }
        }
        return island;
    }

    // 从(i, j)这个位置出发，把所有连成一片的‘1’字符，改成0
    private static void infect(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1'){
            return;
        }
        board[i][j] = 0;
        // 上下左右
        infect(board, i-1, j);
        infect(board, i+1, j);
        infect(board, i, j-1);
        infect(board, i, j+1);
    }

    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        UnionFind unionFind = new UnionFind(board);
        // 首行
        for (int j = 1; j < col; j++) {
            // 与首行的右方合并
            if (board[0][j - 1] == '1' && board[0][j] == '1'){
                unionFind.union(0, j - 1, 0, j);
            }
        }
        // 首列
        for (int i = 1; i < row; i++) {
            // 与首列的上方合并
            if (board[i - 1][0] == '1' && board[i][0] == '1'){
                unionFind.union(i - 1, 0, i, 0);
            }
        }
        // 除了首行和首列
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                // 与右方和上方合并
                if (board[i][j] == '1'){
                    // 右方
                    if (board[i][j - 1] == '1'){
                        unionFind.union(i, j, i, j - 1);
                    }
                    // 上方
                    if (board[i - 1][j] == '1'){
                        unionFind.union(i, j, i - 1, j);
                    }
                }
            }
        }
        return unionFind.sets;
    }

    public static class UnionFind{
        public int[] parent;
        public int[] size;
        public int[] help;
        public int col;
        public int sets;

        public UnionFind(char[][] board){
            col = board[0].length;
            sets = 0;
            int row = board.length;
            // 一维数组的长度
            int len = col * row;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '1'){
                        int index = index(i, j);
                        parent[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        // 获取board的(i, j)位置在并查集的数组下标
        public int index(int i, int j){
            return i * col + j;
        }

        // 查找并查集的i位置的代表结点
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

        // 合并board的(i1, j1)和(i2, j2)位置的元素
        public void union(int i1, int j1, int i2, int j2){
            int a = index(i1, j1);
            int b = index(i2, j2);
            int aParent = find(a);
            int bParent = find(b);
            if (aParent != bParent){
                int aSize = size[aParent];
                int bSize = size[bParent];
                if (aSize >= bSize){
                    parent[bParent] = aParent;
                    size[aParent] += size[bParent];
                }else {
                    parent[aParent] = bParent;
                    size[bParent] += size[aParent];
                }
                sets--;
            }
        }
        // 返回集合个数
        public int sets(){
            return sets;
        }
    }

    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");


        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

    }




}

