package polyutilgh.polyforms;

import java.awt.Color;
import java.math.BigInteger;
import polyutilgh.*;
import java.util.*;

/**
 *
 * @author mason
 */
public abstract class BasePolycube extends PolyformWC3D {
    int[] deltaX = {0, 0, 1, 0, 0, -1};
    int[] deltaY = {0, 1, 0, 0, -1, 0};
    int[] deltaZ = {1, 0, 0, -1, 0, 0};
    protected BasePolycube() {
        array = new ArrayList<>();
    }
    public BasePolycube(BigInteger inValue) {
        rep = inValue;
        innerBuild(inValue);
        
        seal();  
    } 
      
    void innerBuild(BigInteger inValue) { // could do better?
        int x = 1, y = 1, z = 1;
        array = new ArrayList<>();
        int size = inValue.bitCount();
        for (int i = 0; size > 0 ; i++) {
            if (inValue.testBit(i)) {
                array.add(new CoordTriplet(x, y, z));
                size--;                
            }
            if (x == 1 && y == 1) {
                x += z;
                z = 1;
            } else {
                if (x == 1) {
                    // x = 1 + y + z - (1 + z + 1);
                    x = y - 1;
                    y = 1;
                    z++;
                } else {
                    x--;
                    y++;
                }
            }
        }  
        //checkIntact();
    }    
    // for testing above algorithm
    public CoordTriplet fromBit(int n) {
        int x = 1, y = 1, z = 1;
        
        for (int i = 0;  ; i++) {
            if (i == n) {
                return(new CoordTriplet(x, y, z));
                             
            }
            if (x == 1 && y == 1) {
                x += z;
                z = 1;
            } else {
                if (x == 1) {
                    // x = 1 + y + z - (1 + z + 1);
                    x = y - 1;
                    y = 1;
                    z++;
                } else {
                    x--;
                    y++;
                }
            }
        }          
    }

    public void seal() {
        if (rep == null) {
            rep = new BigInteger("0");
            for (int i = 0 ; i < array.size() ; i++) {
                int n = getBit(array.get(i).getX(), array.get(i).getY(), array.get(i).getZ());
                //System.out.println(ret);
                if (n < 0 ) {
                    int k = 1 / 0;
                }
                rep = rep.setBit(n);
                //System.out.println(ret);
            }
        }
        //setFirstSquare();   
        calcMax();         
        this.sealed = true;
    }       
    public int getBit(int x, int y, int z) {
        int n = x + y + z - 3;
        int tmpz = z;
        int ret = n * (n + 1) * (n + 2) / 6;
        int delta = 1;
        while (tmpz > 1){
            ret += n + delta;
            delta--;
            tmpz--;
        }
        ret += y - 1;
        return ret;
    }
    public String drawing() {
        return "";
    }
    public String getUnique() {
        //System.err.println("getUnique debug");
        //return this.getRep().toString();
        return minMirror(this);
    }
    public BasePolycube getUniquePoly() {
        //System.err.println("getUnique debug");
        //return this.getRep().toString();
        return minMirrorPoly(this);
    }
    public abstract BasePolycube maker();
    public abstract BasePolycube maker(BigInteger rep);
    public BasePolycube rotxy() {
        BasePolycube p = maker();
        for (CoordTriplet cp : array) {
            
            CoordTriplet ncp = cp.newValues(cp.getY(), 1 + maxX - cp.getX(), cp.getZ());
            p.add(ncp);
        }
        p.seal();
        if (this.getNumColour(0) != p.getNumColour(0)) {
            int qq = 1 / 0;
        }
        // debug

        return p;
    }
    public BasePolycube rotxz() {
        BasePolycube p = maker();
        for (CoordTriplet cp : array) {
            CoordTriplet ncp = cp.newValues(cp.getZ(), cp.getY(),  1 + maxX - cp.getX());
            p.add(ncp);
        }
        p.seal();
        if (this.getNumColour(0) != p.getNumColour(0)) {
            int qq = 1 / 0;
        }
        // debug

        return p;
    }
    public BasePolycube rotyz() {
        BasePolycube p = maker();
        for (CoordTriplet cp : array) {
            CoordTriplet ncp = cp.newValues(cp.getX(), cp.getZ(), 1 + maxX - cp.getY());
            p.add(ncp);
        }
        p.seal();
        if (this.getNumColour(0) != p.getNumColour(0)) {
            int qq = 1 / 0;
        }
        // debug

        return p;
    }
    public BasePolycube mirrorz() {
        BasePolycube p = maker();
        for (CoordTriplet cp : array) {
            CoordTriplet ncp = cp.newValues(cp.getX(), cp.getY(), 1 + maxZ - cp.getZ());
            p.add(ncp);
        }
        p.seal();
        if (this.getNumColour(0) != p.getNumColour(0)) {
            int qq = 1 / 0;
        }        
        return p;
    }
    public BasePolycube mirrorxz() {
        BasePolycube p = maker();
        for (CoordTriplet cp : array) {
            CoordTriplet ncp = cp.newValues(cp.getZ(), cp.getY(), cp.getX());
            p.add(ncp);
        }
        p.seal();
        if (this.getNumColour(0) != p.getNumColour(0)) {
            int qq = 1 / 0;
        }        
        return p;
    }
    public BasePolycube mirrorxy() {
        BasePolycube p = maker();
        for (CoordTriplet cp : array) {
            CoordTriplet ncp = cp.newValues(cp.getY(), cp.getX(), cp.getZ());
            p.add(ncp);
        }
        p.seal();
        if (this.getNumColour(0) != p.getNumColour(0)) {
            int qq = 1 / 0;
        }        
        return p;
    }
    BigInteger minRotate(BasePolycube p) {
        final BigInteger tmp1 = p.getRep();
        p = p.rotxy();
        final BigInteger tmp2 = p.getRep();
        p = p.rotxy();
        final BigInteger tmp3 = p.getRep();
        p = p.rotxy();
        final BigInteger tmp4 = p.getRep();
        p = p.mirrorz();
        final BigInteger tmp5 = p.getRep();
        p = p.rotxy();
        final BigInteger tmp6 = p.getRep();
        p = p.rotxy();
        final BigInteger tmp7 = p.getRep();
        p = p.rotxy();
        final BigInteger tmp8 = p.getRep();

        BigInteger ret = tmp1;
        if (ret.compareTo(tmp2) > 0) {
          ret = tmp2;
        }
        if (ret.compareTo(tmp3) > 0) {
          ret = tmp3;
        }
        if (ret.compareTo(tmp4) > 0) {
          ret = tmp4;
        }
        if (ret.compareTo(tmp5) > 0) {
          ret = tmp5;
        }
        if (ret.compareTo(tmp6) > 0) {
          ret = tmp6;
        }
        if (ret.compareTo(tmp7) > 0) {
          ret = tmp7;
        }
        if (ret.compareTo(tmp8) > 0) {
          ret = tmp8;
        }
        return ret;
    }
    BasePolycube minRotatePoly(BasePolycube p) {
        final BasePolycube tmp1 = p;
        p = p.rotxy();
        final BasePolycube tmp2 = p;
        p = p.rotxy();
        final BasePolycube tmp3 = p;
        p = p.rotxy();
        final BasePolycube tmp4 = p;
        p = p.mirrorz();
        final BasePolycube tmp5 = p;
        p = p.rotxy();
        final BasePolycube tmp6 = p;
        p = p.rotxy();
        final BasePolycube tmp7 = p;
        p = p.rotxy();
        final BasePolycube tmp8 = p;

        BasePolycube ret = tmp1;
        if (ret.getRep().compareTo(tmp2.getRep()) > 0) {
          ret = tmp2;
        }
        if (ret.getRep().compareTo(tmp3.getRep()) > 0) {
          ret = tmp3;
        }
        if (ret.getRep().compareTo(tmp4.getRep()) > 0) {
          ret = tmp4;
        }
        if (ret.getRep().compareTo(tmp5.getRep()) > 0) {
          ret = tmp5;
        }
        if (ret.getRep().compareTo(tmp6.getRep()) > 0) {
          ret = tmp6;
        }
        if (ret.getRep().compareTo(tmp7.getRep()) > 0) {
          ret = tmp7;
        }
        if (ret.getRep().compareTo(tmp8.getRep()) > 0) {
          ret = tmp8;
        }
        return ret;
    }
    void minRotate(BasePolycube p, ArrayList<String > h1, ArrayList<String > h2 ) {
        final String tmp1 = p.getRep().toString();
        p = p.rotxy();
        final String tmp2 = p.getRep().toString();
        p = p.rotxy();
        final String tmp3 = p.getRep().toString();
        p = p.rotxy();
        final String tmp4 = p.getRep().toString();
        p = p.mirrorz();
        final String tmp5 = p.getRep().toString();
        p = p.rotxy();
        final String tmp6 = p.getRep().toString();
        p = p.rotxy();
        final String tmp7 = p.getRep().toString();
        p = p.rotxy();
        final String tmp8 = p.getRep().toString();

        h1.add(tmp1);
        h1.add(tmp2);
        h1.add(tmp3);
        h1.add(tmp4);
        h2.add(tmp5);
        h2.add(tmp6);
        h2.add(tmp7);
        h2.add(tmp8);
        
    }

    private String minMirror(BasePolycube p) {
        int nc = p.getNumColour(1);
        final BigInteger tmp1 = minRotate(p);
        p = p.mirrorxy();
        final BigInteger tmp2 = minRotate(p);
        p = p.mirrorxz();
        final BigInteger tmp3 = minRotate(p);
        p = p.mirrorxy();
        final BigInteger tmp4 = minRotate(p);
        p = p.mirrorxz();
        final BigInteger tmp5 = minRotate(p);
        p = p.mirrorxy();
        final BigInteger tmp6 = minRotate(p);
        if (p.getNumColour(1) != nc) {
            int qq = 1 / 0;
        }
        BigInteger ret = tmp1;
        if (ret.compareTo(tmp2) > 0) {
          ret = tmp2;
        }
        if (ret.compareTo(tmp3) > 0) {
          ret = tmp3;
        }
        if (ret.compareTo(tmp4) > 0) {
          ret = tmp4;
        }
        if (ret.compareTo(tmp5) > 0) {
          ret = tmp5;
        }
        if (ret.compareTo(tmp6) > 0) {
          ret = tmp6;
        }
        return ret.toString();
    }    
    protected BasePolycube minMirrorPoly(BasePolycube p) {
        int nc = p.getNumColour(1);
        final BasePolycube tmp1 = minRotatePoly(p);
        p = p.mirrorxy();
        final BasePolycube tmp2 = minRotatePoly(p);
        p = p.mirrorxz();
        final BasePolycube tmp3 = minRotatePoly(p);
        p = p.mirrorxy();
        final BasePolycube tmp4 = minRotatePoly(p);
        p = p.mirrorxz();
        final BasePolycube tmp5 = minRotatePoly(p);
        p = p.mirrorxy();
        final BasePolycube tmp6 = minRotatePoly(p);
        if (p.getNumColour(1) != nc) {
            int qq = 1 / 0;
        }
        BasePolycube ret = tmp1;
        if (ret.getRep().compareTo(tmp2.getRep()) > 0) {
          ret = tmp2;
        }
        if (ret.getRep().compareTo(tmp3.getRep()) > 0) {
          ret = tmp3;
        }
        if (ret.getRep().compareTo(tmp4.getRep()) > 0) {
          ret = tmp4;
        }
        if (ret.getRep().compareTo(tmp5.getRep()) > 0) {
          ret = tmp5;
        }
        if (ret.getRep().compareTo(tmp6.getRep()) > 0) {
          ret = tmp6;
        }
        return ret;
    }        
    public int findTransformation(BasePolycube other) {
        ArrayList<BasePolycube> list = listAllTransformations();
        for (int i = 0; i < list.size() ; i++)
            if (other.equals(list.get(i)))
                return i;
        return 1 / 0;
            
    }
    public BasePolycube getTransformation(int n) {
        ArrayList<BasePolycube> list = listAllTransformations();
        return list.get(n);
    }
    public ArrayList<BasePolycube> listAllDistinctTransformations() {
        ArrayList<BasePolycube> list = listAllTransformations();
        ArrayList<BasePolycube> ret = new ArrayList<>();
        for (BasePolycube p : list)
            if (!ret.contains(p))
                ret.add(p);
        return ret;
    }
    public ArrayList listAllDistinctRotations() {
        /*
        ArrayList<BasePolycube> list = listAllTransformations();
        ArrayList<BasePolycube> ret = new ArrayList<>();
        for (int i = 0; i < 48; i += 8)
            for (int j = 0; j < 4; j++) {
                BasePolycube p = list.get(i + j);
                if (!ret.contains(p))
                    ret.add(p);
            }
        return ret;
        */
        ArrayList<BasePolycube> list = listAllRotations();
        ArrayList<BasePolycube> ret = new ArrayList<>();
        for (BasePolycube p : list)
            if (!ret.contains(p))
                ret.add(p);
        return ret;        
    }
    ArrayList<BasePolycube>  listAllRotations() {
        ArrayList<BasePolycube> ret = new ArrayList<>();
        ArrayList<BasePolycube> list1 = list4();
        ret.addAll(list1);
        for (int i = 0; i < 4; i++) {
            BasePolycube p = list1.get(i).rotxz();
            ret.addAll(p.list4());
        }
        ret.addAll((this.rotxz().rotxz()).list4());
        return ret;
    }
    ArrayList<BasePolycube>  list4() {
        ArrayList<BasePolycube> ret = new ArrayList<>();
        BasePolycube p = this;
        for (int i = 0; i < 4; i++) {
            ret.add(p);
            p = p.rotxy();
        }
        return ret;
    }
    
    public ArrayList<BasePolycube> listAllTransformations() {
        ArrayList<BasePolycube> ret = new ArrayList<>();
        BasePolycube p = this;
        ret.addAll(p.list8());
        p = p.mirrorxy();
        ret.addAll(p.list8());
        p = p.mirrorxz();
        ret.addAll(p.list8());
        p = p.mirrorxy();
        ret.addAll(p.list8());
        p = p.mirrorxz();
        ret.addAll(p.list8());
        p = p.mirrorxy();
        ret.addAll(p.list8());
        return ret;
    }
    /*
    part of listAllTransformations
    */
    ArrayList<BasePolycube> list8() {
        ArrayList<BasePolycube> ret = new ArrayList<>();
        BasePolycube p = this;
        ret.add(p);
        p = p.rotxy();
        ret.add(p);
        p = p.rotxy();
        ret.add(p);
        p = p.rotxy();
        ret.add(p);
        p = p.mirrorz();
        ret.add(p);
        p = p.rotxy();
        ret.add(p);
        p = p.rotxy();
        ret.add(p);
        p = p.rotxy();
        ret.add(p); 
        return ret;
    }
    public boolean chiral() {
        ArrayList<String> h1 = new ArrayList<>();
        ArrayList<String> h2 = new ArrayList<>();
        minRotate(this, h1, h2);
        BasePolycube p = this.mirrorxy();
        minRotate(p, h2, h1);
        p = p.mirrorxz();
        minRotate(p, h1, h2);
        p = p.mirrorxy();
        minRotate(p, h2, h1);
        p = p.mirrorxz();
        minRotate(p, h1, h2);
        p = p.mirrorxy();
        minRotate(p, h2, h1);     
        for (String s : h1) {
            if (h2.contains(s))
                return false;
        }
        return true;
    }
    BasePolycube shift(int dx, int dy, int dz) {
    
        BasePolycube p = maker();
        for (CoordTriplet cp : array) {
            CoordTriplet ncp = new CoordTriplet(cp.getX() + dx, cp.getY() + dy, cp.getZ() + dz, cp.getColour());
            p.add(ncp);
        }
        p.seal();
        return p;
        
    }

    public int getMax(int c) {
        if (c == 0)
            return this.maxX;
        if (c == 1)
            return this.maxY;
        return this.maxZ;
    }
    public boolean treelike() {
        
        return (perimeterOrSurface() == maximalSurface())
              //  && simplyConnected()
                ;
    }
    public boolean strip() {
        if (size() <= 1)
            return true;
        
        int adj1 = 0;
        for (int i = 0 ; i < size(); i++) {
            int adj = getAdjacent(array.get(i)).size();
            if (adj > 2)
                return false;
            if (adj == 1)
                adj1++;
        }
        return adj1 == 2;
    }    
    public String listCoords() {
        String ret = "";
        for (CoordTriplet ct : array)
            ret += ct.toString();
        return ret;
    }
    public boolean flat() {
        CoordTriplet ct = array.get(0);
        int x = ct.getX(), y = ct.getY(), z = ct.getZ();
        boolean bx = true, by = true, bz = true;
        for (int i = 1; i < array.size(); i++) {
            ct = array.get(i);
            int nx = ct.getX(), ny = ct.getY(), nz = ct.getZ();
            if (nx != x)
                bx = false;
            if (ny != y)
                by = false;
            if (nz != z)
                bz = false;
            
        }
        boolean ret = bx | by | bz;
        return ret;
    }
    public ArrayList<CoordTriplet> getAdjacent(CoordTriplet cp) {
        ArrayList<CoordTriplet> list = new ArrayList<>();
        adjAdd(list, cp, 1, 0, 0);
        adjAdd(list, cp, -1, 0, 0);
        adjAdd(list, cp, 0, 1, 0);
        adjAdd(list, cp, 0, -1, 0);
        adjAdd(list, cp, 0, 0, 1);
        adjAdd(list, cp, 0, 0, -1);
        return list;
    }    
    private void adjAdd(ArrayList<CoordTriplet> list, CoordTriplet cp, int dx, int dy, int dz) {
        CoordTriplet ncp = new CoordTriplet(cp.getX() + dx, cp.getY() + dy, cp.getZ() + dz);
        if (this.contains(ncp))
            list.add(ncp);
        
    }    
    //protected abstract int maximalPerimeter();
    int maximalSurface() { return array.size() * 4 + 2; }
    public boolean minimalSurface() {
        return this.perimeterOrSurface() == this.minPerimeterOrSurface();
    }
    public int minPerimeterOrSurface() {
        int min[] = new int[]{
        0, 6, 10, 14, 16, 20, 22, 24, 24, 28, 30, 32, 32, 36, 38, 40, 40, 42, 42, 
            46, 48, 50, 50, 52, 52, 54, 54, 54, 
            58, 60, 62, 62, 64, 64, 66, 66, 66, 70, 
            72, 74, 74, 76, 76, 78, 78, 78, 80, 80, 80, 84, 86, 88, 88, 90, 90, 
            92, 92, 92, 94, 94, 94, 96, 96, 96,
            96, 100
        };
        return min[array.size()];
    }
    //protected abstract int minPerimeter();    
    public int perimeterOrSurface() {
        int ret = 0;
        for (int i = 0; i < array.size(); i++) {
            ret += surface(array.get(i));
        }
        return ret;
    }
    int surface(CoordTriplet cp) {
        int ret = 0;
        ret += surface(cp, 1, 0, 0);
        ret += surface(cp, -1, 0, 0);
        ret += surface(cp, 0, 1, 0);
        ret += surface(cp, 0, -1, 0);
        ret += surface(cp, 0, 0, 1);
        ret += surface(cp, 0, 0, -1);
        return ret;
    }
    int surface(CoordTriplet cp, int dx, int dy, int dz) {
        int x = cp.getX() + dx;
        int y = cp.getY() + dy;
        int z = cp.getZ() + dz;
        if (x < 1 || y < 1 || z < 1)
            return 1;
        if (this.contains(x, y, z))
            return 0;
        return 1;
    }          
    public int getNumColour(int colour) {
        int ret = 0;
        for (CoordTriplet cp : array) {
            if (cp.getColour() == colour)
                ret++;
        }
        return ret;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordTriplet cp) {
        int i = array.indexOf(cp);
        if (pointedCell != null && i == pointedCell.intValue())
            return gp.red;
        else
            return gp.green;
       
    }
    
    public  Color getGraphicsColour(GraphicsParameters gp, CoordTriplet cp, boolean applyCB) {
        if (applyCB) {
            if (cp.parity() == fcp.parity())
                return gp.green;
            else
                return gp.yellow;
        }
        return gp.green;
    }  
   
    private ArrayList<GraphicsCoords> gArray;
    double smallAngle = 40;
    double largeAngle = 90 - smallAngle;
    double horizLongBit, horizShortBit, vertLongBit, vertShortBit;
    double high, low, right, left;
    public void displayOnJpeg(GraphicsParameters gp) {
        if (gp.reverse) {
            gp.reverse = false;
            this.mirrorz().displayOnJpeg(gp);
            gp.reverse = true;
            return;
        }
        if (gp.layer) { // instead of dissplaying the 3d polycube, we display its layers, in order to show inner structure
            displayLayers(gp);
            return;
        }
        prepare(gp.edgeLength);
        int maxSum = 3;
        for (CoordTriplet cp : array) {
            int sum = cp.getX() + cp.getY() + cp.getZ();
            if (sum > maxSum)
                maxSum = sum;
        }
        //int lowLimit = 3, highLimit = maxSum, delta = 1;
        /*
        if (gp.reverse) {
            lowLimit = maxSum;
            highLimit = 3;
            delta = -1;
        }
        */
        for (int s = 3; s <= maxSum ; s++) {
            for (int i = 0; i < array.size(); i++) {
                CoordTriplet cp = array.get(i);
                int sum = cp.getX() + cp.getY() + cp.getZ();
                if (sum != s)
                    continue;
                displayOnJpeg(gp, cp, gArray.get(i));                
            }
            /*
            if (s == highLimit)
                break;   
            */
        }
    }
    /*
    
    ++-------++------++------++
    
    
    */
    double layerImageTotalWidth, singleLayerWidth, layerImageTotalWidthWithMargins;
    
    void displayLayers(GraphicsParameters gp) {
        prepareLayers(gp);
        double posX = gp.px * gp.colWidth ; 
        double posY = gp.vertPixelPosition  ;     
        for (int z = 1 ; z <= maxZ; z++) {
            displayLayer(gp, z, posX + (z - 1) * (maxX + 1) * gp.edgeLength, posY);
        }
        
    }
    void displayLayer(GraphicsParameters gp, int z, double posX, double posY) {
        for (int x = 1; x <= maxX ; x++)
            for (int y = 1; y <= maxX ; y++)
                if (this.contains(x, y, z)) {
                    CoordTriplet cp = this.find(x, y, z);
                    int xa[] = {(int)((x - 1) * gp.edgeLength + posX), (int)((x) * gp.edgeLength + posX), (int)((x) * gp.edgeLength + posX), (int)((x - 1) * gp.edgeLength + posX) };
                    int ya[] = {(int)(posY + (y - 1) * gp.edgeLength), (int)(posY  + (y - 1) * gp.edgeLength), (int)(posY  + (y) * gp.edgeLength), (int)(posY  + (y) * gp.edgeLength) };
                    gp.g.setColor(this.getGraphicsColour(gp, cp));
                    gp.g.fillPolygon(xa, ya, 4);


                    gp.g.setColor( Color.BLACK );        
                    gp.g.drawPolygon(xa, ya, 4);               
                }
            int xab[] = {(int)(posX), (int)((maxX) * gp.edgeLength + posX), (int)((maxX) * gp.edgeLength + posX), (int)( posX) };
            int yab[] = {(int)(posY) , (int)(posY ), (int)(posY  + (maxY) * gp.edgeLength), (int)(posY  + (maxY) * gp.edgeLength) };

            gp.g.setColor( Color.BLACK );        
            gp.g.drawPolygon(xab, yab, 4);          
    }
    void prepareLayers(GraphicsParameters gp) {
        singleLayerWidth = maxX * gp.edgeLength;
        layerImageTotalWidth = singleLayerWidth * maxZ + (maxZ - 1) * gp.edgeLength;
        layerImageTotalWidthWithMargins = layerImageTotalWidth ;
    }
    void displayOnJpeg(GraphicsParameters gp, CoordTriplet cp, GraphicsCoords gc) {
        drawPolygon(gp, gc.d, gc.c, gc.h, gc.e, this.getGraphicsColour(gp, cp));
        drawPolygon(gp, gc.c, gc.b, gc.g, gc.h, this.getGraphicsColour(gp, cp).darker());
        drawPolygon(gp, gc.e, gc.h, gc.g, gc.f, this.getGraphicsColour(gp, cp).darker().darker());
        
    }
    void drawPolygon(GraphicsParameters gp, GraphicsCoordPair p1, GraphicsCoordPair p2, 
            GraphicsCoordPair p3, GraphicsCoordPair p4, Color c ) {
        double posX = gp.px * gp.colWidth - left; 
        double posY = gp.vertPixelPosition + high;
        int xa[] = {(int)(p1.x + posX), (int)(p2.x + posX), (int)(p3.x + posX), (int)(p4.x + posX) };
        int ya[] = {(int)(posY - p1.y), (int)(posY - p2.y), (int)(posY - p3.y), (int)(posY - p4.y) };
        
        
        gp.g.setColor( c);
        gp.g.fillPolygon(xa, ya, 4);
       
        
        gp.g.setColor( Color.BLACK );        
        gp.g.drawPolygon(xa, ya, 4);        
        
    }
    void prepare(int edgeLength) {
        horizLongBit = (edgeLength * 89) / 90;
        horizShortBit = (edgeLength * 67) / 90;
        vertLongBit = (edgeLength * 66) / 90;
        vertShortBit = (edgeLength * 44) / 90;
        
        gArray = new ArrayList<>();
        for (CoordTriplet cp : array)
            calcCoords(edgeLength, cp);
        high = gArray.get(0).d.y;
        low = gArray.get(0).g.y;
        left = gArray.get(0).e.x;
        right = gArray.get(0).c.x;
        for (GraphicsCoords gc : gArray) {
            if (gc.d.y > high) high = gc.d.y;
            if (gc.g.y < low) low = gc.g.y;
            if (gc.c.x > right) right = gc.c.x;
            if (gc.e.x < left) left = gc.e.x;
            
        }
    }

    void calcCoords(int edgeLength, CoordTriplet cp) {
        GraphicsCoords gc = new GraphicsCoords();
        gArray.add(gc);
        
        gc.a.y = (cp.getZ() - 1) * edgeLength;
        gc.a.y -= (cp.getX() - 1) * vertShortBit;
        gc.a.y -= (cp.getY() - 1) * vertLongBit;
        gc.a.x = 0;
        gc.a.x += (cp.getY() - 1) * horizShortBit;
        gc.a.x -= (cp.getX() - 1) * horizLongBit;
        gc.b.x = gc.a.x + horizShortBit;
        gc.b.y = gc.a.y - vertLongBit;
        gc.c.x = gc.b.x;
        gc.c.y = gc.b.y + edgeLength;
        gc.d.x = gc.a.x;
        gc.d.y = gc.a.y + edgeLength;
        gc.e.x = gc.d.x - horizLongBit;
        gc.e.y = gc.d.y - vertShortBit;
        gc.f.x = gc.e.x;
        gc.f.y = gc.e.y - edgeLength;
        gc.g.x = gc.f.x + horizShortBit;
        gc.g.y = gc.f.y - vertLongBit;
        gc.h.x = gc.g.x;
        gc.h.y = gc.g.y + edgeLength;
    }
    public  int getPixelHeight(GraphicsParameters gp) { 
        if (gp.layer) {
            prepareLayers(gp);
            return maxY * gp.edgeLength;
        }
        prepare(gp.edgeLength);
        int hh = (int)(high - low);

        return hh ; 
    }
    public int getPixelWidth(GraphicsParameters gp) { 
        if (gp.layer) {
            prepareLayers(gp);
            return (int)(this.layerImageTotalWidthWithMargins);
        }        
        prepare(gp.edgeLength);
        return (int)(right - left); 
    }    
    class GraphicsCoords {
        GraphicsCoordPair a, b, c, d, e, f, g, h;
        GraphicsCoords() {
            a = new GraphicsCoordPair();
            b = new GraphicsCoordPair();
            c = new GraphicsCoordPair();
            d = new GraphicsCoordPair();
            e = new GraphicsCoordPair();
            f = new GraphicsCoordPair();
            g = new GraphicsCoordPair();
            h = new GraphicsCoordPair();
            
        }
    }
    private class GraphicsCoordPair {
        double x, y;
    }   
}
