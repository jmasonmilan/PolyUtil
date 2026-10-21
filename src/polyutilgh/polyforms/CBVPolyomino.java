/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;
import java.awt.Color;
import java.util.*;
import java.math.BigInteger;
import polyutilgh.CoordPair;
import polyutilgh.GraphicsParameters;
import polyutilgh.PolySet;
/**
 *
 * @author jmason
 */
public class CBVPolyomino extends BasePolyomino {
    
    int colourFirstSquare;
    boolean colourSet;
    public CBVPolyomino(BigInteger inValue) {
        rep = inValue;
        build(inValue);
        setFirstSquare();
        seal();
    }    
   
    public CBVPolyomino() {
        array = new ArrayList<CoordPair>();
    }  
    public CBVPolyomino(BigInteger inValue, int colour) {
        rep = inValue.shiftLeft(1);
        
        if (colour == 1)
            rep = rep.setBit(0);
        innerBuild(inValue);
        setFirstSquare();
        this.colourFirstSquare = colour;
        colourSet = true;
        seal();
    }    
    public CBVPolyomino maker(String srep) {
        return new CBVPolyomino(new BigInteger(srep));
    }     
    public CBVPolyomino maker() {
        return new CBVPolyomino();
    }     
    void build(BigInteger rep) {
        innerBuild(rep.shiftRight(1));
        if (rep.testBit(0))
            this.colourFirstSquare = 1;
        else
            this.colourFirstSquare = 0;
        colourSet = true;
    }    
    public void setColour(int c) {
        if (sealed) { int i = 1/0 ;}
        this.colourFirstSquare = c;
        colourSet = true;
    }
    public void seal() {
        if (!this.colourSet) { int i = 1/0 ;}
        if (rep == null) {
            rep = new BigInteger("0");
            for (int i = 0 ; i < array.size() ; i++) {
                int n = getBit(array.get(i).getX(), array.get(i).getY());
                //System.out.println(ret);
                rep = rep.setBit(n);
                //System.out.println(ret);
            }
            rep = rep.shiftLeft(1);
            if (this.colourFirstSquare == 1)
                rep = rep.setBit(0);            
        }

        calcMax();
               
        sealed = true;        
    }
    public int getColour() {
        if (!sealed) { int i = 1/0 ;}
        return this.colourFirstSquare;
    }
    public int oppColour() {
         if (!sealed) { int i = 1/0 ;}
         return 1 - this.colourFirstSquare;
     }
   
   
    public boolean displayCBVertices() {
        return true;
    }
    public void insert(CoordPair cp) {
        array.add(cp);
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp) {
        if (cp.parity() == fcp.parity())
            return innerGetGraphicsColour(gp, this.getColour());
        else
            return innerGetGraphicsColour(gp, this.oppColour());
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp, boolean applyCb) {
        if (applyCb)
        {
            System.err.println("error cb");
            System.exit(1);
        }
        return getGraphicsColour(gp, cp);
    }    
    Color innerGetGraphicsColour(GraphicsParameters gp, int col) {
        return gp.green;
    }
    public boolean areAdjacent(CoordPair cp1, CoordPair cp2) {
        return (1 / 0) == 0;
    }
    public String drawing() {
        
        if (this.size() == 0)
            return "\r\n";
        CoordPair fcp = this.getFirstSquare();
        String buf = "";
        int h = this.getHeight();
        int w = this.getWidth();
        for (int y = h ; y >= 1 ; y--) {
            
            for (int x = 1; x <= w ; x++) {
                CoordPair cp = new CoordPair(x, y);
                if (this.contains(cp)) {
                    if (this.getColour() == 1) {
                        if (cp.parity() == fcp.parity()) 
                            buf += "X";
                        else
                            buf += "O";
                    } else {
                        if (cp.parity() == fcp.parity()) 
                            buf += "O";
                        else
                            buf += "X";                        
                    }
                    
                }
                else 
                    buf += " ";
            }
            buf += "\r\n";
        }
        buf += "\r\n";
        return buf;        
    }
    public String getUnique() {
        int i = 1 / 0;
        return null;
    }
    public  PolySet getFixed() { int i = 1 / 0; return new PolySet();} //wip
    public  PolySet getOnesided() { int i = 1 / 0; return new PolySet();} //wip

   
    

    
}
