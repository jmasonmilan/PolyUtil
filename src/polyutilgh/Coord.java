package polyutilgh;

import java.awt.Color;

/**
 *
 * @author jmason
 * Common class for polyform coordinates
 */
public abstract class Coord {
    protected Color displayColour;
    protected int colour;
    protected boolean flaggedForDisplay;
    protected boolean markedFlag;
    //private CoordPair previous, next; // use in rings
    

    public void setFlag() {
        this.flaggedForDisplay = true;
    }
    public boolean getFlag() {
        return this.flaggedForDisplay;
    }
    public void setDisplayColour(Color value) {
        this.displayColour = value;
    }
    public Color getDisplayColour() {
        return this.displayColour;
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
    public abstract Coord add(Coord c);
    public abstract Coord subtract(Coord c);
    
}
