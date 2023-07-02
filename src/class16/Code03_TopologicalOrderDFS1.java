package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author: yanghl
 * @description: OJ链接：https://www.lintcode.com/problem/topological-sorting
 * @date: 2023/6/6 9:40
 */
public class Code03_TopologicalOrderDFS1 {
    // 不要提交这个类 题目规定的
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 自己再包一层，加上深度
    // 深度大的一定比深度小的排前面
    public static class MyGraphNode{
        public DirectedGraphNode node;
        public int deep;

        public MyGraphNode(DirectedGraphNode node, int deep){
            this.node = node;
            this.deep = deep;
        }
    }

    public static class MyComparator implements Comparator<MyGraphNode>{
        @Override
        public int compare(MyGraphNode o1, MyGraphNode o2) {
            return o2.deep - o1.deep;
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph){
        HashMap<DirectedGraphNode, MyGraphNode> orderBufferMap = new HashMap<>();
        // 建立缓存
        for (DirectedGraphNode node: graph) {
            createBuffer(node, orderBufferMap);
        }
        // 把缓存里的value根据深度排序
        ArrayList<MyGraphNode> myGraphNodes = new ArrayList<>(orderBufferMap.values());
        myGraphNodes.sort(new MyComparator());
        // 结果集
        ArrayList<DirectedGraphNode> ret = new ArrayList<>();
        for (MyGraphNode myGraphNode : myGraphNodes) {
            ret.add(myGraphNode.node);
        }
        return ret;
    }

    /**
     * @param cur: 当前的 node
     * @param orderBufferMap: 缓存map：key-有向图结点, value-该结点在图里的深度
     * @return MyGraphNode
     * @description 建立缓存, 把node加入到缓存里面，并返回当前结点在图里的深度
     */
    public static MyGraphNode createBuffer(DirectedGraphNode cur, HashMap<DirectedGraphNode, MyGraphNode> orderBufferMap){
        // 缓存里有的话，直接拿
        if (orderBufferMap.containsKey(cur)){
            return orderBufferMap.get(cur);
        }
        // 缓存里还没有
        int deep = 0;
        // 计算当前结点的深度
        for (DirectedGraphNode next :cur.neighbors) {
            MyGraphNode node = createBuffer(next, orderBufferMap);
            deep = Math.max(deep, node.deep);
        }
        // 建立缓存
        MyGraphNode myGraphNode = new MyGraphNode(cur, deep + 1);
        orderBufferMap.put(cur, myGraphNode);
        return myGraphNode;
    }
}

