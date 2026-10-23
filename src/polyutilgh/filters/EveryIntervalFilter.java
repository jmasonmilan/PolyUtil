package polyutilgh.filters;
import polyutilgh.polyforms.Polyform;
import java.io.*;
import java.math.BigInteger;
import polyutilgh.*;
/**
 *
 * @author jmason
 * output only every n data lines; useful for leaving only every second entry, etc
 */
public class EveryIntervalFilter extends Filter {
    int interval;
    ParameterAnalyzer pa;
    public EveryIntervalFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"interval"};
        pa = new ParameterAnalyzer(params , rules);
        this.interval = pa.getIntValue("interval");
        
    }
    public boolean write(long n, BigInteger rep) {
        if ((n % interval) == 0)
            return innerWrite(count++, rep);
        
        return true;
    }
    public boolean write(long n, Polyform poly) {
        if ((n % interval) == 0)
            return innerWrite(count++, poly);
        
        return true;
    }    
    public void close() {
        
    }           
  
}

    
 
