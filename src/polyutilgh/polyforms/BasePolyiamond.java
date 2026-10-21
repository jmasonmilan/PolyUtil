/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;

import java.awt.Color;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import polyutilgh.CoordPair;
import polyutilgh.GraphicsParameters;
import polyutilgh.PolySet;

/**
 *
 * @author mason
 */
public abstract class BasePolyiamond extends PolyformWC3 {
    /*       IAMOND
    14   N       ASYM    
    13   M         MA      
    12   L         MU      
    11   K        M2V     
    10   J        M2M     
    9   I       M3A     
    8   H       M3UC    
    7   G        M3UV    
    6   F      R120C   
    5  E      R120V   
    4  D      R180M   
    3  C      R180V   
    2  B        R60     
    1  A        ALL     
    */
    static String[] categoryString = {"", "ALL", "R60", "R180V", "R180M", "R120V", "R120C", "M3UV", 
        "M3UC", "M3A", "M2M", "M2V", "MU", "MA", "ASYM"};
    double triangleHeight; // for graphics display
    int[] deltaX = {-1, 1, 0,  0};
    int[] deltaY = {0,  0, 1, -1};      
    int[] pseudoDeltaX = {-2, -1,  0,  1,  2, -2, -1, 1, 2, -1, 0, 1};
    int[] pseudoDeltaY = {-1, -1, -1, -1, -1,  0,  0, 0, 0,  1, 1, 1};      
    public BasePolyiamond(BigInteger inValue) {
        rep = inValue;
        build(inValue);
        
        seal();  
    }    
   
    public BasePolyiamond() {
        array = new ArrayList<>();
    }
    abstract void build(BigInteger inValue);
    /*
    public void seal() {
         
        if (rep == null) {
            rep = new BigInteger("0");
            for (int i = 0 ; i < array.size() ; i++) {
                if (array.get(i).getY() < 1) { 
                    int j = 1 / 0; 
                }
                int n = getBit(array.get(i).getX(), array.get(i).getY());
                //System.out.println(ret);
                rep = rep.setBit(n);
                //System.out.println(ret);
            }
        }
     
        calcMax();
        sealed = true;        
    }  
    */
    protected ArrayList<CoordPair> adjacentButNotContainedList(CoordPair cp) {
        ArrayList<CoordPair> ret = new ArrayList<>();
        int x = cp.getX(), y = cp.getY();
        for (int i = 0; i < deltaX.length; i++) {
            int nx = x + deltaX[i], ny = y + deltaY[i];
            if (adjacent(x, y, nx, ny) && !contains(nx, ny))
                ret.add(new CoordPair(nx, ny));
        }
        return ret;
    }
    protected ArrayList<CoordPair> pseudoAdjacentButNotContainedList(CoordPair cp) {
        if (pointsUp(cp))
            return pseudoAdjacentButNotContainedList(cp, 1);
        else
            return pseudoAdjacentButNotContainedList(cp, -1);
    }
    protected ArrayList<CoordPair> pseudoAdjacentButNotContainedList(CoordPair cp, int multiplier) {
        ArrayList<CoordPair> ret = new ArrayList<>();
        int x = cp.getX(), y = cp.getY();
        for (int i = 0; i < pseudoDeltaX.length; i++) {
            int nx = x + pseudoDeltaX[i], ny = y + multiplier * pseudoDeltaY[i];
            if (!contains(nx, ny))
                ret.add(new CoordPair(nx, ny));
        }
        return ret;
    }
    
    public int countVertices() {
        int ret = 0;
        int[] tab = new int[7];
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (this.pointsUp(cp)) {
                
                boolean upLeft  = contains(x - 1, y + 1);
                boolean up      = contains(x    , y + 1);
                boolean upRight = contains(x + 1, y + 1);
                boolean left  = contains(x - 1, y);
                boolean leftLeft  = contains(x - 2, y);
                boolean right  = contains(x + 1, y);
                boolean rightRight  = contains(x + 2, y);
                boolean downLeft  = contains(x - 1, y - 1);
                boolean downLeftLeft  = contains(x - 2, y - 1);
                boolean down      = contains(x    , y - 1);
                boolean downRight = contains(x + 1, y - 1);
                boolean downRightRight = contains(x + 2, y - 1);
                int sum = 1;
                sum += oneIfTrue(left);
                sum += oneIfTrue(right);
                sum += oneIfTrue(upLeft);
                sum += oneIfTrue(up);
                sum += oneIfTrue(upRight);
                tab[sum]++;
                sum = 1;
                sum += oneIfTrue(left);
                sum += oneIfTrue(leftLeft);
                sum += oneIfTrue(downLeft);
                sum += oneIfTrue(downLeftLeft);
                sum += oneIfTrue(down);
                tab[sum]++;
                sum = 1;
                sum += oneIfTrue(right);
                sum += oneIfTrue(rightRight);
                sum += oneIfTrue(downRight);
                sum += oneIfTrue(downRightRight);
                sum += oneIfTrue(down);
                tab[sum]++;
            } else {
                
                boolean downLeft  = contains(x - 1, y - 1);
                boolean down      = contains(x    , y - 1);
                boolean downRight = contains(x + 1, y - 1);
                boolean left  = contains(x - 1, y);
                boolean leftLeft  = contains(x - 2, y);
                boolean right  = contains(x + 1, y);
                boolean rightRight  = contains(x + 2, y);
                boolean upLeft  = contains(x - 1, y + 1);
                boolean upLeftLeft  = contains(x - 2, y + 1);
                boolean up      = contains(x    , y + 1);
                boolean upRight = contains(x + 1, y + 1);
                boolean upRightRight = contains(x + 2, y + 1);
                int sum = 1;
                sum += oneIfTrue(left);
                sum += oneIfTrue(right);
                sum += oneIfTrue(downLeft);
                sum += oneIfTrue(down);
                sum += oneIfTrue(downRight);
                tab[sum]++;
                sum = 1;
                sum += oneIfTrue(left);
                sum += oneIfTrue(leftLeft);
                sum += oneIfTrue(upLeft);
                sum += oneIfTrue(upLeftLeft);
                sum += oneIfTrue(up);
                tab[sum]++;
                sum = 1;
                sum += oneIfTrue(right);
                sum += oneIfTrue(rightRight);
                sum += oneIfTrue(upRight);
                sum += oneIfTrue(upRightRight);
                sum += oneIfTrue(up);
                tab[sum]++;                
            }
        }
        for (int i = 1; i <= 6; i++)
            ret += div(tab[i] , i);
        return ret;
    }
    int oneIfTrue(boolean f) {
        if (f) 
            return 1;
        else
            return 0;
    }
    public boolean fixedEncloses(PolyformWC in, int x, int y) {
        if ((x + y) % 2 != 0)
            return false;
        for (CoordPair cp : in.array) {
            if (!this.contains(cp.getX() + x, cp.getY() + y))
                return false;
        }
        return true;
    }    
    protected boolean adjacent(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            if (verticallyTouches(x1, y1, y2))
                return true;
            return false;
            
        }
        if (absoluteDifference(x1, x2) == 1 && y1 == y2)
            return true;
        return false;
    }
    protected boolean verticallyTouches(int x, int y1, int y2) {
        if (absoluteDifference(y1, y2) != 1)
            return false;
        if (y1 < y2)
            return ((x + y1) % 2 != 0);
        else
            return ((x + y2) % 2 != 0);
    }
    protected int absoluteDifference(int v1, int v2) {
        int ret = v1 - v2;
        if (ret < 0)
            ret = -ret;
        return ret;
    }        
    public int getBit(int x, int y) {
        int v;
        if ((x + y) % 2 == 1)
            v = (int)(Math.pow((x + y - 1) / 2, 2)) + 1;
        else
            v = (int)(Math.pow((x + y - 2) / 2, 2));
        //v = v*v;
        v += 2 * (y - 1);   
        return v;
    }
    void innerBuild(BigInteger inValue) { // could do better?
        int x = 1, y = 1;
        array = new ArrayList<>();
        int size = inValue.bitCount();
        for (int i = 0; size > 0 ; i++) {
            if (inValue.testBit(i)) {
                array.add(new CoordPair(x, y));
                size--;
                
            }
            if ((x + y) % 2 == 0) {
                x--;
            } else {
                y++;
            }
            if (x < y) {
                x = 2 * y + 1;
                y = 1;
            }
        }        
    } 
    protected int calcMaxPred() {
        int ret = size() / 3;
        ret *= 2;
        int rem = size() % 3;
        if (rem > 0)
            ret++;
        
        return ret;
    }          
    /*
    public String drawing() {
        String ret = "";
        int h = this.getHeight();
        int w = this.getWidth();
        
        for (int y = h ; y >= 1 ; y--) {
            
            for (int x = 1; x <= w ; x++)
                if (this.contains(new CoordPair(x, y))) {
                    if ((x + y) % 2 == 0)
                        ret += "^";
                    else
                        ret += "v";
                }
                else 
                    ret += " ";
            ret += "\r\n";
        }
        return ret ;
    }
    */
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp) {
        return gp.green;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp, boolean applyCB) {
        
        if (applyCB) {
            if (cp.parity() == fcp.parity())
                return gp.green;
            else
                return gp.yellow;
        }

        return gp.green;
    } 
    public PolySet genNew(int limit) {
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("1");
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            if (array.get(i).parity() == 0) {     //        /\
                tryOne(i, ps, 1, 0);
                tryOne(i, ps, -1, 0);
                tryOne(i, ps, 0, -1);            
            } else {                                //      \/
                tryOne(i, ps, 1, 0);
                tryOne(i, ps, -1, 0);
                tryOne(i, ps, 0, 1);                  
            }    
        }
        return ps;
    }
    Polyiamond tryOne(int i, PolySet ps, int dx, int dy) {
        //System.out.println("tryone : " + dx + " " + dy);
        int x = array.get(i).getX() + dx;
        int y = array.get(i).getY() + dy;
        if (x < y) {
            int ty = 0;
            if (y < 1)
                ty++;
            Polyiamond np =  (Polyiamond)(maker());
            for (int j = 0 ; j < this.array.size() ; j++) {
                np.strictInsert(new CoordPair(array.get(j).getX() + 2 + ty, array.get(j).getY() + ty));
            }
            np.strictInsert(new CoordPair(x + 2 + ty, y + ty));
            np.adjustToSextant60();
            np.seal();
            //System.err.println(np.drawing());
            ps.addUniques(np);            
            return np;
        } else if (y < 1) {
            Polyiamond np =  (Polyiamond)(maker());
            for (int j = 0 ; j < this.array.size() ; j++) {
                np.strictInsert(new CoordPair(array.get(j).getX() + 1, array.get(j).getY() + 1));
            }
            
            np.strictInsert(new CoordPair(x + 1, 1));
            np.adjustToSextant60();
            np.seal();
            ps.addUniques(np);            
            return np;          
        } else {
            CoordPair cp = new CoordPair(x, y);
            if (!this.contains(cp)) {
                BigInteger nrep = this.getRep().setBit(getBit(x, y));
                Polyiamond npoly =  (Polyiamond)(maker(nrep.toString()));
                ps.addUniques(npoly);
                return npoly;      
            }
            return null;
        } 
    }

    public int getDisplayWidth() { return (getWidth() + 1 ) / 2; }
    
   

    public void adjustToSextant60() {
        int worst = 1000;
        if (sealed) { int i = 1 / 0; }
        for (int i = 0 ; i < this.array.size() ; i++) {
            CoordPair cp = this.array.get(i);
            int d = distanceFromSextant60( cp);
            if (d < worst)
                worst = d;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            CoordPair cp = this.array.get(i);
            cp.incrementX(-worst);
            //cp.x -= worst;
        }
    }
    
    

    int maxDistanceFromSextantMinus30() {
        int worst = 0;
       
        for (CoordPair cp : array) {
            
            int d = distanceFromSextantMinus30( cp);
            
            if (d > worst)
                worst = d;
        }
        return worst;
    }
    int minDistanceFromSextantMinus30() {
        int worst = 1000;
       
        for (CoordPair cp : array) {
            
            int d = distanceFromSextantMinus30( cp);
            
            if (d < worst)
                worst = d;
        }
        return worst;
    }
    int distanceFromSextant60(CoordPair cp) {
        int d = cp.getX() - cp.getY();
        if (pointsDown(cp))
            d--;     
        return d;
    }
    int distanceFromSextantMinus60(CoordPair cp) {
        int d = (cp.getX() + cp.getY()) / 2;
        if (pointsUp(cp))
            d--;     
        return d;
    }
    int distanceFromSextantMinus30(CoordPair cp) {
        int d;
        int x = cp.getX() , y = cp.getY();
        if (pointsDown(cp))
            x++; 
        d = 3 * y + x;
        return d ;
    }
    /*
    double scanSextant60() {
        int lowestY = 100,  highestY = 0;
        int lowestX = 100,  highestX = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY(), d = distanceFromSextant60(cp);
            if (d == 0) {
                if (y < lowestY)
                    lowestY = y;
                if (y > highestY)
                    highestY = y;
                if (x < lowestX)
                    lowestX = x;
                if (x > highestX)
                    highestX = x;
                
            }
        }
        double v = (lowestY + highestY - 1) / 2;
        double h = (lowestX + highestX - 1) / 2;
        double m = (getMinX() + getWidth() - 1) / 2;
        double absoluteDifference = m - h;
        v -= absoluteDifference / 3;
        return v;
    } */
    /**
     * Only for very internal use; a polyiamond has all negative y, after rotation; make it positive
     * @param n
     * @return 
     */
    void shiftUp(int n) {       
        for (int i = 0 ; i < this.array.size() ; i++) {
            this.array.set(i, new CoordPair(array.get(i).getX() + n , array.get(i).getY() + n));
        }       
    }    
    public int getPixelWidth(GraphicsParameters gp) { 
        //return (getWidth() * edgeLength * 3 + edgeLength) / 2; 
        int n = getWidth();
        int m = n / 2;
        int ret = m * gp.edgeLength;
        if (n % 2 == 1)
            ret += gp.edgeLength / 2;
        ret += gp.edgeLength / 2;
        return ret;
    }    
    
    public void displayOnJpeg(GraphicsParameters gp) {
        
        triangleHeight = getCellHeight(gp.edgeLength);
        Polyiamond enclosed = null;
        CoordPair ecp = null;
        if (gp.enclosed != null) {
            ArrayList<PolyformWC> orientations = gp.enclosed.getFixedArray();
            for (PolyformWC e : orientations) {
                ecp = ((Polyiamond)this).findEnclosurePosition(e);
                if (ecp == null)
                    continue;
                enclosed = (Polyiamond)e;
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
            int mx1, my1, mx2, my2;
            if (testM60()) {
                int intersectX = (this.maxDistanceFromSextant60() / 2) + 1;
                mx1 = (int)(gp.px * gp.colWidth  + (intersectX - 1) * (gp.edgeLength / 2) + gp.horizMargin + (gp.edgeLength / 4));
                my1 = (int)(( this.getHeight()  ) * triangleHeight + gp.vertPixelPosition + (triangleHeight / 2));
                int units = getHeight() + 1;
                mx2 = mx1 + (gp.edgeLength / 2)  * units;
                my2 = my1 - (int)(triangleHeight * units);
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testMminus60()) {
                int intersectX = (this.maxDistanceFromSextantMinus60() + this.minDistanceFromSextantMinus60() + 1) / 2;
                mx1 = (int)(gp.px * gp.colWidth  + (intersectX ) * (gp.edgeLength ) + gp.horizMargin + (gp.edgeLength / 4));
                my1 = (int)(( this.getHeight()  ) * triangleHeight + gp.vertPixelPosition + (triangleHeight / 2));
                int units = getHeight() + 1;
                mx2 = mx1 - (gp.edgeLength / 2)  * units;
                my2 = my1 - (int)(triangleHeight * units);
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testMminus30()) {
                int intersectX = (this.maxDistanceFromSextantMinus30() + this.minDistanceFromSextantMinus30()) / 2;
                mx1 = (int)(gp.px * gp.colWidth  + (intersectX - 2) * (gp.edgeLength / 2) + gp.horizMargin );
                mx1 += (gp.edgeLength * 3 / 4);
                my1 = (int)(( this.getHeight()  ) * triangleHeight + gp.vertPixelPosition );
                my1 += (int)(triangleHeight / 2);
                int units = getHeight() ;
                mx2 = mx1 - (gp.edgeLength * 3 / 2)  * units;
                mx2 -= (gp.edgeLength * 3 / 4);
                my2 = my1 - (int)(triangleHeight * units);
                my2 -= (int)(triangleHeight / 2);
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testM30()) {                
                // bottom left
                mx1 = (int)(gp.px * gp.colWidth  +   gp.horizMargin );
                my1 = (int)(( this.getHeight()  ) * triangleHeight + gp.vertPixelPosition );
                mx1 -= (gp.edgeLength  *  3) / 4;
                my1 += (int)(triangleHeight / 2);
                double units = getHeight() + 1 ;
                double w = getWidth() - getMinX() + 1;
                
                w++;
                w /= 2;
                if (units > w)
                    units = w + 0.5;
                // top right
                mx2 = mx1 + (int)(((gp.edgeLength )  * units * 3) / 2);
                my2 = my1 - (int)(triangleHeight * units);
                gp.g.drawLine(mx1, my1, mx2, my2);
            }     
            if (testMH()) {
                mx1 = (int)(gp.px * gp.colWidth  +  + gp.horizMargin );
                mx2 = mx1 + (gp.edgeLength * (getWidth() + 1)) / 2 ;
                my1 = (int)(( this.getHeight()  / 2) * triangleHeight + gp.vertPixelPosition );
                my2 = my1;
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testMV()) {
                int mid = ( getMinX() + getWidth() ) / 2;
                CoordPair cp = new CoordPair(mid, 1);
                mx1 = getXcentre(gp, cp);
                mx2 = mx1;
                my1 = (int)(getYbottom(gp, cp) + triangleHeight / 2);
                my2 = (int)(my1 - triangleHeight * (getHeight() + 1));
                gp.g.drawLine(mx1, my1, mx2, my2);
            }
            if (testR180()) {
                gp.g.fillOval(gp.px *  gp.colWidth + ((this.getMinX() + this.getWidth()) * gp.edgeLength) / 4 - 3 + gp.horizMargin, 
                    gp.vertPixelPosition + (int)(((this.getMinY() + this.getHeight() - 1) * triangleHeight) / 2) - 3 , 6, 6);                
            } else if (testR120()) {
                double sumHoriz = 0, sumVert = 0;
                int upCounter = 0;
                for (CoordPair cp : array) {
                    if (pointsDown(cp))
                        continue;
                    int x = cp.getX(), y = cp.getY();
                    sumHoriz += gp.edgeLength * x / 2;
                    sumVert += (y - 1) * triangleHeight + triangleHeight / 3;
                    upCounter++;
                }
                gp.g.fillOval(gp.px *  gp.colWidth + (int)( sumHoriz / upCounter) - 3 + gp.horizMargin, 
                    gp.vertPixelPosition + (int)((( this.getHeight() ) * triangleHeight - sumVert / upCounter)) - 3 , 6, 6);  
            }
        }
        
    }  
    boolean pointsUp(int x, int y) {
        return ((x + y) % 2 == 0);
    }
    boolean pointsUp(CoordPair cp) {
        return cp.parity() == 0;
    }
    public boolean pointsDown(CoordPair cp) {
        return cp.parity() != 0;
    }
    int getXcentre(GraphicsParameters gp, CoordPair cp) {
        return (int)(gp.px * gp.colWidth  + (cp.getX()  ) * (gp.edgeLength / 2) + gp.horizMargin);
    }
    int getYtop(GraphicsParameters gp, CoordPair cp) {
        return (int)(( this.getHeight() - cp.getY() ) * triangleHeight + gp.vertPixelPosition);
    }
    int getYbottom(GraphicsParameters gp, CoordPair cp) {
        return (int)(( this.getHeight() - cp.getY() + 1 ) * triangleHeight + gp.vertPixelPosition);
    }
    int getYcentre(GraphicsParameters gp, CoordPair cp) {
        if (pointsUp(cp))
            return getYtop( gp,  cp);
        else
            return getYbottom( gp,  cp) ;
        
    }
    int getYother(GraphicsParameters gp, CoordPair cp) {
        if (pointsDown(cp))
            return getYtop( gp,  cp);
        else
            return getYbottom( gp,  cp) ;
        
    }
    
    void display(GraphicsParameters gp, CoordPair cp) {
        display(gp, cp, null);
    }
    void display(GraphicsParameters gp, CoordPair cp, Color c) {
       
        //int parity = cp.parity();
        int xCentre =  getXcentre( gp,  cp);
        int yCentre = getYcentre( gp,  cp);
        int yOther = getYother( gp,  cp);
        int xRight = xCentre + gp.edgeLength / 2;
        int yRight = yOther;
        int xLeft = xCentre - gp.edgeLength / 2;
        int yLeft = yOther;
        
        int xa[] = {xCentre, xRight, xLeft };
        int ya[] = {yCentre, yRight, yLeft };
        
        
        if (c == null)
            gp.g.setColor( this.getGraphicsColour(gp, cp, gp.applyCb) );
        else
            gp.g.setColor(c);
        gp.g.fillPolygon(xa, ya, 3);
       
        
        gp.g.setColor( Color.BLACK );        
        gp.g.drawLine(xCentre, yCentre, xRight, yRight);
        gp.g.drawLine(xCentre, yCentre, xLeft, yLeft);
        gp.g.drawLine(xRight, yRight, xLeft, yLeft);
        
        
    }   
    public  double getCellHeight(int squareSize) { return (double)(squareSize * Math.sqrt(3) / 2); }

   
    public ArrayList<CoordPair> getClose(CoordPair cp, boolean within, boolean pseudo) {
        if (pseudo) {
            int qq = 1 / 0;
        }
        ArrayList<CoordPair> a = new ArrayList<>();
        getClose(a, cp, 1, 0, within);
        getClose(a, cp, -1, 0, within);
        if (pointsDown(cp))
            getClose(a, cp, 0, 1, within);
        else
            getClose(a, cp, 0, -1, within);
        return a;
    }    
    public int outside(int[][] flags, int from, int to) {
        int ret = 0;
        for (int x = 1 ; x <= maxX; x++) {            
            if (flags[x][1] == from && ((x + 1) % 2 == 0)) {
                flags[x][1] = to;
                ret++;
                
            }
            
            if (flags[x][maxY] == from && ((x + maxY) % 2 == 1)) {
                flags[x][maxY] = to;
                ret++;
            }  
            
        }
        
        for (int y = 1 ; y <= maxY ; y++) {            
            if (flags[1][y] == from) {
                flags[1][y] = to;
                ret++;
            }
            if (flags[maxX][y] == from) {
                flags[maxX][y] = to;
                ret++;
            }            
        }

        
        return ret;
    }
    protected int perimeter(CoordPair cp) {
        int ret = 0;
        ret += perimeter(cp, 1, 0);
        ret += perimeter(cp, -1, 0); // !!
        if (pointsDown(cp))
            ret += perimeter(cp, 0, 1);
        else
            ret += perimeter(cp, 0, -1);
        return ret;
    }    
    public BigInteger calcBorder() {
        for (int i = 0; i < size(); i++) {
            CoordPair cp = getCoords(i);
            if (pointsUp(cp)) {
                if (!contains(cp.getX() - 1, cp.getY())) {
                    return calcBorder(cp.getX(), cp.getY(), UPRIGHT);            // ok    
                }
                if (!contains(cp.getX() + 1, cp.getY())) {
                    return calcBorder(cp.getX(), cp.getY(), DOWNRIGHT);               // ok 
                }
                if (!contains(cp.getX(), cp.getY() - 1)) {
                    return calcBorder(cp.getX(), cp.getY(), LEFT);                // ok
                }
                
            } else {
                if (!contains(cp.getX() - 1, cp.getY())) {
                    return calcBorder(cp.getX(), cp.getY(), UPLEFT);                
                }
                if (!contains(cp.getX() + 1, cp.getY())) {
                    return calcBorder(cp.getX(), cp.getY(), DOWNLEFT);                
                }
                if (!contains(cp.getX(), cp.getY() + 1)) {
                    return calcBorder(cp.getX(), cp.getY(), RIGHT);                
                }               
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
            /*
 1           
       The x marks the surely missing triangle     
         __\   /__
             ^
         x  / \
            
            */
            if (v == UPRIGHT) {
                dx = new int[] { -1, 0, 1, 1};
                dy = new int[] { 1, 1, 1, 0};
            /*
        
             ^
             x
            ___\/_
               /\
               
                
            */                     
            } else if (v == RIGHT) {
                dx = new int[] { 1, 2, 2, 1};
                dy = new int[] { 1, 1, 0, 0};                               
            /*

        
             ^
            / \ x
              _\/_
               /\
                
                
            */               
            } else if (v == DOWNRIGHT) {
                dx = new int[] { 2, 2, 1, 0};
                dy = new int[] { 0, -1, -1, -1};
            /*
         
            
       -1,0    / x
            _\/_
             /\ +1,-1
       -1,-1 0,-1  
            */                     
            } else if (v == DOWNLEFT) {
                dx = new int[] { 1, 0, -1, -1};
                dy = new int[] { -1, -1, -1, 0};              
            /*
3
        
             ^
            / \ 
         _\/___\
          /\
              x  
                
            */                     
            } else if (v == LEFT) {
                dx = new int[] { -1, -2, -2, -1};
                dy = new int[] { -1, -1, 0, 0}; 


            /*
6
           -1,1 
      -2,1       0,1
           _\/_
            /\  
        -2,0 x\
                
            */                  
            } else  { // UPLEFT
                dx = new int[] { -2, -2, -1, 0};
                dy = new int[] { 0, 1, 1, 1}; 
                
            } 
            
            if (!this.contains(x + dx[3], y + dy[3])) {
                v += 2;
            } else if (!this.contains(x + dx[2], y + dy[2])) {
                v += 1;
                x += dx[3];
                y += dy[3];
            } else if (!this.contains(x + dx[1], y + dy[1])) {
                v += 0;
                x += dx[2];
                y += dy[2];
            } else if (!this.contains(x + dx[0], y + dy[0])) {
                v -= 1;
                x += dx[1];
                y += dy[1];
            } else {
                v -= 2;
                x += dx[0];
                y += dy[0];
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
    /*
    protected boolean groupPresent(CoordPair cp) {
        if (cp.parity() == 0) { 
            if (contains(cp) && 
                contains(cp.getX() - 1, cp.getY()) && 
                contains(cp.getX() + 1, cp.getY()) && 
                contains(cp.getX() - 1, cp.getY() + 1) &&
                contains(cp.getX()    , cp.getY() + 1) &&
                contains(cp.getX() + 1, cp.getY() + 1) 
                    )
                return true;
        } else {
            if (contains(cp) && 
                contains(cp.getX() + 1, cp.getY()) && 
                contains(cp.getX() + 2, cp.getY()) && 
                contains(cp.getX()    , cp.getY() + 1) &&
                contains(cp.getX() + 1, cp.getY() + 1) &&
                contains(cp.getX() + 2, cp.getY() + 1) 
                    )
                return true;            
        }
        return false;
    }   
*/
    protected  int maximalPerimeter() { return this.size()  + 2; }
    // returns the minimum perimeterOrSurface of a polyiamond of this size: 2*ceiling((n + sqrt(6*n))/2) - n 
    public int minPerimeterOrSurface() {
        int n = size();
        double s = Math.sqrt(6 * n);
        int ret = ((int)(Math.ceil((n + s) / 2))) * 2 - n;
        
        return ret;
        
    }   
    int willTouch(int x, int y) {
        int ret = 0;
        int parity = (x + y) % 2;
        if (contains(x - 1, y)) ret++;
        if (contains(x + 1, y)) ret++;
        if (parity == 0 && contains(x, y - 1)) ret++;
        if (parity == 1 && contains(x, y + 1)) ret++;
        return ret;
        
    }    
    CoordPair lowestOnSextant() {
        int lowestX = 9999, lowestY = 9999;
        CoordPair lowestCP = null;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (x - y <= 1) {
                if (x < lowestX || (x == lowestX && y < lowestY)) {
                    lowestCP = cp;
                    lowestX = x;
                    lowestY = y;
                }
            }
        }
        return lowestCP;
    }
    public int countAdjacencies() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (pointsUp(cp)) {
                if (contains(x + 1, y)) ret++;
                if (contains(x - 1, y)) ret++;
                if (contains(x , y - 1)) ret++;
                
            } else {
                if (contains(x + 1, y)) ret++;
                if (contains(x - 1, y)) ret++;
                if (contains(x , y + 1)) ret++;                
            }
        }
        return ret;
    }
    private int category = 0;
    
    //private int verticalAxisLocationC, horizontalAxisLocationC;
    //private int verticalAxisLocationV, horizontalAxisLocationV;
    protected PolyiamondSymmetryCalculator psc;
    String axisType = null; 
    boolean flatM;
    public String getAxisType() {
        return axisType;
    }
    public PolyiamondSymmetryCalculator getPSC() {
        getCategory();
        return psc;
    }
    public void setPSC(PolyiamondSymmetryCalculator psc) {
        this.psc = psc;
    }
    public int getCategory() {
        if (category != 0)
            return category;
        category = innerGetCategory(); 
        return category;
    }
    private int innerGetCategory() {
        if (this.testAsym())
            return ASYM;
        if (this.testStrictlyMa())
            return MA;
        if (this.testStrictlyMu())
            return MU;
        determineMVC();
        if (this.testStrictlyM2()) {
            
            if (axisType.equals("V"))
                return M2V;
            else 
                return M2M;            
        }
        if (this.testStrictlyM3a()) {
            
            return M3A;
        }
        if (this.testStrictlyM3u()) {
            
            if (axisType.equals("V"))
                return M3UV;
            else 
                return M3UC;
        }
        if (this.testStrictlyR120()) {
           
            if (axisType.equals("C"))
                return R120C;
            else
                return R120V;
        }
        if (this.testStrictlyR180()) {
            
            if (axisType.equals("V"))
                return R180V;
            else
                return R180M;
        }
        if (this.testStrictlyR60()) {
           
            return R60;
        }
        if (this.testAll()) {
           
            return ALL;
        }
        int qq = 1 / 0;
        return qq;
    }   
    void determineMVC() {
        int verticalAxisLocationSixths, horizontalAxisLocationHalves;
               
        
        int minY = getMinY();
        int totH = 0, totV = 0;
        int s = size();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            totH += x  ;
            if (pointsDown(cp))
                totV +=  1;
            totV += 3 * (y - minY ) + 1;
        }
        int v6 = totV * 2;
        if (v6 % s != 0) {
            System.err.println(this.drawing());
        }
        int h2 = totH * 2;
        verticalAxisLocationSixths = div(v6, s);
        horizontalAxisLocationHalves = div(h2, s);
        int v6dm = verticalAxisLocationSixths % 6;
        if (v6dm == 2 || v6dm == 4) {
            axisType = "C";
            //horizontalAxisLocationC   =  div(horizontalAxisLocationHalves, 2) ;
            //verticalAxisLocationC = verticalAxisLocationSixths / 6 + 1;            
            //return;
        } else        if (v6dm == 3) {
            axisType =  "M";
            flatM = false;
            //return;
        } else        if (v6dm != 0) {
            int qq = 1 / 0;
        } else {
            int v6dd = verticalAxisLocationSixths / 6;

            if (pointsUp(horizontalAxisLocationHalves / 2 , v6dd + minY - 1)) {
                axisType =  "V";
                //horizontalAxisLocationV   =  div(horizontalAxisLocationHalves, 2) ;
                //verticalAxisLocationV = div(verticalAxisLocationSixths , 6 );                      
                
            } else {
                axisType =  "M";
                flatM = true;
            }
        }
        psc = new PolyiamondSymmetryCalculator(axisType, horizontalAxisLocationHalves, verticalAxisLocationSixths );
    }   
    
    public boolean getFlatM() {
        return flatM;
    }
    int div(int a, int b) {
        if (a % b == 0) {
            return a / b;
        }
        return 1 / 0;
    }

    public static String getCategoryString(int c) {
        return categoryString[c];
    }
}
