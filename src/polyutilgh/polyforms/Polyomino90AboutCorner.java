package polyutilgh.polyforms;

import java.math.BigInteger;
import polyutilgh.*;

/**
 *
 * @author mason
 */
public class Polyomino90AboutCorner extends Polyomino90 {
    public Polyomino90AboutCorner(BigInteger inValue) {
        super(inValue);  
    }    
   
    public Polyomino90AboutCorner() {
        super();
    }      
    public Polyomino90 maker(String srep) {
        return new Polyomino90AboutCorner(new BigInteger(srep));
    }    
    
    public Polyomino90 maker() {
        return new Polyomino90AboutCorner();
    }  
    /*
    boolean inRestrictedSet(CoordPair cp) {
        int w = this.getWidth();
        int half = w / 2;        
       
        int x1 = cp.getX(), y1 = cp.getY();
        if (x1 <= half || y1 <= half)
            return false ;
        return true;
    }
*/
    boolean okToTry(int x, int y) {
        return true;
    }    
    public  Polyomino90 getInnerRing() {
        int w = getWidth();
        int x = w / 2;
        if (this.contains(x, x)) {
            Loop90 ring = new Loop90();
            ring.array.add(new CoordPair(x, x));
            ring.array.add(new CoordPair(x + 1, x));
            ring.array.add(new CoordPair(x, x + 1));
            ring.array.add(new CoordPair(x + 1, x + 1));
            ring.seal();
            return ring;
        }
            
        return getInnerRingAroundHole(x);
    }     
}
