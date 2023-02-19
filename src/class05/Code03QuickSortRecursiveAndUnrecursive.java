package class05;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author yanghl
 * @Description: 快排的递归与非递归
 * @date 2023/2/8 22:27
 */
public class Code03QuickSortRecursiveAndUnrecursive {
    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int[] netherLandFlag(int arr[], int L, int R){
        if (L > R){
            return new int[]{-1, -1};
        }
        if (L == R){
            return new int[]{L, R};
        }
        int less = L - 1;
        int more = R;
        int index = L;
        while (index < more){
            if (arr[index] < arr[R]){
                swap(arr, less + 1, index);
                index++;
                less++;
            }else if (arr[index] > arr[R]){
                swap(arr, more - 1, index);
                more--;
            }else {
                index++;
            }
        }
        swap(arr, more, R);
        return new int[]{less + 1, more};
    }

    public static void quickSort01(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    // 递归实现
    public static void process(int[] arr, int L, int R){
        if (L >= R){
            return;
        }
        swap(arr, L + (int)(Math.random() * (R - L + 1)), R);
        int[] equalArea = netherLandFlag(arr, L, R);
        process(arr, L, equalArea[0] - 1);
        process(arr, equalArea[1] + 1, R);
    }

    public static void printArray(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    // 代表相等区域的左右边界
    public static class Op{
        int left;
        int right;

        public Op(int l, int r){
            left = l;
            right = r;
        }
    }


    // 非递归, 栈实现，实质: 把递归的系统栈换成了用户自己定义的栈
    public static void quickSort02(int[] arr){
        int[] equalArea = netherLandFlag(arr, 0, arr.length - 1);
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0, equalArea[0] - 1));
        stack.push(new Op(equalArea[1] + 1, arr.length - 1));
        while (!stack.isEmpty()){
            Op op = stack.pop();
            if (op.left < op.right){
                swap(arr, op.left + (int)(Math.random() * (op.right - op.left + 1)), op.right);
                int[] equal = netherLandFlag(arr, op.left, op.right);
                stack.push(new Op(op.left, equal[0] - 1));
                stack.push(new Op(equal[1] + 1, op.right));
            }
        }
    }

    // 非递归，队列实现
    public static void quickSort03(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        int N = arr.length - 1;
        swap(arr, (int)(Math.random()*N), N);
        int[] equalArea = netherLandFlag(arr, 0, N);
        Queue<Op> queue = new LinkedList<>();
        queue.offer(new Op(0, equalArea[0] - 1));
        queue.offer(new Op(equalArea[1] + 1, N));
        while (!queue.isEmpty()){
            Op op = queue.poll();
            int left = op.left;
            int right = op.right;
            if (left < right) {
                swap(arr, left + (int) (Math.random() * (right - left + 1)), op.right);
                int[] equal = netherLandFlag(arr, left, right);
                queue.offer(new Op(left, equal[0] - 1));
                queue.offer(new Op(equal[1] + 1, right));
            }
        }
    }


    public static void main(String[] args) {
        int[] arr = {1,8,3,5,7,2,4,6,22,7,33,55,12};
//        quickSort01(arr);
//        quickSort02(arr);
        quickSort03(arr);
        printArray(arr);
    }
}
