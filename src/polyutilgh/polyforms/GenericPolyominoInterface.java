package polyutilgh.polyforms;
import polyutilgh.CoordPair;
/**
 *
 * @author mason
 */
public interface GenericPolyominoInterface {
    public CoordPair getCoords(int i);
    public int getX(int i);
    public int getY(int i);    
    public int getCoord(int i, int c);
    public boolean contains(CoordPair cp);
    public boolean contains(int x, int y);
    public int getMin(int c);
    public int getMax(int c);
    public int size();
}
