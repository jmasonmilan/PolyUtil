package polyutilgh.filters;
import java.util.*;
import java.math.BigInteger;
/**
 *
 * @author jmason
 * sorts data lines according to value
 * eliminates duplicates 
 */
public class SortFilter extends OnlyRepFilter {
    ArrayList<BigInteger> array;
    public SortFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        array = new ArrayList<>();
       
    }
    public boolean write(long n, BigInteger rep) {
        array.add(rep);
        return true;
    }
   
    public void close() {
        int count = 0;
        BigInteger prev = null;
        Collections.sort(array);
        for (int i = 0; i < array.size(); i++) {
            BigInteger current = array.get(i);
            if (prev != null && prev.equals(current))
                continue;
            innerWrite(count++, current);
            prev = current;
        }
    }           
  
}

    
 
