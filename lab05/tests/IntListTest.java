import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static com.google.common.truth.Truth.assertWithMessage;

/** A suite of tests for IntList. */

public class IntListTest {

    /**
     * Example test that verifies correctness of the IntList.of static method.
     * The main point of this is to convince you that assertEquals knows how to
     * handle IntLists just fine because we implemented IntList.equals.
     */
    @Test
    public void testOf() {
        IntList test = IntList.of(1, 2, 3, 4, 5);
        assertWithMessage("Created list is null").that(test).isNotNull();
        assertWithMessage("First item in the list was not 1").that(test.item).isEqualTo(1);
        assertWithMessage("Second item in the list was not 2").that(test.next.item).isEqualTo(2);
        assertWithMessage("Third item in the list was not 3").that(test.next.next.item).isEqualTo(3);
        assertWithMessage("Fourth item in the list was not 4").that(test.next.next.next.item).isEqualTo(4);
        assertWithMessage("Fifth item in the list was not 5").that(test.next.next.next.next.item).isEqualTo(5);
        assertWithMessage("Null expected, but instead found another node").that(test.next.next.next.next.next).isNull();

        IntList empty = IntList.of();
        assertWithMessage("Empty list should be null!").that(empty).isNull();

        IntList single = IntList.of(7);
        assertWithMessage("Single list should not be null!").that(single).isNotNull();
        assertWithMessage("Single list should start with 7").that(single.item).isEqualTo(7);
        assertWithMessage("Single list should only have a single node!").that(single.next).isNull();
    }

    @Test
    public void testGet() {
        IntList test = IntList.of(1, 2, 3, 4, 5);
        assertWithMessage("first item should be 1").that(test.get(0)).isEqualTo(1);
        assertWithMessage("second item should be 2").that(test.get(1)).isEqualTo(2);
        assertWithMessage("third item should be 3").that(test.get(2)).isEqualTo(3);
        assertWithMessage("fourth item should be 4").that(test.get(3)).isEqualTo(4);
        assertWithMessage("fifth item should be 5").that(test.get(4)).isEqualTo(5);
        try {
            test.get(5);
            assertWithMessage("Should throw IllegalArgumentException").fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            assertWithMessage("Should throw IllegalArgumentException").fail();
        }

        try {
            test.get(-1);
            assertWithMessage("Should throw IllegalArgumentException for negative indices").fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            assertWithMessage("Should throw IllegalArgumentException for negative indices").fail();
        }

        IntList single = IntList.of(0);
        assertWithMessage("first item should be 5").that(test.get(4)).isEqualTo(5);
        try {
            single.get(1);
            assertWithMessage("Should throw IllegalArgumentException").fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            assertWithMessage("Should throw IllegalArgumentException").fail();
        }
    }

    @Test
    public void testToString() {
        assertWithMessage("toString does not work on singleton").that(IntList.of(1).toString()).isEqualTo("1");
        assertWithMessage("toString does not work on smaller list").that(IntList.of(2, 3, 4).toString()).isEqualTo("2 3 4");
        IntList test = IntList.of(1, 2, 3, 4, 5);
        assertWithMessage("toString does not work on test").that(test.toString()).isEqualTo("1 2 3 4 5");
    }

    @Test
    public void testEquals() {
        IntList a = IntList.of(1, 2, 3, 4, 5);
        IntList b = IntList.of(1, 2, 3, 4, 5);
        assertWithMessage("List should equal itself").that(a.equals(a)).isTrue();
        assertWithMessage("List should equal itself").that(b.equals(b)).isTrue();
        assertWithMessage("A should equal B").that(a.equals(b)).isTrue();
        assertWithMessage("B should equal A").that(b.equals(a)).isTrue();

        assertWithMessage("A should not equal a generic Object").that(a.equals(new Object())).isFalse();
        assertWithMessage("B should not equal a primitive").that(b.equals(242)).isFalse();

        assertWithMessage("A should not equal this smaller list").that(a.equals(IntList.of(1, 2, 3, 4))).isFalse();
        assertWithMessage("A should not equal this equal-length but different list").that(a.equals(IntList.of(1, 2, 3, 4, 6))).isFalse();
    }

    @Test
    public void testAdd() {
        IntList a = IntList.of(1, 2, 3);
        assertWithMessage("a does not equal (1, 2, 3)").that(a.equals(IntList.of(1, 2, 3))).isTrue();
        a.add(4);
        assertWithMessage("a does not equal (1, 2, 3, 4)").that(a.equals(IntList.of(1, 2, 3, 4))).isTrue();
        a.add(5);
        assertWithMessage("a does not equal (1, 2, 3, 4, 5)").that(a.equals(IntList.of(1, 2, 3, 4, 5))).isTrue();

        IntList single = IntList.of(1);
        assertWithMessage("single does not equal (1)").that(single.equals(IntList.of(1))).isTrue();
        single.add(2);
        assertWithMessage("single does not equal (1, 2)").that(single.equals(IntList.of(1, 2))).isTrue();
    }

    @Test
    public void testSmallest() {
        assertWithMessage("Smallest of list is 6").that(IntList.of(63, 6, 6, 74, 7, 8, 52, 33, 43, 6, 6, 32).smallest()).isEqualTo(6);
        assertWithMessage("Smallest of singleton is element").that(9).isEqualTo(IntList.of(9).smallest());
        assertWithMessage("Smallest of same element list is element").that(9).isEqualTo(IntList.of(9, 9, 9, 9, 9, 9, 9, 9, 9, 9).smallest());
        assertWithMessage("Smallest of 10 to 1 is 1").that(1).isEqualTo(IntList.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1).smallest());
    }

    @Test
    public void testSquaredSum() {
        assertWithMessage("Squared sum of (1, 2, 3) should be 14").that(IntList.of(1, 2, 3).squaredSum()).isEqualTo(14);

        assertWithMessage("Squared sum of (1) should be 1").that(IntList.of(1).squaredSum()).isEqualTo(1);

        assertWithMessage("Squared sum of (1, 2) should be 5").that(IntList.of(1, 2).squaredSum()).isEqualTo(5);

        assertWithMessage("Squared sum of (1, 1) should be 2").that(IntList.of(1, 1).squaredSum()).isEqualTo(2);

        assertWithMessage("Squared sum of (3, 3) should be 18").that(IntList.of(3, 3).squaredSum()).isEqualTo(18);
    }

    @Test
    public void testDSquareList() {
        IntList L = IntList.of(1, 2, 3);
        assertWithMessage("Initial list should stay the same").that(L).isEqualTo(IntList.of(1, 2, 3));

        IntList.dSquareList(L);

        assertWithMessage("After dSquareList, list should be (1, 4, 9)").that(L).isEqualTo(IntList.of(1, 4, 9));
    }

    /**
     * Do not use the new keyword in your tests. You can create
     * lists using the handy IntList.of method.
     *
     * Make sure to include test cases involving lists of various sizes
     * on both sides of the operation. That includes the empty of, which
     * can be instantiated, for example, with
     * IntList empty = IntList.of().
     *
     * Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     * Anything can happen to A.
     */

    @Test
    public void testCatenate() {
        IntList a = IntList.of(1, 2, 3);
        IntList b = IntList.of(4, 5, 6);
        IntList c = IntList.catenate(a, b);
        assertWithMessage("catenated list should stay the same")
                .that(a).isEqualTo(IntList.of(1, 2, 3));
        assertWithMessage("catenated list should stay the same")
                .that(b).isEqualTo(IntList.of(4, 5, 6));
        assertWithMessage("Catenate list (1,2,3) and (4,5,6)")
                .that(IntList.catenate(a, b)).isEqualTo(IntList.of(1, 2, 3, 4, 5, 6));
        assertWithMessage("Catenate list null and (4,5,6)")
                .that(IntList.catenate(null,b)).isEqualTo(IntList.of(4,5,6));
        assertWithMessage("Catenate list (1,2,3) and null")
                .that(IntList.catenate(a,null)).isEqualTo(IntList.of(1,2,3));
        assertWithMessage("Catenate null and null")
                .that(IntList.catenate(null, null)).isEqualTo(null);
    }

    @Test
    public void testDCatenate() {
        IntList a = IntList.of(1, 2, 3);
        IntList b = IntList.of(4, 5, 6);
        IntList expected1 = IntList.of(1, 2, 3, 4, 5, 6);

        IntList result = IntList.dcatenate(a, b);

        // 验证拼接结果是否正确
        assertWithMessage("拼接 (1,2,3) 和 (4,5,6) 的结果不正确")
                .that(result).isEqualTo(expected1);

        // 关键验证：检查 A 是否被“破坏性”地修改了
        assertWithMessage("原始链表 a 应该被修改为拼接后的长链表")
                .that(a).isEqualTo(expected1);

        // 关键验证：检查返回值 result 和 a 是否是同一个对象
        assertWithMessage("返回值应该和第一个参数 a 是同一个对象实例")
                .that(result).isSameInstanceAs(a);

        // 验证 B 链表保持不变
        assertWithMessage("链表 b 不应该被修改")
                .that(b).isEqualTo(IntList.of(4, 5, 6));

        // --- 测试 2: B 为 null 的情况 ---
        IntList a2 = IntList.of(1, 2, 3);
        IntList result2 = IntList.dcatenate(a2, null);
        assertWithMessage("拼接 (1,2,3) 和 null 的结果不正确")
                .that(result2).isEqualTo(IntList.of(1, 2, 3));
        assertWithMessage("当 B 为 null 时，A 不应该被改变")
                .that(a2).isEqualTo(IntList.of(1, 2, 3));
        assertWithMessage("当 B 为 null 时，返回值应和 A 是同一个对象")
                .that(result2).isSameInstanceAs(a2);


        // --- 测试 3: A 为 null 的情况 ---
        IntList b3 = IntList.of(4, 5, 6);
        IntList result3 = IntList.dcatenate(null, b3);
        assertWithMessage("拼接 null 和 (4,5,6) 的结果应为 (4,5,6)")
                .that(result3).isEqualTo(IntList.of(4, 5, 6));
        assertWithMessage("当 A 为 null 时，返回值应和 B 是同一个对象")
                .that(result3).isSameInstanceAs(b3);


        // --- 测试 4: A 和 B 都为 null 的情况 ---
        IntList result4 = IntList.dcatenate(null, null);
        assertWithMessage("拼接 null 和 null 的结果应为 null")
                .that(result4).isNull();
    }
}
