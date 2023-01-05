package class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: yanghl
 * @CreateTime: 2023-01-05  21:54
 * @Description: 队列实现栈
 */
public class Code07TwoQueueImplementStack {
    public static class MyStack<T>{
        public Queue<T> queue;
        public Queue<T> help;

        public MyStack(){
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        /**
         * 入栈
         * @param t
         */
        public void push(T t){
            queue.offer(t);
        }

        public T pop(){
            while (queue.size() > 1){
                help.offer(queue.poll());
            }
            T element = queue.poll();
            Queue<T> temp = queue;
            queue = help;
            help = temp;
            return element;
        }

        public T peek(){
            while (queue.size() > 1){
                help.offer(queue.poll());
            }
            T element = queue.poll();
            help.offer(element);
            Queue<T> temp = queue;
            queue = help;
            help = temp;
            return element;
        }

        public boolean isEmpty(){
            return queue.isEmpty();
        }

        public static void main(String[] args) {
            System.out.println("Test Begin!");
            MyStack<Integer> myStack = new MyStack<>();
            Stack<Integer> testStack = new Stack<>();
            int testTime = 1000000;
            int max = 100000;
            for (int i = 0; i < testTime; i++) {
                if (myStack.isEmpty()){
                    if (!testStack.isEmpty()){
                        System.out.println("Failure!");
                    }
                    int firstNum = (int) (Math.random() * max);
                    myStack.push(firstNum);
                    testStack.push(firstNum);
                }else {
                    if (Math.random() < 0.25){
                        int num = (int) (Math.random() * max);
                        myStack.push(num);
                        testStack.push(num);
                    }else if(Math.random() < 0.5){
                        Integer peek1 = myStack.peek();
                        Integer peek2 = testStack.peek();
                        if (!peek1.equals(peek2)){
                            System.out.println("Failure!");
                        }
                    }else if(Math.random() < 0.75){
                        Integer pop1 = myStack.pop();
                        Integer pop2 = testStack.pop();
                        if (!pop1.equals(pop2)){
                            System.out.println("Failure!");
                        }
                    }else {
                        if (myStack.isEmpty() != testStack.isEmpty()){
                            System.out.println("Failure!");
                        }
                    }
                }
            }
            System.out.println("Test End!");
        }
    }
}
