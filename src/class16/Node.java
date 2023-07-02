package class16;

import java.util.ArrayList;

/**
 * @author: yanghl
 * @description: 点结构描述
 * @date: 2023/6/2 9:23
 */
public class Node {
    // 值
    public int value;
    // 入度: 有多少条边指向它
    public int in;
    // 出度: 它有多少条边指向别人
    public int out;
    // 它指向别人的邻接点集合
    public ArrayList<Node> nexts;
    // 它指向别人的边集合
    public ArrayList<Edge> edges;

    public Node(int value){
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }


}

