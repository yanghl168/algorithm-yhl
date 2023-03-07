package class08;
import java.util.HashMap;

/**
 * @author yanghl
 * @Description:
 * @date 2023/3/4 22:49
 */
public class Code01TrieTree {
    public static class Node01{
        private int pass;
        private int end;
        private Node01[] next;

        public Node01(){
            // 0    a
            // 1    b
            // 2    c
            // ..   ..
            // 25   z
            // next[i] == null   i方向的路不存在
            // next[i] != null   i方向的路存在
            pass = 0;
            end = 0;
            next = new Node01[26];
        }
    }

    public static class Trie1{
        private Node01 root;

        public Trie1(){
            root = new Node01();
        }


        /**
         * 查询一个字符串出现了几次
         * @param str
         * @return
         */
        public int search(String str){
            if (str == null){
                return 0;
            }
            char[] chars = str.toCharArray();
            Node01 node = root;
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = chars[i] - 'a';
                if (node.next[path] == null){
                    return 0;
                }
                node = node.next[path];
            }
            return node.end;
        }

        /**
         * 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
         * @param pre
         * @return
         */
        public int prefixNumber(String pre){
            if (pre == null){
                return 0;
            }
            Node01 node = root;
            char[] chars = pre.toCharArray();
            int path = 0;
            for (int i = 0; i < chars.length; i++) {
                path = chars[i] - 'a';
                if (node.next[path] == null){
                    return 0;
                }
                node = node.next[path];
            }
            return node.pass;
        }

        /**
         * 插入一个字符串
         * @param str
         */
        public void insert(String str){
            if (str == null){
                return;
            }
            char[] chars = str.toCharArray();
            Node01 node = root;
            node.pass++;
            int path = 0;  // 当前字符的路径
            for (int i = 0; i < chars.length; i++) {
                path = chars[i] - 'a';  // 由字符，对应成走向哪条路
                if (node.next[path] == null){
                    node.next[path] = new Node01();
                }
                node = node.next[path];
                node.pass++;
            }
            node.end++;
        }

        /**
         * 删除str
         * @param str
         */
        public void delete(String str){
            if (search(str) != 0) {
                char[] chars = str.toCharArray();
                Node01 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < chars.length; i++) {
                    path = chars[i] - 'a';
                    if (--node.next[path].pass == 0) {
                        node.next[path] = null;
                        return;
                    }
                    node = node.next[path];
                }
                node.end--;
            }
        }
    }

    public static class Node2 {
        public int pass;
        public int end;
        public HashMap<Integer, Node2> nexts;

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int index = 0;
                for (int i = 0; i < chs.length; i++) {
                    index = (int) chs[i];
                    if (--node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    node = node.nexts.get(index);
                }
                node.end--;
            }
        }

        // word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
        }
    }

    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
