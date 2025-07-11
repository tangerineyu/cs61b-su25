public class TriangleDrawer {
    public static void drawTriangle() {
        int m = 0;

        while (m < 10) {
            int n = 0;
            while (n < m ) {
                System.out.print("*");
                n++;
            }
            System.out.println(" ");
            m++;
        }
    }
    public static void main(String[]args) {
        drawTriangle();
    }
}
