package class16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author: yanghl
 * @description: 图-Dijkstra算法: 采用贪心算法的策略，每次遍历到始点距离最近且未访问过的顶点的邻接节点，直到扩展到终点为止。
 * 	             从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
 * @date: 2023/6/14 9:47
 */
public class Code06_Dijkstra {
    public static HashMap<Node, Integer> dijkstra1(Node from){
        // 从head出发，到达每个节点的最小路径记录
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        // 已经访问过的点
        HashSet<Node> touchedNodes = new HashSet<>();
        distanceMap.put(from, 0);
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, touchedNodes);
        while (minNode != null){
            int distance = distanceMap.get(minNode);
            for (Edge edge: minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)){
                    distance += edge.weight;
                }else {
                    distance = Math.min(distanceMap.get(toNode), distance + edge.weight);
                }
                distanceMap.put(toNode, distance);
            }
            touchedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, touchedNodes);
        }
        return distanceMap;
    }

    // 在distanceMap里拿到距离开始节点最小距离的点，要排除touchedNodes里面的点
    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes){
        int minDistance = Integer.MAX_VALUE;
        Node minNode = null;
        Node curNode;
        int weight;
        for (Map.Entry<Node, Integer> entry: distanceMap.entrySet()) {
            curNode = entry.getKey();
            weight = entry.getValue();
            if (!touchedNodes.contains(curNode) && weight < minDistance){
                minNode = curNode;
                minDistance = weight;
            }
        }
        return minNode;
    }
}

