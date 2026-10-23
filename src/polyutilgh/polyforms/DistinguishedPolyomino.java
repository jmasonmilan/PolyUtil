package polyutilgh.polyforms;

import java.math.BigInteger;
import polyutilgh.*;

/**
 *
 * @author mason
 */
public class DistinguishedPolyomino extends Polyomino {
    public DistinguishedPolyomino(BigInteger inValue) {
        super(inValue);  
    }     
    public int logicalSize() {
        int square = 0, vertex = 0;
        for (CoordPair cp : array) {
            if (isVertex(cp))
                vertex++;
            else
                square++;
        }
        return vertex + square / 4;
    }
    boolean isVertex(CoordPair cp) {
        int x = cp.getX(), y = cp.getY();
        return !contains(x, y + 1) && !contains(x, y - 1) && !contains(x + 1, y ) && !contains(x - 1, y ) ;
    }
    public int countVertices() {
        int square = 0, vertex = 0;
        for (CoordPair cp : array) {
            if (isVertex(cp))
                vertex++;
            else
                square++;
        }
        return vertex ;        
    }
}
