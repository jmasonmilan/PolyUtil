package polyutilgh.filters;
import polyutilgh.polyforms.*;
import java.math.*;
import polyutilgh.*;
/**
 *
 * @author jmason
 * passes polyominoes satisfying size restrictions
 */
public class BiTrisectableFilter extends OnlyPolyFilter {
    int number;
    
    ParameterAnalyzer pa;
    public BiTrisectableFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"num"};
        pa = new ParameterAnalyzer(params , rules);  
        number = pa.getIntValue("num");
        
        
    }

    public boolean write(long n, Polyform inPoly) {
        //Polyomino poly = (Polyomino) inPoly;
        
        //    return innerWrite(count++, inPoly);
        PolySet ps = inPoly.getBiTrisect(number);
        for (int i = 0; i < ps.size(); i++) {
            String s = ps.gets(i);
            innerWrite(count++, new BigInteger(s));
        }
        return true;
          
    }     
    public void close() {
        
    }           
  
}

    
 
