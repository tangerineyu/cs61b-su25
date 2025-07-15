public class MyTriangleTest extends TriangleTest{
    @Override
    Triangle getNewTriangle() {
        return new MyTriangle();
    }

}
