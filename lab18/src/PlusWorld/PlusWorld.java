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
        for (int i = -size; i <= size; i++) {
            tiles[x + i][y] = Tileset.WALL;
            tiles[x][y + i] = Tileset.WALL;
        }
    }
    public static void main(String[] args) {
        
    }
}
