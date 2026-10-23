package polyutilgh.functions;
import java.math.BigInteger;
/**
 *
 * @author mason
 */
public class Force {
    int n, h;
    BigInteger v;
    boolean defined, hDefined;
    public Force(int n, int v) {
        this.n = n;
        this.v = BigInteger.valueOf(v);
        
        this.defined = true;
    }
    public Force(int n, int h, int v) {
        this.n = n;
        this.h = h;
        this.v = BigInteger.valueOf(v);
        this.defined = true;
        hDefined = true;
    }
    public Force(int n, BigInteger v) {
        this.n = n;
        this.v = v;
    }
    public Force(int n, int h, BigInteger v) {
        this.n = n;
        this.v = v;
        this.h = h;
        hDefined = true;
    }
    public int getN() {
        return n;
    }
    public BigInteger getV() {
        return v;
    }
    public int getH() {
        return h;
    }
    public String toString() {
        if (defined)
            return "if n = " + n + " then " + v + "\r\n";
        else
            return "";
    }
    public String toStringH() {
        if (defined)
            return "if n = " + n + " and h = " + h + " then " + v + "\r\n";
        else
            return "";
    }
    
}
