package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    public static void addPlus(TETile[][] tiles, int x, int y, int size) {
        if (x - size < 0 || x + size >= WIDTH
        || y - size < 0 || y + size >= HEIGHT) {
            return;
        }
        for (int i = -size; i <= size; i++) {
            tiles[x + i][y] = Tileset.WALL;
            tiles[x][y + i] = Tileset.WALL;
        }
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int size = 3;
        int spacingX = size * 4;
        int spacingY = size * 4;
        for (int startX = size; startX < WIDTH; startX += spacingX) {
            for (int startY = size; startY < HEIGHT; startY += spacingY) {
                addPlus(world, startX, startY, size);
            }
        }
        ter.renderFrame(world);
    }
}
