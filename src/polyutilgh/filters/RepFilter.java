/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import polyutilgh.polyforms.Polyform;
import java.io.*;
import java.math.BigInteger;
/**
 *
 * @author jmason
 * input polyform, output representation
 */
public class RepFilter extends OnlyPolyFilter {
    
    public RepFilter(Filter nextOneParam) {
        super(nextOneParam);
       
    }

    public boolean write(long n, Polyform inPoly) {
        
        
        return innerWrite(count++, inPoly.getRep());
        
        
          
    }     
    public void close() {
        
    }           
  
}

    
 
