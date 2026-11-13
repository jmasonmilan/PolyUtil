
package polyutilgh.filters;
import polyutilgh.polyforms.Polyform;
import java.io.*;
import java.math.BigInteger;
import polyutilgh.CoordPair;
import polyutilgh.*;
/**
 *
 * @author jmason
 * draw polyform in ascii art
 */
public class DrawerFilter extends OnlyPolyFilter {
    
    public DrawerFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
    }

    public boolean write(long n, Polyform poly) {
        //Polyomino p = (Polyomino)poly;
        System.out.println(poly.drawing());
              
        return this.innerWrite(count++, poly);
               
          
    }  
    public void close() {
        
    }        
       
}
