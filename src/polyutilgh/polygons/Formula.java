/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polygons;

/**
 *
 * @author mason
 */
public class Formula {
    private boolean onlyX, onlyY;
    private double a, k; // ax + y = k
    public Formula(LineSegment e) {
        if (e.isHoriz()) {
            onlyY = true;
            k = e.getS().getY();
            return;
        }
        if (e.isVert()) {
            onlyX = true;
            k = e.getS().getX();
            a = 1;
            return;
        }  
        a = (e.getE().getY() - e.getS().getY()) / (e.getS().getX() - e.getE().getX());
        k = a * e.getS().getX() + e.getS().getY();
    }
    public Formula(boolean onlyX, boolean onlyY, double a, double k) {
        this.onlyX = onlyX;
        this.onlyY = onlyY;
        this.a = a;
        this.k = k;
    }
    public boolean equals(Object o) {
        Formula fo = (Formula)o;
        if (onlyX != fo.onlyX)
            return false;
        
        if (onlyY != fo.onlyY)
            return false;
        if (!Comparison.equals(a, fo.a))
            return false;
        if (!Comparison.equals(k, fo.k))
            return false;
        return true;
    }
    public String toString() {
        if (onlyX)
            return "x = " + k;
        if (onlyY)
            return "y = " + k;
        return a + " * x + y = " + k;
    }
    public Formula addConstant(double c) {
        return new Formula(onlyX, onlyY, a, k + c);
    }
    double deduceX(double inY) {
        if (onlyX)
            return k;
        return (k - inY) / a;
    }
    double deduceY(double inX) {
        if (onlyY)
            return k;
        return k - (a * inX);
    }
    public DCoord intersect(Formula other) {
        if (isParallel(other))
            return null;
        if (this.onlyX && other.onlyX) { int k = 1 / 0; }
        if (this.onlyY && other.onlyY) { int k = 1 / 0; }
        if (this.onlyX) {
            return new DCoord(this.k, other.deduceY(this.k));
        }
        if (this.onlyY) {
            return new DCoord(other.deduceX(this.k), this.k);                
        }
        if (other.onlyX) {
            return new DCoord(other.k, this.deduceY(other.k));
        }
        if (other.onlyY) {
            return new DCoord(this.deduceX(other.k), other.k);                
        }
        double tx = (this.k - other.k) / (this.a - other.a);
        return new DCoord(tx, this.deduceY(tx));
    }
    double evaluate(double x, double y) {
        if (onlyX )
            return x - k;
        if (onlyY )
            return y - k;
        return a * x + y - k;
    }    
    double evaluate(DCoord d) {
        return evaluate(d.getX(), d.getY());
    }    
    public boolean isParallel(Formula other) {
        if (this.onlyX && other.onlyX )
            return true;
        if (this.onlyY && other.onlyY )
            return true;
        if (this.onlyX || this.onlyY || other.onlyX || other.onlyY)
            return false;
        if (Comparison.equals(a, other.a))
            return true;
        return false;
    }
    public boolean passesThrough(DCoord point) {
        double v = evaluate(point.getX(), point.getY());
        if (Comparison.equals(v, 0))
            return true;
        else
            return false;
    }
    /*
    generate a formula parallel to this one that passes through specified point
    */
    public Formula findParallel(DCoord other) {
        Formula ret;
        if (onlyX) {
            // x = k
            ret = new Formula(onlyX, onlyY, a, other.getX());
        } else         if (onlyY) {
            // y = k
            ret = new Formula(onlyX, onlyY, a, other.getY());
        } else {
        // ax + y = k
            ret = new Formula(onlyX, onlyY, a, a * other.getX() + other.getY());
        }
        if (!ret.passesThrough(other)) {
            int qq = 1 / 0;
        }
        return ret;
    }
    /*
    find the 2 points on the line defined by the formula, at distance "distance" from the input coordinate (presumed satisfying the formula)
    */
    public DCoord[] findPoints(DCoord in, double distance) {
        if (!this.passesThrough(in)) {
            int qq = 1 / 0;
        }
        DCoord[] ret = new DCoord[2];
        if (onlyX) {
            // x = k
            ret[0] = new DCoord(k, in.getY() + distance);
            ret[1] = new DCoord(k, in.getY() - distance);
           
        } else  if (onlyY) {
            // y = k
            ret[0] = new DCoord(in.getX() + distance, k);
            ret[1] = new DCoord(in.getX() - distance, k);
        } else {
        // ax + y = k
        
        /*
        (nx,ny) = (x +- d/sqrt(1+a^2), y -+ ad/sqrt(1+a^2))
        */
            double v1 = distance / Math.sqrt(1d + a * a);
            double v2 = a * distance / Math.sqrt(1d + a * a);
            double nx = in.getX() + v1, ny = in.getY() - v2;
            ret[0] = new DCoord(nx, ny);
            nx = in.getX() - v1;
            ny = in.getY() + v2;
            ret[1] = new DCoord(nx, ny);
        }
        return ret;
    }
    double square(double in) {
        return in * in;
    }
}
