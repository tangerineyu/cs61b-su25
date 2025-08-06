import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    private HashMap<E, Integer> indexMap;


    // TODO: YOUR CODE HERE (no code should be needed here if not implementing the more optimized version)


    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
        indexMap = new HashMap<>();
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
        indexMap.put(element1, index2);
        indexMap.put(element2, index1);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return 2 * index;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return 2 * index + 1;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        if (index1 >= contents.size()) {
            return index2;
        }
        if (index2 >= contents.size()) {
            return index1;
        }
        E val1 = contents.get(index1);
        E val2 = contents.get(index2);
        if (val1 == null) {
            return index2;
        }
        if (val2 == null) {
            return index1;
        }
        if (val1.compareTo(val2) < 0) {
            return index1;
        }else if (val1.compareTo(val2) > 0) {
            return index2;
        }else {
            return index1;
        }
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        return contents.get(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        while (index > 1) {
            int parentIndex = getParentOf(index);
            E current = contents.get(index);
            E parent = getElement(parentIndex);
            if (current == null || parent == null) {
                break;
            }
            if (current.compareTo(parent) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        while (true) {
            int left = index * 2;
            int right = 2 * index + 1;
            if (left > contents.size()) {
                break;
            }
            E current = contents.get(index);
            int smallChild = min(left, right);
            E child = getElement(smallChild);
            if (smallChild >= contents.size() || child == null) {
                break;
            }
            if (current.compareTo(child) <= 0) {
                break;
            }
            swap(index, smallChild);
            index = smallChild;
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return contents.size() - 1;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contains(element)) {
            throw new IllegalArgumentException("Element already exists");
        }
        contents.add(element);
        indexMap.put(element, contents.size() - 1);
        bubbleUp(contents.size() - 1);
    }

    /* Returns and removes the smallest element in the MinHeap, or null if there are none. */
    public E removeMin() {
        if (size() == 0) {
            return null;
        }
        E removeItem = contents.get(1);
        if (size() == 1) {
            contents.remove(1);
            indexMap.remove(removeItem);
            return removeItem;
        }
        swap(1, contents.size() - 1);
        E removed = contents.remove(contents.size() - 1);
        indexMap.remove(removed);
        bubbleDown(1);
        return removeItem;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if (!indexMap.containsKey(element)) {
            throw new NoSuchElementException();
        }
        int index = indexMap.get(element);
        E oldElement = contents.get(index);
        contents.set(index, element);
        indexMap.put(element, index);
        if (element.compareTo(oldElement) < 0) {
            bubbleUp(index);
        }
        else if (element.compareTo(oldElement) > 0) {
            bubbleDown(index);
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        // OPTIONAL: OPTIMIZE THE SPEED OF THIS TO MAKE IT CONSTANT
        return indexMap.containsKey(element);
    }
}
