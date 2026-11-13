package polyutilgh.polyforms;
import polyutilgh.*;
/**
 *
 * @author mason
 */
public abstract class WCCommon extends Polyform {
    public abstract boolean strip();
    public abstract boolean treelike();
    public abstract int perimeterOrSurface();
    public abstract int minPerimeterOrSurface();
    public abstract Coord getC(int i);
    public boolean minimalPerimeter() {
        return this.perimeterOrSurface() == this.minPerimeterOrSurface();
    }
    public WCCommon getPointed(int i) {
        return null;
    }   
    public boolean testAsym() {
        int qq = 1 / 0;
        return false;
    }
    protected Integer pointedCell;
    public void setPointedCell(int i) {
        pointedCell = new Integer(i);
    }
    public abstract int getNumberOfAdjacentCells();
    public abstract Coord getNewCoord(Coord c, int i);
    public abstract boolean contains(Coord c);
    public abstract WCCommon copy();
    public abstract void insert(Coord c);
    public abstract WCCommon align(boolean seal);
   
    public boolean occupyable(Coord c, Coord d) {
        
        return true;
    }
}
