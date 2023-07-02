package class16;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author: yanghl
 * @description: 图-Prim算法-最小生成树
 * @date: 2023/6/9 10:01
 */
public class Code05_Prim {

    // 边的排序规则
    public static class EdgeComparator implements Comparator<Edge>{
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> primMST(Graph graph) {
        // 边的小根堆
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(new EdgeComparator());
        // 存放已经解锁的点
        HashSet<Node> nodeSet = new HashSet<>();
        // 把需要的边放在result里
        Set<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            // check 当前的node是否已经解锁出来，已经解锁的不用再解锁
            if (!nodeSet.contains(node)){
                nodeSet.add(node);
                // 解锁出来的node的所有的边放到小根堆里
                edgeQueue.addAll(node.edges);
                while (!edgeQueue.isEmpty()){
                    // 拿一个最小权重的边出来
                    Edge edge = edgeQueue.poll();
                    // 拿到最小权重的边指向的下一个node
                    Node toNode = edge.to;
                    // check 当前的node是否已经解锁出来，已经解锁的不用再解锁
                    if (!nodeSet.contains(toNode)){
                        // 还未被解锁的话，就解锁改node,并且此边也要解锁
                        nodeSet.add(toNode);
                        result.add(edge);
                        // 把边放到小根堆
                        edgeQueue.addAll(edge.to.edges);
                    }
                }
            }
            // break;
        }
        return result;
    }

    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length;
        // 第i位置的数表示开始结点到i的最小距离
        int[] distances = new int[size];
        boolean[] visit = new boolean[size];
        visit[0] = true;
        // 初始化distance数组
        for (int i = 0; i < size; i++) {
            distances[i] = graph[0][i];
        }
        int sum = 0;
        for (int i = 1; i < size; i++) {
            int minPath = Integer.MIN_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] > minPath){
                    minPath = distances[j];
                    minIndex = j;
                }
            }
            if (minIndex == -1){
                return sum;
            }
            visit[i] = true;
            sum += minPath;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distances[j] > graph[minIndex][j]){
                    distances[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }
}

