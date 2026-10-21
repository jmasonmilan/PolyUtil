/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;
import polyutilgh.*;
/**
 *
 * @author mason
 */
public class RectangleAboutPolyomino {
    boolean[][] array;
    int height, width;
    public RectangleAboutPolyomino(Polyomino p) {
        height = p.getHeight();
        width = p.getWidth();
        array = new boolean[width][height];
        for (int i = 0; i < p.size(); i++) {
            CoordPair cp = p.getCoords(i);
            int x = cp.getX(), y = cp.getY();
            array[x - 1][y - 1] = true;
        }
    }
    public int countMatch(RectangleAboutPolyomino other) {
        int ret = 0;
        for (int dx = 1 - other.width; dx < width; dx++) {
            for (int dy = 1 - other.height ; dy < height; dy++) {
                int m = countMatch(other, dx, dy);
                if (m > ret)
                    ret = m;
            }
        }
        return ret;
    }
    int countMatch(RectangleAboutPolyomino other, int dx , int dy) {
        int ret = 0;
        for (int x = 0; x < other.width; x++) {
            for (int y = 0; y < other.height; y++) {
                int tx = x + dx, ty = y + dy;
                if (tx < 0 || tx >= width || ty < 0 || ty >= height)
                    continue;
                if (other.array[x][y] && this.array[tx][ty])
                    ret++;
            }
        }
        return ret;
    }
}
