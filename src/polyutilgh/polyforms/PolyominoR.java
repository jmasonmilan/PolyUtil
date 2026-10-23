package polyutilgh.polyforms;


import java.math.BigInteger;
import java.util.ArrayList;
import polyutilgh.CoordPair;

import polyutilgh.*;


/**
 *
 * @author mason -
 * Polyomino with rotational symmetry
 */
public abstract class PolyominoR extends Polyomino {
    Chain sequenceOfSquares;
    boolean enriched;
    public PolyominoR(BigInteger inValue) {
        super(inValue);  
    }    
   
    public PolyominoR() {
        super();
    }
    public boolean isALoop() {
        return false;
    }

    abstract boolean equivalent(int x1, int y1, int x2, int y2);
    public void enrich() {
        if (!this.isALoop()) {
            int qq = 1 /0;
        }
        if (enriched)
            return;
        sequenceOfSquares = new Chain(this);
        enriched = true;
    }    
    public BigInteger calcBorder() {
        if (!this.isALoop()) {
            int qq = 1 / 0;
        }
        enrich();
        String rets = "";
        CoordPair cp = array.get(0);
        CoordPair first = cp;
        while (true) {
            CoordPair ncp = sequenceOfSquares.getNext(cp);
            int cx = cp.getX(), cy = cp.getY(), nx = ncp.getX(), ny = ncp.getY();
            if (ny == cy + 1)
                rets += "1";
            else if (nx == cx + 1)
                rets += "2";
            else if (ny == cy - 1)
                rets += "3";
            else
                rets += "4";
            cp = ncp;
            if (cp.equals(first))
                    break;
        }
        return new BigInteger(rets);
    }        
    abstract public boolean inRestrictedSet(CoordPair cp);
    //abstract public ArrayList<CoordPair>  multiInsert(polyutil.CoordPair cp,int w) ;
    boolean isBorder(Propagator prop, int x, int y) {
        if (prop.getOutsideColour(x + 1, y + 1) == -2) return true;
        if (prop.getOutsideColour(x + 1, y ) == -2) return true;
        if (prop.getOutsideColour(x + 1, y - 1) == -2) return true;
        if (prop.getOutsideColour(x - 1, y + 1) == -2) return true;
        if (prop.getOutsideColour(x - 1, y ) == -2) return true;
        if (prop.getOutsideColour(x - 1, y - 1) == -2) return true;
        if (prop.getOutsideColour(x , y + 1) == -2) return true;
        if (prop.getOutsideColour(x , y - 1) == -2) return true;
        return false;
    }
    int getSegmentLength(CoordPair current, int dx, int dy) {
        int ret = 1;
        while (true) {
            int cx = current.getX(), cy = current.getY();
            CoordPair ncp = sequenceOfSquares.getNext(current);
            int nx = ncp.getX(), ny = ncp.getY();
            if (cx + dx != nx || cy + dy != ny)
                return ret;
            ret++;
            current = ncp;
        }
    }   
    public abstract PolyominoR getInnerRing();
}
