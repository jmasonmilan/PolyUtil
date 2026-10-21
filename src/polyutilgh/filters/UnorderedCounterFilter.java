/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import polyutilgh.polyforms.Polyform;
import java.io.*;
import java.math.BigInteger;
//import polyutil.PolySet;
import polyutilgh.*;
/**
 *
 * @author jmason
 * this will count input according to size; it does not require input to be ordered
 */
public class UnorderedCounterFilter extends OnlyPolyFilter {
    long counts[];
    long maxSize;
    ParameterAnalyzer pa;
    int size;
    long total;
    public UnorderedCounterFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        counts = new long[5000]; // inelegant but sufficient
        String[] rules = new String[] { "size"};
        pa = new ParameterAnalyzer(params , rules);  
        size = pa.getIntValue("size", 0);
    }
       

    public boolean write(long n, Polyform inPoly) {
        //Polyform poly = (Polyform) inPoly;  
        //System.err.println("count size " + poly.size());
        boolean ret = true;
        counts[inPoly.logicalSize()]++;
        if (inPoly.logicalSize() > this.maxSize)
            this.maxSize = inPoly.logicalSize();
        return ret;               
    }  
    public void close() {
        if (size == 0)
            for (int i = 0 ; i <= maxSize; i++) 
                nextOne.write(i, new BigInteger(counts[i] + ""));
        else {
            for (int i = 0 ; i <= maxSize; i++) 
                total += counts[i];
            nextOne.write(size, new BigInteger(total + ""));
        }
    }     
    
}
