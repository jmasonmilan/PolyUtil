package polyutilgh.polygons;
import polyutilgh.polyforms.*;
/**
 *
 * @author mason
 */
public abstract class Polygon {
    
    protected LineSegment edges[];
    protected int dimension;
    public Polygon(int n) {
        if (n == 0) {
            int k = 1 / 0;
        }
        dimension = n;
        
        edges = new LineSegment[n];
    }
    public abstract Polygon maker(int n);
    public int size() {
        return dimension;
    }
    public String toString() {
        String ret = "";
        for (int i = 0 ; i < dimension ; i++)
            ret += edges[i].toString() + " / ";
        ret += " | ";
        return ret;
    }

    public int getNumberOfEdges() {
        return dimension;
    }
    public boolean sharesWith(LineSegment e) {
        for (LineSegment o : edges) {
            if (e == o)
                continue;
            if (e.sharesWith(o))
                return true;
        }
        return false;
    }
    boolean hasAsCorner(DCoord cin) {
        for (int i = 0; i < dimension ; i++) {
            if (edges[i].hasTerminalPointCoincidingWith(cin)    )        
                return true;
        }  
        return false;
    }
    public boolean hasWithin(DCoord cin, boolean dbg) {
        for (int i = 0; i < dimension; i++) {
            
            
            LineSegment e = edges[i];
            if (e.totallyContains(cin))
                return false;
            DCoord copp = differentCorner(e.getS(), e.getE());
            if (!e.sameSide(copp, cin)) {
                if (dbg)
                    edges[i].sameSide(copp, cin);
                return false;
            }
        }
        return true;
    }
    DCoord differentCorner(DCoord c1, DCoord c2) {
        for (int i = 0; i < dimension; i++) {
            LineSegment e = edges[i];
            if (!e.getS().coincidesWith(c1) && !e.getS().coincidesWith(c2))
                return e.getS();
            if (!e.getE().coincidesWith(c1) && !e.getE().coincidesWith(c2))
                return e.getE();
            
        }    
        int qq = 1 / 0;
        return null;
    }
  
    
    public LineSegment getEdge(int i) {
        return this.edges[i];
    }


    public LineSegment findContiguousEdge(int i, DCoord c) {
        if (this.edges[after(i)].hasEndpoint(c))
            return this.edges[after(i)];
        if (this.edges[before(i)].hasEndpoint(c))
            return this.edges[before(i)];
        return null;
    }
    public int after(int i) {
        i++;
        if (i >= dimension)
            i = 0;
        return i;
    }
    public int before(int i) {
        i--;
        if (i < 0) 
            i = dimension - 1;
        return i;
    } 
    public boolean totallyContains(DCoord c) {
        if (this.hasAsCorner(c))
            return false;
        if (this.hasWithinEdge(c))
            return false;
        if (this.hasWithin(c, false))
            return true;
        return false;
    }
    public boolean totallyContainsCornerOf(Polygon other) {
        for (int i = 0 ; i < this.dimension ; i++) {
            LineSegment e = this.edges[i];
            boolean b1 = other.totallyContains(e.getS());
            boolean b2 = other.totallyContains(e.getE());
            if (b1 || b2)
                return true;
        }
        return false;
    }
    public boolean hasWithinEdge(DCoord c) {
        for (int i = 0 ; i < this.dimension ; i++) {
            LineSegment e = this.edges[i];
            if (e.totallyContains(c) )
                return true;
        }
        return false;        
    }
    public boolean hasIntersectingEdge(Polygon other) {
        for (LineSegment e : edges) {
            for (LineSegment oe : other.edges) {
                if (e.intersects(oe)) {
//                    e.intersects(oe); // dbg
                    return true;
                }
            }
        }
        return false;
    }
    public Polygon subtractDelta(DCoord d) {
        Polygon ret = maker(dimension);
        for (int i = 0; i < dimension; i++)
            ret.edges[i] = edges[i].subtractDelta(d);
        return ret;
    }
    public Polygon reflectV(double x) {
        Polygon ret = maker(dimension);
        for (int i = 0; i < dimension; i++)
            ret.edges[i] = edges[i].reflectV(x);
        return ret;
    }
    public Polygon rotate(double r) {
        Polygon ret = maker(dimension);
        for (int i = 0; i < dimension; i++)
            ret.edges[i] = edges[i].rotate(r);
        return ret;
    }
    public DCoord[] getVertexList() {
        DCoord[] ret = new DCoord[dimension];
        int count = 0;
        for (LineSegment edge : edges) {
            count = conditionallyAdd(ret, edge.sc, count);
            count = conditionallyAdd(ret, edge.ec, count);
        }
        return ret;
    }
    int conditionallyAdd(DCoord[] ret, DCoord c, int count) {
        if (!inList(ret, c, count)) {
            ret[count++] = c;
        }
        
        return count;
    }
    boolean inList(DCoord[] ret, DCoord c, int count) {
        for (int i = 0; i < count ; i++)
            if (ret[i].equals(c))
                return true;
        return false;
    }
}
