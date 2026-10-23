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
public class Polyiamond extends BasePolyiamond {
    public Polyiamond(BigInteger inValue) {
        rep = inValue;
        build(inValue);
        
        seal();  
    }    
   
    public Polyiamond() {
        array = new ArrayList<CoordPair>();
    } 
    public Polyiamond(String inValue) {
        array = new ArrayList<CoordPair>();
        int y = 1, x = 1;
        for (int i = 0; i < inValue.length() ; i++) {
            String t = inValue.substring(i, i + 1);
            if (t.equals( "/")) {
                x = 1; 
                y++;
                continue;
            }
            if (t.equals("O"))
                this.insert(new CoordPair(x, y));
            x++;
        }
        this.adjustToSextant60();
        seal();  
    }      
    public Polyiamond maker(String srep) {
        return new Polyiamond(new BigInteger(srep));
    }    
    public Polyiamond maker() {
        return new Polyiamond();
    }    
    public void seal() {
        nonCBSeal();
        
    }    
    public Polyiamond align(boolean seal) {
        int dx = this.getMinX() - 1;
        int dy = this.getMinY() - 1;
        if ((dy + dx) % 2 != 0)
            dx++;
        Polyiamond p = (Polyiamond)maker();
        for (CoordPair cp : array) {
            p.insert(cp.add(-dx, -dy));
        }
        p.adjustToSextant60();
        if (seal)
            p.seal();
        return p;
    }
    void build(BigInteger inValue) { // could do better?
        innerBuild(inValue);       
    }   
    public String drawing() {
        return drawing(false);
    }
    public PolyformWC getPointed(int i) {
    
        Polyiamond ret = new Polyiamond();
        for (int j = 0; j < this.size(); j++) {
            CoordPair cp = this.getCoords(j);
            int x = cp.getX(), y = cp.getY();
            if (pointsUp(cp)) {
                int bx = 2 * (x - 1) + 1, by = 2 * y - 1;
                ret.insert(bx, by);
                ret.insert(bx + 2, by);
                ret.insert(bx + 1, by + 1);
                if (i == j)
                    ret.insert(bx + 1, by);
                
            } else {
                int bx = 2 * x, by = 2 * y - 1;
                ret.insert(bx, by);
                ret.insert(bx - 1, by + 1);
                ret.insert(bx + 1, by + 1);
                if (i == j)
                    ret.insert(bx, by + 1);            
            }
            
            
        }
        
        ret.seal();
        return ret;
           
    }    
    private static int 
            V_1 = 1,
            V_2 = 2,
            V_3 = 3,
            V_4 = 4,
            V_5 = 5,
            V_6 = 6,
            V_1_1 = 7,
            V_1_1_1 = 8,
            V_1_2 = 9,
            V_1_3 = 10,
            V_2_2 = 11;
            
    public int countHoleFormula() {
        int v[][] = new int[7][4];
        for (CoordPair cp : array) {
            
            if (this.pointsUp(cp)) {
                /*
                  ^v^
                 ^v^v^
                 v^v^v
                */
                boolean down = contains(cp.getX(), cp.getY() - 1);
                boolean downL = contains(cp.getX() - 1, cp.getY() - 1);
                boolean downLL = contains(cp.getX() - 2, cp.getY() - 1);
                boolean left = contains(cp.getX() - 1, cp.getY());
                boolean leftL = contains(cp.getX() - 2, cp.getY());
                boolean upL = contains(cp.getX() - 1, cp.getY() + 1);
                boolean up = contains(cp.getX(), cp.getY() + 1);
                boolean upR = contains(cp.getX() + 1, cp.getY() + 1);
                boolean right = contains(cp.getX() + 1, cp.getY());
                boolean rightR = contains(cp.getX() + 2, cp.getY());
                boolean downRR = contains(cp.getX() + 2, cp.getY() - 1);
                boolean downR = contains(cp.getX() + 1, cp.getY() - 1);
                /*
                v[ch(left, upL, up, upR, right)]++;
                v[ch(right, rightR, downRR, downR, down)]++;
                v[ch(left, leftL, downLL, downL, down)]++; */
                /*ch2(v, left, upL, up, upR, right);
                ch2(v, right, rightR, downRR, downR, down);
                ch2(v, left, leftL, downLL, downL, down);*/
                ch3(v, left, upL, up, upR, right);
                ch3(v, right, rightR, downRR, downR, down);
                ch3(v, left, leftL, downLL, downL, down);
                
            } else {
                /*
                ^v^v^
                v^v^v
                 v^v
                */
                boolean up = contains(cp.getX(), cp.getY() + 1);
                boolean upL = contains(cp.getX() - 1, cp.getY() + 1);
                boolean upLL = contains(cp.getX() - 2, cp.getY() + 1);
                boolean left = contains(cp.getX() - 1, cp.getY());
                boolean leftL = contains(cp.getX() - 2, cp.getY());
                boolean downL = contains(cp.getX() - 1, cp.getY() - 1);
                boolean down = contains(cp.getX(), cp.getY() - 1);
                boolean downR = contains(cp.getX() + 1, cp.getY() - 1);
                boolean right = contains(cp.getX() + 1, cp.getY());
                boolean rightR = contains(cp.getX() + 2, cp.getY());
                boolean upRR = contains(cp.getX() + 2, cp.getY() + 1);
                boolean upR = contains(cp.getX() + 1, cp.getY() + 1);
                /* v[ch(up, upL, upLL, leftL, left)]++;
                v[ch(left, downL, down, downR, right)]++;
                v[ch(up, upR, upRR, rightR, right)]++; */
                /*ch2(v, up, upL, upLL, leftL, left);
                ch2(v, left, downL, down, downR, right);
                ch2(v, up, upR, upRR, rightR, right);*/
                ch3(v, up, upL, upLL, leftL, left);
                ch3(v, left, downL, down, downR, right);
                ch3(v, up, upR, upRR, rightR, right);
                
            }
            
           
            
        }
        
        for (int i = 2; i <= 6; i++) {
            for (int j = 0; j <= 3; j++) 
                v[i][j] = div(v[i][j], i);
        }
        /*
        v[V_1_1] = div(v[V_1_1], 2);
        v[V_1_1_1] = div(v[V_1_1_1], 3);
        v[V_1_2] = div(v[V_1_2], 3);
        v[V_1_3] = div(v[V_1_3], 4);
        v[V_2_2] = div(v[V_2_2], 4); */
        
        //int s = v[1] * 4 + v[2] * 5 + v[3] * 6 + v[4] * 7 + v[5] * 8 + v[6] * 12 + v[V_1_1] * 2 + v[V_1_2] * 3 + v[V_1_3] * 4 + v[V_2_2] * 4; // ignore V_1_1_1
        
        /* int s = v[1] + v[2] + v[3] + v[4] + v[5] + v[6] ;
        if (s % 6 != 0) {
            System.err.println(drawing());
            int dbg = 1;
        }*/
        int k[] = new int[4];
        for (int i = 1; i <= 6; i++) {
            for (int j = 0; j <= 3; j++) 
                k[j] += v[i][j];
        }
        int s = 0;
        for (int j = 0; j <= 3; j++) {
            s += k[j] *  (2 - j);
        }
        //int h = size() + 1 - div(s, 6) ;
        int sz = size();
        s += sz ;
        int h = sz + 1 - div(s, 2) ;
        // V - E + F = 2
        // V - E + F + H = 1
        // H = 1 - V + E - F
        int vv = countVertices(), p = perimeterOrSurface(), ie = internalEdges(); 
        int H = 1 - vv + p + ie - size();
        
        if (H != h) {
            System.err.println(drawing());
            vv = countVertices();
            int qq  = 1 / 0;
        }
        return h;
       
    }
    int div(int a, int b) {
        if (a % b != 0) {
            System.err.println("a=" + a + " b=" + b);
            int qq = 1 / 0;
        }
        return a / b;
    }
    int ch(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5) {
        int ret = 1;
        if (b1)
            ret++;
        if (b2)
            ret++;
        if (b3)
            ret++;
        if (b4)
            ret++;
        if (b5)
            ret++;
        switch(ret) {
            case 1:
                break;
            case 2:
                if (b1 || b5)
                    /* */ ;
                else
                    ret = V_1_1;
                break;
            case 3: 
                if (b2 && b4)
                    ret = V_1_1_1;
                else {
                    if ((b1 && b2) || (b1 && b5) || (b4 && b5))
                        break;
                    ret = V_1_2;
                }
                break;
            case 4:
                if (b1 && b2 && b3)
                    break;
                if (b1 && b2 && b5)
                    break;
                if (b1 && b4 && b5)
                    break;
                if (b3 && b4 && b5)
                    break;
                if (b1 && !b2 && !b5)
                    ret = V_2_2;
                else if (b5 && !b1 && !b4)
                    ret = V_2_2;
                else
                    ret = V_1_3;
                break;
                // 5 and 6 fall through
        }
        return ret;
    }    
    void ch2(int v[], boolean b1, boolean b2, boolean b3, boolean b4, boolean b5) {
        int ret = 1;
        if (b1)
            ret++;
        if (b2)
            ret++;
        if (b3)
            ret++;
        if (b4)
            ret++;
        if (b5)
            ret++;
        switch(ret) {
            case 1:
                v[ret] += ret + 3;
                return;
            case 2:
                if (b1 || b5) {
                    v[ret] += ret + 3;
                    return;
                } else {
                    v[ret] += ret;
                    return;
                }
                
            case 3: 
                if (b2 && b4)
                    return;
                else {
                    if ((b1 && b2) || (b1 && b5) || (b4 && b5)) {
                        v[ret] += ret + 3;
                        return;
                    }
                    v[ret] += ret ;
                    return;
                }
                
            case 4:
                if (b1 && b2 && b3) {
                    v[ret] += ret + 3;
                    return;
                }
                if (b1 && b2 && b5) {
                    v[ret] += ret + 3;
                    return;
                }
                if (b1 && b4 && b5) {
                    v[ret] += ret + 3;
                    return;
                }
                if (b3 && b4 && b5) {
                    v[ret] += ret + 3;
                    return;
                }
                if (b1 && !b2 && !b5){
                    v[ret] += ret ;
                    return;
                }
                else if (b5 && !b1 && !b4){
                    v[ret] += ret ;
                    return;
                }
                else{
                    v[ret] += ret ;
                    return;
                }
            case 5:
                
                    v[ret] += ret + 3;
                    return;
                
            case 6:
                {
                    v[ret] += 12;
                    return;
                }
        }
        
    }    
    void ch3(int v[][], boolean b1, boolean b2, boolean b3, boolean b4, boolean b5) {
        int ret = 1;
        if (b1)
            ret++;
        if (b2)
            ret++;
        if (b3)
            ret++;
        if (b4)
            ret++;
        if (b5)
            ret++;
        switch(ret) {
            case 1:
                v[1][1]++;
                return;
            case 2:
                if (b1 || b5) {
                    v[2][1]++;
                    return;
                } else {
                    v[2][2]++;
                    return ;
                }
                
            case 3: 
                if (b2 && b4) {
                    v[3][3]++;
                    return ;
                }
                else {
                    if ((b1 && b2) || (b1 && b5) || (b4 && b5)) {
                        v[3][1]++;
                        return ;
                    }
                    v[3][2]++;
                    return ;
                }
                
            case 4:
                if (b1 && b2 && b3) {
                    v[4][1]++;
                    return;
                }
                if (b1 && b2 && b5) {
                    v[4][1]++;
                    return;
                }
                if (b1 && b4 && b5) {
                    v[4][1]++;
                    return;
                }
                if (b3 && b4 && b5) {
                    v[4][1]++;
                    return;
                }
                if (b1 && !b2 && !b5){
                    
                    v[4][2]++;
                    return;
                }
                else if (b5 && !b1 && !b4){
                    
                    v[4][2]++;
                    return;
                }
                else{
                    
                    v[4][2]++;
                    return;
                }
            case 5:
                
                    v[5][1]++;
                    return;
                
            case 6:
                {
                    
                    v[6][0]++;
                    return;
                }
        }
        int qq = 1 / 0;
    }    
    public String drawing(boolean dot) {
        String ret = "";
        int h = this.getHeight();
        int w = this.getWidth();
        String sDot = " ";
        if (dot) 
            sDot = ".";
        for (int y = h ; y >= 1 ; y--) {
            
            for (int x = 1; x <= w ; x++)
                if (this.contains(x, y)) {
                    if ((x + y) % 2 == 0)
                        ret += "^";
                    else
                        ret += "v";
                }
                else 
                    ret += sDot;
            ret += "\r\n";
        }
        return ret ;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp) {
        return getGraphicsColour(gp, cp, false);
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp, boolean applyCB) {
        if (this.colourArray != null) {
            for (int i = 0; i < this.size(); i++) {
                PolyformWC p = this.colourArray[i];
                if (p.contains(cp))
                    return colourTable[i];
            }
        }
        return super.getGraphicsColour(gp, cp, applyCB);
    }
    public int internalPoints() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if ((x + y) % 2 != 0)
                continue;
            if (y < 2)
                continue;
            if (contains(x + 1, y) && contains(x + 2, y) && contains(x , y - 1) && contains(x + 1, y - 1) && contains(x + 2, y - 1))
                ret++;
        }
        return ret;
    }
    public  int internalEdges() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if ((x + y) % 2 == 0) {
                ret += internalEdges(x - 1, y);
                ret += internalEdges(x + 1, y);
                ret += internalEdges(x , y - 1);
                
            } else {
                ret += internalEdges(x - 1, y);
                ret += internalEdges(x + 1, y);
                ret += internalEdges(x , y + 1);                
            }
        }
        return ret / 2;
    }
    public  int reallyInternalEdges() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if ((x + y) % 2 == 0) {
                ret += reallyInternalEdges(x, y, x - 1, y - 1);
                ret += reallyInternalEdges(x, y, x + 1, y - 1);
                ret += reallyInternalEdges(x - 1, y - 1, x + 1, y - 1);
                
            } else {
                ret += reallyInternalEdges(x - 1, y, x + 1, y);
                ret += reallyInternalEdges(x + 1, y, x, y - 1);
                ret += reallyInternalEdges(x - 1, y, x, y - 1);                
            }
        }
        return ret / 2;
    }
    int reallyInternalEdges(int x1, int y1, int x2, int y2) {
        if (topOfTriangleIsInternal(x1, y1) && topOfTriangleIsInternal(x2, y2))
            return 1;
        else
            return 0;
    }
   
    boolean topOfTriangleIsInternal(int x, int y) {
        return contains(x, y) && contains(x - 1, y) && contains(x + 1, y) && contains(x, y + 1) && contains(x + 1, y + 1) && contains(x - 1, y + 1);
    }
    boolean bottomOfTriangleIsInternal(int x, int y) {
        return contains(x, y) && contains(x - 1, y) && contains(x + 1, y) && contains(x, y - 1) && contains(x + 1, y - 1) && contains(x - 1, y - 1);
    }
    public  PolyformWC core() {
        Polyiamond np = (Polyiamond)(copy());
        boolean flag = true;
        while(flag) {
            flag = false;
            for (int i = 0; i < np.array.size(); i++) {
                if (np.countNear(np.array.get(i)) == 1) {
                    np.array.remove(i);
                    flag = true;
                }
            }
        }
        np.seal();
        return np;
    }    
    public boolean even() {
        int up = 0, down = 0;
        for (CoordPair cp : array) {
            if (cp.parity() == 0)
                up++;
            else
                down++;
        }
        return down == up;
    }
    public Polyiamond rotate60() {
        Polyiamond np = maker();
        for (int i = 0 ; i < this.array.size() ; i++) {
            
            int x = array.get(i).getX() , y = array.get(i).getY();
            int oddity = x % 2;
            int ry = y - 1;
            int dx = -(((3 * ry) + oddity) / 2);
            int dy = (ry + oddity) / 2;
            int nx = (x + 1)/ 2, ny = (x + 2)/ 2;
            nx += dx + 1;
            ny += dy;
            if (np.contains(nx, ny)) {
                int qq = 1 / 0;
            }
            np.insert(new CoordPair(nx, ny));
        }
        int lowestY = 100000;
        for (int i = 0 ; i < this.array.size() ; i++) {
            if (np.array.get(i).getY() < lowestY)
                lowestY = np.array.get(i).getY();
        }       
        np.shiftUp(-lowestY + 1); // if we have ny's of -1,-2,-3 then we want ny's 3,2,1
        np.adjustToSextant60();
        np.seal();
        return np;          
    }    
    /*
    public Polyiamond rotate60() {
        Polyiamond np = maker();
        for (int i = 0 ; i < this.array.size() ; i++) {
            
            int x = array.get(i).getX() , y = array.get(i).getY();
            int z = (x - y) / 2;
            int ny = -(z + 1);
            int nx = 2 * x;
            z = (z + 1) * -3;
            if (this.array.get(i).parity() == 0)
                nx += z + 2;
            else
                nx += z + 1;
            np.insert(new CoordPair(nx, ny));
        }
        int lowestY = 1;
        for (int i = 0 ; i < this.array.size() ; i++) {
            if (np.array.get(i).getY() < lowestY)
                lowestY = np.array.get(i).getY();
        }       
        np.shiftUp(-lowestY + 1); // if we have ny's of -1,-2,-3 then we want ny's 3,2,1
        np.adjustToSextant60();
        np.seal();
        return np;          
    }
*/
    public Polyiamond reflectV() {
        Polyiamond np = maker();
        for (int i = 0 ; i < this.array.size() ; i++) {
            np.insert(new CoordPair(900 - array.get(i).getX() , array.get(i).getY())); // 900 < 1000 !
        }
        np.adjustToSextant60();
        np.seal();
        return np;        
    }
    public PolySet getFixed() {
        
        PolySet ps = new PolySet();
        Polyiamond poly;
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
   
    public PolySet getOnesided() {
        PolySet ps = new PolySet();
        ps.adds(this.getRep().toString());
        if (this.testM() == 0) {
            ps.adds(this.reflectV().getRep().toString());
        }
        return ps;
    }
  
    public boolean needs2CB() {
        if (this.size() % 2 != 0) {
            return true;
             
        }
        int m = this.testM();
        // asym
        if (m == 0 && !this.testR180() && !this.testR120())
            return true;
        if (m == 1 && this.testMU())
            return true;
        if (m == 3 && this.testMU())
            return true;
        if (m == 0 && this.testR120() && !this.testR60())
            return true;
        return false;
        
    }  
    
    public boolean armfull() {
        if (size() == 1)
            return true;
        if (!this.treelike())
            return false;
        if (!this.testR120())
            return false;
        if ((size() - 1) % 3 != 0)
            return false;
        CoordPair cp = findCandidateCentre();
        if (cp == null)
            return false;
        int x = cp.getX();
        int y = cp.getY();
        int nx = x + 1, ny = y;
        CoordPair ncp = new CoordPair(nx, ny);
        for (int i = 0 ; i <= size() / 3; i++) {
            int n = countNear(ncp);
            if (n == 1) {
                if (i == (size() / 3) - 1)
                    return true;
                else
                    return false;
            }
            if (n > 2)
                return false;
            CoordPair tcp = findNextCP(cp, ncp);
            cp = ncp;
            ncp = tcp;
        }
        return false;
    }
    CoordPair findCandidateCentre() {
        for (CoordPair cp : array) {
            if (countNear(cp) == 3)
                return cp;
        }
        return null;
    }
    public BigInteger canonise() {
        int minY = 999999;
        for (CoordPair cp : array) {
            int y = cp.getY();
            if (y < minY)
                minY = y;
        }
        int dy = 1 - minY, dx = 0;
        if (dy % 2 != 0)
            dx = 1;
        Polyiamond p = (Polyiamond)(copy(dx, dy));
        p.adjustToSextant60();
        p.seal();
        BigInteger ret = new BigInteger(p.getUnique());
        return ret;
    }
    CoordPair findNextCP(CoordPair cpOld, CoordPair cpCurr) {
        CoordPair cpNew = tryCP(cpCurr, 1, 0, cpOld);
        if (cpNew != null)
            return cpNew;
        cpNew = tryCP(cpCurr, -1, 0, cpOld);
        if (cpNew != null)
            return cpNew;
        if (cpCurr.parity() == 0) 
        {
            cpNew = tryCP(cpCurr, 0, -1, cpOld);
            if (cpNew != null)
                return cpNew; 
        } else {
            cpNew = tryCP(cpCurr, 0, 1, cpOld);
            if (cpNew != null)
                return cpNew; 
        }
        int qq = 1 / 0;
        return null;
    }

    int countNear(CoordPair cp) {
        int ret = 0;
        int x = cp.getX(), y = cp.getY();
        ret += countIf(x + 1, y);
        ret += countIf(x - 1, y);
        if (cp.parity() == 1)
            ret += countIf(x , y + 1);
        else
            ret += countIf(x , y - 1);
        return ret;
        
    }    

   
}
