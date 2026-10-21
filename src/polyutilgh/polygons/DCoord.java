/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polygons;

import java.text.DecimalFormat;

/**
 *
 * @author mason
 */
public class DCoord {
    private double x, y;
    //private double nextToNothing = 0.00000001;
    public DCoord(double inX, double inY) {
        x = inX;
        y = inY;
    }
    public String toString() { // only for debugging
        return toString(x) + " , " + toString(y);
    }
    String toString(double v ) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(v)) + "";            
    }  
    public String getString() { 
        return getString(x) + " , " + getString(y);
    }
    String getString(double v ) {
        if (Comparison.equals(v, 0))
            v = 0;
        DecimalFormat df = new DecimalFormat("#.######");
        return Double.valueOf(df.format(v)) + "";            
    }  
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean coincidesWith(DCoord other) {
        
        return equals(other);
    }   
    public boolean equals(DCoord other) {
        //if ((Math.abs(x - other.x) < nextToNothing) && (Math.abs(y - other.y) < nextToNothing))
        if (Comparison.equals(x, other.x) && Comparison.equals(y, other.y))
            return true;
        return false;
    }   
    public DCoord addDelta(DCoord d) {
        return addDelta(d.x, d.y);
    }
    public DCoord addDelta(double dx, double dy) {
        return new DCoord(x + dx, y + dy);
    }
    
    public DCoord subtractDelta(DCoord d) {
        return subtractDelta(d.x, d.y);
    }
    public DCoord subtractDelta(double dx, double dy) {
        return new DCoord(x - dx, y - dy);
    }
    public DCoord reflectV(double vx) {
        double nx = vx - (x - vx);
        return new DCoord(nx, y);
    }
    public DCoord rotate(double r) {
        double dx = x, dy = y ;
        double ndx = dx * Math.cos(r) - dy * Math.sin(r);
        double ndy = dx * Math.sin(r) + dy * Math.cos(r);
        double nx = ndx, ny = ndy;
        return new DCoord(nx, ny);
    }
    /*
    return the point that is same distance but 180 degrees wrt to "other"
    */
    public DCoord minus(DCoord other) {
        double dx = x - other.x, dy = y - other.y;
        return addDelta(dx, dy);
    }
    /*
    delta from other to this
    other - this
    */
    public DCoord getDelta(DCoord other) {
        return new DCoord(other.x - x, other.y - y);
    }
    public DCoord multiply(double v) {
        return new DCoord(x * v, y * v);
    }
    public double distanceFromOrigin() {
        return Math.sqrt(square(x) + square(y));
    }
    public double square(double in) {
        return in * in;
    }
}
