package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author: yanghl
 * @description: OJ链接：https://www.lintcode.com/problem/topological-sorting
 * @date: 2023/6/5 9:15
 */
public class Code03_TopologicalOrderDFS2 {
    // 题目要求的类，不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 自己再封装一层,加上点次信息
    // 对于两个点和y, 如果x的点次大于y的点次，则在拓扑排序中，x在前
    public static class MyGraphNode {
        public DirectedGraphNode node;
        // 每个点，当前的点次
        public long nodes;

        public MyGraphNode(DirectedGraphNode node, long nodes){
            this.node = node;
            this.nodes = nodes;
        }
    }

    public static class MyComparator implements Comparator<MyGraphNode>{
        @Override
        public int compare(MyGraphNode o1, MyGraphNode o2) {
            // 对于两个点和y, 如果x的点次大于y的点次，则在拓扑排序中，x在前, 所以倒序排
            return Long.compare(o2.nodes, o1.nodes);
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, MyGraphNode> orderBufferMap = new HashMap<>();
        // 有可能有多个入度为0的结点，所以要循环遍历所有的点，保证所有点都建立在了缓存表
        for (DirectedGraphNode node: graph) {
            createBuffer(node, orderBufferMap);
        }
        // 拿出缓存表的value(MyGraphNode)进行排序
        ArrayList<MyGraphNode> myGraphNodes = new ArrayList<>(orderBufferMap.values());
        myGraphNodes.sort(new MyComparator());

        // 建立结果集
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (MyGraphNode node: myGraphNodes) {
            ans.add(node.node);
        }
        return ans;
    }

    /**
     * @param cur: 当前来到cur结点
     * @param orderBufferMap: 建立的缓存表，key存在，表示已经建立好
     * @return MyGraphNode (cur, 点次)
     * @description 当前来到cur结点, 请返回当前点所到之处，所有的点次
     */
    public static MyGraphNode createBuffer(DirectedGraphNode cur, HashMap<DirectedGraphNode, MyGraphNode> orderBufferMap){
        if (orderBufferMap.containsKey(cur)){
            return orderBufferMap.get(cur);
        }
        // 还没有建立当前点的点次
        long nodes = 0L;
        for (DirectedGraphNode next :cur.neighbors) {
            nodes += createBuffer(next, orderBufferMap).nodes;
        }
        // 当前的点次 = 所有邻接点的点次 + 1
        MyGraphNode ans = new MyGraphNode(cur, nodes + 1);
        // 建立缓存
        orderBufferMap.put(cur, ans);
        return ans;
    }
}

