package polyutilgh.polyforms;

import java.math.BigInteger;
import polyutilgh.*;

/**
 *
 * @author mason
 */
public class Polyomino90AboutCentreFull extends Polyomino90AboutCentre {
    public Polyomino90AboutCentreFull(BigInteger inValue) {
        super(inValue);  
    }    
   
    public Polyomino90AboutCentreFull() {
        super();
    }      
    public Polyomino90 maker(String srep) {
        return new Polyomino90AboutCentreFull(new BigInteger(srep));
    }    
    
    public Polyomino90 maker() {
        return new Polyomino90AboutCentreFull();
    }  

    boolean okToTry(int x, int y) {
        
        return true;
    }    
    public  Polyomino90 getInnerRing() {
        Polyomino90 ring = new Loop90();
        int w = getWidth();
        int x = (w + 1) / 2;
        ring.array.add(new CoordPair(x, x));
        ring.seal();
        return ring;
    }
    public boolean armfull() {
        if (size() == 1)
            return true;
        int x = (maxX + 1) / 2;
        int y = x;
        int nx = x, ny = y + 1;
        CoordPair cp = new CoordPair(x, y), ncp = new CoordPair(nx, ny);
        for (int i = 0 ; i <= size() / 4; i++) {
            int n = countNear(ncp);
            if (n == 1)
                return true;
            if (n > 2)
                return false;
            CoordPair tcp = findNextCP(cp, ncp);
            cp = ncp;
            ncp = tcp;
        }
        return false;
    }

    CoordPair findNextCP(CoordPair cpOld, CoordPair cpCurr) {
        CoordPair cpNew = tryCP(cpCurr, 1, 0, cpOld);
        if (cpNew != null)
            return cpNew;
        cpNew = tryCP(cpCurr, 0, 1, cpOld);
        if (cpNew != null)
            return cpNew;
        cpNew = tryCP(cpCurr, -1, 0, cpOld);
        if (cpNew != null)
            return cpNew;
        cpNew = tryCP(cpCurr, 0, -1, cpOld);
        if (cpNew != null)
            return cpNew;
        int qq = 1 / 0;
        return null;
    }
  
}
