package deque;

import java.util.*;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private LinkedListNode sentinel;
    private int size;

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61B.ArrayDequeIterator();
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
            Object otherItem = otherIterator.next();
            if (!Objects.equals(thisItem, otherItem)) {
                return false;
            }
        }
        return true;
    }

    public class LinkedListNode {
        private LinkedListNode pre;
        private T item;
        private LinkedListNode next;
        public LinkedListNode(T item, LinkedListNode pre, LinkedListNode next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }
    public LinkedListDeque61B() {
        sentinel = new LinkedListNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
    }
    @Override
    public void addFirst(T x) {
        LinkedListNode temp = sentinel.next;
        LinkedListNode newNode = new LinkedListNode(x, null, null);
        newNode.next = temp;
        newNode.pre = sentinel;
        sentinel.next = newNode;
        temp.pre = newNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        LinkedListNode temp = sentinel.pre;
        sentinel.pre = new LinkedListNode(x, temp, sentinel);
        temp.next = sentinel.pre;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<T>();
        LinkedListNode l = sentinel.next;
        for (int i = 0; i < size; i++) {
            returnList.add(l.item);
            l = l.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel && sentinel.pre == sentinel) {
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
        LinkedListNode removedNode = sentinel.next;
        T removedItem = removedNode.item;
        sentinel.next = removedNode.next;
        removedNode.next.pre = sentinel;
        size--;
        removedNode.pre = null;
        removedNode.next = null;
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        LinkedListNode removedNode = sentinel.pre;
        T removedItem = removedNode.item;
        sentinel.pre = removedNode.pre;
        removedNode.pre.next = sentinel;
        size--;
        removedNode.pre = null;
        removedNode.next = null;
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        LinkedListNode l = sentinel.next;
        for (int i = 0; i < index; i++) {
            l = l.next;
        }
        return l.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelp(sentinel.next, index);
    }
    private T getRecursiveHelp(LinkedListNode node, int index) {
        if (index == 0) return node.item;
        return getRecursiveHelp(node.next, index - 1);
    }
}
