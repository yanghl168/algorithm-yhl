package class14;

import java.io.*;

/**
 * @author: yanghl
 * @description: 并查集-数组实现
 *      测试链接：https://www.nowcoder.com/questionTerminal/e7ed657974934a30b2010046536a5372
 * @date: 2023/5/25 9:18
 */
public class Code06UnionFind {

    // 数据量
    public static int N = 1000001;

    // 存储父结点的集合: i的父节点为: fatherArray[i]
    public static int[] fatherArray = new int[N];

    // 存储集合的大小: i所在的集合的大小为: sizeArray[i]
    public static int[] sizeArray = new int[N];

    // 辅助数组, 用于在查找i所在集合的代表结点时，给集合做得更加扁平化，提高下次查询的效率
    public static int[] help = new int[N];

    /**
     * @description 初始化集合
     */
    public static void init(int N){
        for (int i = 0; i <= N; i++) {
            fatherArray[i] = i;
            sizeArray[i] = 1;
        }
    }

    /**
     * @description 查找i所在集合的代表结点
     */
    public static int findRepresent(int i){
        int t = 0;
        while (i != fatherArray[i]){
            help[t++] = i;
            i = fatherArray[i];
        }
        // 扁平化
        t--;
        while (t >= 0){
            fatherArray[help[t--]] = i;
        }
        return i;
    }

    /**
     * @description 查询x和y是不是一个集合
     */
    public static boolean isSameSet(int x, int y){
        return findRepresent(x) == findRepresent(y);
    }

    /**
     * @description x所在的集合，和y所在的集合，合并成一个集合
     */
    public static void union(int x, int y) {
        int representX = findRepresent(x);
        int representY = findRepresent(y);
        if (representX != representY){
            int xSize = sizeArray[representX];
            int ySize = sizeArray[representY];
            if (xSize >= ySize){
                fatherArray[representY] = representX;
                sizeArray[representX] += sizeArray[representY];
            }else {
                fatherArray[representX] = representY;
                sizeArray[representY] += sizeArray[representX];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            init(n);
            in.nextToken();
            int m = (int) in.nval;
            for (int i = 0; i < m; i++) {
                in.nextToken();
                int op = (int) in.nval;
                in.nextToken();
                int x = (int) in.nval;
                in.nextToken();
                int y = (int) in.nval;
                if (op == 1) {
                    out.println(isSameSet(x, y) ? "Yes" : "No");
                    out.flush();
                } else {
                    union(x, y);
                }
            }
        }
    }





}

