/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import java.io.*;
import java.math.BigInteger;
import polyutilgh.*;
/**
 *
 * @author jmason
 * abstract class for filters that only read polyforms
 */
public abstract class OnlyPolyFilter extends Filter {
    public OnlyPolyFilter(Filter nextOneParam) {
        super(nextOneParam);
    }
    public boolean write(long n, BigInteger rep) {
        
        usage();
        return true;
          
    }     
             
  
}

    
 
