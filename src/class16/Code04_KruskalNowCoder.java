package class16;

import java.io.*;
import java.util.Arrays;

/**
 * @author: yanghl
 * @description: 图-k算法-最小生成树-最小代价修路
 *   测试链接 : https://www.nowcoder.com/questionTerminal/c23eab7bb39748b6b224a8a3afbe396b
 *      一个有n户人家的村庄，有m条路连接着。村里现在要修路，每条路都有一个代价，现在请你帮忙计算下，
 *      最少需要花费多少的代价，就能让这n户人家连接起来。
 *      输入第一行，两个整数n,m;
 *      接下来m行，每行三个整数a,b,c，表示有路连接编号为a和b的人家，修路要花费的代价为c。
 *      数据保证能将每户人家都连接起来。
 *      注意重边的情况。n≤10000, m≤100000, 边权 0<c≤10000
 * @date: 2023/6/8 10:16
 */
public class Code04_KruskalNowCoder {

    // 设计并查集结构: 把n户人家表现在并查集里
    public static int maxN = 10001;
    public static int[] parent = new int[maxN];
    public static int[] size = new int[maxN];
    public static int[] help = new int[maxN];

    // 初始化并查集
    public static void build(int n){
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    // 查找第i户人家的代表
    public static int find(int i){
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
    // 如果a和b，原本是一个集合，返回false
    // 如果a和b，不是一个集合，合并，然后返回true
    public static boolean union(int a, int b){
        boolean ret = false;
        int aParent = find(a);
        int bParent = find(b);
        if (aParent != bParent){
            ret = true;
            if (size[aParent] >= size[bParent]){
                parent[bParent] = aParent;
                size[aParent] += size[bParent];
            }else {
                parent[aParent] = bParent;
                size[bParent] += size[aParent];
            }
        }
        return ret;
    }

    // 边
    public static int maxM = 100001;

    // 图的邻接矩阵
    public static int[][] edges = new int[maxM][3];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF){
            int n = (int) in.nval;
            in.nextToken();
            int m = (int) in.nval;
            // 初始化edges数组
            for (int i = 0; i < m; i++) {
                in.nextToken();
                edges[i][0] = (int)in.nval;
                in.nextToken();
                edges[i][1] = (int)in.nval;
                in.nextToken();
                edges[i][2] = (int)in.nval;
            }
            // 把边排序
            Arrays.sort(edges, 0, m, (a, b) -> a[2] - b[2]);
            int ans = 0;
            for (int[] edge : edges) {
                if (union(edge[0], edge[1])){
                    ans += edge[2];
                }
            }
            out.println(ans);
            out.flush();
        }
    }

}

