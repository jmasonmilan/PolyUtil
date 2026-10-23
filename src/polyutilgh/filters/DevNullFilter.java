package polyutilgh.filters;
import polyutilgh.polyforms.Polyform;
import java.math.BigInteger;
import polyutilgh.*;
/**
 *
 * @author Sony
 * loses input data
 */
public  class DevNullFilter extends Filter{
    
    public  DevNullFilter(Filter nextOneParam) {

        super(null);
    }
    public  boolean write(long n, BigInteger rep) {return true;}
    public  boolean write(long n, Polyform poly) {return true;}
    public  void close() {}
    protected void memoryFlush() {}
    
}
