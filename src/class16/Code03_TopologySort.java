package class16;

import java.util.*;

/**
 * @author: yanghl
 * @description: 拓扑排序
 * @date: 2023/6/4 11:48
 */
public class Code03_TopologySort {
    public static List<Node> sortedTopology(Graph graph) {
        // key所有的结点, value 结点的剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 剩余入度为0才会进入此队列
        Queue<Node> zeroInQueue = new LinkedList<>();

        // 初始化inMap zeroInQueue
        for (Node node :graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.value == 0){
                zeroInQueue.add(node);
            }
        }
        List<Node> ans = new ArrayList<>();
        int in;
        while (!zeroInQueue.isEmpty()){
            Node cur = zeroInQueue.poll();
            ans.add(cur);
            for (Node next : cur.nexts) {
                // 剩余入度减1
                in = inMap.get(next) - 1;
                inMap.put(next, in);
                // 如果剩余入度为0，加入队列
                if (in == 0){
                    zeroInQueue.add(next);
                }
            }
        }
        return ans;
    }
}

