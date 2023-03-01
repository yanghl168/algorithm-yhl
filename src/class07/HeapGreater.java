package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author yanghl
 * @Description: 手写小根堆
 *               T一定要是非基础类型，有基础类型需求包一层
 * @date 2023/2/23 22:26
 */
public class HeapGreater<T> {
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<? super T> comp){
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        this.comp  = comp;
    }

    public boolean isEmpty(){
        return heapSize == 0;
    }

    public int size(){
        return heapSize;
    }

    public boolean contains(T obj){
        return indexMap.containsKey(obj);
    }

    public T peek(){
        return heap.get(0);
    }


    public T pop(){
        T ans = heap.get(0);
        swap(0,heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    public void push(T obj){
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public void remove(T obj){
        T replace = heap.get(heapSize - 1);
        Integer index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if (obj != replace){
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    /**
     * 返回所有元素
     * @return
     */
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>(heap);
        return ans;
    }

    public void resign(T obj){
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }


    /**
     * 从 index 位置开始向上调整堆
     * @param index
     */
    public void heapInsert(int index){
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0){
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 从 index 位置开始向下调整堆
     * @param index
     */
    public void heapify(int index){
        int left = index * 2 + 1;
        while (left < heapSize){
            int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            best = comp.compare(heap.get(index), heap.get(best)) < 0 ? index : best;
            if (index == best){
                break;
            }
            swap(index, best);
            index = best;
            left = index * 2 + 1;
        }
    }


    public void swap( int i, int j){
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.replace(o2, i);
        indexMap.replace(o1, j);
    }



}
