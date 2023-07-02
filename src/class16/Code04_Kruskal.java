package class16;

import java.util.*;

/**
 * @author: yanghl
 * @description: 图-k算法-最小生成树
 * @date: 2023/6/7 11:31
 */
public class Code04_Kruskal {

    public static class UnionFind{
        // key-结点  value: key结点的上一个结点
        public HashMap<Node, Node> fatherMap;
        // key-结点 value-key结点的代表结点的集合的大小
        public HashMap<Node, Integer> sizeMap;

        public UnionFind(){
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        // 初始化图的所有结点在并查集的fatherMap和sizeMap
        public void makeSets(Collection<Node> nodes){
            for (Node node: nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node findFather(Node node){
            Stack<Node> stack = new Stack<>();
            while (node != fatherMap.get(node)){
                stack.push(node);
                node = fatherMap.get(node);
            }
            // 扁平化
            while (!stack.isEmpty()){
                fatherMap.put(stack.pop(), node);
            }
            return node;
        }

        public boolean isSameSet(Node a, Node b){
            return fatherMap.get(a) == fatherMap.get(b);
        }

        // 把a, b 合并
        public void union(Node a, Node b){
            if (a == null || b == null){
                return;
            }
            Node aFather = findFather(a);
            Node bFather = findFather(b);
            if (aFather != bFather){
                int aSize = sizeMap.get(aFather);
                int bSize = sizeMap.get(bFather);
                if (aSize >= bSize){
                    fatherMap.put(bFather, aFather);
                    sizeMap.put(aFather, aSize + bSize);
                    sizeMap.remove(bFather);
                }else {
                    fatherMap.put(aFather, bFather);
                    sizeMap.put(bFather, aSize + bSize);
                    sizeMap.remove(aFather);
                }
            }
        }
    }

    // 根据边的权重进行排序
    public static class EdgeComparator implements Comparator<Edge>{
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind unionFind = new UnionFind();
        // 初始化图的结点到并查集里
        unionFind.makeSets(graph.nodes.values());
        // 把边加到小根堆
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(new EdgeComparator());
        edgeQueue.addAll(graph.edges);
        HashSet<Edge> result = new HashSet<>();
        while (!edgeQueue.isEmpty()){
            // 拿一条权重最小的边出来
            Edge edge = edgeQueue.poll();
            // 这条边相连的两个结点如果没有拿过，即属于并查集的不同集合，这条边我们要了，并且合并这两个结点的集合
            if (!unionFind.isSameSet(edge.from, edge.to)){
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }
}

