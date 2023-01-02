package class03;

import java.util.Stack;

/**
 * @author yanghl
 * @Description: 获取栈中的最小元素
 * @date 2022/12/18 21:28
 */
public class Code05GetMinStack {

    public static class MyStack{
        private Stack<Integer> stackData;
        private Stack<Integer> minStack;

        public MyStack(){
            stackData = new Stack<>();
            minStack = new Stack<>();
        }

        public Integer getMin(){
            if (minStack.isEmpty()){
                throw new RuntimeException("your stack is Empty");
            }
            return minStack.peek();
        }

        public void push(Integer value){
            if (minStack.isEmpty()){
                minStack.push(value);
            }else if (value < minStack.peek()){
                minStack.push(value);
            }else {
                minStack.push(minStack.peek());
            }
            stackData.push(value);
        }

        public Integer pop(){
            if (stackData.isEmpty()){
                throw new RuntimeException("your stack is empty");
            }
            minStack.pop();
            return stackData.pop();
        }
    }


    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(3);
        System.out.println(stack.getMin());
        stack.push(4);
        System.out.println(stack.getMin());
        stack.push(1);
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
    }

}
