/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh;
import java.util.*;
import polyutilgh.polyforms.*;
/**
 *
 * @author mason
 * used in more recent counting filters; lists possible new squares.
 * offers exclusion from list
 * may have a position with respect to the origin distinct from associated polyomino
 */
public class InclusionList {
    ArrayList<CoordPair> coordArray;
  
    long excluded = 0, excluded2 = 0;
    int lastExcluded = -1;
    int xOffset, yOffset;
    static int progr = 0;
    int current;
    //String history;
    /*
    if offsets are 2, then adding 4,5 will result in adding 2,3, and
    reading 2,3 will result in 4,5
    */
    public InclusionList() {
       
        progr++;
        current = progr;
        //history = progr + "/";
        coordArray = new ArrayList<>();
      
        
    }
    private InclusionList(String hist) {
        //size = 0;
        progr++;
        current = progr;
        //history = hist + progr + "/";
        coordArray = new ArrayList<>();
        
    }
   
    public InclusionList add(CoordPair cp) {
       
        if (contains(cp))
            return this;
        cp = inMassage(cp);
             
        this.coordArray.add(cp);
        if (coordArray.size() >= 127) {
            int qq = 1 / 0;
        }
        
        return this;
        
    }
    public ArrayList<CoordPair> getCoordArray() {
        return coordArray;
    }
    public InclusionList addExcluded(CoordPair cp) {
       
        if (contains(cp)) {
            int i = indexOf(cp);
            exclude(i);
            return this;
        }
        cp = inMassage(cp);
              
        this.coordArray.add(cp);
        if (coordArray.size() >= 127) {
            int qq = 1 / 0;
        }
        exclude(coordArray.size() - 1);
        return this;
        
    }
    
    public InclusionList increaseOffsets() {
        //InclusionList newList = copy();
        xOffset++;
        yOffset++;
        return this;
    }
    public InclusionList increaseXOffset() {
        /*
        InclusionList newList = copy();
        newList.xOffset++;
        return newList;
        */
        xOffset++;
        return this;
    }
    public InclusionList increaseYOffset() {
        /*
        InclusionList newList = copy(); // needn't
      
        newList.yOffset++;
        return newList;
        */
        yOffset++;
        return this;
    }
    public int size() {
        return coordArray.size();
    }
    private long getBit(int i) {
        return 1L << i;
    }
    private boolean testExcluded(int i) {
        if (i < 64)
            return ((excluded & getBit(i)) != 0);
        else
            return ((excluded2 & getBit(i - 64)) != 0);
    }
    public int getStarter() {
        return lastExcluded + 1;
    }
    public CoordPair get(int i) {
        if (testExcluded(i))
            return null;
        return outMassage(coordArray.get(i));
    }
    public InclusionList remove(int i) {
        if (coordArray.size() == 0) {
            int qq = 1 / 0;
        }
        InclusionList newList = copy();
       
        
        if (testExcluded(i)) {
            int qq = 1 / 0;
        }
        
        newList.setBit(i);
        
        return newList;
    }
    public void exclude(int i) {
        if (coordArray.size() == 0) {
            int qq = 1 / 0;
        }
        /*
         
        if (testExcluded(i)) {
            int qq = 1 / 0;
        } */
        setBit(i);
        if (i == lastExcluded + 1) {
            lastExcluded++;
            
            for (int j = i + 1; j < this.coordArray.size(); j++) {
                if (testExcluded(j))
                    lastExcluded++;
                else
                    break;
            }
            
        }
        
    }
    void setBit(int i) {
        if (i < 64)
            excluded |= getBit(i);
        else
            excluded2 |= getBit(i - 64);
    }
    public InclusionList copy() {
        //InclusionList newList = new InclusionList(history);
        InclusionList newList = new InclusionList();
        
        newList.coordArray = new ArrayList<>(coordArray);
        newList.excluded = excluded;
        newList.excluded2 = excluded2;
        
       
        newList.xOffset = xOffset;
        newList.yOffset = yOffset;
        return newList;
    }
    public boolean contains(CoordPair cp) {
        cp = inMassage(cp);
        for (int i = 0 ; i < coordArray.size(); i++)
            if (coordArray.get(i).equals(cp))
                return true;
        return false;
    }
    public int indexOf(CoordPair cp) {
        cp = inMassage(cp);
        for (int i = 0 ; i < coordArray.size(); i++)
            if (coordArray.get(i).equals(cp))
                return i;
        return -1;
    }
    CoordPair inMassage(CoordPair cp) {
        return new CoordPair(cp.getX() - this.xOffset, cp.getY() - this.yOffset);
    }
    CoordPair outMassage(CoordPair cp) {
        return new CoordPair(cp.getX() + this.xOffset, cp.getY() + this.yOffset);
    }
    public String drawing() {
        Polyomino p = new Polyomino();
        for (int i = 0; i < coordArray.size(); i++)  {
            p.insert(coordArray.get(i));
        }
        p.seal();
        return p.drawing();
    }
    public String toString() {
        String ret = // history + 
                " (" + xOffset + "/" + yOffset + ") ";
        for (int i = 0; i < coordArray.size() ; i++) {
            if (testExcluded(i)) 
                ret += "-";
            else
                ret += "+";
            ret += coordArray.get(i).getX() + "/" + coordArray.get(i).getY() + "(" + (coordArray.get(i).getX() + xOffset) + "/" + (coordArray.get(i).getY() + yOffset) + ") ";
        }
        return ret;
    }
    /*
    public String getHistory() {
        return history;
    }
*/
    public void verify(PolyominoR p) {
        /*
        for (int i = 0; i < coordArray.size() ; i++) 
            if (!testExcluded(i))  {
                CoordPair cp = outMassage(coordArray.get(i));
                if (!p.inRestrictedSet(cp)) {
                    p.inRestrictedSet(cp);
                    int qq = 1 / 0;
                }
            }  
*/
    }
    public void verify2(Polyomino p) {
        /*
        for (CoordPair cp : coordArray) {
            if (!p.touches(outMassage(cp))) {
                int qq = 1 / 0;
            }
        }
*/
    }
}
