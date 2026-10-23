package polyutilgh.polyforms;

import java.math.BigInteger;
import polyutilgh.CoordPair;

/**
 *
 * @author mason
 */
public abstract class PolyformWC3 extends  PolyformWC {
    public abstract PolyformWC3 reflectV();
    public abstract PolyformWC3 rotate60();
    public String getUnique() {
        
        BigInteger rep1 = this.getRep();  
        PolyformWC3 np2 = this.rotate60();
        PolyformWC3 np3 = np2.rotate60();
        PolyformWC3 np4 = np3.rotate60();
        PolyformWC3 np5 = np4.rotate60();
        PolyformWC3 np6 = np5.rotate60();
        PolyformWC3 np7 = np6.reflectV();
        PolyformWC3 np8 = np7.rotate60();
        PolyformWC3 np9 = np8.rotate60();
        PolyformWC3 np10 = np9.rotate60();
        PolyformWC3 np11 = np10.rotate60();
        PolyformWC3 np12 = np11.rotate60();  
        /*
        System.err.println("this\r\n" + this.drawing());
        System.err.println("np2\r\n" + np2.drawing());
        System.err.println("np3\r\n" + np3.drawing());
        System.err.println("np4\r\n" + np4.drawing());
        System.err.println("np5\r\n" + np5.drawing());
        System.err.println("np6\r\n" + np6.drawing());
        System.err.println("np7\r\n" + np7.drawing());
        System.err.println("np8\r\n" + np8.drawing());
        System.err.println("np9\r\n" + np9.drawing());
        System.err.println("np10\r\n" + np10.drawing());
        System.err.println("np11\r\n" + np11.drawing());
        System.err.println("np12\r\n" + np12.drawing());
        */
        BigInteger rep2 = np2.getRep();
        BigInteger rep3 = np3.getRep();
        BigInteger rep4 = np4.getRep();
        BigInteger rep5 = np5.getRep();
        BigInteger rep6 = np6.getRep();
        BigInteger rep7 = np7.getRep();
        BigInteger rep8 = np8.getRep();
        BigInteger rep9 = np9.getRep();
        BigInteger rep10 = np10.getRep();
        BigInteger rep11 = np11.getRep();
        BigInteger rep12 = np12.getRep();
        /*
        BigInteger rep2 = ((PolyformWC3)(this.maker(rep1.toString()))).rotate60().getRep();     
        BigInteger rep3 = ((PolyformWC3)(this.maker(rep2.toString()))).rotate60().getRep();
        BigInteger rep4 = ((PolyformWC3)(this.maker(rep3.toString()))).rotate60().getRep();
        BigInteger rep5 = ((PolyformWC3)(this.maker(rep4.toString()))).rotate60().getRep();
        BigInteger rep6 = ((PolyformWC3)(this.maker(rep5.toString()))).rotate60().getRep();
        
        BigInteger rep7 = ((PolyformWC3)(this.maker(rep6.toString()))).reflectV().getRep();
        
        BigInteger rep8 = ((PolyformWC3)(this.maker(rep7.toString()))).rotate60().getRep();
        BigInteger rep9 = ((PolyformWC3)(this.maker(rep8.toString()))).rotate60().getRep();
        BigInteger rep10 = ((PolyformWC3)(this.maker(rep9.toString()))).rotate60().getRep();
        BigInteger rep11 = ((PolyformWC3)(this.maker(rep10.toString()))).rotate60().getRep();
        BigInteger rep12 = ((PolyformWC3)(this.maker(rep11.toString()))).rotate60().getRep();
        */
        
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
    public int testM() {
        int ret = 0;
        if (this.testMV()) { 
            ret++;  
        }
        if (this.testM60()) { 
            ret++;  
        }
        if (this.testM30()) { 
            ret++;  
        }
        if (this.testMH()) { 
            ret++; 
        }
        if (this.testMminus30()) { 
            ret++; 
        }
        if (this.testMminus60()) { 
            ret++; 
        }        
        return ret;
    }
    public boolean testMA() {
        
        if (this.testM60()) { return true;  }
        
        if (this.testMH()) { return true; }
        
        if (this.testMminus60()) { return true;  }        
        return false;
    }
    public boolean testMU() {
        
        if (this.testMV()) { return true;  }
        
        if (this.testM30()) { return true;  }
        
        if (this.testMminus30()) { return true;  }
               
        return false;
    }
    public boolean testMV() { // axis 1
        BigInteger mRep = this.reflectV().getRep();
        return (mRep.equals(this.getRep()));
    }
    public boolean testM60() { // axis 2
        BigInteger mRep = this.reflectV().rotate60().getRep();
        return (mRep.equals(this.getRep()));
    }
    public boolean testM30() { // axis 3
        //System.err.println("test3M0");
        //System.err.println(this.toString() + "\r\n" + this.drawing());
        PolyformWC3 np = this.reflectV();
        //System.err.println(np.toString() + "\r\n" + np.drawing());
        np = np.rotate60();
        //System.err.println(np.toString() + "\r\n" + np.drawing());
        np = np.rotate60();
        //System.err.println(np.toString() + "\r\n" + np.drawing());
       
        BigInteger mRep = np.getRep();
        return (mRep.equals(this.getRep()));
    }
    public boolean testMH() { // axis 4
        PolyformWC3 np = this.reflectV();
        np = np.rotate60();
        np = np.rotate60();
        np = np.rotate60();
        BigInteger mRep = np.getRep();
        return (mRep.equals(this.getRep()));
    }
    public boolean testMminus30() { // axis 5
        BigInteger mRep = this.reflectV().rotate60().rotate60().rotate60().rotate60().getRep();
        return (mRep.equals(this.getRep()));
    }
    public boolean testMminus60() { // axis 6
        BigInteger mRep = this.reflectV().rotate60().rotate60().rotate60().rotate60().rotate60().getRep();
        return (mRep.equals(this.getRep()));
    }
    public boolean testR60() { // axis 1
        BigInteger mRep = this.rotate60().getRep();
        return (mRep.equals(this.getRep()));
    }
    public boolean testR120() { // axis 1
        BigInteger mRep = this.rotate60().rotate60().getRep();
        return (mRep.equals(this.getRep()));
    }
    public boolean testR180() { // axis 1
        BigInteger mRep = this.rotate60().rotate60().rotate60().getRep();
        return (mRep.equals(this.getRep()));
    }    
    /*
condition:sym3=+MC0 condition:sym3=-R180  condition:sym3=-R120  condition:sym3=-R60 asym
condition:sym3=+MC1 condition:sym3=+MA  ma
condition:sym3=+MC1 condition:sym3=+MU  mu
condition:sym3=+MC2  m2
condition:sym3=+MC3  condition:sym3=+MA  m3a
condition:sym3=+MC3  condition:sym3=+MU  m3u
condition:sym3=+MC0 condition:sym3=+R120 condition:sym3=-R60  r120
condition:sym3=+MC0 condition:sym3=+R180 condition:sym3=-R60  r180
condition:sym3=+MC6 condition:sym3=+R60 all
condition:sym3=+MC0 condition:sym3=+R60 r60
    */
    public boolean testAsym() {
        return this.testM() == 0 && !this.testR120() && !this.testR180() && !this.testR60();
    }
    public boolean testStrictlyMa() {
        return this.testM() == 1 && this.testMA();
    }
    public boolean testStrictlyMu() {
        return this.testM() == 1 && this.testMU();
    }
    public boolean testStrictlyM2() {
        return this.testM() == 2;
    }
    public boolean testStrictlyM3a() {
        return this.testM() == 3 && this.testMA();
    }
    public boolean testStrictlyM3u() {
        return this.testM() == 3 && this.testMU();
    }
    public boolean testStrictlyR120() {
        return this.testM() == 0 && this.testR120() && !this.testR60();
    }
    public boolean testStrictlyR180() {
        return this.testM() == 0 && this.testR180() && !this.testR60();
    }
    public boolean testAll() {
        return this.testM() == 6 && this.testR60();
    }
    public boolean testStrictlyR60() {
        return this.testM() == 0 && this.testR60();
    }
    abstract int distanceFromSextant60(CoordPair cp);
    int maxDistanceFromSextant60() {
        int worst = 0;
       
        for (CoordPair cp : array) {
            
            int d = distanceFromSextant60( cp);
            
            if (d > worst)
                worst = d;
        }
        return worst;
    }    
    int minDistanceFromSextant60() {
        int worst = 9999;
       
        for (CoordPair cp : array) {
            
            int d = distanceFromSextant60( cp);
            
            if (d < worst)
                worst = d;
        }
        return worst;
    }    
    abstract int distanceFromSextantMinus60(CoordPair cp);
    int maxDistanceFromSextantMinus60() {
        int worst = 0;
       
        for (CoordPair cp : array) {
            
            int d = distanceFromSextantMinus60( cp);
            
            if (d > worst)
                worst = d;
        }
        return worst;
    }
    int minDistanceFromSextantMinus60() {
        int worst = 1000;
       
        for (CoordPair cp : array) {
            
            int d = distanceFromSextantMinus60( cp);
            
            if (d < worst)
                worst = d;
        }
        return worst;
    }    
    abstract int distanceFromSextantMinus30(CoordPair cp);
    int maxDistanceFromSextantMinus30() {
        int worst = 0;
       
        for (CoordPair cp : array) {
            
            int d = distanceFromSextantMinus30( cp);
            
            if (d > worst)
                worst = d;
        }
        return worst;
    }
    int minDistanceFromSextantMinus30() {
        int worst = 1000;
       
        for (CoordPair cp : array) {
            
            int d = distanceFromSextantMinus30( cp);
            
            if (d < worst)
                worst = d;
        }
        return worst;
    }  
    /*      POLYHEX IAMOND
    14   N   ASYM    ASYM    =
    13   M   MA      MA      =
    12   L   MU      MU      =
    11   K   M2C     M2V     
    10   J   M2M     M2M     =
    9   I   M3AV    M3A     
    8   H   M3AC    M3UC    
    7   G   M3U     M3UV    
    6   F   R120C   R120C   =
    5  E   R120V   R120V   =
    4  D   R180M   R180M   =
    3  C   R180C   R180V   
    2  B   R60     R60     =
    1  A   ALL     ALL     =
    */
    public static final int ASYM = 14, MA = 13, MU = 12, M2C = 11, M2M = 10, M3AV = 9, M3AC = 8, MU3 = 7,
            R120C = 6, R120V = 5, R180M = 4, R180C = 3, R60 = 2, ALL = 1, M2V = 11, M3A = 9, M3UC = 8, M3UV = 7, R180V = 3;
    // inefficient implementation
    public abstract int getCategory() ;
    public String getCategoryLetter() {
        String letters = " ABCDEFGHIJKLMN";
        int cat = getCategory();
        String letter = letters.substring(cat, cat + 1);
        return letter;
    }
    public boolean fixedEncloses(PolyformWC in, int x, int y) {
        if (((x + 1000) % 2) != ((y + 1000) % 2))
            return false;
        for (CoordPair cp : in.array) {
            if (!this.contains(cp.getX() + x, cp.getY() + y))
                return false;
        }
        return true;
    }   
}
