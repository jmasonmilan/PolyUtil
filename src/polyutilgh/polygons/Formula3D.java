package polyutilgh.polygons;

/**
 *
 * @author mason
 */
public class Formula3D {
    
    /* Generic case
    x-a   y-b   
    --- = --- = z-c
     d     e     
    
    where we deduce p=x2-x1, q=y2-y1, r=z2-z1
    
    Then:
    a = x1/r, b=y1/r, c=z1/r, d=p/r, e=q/r
    
    Given x, we can deduce y = b + e*(x-a)/d, and z = c + (x-a)/d
    
    if p=0 and q!=0 and r!=0 then
    y-b   z-c
    --- = ---
     e     1   
    
    b=y1/r, c=z1/r, e=q/r
    
    Given x, we cannot deduce anything!
    Given y, we can deduce x=x1, and z=c + (y-b)/e
    
    if p!=0 and q=0 and r!=0 then
    x-a   z-c
    --- = ---
     d     1   
    
    a=x1/r, c=z1/r, d=p/r
    
    Given y, we cannot deduce anything!
    Given x, we can deduce y=y1, and z=c + (x-a)/d
    
    if p!=0 and q!=0 and r=0 then
    x-a   y-b
    --- = ---
     d     1   
    
    a=x1/q, b=y1/q d=p/q
    
    Given z, we cannot deduce anything!
    Given x, we can deduce z=z1, and y=b + (x-a)/d
    
    if p=0 and q=0
    Given x or y we cannot deduce anything
    Given z we can deduce x=x1, y=y1, z=z
    
    etc
    */
    private boolean noX, noY, noZ;
    double x1, x2, y1, y2, z1, z2, a, b, c, d, e, p, q, r;
    public Formula3D(LineSegment3D edge) { 
        x1 = edge.getS().getX();
        y1 = edge.getS().getY();
        z1 = edge.getS().getZ();
        x2 = edge.getE().getX();
        y2 = edge.getE().getY();
        z2 = edge.getE().getZ();
        p = x2 - x1;
        q = y2 - y1;
        r = z2 - z1;
        noX = Comparison.isZero(p);
        noY = Comparison.isZero(q);
        noZ = Comparison.isZero(q);
        if (!noX && !noY && !noZ) {
            a = x1 / r;
            b = y1 / r;
            c = z1 / r;
            d = p / r;
            e = q / r;
        } else if (noX && noY) {
            // noop
        } else if (noX && noZ) {
            // noop
        } else if (noY && noZ) {
            // noop
        } else if (noX) {
            b = y1 / r;
            c = z1 / r;
            e = q / r;
        } else if (noY) {
            a = x1 / r;
            c = z1 / r;
            d = p / r;
        } else { // if noZ
            a = x1 / q;
            b = y1 / q;
            d = p / q;
        }
        
    }
    /*
    public String toString() {
        
    }
    */
    /*
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
    */
    /*
    double evaluate(double x, double y) {
        if (onlyX )
            return x - k;
        if (onlyY )
            return y - k;
        return a * x + y - k;
    }  
    */
    /*
    public DCoord intersect(Formula3D other) {
        if (parallel(other))
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
*/

    /*
    public boolean parallel(Formula3D other) {
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
*/
}
