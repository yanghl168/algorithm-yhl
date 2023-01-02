package class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author yanghl
 * @Description: 链表实现双端队列，利用双端队列再实现栈和队列
 * @date 2022/12/16 22:42
 */
public class Code03DoubleEndsQueueToStackAndQueue {

    public static class Node<T>{
        public T value;
        public Node<T> next;
        public Node<T> last;

        public Node(T value){
            this.value = value;
            next = null;
            last = null;
        }
    }

    public static class DoubleEndsQueue<T>{
        public Node<T> head;
        public Node<T> tail;

        public DoubleEndsQueue(){
            head = null;
            tail = null;
        }


        /**
         * 从头部加
         * @param value
         */
        public void addFromHead(T value){
            Node<T> cur = new Node<>(value);
            if (head == null){
                head = cur;
                tail = cur;
            }else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        /**
         * 从尾部加
         * @param value
         */
        public void addFromTail(T value){
            Node<T> cur = new Node<>(value);
            if (head == null){
                head = cur;
                tail = cur;
            }else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        /**
         * 从头部弹出
         * @return
         */
        public T popFromHead(){
            if (head == null){
                return null;
            }
            Node<T> cur = head;
            if (head == tail){
                head = null;
                tail = null;
            }else {
                head = head.next;
                cur.next = null;
                head.last = null;
            }
            return cur.value;
        }

        /**
         * 从尾部弹出
         * @return
         */
        public T popFromTail(){
            if (tail == null){
                return null;
            }
            Node<T> cur = tail;
            if (head == tail){
                head = null;
                tail = null;
            }else {
                tail = tail.last;
                cur.last = null;
                tail.next = null;
            }
            return cur.value;
        }

        public Boolean isEmpty(){
            return head == null;
        }

    }

    public static class MyStack<T>{
        public DoubleEndsQueue<T> myStack;

        public MyStack(){
            myStack = new DoubleEndsQueue<>();
        }

        public void push(T value){
            myStack.addFromHead(value);
        }
        public T pop(){
            return myStack.popFromHead();
        }
        public boolean isEmpty(){
            return myStack.isEmpty();
        }
    }

    public static class MyQueue<T>{
        public DoubleEndsQueue<T> myQueue;

        public MyQueue(){
            myQueue = new DoubleEndsQueue<>();
        }
        public void push(T value){
            myQueue.addFromHead(value);
        }
        public T poll(){
            return myQueue.popFromTail();
        }
        public Boolean isEmpty(){
            return myQueue.isEmpty();
        }
    }

    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            MyStack<Integer> myStack = new MyStack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("oops!");
                        }
                    }
                }
                int numq = (int) (Math.random() * value);
                if (queue.isEmpty()) {
                    myQueue.push(numq);
                    queue.offer(numq);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(numq);
                        queue.offer(numq);
                    } else {
                        if (!isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

}
