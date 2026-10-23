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
public class CBPolyhex extends Polyhex {
    //String CBRep;
    public CBPolyhex() {
        super();
    }  
    public CBPolyhex(BigInteger inValue) {
        rep = inValue;
        build(inValue);
        
        seal();  
    }        
    public CBPolyhex maker() {
        return new CBPolyhex();
    }
    public void seal() {
        if (rep == null) {
            this.genCBRep();
        }
     
        calcMax();
        sealed = true;          
       
    }      
    void build(BigInteger inValue) { // could do better?
        int x = 1,  y = 1;
        int nx = 3;
        array = new ArrayList<>();
        int size = inValue.bitCount();
        for (int i = 0; size > 0 ; i += 3) {
            if (inValue.testBit(i)) {
                array.add(new CoordPair(x, y, 1));
                size--;               
            } else if (inValue.testBit(i + 1)) {
                array.add(new CoordPair(x, y, 2));
                size--;               
            } else if (inValue.testBit(i + 2)) {
                array.add(new CoordPair(x, y, 3));
                size--;               
            }
            
            y++;
            x--;
            
            if (3 * x <= y) {
                x = nx;            
                nx += 2;             
                
                y = 1;
            }
        }    
       
            
    }       
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp) {
        if (cp.getColour() == 1)
            return gp.green;
        else if (cp.getColour() == 2)
            return gp.yellow;
        else
            return gp.blue;
    }   
    private void genCBRep() {
        BigInteger bi = new BigInteger("0");
        for (int i = 0 ; i < array.size() ; i++) {
            CoordPair cp = array.get(i);
            int n = getBit(cp.getX(), cp.getY());
            n *= 3;
            if (cp.getColour() == 2)
                n += 1;
            else if (cp.getColour() == 3)
                n += 2;
            //System.out.println(ret);
            if (n < 0 ) {
                int k = 1 / 0;
            }
            bi = bi.setBit(n);
            //System.out.println(ret);
        }  
        rep = bi;
    }
    public BigInteger getRep() {
        if (this.rep == null)
            genCBRep();
        return rep;
    }
    public String getUnique() {
        
        BigInteger rep1 = this.getRep();      
        CBPolyhex poly = (CBPolyhex)(this.rotate60()); BigInteger rep2 = poly.getRep();     
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep3 = poly.getRep();
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep4 = poly.getRep();
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep5 = poly.getRep();
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep6 = poly.getRep();
        
        
        poly = (CBPolyhex)(poly.reflectV()); BigInteger rep7 = poly.getRep();
        
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep8 = poly.getRep();     
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep9 = poly.getRep();
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep10 = poly.getRep();
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep11 = poly.getRep();
        poly = (CBPolyhex)(poly.rotate60()); BigInteger rep12 = poly.getRep();
        

        
        if (rep1.compareTo(rep2) > 0)
            rep1 = rep2;
        if (rep1.compareTo(rep3) > 0)
            rep1 = rep3;
        if (rep1.compareTo(rep4) > 0)
            rep1 = rep4;
        if (rep1.compareTo(rep5) > 0)
            rep1 = rep5;
        if (rep1.compareTo(rep6) > 0)
            rep1 = rep6;
        if (rep1.compareTo(rep7) > 0)
            rep1 = rep7;
        if (rep1.compareTo(rep8) > 0)
            rep1 = rep8;
        if (rep1.compareTo(rep9) > 0)
            rep1 = rep9;
        if (rep1.compareTo(rep10) > 0)
            rep1 = rep10;
        if (rep1.compareTo(rep11) > 0)
            rep1 = rep11;
        if (rep1.compareTo(rep12) > 0)
            rep1 = rep12;
        
        return rep1.toString();
    }  
    public boolean allColoured() {
        for (int i = 0 ; i < this.array.size() ; i++)
            if (this.array.get(i).getColour() == 0)
                return false;
        return true;
    }
    public int countColoured() {
        int count = 0;
        for (int i = 0 ; i < this.array.size() ; i++)
            if (this.array.get(i).getColour() != 0)
                count++;
        return count;
    }
    public CBPolyhex copySoFar() {
        CBPolyhex ncbp = new CBPolyhex();
        for (int i = 0 ; i < this.array.size() ; i++) {
            CoordPair cp = this.array.get(i);
            ncbp.insert(new CoordPair(cp.getX(), cp.getY(), cp));
        }
        return ncbp;
    }
    public boolean badlyColoured() {
        for (int i = 0 ; i < this.array.size() ; i++) {
            ArrayList<CoordPair>  adj = getAdjacentIn(this.array.get(i));
            for (int j = 0; j < adj.size() ; j++) {
                if (this.array.get(i).getColour() == adj.get(j).getColour() && 
                        this.array.get(i).getColour() != 0)
                    return true;
            }
        }
        return false;
    }
}
