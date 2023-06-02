package class16;

/**
 * @author: yanghl
 * @description: 图的边的描述
 * @date: 2023/6/2 9:28
 */
public class Edge {
    // 权重
    public int weight;
    // 出发结点
    public Node from;
    // 结束结点
    public Node to;

    public Edge(int weight, Node from, Node to){
        this.weight = weight;
        this.from = from;
        this.to = to;
    }


}

