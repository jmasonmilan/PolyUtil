/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh;

/**
 *
 * @author mason
 * Edge of a square used in self avoiding walks
 */
public class Edge {
    private CoordPair cp;
    private int side;
    private int x1, y1, x2, y2;
    /*
       N=1
  0,1----1,1
    | 1,1|E=2
 W=4|    |
  0,0----1,0
      S=3
    */
    public Edge(CoordPair inCp, int inSide) {
        cp = inCp;
        side = inSide;
        x1 = x2 = cp.getX();
        y1 = y2 = cp.getY();
        if (side == 1) {
            x1--; // 0,1 - 1,1
        } else if (side == 2) {
            y2--; // 1,1 - 1,0
        } else if (side == 3) {
            x1--; // 0,0 - 1,0
            y1--;
            y2--;
        } else { // if (side == 4) 
            x1--; // 0,1 - 0,0
            x2--;
            y2--;
        }  
    
    }
    public CoordPair getCoordPair() {
        return cp;
    }
    public int getSide() {
        return side;
    }
    public CoordPair getAdjacentCoordPair() {
        int x = cp.getX();
        int y = cp.getY();
        if (side == 1) {
            y++; 
        } else if (side == 2) {
            x++; 
        } else if (side == 3) {
            y--;
        } else { // if (side == 4) 
            x--;
        }     
        return new CoordPair(x, y);
    }

}
