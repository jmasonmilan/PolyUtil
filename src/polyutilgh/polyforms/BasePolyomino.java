/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;
import java.awt.BasicStroke;
import java.util.*;
import java.math.BigInteger;
import java.awt.Color;
import java.awt.Graphics2D;
import java.math.*;
import polyutilgh.CoordPair;
import polyutilgh.Edge;
import polyutilgh.GraphicsParameters;
import polyutilgh.PolySet;
import polyutilgh.polygons.*;
/**
 *
 * @author jmason
 */
public abstract class BasePolyomino extends PolyformWC {   
    public ArrayList<String> leftSides, topSides, rightSides, bottomSides;
    double centreX, centreY, diameter;
    double centreX2, centreY2, diameter2;
    boolean displayCentre;
    public BasePolyomino() {
        
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
            x--;
            y++;
            if (x < 1) {
                x = y;
                y = 1;
            }
        }        
    }    
    abstract public boolean areAdjacent(CoordPair cp1, CoordPair cp2);
    public ConnectedGraph getConnectednessGraph(boolean seal) {
        ConnectedGraph ret = new ConnectedGraph();
        ret.init(size());
        for (int i = 0; i < size(); i++)
            for (int j = 0; j < size(); j++) {
                if (i == j)
                    continue;
                if (areAdjacent(array.get(i), array.get(j)))
                    ret.array.get(i).set(j);
            }
        if (seal)
            ret.seal();
        return ret;        
    }
    public ConnectedGraph getConnectednessGraph() {
        ConnectedGraph ret = getConnectednessGraph(true);
        
        return ret;
    }
    public int getBit(int x, int y) {
        if (x < 1 || y < 1) {
            int qq = 1 / 0;
        }
        return ((x + y - 1)*(x + y - 2)/2) + y - 1;
    }
    public int lowest(int x) {
        for (int y = 1 ; y <= this.getHeight(); y++)
            if (this.contains(x, y))
                return y;
        //int qq = 1 / 0;
        return 0; // will not happen for intact polyomino
    }
    public int highest(int x) {
        for (int y = this.getHeight() ; y >= 1; y--)
            if (this.contains(x, y))
                return y;
        //int qq = 1 / 0;
        return 0; // will not happen for intact polyomino
    }
    public int leftmost(int y) {
        for (int x = 1 ; x <= this.getWidth(); x++)
            if (this.contains(x, y))
                return x;
        int qq = 1 / 0;
        return qq;
    }
    public int rightmost(int y) {
        for (int x = this.getWidth() ; x >= 1; x--)
            if (this.contains(x, y))
                return x;
        int qq = 1 / 0;
        return qq;
    }

    public abstract Color getGraphicsColour(GraphicsParameters gp, CoordPair cp);    
    public abstract Color getGraphicsColour(GraphicsParameters gp, CoordPair cp, boolean applyCb);    
    protected int calcMaxPred() {
        int ret = array.size() / 4;
        ret *= 3;
        int rem = array.size() % 4;
        if (rem == 1 || rem == 2)
            ret++;
        else
            if (rem == 3)
                ret += 2;
        return ret;
    }   
    public boolean asym() { // see also testAsym
        return !testMirror() && !testRotate180();
    }
    public boolean testMirror() {
        return testMirror90() || testMirror45()  ;
    }
    public boolean testMirror90() {
        return testMirrorH() || testMirrorV();
    }
    public boolean testMirror90A() {
        boolean h = testMirrorH(), v = testMirrorV();
        boolean ret = (((maxY % 2) == 0) && h ) || (((maxX % 2) == 0) && v );
        return ret;
    }
    public boolean testMirror45() {
        return testMirrorSWNE() || testMirrorSENW();
    }
    public boolean testMirrorH() {
        int h = this.getHeight();
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains(cp.getX(), h - cp.getY() + 1))
                return false;
        }
        return true;
    }
    public boolean testMirrorH(int minY, int maxY) {
        
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains(cp.getX(), maxY - cp.getY() + minY))
                return false;
        }
        return true;
    }
    public boolean testMirrorV() {
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains(this.getWidth() - cp.getX() + 1, cp.getY()))
                return false;
        }
        return true;
    }
    public boolean testMirrorSENW() {
        if (this.getWidth() != this.getHeight())
            return false;
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains(this.getHeight() - cp.getY() + 1, 
                    this.getWidth() - cp.getX() + 1))
                return false;
        }
        return true;
    }
    public boolean testMirrorSWNE() {
        if (this.getWidth() != this.getHeight())
            return false;
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains(cp.getY() , cp.getX() ))
                return false;
        }
        return true;
    }
    public boolean testMirrorSWNE(int minX, int minY, int maxX, int maxY) {
        if (maxX - minX != maxY - minY)
            return false;
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains(cp.getY() - minY + minX , cp.getX() - minX + minY ))
                return false;
        }
        return true;
    }
    public boolean testRotate90() {
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains( this.getHeight() - cp.getY() + 1, cp.getX() ))
                return false;
        }
        return true;        
    }
    public boolean testRotate90(int minX, int minY, int maxX, int maxY) {
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains( maxY - cp.getY() + minY, cp.getX() ))
                return false;
        }
        return true;        
    }
    public boolean testRotate180() {
        for (int i = 0 ; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            if (!this.contains(this.getWidth() - cp.getX() + 1, 
                    this.getHeight() - cp.getY() + 1 ))
                return false;
        }
        return true;        
    }
    public boolean testRotate90Vertex() {
        return ((this.getWidth() % 2) == 0) && this.testRotate90();
    }
    public boolean testRotate180Centre() {
        return ((this.getWidth() % 2) == 1) && ((this.getHeight() % 2) == 1) && this.testRotate180();
    }
    public boolean testRotate180MidEdge() {
        return ((this.getWidth() % 2) +  (this.getHeight() % 2) == 1) && this.testRotate180();
    }    
    public boolean testAsym() {
        return !(testMirror90() || testMirror45() || testRotate180());
    }
    public boolean test1M90exclusive() {
        return testMirror90() && !(testMirror45() || testRotate180());
    }
    public boolean test1M45exclusive() {
        return testMirror45() && !(testMirror90() || testRotate180());
    }
    public boolean testR180exclusive() {
        return testRotate180() && !(testMirror90() || testMirror45() || testRotate90());
    }
    public boolean test2M90R180exclusive() { // includes implicit R180
        return testMirrorV() && testMirrorH() && !(testMirror45() || testRotate90());
    }
    public boolean test2M45R180exclusive() { // includes implicit R180
        return testMirrorSENW() && testMirrorSWNE() && !(testMirror90() || testRotate90());
    }
    public boolean testR90exclusive() { // includes implicit R180
        return testRotate90() && !(testMirror90() || testMirror45());
    }
    public boolean testAllSym() { // includes implicit R180
        return testRotate90() && testMirror90();
    }
    public String getW3Category() {
        if (this.getWidth() != 3)
            return "";        
        boolean sud = testMirrorH() , slr = testMirrorV(), srot = testRotate180();
        String top = getW3String(getHeight());
        String bottom = getW3String(1);
        String category = "w3_" + top + "_" + bottom;
        if (slr)
            category += "_slr";
        if (sud)
            category += "_sud";
        if (srot)
            category += "_srot";
        return category;
    }
    String getW3String(int y) {
        String ret = "";
        if (contains(1, y))
            ret += "l";
        if (contains(2, y))
            ret += "c";
        if (contains(3, y))
            ret += "r";
        return ret;
    }
    public String getW3CategoryABC() {
        if (this.getWidth() != 3)
            return "";        
        boolean sud = testMirrorH() , slr = testMirrorV(), srot = testRotate180();
        String top = getW3StringABC(getHeight());
        String bottom = getW3StringABC(1);
        String category = "w3_" + top + "_" + bottom;
        if (slr)
            category += "_slr";
        if (sud)
            category += "_sud";
        if (srot)
            category += "_srot";
        return category;
    }
    public String getW3CategoryABCh() {
        if (this.getWidth() != 3)
            return "";        
        boolean sud = testMirrorH() , slr = testMirrorV(), srot = testRotate180();
        String top = getW3StringABC(getHeight());
        String bottom = getW3StringABC(1);
        String category = "W3_3_abc_" + top + "_" + bottom;
        if (slr)
            category += "_slr";
        if (sud)
            category += "_sud";
        if (srot)
            category += "_srot";
        return category;
    }
    public String getW3CategoryABCnew(int width) {
        String letters = "abc";
        if (this.getWidth() != width)
            return "";        
        boolean sud = testMirrorH() , slr = testMirrorV(), srot = testRotate180();
        String top = getW3StringABC(getHeight());
        String bottom = getW3StringABC(1);
        String category = "W" + width + "_" + width + "_" + letters.substring(0, width) + "_" + top + "_" + bottom;
        if (slr)
            category += "_slr";
        if (sud)
            category += "_sud";
        if (srot)
            category += "_srot";
        return category;
    }
    String getW3StringABC(int y) {
        String ret = "";
        if (contains(1, y))
            ret += "a";
        if (contains(2, y))
            ret += "b";
        if (contains(3, y))
            ret += "c";
        return ret;
    }
    public String getW4Category() {
        if (this.getWidth() != 4)
            return "";        
        boolean sud = testMirrorH() , slr = testMirrorV(), srot = testRotate180();
        String top = getW4String(getHeight());
        String bottom = getW4String(1);
        String category = "w4_" + top + "_" + bottom;
        if (slr)
            category += "_slr";
        if (sud)
            category += "_sud";
        if (srot)
            category += "_srot";
        return category;
    }
    String getW4String(int y) {
        String ret = "";
        if (contains(1, y))
            ret += "a";
        if (contains(2, y))
            ret += "b";
        if (contains(3, y))
            ret += "c";
        if (contains(4, y))
            ret += "d";
        return ret;
    }
    public String getW2CategoryNew() {
        if (this.getWidth() != 2)
            return "";        
        boolean sud = testMirrorH() , slr = testMirrorV(), srot = testRotate180();
        String top = getW2String(getHeight());
        String bottom = getW2String(1);
        String category = "w2_" + top + "_" + bottom;
        if (slr)
            category += "_slr";
        if (sud)
            category += "_sud";
        if (srot)
            category += "_srot";
        return category;
    }
    String getW2String(int y) {
        String ret = "";
        if (contains(1, y))
            ret += "a";
        if (contains(2, y))
            ret += "b";
       
        return ret;
    }
    public String getW2Category() {
        if (this.getWidth() != 2)
            return "";
        boolean sym = testMirrorH() || testRotate180();
        int height = getHeight();
        int size = size();
        
        if (size == 2 * height)
            return "a";
        
        if (contains(1, 1) && contains(2, 1) && contains(1, height) && contains(2, height)) {
            if (sym) 
                return "d'";
            else
                return "d";
            
        }
        
        if (contains(1, 1) && contains(2, 1)) {
            
            return "e";
        }
        
        if (contains(1, height) && contains(2, height)) {
            
            return "f";
        }
        
        if (contains(1, 1) == contains(1, height)) {
            if (sym)
                return "g'";
            else
                return "g";
        }
        if (sym)
            return "h'";
        else
            return "h";                
    }
    public String getCategoryLetter() {
        int c = getCategory();
        return "ABCDEFGHIJKLMNOP".substring(c - 1, c);
    }
    public int getCategory() {
        int w = getWidth(), h = getHeight();
        if (this.testAllSym()) {
            if (h % 2 == 1)
                return 1;
            else
                return 2;
        }
        if (this.testR90exclusive()) {
            if (h % 2 == 1)
                return 3;
            else
                return 4;
        }
        if (this.test2M90R180exclusive()) {
            if ((w * h) % 2 == 1)
                return 5;
            if ((w + h) % 2 == 1)
                return 7;
            return 9;                        
        }
        if (this.test2M45R180exclusive()) {
            if (w % 2 == 1)
                return 6;
            else
                return 8;                        
        }
        if (this.testR180exclusive()) {
            if ((w * h) % 2 == 1)
                return 10;
            if ((w + h) % 2 == 1)
                return 11;
            return 12;                        
        }
        if (this.test1M90exclusive()) {
            if (!this.testMirror90A())
                return 13;
            else
                return 15;
        }
        if (this.test1M45exclusive())
            return 14;
        return 16;
        
    }
    public String getCategorySet() {
        int w = getWidth(), h = getHeight();
        String ret = "";
        if (this.testAllSym()) {
            if (h % 2 == 1)
                ret += "A";
            else
                ret += "B";
        }
        if (this.testRotate90()) {
            if (h % 2 == 1)
                ret += "C";
            else
                ret += "D";
        }
        if (this.testMirrorH() && this.testMirrorV()) {
            if ((w * h) % 2 == 1)
                ret += "E";
            else if ((w + h) % 2 == 1)
                ret += "G";
            else
                ret += "I";                        
        }
        if (this.testMirrorSENW() && this.testMirrorSWNE()) {
            if (w % 2 == 1)
                ret += "F";
            else
                ret += "H";                        
        }
        if (this.testRotate180()) {
            if ((w * h) % 2 == 1)
                ret += "J";
            else if ((w + h) % 2 == 1)
                ret += "K";
            else
                ret += "L";                        
        }
        if (this.testMirror90()) {
            if (!this.testMirror90A())
                ret += "M";
            else
                ret += "O";
        }
        if (this.testMirror45())
            ret += "N";
        return ret;
        
    }

    protected int perimeter(CoordPair cp) {
        int ret = 0;
        ret += perimeter(cp, 1, 0);
        ret += perimeter(cp, -1, 0);
        ret += perimeter(cp, 0, 1);
        ret += perimeter(cp, 0, -1);
        return ret;
    }
    public boolean smooth() {
        for (int i = 0 ; i < array.size() ; i++)
            if (perimeter(array.get(i)) == 3)
                return false;
        return true;
    }
    public int boundingBoxPerimeter() {
        return 2 * this.getWidth() + 2 * this.getHeight();
    }
    public boolean convex() {
        if (!selfavoiding()) 
            return false;        
        return (perimeterOrSurface() == boundingBoxPerimeter());
    }
    public boolean staircase() {
        if (!convex())
            return false;
        if (this.contains(1,1) && this.contains(maxX,maxY))
            return true;
        if (this.contains(maxX,1) && this.contains(1,maxY))
            return true;
        return false;
    }
    public boolean ferrers() {
        if (!selfavoiding()) {
            return false;
        }
        int cc = 0;
        if (this.contains(1,1))
            cc++;
        if (this.contains(1,maxY))
            cc++;
        if (this.contains(maxX,1))
            cc++;
        if (this.contains(maxX,maxY))
            cc++;
        return (cc > 3);
    }
    public boolean selfavoiding() {
        return countHoles() == 0;
    }
    public boolean convexTreelike() {
        if (!convex())
            return false;
        
        return treelike();
    }
    public int linesNeeded() {
        if (array.size() == 0)
            return 0;
        int ret = 0;
        for (int i = 0; i < array.size(); i++) {     
            ret += linesNeeded(array.get(i));
        }  
        return ret;
    }
    int linesNeeded(CoordPair cp) {
        int ret =  0;
        int x = cp.getX(), y = cp.getY();
        if (!contains(x + 1, y) && (!contains(x, y + 1) || (contains(x, y + 1) && contains(x + 1, y + 1))))
            ret++;
        if (!contains( x, y + 1) && (!contains(x - 1, y ) || (contains(x - 1, y) && contains(x - 1, y + 1))))
            ret++;
        if (!contains(x, y - 1) && (!contains(x - 1, y) || (contains(x - 1, y) && contains(x - 1, y - 1))))
            ret++;
        if (!contains(x - 1, y) && (!contains(x, y + 1 ) || (contains(x, y + 1) && contains(x - 1, y + 1))))
            ret++;
        
        return ret;
    }
    /*
    boolean lineInPerimeter(int x1, int y1, int x2, int y2) {
        if (contains(x1, y1) && !contains(x2, y2))
            return true;
        if (!contains(x1, y1) && contains(x2, y2))
            return true;
        return false;
    }
    */
    // there have been doubts about this routine
    public BigInteger calcBorder() {
        //System.err.println("dubious");
        //System.exit(1);    
        if (array.size() == 0)
            return BigInteger.ZERO;
        for (int i = 0; i < array.size(); i++) {
            CoordPair cp = getCoords(i);
            if (!contains(cp.getX() - 1, cp.getY())) {
                return calcBorder(cp.getX(), cp.getY(), 1);                
            }
            if (!contains(cp.getX(), cp.getY() + 1)) {
                return calcBorder(cp.getX(), cp.getY(), 2);                
            }
            if (!contains(cp.getX() + 1, cp.getY())) {
                return calcBorder(cp.getX(), cp.getY(), 3);                
            }
            if (!contains(cp.getX(), cp.getY() - 1)) {
                return calcBorder(cp.getX(), cp.getY(), 4);                
            }
            
        }  
        int i = 1 / 0;
        return null;
    }
    BigInteger calcBorder(int x, int y, int v) {

        int startX = x, startY = y, startV = v;
        String ret = "" + v;
        int per = perimeterOrSurface();
        if (array.size() == 0)
            return BigInteger.ZERO;
        for (int i = 1 ; i < per ; i++) {
            if (!contains(x, y)) {
                int qq = 1 / 0;
            }
            if (v == 1) {
                if (this.contains(x - 1, y + 1) && this.contains(x, y + 1)) { 
                    x--; y++; v = 4; ret += v; }
                else if (this.contains(x, y + 1)) { 
                    y++; v = 1; ret += v; }
                else {
                    v = 2; ret += v;
                }
                
            } else if (v == 2) {
                if (this.contains(x + 1, y + 1) && this.contains(x + 1, y )) { 
                    x++; y++; v = 1; ret += v; ;}
                else if (this.contains(x + 1, y)) { 
                    x++; v = 2; ret += v; }
                else {
                    v = 3; ret += v;
                }
                
            } else if (v == 3) {
                if (this.contains(x + 1, y - 1) && this.contains(x, y - 1)) { 
                    x++; y--; v = 2; ret += v; }
                else if (this.contains(x, y - 1)) { 
                    y--; v = 3; ret += v; }
                else {
                    v = 4;
                    ret += v;
                }
                
            } else  {
                if (this.contains(x - 1, y - 1) && this.contains(x - 1, y )) { 
                    x--; y--; v = 3; ret += v; }
                else if (this.contains(x - 1, y)) { 
                    x--; v = 4; ret += v; }
                else {
                    v = 1; 
                    ret += v;
                }
                
            } 
            if (x == startX && y == startY && startV == v)
                break;
        }
        return new BigInteger(ret);
    }    

    // returns the minimum perimeterOrSurface of minChildrenTab polyomino of this size
    public int minPerimeterOrSurface() {
        int n = array.size();
        double s = Math.sqrt(n) * 2;
        int ret = ((int)(Math.ceil(s))) * 2;
        
        return ret;
        
    }

    public int nonUnitExternalEdges() {
        int ret = 0;
        ret += nonUnitExternalEdges(-1, 0,  0, -1 );
        ret += nonUnitExternalEdges( 1, 0,   0, -1 );
        ret += nonUnitExternalEdges(0, -1,  -1, 0);
        ret += nonUnitExternalEdges(0,  1,  -1, 0);
        return ret;
    }
    int nonUnitExternalEdges(int leftDx, int leftDy,  int belowDx, int belowDy) {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (contains(x + leftDx, y + leftDy))
                continue;
            boolean b1 = contains(x + leftDx + belowDx, y + leftDy + belowDy);
            boolean b2 = contains(x + belowDx, y + belowDy);
            if (b2 && !b1)
                continue;
            if (lengthExternalEdge(x, y, leftDx,  leftDy,  belowDx,  belowDy) > 1)
                ret++;
        }
        return ret;
    }
    int lengthExternalEdge(int x, int  y, int leftDx,  int leftDy, int belowDx,  int belowDy) {
        int ret = 0;
        while (true) {
            int nx = x + leftDx, ny = y + leftDy;
            if (contains(x, y) && !contains(nx, ny))
                ret++;
            else
                break;
            
            x -= belowDx;
            y -= belowDy;
            
        }
        return ret;
    }
    public boolean almostSquare() {
        return Math.abs(maxX - maxY) <= 1;
    }
    public boolean isPerimetral(Edge e) {
        CoordPair adj = e.getAdjacentCoordPair();
        return !contains(adj);
    }
    public PolySet genNew(int limit) {
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("1");
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            tryOne(i, ps, 1, 0);
            tryOne(i, ps, -1, 0);
            tryOne(i, ps, 0, 1);
            tryOne(i, ps, 0, -1);  
            if (limit > 0 && ps.size() > limit)
                break;
        }
        return ps;
    }
    public PolySet genNewWidth(int width, int height) {
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("1");
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            int x = array.get(i).getX(), y = array.get(i).getY();
            if (x < width)
                tryOne(i, ps, 1, 0);
            if (x > 1 || getWidth() < width)
                tryOne(i, ps, -1, 0);
            if (height == 0 || y < height)
                tryOne(i, ps, 0, 1);
            if (height == 0 || y > 1 || getHeight() < height)
                tryOne(i, ps, 0, -1);  
            
        }
        return ps;
    }
    public PolySet genStrip() {
        PolySet ps[] = this.stripGrowth();
        if (ps.length == 2)
            ps[0].add(ps[1]);
        return ps[0];
    }
    public PolySet genNewTreelike() {
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("1");
            return ps;
        }
        for (CoordPair cp : array) {
            tryOneNotTouching(cp, ps, 1, 0);
            tryOneNotTouching(cp, ps, -1, 0);
            tryOneNotTouching(cp, ps, 0, 1);
            tryOneNotTouching(cp, ps, 0, -1);            
        }
        return ps;
    }
    public CoordPair[] findStripExtremities() {
        if (size() == 1) {
            CoordPair[] ret = new CoordPair[1];
            ret[0] = array.get(0);
            return ret;
        } else {
            CoordPair[] ret = new CoordPair[2];
            int sofar = 0;
            for (CoordPair cp : array) {
                if (countNear(cp) == 1) {
                    ret[sofar] = cp;
                    sofar++;
                    if (sofar == 2)
                        return ret;
                }
            }
            return null;
        }           
    }
    public PolySet[] stripGrowth() {
        CoordPair extremities[] = this.findStripExtremities();
        PolySet[] ret = new PolySet[extremities.length];
        int sofar = 0;
        for (CoordPair cp : extremities) {
            ret[sofar] = stripGrowth(cp);
            sofar++;
        }
        return ret;
    }
    private PolySet stripGrowth(CoordPair cp) {
        PolySet ps = new PolySet();
        tryOneNotTouching(cp, ps, 1, 0);
        tryOneNotTouching(cp, ps, -1, 0);
        tryOneNotTouching(cp, ps, 0, 1);
        tryOneNotTouching(cp, ps, 0, -1);           
        return ps;
    }
    public PolySet genNewWithDomino() {
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("3");
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            tryTwo(i, ps, 1, 0);
            tryTwo(i, ps, -1, 0);
            tryTwo(i, ps, 0, 1);
            tryTwo(i, ps, 0, -1);
            
        }
        return ps;
    }
    public PolySet genNewWithAirplane() {
        PolySet ps = new PolySet();
        
        for (int i = 0 ; i < this.array.size() ; i++) {
            if (perimeter(array.get(i)) < 3)
                continue;
            tryAirplane(i, ps, 1, 0);
            tryAirplane(i, ps, -1, 0);
            tryAirplane(i, ps, 0, 1);
            tryAirplane(i, ps, 0, -1);
            
        }
        return ps;
    }
    public PolySet genNewMaintainParity() {
        int extraParity = measureParity();
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("1");
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            CoordPair cp = this.getCoords(i);
            /*
            alreday more odd than even; so extraParity = 1
            so we want to add even
            so we want this to be odd
            */
            if (cp.parity() != extraParity) // current is odd, then new would be even
                continue;
            tryOne(i, ps, 1, 0);
            tryOne(i, ps, -1, 0);
            tryOne(i, ps, 0, 1);
            tryOne(i, ps, 0, -1);
            
        }
        return ps;
    }
    public int stripFertility() {
        PolySet psa[] = this.stripGrowth();
        int ret = 0;
        for (int i = 0; i < psa.length; i++) {
            PolySet ps = psa[i];
            if (ps.size() > 0)
                ret++;
        }
        return ret;
    }
    int measureParity() {
        int countE = 0, countO = 0;
        for (int i = 0; i < array.size(); i++ ) {
            if (this.array.get(i).parity() == 1)
                countO++;
            else
                countE++;            
        }
        if (countO > countE)
            return 1;
        return 0;
    }
    boolean tryOne(int i, PolySet ps, int dx, int dy) {
        int x = array.get(i).getX() + dx, y = array.get(i).getY() + dy;
        Polyomino p = addOneCoords(x, y );
        if (p != null) {
            ps.addUniques(p);
            return true;
        }
        return false;
    }
    void tryOneNotTouching(CoordPair cp, PolySet ps, int dx, int dy) {
        if (wouldTouch(cp, dx, dy))
            return;
        int x = cp.getX() + dx, y = cp.getY() + dy;
        Polyomino p = addOneCoords(x, y );
        if (p != null) 
            ps.addUniques(p);
    }
    boolean wouldTouch(CoordPair cp, int dx, int dy) {
        int x = cp.getX(), y = cp.getY();
        if (dx == 1) {
            return wouldTouch(x, y, 1, 1) || wouldTouch(x, y, 1, -1) || wouldTouch(x, y, 2, 0) ;
        }
        if (dx == -1) {
            return wouldTouch(x, y, -1, 1) || wouldTouch(x, y, -1, -1) || wouldTouch(x, y, -2, 0) ;
        }
        if (dy == 1) {
            return wouldTouch(x, y, 1, 1) || wouldTouch(x, y, -1, 1) || wouldTouch(x, y, 0, 2) ;
        }
         return wouldTouch(x, y, 1, -1) || wouldTouch(x, y, -1, -1) || wouldTouch(x, y, 0, -2) ;   
        
    }
    boolean wouldTouch(int x, int y, int dx, int dy) {
        return contains(x + dx, y + dy);
    }
    Polyomino addOneCoords(int x, int y) {
        return addOneCoords( x,  y,  false);
    }
    /*
    work with only limited set of combinations of values for dx and dy
    */
    Polyomino addOneCoords(int x, int y, boolean leaf) {
        
        if (x < 1 && y < 1) {
            Polyomino p1 = this.shiftRight(1 - x);
            p1 = p1.shiftUp(1 - y);
            return new Polyomino(p1.getRep().setBit(getBit(1, 1)));
        } else         if (x < 1) {
            Polyomino p1 = this.shiftRight(1 - x);
            return new Polyomino(p1.getRep().setBit(getBit(1, y)));
        } else        if (y < 1) {
            Polyomino p1 = this.shiftUp(1 - y);
            return new Polyomino(p1.getRep().setBit(getBit(x, 1)));
        } else   {
            CoordPair cp = new CoordPair(x, y);
            if (leaf && willTouch(x, y) != 1)
                return null;
            if (canAdd(cp))
                return new Polyomino(this.getRep().setBit(getBit(x, y)));
            else 
                return null;
        } 
    }    
    int willTouch(int x, int y) {
        int ret = 0;
        if (contains(x - 1, y)) ret++;
        if (contains(x + 1, y)) ret++;
        if (contains(x, y - 1)) ret++;
        if (contains(x, y + 1)) ret++;
        return ret;
        
    }
    void tryTwo(int i, PolySet ps, int dx, int dy) {
        int x = array.get(i).getX() + dx;
        int y = array.get(i).getY() + dy;    

        Polyomino p1 = this.addOneCoords(x, y);
        if (x < 1)
            x++;
        if (y < 1)
            y++;        
        if (p1 != null) {
            int n = p1.locate(x, y);
            p1.tryOne(n, ps, 1, 0);
            p1.tryOne(n, ps, -1, 0);
            p1.tryOne(n, ps, 0, 1);
            p1.tryOne(n, ps, 0, -1);
        }       
    }
    void tryAirplane(int i, PolySet ps, int dx, int dy) {
        if (!spaceForAirplane(i, dx, dy))
            return;
        Polyomino p = (Polyomino)this;
        int x = array.get(i).getX();
        int y = array.get(i).getY();    
        if (x == 1 && dx != 1) {
            int delta = 1;
            if (dx == -1)
                delta = 2;
            p = p.shiftRight(delta);
            x += delta;
        }
        if (y == 1 && dy != 1) {
            int delta = 1;
            if (dy == -1)
                delta = 2;            
            p = p.shiftUp(delta);
            y += delta;
        }        
        
        if (dy == 1) {
            p = new Polyomino(p.rep.setBit(getBit(x, y + 1)).setBit(getBit(x - 1, y + 1)).setBit(getBit(x + 1, y + 1)).setBit(getBit(x, y + 2)));
        }
        if (dy == -1) {
            p = new Polyomino(p.rep.setBit(getBit(x, y - 1)).setBit(getBit(x - 1, y - 1)).setBit(getBit(x + 1, y - 1)).setBit(getBit(x, y - 2)));
        }
        if (dx == 1) {
            p = new Polyomino(p.rep.setBit(getBit(x + 1, y)).setBit(getBit(x + 1, y - 1)).setBit(getBit(x + 1, y + 1)).setBit(getBit(x + 2, y)));
        }
        if (dx == -1) {
            p = new Polyomino(p.rep.setBit(getBit(x - 1, y)).setBit(getBit(x - 1, y - 1)).setBit(getBit(x - 1, y + 1)).setBit(getBit(x - 2, y)));
        }
        //System.out.println(p.drawing());
        ps.addUniques(p);
    }
    boolean spaceForAirplane(int i, int dx, int dy) {
        if (dy == 1)
            return spaceForSquare(i, 0, 1) && spaceForSquare(i, -1, 1) && spaceForSquare(i, 1, 1) && spaceForSquare(i, 0, 2);
        if (dy == -1)
            return spaceForSquare(i, 0, -1) && spaceForSquare(i, -1, -1) && spaceForSquare(i, 1, -1) && spaceForSquare(i, 0, -2);
        if (dx == 1)
            return spaceForSquare(i, 1, 0) && spaceForSquare(i, 1, 1) && spaceForSquare(i, 1, -1) && spaceForSquare(i, 2, 0);
        if (dx == -1)
            return spaceForSquare(i, -1, 0) && spaceForSquare(i, -1, 1) && spaceForSquare(i, -1, -1) && spaceForSquare(i, -2, 0);
        int qq = 1 / 0;
        return false;
    }
    boolean spaceForSquare(int i, int dx, int dy) {
        return !contains(array.get(i).getX() + dx, array.get(i).getY() + dy);
    }
    Polyomino shiftUp(int dy) {
        Polyomino np = (Polyomino)(maker());
        for (int i = 0 ; i < this.array.size() ; i++) {
            np.insert(new CoordPair(array.get(i).getX(), array.get(i).getY() + dy));
        }
        np.seal();
        return np;
    }
    Polyomino shiftRight(int dx) {
        Polyomino np = (Polyomino)(maker());
        for (int i = 0 ; i < this.array.size() ; i++) {
            np.insert(new CoordPair(array.get(i).getX() + dx, array.get(i).getY()));
        }
        np.seal();
        return np;
    }
    int lowestX() {
        int ret = 9999;
        for (CoordPair cp : array)
            if (cp.getY() == 1 && cp.getX() < ret)
                ret = cp.getX();
        return ret;
    }
    public void setCircle(double x, double y, double d) {
        centreX = x;
        centreY = y;
        diameter = d;
    }
    public void setCircle2(double x, double y, double d) {
        centreX2 = x;
        centreY2 = y;
        diameter2 = d;
    }
    public void setDisplayCentre() {
        displayCentre = true;
    }
    public void displayOnJpeg(GraphicsParameters gp) {
        // ( ((gp.px) * (gp.colWidth )) + cp.getX() - 1 ) * gp.squareSize + gp.horizMargin;
        int blah;
        blah = 1;
        if (gp.mec) {
            Polyomino p = (Polyomino)this;
            Circle c = p.getMEC();
            gp.centreX = c.getCentre().getX();
            gp.centreY = c.getCentre().getY();
            gp.diameter = c.getDiameter();
            
        }
        if (gp.lattice) {
            for (int x = 1 ; x <= this.getWidth() + 1; x++) {
                int xTop = ( (gp.px * gp.colWidth ) + x - 1 ) * gp.edgeLength2 + gp.horizMargin;
                int yTop = gp.vertPixelPosition - (gp.edgeLength2 / 2) ;
                int xBottom = xTop;
                int yBottom = yTop + gp.edgeLength2 * (this.getHeight() + 1);                
                gp.g.setColor( Color.LIGHT_GRAY );
                gp.g.drawLine(xTop, yTop, xBottom, yBottom);
            }
            for (int y = 1 ; y <= this.getHeight() + 1; y++) {
                int xLeft = ( ((gp.px) * (gp.colWidth ))  ) * gp.edgeLength2 - (gp.edgeLength2 / 2) + gp.horizMargin;
                int yLeft = (( y - 1 ) * gp.edgeLength2) + gp.vertPixelPosition;
                int xRight = xLeft + ( 1 + this.getWidth()) * gp.edgeLength2;
                int yRight = yLeft;                
                gp.g.setColor( Color.LIGHT_GRAY );
                gp.g.drawLine(xLeft, yLeft, xRight, yRight);
            }            
        }
        if (gp.border) {
            for (int x = 1 ; x <= this.getWidth() + 1; x += this.getWidth()) {
                int xTop = ( ((gp.px) * (gp.colWidth )) + x - 1) * gp.edgeLength2 + gp.horizMargin;
                int yTop = gp.vertPixelPosition;
                int xBottom = xTop;
                int yBottom = yTop + gp.edgeLength2 * (this.getHeight() );                
                gp.g.setColor( Color.LIGHT_GRAY );
                gp.g.drawLine(xTop, yTop, xBottom, yBottom);
            }
            for (int y = 1 ; y <= this.getHeight() + 1; y += this.getHeight()) {
                int xLeft = ( ((gp.px) * (gp.colWidth))  ) * gp.edgeLength2  + gp.horizMargin;
                int yLeft = (( y - 1 ) * gp.edgeLength2) + gp.vertPixelPosition;
                int xRight = xLeft + ( this.getWidth()) * gp.edgeLength2;
                int yRight = yLeft;                
                gp.g.setColor( Color.LIGHT_GRAY );
                gp.g.drawLine(xLeft, yLeft, xRight, yRight);
            }            
        }
        if (gp.connectedness) {
            for (int i = 0 ; i < array.size(); i++) {
                displayDot(gp, this.getCoords(i));
            }            
        } else {
            Polyomino enclosed = null;
            CoordPair cp = null;
            if (gp.enclosed != null) {
                ArrayList<PolyformWC> orientations = gp.enclosed.getFixedArray();
                for (PolyformWC e : orientations) {
                    cp = ((Polyomino)this).findEnclosurePosition(e);
                    if (cp == null)
                        continue;
                    enclosed = (Polyomino)e;
                    break;
                }
            }
            for (int i = 0 ; i < array.size(); i++) {
                CoordPair tcp = this.getCoords(i);
                if (gp.multiColour) {
                    display(gp, i, gp.colour);
                }
                else if (cp == null) {
                    display(gp, i);
                    
                }
                else {
                    if (enclosed.contains(tcp.getX() - cp.getX(), tcp.getY() - cp.getY()))
                        display(gp, i, gp.red);
                    else
                        display(gp, i, gp.green);
                }

            }
            if (gp.trivet)
                for (int i = 0 ; i < array.size(); i++) {
                    displayTrivet(gp, i);
                }            
        }
        
        if (gp.centre || (gp.showSym && this.testRotate180())) {
            gp.g.setColor( Color.BLACK );
            drawCentre(gp);
        }
        if (gp.hAxis || (gp.showSym && this.testMirrorH())) {
            gp.g.setColor( Color.BLACK );
            int xLeft = gp.px * gp.colWidth - (gp.edgeLength2 / 8) + gp.horizMargin;
            int yLeft = (this.getHeight() * gp.edgeLength2) / 2 + gp.vertPixelPosition;
            int xRight = xLeft + (  this.getWidth()) * gp.edgeLength2 + (gp.edgeLength2 / 4);
            int yRight = yLeft;                      
            gp.g.drawLine(xLeft, yLeft, xRight, yRight);
        }
        if (gp.vAxis || (gp.showSym && this.testMirrorV())) {
            int xTop =  gp.px * gp.colWidth + (this.getWidth() * gp.edgeLength2) / 2  + gp.horizMargin;
            int yTop = -(gp.edgeLength2 / 8) + gp.vertPixelPosition;
            int xBottom = xTop;
            int yBottom = yTop + ( this.getHeight()) * gp.edgeLength2 + (gp.edgeLength2 / 4);              
            gp.g.setColor( Color.BLACK );
            gp.g.drawLine(xTop, yTop, xBottom, yBottom);            
        }
        int hss = gp.edgeLength2 / 8;
        if (gp.swneAxis || (gp.showSym && this.testMirrorSWNE())) { // /
            
            int xLeft = ( ((gp.px) * (gp.colWidth ))  )  - hss + gp.horizMargin;
            int yLeft = gp.vertPixelPosition + (this.getHeight() * gp.edgeLength2) + hss ;
            int xRight = xLeft + ( this.getWidth()) * gp.edgeLength2 + 2 * hss;
            int yRight = yLeft - ( this.getHeight()) * gp.edgeLength2 - 2* hss;  
            gp.g.setColor( Color.BLACK );
            gp.g.drawLine(xLeft, yLeft, xRight, yRight);
        }
        if (gp.senwAxis || (gp.showSym && this.testMirrorSENW())) { // \
            int xLeft = ( ((gp.px) * (gp.colWidth ))  )  - hss + gp.horizMargin;
            int yLeft = - hss  + gp.vertPixelPosition;
            int xRight = xLeft + (  this.getWidth()) * gp.edgeLength2 + 2 * hss;
            int yRight = yLeft + (  this.getHeight()) * gp.edgeLength2 + 2 * hss;  
            gp.g.setColor( Color.BLACK );
            gp.g.drawLine(xLeft, yLeft, xRight, yRight);
        }
        /*
        double cX = centreX, cY = centreY, cD = diameter;
        if (gp.centreX != 0 && gp.centreY != 0) {
            cX = gp.centreX;
            cY = gp.centreY;
            cD = gp.diameter;
        }
        if (cX != 0 && cY != 0) {

        }       
        */
        drawCircle(gp.centreX, gp.centreY, gp.diameter, gp);
        drawCircle(centreX, centreY, diameter, gp);
        drawCircle(centreX2, centreY2, diameter2, gp);
        
    }    
    void drawCircle(double cX , double cY , double cD , GraphicsParameters gp) {
        if (cD == 0)
            return;
        int cornerX = (int)( ((gp.px) * (gp.colWidth )) +  gp.horizMargin + (cX  - cD / 2) * gp.edgeLength2);
        int cornerY = (int)(this.getHeight()  * gp.edgeLength2 + gp.vertPixelPosition - (cY + cD / 2) * gp.edgeLength2);
        gp.g.setColor( Color.BLACK );
        gp.g.drawOval(cornerX, cornerY, (int)(cD * gp.edgeLength2 - 1), (int)(cD * gp.edgeLength2 - 1));        
    }
    void drawCentre(GraphicsParameters gp) {
        gp.g.setColor( Color.BLACK );
        gp.g.fillOval(gp.px *  gp.colWidth + (this.getWidth() * gp.edgeLength2) / 2 - 3 + gp.horizMargin, 
                    gp.vertPixelPosition + (this.getHeight() * gp.edgeLength2) / 2 - 3 , 6, 6);
    }
    public boolean displayCBVertices() {
        return false;
    }    
    void display(GraphicsParameters gp, int i) {
        if (gp.fillYellow)
            display(gp, i, gp.yellow);
        else if (!gp.filling)
            display(gp, i, Color.WHITE);
        else
            display(gp, i, array.get(i).getDisplayColour());
    }
    void display(GraphicsParameters gp, int i, Color c) {
        CoordPair cp = array.get(i);
        //int xTopLeft = ((gp.px) * (gp.colWidth )) + ( cp.getX() - 1 ) * gp.edgeLength2 + gp.horizMargin;
        int xTopLeft = calcXtopLeft(gp, cp);
        int yTopLeft = ( this.getHeight() - cp.getY() ) * gp.edgeLength2 + gp.vertPixelPosition;
        int xTopRight = xTopLeft + gp.edgeLength2;
        int yTopRight = yTopLeft;
        int xBottomLeft = xTopLeft;
        int yBottomLeft = yTopLeft + gp.edgeLength2;
        int xBottomRight = xTopRight;
        int yBottomRight = yBottomLeft;
        if (c == null)
            gp.g.setColor( this.getGraphicsColour(gp, cp, gp.applyCb) );
        else
            gp.g.setColor(c);
        if (gp.ring != null) {
            if (gp.ring.contains(cp))
                gp.g.setColor(gp.yellow);
        }
        gp.g.fillRect(xTopLeft, yTopRight, gp.edgeLength2, gp.edgeLength2);
        
        gp.g.setColor( Color.BLACK );   
        if (gp.suppressInnerBorders) {
            if (!contains(cp.getX(), cp.getY() + 1))
                gp.g.drawLine(xTopLeft, yTopLeft, xTopRight, yTopRight);
            if (!contains(cp.getX() + 1, cp.getY() ))
                gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);
            if (!contains(cp.getX(), cp.getY() - 1))
                gp.g.drawLine(xBottomRight, yBottomRight, xBottomLeft, yBottomLeft);
            if (!contains(cp.getX() - 1, cp.getY() ))
                gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);            
        } else {
            gp.g.drawLine(xTopLeft, yTopLeft, xTopRight, yTopRight);
            boolean rightBorder = true;
            boolean leftBorder = true;
            if (gp.polybrick) {
                if ((cp.getX() + cp.getY() + lowestX()) % 2 == 1)
                    rightBorder = false;
                else
                    leftBorder = false;
            }
            if (rightBorder)
                gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);
            gp.g.drawLine(xBottomRight, yBottomRight, xBottomLeft, yBottomLeft);
            if (leftBorder)
                gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);
            
            if (topSides != null) {
                setLine(gp, this.topSides.get(i));
                gp.g.drawLine(xTopLeft, yTopLeft, xTopRight, yTopRight);
                setLine(gp, this.rightSides.get(i));
                gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);
                setLine(gp, this.bottomSides.get(i));
                gp.g.drawLine(xBottomRight, yBottomRight, xBottomLeft, yBottomLeft);
                setLine(gp, this.leftSides.get(i));
                gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);
                gp.g.setColor( Color.BLACK ); 
                gp.g.setStroke(new BasicStroke(gp.pen));
            }
        }
        
        if (displayCBVertices()) {
            int parity = (cp.getX() + cp.getY()) % 2;
            parity += ((CBVPolyomino)(this)).colourFirstSquare;
            parity = parity % 2;
            displayVertex(gp, xTopLeft, yTopLeft, parity);
            displayVertex(gp, xBottomLeft, yBottomLeft, 1 - parity);
            displayVertex(gp, xTopRight, yTopRight, 1 - parity);
            displayVertex(gp, xBottomRight, yBottomRight, parity);
            
        }
        if (this.displayCentre) {
            gp.g.setColor( Color.BLACK );
            gp.g.fillOval(xTopLeft + gp.edgeLength2 / 2 - 3, 
                    yTopLeft + gp.edgeLength2 / 2 - 3 , 6, 6);   
        }
    }     
    void setLine(GraphicsParameters gp , String mv) {
        if (mv.equals("m")) {
            gp.g.setColor( Color.RED ); 
            gp.g.setStroke(new BasicStroke(5));
        }
        else if (mv.equals("v")) {
            gp.g.setColor( Color.BLUE ); 
            gp.g.setStroke(new BasicStroke(5));
        }
        else {
            gp.g.setColor( Color.BLACK ); 
            gp.g.setStroke(new BasicStroke(gp.pen));
        }
            
    }
    int vSize = 5;
    void displayVertex(GraphicsParameters gp, int x, int y, int parity) {
        if (parity == 1)
            gp.g.setColor( Color.BLACK ); 
        else
            gp.g.setColor( Color.WHITE ); 
        gp.g.fillOval(x - vSize, y - vSize, vSize * 2, vSize * 2);
        if (parity == 1)
            gp.g.setColor( Color.BLACK ); 
        else
            gp.g.setColor( Color.WHITE ); 
        gp.g.setColor( Color.BLACK ); 
        gp.g.drawOval(x - vSize, y - vSize, vSize * 2, vSize * 2);       
    }
    void displayDot(GraphicsParameters gp, CoordPair cp) {
        gp.g.setColor( Color.BLACK );   
        int x = calcDotX( gp,  cp);
        int y = calcDotY( gp,  cp);
        gp.g.drawOval(x - 3, y - 3, 6, 6);
        ArrayList<CoordPair> adj = this.getAdjacentIn(cp);
        for (int i = 0 ; i < adj.size(); i++) {
            CoordPair ocp = adj.get(i);
            int ox = calcDotX( gp,  ocp);
            int oy = calcDotY( gp,  ocp);    
            gp.g.drawLine(x, y, ox, oy);
        }
    }
    
    void displayTrivet(GraphicsParameters gp, int i) {
        CoordPair cp = array.get(i);
        gp.g.setColor( Color.BLACK );   
        int x = calcDotX( gp,  cp);
        int y = calcDotY( gp,  cp);
        gp.g.drawOval(x - 3, y - 3, 6, 6);
        
        int j = i + 1;
        if (j == array.size())
            j = 0;
        CoordPair ocp = array.get(j);
        int ox = calcDotX( gp,  ocp);
        int oy = calcDotY( gp,  ocp);    
        gp.g.drawLine(x, y, ox, oy);
        
    } 
    int calcDotX(GraphicsParameters gp, CoordPair cp) {
        int xTopLeft = calcXtopLeft(gp, cp);
        return xTopLeft + gp.edgeLength2 / 2;
 
    }   
    int calcDotY(GraphicsParameters gp, CoordPair cp) {
        
        int yTopLeft = calcYtopLeft( gp,  cp);
        return yTopLeft + gp.edgeLength2 / 2;
 
    }      
    int calcXtopLeft(GraphicsParameters gp, CoordPair cp) {
        int xTopLeft = ((gp.px) * (gp.colWidth )) + ( cp.getX() - 1 ) * gp.edgeLength2 + gp.horizMargin;
        return xTopLeft;
    }
    int calcYtopLeft(GraphicsParameters gp, CoordPair cp) {
        int yTopLeft = ( this.getHeight() - cp.getY() ) * gp.edgeLength2 + gp.vertPixelPosition;
        return yTopLeft;
    }
    public ArrayList<CoordPair> getClose(CoordPair cp, boolean within, boolean pseudo) {
        ArrayList<CoordPair> a = new ArrayList<>();
        getClose(a, cp, 1, 0, within);
        getClose(a, cp, -1, 0, within);
        getClose(a, cp, 0, 1, within);
        getClose(a, cp, 0, -1, within);
        if (pseudo) {
            getClose(a, cp, 1, 1, within);
            getClose(a, cp, -1, 1, within);
            getClose(a, cp, 1, -1, within);
            getClose(a, cp, -1, -1, within);            
        }
        return a;
    }
    public int outside(int[][] flags, int from, int to) {
        int ret = 0;
        for (int x = 1 ; x <= maxX; x++) {            
            if (flags[x][1] == from) {
                flags[x][1] = to;
                ret++;
                //break;
            }
            
            if (flags[x][maxY] == from) {
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
    public boolean needs2CB() {
       
        if (array.size() % 2 != 0) {
            return true;
             
        }
        if (this.testMirror90A() || this.testRotate90Vertex() || this.testRotate180MidEdge()) {
            return false;
        }
        
        return true;            
    }
    public boolean needs2CBV() {
/*
M90C: Precisely 1 horizontal or vertical mirror symmetry with axis passing through the centre of some square.
Both90M: both horizontal or vertical mirror symmetry, consequent 180 degree rotational symmetry but not 90 degree rotational, with an axis at the middle of an edge of a square in the lattice.
Both90C: both horizontal or vertical mirror symmetry, consequent 180 degree rotational symmetry but not 90 degree rotational, with an axis at the centre of a square in the lattice.
R180M: 180 degree rotational symmetry and no other, with an axis at the middle of an edge of a square in the lattice.
AllC: all symmetries, with rotational symmetry about an axis at the centre of a square in the lattice.
R90C
        */       
        String letter = this.getCategoryLetter();
        switch(letter) {
            case "M":
            case "G":
            case "E":
            case "K":
            case "C":
            case "A":
                return false;
        }
        
        return true;            
    }
    public BasePolyomino align() {
        int dx = this.getMinX() - 1;
        int dy = this.getMinY() - 1;
        if (dx == 0 && dy == 0)
            return this;
        return this.shiftRight(-dx).shiftUp(-dy);
        
    }
    public BasePolyomino align(boolean seal) {
        int dx = this.getMinX() - 1;
        int dy = this.getMinY() - 1;
        BasePolyomino p = (BasePolyomino)maker();
        for (CoordPair cp : array) {
            p.insert(cp.add(-dx, -dy));
        }
        if (seal)
            p.seal();
        return p;
    }
    public void unsealedAlign() { // difficult ; it abuses input
        int dx = this.getMinX() - 1;
        int dy = this.getMinY() - 1;
        if (dx == 0 && dy == 0)
            return ;
        for (CoordPair cp : array) {
            cp.incrementX(-dx);
            cp.incrementY(-dy);
        }
        
    }    
    /*
    protected boolean groupPresent(CoordPair cp) {
        if (contains(cp) && 
                contains(cp.getX() + 1, cp.getY()) && 
                contains(cp.getX(), cp.getY() + 1) && 
                contains(cp.getX() + 1, cp.getY() + 1) )
            return true;
        return false;
    } 
*/
    protected  int maximalPerimeter() { return array.size() * 2 + 2; }
    int[] minChildrenTab = {1, 1, 2, 3, 1, 2, 3, 3, 3, 2, 3, 4, 2, 2, 4, 4, 2, 3, 4, 4, 3, 3, 5, 4, 2, 3, 5, 5, 3, 3, 5, 6, 3, 3, 5, 6, 3};
    public int minChildren() {
        
        return minChildrenTab[size()] ;
    }
    public int maxChildren() {
        int size = this.size();
        if (size ==1)
            return 1;
        if (size == 2 )
            return 2;
        if (size == 3)
            return 4;
        return size + size + 1;
    }   
    int[] minVerticesTab = {0, 4, 6, 8, 9, 11, 12, 14, 15, 16, 18, 19, 20, 22, 23, 24, 25, 27, 28};
    public int minVertices() {
        
        return minVerticesTab[size()] ;
    }
    Hashtable forVertices;
    public int countVertices() {
        int ret = 0;
        forVertices = new Hashtable();
        for (CoordPair cp : array) {
            ret += countVertices(cp, -1, 0);
            ret += countVertices(cp, 0, 0);
            ret += countVertices(cp, 0, -1);
            ret += countVertices(cp, -1, -1);
            
        }
        return ret;
    }
    int countVertices(CoordPair cp, int dx, int dy) {
        String key = (cp.getX() + dx) + ";" + (cp.getY() + dy);
        if (forVertices.get(key) == null) {
            forVertices.put(key, key);
            return 1;
        }
        return 0;
    }
    public int countInternalVertices() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (contains(x + 1, y) && contains(x, y + 1) && contains(x + 1, y + 1))
                ret++;
        }
        return ret;
    }
    public int countLeakyVertices() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (!contains(x + 1, y) && !contains(x, y + 1) && contains(x + 1, y + 1))
                ret++;
            if (!contains(x + 1, y) && !contains(x, y - 1) && contains(x + 1, y - 1))
                ret++;
            
        }
        return ret;
    }
    public int doubleHoleFormula() {
        int size = size();
        int internalVertices = countInternalVertices();
        int vertices = countVertices();
        int externalVertices = vertices - internalVertices;
              
        int leakyVertices = countLeakyVertices();
        int newDoubleFormula = 2 * (size + 1 - internalVertices) - externalVertices + leakyVertices;  
       
        return newDoubleFormula;
    }
    public boolean touches(CoordPair cp) {
        int x = cp.getX(), y = cp.getY();
        return contains(x + 1, y) || contains(x - 1, y) || contains(x, y + 1) || contains(x, y - 1);
    }
    int countNear(CoordPair cp) {
        int ret = 0;
        int x = cp.getX(), y = cp.getY();
        ret += countIf(x + 1, y);
        ret += countIf(x - 1, y);
        ret += countIf(x , y + 1);
        ret += countIf(x , y - 1);
        return ret;
        
    }

}
