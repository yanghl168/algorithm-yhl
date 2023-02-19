package class06;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author yanghl
 * @Description: 比较器
 * @date 2023/2/9 22:34
 */
public class Code01Comparator {

    public static class Student{
        public String name;
        public int id;
        public int age;

        public Student(String name, int id, int age){
            this.name = name;
            this.age = age;
            this.id = id;
        }
    }

    /**
     * 任何比较器里的compare方法都遵循的规则:
     *      返回负数，认为第一个参数排在前面
     *      返回正数，认为第二个参数排在前面
     *      返回 0 ，认为谁排在前面无所谓
     */

    /**
     * 根据id从小到大排序的比较器
     */
    public static class IdComparator implements Comparator<Student>{

        @Override
        public int compare(Student o1, Student o2) {
            // 根据id从小到大排序
            return o1.id - o2.id;
        }
    }

    /**
     * 根据id从小到大排序,id相等年龄从大到小排序
     */
    public static class IdAndAgeComparator implements Comparator<Student>{

        @Override
        public int compare(Student o1, Student o2) {
            // 根据id从小到大排序
            return o1.id != o2.id ? (o1.id - o2.id) : (o2.age - o1.age);
        }
    }

    public static void printStudents(Student[] students) {
        for (Student student : students) {
            System.out.println("Name : " + student.name + ", Id : " + student.id + ", Age : " + student.age);
        }
    }

    public static void printArray(Integer[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Student student1 = new Student("A", 1, 25);
        Student student2 = new Student("B", 2, 21);
        Student student3 = new Student("C", 3, 22);
        Student student4 = new Student("D", 1, 27);
        Student student5 = new Student("E", 2, 24);

        Student[] students = {student1, student2, student3, student4, student5};
        // 根据id升序排序
        Arrays.sort(students, new IdComparator());
        System.out.println("==================学生根据id升序排序====================");
        printStudents(students);

        Arrays.sort(students, new IdAndAgeComparator());
        System.out.println("==================学生根据id从小到大排序,id相等年龄从大到小排序====================");
        printStudents(students);


        // TreeMap 有序Map
        // 不给出比较器或者排序方式会报错
//        TreeMap<Student, String> treeMap = new TreeMap<>();

        TreeMap<Student, String> treeMap = new TreeMap<>(new IdAndAgeComparator());
        treeMap.put(student1, "我是A");
        treeMap.put(student2, "我是B");
        treeMap.put(student3, "我是C");
        treeMap.put(student4, "我是D");
        treeMap.put(student5, "我是E");

        for (Student st: treeMap.keySet()) {
            System.out.println(st.name + "," + st.id + "," + st.age);
        }

    }
}
