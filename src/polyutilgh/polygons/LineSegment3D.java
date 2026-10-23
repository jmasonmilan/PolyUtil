package polyutilgh.polygons;
import polyutilgh.*;
/**
 *
 * @author mason
 */
public class LineSegment3D {
    protected DCoord3D sc, ec;
   
    //private  double justABit = 0.0000001;
    public LineSegment3D(DCoord3D insc, DCoord3D inec) {
        sc = insc;
        ec = inec;
                    
    }
    protected LineSegment3D() {
        
    }
    public LineSegment3D invert() {
        return new LineSegment3D(ec, sc);
    }
    public DCoord3D getS() { return sc; }
    public DCoord3D getE() { return ec; }
   
    public String toString() {
        return "(" + sc.toString() + ")-(" + ec.toString() + ")";
    }
    public String toSortedString() {
        String s = sc.toString(), e = ec.toString();
        if (s.compareTo(e) < 0)
            return "(" + s + ")-(" + e + ")";
        else
            return "(" + e + ")-(" + s + ")";
    }
    public LineSegment3D nextSegment() {
        return new LineSegment3D(ec, ec.addDelta(ec.subtract(sc)));
    }
    public LineSegment3D  extendToUnitLength() {
        double len = length();
        double one = 1;
        double factor = one / len;
        DCoord3D v = getVector();
        return new LineSegment3D(sc, sc.addDelta(v.multiply(factor)));
    }
    public DCoord3D getVector() {
        return ec.subtract(sc);
    }
    public DCoord3D getPercentagePoint(double percentage) {
        double hundred = 100;
        return sc.addDelta(getVector().multiply(percentage / hundred));
    }
    public DCoord3D findPoint(DCoord c) {
        double dx = ec.getX() - sc.getX();
        double mult;
        if (!Comparison.isZero(dx)) {
            mult = (c.getX() - sc.getX()) / dx;
        } else {
            double dy = ec.getY() - sc.getY();
            mult = (c.getY() - sc.getY()) / dy;
        }
        double newX = sc.getX() + mult * (ec.getX() - sc.getX());
        double newY = sc.getY() + mult * (ec.getY() - sc.getY());
        double newZ = sc.getZ() + mult * (ec.getZ() - sc.getZ());
        return new DCoord3D(newX, newY, newZ);
    }    
    /*
    protected boolean edgeContainsPointOnLine(DCoord c) {
        if (strictlyWithin(sc.getX(), ec.getX(), c.getX()) && strictlyWithin(sc.getY(), ec.getY(), c.getY())) 
            return true;
        else
            return false;
    }
    boolean strictlyWithin(double r1, double r2, double v) {
        if (r1 < r2) {
            return v > r1 + justABit && v < r2 - justABit;
        } else {
            return v > r2 + justABit && v < r1 - justABit;
        }
    }
    public boolean intersects(LineSegment3D other) {
        DCoord c = new Formula(this).intersect(new Formula(other));
        if (c == null)
            return false;
        return this.edgeContainsPointOnLine(c) && other.edgeContainsPointOnLine(c);
    }
    public boolean sameSide(DCoord c1, DCoord c2) {
        Formula f = new Formula(this);
        double v1 = f.evaluate(c1.getX(), c1.getY());
        double v2 = f.evaluate(c2.getX(), c2.getY());
        return (v1 * v2 > 0);
    }    
    public boolean sameEndpoints(LineSegment3D other) {
        if (sc == other.sc && ec == other.ec)
            return true;
        if (sc == other.ec && sc == other.ec)
            return true;
        return false;
    }
    */
    public boolean coincidentEndpoints(LineSegment3D other) {
        if (sc.coincidesWith(other.sc) && ec.coincidesWith(other.ec))
            return true;
        if (sc.coincidesWith(other.ec) && ec.coincidesWith(other.sc))
            return true;
        return false;
    }
    /*
    public boolean hasEndpoint(DCoord c) {
        return (sc == c) || (ec == c);
    }
    public boolean containsApprox(DCoord c) {
        if (sc.coincidesWith(c) || ec.coincidesWith(c))
            return true;
        return false;
    }
    */
    public boolean isParaZ0() {
        return Comparison.equals(sc.getZ(), ec.getZ());
    }
    public boolean isParaY0() {
        return Comparison.equals(sc.getY(), ec.getY());
    }
    public boolean isParaX0() {
        return Comparison.equals(sc.getX(), ec.getX());
    }
    /*
    public boolean totallyContains(DCoord c) {
        Formula f = new Formula(this);
        DCoord nc = new DCoord(c.getX(), f.deduceY(c.getX()));
        return c.coincidesWith(nc);
    }
    */
    public double length() {       
        //return distance(sc, ec);
        return sc.distance(ec);
    }
    /* moved to DCoord3D
    public static double distance(DCoord3D c1, DCoord3D c2) {
        double xs = Math.pow(c1.getX() - c2.getX(), 2);
        double ys = Math.pow(c1.getY() - c2.getY(), 2);
        double zs = Math.pow(c1.getZ() - c2.getZ(), 2);
        double sum = xs + ys + zs;
        double ret = Math.sqrt(sum);
        return ret;        
    }
    */
    /*
    public DCoord halfway() {
        double x = (sc.getX() + ec.getX()) / 2;
        double y = (sc.getY() + ec.getY()) / 2;
        return new DCoord(x, y);
        
    }
    public LineSegment3D bisector() {
        DCoord h = halfway();
        DCoord scn = new DCoord(h.getX() - (ec.getY() - h.getY()), h.getY() + (ec.getX() - h.getX()));
        DCoord ecn = new DCoord(h.getX() + (ec.getY() - h.getY()), h.getY() - (ec.getX() - h.getX()));
        return new LineSegment3D(scn, ecn);
    }
    */
    /*
    return that point that is a distance of d before the endpoint
    */
    /*
    public DCoord lenghtenEndBy(double d) {
        double portion = d / length();
        DCoord ret = new DCoord(ec.getX() + (ec.getX() - sc.getX()) * portion, ec.getY() + (ec.getY() - sc.getY()) * portion);
        return ret;
    }
    public DCoord lenghtenStartBy(double d) {
        double portion = d / length();
        DCoord ret = new DCoord(sc.getX() + (sc.getX() - ec.getX()) * portion, sc.getY() + (sc.getY() - ec.getY()) * portion);
        return ret;
    }
    */
}
