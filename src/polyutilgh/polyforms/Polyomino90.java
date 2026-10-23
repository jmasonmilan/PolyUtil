package polyutilgh.polyforms;


import java.math.BigInteger;
import java.util.ArrayList;
import polyutilgh.CoordPair;

import polyutilgh.*;


/**
 *
 * @author mason -
 * Polyomino with R90 symmetry
 */
public abstract class Polyomino90 extends PolyominoR {
    public Polyomino90(BigInteger inValue) {
        super(inValue);  
    }    
   
    public Polyomino90() {
        super();
    }
    // works if both are "about corner" or both "about centre empty"
    public Polyomino90 merge(Polyomino90 smaller) {
        int smallerWidth = smaller.getWidth();
        int delta = (maxX - smallerWidth) / 2;
        
        Polyomino90 step1 = (Polyomino90)(smaller.copy(delta, delta));
        for (CoordPair cp : array) {
            if (!step1.contains(cp)) {
                step1.insert(cp);
            }
        }
        step1.seal();
        return step1;
    }
    
    public boolean inRestrictedSet(CoordPair cp) {
        int x = cp.getX(), y = cp.getY();
        if (maxX == 1) {
            return (x >= 1 && y >= 1);
        }
        int half = (maxX + 1) / 2;
        int xHalf = half;
        if (maxX % 2 == 1)
            xHalf--;  
        return (x > xHalf && y > half);
    }
    public ArrayList<CoordPair> multiInsert(CoordPair ncp, int width) {
        ArrayList<CoordPair> ret = new ArrayList<>();
        ret.add(ncp);
        if (!contains(ncp))
                insert(ncp);
        ncp = nextCP(ncp, width);
        ret.add(ncp);
        if (!contains(ncp))
                insert(ncp);
        ncp = nextCP(ncp, width);
        ret.add(ncp);
        if (!contains(ncp))
                insert(ncp);
        ncp = nextCP(ncp, width);
        ret.add(ncp);
        if (!contains(ncp))
                insert(ncp);
        return ret;
        
    }
    CoordPair nextCP(CoordPair cp, int width) {
        int x = cp.getX(), y = cp.getY();
       
        x = y;
        y = width + 1 - cp.getX();
        return new CoordPair(x, y);        
    }    
    public PolySet genNew(int limit) {
        PolySet ps = new PolySet(size() + 4);
         
        for (int i = 0 ; i < this.array.size() ; i++) {

            CoordPair cp = this.array.get(i);
            if (!inRestrictedSet(cp))
                continue ;            
            tryOne(i, ps, 1, 0);
            tryOne(i, ps, -1, 0);
            tryOne(i, ps, 0, 1);
            tryOne(i, ps, 0, -1);
           
            
        }
        tryBridge(ps);
        if (!this.testMirror90())
            ((Polyomino90)this.reflectV()).tryBridge(ps);
        return ps;
    }   

    abstract boolean okToTry(int x, int y) ;
    boolean tryOne(int i, PolySet ps, int dx, int dy) {
        Polyomino90 p = this;
        int w = this.getWidth();
      
        CoordPair cp = this.array.get(i);
        int x1 = cp.getX(), y1 = cp.getY();

        x1 += dx;
        y1 += dy;
        if (!this.okToTry(x1, y1))
            return false;
        if (contains(x1, y1))
            return false;
        int x2 = y1, y2 = w - x1 + 1;
        int x3 = y2, y3 = w - y1 + 1;
        int x4 = y3, y4 = x1;
        if (y1 < 1 || y2 < 1 || y3 < 1 || y4 < 1) {
            p = (Polyomino90)(p.shiftUp(1));
            y1++;
            y2++;
            y3++;
            y4++;
            
        }
        if (x1 < 1 || x2 < 1 || x3 < 1 || x4 < 1) {
            p = (Polyomino90)(p.shiftRight(1));
            x1++;
            x2++;
            x3++;
            x4++;
            
        }
        BigInteger bi;
        bi = p.getRep().setBit(getBit(x1, y1));
        bi = bi.setBit(getBit(x2, y2));
        bi = bi.setBit(getBit(x3, y3));
        bi = bi.setBit(getBit(x4, y4));
        p = (Polyomino90)(maker(bi.toString()));
        int s1 = this.size(), s2 = p.size();
        if (s1 + 4 != s2) {
            int qq = 1 / 0;
        }
        ps.addUniques(p);
        //System.out.println(p.drawing());
        p.tryPop(ps);
        return true;
    }
    void tryPop(PolySet ps) {
        
        int w = this.getWidth();
        if (w < 4)
            return;
        if (this.contains(w, w))
            return;
        if (!this.contains(w, w - 1))
            return;
        if (!this.contains(w - 1, w))
            return;
        if (!contains(w - 1, w - 1))
            return;
        Polyomino90 p = (Polyomino90)(maker());
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (x == w - 1 && y == w - 1)
                p.array.add(new CoordPair(w, w));
            else if (x == w - 1 && y == 2)
                p.array.add(new CoordPair(w, 1));
            else if (x == 2 && y == w - 1)
                p.array.add(new CoordPair(1, w));
            else if (x == 2 && y == 2)
                p.array.add(new CoordPair(1, 1));
            else 
                p.array.add(cp);
        }
        p.seal();
        //System.out.println(p.drawing());
        if (p.intact())
            ps.addUniques(p);
    }
    void tryBridge(PolySet ps) {
        int endX = leftmostTop();
        int w = getWidth();
        if (endX < 3)
            return;
        int cx = endX - 1;
        for ( ; cx > 0 ; cx--) {
            if (contains(cx, w)) 
                break;
            if (!contains(cx, w - 1))
                break;
        }
        // ....O
        // .OOOO endX = 5, cx = 1
        if (endX - cx < 2)
            return;
        cx++; // cx = 2
        //String dbg1 = this.drawing();
        while (endX - cx >= 2) {
            Polyomino90 p = (Polyomino90)(maker());
            for (CoordPair cp : array) {
                int sx = cp.getX(), sy = cp.getY();
                if (sy == w - 1 && sx >= cx && sx < endX) {
                    if (sx == cx) {
                        p.array.add(new CoordPair(sx, w - 1));
                        p.array.add(new CoordPair(w - 1, w - sx + 1));
                        p.array.add(new CoordPair(w - sx + 1, 2));
                        p.array.add(new CoordPair(2, sx));

                    } 
                    p.array.add(new CoordPair(sx, w ));
                    p.array.add(new CoordPair(w , w - sx + 1));
                    p.array.add(new CoordPair(w - sx + 1, 1));
                    p.array.add(new CoordPair(1, sx));


                } else {
                    boolean flag = false;
                    for (int tmpX = cx; tmpX < endX; tmpX++) {
                        if (equivalent(sx, sy, tmpX, w - 1)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag)
                        p.array.add(cp); 
                }
            }
            p.seal();  
            if (this.size() == p.size()) {
                int qq = 1 / 0;
            }
            //String dbg = p.drawing();
            if (p.intact())
                ps.addUniques(p);
            cx++;
        }
    
    }
    boolean equivalent(int x1, int y1, int x2, int y2) {
        int w = getWidth();
        if (x1 == x2 && y1 == y2)
            return true;
        if (x1 == y2 && y1 == w - x2 + 1)
            return true;
        if (x1 == w - x2 + 1 && y1 == w - y2 + 1)
            return true;
        if (x1 == w - y2 + 1 && y1 == x2)
            return true;
        return false;
    }

    public abstract Polyomino90 getInnerRing();
    public Polyomino90 getInnerRingAroundHole(int x) {
        Propagator prop = new Propagator(this, 1, 0);
        if (prop.getColour(x, x) != 0) {
            int qq = 1 / 0;
        }
        prop.set(new CoordPair(x, x), 0, 2, true);
        prop.removeOnesAndTwos(2, 0, 4);
        Polyomino hole = prop.PolyominoOfColour(2);
        //String s1 = hole.drawing();
        Polyomino ringPoly = hole.makeBorderPoly(0);
        Loop90 ring = new Loop90(ringPoly.getRep());
        if (!ring.loop()) {
            Propagator ringProp = new Propagator(ring, -1, 0);            
            ringProp.outside(0, -2); // set outside edges and propagate    
            Loop90 newRing = new Loop90();
            for (CoordPair rcp : ring.array) {
                int rx = rcp.getX(), ry = rcp.getY();
                if (isBorder(ringProp, rx, ry))
                    newRing.insert(rcp);
            }
            newRing.seal();
            if (newRing.loop())
                return newRing;
            System.err.println(this.drawing());
            System.err.println(newRing.drawing());
            System.err.println(ring.drawing());
            System.err.println(this.size() + "");
            System.exit(1);
        }
        //String s2 = ring.drawing();
        return ring;
    }
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
    // only for "about corner"
    public int getWithinInnerSize() {
        int w = getWidth();
        int x = w / 2;        
        Propagator prop = new Propagator(this, 1, 0);
        if (prop.getColour(x, x) != 0) {
            int qq = 1 / 0;
        }
        prop.set(new CoordPair(x, x), 0, 2, true);
        return prop.removeOnesAndTwos(2, 0, 4);        
    }
    public String getUnique() {
        BigInteger rep1 = this.getRep();
        BigInteger rep5 = this.reflectH().getRep();

        if (rep1.compareTo(rep5) > 0)
            rep1 = rep5;
        
        return rep1.toString();
    }       
}
