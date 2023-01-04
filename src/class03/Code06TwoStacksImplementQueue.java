package class03;

import java.util.Stack;

/**
 * @author yanghl
 * @Description: 用栈实现队列
 * @date 2023/01/04 21:28
 */
public class Code06TwoStacksImplementQueue {
    public static class StackToQueue{
        public Stack<Integer> pushStack;
        public Stack<Integer> popStack;

        StackToQueue(){
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        /**
         * 倒数据
         */
        public void pushToPop(){
            if (popStack.isEmpty()){
                while (!pushStack.isEmpty()){
                    popStack.push(pushStack.pop());
                }
            }
        }

        /**
         * 入队
         * @param value
         */
        public void add(Integer value){
            pushStack.push(value);
            pushToPop();
        }

        /**
         * 出队列
         */
        public Integer poll(){
            if (popStack.isEmpty()){
                throw new RuntimeException("your queue is empty");
            }
            Integer value = popStack.pop();
            pushToPop();
            return value;
        }

        /**
         * 获取队头元素
         */
        public Integer peek(){
            if (popStack.isEmpty()){
                throw new RuntimeException("your queue is empty");
            }
            Integer value = popStack.peek();
            return value;
        }
    }

    public static void main(String[] args) {
        StackToQueue queue = new StackToQueue();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
    }
}
