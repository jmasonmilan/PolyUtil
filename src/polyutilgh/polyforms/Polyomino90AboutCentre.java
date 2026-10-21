/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;

import java.math.BigInteger;
import polyutilgh.*;

/**
 *
 * @author mason
 */
public abstract class Polyomino90AboutCentre extends Polyomino90 {
    public Polyomino90AboutCentre(BigInteger inValue) {
        super(inValue);  
    }    
   
    public Polyomino90AboutCentre() {
        super();
    }      
    /*
    boolean inRestrictedSet(CoordPair cp) {
        int w = this.getWidth();
        if (w == 1)
            return true;
        int half = (w + 1 ) / 2;        
       
        int x1 = cp.getX(), y1 = cp.getY();
        if (x1 < half || y1 <= half)
            return false ;
        return true;
    }
    */
    boolean okToTry(int x, int y) {
        int w = this.getWidth();
        int half = (w + 1 ) / 2;   
        if (x == half && y == half)
            return false;
        return true;
    }    
}
