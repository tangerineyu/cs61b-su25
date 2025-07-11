public class TriangleDrawer2 {
    public static void drawTriangle() {
        for (int i = 0;i < 10;i ++) {
            for (int j = 0;j < i;j ++) {
                System.out.print('*');
            }
            System.out.println();
        }

    }
    public static void main(String[] args) {
        drawTriangle();
    }
}
