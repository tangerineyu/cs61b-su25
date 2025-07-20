import java.util.ArrayList;
import java.util.List;

/**
 * Represents a set of ints. A simple implementation of a set using a list.
 */
public class ListSet implements SimpleSet {

    List<Integer> elems;

    public ListSet() {
        elems = new ArrayList<Integer>();
    }

    /** Adds k to the set. */
    @Override
    public void add(int k) {
        // TODO: Implement this method.
        if (!elems.contains(k)) {
            elems.add(k);
        }
    }

    /** Removes k from the set. */
    @Override
    public void remove(int k) {
        Integer toRemove = k;
        // TODO - use the above variable with an appropriate List method.
        // The reason is beyond the scope of this lab, but involves
        // method resolution.
        //这里是因为remove有两个不同参数的方法，一种是接受int对象，移除对应index的元素，这会导致indexoutofbound
        // 所以这里使用自动装箱，转换为Integer类型，调用另一个接受object的方法
        elems.remove(toRemove);
    }

    /** Return true if k is in this set, false otherwise. */
    @Override
    public boolean contains(int k) {
        // TODO: Implement this method.
        if (elems.contains(k)) {
            return true;
        }
        return false;
    }

    /** Return true if this set is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
      return this.size() == 0;
    }

    /** Returns the number of items in the set. */
    @Override
    public int size() {
        // TODO: Implement this method.
        return elems.size();
    }

    /** Returns an array containing all of the elements in this collection. */
    @Override
    public int[] toIntArray() {
        // TODO - use a for loop!
        int[] arr = new int[size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = elems.get(i);
        }
        return arr;
    }
}
