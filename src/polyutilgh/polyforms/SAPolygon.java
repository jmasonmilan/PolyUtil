/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;
import java.awt.Color;
import java.awt.Graphics2D;
import java.math.BigInteger;
import polyutilgh.CoordPair;
import polyutilgh.GraphicsParameters;
import polyutilgh.PolySet;
/**
 *
 * @author mason
 * self avoiding polygon on the square lattice
 * represented by a sequence of 1,2,3,4 : 1 north, 2 east, 3 south, 4 west
 * externally, sequence is compressed to letters a-p
 */
public class SAPolygon extends BaseSAP {

    public SAPolygon(BigInteger inRep) {
        rep = inRep;
        srep = rep.toString();
        calcHW();
        sealed = true;      
    }
    public SAPolygon(String inRep) {
        //this.rep = inRep;
        //srep = decompress(inRep);
        //crep = inRep;
        srep = inRep;
        rep = new BigInteger(inRep);
       
        calcHW();
        sealed = true;
    }
    public SAPolygon(SAShape bracelet) {
        rep = new BigInteger(srep = convertFromBracelet(bracelet.getRep()));
        //crep = compress(srep);
        calcHW();
        sealed = true;        
    }
    /*
    public SAPolygon(String inRep, boolean compressed) {
        //this.rep = inRep;
        if (compressed) {
            srep = decompress(inRep);
            crep = inRep;
        
        } else {
            srep = inRep;
            crep = compress(srep);
        }
        calcHW();
        sealed = true;
    }
    */
    void calcHW() {
        int cvp = 0 , chp = 0;
        for (int i = 0 ; i < srep.length(); i++) {
            String s = srep.substring(i, i + 1);
            if (s.equals(NORTHS /* "1" */)) {
                cvp++;
                if (cvp > maxv)
                    maxv = cvp;
            } else if (s.equals(EASTS /* "2" */)) {
                chp++;
                if (chp > maxh)
                    maxh = chp;
            } else if (s.equals(SOUTHS /* "3" */)) {
                cvp--;
                if (cvp < minv)
                    minv = cvp;
            } else  {
                chp--;
                if (chp < minh)
                    minh = chp;
            } 
        }
        height = maxv - minv;
        width = maxh - minh;
    }

    //public String getCRep() { return crep; }
    //public String getSRep() { return srep; }
    public SAPolygon maker(String rep) {
        return new SAPolygon(rep);
    }     
    public SAPolygon maker() {
        return null;
    }     
    void build(BigInteger inValue) {}
    public void seal() {}
    public String getUnique() {
        return innerGetUnique(rep.toString());
    }
    public String innerGetUnique(String in) {
        String ret , srep1 = minimise(in);
        ret = srep1;
        srep1 = rotate90(srep1); if (srep1.compareTo(ret) < 0) ret = srep1;
        srep1 = rotate90(srep1); if (srep1.compareTo(ret) < 0) ret = srep1;
        srep1 = rotate90(srep1); if (srep1.compareTo(ret) < 0) ret = srep1;
        srep1 = reflectV(srep1); if (srep1.compareTo(ret) < 0) ret = srep1;
        srep1 = rotate90(srep1); if (srep1.compareTo(ret) < 0) ret = srep1;
        srep1 = rotate90(srep1); if (srep1.compareTo(ret) < 0) ret = srep1;
        srep1 = rotate90(srep1); if (srep1.compareTo(ret) < 0) ret = srep1;
        return ret;
        //return compress(ret);
    }
    String rotate90(String in) {
        String ret = "";
        for (int i = 0 ; i < in.length(); i++) {
            ret += rotate90char(in.substring(i, i + 1));
        }
        return minimise(ret);
    }
    String reflectV(String in) {
        String ret = "";
        for (int i = in.length() - 1 ; i  >= 0; i--) {
            ret += reflectVChar(in.substring(i, i + 1));
        }
        return minimise(ret);
    }
 
    String rotate90char(String in) {
        if (in.equals(NORTHS /* "1" */))
            return EASTS /* "2" */;
        else if (in.equals(EASTS /* "2" */))
            return SOUTHS /* "3" */;
        else if (in.equals(SOUTHS /* "3" */))
            return WESTS /* "4" */;
        else
            return NORTHS /* "1" */;
    }
    String reflectVChar(String in) {
        if (in.equals(NORTHS /* "1" */))
            return SOUTHS /* "3" */;
        else if (in.equals(SOUTHS /* "3" */))
            return NORTHS /* "1" */;
        else 
            return in;
    }

    public PolySet genNew(int limit) {
        PolySet ret = new PolySet();
        buildArray();
        int per = perimeter();
        int mid = per / 2;
        int x = mid, y = mid; 
        
        for (int i = 0 ; i < per ; i++) {
            int v = array[x][y];
            
            if (isFreeNear(x, y, v)) {
               
                //int len = contiguous(x, y, v);
                trySegment(i, x, y, v, 
                      //  len, 
                        ret);
            }
            int dx = 0, dy = 0;
            if (v == NORTH /* 1 */)
                dy++;
            else if (v == EAST /* 2 */)
                dx++;
            else if (v == SOUTH /* 3 */)
                dy--;
            else
                dx--;  
            x += dx;
            y += dy;
        }
        return ret;
    }
    void trySegment(int pos, int x, int y, int v, 
            // int len, 
            PolySet ps) {
        int dx = 0, dy = 0;
        if (v == NORTH /* 1 */)
            dy++;
        else if (v == EAST /* 2 */)
            dx++;
        else if (v == SOUTH /* 3 */)
            dy--;
        else
            dx--;   
        int nx = x, ny = y, npos = pos + 1;
        for ( ; npos < perimeter() && array[nx][ny] == v ; nx += dx, ny += dy, npos++) {
            if (!isFreeNear(nx, ny, v))
                break;
            String ns = shift(pos, npos, v);
            /*
            if (check(ns) == false) {
                System.err.println("was " + srep);
                System.err.println("new " + ns);
                isFreeNear(nx, ny, v);
                int i = 1 / 0;
            }
            */
            ns = innerGetUnique(ns);
            /*
            if (check(ns) == false) {
                int i = 1 / 0;
            }    
            */
            ps.adds(ns);
            //ps.adds(ns);
        }        
    }
    boolean check(String in) {
        for (int i = 0 ; i < in.length() - 1 ; i++) {
            String a = in.substring(i, i + 1);
            String b = in.substring(i + 1, i + 2);
            if (a.equals(NORTHS /* "1" */) && b.equals(SOUTHS /* "3" */))
                return false;
            if (a.equals(SOUTHS /* "3" */) && b.equals(NORTHS /* "1" */))
                return false;
            if (a.equals(EASTS /* "2" */) && b.equals(WESTS /* "4" */))
                return false;
            if (a.equals(WESTS /* "4" */) && b.equals(EASTS /* "2" */))
                return false;
            
        }
        return true;
    }
    String shift(int pos, int npos, int v) {
        String srep = rep.toString();
        return srep.substring(0, pos) + before(v) + srep.substring(pos, npos) + after(v) + srep.substring(npos);
    }
    int before(int v) {
        v--;
        if (v < MINNESW)
            v = MAXNESW;
        return v;
    }
    int after(int v) {
        v++;
        if (v > MAXNESW)
            v = MINNESW;
        return v;
    }
    /*
    int contiguous(int x, int y, int v) {
        int ret = 0;
        int dx = 0, dy = 0;
        if (v == 1)
            dy++;
        else if (v == 2)
            dx++;
        else if (v == 3)
            dy--;
        else
            dx--;   
        for ( ; ; x += dx, y += dy) {
            if (array[x][y] == v)
                ret++;
            else
                break;
        }
        return ret;
    }
*/
    boolean isFreeNear(int x, int y, int v) {
        int nx = x, ny = y, mx = x, my = y
                //, px = x, py = y
                ;
        if (v == NORTH) {
            nx--; 
            mx--; 
            // px--; 
            ny++; 
            // py++;
        } else if (v == EAST) {
            ny++; 
            my++; 
            // py++; 
            nx++; 
            // px++;
        } else if (v == SOUTH) {
            nx++; 
            mx++; 
            // px++; 
            ny--; 
            // py--;
        } else {
            ny--; 
            my--; 
            // py--; 
            nx--; 
            // px--;
        }
        return array[nx][ny] == 0 && array[mx][my] == 0 
        //        && array[px][py] == 0
                ;
    }
    void buildArray() {
        if (array != null)
            return;
        int per = perimeter();
        int mid = per / 2;
        int x = mid, y = mid;
        array = new int[per + 1][per + 1];
        for (int i = 0 ; i < per ; i++) {
            int v = Integer.parseInt(srep.substring(i, i + 1));
            array[x][y] = v;
            if (v == NORTH)
                y++;
            else if (v == EAST)
                x++;
            else if (v == SOUTH)
                y--;
            else
                x--;
        } 
    }
    public String drawing() {
        this.buildArray();
        String ret = "";
        for (int y = perimeter() - 1; y >= 0; y--) {
            for (int x = 0; x < perimeter() ; x++) {
                ret += drawChar(array[x][y]);
            }
            ret += "\r\n";
        }
        return ret;   
    }
    String drawChar(int v) {
        return " ^>v<".substring(v, v + 1);
    }
    /*
    static String intS = "11.12." + "13.14." + "21.22." + "23.24." + "31.32." + "33.34." + "41.42." + "43.44.";
    static String extS = "abcdefghijklmnop";
    public static String compress(String in) {
        String ret = "";
        if (in.length() % 2 == 1) {         
            int i = 1 / 0;
        }
        for (int i = 0; i < in.length(); i += 2) {
            //String tmp = in.substring(i, i + 2);
            int pos = intS.indexOf(in.substring(i, i + 2)) / 3;
            ret += extS.substring(pos, pos + 1);
        }
        if (ret.contains("c") || ret.contains("h") || ret.contains("i") || ret.contains("n"))
        {
         
            int i = 1 / 0;
        }
        
        return ret;
    }
    */
    /*
    public static String decompress(String in) {
        String ret = "";
        for (int i = 0; i < in.length(); i ++) {
            int pos = extS.indexOf(in.substring(i, i + 1)) * 3;
            ret += intS.substring(pos, pos + 2);
        }
        return ret;
    }
    */
    public String getShape() {
        String ret = "";
        String red = reduce(srep) + "1";
        for (int i = 0 ; i < red.length() - 1; i++) {
            int first = Integer.parseInt(red.substring(i, i + 1));
            int second = Integer.parseInt(red.substring(i + 1, i + 2));
            if (second == after(first))
                ret += "B"; // ??
            else
                ret += "W";
        }
        return SAShape.getUnique(ret);
    }
    public boolean segregated() {
        String s = getShape();
        int initB = s.indexOf("W"), len = s.length();
        if (initB < 0)
            return true;
        // BBBBBW : length = 6, w = 5
        return initB + initB - 4 == len;
    }
    String reduce(String in) {
        String ret = in.substring(0, 1);
        for (int i = 0, j = 1 ; j < in.length(); j++) {
            if (ret.substring(i).equals(in.substring(j, j + 1)))
                continue;
            ret += in.substring(j, j + 1);
            i++;
        }
        return ret;
    }
    
   
    int dd = 2;
    public void displayOnJpeg(GraphicsParameters gp) {
        int x1 = (this.getWidth() - this.getMaxH() + gp.px * gp.colWidth) * gp.edgeLength + gp.horizMargin;
        int y1 = (this.getHeight() - this.getMaxV() ) * gp.edgeLength + gp.vertPixelPosition;
        gp.g.setColor( Color.BLACK );
        String trep = this.getSRep() + this.getSRep().substring(0, 1);
        for (int i = 0 ; i < this.getSRep().length() ; i++) {
            int x2 = x1, y2 = y1;
            String move = this.getSRep().substring(i, i + 1);
            String nextMove = trep.substring(i + 1, i + 2);
            if (move.equals(NORTHS)) 
                y2 += gp.edgeLength;
            else if (move.equals(EASTS)) 
                x2 += gp.edgeLength;
            else if (move.equals(SOUTHS)) 
                y2 -= gp.edgeLength;
            else  // if (move.equals(WESTS)) 
                x2 -= gp.edgeLength;
            if (gp.round) {
                gp.g.drawLine(x1 + delta(x1, x2), y1 + delta(y1, y2), x2 - delta(x1, x2), y2 - delta(y1, y2));
                
            } else {
                gp.g.drawLine(x1, y1, x2, y2); 
            }
            if (gp.markers && move.equals(nextMove)) {
                gp.g.drawLine(x2 - 1, y2, x2 + 1, y2);
                gp.g.drawLine(x2, y2 - 1, x2, y2 + 1);
            } else if (gp.round) {
                if (move.equals(NORTHS) && nextMove.equals(EASTS)) gp.g.drawLine(x2, y2 - dd, x2 + dd, y2); else
                if (move.equals(EASTS) && nextMove.equals(SOUTHS)) gp.g.drawLine(x2 - dd, y2, x2, y2 - dd); else
                if (move.equals(SOUTHS) && nextMove.equals(WESTS)) gp.g.drawLine(x2, y2 + dd, x2 - dd, y2); else
                if (move.equals(WESTS) && nextMove.equals(NORTHS)) gp.g.drawLine(x2 + dd, y2, x2, y2 + dd); else
                if (move.equals(NORTHS) && nextMove.equals(WESTS)) gp.g.drawLine(x2, y2 - dd, x2 - dd, y2); else
                if (move.equals(EASTS) && nextMove.equals(NORTHS)) gp.g.drawLine(x2 - dd ,y2, x2, y2 + dd); else
                if (move.equals(SOUTHS) && nextMove.equals(EASTS)) gp.g.drawLine(x2, y2 + dd, x2 + dd, y2); else
                if (move.equals(WESTS) && nextMove.equals(SOUTHS)) gp.g.drawLine(x2 + dd, y2, x2, y2 - dd); 
                    
            }
            x1 = x2; 
            y1 = y2;
        }
        if (gp.caption) {
            gp.g.drawString("Perimeter " + this.perimeter(), gp.horizMargin, 15);
            gp.g.drawString("Shape " + this.getShape(), gp.horizMargin, gp.vertMargin + this.getHeight() * gp.edgeLength + 15);
        }
    }
    int delta(int v1, int v2) {
        if (v1 == v2)
            return 0;
        if (v2 > v1)
            return dd;
        else
            return -dd;
    }
    String convertFromBracelet(BigInteger bracelet) {
        String[] ba = convertToArray(SAShape.getUnique(bracelet.toString()));
        String[] moves = new String[ba.length];
        generate(ba, moves);
        String ret = "";
        for (int i = 0 ; i < moves.length ; i++)
            ret += moves[i];
        return ret;
    }
    String[] convertToArray(String bracelet) {
        String ret[] = new String[bracelet.length()];
        for (int i = 0 ; i < bracelet.length() ; i++)
            ret[i] = bracelet.substring(i, i + 1);
        return ret;
    }
    void generate(String[] ba, String[] moves) {
        if (ba.length == 4) {
            moves[0] = NORTHS;
            moves[1] = EASTS;
            moves[2] = SOUTHS;
            moves[3] = WESTS;
            return;
        }
        int shortLength = ba.length - 2;
        int posB1 = findBBW(ba); // points BBw in long string
        int posB2 = posB1 + 1;   // points BW in long string
        int posB3 = posB2 % shortLength; // points after first B in short string
       
        // posB2 now points to BW in ba
        
        String[] nba = copyLosingBW(ba, posB2);
        /*
            if was xxxBBW is now xxxB (posB1 = 3)
            if was xxxBBWyy is now xxxByy (posB1 = 3)
        */
        String shortMoves[] = new String[shortLength];
        
        generate(nba, shortMoves);
        checkMoves(shortMoves);                   
        make2AfterPos(shortMoves, posB1);
        
        CoordPair[] cpa = new CoordPair[shortLength];
        convertToCP(shortMoves, cpa);
        if (!checkCpa(cpa)) {
            int i = 1 / 0;
        }
        if (cpa[posB3].getX() - cpa[posB1].getX() == 1)
            increaseX(cpa, cpa[posB3].getX());
        increaseY(cpa, cpa[posB1].getY() + 1);
        if (!checkCpa(cpa)) {
            int i = 1 / 0;
        }        
        CoordPair[] ncpa = new CoordPair[ba.length];
        copyJump2(cpa, ncpa, posB1);
        int x1 = cpa[posB1].getX(), y1 = cpa[posB1].getY();
        int x2 = x1 + 1, y2 = y1;
        // increase y1 by 1
        y1++;
        ncpa[posB1] = new CoordPair(x1, y1);
        // introduce new vertices at (x1+1, y1) and (x1+1, y2)
        ncpa[posB1 + 1] = new CoordPair(x2, y1);
        ncpa[posB1 + 2] = new CoordPair(x2, y2);
        if (!checkCpa(ncpa)) {
            int i = 1 / 0;
        }        
        convertFromCP(ncpa, moves);
    }
    // find last BBW; I have decided that the BBW will NOT span
    int findBBW(String[] ba) {
        for (int i = ba.length - 1; i > 0 ; i--) {
            if (ba[i - 2].equals(SAShape.BLACK) && ba[i - 1].equals(SAShape.BLACK) && ba[i].equals(SAShape.WHITE))
                return i - 2;
        }
        int i = 1 / 0;
        return i;
    }
    String[] copyLosingBW(String[] ba, int pos) {
        String[] ret = new String[ba.length - 2];
        //int np = pos + 2;
        for (int i = 0 ; i < pos ; i++) {            
            ret[i] = ba[i];
        }
        for (int i = pos + 2 ; i < ba.length ; i++) {            
            ret[i - 2] = ba[i];
        }
        return ret;
    }
    void make2AfterPos(String[] moves, int pos) {
        checkMoves(moves);             
        if (moves[pos].equals("2"))
            return;
        increaseAll(moves);
        checkMoves(moves);        
        make2AfterPos(moves, pos);
        checkMoves(moves);
    }
    void increaseAll(String moves[]) {
        for (int i = 0 ; i < moves.length; i++) {
            String tmp = moves[i];
            moves[i] = "";
            for (int j = 0 ; j < tmp.length() ; j++)
                moves[i] += after(Integer.parseInt(tmp.substring(j, j + 1)));
        }
    }
    void convertToCP(String[] moves, CoordPair[] cpa) {
        int x = 0, y = 0;
        cpa[0] = new CoordPair(x, y);
        for (int i = 0 ; i < moves.length - 1; i++) {
            for (int j = 0 ; j < moves[i].length(); j++) {
                String t = moves[i].substring(j, j + 1);
                if (t.equals(NORTHS))
                    y++;
                else if (t.equals(EASTS))
                    x++;
                else if (t.equals(SOUTHS))
                    y--;
                else  if (t.equals(WESTS))
                    x--;
                else {
                    int k = 1 / 0;
                }
            }
            cpa[i + 1] = new CoordPair(x, y);
        }
    }
    void increaseX(CoordPair[] cpa, int inx) {
        for (int i = 0; i < cpa.length ; i++) {
            int x = cpa[i].getX();
            int y = cpa[i].getY();
            if (x >= inx)
                cpa[i] = new CoordPair(x + 1, y);
        }
    }
    void increaseY(CoordPair[] cpa, int iny) {
        for (int i = 0; i < cpa.length ; i++) {
            int x = cpa[i].getX();
            int y = cpa[i].getY();
            if (y >= iny)
                cpa[i] = new CoordPair(x, y + 1);
        }
    }
    void copyJump2(CoordPair cpa[], CoordPair ncpa[], int pos) {
       // int a = pos, b = pos + 2;
        for (int i = 0 ; i <= pos ; i++) {
            ncpa[i] = cpa[i];
        }
        for (int i = pos + 1; i < cpa.length ; i++) {
            ncpa[i + 2] = cpa[i];
        }
        
    }
    boolean f = false;
    void convertFromCP(CoordPair[] cpa, String[] moves) {
        for (int i = 0; i < cpa.length ; i++) {
            int j = i + 1;
            if (j >= cpa.length)
                j = 0;
            moves[i] = "";
            int x1 = cpa[i].getX(), y1 = cpa[i].getY();
            if (f)
                System.err.println(x1 + "," + y1);
            int x2 = cpa[j].getX(), y2 = cpa[j].getY();
            if (y2 > y1)
                moves[i] += seq(NORTHS, y2 - y1);
            else if (x2 > x1)
                moves[i] += seq(EASTS, x2 - x1);
            else if (y1 > y2)
                moves[i] += seq(SOUTHS, y1 - y2);
            else 
                moves[i] += seq(WESTS, x1 - x2);
        }
        int tmp = 0;
        for (int i = 0; i < moves.length ; i++) {
            tmp += moves[i].length();
        }
        if (tmp % 2 == 1) {
            if (!f) {
                f = true;
                convertFromCP(cpa, moves);
                
            }
            int i = 1 / 0;
        }
        
        if (!checkMoves(moves)) {
            if (!f) {
                f = true;
                convertFromCP(cpa, moves);
                
            }   
            int i = 1 / 0;
        }
            
        
    }
    String seq(String s, int c) {
        String ret = "";
        for (int i = 0; i < c; i++)
            ret += s;
        return ret;
    }
    boolean checkMoves(String[] moves) {
        for (int i = 1; i < moves.length ; i++) {
            String a = moves[i - 1].substring(0, 1);
            String b = moves[i].substring(0, 1);
            if (a.equals(b)) {
                               
                return false;
            }
            if (a.equals(NORTHS) && b.equals(SOUTHS)) {
                              
                return false;
            }  
            if (a.equals(SOUTHS) && b.equals(NORTHS)) {
                                
                return false;
            }  
            if (a.equals(EASTS) && b.equals(WESTS)) {
                              
                return false;
            }  
            if (a.equals(WESTS) && b.equals(EASTS)) {
                              
                return false;
            }  
            
        }      
        return true;
    }
    boolean checkCpa(CoordPair[] cpa) {
        for (int i = 0 ; i < cpa.length - 1; i++)
            for (int j = i + 1; j < cpa.length ; j++) {
                if (cpa[i].equals(cpa[j]))
                    return false;
            }
        return true;
    }
}
