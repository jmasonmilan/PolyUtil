/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;

import java.awt.Color;
import java.math.BigInteger;
import java.util.ArrayList;
import polyutilgh.CoordPair;
import polyutilgh.GraphicsParameters;
import polyutilgh.*;


/**
 *
 * @author mason -
 * Ring with R90 symmetry
 */
public  class Loop90 extends Polyomino90 {
    
    public Loop90(BigInteger inValue) {
        super(inValue);  
    }    
   
    public Loop90() {
        super();
    }
         
    public Loop90 maker(String srep) {
        return new Loop90(new BigInteger(srep));
    }    
    
    public Loop90 maker() {
        return new Loop90();
    } 
    public void seal() {
        super.seal();
        //enrich();
    }
    public boolean isALoop() {
        return true;
    }    
    public PolyformWC copy() {
        Polyomino90 np ;
        if (this.getWidth() % 2 == 0)
            np = new Polyomino90AboutCorner();
        else
            np = new Polyomino90AboutCentreEmpty();
        for (CoordPair cp : array)
            np.insert(cp);
        return np;
    }    
    public Loop90 getInnerRing() {
        return this;
    }
    boolean okToTry(int x, int y) {
        int qq = 1 / 0;
        return true;
    }    
    public boolean inRestrictedSet(CoordPair cp) {
        int qq = 1 / 0;
        return true;
    }    
    
   

    public PolySet genNew(int limit) {
        int thisSize = size();
        Loop90 epoly = (Loop90)(this.shiftRight(1).shiftUp(1));
        
        epoly.enrich(); 
        PolySet ps = new PolySet(size() + 8);

        int w = this.getWidth();
        int q = size() / 4;   
        int x = this.leftmostTop();
        int nw = w + 2;
        CoordPair current = epoly.find(x + 1, w + 1);
        for (int i = 0 ; i < q; i++) {
            epoly.procede(thisSize, current, 1, 0, ps, 0, 1, nw);
            epoly.procede(thisSize, current, -1, 0, ps, 0, -1, nw);
            epoly.procede(thisSize, current, 0, 1, ps, -1, 0, nw);
            epoly.procede(thisSize, current, 0, -1, ps, 1, 0, nw);
            epoly.procede(thisSize, current, 1, 0, ps, 0, -1, nw);
            epoly.procede(thisSize, current, -1, 0, ps, 0, 1, nw);
            epoly.procede(thisSize, current, 0, 1, ps, 1, 0, nw);
            epoly.procede(thisSize, current, 0, -1, ps, -1, 0, nw);
            current = epoly.sequenceOfSquares.getNext(current);
        }
        
        
        
        return ps;
    }   
    void procede(int thisSize, CoordPair current, int dx, int dy, PolySet ps, int px, int py, int nw) {
        //CoordPair next = current.getNext();
        //int cx = current.getX(), cy = current.getY();
        //int nx = next.getX(), ny = next.getY();
        //if (cx + dx != nx || cy + dy != ny)
        //    return;
        int segmentLength = getSegmentLength(current, dx, dy);
        for (int startPos = 0; startPos < segmentLength - 2; startPos++) {
            for (int len = 3; len <= segmentLength - startPos; len++) {
                tryBridge(thisSize, current, startPos, len, dx, dy, ps, px, py, nw);
            }
        }
    }
    void tryBridge(int thisSize, CoordPair current, int startPos, int len, int dx, int dy, PolySet ps, int px, int py, int nw) {
        while (startPos-- > 0) 
            current = sequenceOfSquares.getNext(current);
        
        int tx = current.getX() - dx, ty = current.getY() - dy;
        for (int i = -1 ; i <= len ; i++) {
            if (contains(tx + px, ty + py))
                return;
            tx += dx;
            ty += dy;
        }
        tx = current.getX() ;
        ty = current.getY() ;
        for (int i = 0 ; i < len ; i++) {
            if (contains(tx + px + px, ty + py + py))
                return;
            tx += dx;
            ty += dy;
        }
        
        Loop90 np = new Loop90();
       
        
        int q = size() / 4;
        for (int i = 0; i <= q; i++) { // was < but is insufficient
            int x = current.getX(), y = current.getY();      
            if (i == 0 || i >= len - 1) {
                np.multiInsert(new CoordPair(x , y ), nw);
            }
            if (i <= len - 1)
                np.multiInsert(new CoordPair(x  + px, y  + py), nw);
            current = sequenceOfSquares.getNext(current);
        }
        np.seal();
        np = (Loop90)(np.align());
        if (!np.intact() || !np.loop())
            return;
        if (np.size() != thisSize + 8 ) {
            String dbg1 = this.drawing();
            String dbg2 = np.drawing();
            System.err.println(dbg1);
            System.err.println(dbg2);
            System.exit(1);
        }
        ps.addUniques(np);
        np.tryLoopPop(ps);
        np.tryDePop(ps);
    }



    void tryLoopPop(PolySet ps) {
        int tx = this.leftmostTop();
        int w = this.getWidth();
        if (w < 4)
            return;
        //if (this.contains(tx - 1, w))
        //    return;
        if (!this.contains(tx - 1, w - 1))
            return;
        //if (!this.contains(w - 1, w))
        //    return;
        if (!this.contains(tx, w - 1))
            return;
        Loop90 p = (Loop90)(maker());
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (x == tx && y == w - 1)
                p.array.add(new CoordPair(tx - 1, w));
            else if (x == w - 1 && y == w - tx + 1)
                p.array.add(new CoordPair(w, w - tx + 2));
            else if (x == w - tx + 1 && y == 2)
                p.array.add(new CoordPair(w - tx + 2, 1));
            else if (x == 2 && y == tx)
                p.array.add(new CoordPair(1, tx - 1));
            else 
                p.array.add(cp);
        }
        //String dbg1 = this.drawing();
        //String dbg2 = p.drawing();
        p.seal();
        
        //System.out.println(p.drawing());
        if (p.intact())
            ps.addUniques(p);
    }      
    void tryDePop(PolySet ps) {
        int tx = this.leftmostTop();
        int w = this.getWidth();
        if (w < 4)
            return;
        if (this.contains(tx + 1, w - 1))
            return;
        //if (!this.contains(tx - 1, w - 1))
        //    return;
        //if (!this.contains(w - 1, w))
        //    return;
        //if (!this.contains(tx, w - 1))
        //    return;
        Loop90 p = (Loop90)(maker());
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (x == tx && y == w)
                p.array.add(new CoordPair(tx + 1, w - 1));
            else if (x == w && y == w - tx + 1)
                p.array.add(new CoordPair(w - 1, w - tx));
            else if (x == w - tx + 1 && y == 1)
                p.array.add(new CoordPair(w - tx, 2));
            else if (x == 1 && y == tx)
                p.array.add(new CoordPair(2, tx + 1));
            else 
                p.array.add(cp);
        }
        //String dbg1 = this.drawing();
        //String dbg2 = p.drawing();
        p.seal();
        if (!p.loop() ) {
            return;
        }
        //System.out.println(p.drawing());
        if (p.intact())
            ps.addUniques(p);
    }            
    /* this version popped only extreme corners
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
        Loop90 p = (Loop90)(maker());
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
    */
 
    
    public String getUnique() {
        
        BigInteger rep1 = this.getRep();

        BigInteger rep5 = this.reflectH().getRep();

        if (rep1.compareTo(rep5) > 0)
            rep1 = rep5;
        
        return rep1.toString();
    }   
    int half, xHalf;
    public InclusionList loopInside() {
        
        half = (maxX + 1) / 2;
        xHalf = half;
        if (maxX % 2 == 1)
            xHalf--;
        InclusionList ret = new InclusionList();
        enrich();
        for (CoordPair cp : array) {
            int dx1 = cp.getX() - sequenceOfSquares.getPrevious(cp).getX(), dy1 = cp.getY() - sequenceOfSquares.getPrevious(cp).getY();
            int dx2 =  sequenceOfSquares.getNext(cp).getX()- cp.getX(), dy2 =  sequenceOfSquares.getNext(cp).getY() - cp.getY();
            String s;
            s = dx1 + "" + dy1 + "" + dx2 + "" + dy2;
            if (s.equals("1010")) { // 1
                ret = liIns(ret, cp, 0, -1);
            } else if (s.equals("100-1")) { // 2
                // noop
            } else if (s.equals("1001")) { // 3
                ret = liIns(ret, cp, 0, -1);
                ret = liIns(ret, cp, 1, 0);
            } else if (s.equals("-10-10")) { // 4
                ret = liIns(ret, cp, 0, 1);
            } else if (s.equals("-1001")) { // 5
                // noop
            } else if (s.equals("-100-1")) { // 6
                ret = liIns(ret, cp, 0, 1);
                ret = liIns(ret, cp, -1, 0);
            } else if (s.equals("0-10-1")) { // 7
                ret = liIns(ret, cp, -1, 0);
            } else if (s.equals("0-1-10")) { // 8
                // noop
            } else if (s.equals("0-110")) { // 9
                ret = liIns(ret, cp, -1, 0);
                ret = liIns(ret, cp, 0, -1);
            } else if (s.equals("0101")) { // 10
                ret = liIns(ret, cp, 1, 0);
            } else if (s.equals("01-10")) { // 11
                ret = liIns(ret, cp, 1, 0);
                ret = liIns(ret, cp, 0, 1);
            } else  { // if (s.equals("0110")) // 12
                // noop
            } 
            
        }

       
        return ret;
    } 
    public InclusionList loopOutside() {
        half = (maxX + 1) / 2;
        xHalf = half;
        if (maxX % 2 == 1)
            xHalf--;        
        InclusionList ret = new InclusionList();
        if (size() == 1) {
            ret = ret.add(new CoordPair(1, 2));
            return ret;
        }
       
        
        enrich();
        for (CoordPair cp : array) {
            int dx1 = cp.getX() - sequenceOfSquares.getPrevious(cp).getX(), dy1 = cp.getY() - sequenceOfSquares.getPrevious(cp).getY();
            int dx2 =  sequenceOfSquares.getNext(cp).getX()- cp.getX(), dy2 =  sequenceOfSquares.getNext(cp).getY() - cp.getY();
            String s;
            s = dx1 + "" + dy1 + "" + dx2 + "" + dy2;
            if (s.equals("1010")) { // 1
                ret = liIns(ret, cp, 0, 1);
            } else if (s.equals("100-1")) { // 2
                ret = liIns(ret, cp, 0, 1);
                ret = liIns(ret, cp, 1, 0);
            } else if (s.equals("1001")) { // 3
                // noop
            } else if (s.equals("-10-10")) { // 4
                ret = liIns(ret, cp, 0, -1);
            } else if (s.equals("-1001")) { // 5
                ret = liIns(ret, cp, 0, -1);
                ret = liIns(ret, cp, -1, 0);
            } else if (s.equals("-100-1")) { // 6
                // noop
            } else if (s.equals("0-10-1")) { // 7
                ret = liIns(ret, cp, 1, 0);
            } else if (s.equals("0-1-10")) { // 8
                ret = liIns(ret, cp, 0, -1);
                ret = liIns(ret, cp, 1, 0);
            } else if (s.equals("0-110")) { // 9
                // noop
            } else if (s.equals("0101")) { // 10
                ret = liIns(ret, cp, -1, 0);
            } else if (s.equals("01-10")) { // 11
                // noop
            } else  { // if (s.equals("0110")) // 12
                ret = liIns(ret, cp, 0, 1);
                ret = liIns(ret, cp, -1, 0);
            } 
            
        }

        return ret;
    }     
    private InclusionList liIns(InclusionList incl, CoordPair cp, int dx, int dy) {
        int nx = cp.getX() + dx, ny = cp.getY() + dy;
        if (nx <= xHalf || ny <= half)
            return incl;
        CoordPair ncp = new CoordPair(nx, ny);
        incl = incl.add(ncp);
        return incl;
    }

}
