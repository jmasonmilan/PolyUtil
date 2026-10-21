/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import polyutilgh.polyforms.Polyform;
import java.io.*;
import java.math.BigInteger;
import polyutilgh.*;
/**
 *
 * @author jmason
 * abstract class for filters that only read representations
 */
public abstract class OnlyRepFilter extends Filter {
    public OnlyRepFilter(Filter nextOneParam) {
        super(nextOneParam);
    }
    public boolean write(long n, Polyform poly) {
        
        usage();
        return true;
          
    }     
             
  
}

    
 
