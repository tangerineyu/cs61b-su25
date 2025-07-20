import org.junit.Test;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.common.truth.Truth.assertThat;


public class ListSetTest {

    @Test
    public void testBasics() {
        ListSet aSet = new ListSet();
        assertWithMessage("Size is not zero upon instantiation").that(aSet.size()).isEqualTo(0);
        for (int i = -50; i < 50; i += 2) {
            aSet.add(i);
            assertWithMessage("aSet should contain " + i).that(aSet.contains(i));
        }

        assertWithMessage("Size is not 50 after 50 calls to add").that(aSet.size()).isEqualTo(50);
        for (int i = -50; i < 50; i += 2) {
            aSet.remove(i);
            assertWithMessage("aSet should not contain " + i).that(!aSet.contains(i));
        }

        assertWithMessage("aSet is not empty after removing all elements").that(aSet.isEmpty());
        assertWithMessage("Size is not zero after removing all elements").that(aSet.size()).isEqualTo(0);
    }
    @Test
    public void testAdd() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.add(2);
        assertThat(aSet.size()).isEqualTo(2);
    }
    @Test
    public void testRemove() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.add(2);
        aSet.remove(2);
        assertThat(aSet.size()).isEqualTo(1);
        aSet.remove(1);
        assertThat(aSet.size()).isEqualTo(0);
        aSet.remove(1);
        assertThat(aSet.size()).isEqualTo(0);
    }
    @Test
    public void testContains() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.add(2);
        aSet.add(3);
        assertThat(aSet.contains(3)).isTrue();
        assertThat(aSet.contains(4)).isFalse();
        assertThat(aSet.contains(5)).isFalse();
    }
    @Test
    public void testSize() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.add(2);
        assertThat(aSet.size()).isEqualTo(2);
    }
    @Test
    public void testIsEmpty() {
        ListSet aSet = new ListSet();
        assertThat(aSet.isEmpty()).isTrue();
        aSet.add(1);
        assertThat(aSet.isEmpty()).isFalse();
        aSet.add(2);
        assertThat(aSet.isEmpty()).isFalse();
        aSet.remove(2);
        aSet.remove(1);
        assertThat(aSet.isEmpty()).isTrue();
    }
    @Test
    public void testToIntArray() {
        ListSet aSet = new ListSet();
        aSet.add(1);
        aSet.add(2);
        assertWithMessage("intToArray failed").that(aSet.toIntArray()).isEqualTo(new int[] {1, 2});
    }

}
