import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class SLListTest {

    @Test
    public void testSLListAdd() {
        SLList test1 = SLList.of(1, 3, 5); /* test1: {1, 3, 5} */
        SLList test2 = new SLList(); /* test2: {} */

        test1.add(1, 2); /* test1: {1, 2, 3, 5}*/
        test1.add(3, 4); /* test1: {1, 2, 3, 4, 5}*/
        assertWithMessage("test1 does not have a size of 5").that(test1.size()).isEqualTo(5);
        assertWithMessage("test1 does not have 3 at index 2 or 4 at index 3").that(test1.equals(SLList.of(1, 2, 3, 4, 5))).isTrue();

        test2.add(1, 1); /* test2: {1} */
        assertWithMessage("test2 does not contain 1").that(test2.equals(SLList.of(1))).isTrue();
        assertWithMessage("test2 does not have a size of 1").that(test2.size()).isEqualTo(1);

        test2.add(10, 10); /* test2: {1, 10} */
        assertWithMessage("test2 is incorrect after adding at an out-of-bounds index").that(test2.equals(SLList.of(1, 10))).isTrue();
        test1.add(0, 0); /* test1: {0, 1, 2, 3, 4, 5}*/
        assertWithMessage("test1 is incorrect after addition at the front").that(test1.equals(SLList.of(0, 1, 2, 3, 4, 5))).isTrue();
    }

    @Test
    public void testSLListReverse() {
        // TODO: Add tests
        SLList test1 = SLList.of(1, 2, 3, 4, 5);
        assertWithMessage("test1 does not reverse")
                .that(test1.equals(SLList.of(1,2,3,4,5))).isTrue();
        test1.reverse();
        assertWithMessage("test1 reverse successful")
                .that(test1.equals(SLList.of(5,4,3,2,1))).isTrue();
        //链表中只有一个元素
        SLList test2 = SLList.of(1);
        assertWithMessage("test2 does not reverse successful")
                .that(test2.equals(SLList.of(1))).isTrue();
        test2.reverse();
        assertWithMessage("test2 reverse successful")
                .that(test2.equals(SLList.of(1))).isTrue();
        //链表中不存在元素
        SLList test3 = SLList.of();
        assertWithMessage("test3 does not reverse")
                .that(test3.equals(SLList.of())).isTrue();
        test3.reverse();
        assertWithMessage("test3 reverse successful")
                .that(test3.equals(SLList.of())).isTrue();


    }
}
