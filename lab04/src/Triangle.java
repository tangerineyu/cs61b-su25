/** Abstract class for Triangle methods.
 * DO NOT EDIT.
 * @author Crystal Wang
 * */
abstract class Triangle {
     /** Given triangle side lengths side1, side2, and side3, return whether or not they could form a valid triangle
      * defined by the triangle inequality: any the sum of any two sides must be > the third side. */
     abstract boolean sidesFormTriangle(int side1, int side2, int side3) ;

     /** Given points (x1, y1), (x2, y2), and (x3, y3), return whether they could form a valid triangle in a 2-D plane. */
     abstract boolean pointsFormTriangle(int x1, int y1, int x2, int y2, int x3, int y3);

     /** Given triangle side lengths side1, side2, and side3, return whether the triangle is
      * Scalene (all sides are different lengths), Isosceles (two sides are different lengths), or Equilateral (all sides are the same length).
      * You may assume that the side lengths actually form a triangle together (no need to check first). */
     abstract String triangleType(int side1, int side2, int side3);

     /** Given triangle leg lengths side1 and side2, return the squared hypotenuse of the triangle according to the formula
      * squaredHypotenuse = side1^2 + side2^2 */
     abstract int squaredHypotenuse(int side1, int side2);
}
class MyTriangle extends Triangle {

     @Override
     boolean sidesFormTriangle(int side1, int side2, int side3) {
          if (side1 <= 0 || side2 <= 0 || side3 <= 0) {
               return false;
          }

          return (side1 + side2 > side3)
                  &&(side1 + side3 > side2)&&(side2 + side3 > side1);
     }

     @Override
     boolean pointsFormTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
          int areaTWO = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
          return areaTWO != 0 ;
     }

     @Override
     String triangleType(int side1, int side2, int side3) {
          if (side1 == side2 && side2 == side3) {
               return "Equilateral";
          } else if (side1 == side2 || side1 == side3 || side2 == side3) {
               return "Isosceles";
          } else {
               return "Scalene";
          }
     }

     @Override
     int squaredHypotenuse(int side1, int side2) {
          return side1 * side1 + side2 * side2;
     }
}