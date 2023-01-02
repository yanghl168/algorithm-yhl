package class03;

/**
 * @author yanghl
 * @Description: 把给定值删除
 * @date 2022/12/15 22:19
 */
public class Code02DeleteGivenValue {

    public static class Node{
        public int value;
        public Node next;

        public Node(int val){
            value = val;
        }
    }

    public Node deleteGivenValue(Node head, int num){
        while (head != null){
            if (head.value == num){
                head = head.next;
            }else {
                break;
            }
        }
        Node pre = head;
        Node cur = head;
        while (cur != null){
            if (cur.value == num){
                pre.next = cur.next;
            }else {
                pre = head;
            }
            cur = cur.next;
        }
        return head;
    }
}
