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
 * for each line of the input, this filter will add the value of the corresponding line in the named file and output the sum.
 * optionally it will multiply the input file value first
 * 
 * CONVERT TO USE FILEREADER
 * 
 * file=xxxx\nnnnnn.txt[,mult=n]
 */
public class ModFilter extends OnlyRepFilter {
    
    BigInteger  divisor;
    int divi;
    ParameterAnalyzer pa;
    int[] array;
    /**
     * 
     * @param nextOneParam: next filter down the line
     * @param params : parameters file=xxxx\nnnnnn.txt[,mult=k][,nover=d]
     */
    public ModFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"divisor"};
        pa = new ParameterAnalyzer(params , rules); 
        
        String sdivisor = pa.getStringValue("divisor", true);
        
        divisor =  new BigInteger(sdivisor);
        divi = Integer.parseInt(sdivisor);
        count = 1;
        array = new int[divi];
    }
    public boolean write(long n, BigInteger rep) {
        
        BigInteger rem = rep.remainder(divisor);
        int remi = rem.intValue();
        array[remi]++;
        return true;
        
        
    }
    public boolean write(long n, String line) {
        
        
        return true;
        
        
    }
    
    public void close() {
        for (int i = 0; i < divi; i++)
            System.out.println(i + " " + array[i]);
    }      
}
