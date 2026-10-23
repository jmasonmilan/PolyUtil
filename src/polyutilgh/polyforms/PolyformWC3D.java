package polyutilgh.polyforms;
import java.util.*;
import java.math.BigInteger;
import java.awt.Color;
import java.math.*;
import polyutilgh.*;
import polyutilgh.PolySet;
import polyutilgh.Propagator;
/**
 *
 * @author jmason
 */
public abstract class PolyformWC3D extends WCCommon {
    ArrayList<CoordTriplet> array ;
    CoordTriplet fcp;
   
    int maxX, maxY, maxZ;
    
    public PolyformWC3D() {
        
    }
     
 
    protected void calcMax() {
        for (int i = 0 ; i < array.size(); i++) {
            if (array.get(i).getX() > maxX)
                maxX = array.get(i).getX();
            if (array.get(i).getY() > maxY)
                maxY = array.get(i).getY();       
            if (array.get(i).getZ() > maxZ)
                maxZ = array.get(i).getZ();             
        }
        
    
        
       
    }
    public void nonCBSeal() {
          
        if (rep == null) {
            rep = new BigInteger("0");
            for (int i = 0 ; i < array.size() ; i++) {
                int n = getBit(array.get(i).getX(), array.get(i).getY(), array.get(i).getZ());
                //System.out.println(ret);
                if (n < 0 ) {
                    int k = 1 / 0;
                }
                rep = rep.setBit(n);
                //System.out.println(ret);
            }
        }
        //setFirstSquare();   
        calcMax();
        sealed = true;        
    }
    /**
     * 
     * @return width of the polyform
     */
    public int getWidth() {
        if (!sealed) {
            int i = 1 / 0;
        }
        return maxX;
    }
    /**
     * 
     * @return height of the polyform
     */
    public int getHeight() {
        if (!sealed) {
            int i = 1 / 0;
        }
        return maxY;
    }
    public PolyformWC3D copy() {
        PolyformWC3D np = (PolyformWC3D)(maker());
        for (CoordTriplet cp : array)
            np.insert(new CoordTriplet(cp.getX(), cp.getY(), cp.getZ()));
        return np;
    }    
    public int getDepth() {
        if (!sealed) {
            int i = 1 / 0;
        }
        return maxZ;
    }
    public PolyformWC3D remove(CoordTriplet before, boolean seal) {
        PolyformWC3D p = (PolyformWC3D)maker();
        for (CoordTriplet cp : array)
            if (!cp.equals(before))
                p.insert(cp);
        p = (p.align(seal));
        
        return p;
    }
    public PolyformWC3D add(CoordTriplet after, boolean seal) {
        PolyformWC3D p = (PolyformWC3D)maker();
        for (CoordTriplet cp : array)
            
            p.insert(cp);
        p.insert(after);
        
        p = (p.align(seal));
        
        return p;
    }
    public PolyformWC3D align(boolean seal) {
        Runner.notImplemented("PolyformWC align");
        return null;
    }
    public int swappables(boolean intact) {
        boolean dbg = false;
        if (size() == 1) {
            return 0;
        }
        if (dbg)
            System.err.println("testing " + this.toString());
        
        Hashtable<String, String> sh = new Hashtable<>();
        for (CoordTriplet cp : array) {
            PolyformWC3D p = this.remove(cp, true);
            if (dbg)
                System.err.println("removed " + p.getRep());
            if (intact && !((Polycube)p).intact())
                continue;
            for (CoordTriplet ocp : p.array) {
                ArrayList<CoordTriplet> list = ((Polycube)p).getClose(ocp, false);
                for (CoordTriplet xcp : list) {
                    if (p.contains(xcp))
                        continue;
                    PolyformWC3D np = p.add(xcp, true);
                    if (!((Polycube)np).intact())
                        continue;
                    if (dbg)
                        System.err.println("added " + np.getRep());
                    String s = np.getUnique();
                    if (sh.get(s) == null) {
                        sh.put(s, s);
                        if (dbg)
                            System.err.println("found " + s);
                    }
                }
            }
        }
        if (sh.get(this.toString()) == null) {
            int qq = 1 / 0;
        }
        return sh.size() - 1;
    }        
    public void add(CoordTriplet cp) {
        array.add(cp);
    }
    /**
     * 
     * @param x
     * @param y
     * @return the number of the bit that will represent the specificed cooedinate position
     */
    public abstract int getBit(int x, int y, int z)   ;
    /**
     * 
     * @param cp
     * @return whether cp is a cell in the polyform
     */
    public boolean contains(CoordTriplet cp) {
        
        return contains(cp.getX(), cp.getY(), cp.getZ());
        
    }   
    /**
     * 
     * @param x
     * @param y
     * @return whether x,y is a cell in the polyform
     */
    public boolean contains(int x, int y, int z) {
        
        
        //if (x < 1 || y < 1 || z < 1) would be quicker but does not give correct result on unaligned polyform
        //    return false;
        //if (sealed)   speed mprovement that does not work for polyiamonds
        //    return rep.testBit(getBit(x, y));
        
        for (int i = 0 ; i < array.size(); i++)
            if (x == array.get(i).getX() && y == array.get(i).getY()  && z == array.get(i).getZ() )
                return true;
        return false;
    }   
    /**
     * 
     * @param x
     * @param y
     * @return the coordinate pair corresponding to the input coordinates, if it exists
     */
    public CoordTriplet find(int x, int y, int z) {
        for (int i = 0 ; i < array.size(); i++)
            if (x == array.get(i).getX() && y == array.get(i).getY() && z == array.get(i).getZ() )
                return array.get(i);
        return null;
    }   
    /**
     * 
     * @param x
     * @param y
     * @return the position in the array of the coordinate pair, if it exists
     */
    public int locate(int x, int y, int z) {
        for (int i = 0 ; i < array.size(); i++)
            if (x == array.get(i).getX() && y == array.get(i).getY() && z == array.get(i).getZ() )
                return i;
        return -1;
    }   
    /**
     * 
     * @return the size of the polyform
     */
    public int size() {
        return array.size();
    }
    /**
     * 
     * @param i
     * @return the i'th coordinate pair
     */
    public CoordTriplet getCoords(int i) {
        return array.get(i);
    }
    /**
     * 
     * @param cp
     * @return what colour should be used for indicated cell
     */
    public abstract Color getGraphicsColour(GraphicsParameters gp, CoordTriplet cp);  
    /**
     * 
     * @param cp
     * @param applyCb
     * @return return what colour should be used for indicated cell, 
     * according to whether chessboard colouring should be applied
     */
    public abstract Color getGraphicsColour(GraphicsParameters gp, CoordTriplet cp, boolean applyCb); 
    /**
     * 
     * @return list of fixed polyforms buildable from this polyform
     */
   
    public void insert(CoordTriplet cp) {
        array.add(cp);
    } 
    public void insert(int x, int y, int z) {
        array.add(new CoordTriplet(x, y, z));
    } 
    public boolean unconditionallyInsert(int x, int y, int z) {
        if (!contains(x, y, z)) {
            insert(new CoordTriplet(x, y, z));
            return true;
        }
        int qq = 1 / 0;
        return false;
    }     
    public boolean unconditionallyInsert(CoordTriplet cp) {
        if (!contains(cp)) {
            insert(cp);
            return true;
        }
        int qq = 1 / 0;
        return false;
    }     
    /*
    ArrayList<CoordPair> getClose(CoordPair cp) {
        ArrayList<CoordPair> a = new ArrayList<>();
        getClose(a, cp, 1, 0);
        getClose(a, cp, -1, 0);
        getClose(a, cp, 0, 1);
        getClose(a, cp, 0, -1);
        return a;
    }    
    */
    
     
   
    /**
     * 
     * @param cp
     * @return if input cell can be added
     */
    protected boolean canAdd(CoordTriplet cp) {
        return !this.contains(cp);
    }    
   
   
    public String dumpCoords( ) {
        String ret = "";
        boolean first = true;
        for (CoordTriplet ct : array) {
            if (!first)
                ret += " ";
            else
                first = false;
            ret += ct.toString();
        }
        return ret;
    } 

}
