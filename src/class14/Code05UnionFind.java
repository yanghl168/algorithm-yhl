package class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @author: yanghl
 * @description: 并查集-map实现
 *      1. 初始化时每个元素的代表元素是自己
 *      2. 判断两个元素是否是存在同一集合，只需要判断，他们的往上到不能再往上的代表元素是否相等
 *      2. 合并两个元素所在的集合
 *       2.1 查找两个往上到不能再往上的代表元素是否相等，相等则已经是同一集合
 *       2.2 不相等:
 *          2.2.1 分别找出个往上到不能再往上的代表元素，分别找出个往上到不能再往上的代表元素所在的集合的大小
 *          2.2.2 把小的集合的代表元素指向大的集合的代表元素
 *          2.2.3 把大的集合的大小改成大小集合大小总和
 *          2.2.4 把小的集合的元素从sizeMap移除
 *
 * @date: 2023/5/24 9:53
 */
public class Code05UnionFind {

    public static class UnionFind<T>{
        // 存储每一个元素的父元素
        public HashMap<T, T> fatherMap;
        public HashMap<T, Integer> sizeMap;

        public UnionFind(List<T> list){
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (T cur : list) {
                fatherMap.put(cur, cur);
                sizeMap.put(cur, 1);
            }
        }

        /**
         * @param cur:
         * @description 给你一个节点，请你往上到不能再往上，把代表返回
         */
        public  T findFather(T cur){
            Stack<T> stack = new Stack<>();
            T father = fatherMap.get(cur);
            stack.push(father);
            while (father != cur){
                stack.push(father);
                father = fatherMap.get(father);
            }
            // 这一步是为了提高下一次的查询效率
            while (!stack.isEmpty()){
                fatherMap.put(stack.pop(), cur);
            }
            return stack.peek();
        }

        /**
         * @description 判断两个元素是否是存在同一集合
         */
        public boolean isSameSet(T t1, T t2){
            return findFather(t1) == findFather(t2);
        }

        /**
         * @description 合并两个元素所在的集合
         */
        public void union(T a, T b) {
            T aFather = findFather(a);
            T bFather = findFather(b);
            if (aFather != bFather){
                int aSize = sizeMap.get(a);
                int bSize = sizeMap.get(b);
                T big = aSize >= bSize ? aFather : bFather;
                T small = big == aFather ? bFather : aFather;
                fatherMap.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }

        /**
         * @description 集合数量
         */
        public int getSize() {
            return sizeMap.size();
        }
    }


}

