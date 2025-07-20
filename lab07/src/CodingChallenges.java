import java.util.*;

public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        Arrays.sort(values);
        for (int i = 0; i < values.length; i++) {
            if (i != values[i]) {
                return i;
            }
        }
        return values.length;
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        HashMap<Character, Integer> charCount = new HashMap<>();
        for (char c : s1.toCharArray()) {
            charCount.put(c,charCount.getOrDefault(c,0) + 1);
        }
        for (char c : s2.toCharArray()) {
            Integer count = charCount.get(c);
            if (count == null || count == 0) {
                return false;
            }
            charCount.put(c,count - 1);
        }
        return true;
    }
}
