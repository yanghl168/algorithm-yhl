package class02;

/**
 * @author yanghl
 * @Description:
 * @date 2022/12/8 21:44
 */
public class Code02EvenTimesOddTimes {
    /**
     * arr中有一种数，出现了奇数次，其他的数都出现了偶数次，请找出奇数次的数
     * @param arr
     */
    public static void printOddTimesNum1(int[] arr) {
        int temp = 0;
        for (int i = 0; i < arr.length; i++) {
            temp = temp ^ arr[i];
        }
        System.out.println("出现了奇数次的数:" + temp);
    }


    /**
     * arr中有2种数，出现了奇数次，其他的数都出现了偶数次，请找出奇数次的数a和b
     * @param arr
     */
    public static void printOddTimesNum2(int[] arr) {
        int t1 = 0;
        for (int i = 0; i < arr.length; i++) {
            t1 = t1 ^ arr[i];
        }
        // 此时 t1=a^b,由于 a!=b,所以 t1!=0
        // 找到 t1 的最右的一个1位置, 这个位置在a或b也会出现一个1
        int onlyOne = t1 & (-t1);
        int t2 = 0;
        for (int i = 0; i < arr.length; i++) {
            // 用 onlyOne 去筛选与t1在相同的位置出现1的数
            if ((arr[i] & onlyOne) != 0){
                t2 = t2 ^ arr[i];
            }
        }
        // 循环过后此时t2为a或b
        // 再去异或原来的t1 即可得到另一个数:
        // a^t1 即 a^a^b 得到 b; b^t1 即 b^a^b 得到a
        int t3 = t2 ^ t1;
        System.out.println("出现奇数次的数是:" + t2 + "、" + t3);
    }

    public static void main(String[] args) {
        // 模拟2出现奇数次
        int[] arr = {1,1,1,2,2,3,4,5,3,2,1,4,6,5,6};
        printOddTimesNum1(arr);


        // 模拟2、4出现奇数次
        int[] arr2 = {1,1,4,2,2,3,4,5,3,2,4,6,5,6};
        printOddTimesNum2(arr2);
    }
}
