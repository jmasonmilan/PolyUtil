package polyutilgh.polyforms;

import java.math.BigInteger;
import java.util.ArrayList;
import polyutilgh.Chain;
import polyutilgh.CoordPair;
import polyutilgh.InclusionListF;
import polyutilgh.PolySet;

/**
 *
 * @author mason
 */
public class PolyhexLoop extends Polyhex {
    Chain sequenceOfSquares;
    boolean enriched;    
    public PolyhexLoop(BigInteger inValue) {
       
        super(inValue);
        
       
    }    
   
    public PolyhexLoop() {
        super();
    }   
    public PolyhexLoop maker(String srep) {
        return new PolyhexLoop(new BigInteger(srep));
    }      
    public PolyhexLoop maker() {
        return new PolyhexLoop();
    }     
    public PolySet genNew(int limit) {
        
        
        
        
        PolySet ps = new PolySet();

        tryOut(ps, 0);
        PolyhexLoop rot = this;
        for (int i = 0; i < 5; i++) {
            rot = (PolyhexLoop)(rot.rotate60());
            rot.tryOut(ps, i + 1);            
        }

       
        
        return ps;
    }  
    void tryOut(PolySet ps, int i) {
        //System.err.println(i + ":\r\n" + this.drawing());
        if (i == 4) {
            int dbg = 1;
        }
        tryOut1to3(ps);
        tryOut1to2(ps);
    }
        /*
    
           /o\
         /-\o/-\
         \-/ \-/-\
               \-/
    
    destined to become
    
           /n\
         /n\n/n\
         \n/o\n/
         /-\o/-\
         \-/ \-/-\
               \-/
    
    but we can pop on the right
    
           /n\
         /n\n/n\
         \n/o\n/n\
         /-\o/o\n/
         \-/ \o/-\
               \-/
    */
    void tryOut1to3(PolySet ps) {
        this.enrich(); 
        for (int i = 0; i < size(); i++) {
            CoordPair c = this.getCoords(i);
            CoordPair p = this.sequenceOfSquares.getPrevious(i);
            CoordPair n = this.sequenceOfSquares.getNext(i);
            if (p.getX() == c.getX() - 1 && p.getY() == c.getY() - 1 && n.getX() == c.getX() + 1 && n.getY() == c.getY() - 1) {
                CoordPair[] list = new CoordPair[3];
                list[0] = new CoordPair(c.getX() - 1, c.getY() + 1);
                list[1] = new CoordPair(c.getX() + 1, c.getY() + 1);
                list[2] = new CoordPair(c.getX(), c.getY() + 2 );
                CoordPair[] cancel = {c};
                CoordPair[] freeList = new CoordPair[7];
                freeList[0] = new CoordPair(c.getX() - 2, c.getY() );
                freeList[1] = new CoordPair(c.getX() - 2, c.getY() + 2);
                freeList[2] = new CoordPair(c.getX() - 1, c.getY() + 3);
                freeList[3] = new CoordPair(c.getX() , c.getY() + 4);
                freeList[4] = new CoordPair(c.getX() + 1, c.getY() + 3);
                freeList[5] = new CoordPair(c.getX() + 2, c.getY() + 2);
                freeList[6] = new CoordPair(c.getX() + 2, c.getY());
                if (tryOut(ps, cancel, list , freeList)) {
                    int left = countDown(c, -1);
                    int right = countDown(c, 1);
                    left = limit(c, left, -1);
                    right = limit(c, right, 1);
                    for (int li = 0; li <= left; li++) 
                        for (int ri = 0; ri <= right; ri++) {
                            if (ri == 0 && li == 0)
                                continue;
                            int tot = li + ri;
                            cancel = new CoordPair[tot + 1];
                            cancel[0] = c;
                            freeList = new CoordPair[0];
                            list = new CoordPair[3 + tot];
                            list[0] = new CoordPair(c.getX() - 1, c.getY() + 1);
                            list[1] = new CoordPair(c.getX() + 1, c.getY() + 1);
                            list[2] = new CoordPair(c.getX(), c.getY() + 2 );      
                            for (int tl = 1; tl <= li; tl++) {
                                cancel[tl] = new CoordPair(c.getX() - tl, c.getY() - tl);
                                list[2 + tl] = new CoordPair(c.getX() - tl - 1, c.getY() - tl + 1);
                            }
                            for (int tr = 1; tr <= ri; tr++) {
                                cancel[tr + li] = new CoordPair(c.getX() + tr, c.getY() - tr);
                                list[2 + tr + li] = new CoordPair(c.getX() + tr + 1, c.getY() - tr + 1);
                            }
                            if (!tryOut(ps, cancel, list , freeList)) {
                                int qq = 1 / 0;
                            }
                        }
                   
                }
            }
        }        
    }
    /*
    
           /-\
         /-\-/-\
         \-/ \-/-\
               \-/
    
    With input the top hexagon, and delta = -1, should return 1; with delta = 1, returns 2
    
    */
    int countDown(CoordPair cp, int delta) {
        int ret = 0;
        int x = cp.getX(), y = cp.getY();
        while (true) {
            x += delta;
            y--;
            if (contains(x, y))
                ret++;
            else
                break;
        }
        return ret;
    }
    /*
    
           /-\
         /-\-/-\
         \-/ \-/-\
               \-/
    
    destined to become
    
           /-\
         /-\-/-\
         \-/ \-/a
         /-\ /-\a
         \-/ \-/d\
               \d/
    
Can we pop thus? We need to be free the following: b and c
    
           /-\
         /-\-/-\  b
         \-/o\-/a\b
         /-\o  \a/c
         \-/   /d\c
               \d/    
    
    With input the top hexagon, delta = 1, max = 2, the most we can return is 1
    
    */    
    int limit(CoordPair cp, int max, int delta) {
        int ret = 0;
        for (int i = 1; i < max; i++) {
            if (contains(cp.getX() + delta * i + 2 * delta, cp.getY() - i ) || contains(cp.getX() + delta * i + 2 * delta, cp.getY() - i + 2))
                return ret;
            else
                ret++;
        }
        return ret;
    }
    void tryOut1to2(PolySet ps) {
        this.enrich(); 
        for (int i = 0; i < size(); i++) {
            CoordPair c = this.getCoords(i);
            CoordPair p = this.sequenceOfSquares.getPrevious(i);
            CoordPair n = this.sequenceOfSquares.getNext(i);
            if (p.getX() == c.getX() && p.getY() == c.getY() + 2 && n.getX() == c.getX()  && n.getY() == c.getY() - 2) {
                CoordPair[] list = new CoordPair[2];
                list[0] = new CoordPair(c.getX() + 1, c.getY() - 1);
                list[1] = new CoordPair(c.getX() + 1, c.getY() + 1);
                CoordPair[] cancel = {c};
                CoordPair[] freeList = new CoordPair[5];
                freeList[0] = new CoordPair(c.getX() + 1, c.getY() + 3);
                freeList[1] = new CoordPair(c.getX() + 2, c.getY() + 2);
                freeList[2] = new CoordPair(c.getX() + 2, c.getY()    );
                freeList[3] = new CoordPair(c.getX() + 2, c.getY() - 2);
                freeList[4] = new CoordPair(c.getX() + 1, c.getY() - 3);
                if (tryOut(ps, cancel, list , freeList)) {
                    for (int num = 2; ; num++) {
                        CoordPair lower = new CoordPair(c.getX(), c.getY() - 2 * num);
                        if (contains(lower)) {
                            
                            CoordPair[] cancel2 = new CoordPair[num];
                            for (int j = 0; j < num; j++)
                                cancel2[j] = new CoordPair(c.getX(), c.getY() - 2 * j);
                            CoordPair[] freeList2 = new CoordPair[2];
                            freeList2[0] = new CoordPair(c.getX() + 1, c.getY() - 1 - 2 * num);
                            freeList2[1] = new CoordPair(c.getX() + 2, c.getY() - 2 * num);
                            CoordPair[] list2 = new CoordPair[num + 1];
                            list2[0] = new CoordPair(c.getX() + 1, c.getY() + 1);
                            for (int j = 1; j <= num; j++)
                                list2[j] = new CoordPair(c.getX() + 1, c.getY() + 1 - 2 * j);
                            
                            if (!tryOut(ps, cancel2, list2 , freeList2))
                                break;
                        } else
                            break;
                    }                    
                }
            }
        }        
    }
    boolean tryOut(PolySet ps, CoordPair[] cancel, CoordPair[] list, CoordPair[] freeList) {
        int delta = calcDelta(list);
        
        if (!verifFree(freeList))
            return false;
        PolyhexLoop np = new PolyhexLoop();
        for (CoordPair cp : array) {
            if (!inList( cp, cancel))
                np.insert(cp.getX() + delta, cp.getY() + delta);
        }
        
        for (CoordPair cp : list) {
            np.insert(cp.getX() + delta, cp.getY() + delta);
        }
        np.adjustToSextantLR();
        np.seal();
        ps.addUniques(np); 
        //System.err.println("son:\r\n" + np.drawing());
        if (!np.loop()) {
            System.err.println(this.rep.toString() + "\r\n" + this.drawing());
            System.err.println(np.drawing());
            int qq = 1 / 0;
        }
        return true;
    }
    boolean inList(CoordPair cp, CoordPair[] list) {
        for (CoordPair xcp : list)
            if (cp.equals(xcp))
                return true;
        return false;
    }
    int calcDelta(CoordPair[] list) {
        int ret = 0;
        for (CoordPair cp : list) {
            // 0 -> 1
            // -1 -> 2
            if (cp.getY() == -1)
                ret = 2;
            else if (cp.getY() == 0 && ret == 0)
                ret = 1;
        }
        return ret;
    }
    boolean verifFree(CoordPair[] list) {
        if (list == null)
            return true;
        for (CoordPair cp : list) {
            if (contains(cp))
                return false;
        }
        return true;
    }
    public void enrich() {
        
        if (enriched)
            return;
        sequenceOfSquares = new Chain(this);
        enriched = true;
    }   

    boolean within(int x, int y) {
        boolean ret = false;
        for (int ty = getMinY() - 1; ty <= y; ty++) {
            if (!contains(x, ty))
                continue;
            if (!reallyCrosses(x, ty))
                continue;
            ret = !ret;
            ty = topOne(x, ty);
        }
        return ret;
    }
    boolean reallyCrosses(int x, int y) {
        boolean left = false, right = true;
        while (true) {
            left |= adjacentX(x, y, -1);
            right |= adjacentX(x, y, 1);
            y++;
            if (!contains(x, y))
                break;
        }
        return left && right;
    }
    int topOne(int x, int ty) {
        while (true) {
            if (!contains(x, ty + 1))
                return ty;
        }
    }
}
