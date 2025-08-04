package deque;

import java.util.*;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int size;
    private T[] items;
    private int nextLast;
    private int nextFirst;
    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextLast = 0;
        nextFirst = 0;
    }
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int currentIndex;
        private int count;
        public ArrayDequeIterator() {
            this.currentIndex = Math.floorMod(nextFirst + 1, items.length);
            this.count = 0;
        }

        @Override
        public boolean hasNext() {
            return this.count < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more elements in the deque");
            }
            T returnItem = items[currentIndex];
            this.currentIndex = Math.floorMod(currentIndex + 1, items.length);
            this.count++;
            return returnItem;
        }
    }
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        nextLast = Math.floorMod(nextLast + 1, items.length);
        items[nextLast] = x;
        size++;
    }
    private void resize(int newSize) {
        T[] newItems = (T[]) new Object[newSize];
        int oldIndex = Math.floorMod(nextFirst + 1, items.length);
        for (int i = 0; i < size; i++) {
            newItems[i] = items[oldIndex];
            oldIndex = Math.floorMod(oldIndex + 1, items.length);
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int physicalindex = Math.floorMod(nextFirst + 1 + i, items.length);
            returnList.add(items[physicalindex]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T returnItem = items[nextFirst];
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        items[nextFirst] = null;
        size--;
        final int MIN_CAPACITY = 16;
        if (items.length > MIN_CAPACITY && size <= items.length / 4) {
            resize(items.length / 2);
        }
        return returnItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int removeIndex = Math.floorMod(nextLast - 1, items.length);
        T returnItem = items[removeIndex];
        nextLast = removeIndex;
        items[removeIndex] = null;
        size--;
        final int MIN_CAPACITY = 16;
        if (items.length > MIN_CAPACITY && size <= items.length / 4) {
            resize(items.length / 2);
        }
        return returnItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int physicalIndex = Math.floorMod(nextFirst + 1 + index, items.length);
        return items[physicalIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
    @Override
    public String toString() {
        List<String> elementStrings = new ArrayList<>();
        for (T item : this) {
            elementStrings.add(String.valueOf(item));
        }
        return "[" + String.join(", ", elementStrings) + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrayDeque61B)) {
            return false;
        }
        ArrayDeque61B<?> other = (ArrayDeque61B<?>) o;
        if (this.size != other.size) {
            return false;
        }
        Iterator<T> thisIterator = this.iterator();
        Iterator<?> otherIterator = other.iterator();
        while (thisIterator.hasNext()) {
            T thisItem = thisIterator.next();
            Object otherItem = oth
        }
    }


}
