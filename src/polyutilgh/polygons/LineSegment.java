/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polygons;
import polyutilgh.*;
/**
 *
 * @author mason
 */
public class LineSegment {
    protected DCoord sc, ec;
    boolean forbiddenGrowth;
    private  double justABit = 0.0000001;
    public LineSegment(DCoord insc, DCoord inec) {
        sc = insc;
        ec = inec;
                    
    }
    protected LineSegment() {
        
    }
    public LineSegment copy() {
        LineSegment ret = new LineSegment(sc, ec);
        ret.forbiddenGrowth = this.forbiddenGrowth;
        return ret;
    }
    public boolean isForbidden() {
        return this.forbiddenGrowth;
    }
    public void setForbidden() {
        this.forbiddenGrowth = true;
    }
    public LineSegment invert() {
        return new LineSegment(ec, sc);
    }
    public DCoord getS() { return sc; }
    public DCoord getE() { return ec; }
   
    public String toString() {
        return "(" + sc.toString() + ")-(" + ec.toString() + ")";
    }
    public String getString() {
        String s1 = sc.getString(), s2 = ec.getString();
        if (s1.compareTo(s2) > 0 ) {
            String t = s1;
            s1 = s2;
            s2 = t;
        }
        return "(" + s1 + ")-(" + s2 + ")";
    }
    public boolean sharesWith(LineSegment o) {
        Formula f = new Formula(this);
        Formula fo = new Formula(o);
        if (!f.equals(fo))
            return false;
        return edgeContainsPointOnLine(o.sc) || edgeContainsPointOnLine(o.ec);
    }
    protected boolean edgeContainsPointOnLine(DCoord c) {
        if (strictlyWithin(sc.getX(), ec.getX(), c.getX()) && strictlyWithin(sc.getY(), ec.getY(), c.getY())) 
            return true;
        else
            return false;
    }
    boolean strictlyWithin(double r1, double r2, double v) {
        if (Comparison.equals(r1, r2)) 
            return Comparison.equals(r1, v);
        if (r1 < r2) {
            return v > r1 + justABit && v < r2 - justABit;
        } else {
            return v > r2 + justABit && v < r1 - justABit;
        }
    }
    public boolean intersects(LineSegment other) {
        /*
        DCoord c = new Formula(this).intersect(new Formula(other));
        if (c == null)
            return false;
        return this.edgeContainsPointOnLine(c) && other.edgeContainsPointOnLine(c);
        */
        return intersection(other) != null;
    }
    public DCoord intersection(LineSegment other) {
        DCoord c = new Formula(this).intersect(new Formula(other));
        if (c == null)
            return null;
        if (this.edgeContainsPointOnLine(c) && other.edgeContainsPointOnLine(c))
            return c;
        return null;
    }
    public boolean sameSide(DCoord c1, DCoord c2) {
        Formula f = new Formula(this);
        double v1 = f.evaluate(c1.getX(), c1.getY());
        double v2 = f.evaluate(c2.getX(), c2.getY());
        return (v1 * v2 > 0);
    }    
    public boolean sameEndpoints(LineSegment other) {
        if (sc == other.sc && ec == other.ec)
            return true;
        if (sc == other.ec && sc == other.ec)
            return true;
        return false;
    }
    public boolean coincidentEndpoints(LineSegment other) {
        if (sc.coincidesWith(other.sc) && ec.coincidesWith(other.ec))
            return true;
        if (sc.coincidesWith(other.ec) && ec.coincidesWith(other.sc))
            return true;
        return false;
    }
    
    public boolean hasEndpoint(DCoord c) {
        return (sc == c) || (ec == c);
    }
    public boolean hasTerminalPointCoincidingWith(DCoord c) {
        if (sc.coincidesWith(c) || ec.coincidesWith(c))
            return true;
        return false;
    }
    public boolean isHoriz() {
        return Comparison.equals(sc.getY(), ec.getY());
    }
    public boolean isVert() {
        return Comparison.equals(sc.getX(), ec.getX());
    }
   
    public boolean totallyContains(DCoord c) {
        Formula f = new Formula(this);
        /*
        DCoord nc = new DCoord(c.getX(), f.deduceY(c.getX()));
        return c.coincidesWith(nc);
        */
        if (!f.passesThrough(c))
            return false;
        if (!this.edgeContainsPointOnLine(c))
            return false;
        return true;
    }
    public double length() {       
        return distance(sc, ec);
    }
    public static double distance(DCoord c1, DCoord c2) {
        double xs = Math.pow(c1.getX() - c2.getX(), 2);
        double ys = Math.pow(c1.getY() - c2.getY(), 2);
        double sum = xs + ys;
        double ret = Math.sqrt(sum);
        return ret;        
    }
    public DCoord halfway() {
        double x = (sc.getX() + ec.getX()) / 2;
        double y = (sc.getY() + ec.getY()) / 2;
        return new DCoord(x, y);
        
    }
    /*
    return a bisector of the same length as the segment
    */
    public LineSegment bisector() {
        return bisector(1);
    }
    /*
    return a bisector of a length each side = factor * current length (so if input is length 1, and factor is 1, then bisecotr is 2)
    */
    public LineSegment bisector(double factor) {
        DCoord h = halfway();
        DCoord scn = new DCoord(h.getX() - factor * (ec.getY() - h.getY()), h.getY() + factor * (ec.getX() - h.getX()));
        DCoord ecn = new DCoord(h.getX() + factor * (ec.getY() - h.getY()), h.getY() - factor * (ec.getX() - h.getX()));
        return new LineSegment(scn, ecn);
    }
    public DCoord[] get90(DCoord c) {
        return get90(c, 1);
    }
    public DCoord[] get90(DCoord c, double factor) {
        DCoord[] ret = new DCoord[2];
        ret[0] = new DCoord(c.getX() - factor * (ec.getY() - sc.getY()), c.getY() + factor * (ec.getX() - sc.getX()));
        ret[1] = new DCoord(c.getX() + factor * (ec.getY() - sc.getY()), c.getY() - factor * (ec.getX() - sc.getX()));
        return ret;
    }
    /*
    return that point that is a distance of d before (or after?) the endpoint
    */
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
    public LineSegment[] divideBy2() {
        LineSegment[]  ret  = new LineSegment[2];
        ret[0] = new LineSegment(sc, halfway());
        ret[1] = new LineSegment(halfway(), ec);
        return ret;
    }
    public LineSegment subtractDelta(DCoord d) {
        return new LineSegment(sc.subtractDelta(d), ec.subtractDelta(d));
    }
    public LineSegment reflectV(double x) {
        return new LineSegment(sc.reflectV(x), ec.reflectV(x));
    }
    public LineSegment rotate(double r) {
        return new LineSegment(sc.rotate(r), ec.rotate(r));
    }
    public double findAngle() {
        double dx = ec.getX() - sc.getX();
        double dy = ec.getY() - sc.getY();
        double ret = Math.atan2(dy, dx);
        return ret;
    }
}
