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
import polyutilgh.PolySet;
/**
 *
 * @author mason
 * 
 * 
 * .1
 * 234
 * .5
 * 
 * 67
 * 89
 * 
 */
public class TruncatedSquarePolyform extends Polyomino {
    final int CROSSTOP = 1, CROSSLEFT = 2 , CROSSCENTRE = 3 , CROSSRIGHT = 4, CROSSBOTTOM = 5;
    final int SQUARETOPLEFT = 6, SQUARETOPRIGHT = 7, SQUAREBOTTOMLEFT = 8, SQUAREBOTTOMRIGHT = 9;
    public TruncatedSquarePolyform(BigInteger inValue) {
        
        super(inValue);
        
       
    }    
   
    public TruncatedSquarePolyform() {
        super();
    }     
    public TruncatedSquarePolyform maker(String srep) {
        return new TruncatedSquarePolyform(new BigInteger(srep));
    }    
    public TruncatedSquarePolyform maker() {
        return new TruncatedSquarePolyform();
    }      
    CoordPair lowestOnLeft() {
        int minY = 9999;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (x != 1)
                continue;
            if (y < minY)
                minY = y;
        }
        return new CoordPair(1, minY);
    }
        
    CoordPair saveNS;
    CoordPair needsShift() {
        if (saveNS != null)
            return saveNS;
        int retX = 0, retY = 0;
        CoordPair cp = this.lowestOnLeft();
        int x = cp.getX(), y = cp.getY(); 
        int n = y % 3;
        if (contains(1, y + 1)) { // square
            retX = 2;
            retY = 3 - n;           // n = 1, retY = 2; n = 2, retY = 1; n =  0, retY = 3
        } else { // cross
            
            retX = 0;
            retY = 2 - n;           // n = 2, retY = 0; n = 1, retY = 1; n =  0, retY = 2
        }

        saveNS = new CoordPair(retX, retY);
        return saveNS;
    }
   
    /*
*   1
 * 234
 *  5
 * 
 * 67
 * 89  
    */
    private int position(CoordPair cp) {
        CoordPair ns = needsShift();
        int x = cp.getX() + ns.getX(), y = cp.getY() + ns.getY();
        int n = y % 3;
        int m = x % 3;
       
        if (n == 0) {
            // m = 120 becomes 918
            if (m == 1) return SQUAREBOTTOMRIGHT;
            if (m == 2) return CROSSTOP;
            return SQUAREBOTTOMLEFT;
        }
        if (n == 2) {
            // m = 120 becomes 234
            if (m == 1) return CROSSLEFT;
            if (m == 2) return CROSSCENTRE;
            return CROSSRIGHT;
        } else { // n = 1
            // m = 120 becomes 756
            if (m == 1) return SQUARETOPRIGHT;
            if (m == 2) return CROSSBOTTOM;
            return SQUARETOPLEFT;            
        }
    }
    /*
*   1
 * 234
 *  5
 * 
 * 67
 * 89  
    */
    boolean tryOne(int i, PolySet ps, int dx, int dy) {
        //System.out.println("tryone : " + dx + " " + dy);
        CoordPair cp = array.get(i);
        int x = cp.getX();
        int y = cp.getY();
        TruncatedSquarePolyform np;
        if (contains(x + dx, y + dy))
            return false;
        np = (TruncatedSquarePolyform)(copy(0, 0));
        int pos = position(cp);
        if (pos < 6) { // cross
            if (pos == CROSSTOP) {
                if (dx < 0) {
           
                    insert(np, x - 1, y);
                    insert(np, x - 2, y);
                    insert(np, x - 1, y + 1);
                    insert(np, x - 2, y + 1);
                    
                } else if (dx > 0) {
                  
                    insert(np, x + 1, y);
                    insert(np, x + 2, y);
                    insert(np, x + 1, y + 1);
                    insert(np, x + 2, y + 1);                    
                } else /* dy > 0 */ {
                   
                    insert(np, x, y + 1);
                    insert(np, x - 1, y + 2);
                    insert(np, x , y + 2);
                    insert(np, x + 1, y + 2);
                    insert(np, x , y + 3);                        
                }
            } else if (pos == CROSSLEFT) {
                if (dx < 0) {
                   
                    insert(np, x - 2, y + 1);
                    insert(np, x - 3, y );
                    insert(np, x - 2 , y );
                    insert(np, x - 1, y );
                    insert(np, x - 2 , y - 1);                      
                } else if (dy > 0) {
                    insert(np, x , y + 2);
                    insert(np, x - 1, y + 2);
                    insert(np, x , y + 1);
                    insert(np, x - 1, y + 1);                      
                } else /* dy < 0 */ {
                    insert(np, x , y - 2);
                    insert(np, x - 1, y - 2);
                    insert(np, x , y - 1);
                    insert(np, x - 1, y - 1);                       
                }
            } else if (pos == CROSSRIGHT) {
                if (dx > 0) {
                    insert(np, x + 2, y + 1);
                    insert(np, x + 3, y );
                    insert(np, x + 2 , y );
                    insert(np, x + 1, y );
                    insert(np, x + 2 , y - 1);                      
                } else if (dy > 0) {
                    insert(np, x , y + 2);
                    insert(np, x + 1, y + 2);
                    insert(np, x , y + 1);
                    insert(np, x + 1, y + 1);                       
                } else /* dy < 0 */ {
                    insert(np, x , y - 2);
                    insert(np, x + 1, y - 2);
                    insert(np, x , y - 1);
                    insert(np, x + 1, y - 1);                        
                }
            } else  if (pos == CROSSBOTTOM) {
                if (dx < 0) {
                    insert(np, x - 1, y);
                    insert(np, x - 2, y);
                    insert(np, x - 1, y - 1);
                    insert(np, x - 2, y - 1);                    
                } else if (dx > 0) {
                    insert(np, x + 1, y);
                    insert(np, x + 2, y);
                    insert(np, x + 1, y - 1);
                    insert(np, x + 2, y - 1);                     
                } else /* dy < 0 */ {
                    insert(np, x, y - 1);
                    insert(np, x - 1, y - 2);
                    insert(np, x , y - 2);
                    insert(np, x + 1, y - 2);
                    insert(np, x , y - 3);                       
                }
            }  
        } else { // square
            if (pos == SQUARETOPLEFT) {
                if (dx < 0) {
                    insert(np, x - 1, y );
                    insert(np, x , y + 1);
                    insert(np, x - 1, y + 1);
                    insert(np, x - 2, y + 1);
                    insert(np, x - 1, y + 2);                           
                } else /* if dy > 0 */ {
                   return false;                      
                }
            } else if (pos == SQUARETOPRIGHT) {
                if (dx > 0) {
                    insert(np, x + 1, y );
                    insert(np, x , y + 1);
                    insert(np, x + 1, y + 1);
                    insert(np, x + 2, y + 1);
                    insert(np, x + 1, y + 2);                      
                } else /* if dy > 0 */ {
                    return false;   
                }                
            } else if (pos == SQUAREBOTTOMLEFT) {
                if (dx < 0) {
                    insert(np, x - 1, y );
                    insert(np, x , y - 1);
                    insert(np, x - 1, y - 1);
                    insert(np, x - 2, y - 1);
                    insert(np, x - 1, y - 2);                            
                } else /* if dy < 0 */ {
                    return false;   
                }                
            } else /* if (pos == SQUAREBOTTOMRIGHT) */ {
                if (dx > 0) {
                    insert(np, x + 1, y );
                    insert(np, x , y - 1);
                    insert(np, x + 1, y - 1);
                    insert(np, x + 2, y - 1);
                    insert(np, x + 1, y - 2);                      
                } else /* if dy < 0 */ {
                    return false;   
                }                
            } 
        }
        np.unsealedAlign();
        np.seal();

        if (ps.addUniques(np)) {
//            System.err.println(this.drawing());
//            System.err.println(np.drawing());
            
        }                     
            
        
        return true;

       
    }  
    void insert(TruncatedSquarePolyform np, int x, int y ) {
        if (np.contains(x, y)) {
            int qq = 1 / 0;
        }
        np.insert(x, y);
    }
            
    public void insert(CoordPair cp) {
        if (cp.getX() < 1 || cp.getY() < 1) {
           // int qq = 1 / 0;
        }
        array.add(cp);
    }     
    void display(GraphicsParameters gp, CoordPair cp) {
       
        //int parity = cp.parity();
        int xTopLeft = ((gp.px) * (gp.colWidth )) + ( cp.getX() - 1 ) * gp.edgeLength + gp.horizMargin;
        int yTopLeft = ( this.getHeight() - cp.getY() ) * gp.edgeLength + gp.vertPixelPosition;
        int xTopRight = xTopLeft + gp.edgeLength;
        int yTopRight = yTopLeft;
        int xBottomLeft = xTopLeft;
        int yBottomLeft = yTopLeft + gp.edgeLength;
        int xBottomRight = xTopRight;
        int yBottomRight = yBottomLeft;
        int pos = position(cp);
        if (gp.alternative == 0) {
            int xa[] = {xTopLeft, xTopRight, xBottomRight, xBottomLeft };
            int ya[] = {yTopLeft, yTopRight, yBottomRight, yBottomLeft };


            gp.g.setColor( this.getGraphicsColour(gp, cp, gp.applyCb) );
            if (gp.filling)
                gp.g.fillPolygon(xa, ya, 4);

            gp.g.setColor( Color.BLACK ); 

            
            if (pos == CROSSTOP) {
                gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);
                gp.g.drawLine(xTopRight, yTopRight, xTopLeft, yTopLeft);
                gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);           
            } else if (pos == CROSSLEFT) {
                gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);
                gp.g.drawLine(xTopRight, yTopRight, xTopLeft, yTopLeft);
                gp.g.drawLine(xBottomLeft, yBottomLeft, xBottomRight, yBottomRight);            
            } else if (pos == CROSSCENTRE) {
                // noop
            } else if (pos == CROSSRIGHT) {
                gp.g.drawLine(xBottomLeft, yBottomLeft, xBottomRight, yBottomRight);
                gp.g.drawLine(xTopRight, yTopRight, xTopLeft, yTopLeft);
                gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);            
            } else if (pos == CROSSBOTTOM) {
                gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);
                gp.g.drawLine(xBottomLeft, yBottomLeft, xBottomRight, yBottomRight);
                gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);            
            } else if (pos == SQUARETOPLEFT) {
                gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);
                gp.g.drawLine(xTopRight, yTopRight, xTopLeft, yTopLeft);            
            } else if (pos == SQUARETOPRIGHT) {
                gp.g.drawLine(xTopRight, yTopRight, xTopLeft, yTopLeft);
                gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);                
            } else if (pos == SQUAREBOTTOMLEFT) {
                gp.g.drawLine(xBottomLeft, yBottomLeft, xTopLeft, yTopLeft);
                gp.g.drawLine(xBottomLeft, yBottomLeft, xBottomRight, yBottomRight);              
            } else { // SQUAREBOTTOMRIGHT
                gp.g.drawLine(xBottomLeft, yBottomLeft, xBottomRight, yBottomRight);
                gp.g.drawLine(xTopRight, yTopRight, xBottomRight, yBottomRight);                
            }
        } else /* alternative == 1 */ {
            int xa[] = null ;
            int ya[] = null;            
            int nPoints = 0;
            if (pos == CROSSTOP) {
                xa = new int[]{ xTopLeft, xTopRight,  xBottomRight + gp.edgeLength,
                xBottomRight + gp.edgeLength, xBottomRight,  xBottomLeft,  xBottomLeft - gp.edgeLength, 
                xBottomLeft - gp.edgeLength};
                ya = new int[]{ yTopLeft, yTopLeft, yBottomLeft,  yBottomLeft + gp.edgeLength,
                 yBottomLeft + gp.edgeLength * 2, yBottomLeft + gp.edgeLength * 2, yBottomLeft + gp.edgeLength,
                 yBottomLeft};
                nPoints = 8;
            } else if (pos == SQUARETOPLEFT) {
               xa = new int[]{  xTopRight,  xBottomRight + gp.edgeLength,
                 xBottomRight,    xBottomLeft};
                ya = new int[]{  yTopLeft, yBottomLeft,  yBottomLeft + gp.edgeLength,
                                  yBottomLeft};
                nPoints = 4;                
            }
            if (nPoints > 0 ) {
                gp.g.setColor( this.getGraphicsColour(gp, cp, gp.applyCb) );
                if (gp.filling)
                    gp.g.fillPolygon(xa, ya, nPoints);

                gp.g.setColor( Color.BLACK ); 
                gp.g.drawPolygon(xa, ya, nPoints);
            }
            
        }
        
    } 
    public int logicalSize() {
        int c = 0, s = 0;
        for (CoordPair cp : array) {
            int pos = position(cp);
            if (pos <= 5)
                c++;
            else
                s++;
        }
        return c / 5 + s / 4;
    }
    public boolean same() {
        int c = 0, s = 0;
        for (CoordPair cp : array) {
            int pos = position(cp);
            if (pos <= 5)
                c++;
            else
                s++;
        }
        return c / 5 == s / 4;        
    }
   
}
