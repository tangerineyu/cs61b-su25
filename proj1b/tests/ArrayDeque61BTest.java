import deque.ArrayDeque61B;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }
    @Test
    void addFirstTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addFirst(2);
        assertThat(deque.toList()).containsExactly(2, 1);

    }
    @Test
    void testResizeOnAddLast() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>(); // 初始容量 8
        // 1. 用 addFirst 填满，造成环绕
        for (int i = 0; i < 8; i++) {
            deque.addFirst(i); // Deque: [7, 6, 5, 4, 3, 2, 1, 0]
        }
        assertThat(deque.size()).isEqualTo(8);
        assertThat(List.of(7, 6, 5, 4, 3, 2, 1, 0)).isEqualTo(deque.toList());

        // 2. 调用 addLast 触发扩容
        deque.addLast(-1);

        // 3. 验证扩容后的状态
        assertThat(deque.size()).isEqualTo(9);
        List<Integer> expected = List.of(7, 6, 5, 4, 3, 2, 1, 0, -1);
        assertThat(deque.toList()).isEqualTo(expected);
    }

    @Test
    void getTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        assertThat(deque.get(0)).isEqualTo(1);
        deque.addLast(2);
        assertThat(deque.get(1)).isEqualTo(2);
        deque.addLast(3);
    }
    @Test
    void removeFirstTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.removeFirst();
        assertThat(deque.toList()).containsExactly(2);
        deque.addFirst(3);
        assertThat(deque.toList()).containsExactly(3, 2);
        deque.removeFirst();
        assertThat(deque.toList()).containsExactly(2);
    }
    @Test
    void removeLastTest() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        assertThat(deque.toList()).containsExactly(1, 2);
        deque.removeLast();
        assertThat(deque.toList()).containsExactly(1);
        deque.removeLast();
        deque.removeLast();
        assertThat(deque.toList()).containsExactly();
    }
    @Test
    void removeAllTest() {
        ArrayDeque61B deque = new ArrayDeque61B();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        assertThat(deque.toList()).containsExactly(1, 2, 3, 4);
        deque.removeFirst();
        assertThat(deque.toList()).containsExactly(1, 2, 3);
        deque.removeLast();
        assertThat(deque.toList()).containsExactly( 1, 2);
        deque.removeLast();
        assertThat(deque.toList()).containsExactly(1);
        deque.removeFirst();
        assertThat(deque.toList()).containsExactly();
    }
}
