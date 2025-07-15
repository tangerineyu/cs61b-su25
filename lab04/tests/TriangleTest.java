import org.junit.Rule;
import org.junit.Test;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.google.common.truth.Truth.assert_;

public abstract class TriangleTest {

    /** For autograding purposes; do not change this line. */
    abstract Triangle getNewTriangle();

    /* ***** TESTS ***** */

    // FIXME: Add additional tests for Triangle.java here that pass on a
    //  correct Triangle implementation and fail on buggy Triangle implementations.



    @Test
    public void sidesFormTriangle() {
        Triangle t = getNewTriangle();
        assertWithMessage("Side 3,4, 5 should form a valid triangle")
                .that(t.sidesFormTriangle(3, 4, 5)).isTrue();
        assertWithMessage("Side 1,2 ,3 should not form a triangle")
                .that(t.sidesFormTriangle(1, 2, 3)).isFalse();
        assertWithMessage("Side 1,2 ,4 should not form a triangle")
                .that(t.sidesFormTriangle(1, 2, 4)).isFalse();
        assertWithMessage("A triangle cannot have a side of jlength 0")
                .that(t.sidesFormTriangle(5, 5, 0)).isFalse();
    }

    @Test
    public void pointsFormTriangle() {
        Triangle t = getNewTriangle();
        assertWithMessage("point (0,0),(3,0),(0,4) should form a valid triangle")
                .that(t.pointsFormTriangle(0,0,3,0,0,4)).isTrue();
        assertWithMessage("Collinear points (1,1),(2,2）,(3，3) should not form a triangle")
                .that(t.pointsFormTriangle(1,1,2,2,3,3)).isFalse();
        assertWithMessage("having teo identical points (1,1) should not form a triangle")
                .that(t.pointsFormTriangle(1,1,1,1,2,2)).isFalse();
    }

    @Test
    public void triangleType() {
        Triangle t = getNewTriangle();
        assertWithMessage("a triangle with side 5,5,5 should be Equilateral")
                .that(t.triangleType(5,5,5)).isEqualTo("Equilateral");
        assertWithMessage("a triangle with side 3,3,5 should be Equilateral")
                .that(t.triangleType(3,3,5)).isEqualTo("Isosceles");
        assertWithMessage("a triangle with side 3,4,5 should be Isosceles")
                .that(t.triangleType(3,4,5)).isEqualTo("Scalene");
    }

    @Test
    public void squaredHypotenuse() {
        Triangle t = getNewTriangle();
        assertWithMessage("squared Hypotenuse for legs 3 and 4 should be 25")
                .that(t.squaredHypotenuse(3,4)).isEqualTo(25);
        assertWithMessage("squared Hypotenuse for legs 5 and 12 should be 13")
                .that(t.squaredHypotenuse(5,12)).isEqualTo(169);
        assertWithMessage("The squared hupotenuse for legs 7 and 0 should be 49")
                .that(t.squaredHypotenuse(7,0)).isEqualTo(49);
    }
}
