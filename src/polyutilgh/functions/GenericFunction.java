package polyutilgh.functions;
import java.math.BigInteger;
import java.util.*;
/**
 *
 * @author mason
 */
public abstract class GenericFunction extends Function {
    Hashtable<String, BigInteger> obligedValues;
    ArrayList<Force> forceList;
    int minimum, minHeight;
    BigInteger hard, soft;    
    public int debug = 0;
    public GenericFunction(String inName) {
        super(inName);
        forceList = new ArrayList<>();
        obligedValues = new Hashtable<>();
    }
    public void addForce(int n, int v){
        forceList.add(new Force(n, v));
    }    
    public void shouldBe(int n, int v) {
        obligedValues.put("" + n, BigInteger.valueOf(v));
    }
    public void addForce(int n,  int h, int v){
        forceList.add(new Force(n, h, v));
    }    
    public void shouldBe(int n, int h, int v) {
        obligedValues.put(n + " " + h, BigInteger.valueOf(v));
    }
    public void addToDependencies(ArrayList<GenericFunction> list) {
        list.add(this);
    }
    public void setMinimum(int inMinimum) {
        minimum = inMinimum;
    }
    public void setMinimum(int inMinimum, int minHeight) {
        //odd.setMinimum(inMinimum);
        //even.setMinimum(inMinimum);
        minimum = inMinimum;
        this.minHeight = minHeight;
    }
    public void addForce(int n, BigInteger v) {
        forceList.add(new Force(n, v));
        this.hasBeenDefined = true;
    }
    public void addForce(int n, int h, BigInteger v) {
        forceList.add(new Force(n, h, v));
        this.hasBeenDefined = true;
    }    
    public final BigInteger value(int n)  throws FunctionError {
        if (this.hasBeenDefined != true) {
            System.err.println(name + " has not been defined");
            int qq = 1 / 0;
        }
        if (n > 0 && n == debug) {
            int dbg = 1;
        }
        BigInteger ret = null;
        if (n == 0) {
            ret = BigInteger.ZERO;
        }
        if (ret == null)
            for (Force force : forceList) {
                if (force.hDefined)
                    continue;
                if (n == force.getN()) {
                    ret = force.getV();
                    break;
                }
            }
        if (ret == null && n < minimum)
            ret = BigInteger.ZERO;
        if (ret == null && hard != null)
            ret = hard;

            
        if (ret == null) {
            ret = innerValue(n);
            if (ret.compareTo(BigInteger.ZERO) < 0) {
                ret = innerValue(n);
                int qq = 1 / 0;
            }
            addForce(n, ret);
        }
        if (name.equals("w3_lc_cr") && n == 4) {
            int dbg = 1;
        }
        return ret;
    }   
    public final BigInteger value(int n, int h)  throws FunctionError {
        BigInteger ret = null;
        for (Force force : forceList) {
            if (!force.hDefined)
                continue;
            if (n == force.getN() && h == force.getH()) {
                ret = force.getV();
                break;
            }
            
        }
        if (ret == null && n < minimum)
            ret = BigInteger.ZERO;
        if (h < minHeight)
            return BigInteger.ZERO;
        if (ret == null && soft != null) {
            if (n == h)
                ret = hard;       
            else
                ret = BigInteger.ZERO;
        }
        if (ret == null) {
            ret = innerValue(n, h);
            addForce(n, h, ret);
        }
        
        return ret;
    }    
    abstract BigInteger innerValue(int n)  throws FunctionError ;
    abstract BigInteger innerValue(int n, int h)  throws FunctionError ;
    
}
