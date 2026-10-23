package polyutilgh.polyforms;

import java.math.BigInteger;
import polyutilgh.*;

/**
 *
 * @author mason
 */
public class Polyomino90AboutCentreEmpty extends Polyomino90AboutCentre {
    public Polyomino90AboutCentreEmpty(BigInteger inValue) {
        super(inValue);  
    }    
   
    public Polyomino90AboutCentreEmpty() {
        super();
    }      
    public Polyomino90 maker(String srep) {
        return new Polyomino90AboutCentreEmpty(new BigInteger(srep));
    }    
    
    public Polyomino90 maker() {
        return new Polyomino90AboutCentreEmpty();
    }  
   
    boolean okToTry(int x, int y) {
        int w = this.getWidth();
        int half = (w + 1 ) / 2;   
        if (x == half && y == half)
            return false;
        return true;
    }    
    public  Polyomino90 getInnerRing() {
        int w = getWidth();
        return getInnerRingAroundHole((w + 1) / 2);
    }    
}
