import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B <T> implements Deque61B<T> {
    private LinkedListNode sentinel;
    private int size;
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
        for (int i = 0; i < size;i++) {
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
//        LinkedListNode l = sentinel;
//        while (l.next != sentinel) {
//            l = l.next;
//            size += 1;
//        }
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
        if (index == 0) {return node.item;}
        return getRecursiveHelp(node.next, index - 1);
    }
}
