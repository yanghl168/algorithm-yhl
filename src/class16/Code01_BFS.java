package class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: yanghl
 * @description: 宽度优先遍历
 * @date: 2023/6/2 9:55
 */
public class Code01_BFS {
    public static void bfs(Node start) {
        if (start == null){
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node next: cur.nexts) {
                if (!set.contains(next)){
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

}

