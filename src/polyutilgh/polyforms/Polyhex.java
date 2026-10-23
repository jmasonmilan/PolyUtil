package polyutilgh.polyforms;

import java.awt.Color;
import java.math.BigInteger;
import java.util.ArrayList;
import polyutilgh.CoordPair;
import polyutilgh.GraphicsParameters;
import polyutilgh.PolySet;
import polyutilgh.Runner;

/**
 *
 * @author mason
 */
public  class Polyhex extends PolyformWC3 {
    int deltaX[] = {0, 1, 1, 0, -1, -1};
    int deltaY[] = {2, 1, -1, -2, 1, -1};
    public Polyhex(BigInteger inValue) {
        rep = inValue;
        build(inValue);
        
        seal();  
    }    
   
    public Polyhex() {
        array = new ArrayList<CoordPair>();
    } 
    public Polyhex(String s, boolean numeric) {
        array = new ArrayList<CoordPair>();
        if (numeric) {
            BigInteger bi = new BigInteger(s);
            rep = bi;
            build(bi);

            seal();             
        } else {
            int y = 1, x = 1;

            for (int i = 0; i < s.length() ; i++) {
                String t = s.substring(i, i + 1);
                if (t.equals( "/")) {
                    x = 1; 
                    y++;
                    continue;
                }
                if (t.equals("O")) {
                    if ((x + y) % 2 == 1) {
                        System.err.println("bad string at x=" + x + ",y=" + y + ":" + s.substring(i));
                        System.exit(1);
                    }
                    this.insert(new CoordPair(x, y));
                }
                x++;
            }
            this.adjustToSextantLR();
            this.seal();            
        }
    }
    public void seal() {
         
        nonCBSeal(); 
        if (getMinY() > 1) {
            int dbgs = 1;
        }
        /*
        if (!intact()) {
            int i = 1 / 0;
        }
        */
    }    
    void build(BigInteger inValue) { // could do better?
        innerBuild(inValue);       
    }       
    public int getBit(int x, int y) {
        int v, t;
        t = x + y  - 1; 
        t = (t - 1) / 2; // 1
        t--;
        v = formula(t);
        v += y - 1; // 2
        return v;
    }
    
    int formula(int t) {
        return (t + 1) * ( t + 2) / 2 + (int)(Math.floor(Math.pow(t, 2) / 4));
    }
    public PolyformWC getPointed(int i) {
    
        Polyhex ret = new Polyhex();
        for (int j = 0; j < this.size(); j++) {
            CoordPair cp = this.getCoords(j);
            int x = cp.getX(), y = cp.getY();
            int bx, by;
            if (x % 2 == 1) {
                bx = 4 * x - 1;
                by = 4 * y - 1;
                
                
            } else {
                bx = 4 * x - 1;
                by = 4 * y - 1;
               
                
        
            }
            ret.unconditionallyInsert(bx - 1, by + 1);
            ret.unconditionallyInsert(bx - 0, by + 2);
            ret.unconditionallyInsert(bx + 1, by + 1);
            ret.unconditionallyInsert(bx + 1, by + -1);
            ret.unconditionallyInsert(bx - 0, by + -2);
            ret.unconditionallyInsert(bx - 1, by + -1);
            
            if (i == j)
                ret.unconditionallyInsert(bx, by);                
            
        }
        ret.adjustToSextantLR();
        ret.seal();
        return ret;
           
    }       
    void innerBuild(BigInteger inValue) { // could do better?
        int x = 1,  y = 1;
        int nx = 3;
        array = new ArrayList<>();
        int size = inValue.bitCount();
        for (int i = 0; size > 0 ; i++) {
            if (inValue.testBit(i)) {
                array.add(new CoordPair(x, y));
                size--;
                
            }
            
            y++;
            x--;
            
            if (3 * x <= y) {
                x = nx;            
                nx += 2;             
                
                y = 1;
            }
        }    
       
            
    }   
    public Polyhex maker(String srep) {
        return new Polyhex(new BigInteger(srep));
    }      
    public Polyhex maker() {
        return new Polyhex();
    }      
    public String dumpCoords() {
        String ret = "";
        for (int i = 0 ; i < array.size() ; i++) {
            ret += array.get(i).toString() ;
        }
        return ret;
    }
    public String drawing() {
        
        String ret = "";
        // ret += dumpCoords() + this.getRep() + "\r\n";
        int h = this.getHeight();
        int w = this.getWidth();
        
        for (int y = h ; y >= 1 ; y--) {
            
            for (int x = 1; x <= w ; x++)
                if (this.contains(x, y)) {
                    
                    ret += "O";
                    
                }
                else 
                    ret += " ";
            ret += "\r\n";
        }
        return ret ;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp) {
        return gp.green;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp, boolean applyCB) {
        /*
        if (applyCB) {
            if (cp.parity() == fcp.parity())
                return Color.GREEN;
            else
                return Color.YELLOW;
        }
*/
        return gp.green;
    } 
    int countSameColourAsFirst() {
        Runner.notImplemented("polyhex countSameColourAsFirst");
        return 0;
    }
    protected int calcMaxPred() {
        Runner.notImplemented("polyhex calcMaxPred");
        return 0;
    }
    public PolySet genNew(int limit) {
        //System.err.println("gennew : " + this.getRep());
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("1");
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            ArrayList<CoordPair> list  = this.getClose(this.array.get(i), false, false);
            for (int j = 0 ; j < list.size() ; j++) {
                tryOne(i, ps, list.get(j));
            }
            
        }
        return ps;
    }
    Polyhex tryOne(int i, PolySet ps, CoordPair cp) {
        return tryOne(i, ps, cp, false);
    }
    Polyhex tryOne(int i, PolySet ps, CoordPair cp, boolean leaf) {
        int dx = cp.getX() - this.array.get(i).getX(),  dy = cp.getY() - this.array.get(i).getY();
       
            
        int x = cp.getX();
        int y = cp.getY();
        if (y >= 1) {
            /*
            if (!intact()) {
                int k = 1 / 0;
            }      
            */
            if (!canAdd(cp))
                return null;
            Polyhex np = new Polyhex();
            for (int j = 0 ; j < this.array.size() ; j++) {
                np.insert(new CoordPair(array.get(j).getX(), array.get(j).getY()));
            }
            if (leaf && willTouch(cp) != 1)
                return null;
            np.insert(cp);
                        
            np.adjustToSextantLR();
            np.seal();
            
            if (ps.addUniques(np))
                return np;
            return null;
            //System.out.println("tryone1 : " + dx + " " + dy);
            //System.out.println(np.drawing());
        } else  {
            Polyhex np = new Polyhex();
            if (dy == -1) { // y = 0
                for (int j = 0 ; j < this.array.size() ; j++) {
                    np.insert(new CoordPair(array.get(j).getX() + 1, array.get(j).getY() + 1));
                }
                CoordPair tmp = new CoordPair(x + 1, 1);
                if (tmp.parity() == 1) {
                    int k = 1 / 0;
                }
                if (leaf && willTouch(x + 1, 1) != 1)
                    return null;
                np.insert(new CoordPair(x + 1, 1));
            } else { // dy = -2, y = 0 or -1
                if (y == 0) {
                    for (int j = 0 ; j < this.array.size() ; j++) {
                        np.insert(new CoordPair(array.get(j).getX() + 1, array.get(j).getY() + 1));
                    }

                    np.insert(new CoordPair(x + 1, 1));   
                    if (leaf && willTouch(x + 1, 1) != 1)
                        return null;                    
                } else {
                    for (int j = 0 ; j < this.array.size() ; j++) {
                        np.insert(new CoordPair(array.get(j).getX() , array.get(j).getY() + 2));
                    }
                    if (leaf && willTouch(x , 1) != 1)
                        return null;
                    np.insert(new CoordPair(x , 1));                    
                }
            }
            np.adjustToSextantLR();
            np.seal();
            if (ps.addUniques(np))
                return np;
            return null;
            //System.out.println("tryone2 : " + dx + " " + dy);
            //System.out.println(np.drawing());           
        } 
    }
    //public int getDisplayWidth() { return (getWidth() + 1 ) / 2; }
    
    public Polyhex align(boolean seal) {
        int dx = this.getMinX() - 1;
        int dy = this.getMinY() - 1;
        
        if ((dy + dx) % 2 != 0)
            dx++;
        Polyhex p = (Polyhex)maker();
        for (CoordPair cp : array) {
            p.insert(cp.add(-dx, -dy));
        }
        p.adjustToSextantLR();
        if (seal)
            p.seal();
        return p;
    }   
    public void insert(CoordPair cp) {
        if (cp.parity() == 1) {
            int i = 1 / 0;
        }
        super.insert(cp);
    }
    public void adjustToSextantLR() {
        while(tooLeft())
            nudge(2);
        while(tooRight())
            nudge(-2);
    }
    boolean tooLeft() {
        for (int i = 0 ; i < this.array.size() ; i++) {
            if (yTooHigh(this.array.get(i).getX() , this.array.get(i).getY()))
                return true;
        }
        return false;
    }
    void nudge(int dx) {
        for (int i = 0 ; i < this.array.size() ; i++) {
            //this.array.get(i).x += dx;
            this.array.get(i).incrementX(dx);
        }        
    }
    boolean tooRight() {
        for (int i = 0 ; i < this.array.size() ; i++) {
            if (justRight(i))
                return false;
        }
        return true;
    }
    boolean justRight(int i) {
        int x = this.array.get(i).getX(), y = this.array.get(i).getY();
        if (yTooHigh(x - 2, y))
            return true;
        else
            return false;
    }
    boolean yTooHigh(int x, int y) {
        if (y >= 3 * x)
            return true;
        else
            return false;
    }
    /**
     * Only for very internal use; a polyhex has all negative y, after rotation; make it positive
     * @param n
     * @return 
     */
    void shiftUp(int n) {
        int dx = 0;
        if (n % 2 == 1)
            dx = 1;
        for (int i = 0 ; i < this.array.size() ; i++) {
            CoordPair cp = array.get(i);
            cp.incrementX(dx);
            cp.incrementY(n);
            //this.array.set(i, new CoordPair(array.get(i).getX() + dx, array.get(i).getY() + n));
        }
        
    }    
    double getHalfHeight(int edgeLength) { 
        double ret = (double)(edgeLength * Math.sqrt(3) / 2); 
        return ret;
    }
    public  int getPixelHeight(GraphicsParameters gp) { 
        int hh = (int)getHalfHeight(gp.edgeLength);

        return hh * this.getHeight() + (hh ); 
    }
    public int getPixelWidth(GraphicsParameters gp) { 
        return (getWidth() * gp.edgeLength * 3 + gp.edgeLength) / 2; 
    }
    int distanceFromSextant60(CoordPair cp) {
        int d = (3 * cp.getX() - cp.getY() - 2 ) / 2;
           
        return d;
    }    
    int distanceFromSextantMinus60(CoordPair cp) {
        int d = (3 * cp.getX() + cp.getY() - 4) / 2;
          
        return d;
    }    
    int distanceFromSextantMinus30(CoordPair cp) {
        int d = (cp.getX() + cp.getY() - 2) / 2;
           
        return d;
    }    
    double hexHeight; 
  
    public void displayOnJpeg(GraphicsParameters gp) {
        
        
        hexHeight = getHalfHeight(gp.edgeLength );
        Polyhex enclosed = null;
        CoordPair ecp = null;
        if (gp.enclosed != null) {
            ArrayList<PolyformWC> orientations = gp.enclosed.getFixedArray();
            for (PolyformWC e : orientations) {
                ecp = ((Polyhex)this).findEnclosurePosition(e);
                if (ecp == null)
                    continue;
                enclosed = (Polyhex)e;
                break;
            }
        }
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair tcp = this.getCoords(i);
            if (ecp == null)
                display(gp, tcp);
            else {
                if (enclosed.contains(tcp.getX() - ecp.getX(), tcp.getY() - ecp.getY()))
                    display(gp, tcp, gp.red);
                else
                    display(gp, tcp, gp.green);
            }
        }
        if (gp.showSym) {
            int mx1, my0 = getYtop(gp, new CoordPair(1, 1)), my1, mx2, my2;
            if (testM60()) {
                double intersectX = ((this.maxDistanceFromSextant60() + this.minDistanceFromSextant60()) + 2) / 2;
                // bottom left
                mx1 = (int)(gp.px * gp.colWidth  + (intersectX ) * (gp.edgeLength ) + gp.horizMargin - (3 * gp.edgeLength / 4));
                my1 = (int)(my0 + hexHeight  / 2);
                //my1 += hexHeight  / 4;
                //my1 = (int)(( this.getHeight()  ) * hexHeight + gp.vertPixelPosition + (hexHeight / 2));
                int units = getHeight() + 2;
                // top right
                mx2 = mx1 + (gp.edgeLength / 2)  * units;
                my2 = my1 - (int)(hexHeight * units);
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testMminus60()) {
                int intersectX = (this.maxDistanceFromSextantMinus60() + this.minDistanceFromSextantMinus60() + 4) / 2;
                mx1 = (int)(gp.px * gp.colWidth  + (intersectX ) * (gp.edgeLength ) + gp.horizMargin - (gp.edgeLength / 4));
                
                my1 = (int)(my0 + hexHeight  / 2);
                int units = getHeight() + 2;
                mx2 = mx1 - (gp.edgeLength / 2)  * units;
                my2 = my1 - (int)(hexHeight * units);
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testMminus30()) {
                int intersectX = (this.maxDistanceFromSextantMinus30() + this.minDistanceFromSextantMinus30()) + 2;
                mx1 = (int)(gp.px * gp.colWidth  + (intersectX ) * (gp.edgeLength * 3 / 2  ) + gp.horizMargin  - gp.edgeLength / 2);
                //mx1 += (gp.edgeLength * 3 / 4);
                //my1 = (int)(my0 + hexHeight  / 2);
                my1 = my0; // low
                //my1 += hexHeight  / 2;
                int units = getHeight() + 1;
                mx2 = mx1 - (gp.edgeLength * 3 / 2)  * units;
                mx2 -= (gp.edgeLength * 3 / 4);
                my2 = my1 - (int)(hexHeight * units);
                my2 -= (int)(hexHeight / 2);
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testM30()) {                
                // bottom left
                mx1 = (int)(gp.px * gp.colWidth  +   gp.horizMargin - gp.edgeLength / 2);
                
                //my1 = (int)(my0 + hexHeight  / 2);
                my1 = my0;
               
                double units = getHeight() + 1 ;
                double w = getWidth() - getMinX() + 1;
                /*
                w++;
                w /= 2;
                if (units > w)
                    units = w + 1;
                */
                // top right
                mx2 = mx1 + (int)(((gp.edgeLength )  * units * 3) / 2);
                my2 = my1 - (int)(hexHeight * units);
                gp.g.drawLine(mx1, my1, mx2, my2);
            }     
            if (testMH()) {
                mx1 = (int)(gp.px * gp.colWidth  +  + gp.horizMargin - gp.edgeLength / 2);
                mx2 = mx1 + (gp.edgeLength * 3 / 2 * (getWidth())) + gp.edgeLength * 2;
                //my1 = (int)(my0 + hexHeight  / 2);
                my1 = my0;
                my1 -= (int)(( this.getHeight() + 1 ) * hexHeight / 2 );
                my2 = my1;
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testMV()) {
                int mid = ( getMinX() + getWidth() ) / 2;
                int ty;
                if (getMinX() % 2 == 0) {
                    ty = 2;
                } else {
                    ty = 1;
                }
                CoordPair cp = new CoordPair(mid, ty);
                mx1 = getXbaseLeft(gp, cp) + gp.edgeLength / 2;
                mx2 = mx1;
                my1 = (int)(my0 + hexHeight / 2);
                my2 = (int)(my1 - hexHeight * (getHeight() + 3));
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testR180()) {
                gp.g.fillOval(gp.px *  gp.colWidth + ((this.getMinX() + this.getWidth() ) * 3 * gp.edgeLength) / 4 - 3 + gp.horizMargin - gp.edgeLength / 2, 
                    my0 - (int)(((this.getHeight() + 1)  * hexHeight / 2) ) - 3 , 6, 6);                
            } else if (testR120()) {
                double sumHoriz = 0, sumVert = 0;
                int upCounter = 0;
                for (CoordPair cp : array) {

                    int x = cp.getX(), y = cp.getY();
                    sumHoriz += gp.edgeLength *  3 * x / 2;
                    sumVert += (y ) * hexHeight ;
                    upCounter++;
                }
                gp.g.fillOval(gp.px *  gp.colWidth + (int)( sumHoriz / upCounter) - 3 + gp.horizMargin - gp.edgeLength / 2, 
                    (int)(( my0 - sumVert / upCounter)) - 3 , 6, 6);  
            }
        }        
        if (gp.rep)
            gp.g.drawString(this.rep.toString(), gp.px * gp.colWidth, 10);
        
    }    
    int getXbaseLeft(GraphicsParameters gp, CoordPair cp) {
        int halfWidth = (gp.edgeLength * 3) / 2;
        return gp.px * gp.colWidth  + (cp.getX() - 1 ) * (halfWidth ) + gp.horizMargin + gp.edgeLength  / 2;
    }
    int getYtop(GraphicsParameters gp, CoordPair cp) {
        int thisph = this.getPixelHeight(gp);
        int thisCellPos = (int)((double)(cp.getY() - 1) * hexHeight);
        return  thisph + gp.vertPixelPosition - thisCellPos;        
    }
    void display(GraphicsParameters gp, CoordPair cp) {
        if (!gp.filling)
            display(gp, cp, Color.WHITE);
        else
            display(gp, cp, null);
    }
    void display(GraphicsParameters gp, CoordPair cp, Color c) {
             // here, top means bottom and vice versa
        int xTopLeft =  getXbaseLeft( gp,  cp);
        
        int yTopLeft =  getYtop( gp,  cp);
        
        int xTopRight = xTopLeft + gp.edgeLength ;
        int yTopRight = yTopLeft;
        
        int xMidLeft = xTopLeft - gp.edgeLength  / 2;
        int yMidLeft = (int)(yTopLeft - hexHeight);
        
        int xMidRight = xTopRight + gp.edgeLength  / 2;
        int yMidRight = yMidLeft;
        
        int xBottomLeft = xTopLeft;
        int yBottomLeft = (int)(yMidLeft - hexHeight);
        
        int xBottomRight = xTopRight;
        int yBottomRight = yBottomLeft;
        
        if (yBottomRight < 0) {
            int qq = 1 / 0;
        }
        if (xMidLeft < 0) {
            int qq = 1 / 0;
        }
        if (yTopRight > gp.imageHeight) {
            int qq = 1 / 0;
        }
        if (xMidRight > gp.imageWidth) {
            int qq = 1 / 0;
        }
        int xa[] = {xTopLeft, xTopRight, xMidRight, xBottomRight , xBottomLeft , xMidLeft };
        int ya[] = {yTopLeft, yTopRight, yMidRight, yBottomRight , yBottomLeft , yMidLeft };
        
        
        if (c == null)
            gp.g.setColor( this.getGraphicsColour(gp, cp, gp.applyCb) );
        else
            gp.g.setColor(c);
        gp.g.fillPolygon(xa, ya, 6);
       
        
        gp.g.setColor( Color.BLACK );   
        //gp.g.drawString(cp.toString(), xBottomLeft, yBottomLeft);
        gp.g.drawPolygon(xa, ya, 6);
 
        
        
    }   
    int willTouch(CoordPair cp) {
        return willTouch(cp.getX(), cp.getY());
    }
    int willTouch(int x, int y) {
        int ret = 0;
        if (contains(x + 1, y + 1)) ret++;
        if (contains(x - 1, y + 1)) ret++;
        if (contains(x + 1, y - 1)) ret++;
        if (contains(x - 1, y - 1)) ret++;
        if (contains(x , y + 2)) ret++;
        if (contains(x , y - 2)) ret++;
        return ret;
        
    }
   
    public ArrayList<CoordPair> getClose(CoordPair cp, boolean within, boolean pseudo) {
        if (pseudo) {
            int qq = 1 / 0;
        }
        ArrayList<CoordPair> a = new ArrayList<>();
        getClose(a, cp, 1, 1, within);
        getClose(a, cp, -1, 1, within);
        getClose(a, cp, 1, -1, within);
        getClose(a, cp, -1, -1, within);
        getClose(a, cp, 0, 2, within);
        getClose(a, cp, 0, -2, within);
        
        return a;
    }    
    public int outside(int[][] flags, int from, int to) {
        int ret = 0;
        for (int x = 1 ; x <= maxX; x++) {  
            if (x % 2 == 1) {
                if (flags[x][1] == from) {
                    flags[x][1] = to;
                    ret++;
                }
                int y = maxY;
                if (y % 2 == 0)
                    y--;
                if (y < 1)
                    continue;
                if (flags[x][y] == from) {
                    flags[x][y] = to;
                    ret++;
                }  
            } else {
                if (maxY == 1)
                    continue;
                if (flags[x][2] == from) {
                    flags[x][2] = to;
                    ret++;
                }
                int y = maxY;
                if (y % 2 == 1)
                    y--;
                if (y < 1)
                    continue;
                if (flags[x][y] == from) {
                    flags[x][y] = to;
                    ret++;
                }  
            }
        }
        
        for (int y = 1 ; y <= maxY ; y++) {            
            if (y % 2 == 1 && flags[1][y] == from) {
                flags[1][y] = to;
                ret++;
            }
            if ((maxX + y) % 2 == 0 && flags[maxX][y] == from) {
                flags[maxX][y] = to;
                ret++;
            }            
        }

        
        return ret;
    }
    public boolean ifUseful(int x, int y) { 
        if ((x + y) % 2 == 0)
            return true; 
        return false;
    }
    public int countHoleFormula() {
        int v[] = new int[4];
        for (CoordPair cp : array) {
            boolean up = contains(cp.getX(), cp.getY() + 2);
            boolean down = contains(cp.getX(), cp.getY() - 2);
            boolean upLeft = contains(cp.getX() - 1, cp.getY() + 1);
            boolean upRight = contains(cp.getX() + 1, cp.getY() + 1);
            boolean downLeft = contains(cp.getX() - 1, cp.getY() - 1);
            boolean downRight = contains(cp.getX() + 1, cp.getY() - 1);
            v[ch(up, upLeft)]++;
            v[ch(up, upRight)]++;
            v[ch(downRight, upRight)]++;
            v[ch(downRight, down)]++;
            v[ch(down, downLeft)]++;
            v[ch(downLeft, upLeft)]++;
            
        }
        /* 
            h = A + 1 - i/2 -j/6 - k/3
              = A + 1 - (3i + j + 2k) / 6
        */
        int i = v[3] / 3;
        int j = v[2] / 2;
        int k = v[1];
        int s = 3 * i + j + 2 * k;
        int h = size() + 1 - div(s, 6) ;
        return h;
    }
    int div(int a, int b) {
        if (a % b != 0) {
            System.err.println("a=" + a + " b=" + b);
            int qq = 1 / 0;
        }
        return a / b;
    }
    int ch(boolean b1, boolean b2) {
        if (b1 && b2)
            return 3;
        if (b1 || b2)
            return 2;
        return 1;
    }
    protected int perimeter(CoordPair cp) {
        int ret = 0;
        ret += perimeter(cp, 1, 1);
        ret += perimeter(cp, -1, 1);
        ret += perimeter(cp, 1, -1);
        ret += perimeter(cp, -1, -1);
        ret += perimeter(cp, 0, 2);
        ret += perimeter(cp, 0, -2);
        
        return ret;
    }    
    public BigInteger calcBorder() {
       
        for (int i = 0; i < size(); i++) {
            CoordPair cp = getCoords(i);
            
            if (!contains(cp.getX() - 1, cp.getY() + 1)) {
                return calcBorder(cp.getX(), cp.getY(), UPRIGHT);                
            }
            if (!contains(cp.getX(), cp.getY() + 2)) {
                return calcBorder(cp.getX(), cp.getY(), RIGHT);                
            }
            if (!contains(cp.getX() + 1, cp.getY() + 1)) {
                return calcBorder(cp.getX(), cp.getY(), DOWNRIGHT);                
            }
            if (!contains(cp.getX() + 1, cp.getY() - 1)) {
                return calcBorder(cp.getX(), cp.getY(), DOWNLEFT);                
            }
            if (!contains(cp.getX(), cp.getY() - 2)) {
                return calcBorder(cp.getX(), cp.getY(), LEFT);                
            }
            if (!contains(cp.getX() + 1, cp.getY())) {
                return calcBorder(cp.getX() - 1, cp.getY() - 1, UPLEFT);                
            }               
           
            
        }  
        int i = 1 / 0;
        return null;
    }
    /*
    should work for a SAP, but not for a leaky hole
    */
    BigInteger calcBorder(int x, int y, int v) {
        int startX = x, startY = y, startV = v;
        String ret = "" + v;
        int per = perimeterOrSurface();
        if (size() == 0)
            return BigInteger.ZERO;
        int dx[], dy[];
        for (int i = 1 ; i < per ; i++) {
            
 
            if (v == UPRIGHT) {
                dx = new int[] { 0};
                dy = new int[] { 2};
                     
            } else if (v == RIGHT) {
                dx = new int[] { 1};
                dy = new int[] { 1};                               
               
            } else if (v == DOWNRIGHT) {
                dx = new int[] { 1};
                dy = new int[] { -1};
                             
            } else if (v == DOWNLEFT) {
                dx = new int[] { 0};
                dy = new int[] { -2};              
                            
            } else if (v == LEFT) {
                dx = new int[] { -1};
                dy = new int[] { -1}; 
              
            } else  { // UPLEFT
                dx = new int[] { -1};
                dy = new int[] { 1}; 
                
            } 
            
            if (!this.contains(x + dx[0], y + dy[0])) {
                v += 2;
                x += dx[0];
                y += dy[0];                

            } else {
                v += 1;
               
            }
            if (v > 6)
                v -= 6;
            if (v < 1)
                v += 6;
            ret += v;
            if (x == startX && y == startY && startV == v)
                break;
        }
        return new BigInteger(ret);
    }   
    public  boolean needs2CB() { int i = 1 / 0 ; return false; }
    public  PolySet getOnesided() { int i = 1 / 0 ; return null; }
    public PolySet getFixed() {
        // copied from Polyiamond
        PolySet ps = new PolySet();
        Polyhex poly;
        ps.adds(this.getRep().toString());
        poly = this.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.reflectV(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());
        poly = poly.rotate60(); ps.adds(poly.getRep().toString());      
        return ps;
    } 
    public Polyhex rotate60() {
        /*
        if (!this.intact()) {
            int k = 1 / 0;
        }
*/
        Polyhex np = this.maker();
        for (int i = 0 ; i < this.array.size() ; i++) {
            CoordPair cp = this.array.get(i);
            int x = cp.getX();
            int y = cp.getY();
            int x3 ;
            int y3 ;            
            if (( x + y + 2) % 4 == 0) {
                int x1 = (x + y + 2) / 4;
                //int y1 = x + y - x1;
                int x2 = 2 * x1 - 1;
                int y2 = -1;
                x3 = x2;
                y3 = y2 - 2 * (x - x1);
            } else {
                int x1 = (x + y + 4) / 4;
                //int y1 = x + y - x1;
                int x2 = 2 * x1 - 2;
                int y2 = -2;
                x3 = x2;
                y3 = y2 - 2 * (x - x1);                
            }
            np.insert(new CoordPair(x3, y3, cp));
        }
        int lowestY = 1;
        for (int i = 0 ; i < this.array.size() ; i++) {
            if (np.array.get(i).getY() < lowestY)
                lowestY = np.array.get(i).getY();
        }       
        np.shiftUp(-lowestY + 1); // if we have y's of -1,-2,-3 then we want y's 3,2,1
        np.adjustToSextantLR();
        np.seal();
        /*
        if (!np.intact()) {
            int k = 1 / 0;
        }        
*/
        return np;          
    }    
    public Polyhex reflectV() {
        Polyhex np = this.maker();
        for (int i = 0 ; i < this.array.size() ; i++) {
            np.insert(new CoordPair( - array.get(i).getX() , array.get(i).getY(), array.get(i)));
        }
        np.adjustToSextantLR();
        np.seal();
        return np;        
    }    

    public int getCategory() {
        if (this.testAsym())
            return ASYM;
        if (this.testStrictlyMa())
            return MA;
        if (this.testStrictlyMu())
            return MU;
        if (this.testStrictlyM2()) {
            PolyformWC3 t = this;
            int q = 0;
            while (!t.testMH()) {
                t = t.rotate60();
                q++;
                if (q > 6) {
                    int qq = 1 / 0;
                }
                    
            }
            if (determineMVC().equals("C"))
                return M2C;
            else 
                return M2M;
            
        }
        if (this.testStrictlyM3a()) {
            if (determineMVC().equals("C"))
                return M3AC;
            else
                return M3AV;
        }
        if (this.testStrictlyM3u())
            return MU3;
        if (this.testStrictlyR120()) {
            if (determineMVC().equals("C"))
                return R120C;
            else
                return R120V;
        }
        if (this.testStrictlyR180()) {
            if (determineMVC().equals("C"))
                return R180C;
            else
                return R180M;
        }
        if (this.testStrictlyR60())
            return R60;
        if (this.testAll()) 
            return ALL;
        int qq = 1 / 0;
        return qq;
    }   
    String determineMVC() {
        int minX = getMinX();
        int minY = getMinY();
        int totH = 0, totV = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            totH += x - minX;
            totV += y - minY;
        }
        if (totH % size() != 0)
            return "V";
        int h = totH / size();
        int v = totV / size();
      
        CoordPair chosen = array.get(0);
        boolean pcv = ((chosen.getY() - minY) % 2) == 0;
        boolean pch = ((chosen.getX() - minX) % 2) == 0;
        boolean pv = (v % 2) == 0;
        boolean ph = (h % 2) == 0;
        boolean bh = (pch == ph);
        boolean bv = (pcv == pv);
        if (bh == bv)
            return "C";
        return "M";
    }    
    /*
    protected boolean groupPresent(CoordPair cp) {
        if (contains(cp) && 
                contains(cp.getX() + 1, cp.getY() + 1) && 
                contains(cp.getX() + 1, cp.getY() - 1)  )
            return true;
        if (contains(cp) && 
                contains(cp.getX() - 1, cp.getY() + 1) && 
                contains(cp.getX() - 1, cp.getY() - 1)  )
            return true;
        return false;
    }  
*/
    // 2*ceiling(sqrt(12*n-3))
    public int minPerimeterOrSurface() {
        int n = size();
        double s = Math.sqrt(12 * n - 3);
        int ret = (int)(Math.ceil(s)) * 2;
        
        return ret;
        
    }       
    protected  int maximalPerimeter() { return this.size() * 4 + 2; }
    public ArrayList<CBPolyhex> genCBPolyhex(boolean canonical) {
        ArrayList<CBPolyhex> list = new ArrayList<>();
        
        PolySet ps = new PolySet();
        for (int c = 1; c <= 3; c++) {
            

            if (this.size() == 1) {
                CBPolyhex cbp = new CBPolyhex();
                cbp.insert(new CoordPair(this.array.get(0).getX(), this.array.get(0).getY(), c));
                          
                cbp.seal();
                
                if (ps.adds(cbp.getUnique()))
                    list.add(cbp);
            } else
                genCBPolyhex1(c, ps, list, canonical);
        }
        return list;
    }
    void genCBPolyhex1(int c, PolySet ps, ArrayList<CBPolyhex> list, boolean canonical) {
        ArrayList<CoordPair> nearList = this.getAdjacentIn(this.array.get(0));
        CoordPair ncp = nearList.get(0);
        if (!this.contains(ncp)) {
            nearList = this.getAdjacentIn(this.array.get(0));
            int k = 1 / 0;
        }       
        if (canonical) {
            genCanonCBPolyhex2( c, nextColour(c), nextColour(nextColour(c)), ncp, ps, list);
            genCanonCBPolyhex2( c, nextColour(nextColour(c)), nextColour(c), ncp, ps, list);
        } else {
            genUncanonCBPolyhex2( c, nextColour(c), nextColour(nextColour(c)), ncp, ps, list);
            genUncanonCBPolyhex2( c, nextColour(nextColour(c)), nextColour(c), ncp, ps, list);            
        }
        
    }
    int nextColour(int c) {
        c++;
        if (c > 3)
            c = 1;
        return c;
    }
    void genCanonCBPolyhex2(int c1, int c2, int c3, CoordPair scp, PolySet ps, ArrayList<CBPolyhex> list) {
        if (!this.contains(scp)) {
            int k = 1 / 0;
        }
        int h = this.getHeight(), w = this.getWidth();
        Tmp[][] a = new Tmp[w + 1][h + 1];
        CBPolyhex cbp = new CBPolyhex();
        cbp.insert(new CoordPair(this.array.get(0).getX(), this.array.get(0).getY(), c1));
        cbp.insert(new CoordPair(scp.getX(), scp.getY(), c2));
        Tmp t[] = new Tmp[] { new Tmp(), new Tmp(), new Tmp() };
        
        for (int x = 1 ; x <= w; x++) {
            for (int y = 1 ; y <= h ; y++) {
                if ((x + y) % 2 == 1)
                    continue;
                int d = conv(x, y);
                a[x][y] = t[d];
            }
        }
        a[this.array.get(0).getX()][this.array.get(0).getY()].colour = c1;
        a[scp.getX()][scp.getY()].colour = c2;
        for (int i = 0 ; i < 3 ; i++)
            if (t[i].colour == 0) {
                t[i].colour = c3;
                break;
            }
        for (int i = 1; i < this.size(); i++) {
            CoordPair cp = this.array.get(i);
            if (cp.equals(scp))
                continue;
            int x = this.array.get(i).getX(), y = this.array.get(i).getY();
            cbp.insert(new CoordPair(x, y, a[x][y].colour));         
        }
        cbp.seal();
        if (this.size() != cbp.size()) {
            int k = 1 / 0;
        }
        if (ps.adds(cbp.getUnique()))
            list.add(cbp);
    }
    int conv(int x, int y) {
        int d;
        if ( x % 2 == 1) {
            d = (y / 2) % 3;

        } else {
            d = y / 2;
            d = (d + 1) % 3;
        }        
        return d;
    }
    public boolean adjacent(int x, int y) {
        for (int i = 0; i < 6; i++)
            if (easyContains(x + deltaX[i], y + deltaY[i]))
                return true;
        return false;
    }
    public boolean adjacentX(int x, int y, int dx) {
        return easyContains(x + dx, y - 1) || contains(x + dx, y + 1);
    }
    private class Tmp{
        int colour;
        private Tmp() { }
    }
    void genUncanonCBPolyhex2(int c1, int c2, int c3, CoordPair scp, PolySet ps, ArrayList<CBPolyhex> list) {
        if (!this.contains(scp)) {
            int k = 1 / 0;
        }
        int h = this.getHeight(), w = this.getWidth();
        
        CBPolyhex cbp = new CBPolyhex();
        cbp.insert(new CoordPair(this.array.get(0).getX(), this.array.get(0).getY(), c1));
        cbp.insert(new CoordPair(scp.getX(), scp.getY(), c2));
        
        for (int i = 1; i < this.size(); i++) {
            CoordPair cp = this.array.get(i);
            if (cp.equals(scp))
                continue;
            int x = this.array.get(i).getX(), y = this.array.get(i).getY();
            cbp.insert(new CoordPair(x, y));         
        }
        ArrayList<CBPolyhex> newList = new ArrayList<>();
        newList.add(cbp);
        while (prop1(newList)) ;
        
        for (int i = 0 ; i < newList.size() ; i++) {
            cbp = newList.get(i);
            
            cbp.seal();
            
            if (ps.adds(cbp.getUnique()))
                list.add(cbp);
        }
    }   
    boolean prop1(ArrayList<CBPolyhex> newList) {
        for (int i = 0; i < newList.size() ; i++) {
           
            if (prop2(i, newList.get(i), newList)) {
                
                return true;
            }
        }
        return false;
    }
    boolean prop2(int i, CBPolyhex cbp, ArrayList<CBPolyhex> newList) {
        if (cbp.allColoured())
            return false;
        //System.err.println("1: " + i + " " + cbp.countColoured());
        int tmp = prop3(cbp);
        
        if (tmp > 0)
            return true;
        if (tmp < 0) {
            //System.err.println("2: "  + i + " " + cbp.countColoured());
            newList.remove(cbp);
            return true;
        }
        //System.err.println("3: "  + i + " " + cbp.countColoured());
        newList.add(prop4(cbp));
        //System.err.println("4: "  + i + " " + cbp.countColoured());
        
        return true;
    }
    int prop3(CBPolyhex cbp) {
        int cc = cbp.countColoured();
        for (int i = 0 ; i < cbp.array.size() ; i++)
            if (cbp.array.get(i).getColour() == 0)   {
                ArrayList<CoordPair> adjList = cbp.getAdjacentIn(cbp.array.get(i));
                ColourFree cf = new ColourFree(adjList);
                if (cf.freeNum() == 1) {
                    cbp.array.get(i).setColour(cf.freeColour());
                    if (cbp.countColoured() != cc + 1) {
                        int k = 1 / 0;
                    }                   
                    return 1;
                }
                if (cf.freeNum() == 0) {
                    
                    return -1;
                }                
            }  
        return 0;
    }
    CBPolyhex prop4(CBPolyhex cbp) {
        int cc = cbp.countColoured();
        for (int i = 0 ; i < cbp.array.size() ; i++)
            if (cbp.array.get(i).getColour() == 0)   {
                ArrayList<CoordPair> adjList = cbp.getAdjacentIn(cbp.array.get(i));
                ColourFree cf = new ColourFree(adjList);
                if (cf.freeNum() == 2) {
                    CBPolyhex ncbp = cbp.copySoFar();
                    cbp.array.get(i).setColour(cf.freeColour());
                    if (cbp.countColoured() != cc + 1) {
                        int k = 1 / 0;
                    }
                    ncbp.array.get(i).setColour(cf.otherColour());
                    if (ncbp.countColoured() != cc + 1) {
                        int k = 1 / 0;
                    }                   
                    return ncbp;
                }
                               
            }      
        int k = 1 / 0;
        return null;
    }
    public int internalPoints() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            
            if (y < 3)
                continue;
            if (contains(x + 1, y - 1)  && contains(x , y - 2) )
                ret++;
        }
        return ret;
    }    
    public  int internalEdges() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            
                ret += internalEdges(x - 1, y + 1);
                ret += internalEdges(x - 1, y - 1);
                ret += internalEdges(x + 1, y + 1);
                ret += internalEdges(x + 1, y - 1);
                ret += internalEdges(x , y + 2);                
                ret += internalEdges(x , y - 2);                
                
        }
        return ret / 2;
    }        
    private class ColourFree {
        boolean used[];
        private ColourFree(ArrayList<CoordPair> adjList) {
            used = new boolean[4];
            for (int i = 0 ; i < adjList.size(); i++) {
                used[adjList.get(i).getColour()] = true;
            }
        }
        int free, other;
        private int freeNum() {
            int ret = 0;
            for (int i = 1; i <= 3 ; i++) {
                if (used[i] == false) {
                    ret++;
                    other = free;
                    free = i;
                }
            }
            return ret;
        }
        private int freeColour() {
            return free;
        }
        private int otherColour() {
            return other;
        }        
    }
    
}

