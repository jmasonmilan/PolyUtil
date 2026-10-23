package polyutilgh.polygons;
import java.util.*;
/**
 *
 * @author mason
 */
public class Circle {
    DCoord centre;
    double radius;
    double diameter;
    public Circle(DCoord inCentre, double inRadius) {
        centre = inCentre;
        radius = inRadius;
        diameter = 2 * radius;
    }
    public double getRadius() {
        return radius;
    }
    public double getDiameter() {
        return diameter;
    }
    public DCoord getCentre() {
        return centre;
    }
    public boolean contains(DCoord point) {
        double d = LineSegment.distance(centre, point);
        return Comparison.equals(d, radius);
    }
    public String toString() {
        return centre.toString() + "/" + radius;
    }
    public ArrayList<DCoord> contains(ArrayList<DCoord> list) {
        ArrayList<DCoord> ret = new ArrayList<>();
        for (DCoord d : list)
            if (contains(d))
                ret.add(d);
        return ret;
    }
    public boolean allInDisc(ArrayList<DCoord> list) {
        
        for (DCoord d : list)
            if (!inDisc(d))
                return false;
        return true;
    }
    public boolean inDisc(DCoord d) {
        return Comparison.le(LineSegment.distance(d, centre), radius);
    }
}
