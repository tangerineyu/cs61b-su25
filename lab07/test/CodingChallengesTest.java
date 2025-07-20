import org.junit.Test;

import static com.google.common.truth.Truth.assertWithMessage;

public class CodingChallengesTest {

    @Test
    public void testMissingNumber() {
        int[] values = new int[]{0,1,2,3,4,5,6,7,8,9,10};
        assertWithMessage("Missing number")
                .that(CodingChallenges.missingNumber(values)).isEqualTo(11);
        int[] values2 = new int[]{0,1,2,3,4,6,7,8,9};
        assertWithMessage("Missing number")
            .that(CodingChallenges.missingNumber(values2)).isEqualTo(5);
    }

    @Test
    public void testIsPermutation() {
        assertWithMessage("isPermutation含重复字符的测试失败")
                .that(CodingChallenges.isPermutation("abc", "cba")).isTrue();
        assertWithMessage("isPermutation两个空字符串应为互排列")
                .that(CodingChallenges.isPermutation(" ", " ")).isTrue();
        assertWithMessage("isPermutation")
                .that(CodingChallenges.isPermutation("hello", "helo")).isFalse();
    }
}
