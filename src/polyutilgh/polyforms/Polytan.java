package polyutilgh.polyforms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.math.BigInteger;
import java.util.ArrayList;
import polyutilgh.*;

/**
 *
 * @author mason
 *     1         2         3       4
 * Top Left  Top Right  Bottom R Bottom L
 * O    /       \    O  O     /    \    O
 *  X  /         \  X        /      \
 *   O/           \O        /        \
 *   /             \       /O        O\
 *  /               \     /  X      X  \
 * /    O       O    \   /    O    O    \
 * 
 * 
 *  5
 * Both
 * O    O
 * 
 *   OO
 *   XO
 * 
 * O    O
 */
public class Polytan extends Polyomino {
        final int triangleUpLeft = 1;
        final int triangleUpRight = 2;
        final int triangleBottomLeft = 4;
        final int triangleBottomRight = 3;
        final int completeSquare = 5;    
    public Polytan(BigInteger inValue) {
        super(inValue);  
    }   
    public Polytan() {
        super();
    }
    public Polytan maker(String srep) {
        return new Polytan(new BigInteger(srep));
    }
    public Polytan maker() {
        return new Polytan();
    }   
    public Polytan(String s, boolean numeric) {
        array = new ArrayList<CoordPair>();
        if (numeric) {
            BigInteger bi = new BigInteger(s);
            rep = bi;
            build(bi);

            seal();     
        } else {
            int y = 0, x = 0;

            for (int i = 0; i < s.length() ; i++) {
                String t = s.substring(i, i + 1);
                if (t.equals( "/")) {
                    x = 0; 
                    y += 6;
                    continue;
                }
                if (t.equals(triangleUpLeft + "")) {
                    insert(x + 1, y + 6);
                    insert(x + 2, y + 5);
                    insert(x + 3, y + 4);
                    insert(x + 6, y + 1);
                } else if (t.equals(triangleUpRight + "")) {
                    insert(x + 6, y + 6);
                    insert(x + 5, y + 5);
                    insert(x + 4, y + 4);
                    insert(x + 1, y + 1);
                } else if (t.equals(triangleBottomRight+ "")) {
                    insert(x + 6, y + 1);
                    insert(x + 5, y + 2);
                    insert(x + 4, y + 3);
                    insert(x + 1, y + 6);
                } else if (t.equals(triangleBottomLeft+ "")) { 
                    insert(x + 1, y + 1);
                    insert(x + 2, y + 2);
                    insert(x + 3, y + 3);
                    insert(x + 6, y + 6);
                } else if (t.equals(completeSquare + "")) {
                    insert(x + 1, y + 1);
                    insert(x + 1, y + 6);
                    insert(x + 6, y + 1);
                    insert(x + 6, y + 6);   
                    insert(x + 3, y + 3);
                    insert(x + 3, y + 4);
                    insert(x + 4, y + 3);
                    insert(x + 4, y + 4);   
                    
                }
                x += 6;
            }
            
            this.seal();            
        }
    }  
    public Polyomino fill() {
        Polyomino p = new Polyomino();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            int bx = (x - 1) / 6, by = (y - 1) / 6;     // 1,1
            bx *= 6;                                    // 6
            by *= 6;             
            int num = indicator(x, y);
            if (num == 0)
                continue;
            if (num == triangleUpLeft) {
                for (int dy = 1; dy <= 6; dy++) {
                    for (int dx = 1; dx <= dy; dx++)
                        p.insert(bx + dx, by + dy);
                }
            } else if (num == triangleUpRight) {
                for (int dy = 1; dy <= 6; dy++) {
                    for (int dx = 7 - dy; dx <= 6; dx++)
                        p.insert(bx + dx, by + dy);
                }
            } else if (num == triangleBottomLeft) {
                for (int dy = 1; dy <= 6; dy++) {
                    for (int dx = 1; dx <= 7 - dy; dx++)
                        p.insert(bx + dx, by + dy);
                }
            } else if (num == triangleBottomRight) {
                for (int dy = 1; dy <= 6; dy++) {
                    for (int dx = dy; dx <= 6; dx++)
                        p.insert(bx + dx, by + dy);
                }
            } else /* if (num == completeSquare) */ {
                for (int dy = 1; dy <= 6; dy++) {
                    for (int dx = 1; dx <= 6; dx++)
                        p.insert(bx + dx, by + dy);
                }
            } 
        }
        p.seal();
        return p;
    }
    public int countHoles() {
        Polyomino p = fill();
        return p.countHoles();
    }

    public PolySet genNew(int limit) {
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            Polytan p = new Polytan();
            p.insert(new CoordPair(1, 1));
            p.insert(new CoordPair(2, 2));
            p.insert(new CoordPair(3, 3));
            p.insert(new CoordPair(6, 6));
            p.seal();
            add(ps, p);
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            CoordPair cp = array.get(i);
            int x = cp.getX(), y = cp.getY();
            int ind = indicator(x, y);
            if (ind == 0)
                continue;
            if (ind < completeSquare)
                complete(ps, i, x, y);
            if (ind == triangleUpLeft || ind == completeSquare) {
                tryOne(ps,  x, y, -1, 0, triangleUpRight);
                tryOne(ps,  x, y, -1, 0, triangleBottomRight);
                tryOne(ps,  x, y, 0, 1, triangleBottomRight);
                tryOne(ps,  x, y, 0, 1, triangleBottomLeft);                
            } 
            if (ind == triangleUpRight || ind == completeSquare) {
                tryOne(ps,  x, y, 1, 0, triangleUpLeft);
                tryOne(ps,  x, y, 1, 0, triangleBottomLeft);
                tryOne(ps,  x, y, 0, 1, triangleBottomRight);
                tryOne(ps,  x, y, 0, 1, triangleBottomLeft);                
            } 
            if (ind == triangleBottomRight || ind == completeSquare) {
                tryOne(ps,  x, y, 1, 0, triangleUpLeft);
                tryOne(ps,  x, y, 1, 0, triangleBottomLeft);
                tryOne(ps,  x, y, 0, -1, triangleUpLeft);
                tryOne(ps,  x, y, 0, -1, triangleUpRight);                
            } 
            if (ind == triangleBottomLeft || ind == completeSquare) {
                tryOne(ps,  x, y, -1, 0, triangleUpRight);
                tryOne(ps,  x, y, -1, 0, triangleBottomRight);
                tryOne(ps,  x, y, 0, -1, triangleUpLeft);
                tryOne(ps,  x, y, 0, -1, triangleUpRight);                
            } 
            
        }
        return ps;
    }   
    void add(PolySet ps, Polytan p) {
        if (ps.addUniques(p)) {
            /*
            String dbg = this.drawing();
            p = new Polytan(new BigInteger(p.getUnique()));
            String dbg1 = p.drawing();
            System.err.println(dbg1);
            System.err.println(p.image());
            p.image();
            */
        }
    }
    void tryOne(PolySet ps,  int x, int y, int dx, int dy, int num) {
        Polytan p = new Polytan();
        int nx = x + 6 * dx, ny = y + 6 * dy;
        if (nx < 1) {
            for (int j = 0 ; j < this.array.size() ; j++) {
                p.insert(new CoordPair(array.get(j).getX() + 6, array.get(j).getY()));                
            }
            p.addInBlock(x, y, num);
            p.seal();
            add(ps, p);           
            return;
        }
        if (ny < 1) {
            for (int j = 0 ; j < this.array.size() ; j++) {
                p.insert(new CoordPair(array.get(j).getX(), array.get(j).getY() + 6));                
            }
            p.addInBlock(x, y, num);
            p.seal();
            add(ps, p);             
            return;            
        }
        int pnum = blockIndicator(nx, ny);
        
        
        if (pnum != 0) { // don't bother; we'll find it later
            /*
            int bx = (x - 1) / 6, by = (y - 1) / 6;    
            bx *= 6;                                    
            by *= 6;                                         
            bx += dx * 6;
            by += dy * 6;
            CoordPair cp;
            if (pnum == 1) {
                cp = new CoordPair(bx + 2, by + 5);
            } else if (pnum == 2) {
                cp = new CoordPair(bx + 5, by + 5);
            } else if (pnum == 3) {
                cp = new CoordPair(bx + 5, by + 2);
            } else  {
                cp = new CoordPair(bx + 2, by + 2);
            } 
            int pos = this.locate(cp);
            complete(ps, pos, cp.getX(), cp.getY());
*/
            return;
        }
        for (int j = 0 ; j < this.array.size() ; j++) {
            p.insert(new CoordPair(array.get(j).getX(), array.get(j).getY()));                
        }        
        p.addInBlock(nx, ny, num);
        p.seal();
        add(ps, p);
        /*
        if (!p.intact()) {
            int qq = 1 / 0;
        }
        */
        return;
    }
    public String image() {
        int img[][] = new int[100][100];
        int maxW = 0, maxH = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            int ind = indicator(x, y);
            if (ind == 0)
                continue;
            int bx = (x - 1) / 6, by = (y - 1) / 6;
            if (bx > maxW)
                maxW = bx;
            if (by > maxH)
                maxH = by;
            img[bx][by] = ind;
        }
        String ret = "";
        for (int y = maxH; y >= 0; y--) {
            for (int x = 0; x <= maxW; x++)
                ret += img[x][y];
            ret += "\r\n";
        }
        return ret;
    }
    public void externalAddInBlock(int x, int y, int num) {
        addInBlock(x * 6, y * 6, num);
    }
    void addInBlock(int x, int y, int num) {
        int bx = (x - 1) / 6, by = (y - 1) / 6;     // 1,1
        bx *= 6;                                    // 6
        by *= 6;   
        if (num == triangleUpLeft) {
            this.insert(new CoordPair(bx + 1, by + 6));
            this.insert(new CoordPair(bx + 2, by + 5));
            this.insert(new CoordPair(bx + 3, by + 4));
            this.insert(new CoordPair(bx + 6, by + 1));
            
        } else if (num == triangleUpRight) {
            this.insert(new CoordPair(bx + 6, by + 6));
            this.insert(new CoordPair(bx + 5, by + 5));
            this.insert(new CoordPair(bx + 4, by + 4));
            this.insert(new CoordPair(bx + 1, by + 1));
            
        } else if (num == triangleBottomRight) {
            this.insert(new CoordPair(bx + 6, by + 1));
            this.insert(new CoordPair(bx + 5, by + 2));
            this.insert(new CoordPair(bx + 4, by + 3));
            this.insert(new CoordPair(bx + 1, by + 6));
            
        } else if (num == triangleBottomLeft) {
            this.insert(new CoordPair(bx + 1, by + 1));
            this.insert(new CoordPair(bx + 2, by + 2));
            this.insert(new CoordPair(bx + 3, by + 3));
            this.insert(new CoordPair(bx + 6, by + 6));
            
        }  else {
            if (num != completeSquare) {
                int qq = 1 / 0;
            }
            this.insert(new CoordPair(bx + 1, by + 1));
            this.insert(new CoordPair(bx + 1, by + 6));
            this.insert(new CoordPair(bx + 6, by + 1));
            this.insert(new CoordPair(bx + 6, by + 6));
            this.insert(new CoordPair(bx + 3, by + 3));
            this.insert(new CoordPair(bx + 3, by + 4));
            this.insert(new CoordPair(bx + 4, by + 3));
            this.insert(new CoordPair(bx + 4, by + 4));
            
        } 
    }
    void complete(PolySet ps, int i, int x, int y) {
        int mx = ((x - 1) % 6) + 1, my = ((y - 1) % 6) + 1;     
        Polytan p = new Polytan();
        for (int j = 0; j < array.size(); j++)
            if (j != i)
                p.insert(array.get(j));
        if (mx == 2 && my == 5) { // 1
            p.insert(new CoordPair(x - 1, y - 4));
            p.insert(new CoordPair(x + 4, y + 1));
            p.insert(new CoordPair(x + 2, y - 1));
            p.insert(new CoordPair(x + 1, y - 2));
            p.insert(new CoordPair(x + 2, y - 2));
            
        } else if (mx == 5 && my == 5) { // 2
            p.insert(new CoordPair(x - 4, y + 1));
            p.insert(new CoordPair(x + 1, y - 4));
            p.insert(new CoordPair(x - 2, y - 1));
            p.insert(new CoordPair(x - 2, y - 2));
            p.insert(new CoordPair(x - 1, y - 2));
            
        } else if (mx == 5 && my == 2) { // 3
            p.insert(new CoordPair(x - 4, y - 1));
            p.insert(new CoordPair(x + 1, y + 4));
            p.insert(new CoordPair(x - 2, y + 2));
            p.insert(new CoordPair(x - 2, y + 1));
            p.insert(new CoordPair(x - 1, y + 2));
            
        }  else {                             // 4
            if (!(mx == 2 && my == 2)) {
                int qq = 1 / 0;
            }
            p.insert(new CoordPair(x - 1, y + 4));
            p.insert(new CoordPair(x + 4, y - 1));
            p.insert(new CoordPair(x + 2, y + 1));
            p.insert(new CoordPair(x + 1, y + 2));
            p.insert(new CoordPair(x + 2, y + 2));
            
        }  
        p.seal();
        add(ps, p);
    }
    int indicator(int x, int y) {
        int mx = ((x - 1) % 6) + 1, my = ((y - 1) % 6) + 1;
        if (mx == 2 && my == 5)
            return triangleUpLeft;
        if (mx == 5 && my == 5)
            return triangleUpRight;
        if (mx == 5 && my == 2) 
            return triangleBottomRight;
        if (mx == 2 && my == 2)
            return triangleBottomLeft;
        if (mx == 3 && my == 3 && contains(x + 1, y)) // used to be "y + 1"; changed for distinguished polytans
            return completeSquare;
        return 0;
    }
    int blockIndicator(int x, int y) {              // e.g. 12,9
        int bx = (x - 1) / 6, by = (y - 1) / 6;     // 1,1
        bx *= 6;                                    // 6
        by *= 6;                                    // 6
        if (contains(bx + 2, by + 5))
            return triangleUpLeft;
        if (contains(bx + 5, by + 5))
            return triangleUpRight;
        if (contains(bx + 5, by + 2)) 
            return triangleBottomRight;
        if (contains(bx + 2, by + 2))
            return triangleBottomLeft;
        if (contains(bx + 3, by + 3) && contains(bx + 4, by + 4))
            return completeSquare;
        return 0;
    }
    boolean compatible(int v1, int v2) {
        if (v1 == completeSquare || v2 == completeSquare)
            return false;
        if (v1 == 0 || v2 == 0)
            return true;
        if (v1 - v2 == 3)
            return true;
        if (v2 - v1 == 2)
            return true;
        return false;
    }
    
    public void displayOnJpeg(GraphicsParameters gp) {
        
        
       
        for (int i = 0 ; i < array.size(); i++) {
            

            display(gp, i);
        }
       
       
        
    }    
    void display(GraphicsParameters gp, int i) {
        CoordPair cp = array.get(i);
        int x = cp.getX(), y = cp.getY();
        int dx = (x - 1) / 6, dy = (this.maxY - y + 1) / 6;
        dx *= 6 * gp.edgeLength;
        dy *= 6 * gp.edgeLength;
        int num = indicator(x, y);
        if (num == 0)
            return;
        if (num == completeSquare) { // 3,3
            int xTopLeft = ((gp.px) * (gp.colWidth ))  + gp.horizMargin + dx;
            int yTopLeft = gp.vertPixelPosition + dy;
            int xTopRight = xTopLeft + gp.edgeLength * 6;
            int yTopRight = yTopLeft;
            int xBottomLeft = xTopLeft;
            int yBottomLeft = yTopLeft + gp.edgeLength * 6;
            int xBottomRight = xTopRight;
            int yBottomRight = yBottomLeft;

            gp.g.setColor( this.getGraphicsColour(gp, cp, gp.applyCb) );

            gp.g.fillRect(xTopLeft, yTopRight, gp.edgeLength * 6, gp.edgeLength * 6 );

            gp.g.setColor( Color.BLACK );   

            gp.g.drawLine(xTopLeft, yTopLeft, xTopRight, yTopRight);
            gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);
            gp.g.drawLine(xBottomRight, yBottomRight, xBottomLeft, yBottomLeft);
            gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);
            
            if (contains(x - 1, y - 1))
                gp.g.drawLine(xTopLeft, yTopLeft, xBottomRight, yBottomRight);
            if (contains(x + 2, y - 1))
                gp.g.drawLine(xTopRight, yTopRight,  xBottomLeft, yBottomLeft);
            if (gp.innerSENW)
                gp.g.drawLine(xTopLeft, yTopLeft, xBottomRight, yBottomRight);
            if (gp.innerSWNE)
                gp.g.drawLine(xTopRight, yTopRight,  xBottomLeft, yBottomLeft);
            
        } else {
            int xa1, xa2, xa3, ya1, ya2, ya3;
            if (num == triangleUpLeft) {
                xa1 = ((gp.px) * (gp.colWidth ))  + gp.horizMargin + dx;
                ya1 = gp.vertPixelPosition + dy;
                xa2 = xa1 + 6 * gp.edgeLength;
                ya2 = ya1;
                xa3 = xa1;
                ya3 = ya1 + 6 * gp.edgeLength;
            } else if (num == triangleUpRight) {
                xa1 = ((gp.px) * (gp.colWidth )) + gp.horizMargin + dx;
                ya1 = gp.vertPixelPosition + dy;
                xa2 = xa1 + 6 * gp.edgeLength;
                ya2 = ya1;
                xa3 = xa1 + 6 * gp.edgeLength;
                ya3 = ya1 + 6 * gp.edgeLength;
            } else if (num == triangleBottomRight) {
                xa1 = ((gp.px) * (gp.colWidth )) + gp.horizMargin + 6 * gp.edgeLength + dx;
                ya1 = gp.vertPixelPosition + dy;
                xa2 = xa1 - 6 * gp.edgeLength;
                ya2 = ya1 + 6 * gp.edgeLength;
                xa3 = xa1;
                ya3 = ya2;
            } else  { // triangleBottomLeft
                xa1 = ((gp.px) * (gp.colWidth ))  + gp.horizMargin + dx;
                ya1 = gp.vertPixelPosition + dy;
                xa2 = xa1;
                ya2 = ya1 + 6 * gp.edgeLength;
                xa3 = xa1 + 6 * gp.edgeLength;
                ya3 = ya2;
            }  
            int[] xa = new int[] {xa1, xa2, xa3};
            int[] ya = new int[] {ya1, ya2, ya3};
            gp.g.setColor( this.getGraphicsColour(gp, cp, gp.applyCb) );

            gp.g.fillPolygon(xa, ya, 3);

            gp.g.setColor( Color.BLACK );   

            gp.g.drawPolygon(xa, ya, 3);            
        }
       
    }
    public boolean strip() {
        if (size() <= 8)
            return true;
        
        int adj1 = 0;
        for (int i = 0 ; i < size(); i++) {
            CoordPair cp = array.get(i);
            int x = cp.getX(), y = cp.getY();
            int num = indicator(x, y);
            if (num == 0) 
                continue;
            int adj = getAdjacent(x, y, num);
            if (adj > 2)
                return false;
            if (adj == 1)
                adj1++;
        }
        return adj1 == 2;
    }   
    int getAdjacent(int x, int y, int num) {
        int ret = 0;
        if (num == completeSquare) {
            ret += occupied(x, y, 1, 0);
            ret += occupied(x, y, -1, 0);
            ret += occupied(x, y, 0, 1);
            ret += occupied(x, y, 0, -1);           
        } else if (num == triangleUpLeft) {
            ret += occupied(x, y, -1, 0);
            ret += occupied(x, y, 0, 1);            
        } else if (num == triangleUpRight) {
            ret += occupied(x, y, 1, 0);
            ret += occupied(x, y, 0, 1);
        } else if (num == triangleBottomRight) {
            ret += occupied(x, y, 1, 0);
            ret += occupied(x, y, 0, -1);
        } else { // triangleBottomLeft
            ret += occupied(x, y, -1, 0);
            ret += occupied(x, y, 0, -1);
        }
        return ret;
    }
    int occupied(int x, int y, int dx, int dy) {
        int bx = (x - 1) / 6, by = (y - 1) / 6;     
        bx *= 6;                                    
        by *= 6; 
        if (dx == 1) {
            if (contains(bx + 9, by + 3) || contains(bx + 9, by + 4))
                return 1;
        } else if (dx == -1) {
            if (contains(bx - 2, by + 3) || contains(bx - 2, by + 4))
                return 1;
        } else if (dy == 1) {
            if (contains(bx + 3, by + 9) || contains(bx + 4, by + 9))
                return 1;
        } else { // dy == -1
            if (contains(bx + 3, by - 2) || contains(bx + 4, by - 2))
                return 1;
        }
        return 0;
    }
    int corner(int v) {
        int bv = (v - 1) / 6;
        return bv * 6;
    }
    int contentAdjacent(int x, int y, int dx, int dy) {
        int bx = corner(x), by = corner(y);
        bx += dx * 6 + 1;
        by += dy * 6 + 1;
        return blockIndicator(bx , by);
    }
    public boolean blunt() {
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            int num = indicator(x, y);
            if (num == 0)
                continue;
            int above = contentAdjacent(x, y, 0, 1), below = contentAdjacent(x, y, 0, -1),
                    right = contentAdjacent(x, y, 1, 0), left = contentAdjacent(x, y, -1, 0);
            if (num == this.triangleUpLeft) {
                if (left == 0 || left == triangleUpLeft || left == triangleBottomLeft ||
                        above == 0 || above == triangleUpLeft || above == triangleUpRight)
                    return false;
            } else if (num == this.triangleUpRight) {
                if (right == 0 || right == triangleUpRight || right == triangleBottomRight ||
                        above == 0 || above == triangleUpLeft || above == triangleUpRight)
                    return false;
            } else if (num == this.triangleBottomLeft) {
                if (left == 0 || left == triangleUpLeft || left == triangleBottomLeft ||
                        below == 0 || below == triangleBottomLeft || below == triangleBottomRight)
                    return false;
            } else if (num == this.triangleBottomRight) {
                if (right == 0 || right == triangleUpRight || right == triangleBottomRight ||
                        below == 0 || below == triangleBottomLeft || below == triangleBottomRight)
                    return false;
            } 
        }
        return true;
    }
}
