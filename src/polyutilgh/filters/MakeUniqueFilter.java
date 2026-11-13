/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import polyutilgh.polyforms.Polyform;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.BigInteger;
import polyutilgh.*;
import polyutilgh.ParameterAnalyzer;
import polyutilgh.PolySet;
/**
 *
 * @author jmason
 * converts input polyform to its unique free representation
 */
public class MakeUniqueFilter extends OnlyPolyFilter {
    String reverse;
    ParameterAnalyzer pa;
    public MakeUniqueFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"reverse"};
        pa = new ParameterAnalyzer(params , rules);  
        reverse = pa.getStringValue("reverse");
    }    

    public boolean write(long n, Polyform poly) {
        // 18742473055952330317824
       
        Polyform npoly = poly.maker(poly.getUnique());
        if (reverse != null) {
            if (npoly.getRep().toString().equals(reverse))
                return this.innerWrite(count++, poly);
            else
                return true;
        } else
            return this.innerWrite(count++, npoly);
          
          
    }  
    public void close() {
        
    }
   
}
