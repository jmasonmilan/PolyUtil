package polyutilgh.filters;
import java.util.*;
import java.math.BigInteger;
/**
 *
 * @author jmason
 * eliminates duplicates
 */
public class EliminateOrderedDuplicatesFilter extends OnlyRepFilter {
    String previous = "";
    long countE = 0;
    public EliminateOrderedDuplicatesFilter(Filter nextOneParam) {
        super(nextOneParam);
        
       
    }
    public boolean write(long n, BigInteger rep) {
        String srep = rep.toString();
        if (srep.equals(previous)) {
            countE++;
            return true;
        }
        previous = srep;
        return innerWrite(count++, rep);
        
    }
   
    public void close() {
        System.err.println("Eliminated " + countE);
    }           
  
}

    
 
