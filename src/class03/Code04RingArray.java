package class03;

import lombok.ToString;

import java.util.*;

/**
 * @author yanghl
 * @Description: 用数组实现队列
 * @date 2022/12/17 22:29
 */
public class Code04RingArray {
    @ToString
    public static class MyQueue{
        public int pushIndex;
        public int popIndex;
        public int size;
        public int[] queue;
        public int limit;

        public MyQueue (int limit){
            this.queue = new int[limit];
            this.limit = limit;
            pushIndex = 0;
            popIndex = 0;
            size = 0;
        }

        public void push(int value){
            if (limit == size){
                throw new RuntimeException("队列已满！");
            }
            size++;
            queue[pushIndex] = value;
            pushIndex = nextIndex(pushIndex);
        }

        public int pop(){
            if (size == 0){
                throw new RuntimeException("队列已空！");
            }
            size--;
            int ans = queue[popIndex];
            popIndex = nextIndex(popIndex);
            return ans;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        public int nextIndex(int i){
            return i < limit - 1 ? i + 1 : 0;
        }
    }

    public static int[] getRandomArray(int maxLen, int max){
        int length = (int)(Math.random() * maxLen);
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int)(Math.random() * max);
        }
        return arr;
    }


    public static void main(String[] args) {

        MyQueue myQueue = new MyQueue(5);
        myQueue.push(4);
        myQueue.push(5);
        myQueue.push(6);
        myQueue.pop();
        myQueue.push(7);
        myQueue.push(8);
        while (!myQueue.isEmpty()){
            System.out.println(myQueue.pop() + "");
        }
    }
}
