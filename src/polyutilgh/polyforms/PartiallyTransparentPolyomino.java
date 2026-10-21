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

/**
 *
 * @author mason
 */
public class PartiallyTransparentPolyomino extends Polyomino {
    int blackSquares, whiteSquares;
    public PartiallyTransparentPolyomino(BigInteger inValue) {
        
        super(inValue);
        
        
    }        
    public PartiallyTransparentPolyomino() {
        
        super();
        
        
    }      
    public PartiallyTransparentPolyomino maker(String srep) {
        return new PartiallyTransparentPolyomino(new BigInteger(srep));
    }
    public PartiallyTransparentPolyomino maker() {
        return new PartiallyTransparentPolyomino();
    } 
    public Polyomino maker(BigInteger rep) {
        return new PartiallyTransparentPolyomino(rep);
    }    
    void innerBuild(BigInteger inValue) { // could do better?
        int x = 1, y = 1;
        array = new ArrayList<>();
        int size = inValue.bitCount();
        for (int i = 0; size > 0 ; i += 2) {
            if (inValue.testBit(i)) {
                CoordPair cp = new CoordPair(x, y);
                array.add(cp);
                if (inValue.testBit(i + 1)) {
                    cp.setColour(1);
                    size --;
                }
                size --;                
            }
            x--;
            y++;
            if (x < 1) {
                x = y;
                y = 1;
            }
        }        
    } 
    public void nonCBSeal() {
          
        if (rep == null) {
            rep = new BigInteger("0");
            for (int i = 0 ; i < array.size() ; i++) {
                CoordPair cp = array.get(i);
                int n = getBit(cp.getX(), cp.getY());
                
                if (n < 0 ) {
                    System.err.println(array.get(i).toString());
                    int k = 1 / 0;
                }
                n *= 2;
                rep = rep.setBit(n);
                if (cp.getColour() != 0) {
                    rep = rep.setBit(n + 1);
                    
                } 
            }
        }
        blackSquares = 0;
        whiteSquares = 0;
        for (int i = 0 ; i < array.size() ; i++) {
            CoordPair cp = array.get(i);
            if (cp.getColour() != 0) {
                blackSquares++;
            } else {
                whiteSquares++;
            }
        }
        setFirstSquare();   
        calcMax();
        sealed = true;        
    }    
    public int getWhiteSquares() {
        return whiteSquares;
    }
    public int getBlackSquares() {
        return blackSquares;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp) {
            if (cp.getColour() == 1)
                return gp.green;
            else
                return gp.yellow;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp, boolean applyCB) {
        
            if (cp.getColour() == 1)
                return gp.green;
            else
                return gp.yellow;
        
    }    
}
