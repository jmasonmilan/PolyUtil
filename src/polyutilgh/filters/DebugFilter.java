/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import java.io.*;
import java.math.BigInteger;
import polyutilgh.*;
import polyutilgh.polyforms.*;
/**
 *
 * @author jmason
 * for each line of the input, this filter will add the value of the corresponding line in the named file and output the sum.
 * optionally it will multiply the input file value first
 * 
 * CONVERT TO USE FILEREADER
 * 
 * file=xxxx\nnnnnn.txt[,mult=n]
 */
public class DebugFilter extends Filter {
    public static boolean add;
    ParameterAnalyzer pa;
    
    /**
     * 
     * @param nextOneParam: next filter down the line
     * @param params : parameters 
     */
    public DebugFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"add"};
        pa = new ParameterAnalyzer(params , rules); 
        add = pa.getBooleanValue("add");
        
    }
    public boolean write(long n, BigInteger rep) {
        
        return innerWrite(n, rep);
        
        
    }
    public boolean write(long n, Polyform p) {
        
        return innerWrite(n, p);
        
        
    }
    
    public void close() {
        
    }      
}
