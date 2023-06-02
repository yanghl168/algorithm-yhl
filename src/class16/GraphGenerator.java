package class16;

/**
 * @author: yanghl
 * @description: 图的转换接口
 * @date: 2023/6/2 9:34
 */
public class GraphGenerator {

    /**
     * 	matrix 所有的边
     * 	N*3 的矩阵
     * 	[weight, from节点上面的值，to节点上面的值]
     * 	[ 5 , 0 , 7]
     * 	[ 3 , 0,  1]
     */
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            // 权重、出发结点、去向结点的值
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];
            // 出发结点、去向结点，图里没有的话就new出来放到图里
            if (!graph.nodes.containsKey(from)){
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)){
                graph.nodes.put(to, new Node(to));
            }
            // 取出 出发结点、去向结点
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            // 建边
            Edge edge = new Edge(weight, fromNode, toNode);
            // 把边放到图里
            graph.edges.add(edge);
            // 设置出发结点的邻接点
            fromNode.nexts.add(toNode);
            // 设置出发结点的边
            fromNode.edges.add(edge);
            // 设置出发结点和去向结点的出入度
            fromNode.out++;
            toNode.in++;
        }
        return graph;
    }





}

