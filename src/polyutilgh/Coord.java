package polyutilgh;

/**
 *
 * @author jmason
 * Pair of x,y coordinates for 2D polyforms
 */
public class Coord {
    
    
    protected boolean flaggedForDisplay;
    protected boolean markedFlag;
    //private CoordPair previous, next; // use in rings
    
   
    public void setFlag() {
        this.flaggedForDisplay = true;
    }
    public boolean getFlag() {
        return this.flaggedForDisplay;
    }
    
    public void setMarked() {
        markedFlag = true;
    }
    public void unMark() {
        markedFlag = false;
    }
    public boolean marked() {
        return markedFlag;
    }
    
    
}
