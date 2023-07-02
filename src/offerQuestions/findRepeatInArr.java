package offerQuestions;

/**
 * @author: yanghl
 * @description: 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
 *               要求时间复杂度是O(n),空间复杂度是O(1)
 * @date: 2023/6/26 9:18
 */
public class findRepeatInArr {
    /**
     * 思路：
     *     与&：两位同时为 1 ，结果才为 1，否则为0
     *     异或：运算的两个对象，如果两个相应位为异（值不同），则该位结果为1，否则为0。性质：对于整数a,有
     *         （1）a^a=0（2）a^0=a（3）a^b^c=a^(b^c)=(a^c)^b
     *     利用以上的性质，上面的题目的解法为：
     *      (1)对于出现两次的元素，使用“异或”操作后结果肯定为0，那么我们就可以遍历一遍数组，对所有元素使用异或操作，
     *          那么得到的结果就是两个出现一次的元素的异或结果。
     *      (2)因为这两个元素不相等，所以异或的结果肯定不是0，也就是可以在异或的结果中找到1位不为0的位，
     *         例如异或结果的最后一位不为0。
     *      (3)这样我们就可以最后一位将原数组元素分为两组，一组该位全为1，另一组该位全为0。
     *      (4)再次遍历原数组，最后一位为0的一起异或，最后一位为1的一起异或，两组异或的结果分别对应着两个结果。
     */
    public static int[] getTwoRepeat(int[] arr) {
        if(arr.length < 2)
            return arr;
        // 要返回的结果
        int[] result = new int[2];
        int res = arr[0];
        // 第一次对所有元素进行异或操作结果为两个出现一次的元素的异或结果
        for(int i = 1; i < arr.length; i++) {
            res ^= arr[i];
        }
        // 找出异或结果中为1的一个位置，此时要找的两个元素在该位置上不同
        int bitIndex = 0;
        // int类型4个字节，32位
        for (int i = 0; i < 32; i++) {
            if ((res >> i & 1) == 1){
                bitIndex = i;
                break;
            }
        }
        // 根据bitIndex为1，将元素分为两组
        for(int i = 0; i < arr.length; i++) {
            if((arr[i] >> bitIndex & 1) == 1) {
                // 对应位为1，异或得到的结果
                result[0] ^= arr[i];
            } else {
                //对应位为0，异或得到的结果
                result[1] ^= arr[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr1 = {1,1,3,4,3,4,6,5,7,9,7,9};
        int[] arr = {100000,100000,300000,400000,300000,400000,600000,500000,700000,900000,700000,900000};
        int[] twoRepeat = getTwoRepeat(arr);
        System.out.println(twoRepeat[0] + " " + twoRepeat[1]);
    }

}

