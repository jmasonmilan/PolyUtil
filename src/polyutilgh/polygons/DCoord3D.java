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
public class DCoord3D {
    private double x, y, z;
    //private double nextToNothing = 0.00000001;
    public DCoord3D(double inX, double inY, double inZ) {
        x = inX;
        y = inY;
        z = inZ;
        
    }
    public String toString() {
        return toString(x) + " , " + toString(y) + " , " + toString(z);
    }
    String toString(double v ) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(v)) + "";            
    }  
    public DCoord get2D() {
        return new DCoord(x, y);
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public boolean coincidesWith(DCoord3D other) {
        if (Comparison.equals(x, other.x) && Comparison.equals(y, other.y) && Comparison.equals(z, other.z))
            return true;
        return false;
    }   
    public DCoord3D addDelta(double dx, double dy, double dz) {
        return new DCoord3D(x + dx, y + dy, z + dz);
    }
    public DCoord3D addDelta(DCoord3D other) {
        return new DCoord3D(x + other.x, y + other.y, z + other.z);
    }
    public DCoord3D multiply(double v) {
        return new DCoord3D(x * v, y * v, z * v);
    }
    
    public double distance(DCoord3D c2) {
        double xs = Math.pow(this.getX() - c2.getX(), 2);
        double ys = Math.pow(this.getY() - c2.getY(), 2);
        double zs = Math.pow(this.getZ() - c2.getZ(), 2);
        double sum = xs + ys + zs;
        double ret = Math.sqrt(sum);
        return ret;        
    }

    /*
    public DCoord3D rotateCoords(int n) {
        if (n > 0)
            return new DCoord3D(y, z, x);
        else
            return new DCoord3D(z, x, y);
    }
*/
    public DCoord3D crossProduct(DCoord3D other) {
        return new DCoord3D(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }
    public DCoord3D subtract(DCoord3D other) {
        return new DCoord3D(x - other.x , y - other.y , z - other.z);
    }
     
}
