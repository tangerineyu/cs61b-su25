package game2048logic;

import game2048rendering.Side;
//import static game2048logic.MatrixUtils.rotateLeft;
//import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return minR.
     */

    private static boolean []merged = new boolean[4];
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        int currentR = r;
        while (currentR > minR){
            if (board[currentR - 1][c] == 0) {
                board[currentR - 1][c] = board[currentR][c];
                board[currentR][c] = 0;
                currentR--;
            }
                //发生合并时，返回发生合并的行号 + 1
            else if (board[currentR][c] == board[currentR - 1][c] && merged[currentR - 1] == false) {
                board[currentR - 1][c] *= 2;
                board[currentR][c] = 0;
                merged [currentR - 1] = true;
                return currentR - 1 + 1;
            }
            //没有移动，并且没有合并，返回minR
            else {
                return minR;
            }
        }
        /*if (merged[r - 1] == false) {
            return minR;
        }*/
        return currentR;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        merged = new boolean[4];
        for (int i = 1;i < 4;i ++) {
            moveTileUpAsFarAsPossible(board, i, c, 0);

        }

    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        for (int j = 0 ; j < 4; j ++) {
            tiltColumn(board, j);
        }
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */

    public static int[][] rotateLeft(int[][] board) {
        int[][] rotatedBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotatedBoard[3 - j][i] = board[i][j];
            }
        }
        return rotatedBoard;
    }
    public static int[][] rotateRight(int[][] board) {
        int[][] rotatedBoard = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rotatedBoard[j][3 - i] = board[i][j];
            }
        }
        return rotatedBoard;
    }
    public static void copyBoard(int[][] board, int[][] newBoard) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
    }
    //一开始是直接在board上做修改，以东方向为例，最开始的思路就是左转，上升，右转，
    // 但是这样带来的后果就是东西南这三个方向，不会发生任何改变，因为现在board上旋转，得到的是一个新数组，
    //之后都在新数组上操作，所以board最后不会有任何改变
    //解决方法就是在方法内部创建一个新数组，在新数组上作出修改，然后复制到board上
    public static void tilt(int[][] board, Side side) {
        if (side == Side.NORTH) {
            tiltUp(board);
        } else if (side == Side.EAST) {
            int[][]rotatedBoard = rotateLeft(board);
            //board = rotateLeft(board);
            tiltUp(rotatedBoard);
            rotatedBoard = rotateRight(rotatedBoard);
            copyBoard(rotatedBoard, board);
        } else if (side == Side.SOUTH) {
            int[][]rotatedBoard = rotateRight(board);
            //board = rotateRight(board);
            rotatedBoard = rotateRight(rotatedBoard);
            tiltUp(rotatedBoard);
            rotatedBoard = rotateLeft(rotatedBoard);
            rotatedBoard = rotateLeft(rotatedBoard);
            copyBoard(rotatedBoard, board);
        } else if (side == Side.WEST) {
            int[][]rotatedBoard = rotateRight(board);
            tiltUp(rotatedBoard);
            rotatedBoard = rotateLeft(rotatedBoard);
            copyBoard(rotatedBoard, board);
        } else {
            System.out.println("Invalid side specified");
        }
    }
}
