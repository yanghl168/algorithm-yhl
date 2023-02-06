package class05;

/**
 * @author yanghl
 * @Description:
 * @date 2023/2/5 15:37
 */
public class Code02PartitionAndQuickSort {
    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 把数组arr[]的L...R 划分为两组: <=X 和 >X
     * @param arr
     * @param L
     * @param R
     * @return 分好组后等于arr[R]的位置 equal
     */
    public static int partition(int[] arr, int L, int R){
        if (L > R){
            return -1;
        }
        if (L == R){
            return L;
        }
        int lessEqual = L - 1;
        int index = L;
        while (index < R){
            if (arr[index] <= arr[R]){
                swap(arr, lessEqual + 1, index);
                lessEqual++;
            }
            index++;
        }
        swap(arr, ++lessEqual, R);
        // 分好组后等于arr[R]的位置
        return lessEqual;
    }

    /**
     * 把数组的L...R分为三组: <X  ==X   >X  ,荷兰国旗问题的划分方法
     * @param arr
     * @param L
     * @param R
     * @return 返回 ==X 的开始和结束位置
     */
    public static int[] netherlandsFlag(int[] arr, int L, int R){
        if (L > R){
            return new int[]{-1, -1};
        }
        if (L == R){
            return new int[]{L, R};
        }
        // 小于区的右边界
        int less = L - 1;
        // 大于区的左边界
        int more = R;
        int index = L;
        while (index < more){
            if (arr[index] < arr[R]){
                swap(arr, less + 1, index);
                less ++;
                index++;
            }else if (arr[index] == arr[R]){
                index++;
            }else {
                more--;
                swap(arr, more, index);
            }
        }
        swap(arr, more, R);
        return new int[]{less + 1, more};
    }

    public static void quickSort1(int[]arr){
        if (arr == null || arr.length < 1){
            return;
        }
        process01(arr, 0, arr.length - 1);
    }

    // 快排1.0
    public static void process01(int[] arr, int L, int R){
        if (L >= R){
            return;
        }
        int equalIndex = partition(arr, L, R);
        process01(arr, L, equalIndex - 1);
        process01(arr, equalIndex + 1, R);
    }






    public static void quickSort2(int[]arr){
        if (arr == null || arr.length < 1){
            return;
        }
        process02(arr, 0, arr.length - 1);
    }

    // 快排2.0
    private static void process02(int[] arr, int L, int R) {
        if (L >= R){
            return;
        }
        int[] equalRange = netherlandsFlag(arr, L, R);
        process02(arr, L, equalRange[0] - 1);
        process02(arr, equalRange[1] + 1, R);
    }








    public static void quickSort3(int[]arr){
        if (arr == null || arr.length < 1){
            return;
        }
        process03(arr, 0, arr.length - 1);
    }

    // 快排3.0
    private static void process03(int[] arr, int L, int R) {
        if (L >= R){
            return;
        }
        // 把当前arr数组L...R的arr[R]随机与L...R位置交换, 减少极端事件的发生
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] equalRange = netherlandsFlag(arr, L, R);
        process03(arr, L, equalRange[0] - 1);
        process03(arr, equalRange[1] + 1, R);
    }


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");

    }
}
