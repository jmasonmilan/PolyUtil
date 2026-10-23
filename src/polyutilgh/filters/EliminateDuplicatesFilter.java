package polyutilgh.filters;
import java.util.*;
import java.math.BigInteger;
/**
 *
 * @author jmason
 * eliminates duplicates
 */
public class EliminateDuplicatesFilter extends OnlyRepFilter {
    Hashtable h;
    public EliminateDuplicatesFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        h = new Hashtable<>();
       
    }
    public boolean write(long n, BigInteger rep) {
        String srep = rep.toString();
        if (h.get(srep) == null) {
            h.put(srep, srep);
            return innerWrite(count++, rep);
        }
        return true;
    }
   
    public void close() {
    
    }           
  
}

    
 
