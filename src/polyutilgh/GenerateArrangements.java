/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh;
import java.util.*;
/**
 *
 * @author mason
 */
public class GenerateArrangements {
    public static int[][] generate(int size) {
        int arrayDim = factorial(size);
        int[][] ret = new int[arrayDim][size];
        BitSet s = new BitSet(size);
        fill(ret, 0, s, size, 0);
        return ret;
    }
    static void fill(int[][] ret, int startPos, BitSet s, int size, int horiz) {
        int width = factorial(size - 1);
        for (int i = 0; i < size; i++) {
            int v = getNthFreeBit(s, i);
            
            s.set(v);
            for (int j = 0; j < width; j++) {
                ret[startPos + width * i + j][horiz] = v;
                
            }
            if (size > 1)
                fill(ret, startPos + width * i, s, size - 1, horiz + 1);
            s.set(v, false);
        }
    }
    static int getNthFreeBit(BitSet s, int i) {
        for (int j = 0; j < s.size(); j++) {
            if (s.get(j))
                continue;
            if (i == 0)
                return j;
            i--;
        }
        return 1 / 0;
    }
    static int factorial(int in) {
        int ret = 1;
        for (int i = 1; i <= in; i++)
            ret *= i;
        return ret;
    }
    public static int[] reverse(int[] arrangement) {
        int[] ret = new int[arrangement.length];
        for (int i = 0; i < arrangement.length; i++)
            ret[arrangement[i]] = i;
        return ret;
    }
}
