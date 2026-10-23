package polyutilgh.filters;
import java.io.*;
import java.math.BigInteger;
import polyutilgh.*;


import java.util.*;

/**
 *
 * @author jmason
 * output the first few input date lines in comma separated format, up to 1000 characters,
 * ready for OEIS
 */
public class CommaStdoutFilter extends OnlyRepFilter {
    
    String ret  = "";
    
    public CommaStdoutFilter(Filter nextOneParam) {
        super(nextOneParam);
       
        
    }
    public boolean write(long n, BigInteger rep) {
        if (ret.length() > 1000)
            return false;
        if (ret.length() > 0)
            ret += ", ";
        ret += rep.toString();
        
        return true;
    }
 
    public void close() {
        PolyUtilGH.logOutput(ret);
    }    
    
    
}
