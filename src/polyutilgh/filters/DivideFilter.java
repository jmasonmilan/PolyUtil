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
 * divides values by input divisor
 */
public class DivideFilter extends OnlyRepFilter {
    
    BigInteger divisor;
    ParameterAnalyzer pa;
    boolean byN;
    /**
     * 
     * @param nextOneParam: next filter down the line
     * @param params : parameters divisor=m
     */
    public DivideFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] { "divisor"};
        pa = new ParameterAnalyzer(params , rules); 
        
       
        String sdiv = pa.getStringValue("divisor", true);
        if (sdiv.equals("n"))
            byN = true;
        else
            divisor =  new BigInteger(sdiv);
        
        count = 1;
    }
    public boolean write(long n, BigInteger rep) {
        if (n >= 1) {
            
            return innerWrite(count++, divide(n, rep));
        }            
        return true;
     
    }
    public boolean write(long n, String line) {
        if (n >= 1) {
            String bits[] = line.split(";");
            //BigInteger results[] = new BigInteger[bits.length];
            String newLine = "";
            for (int i = 0; i < bits.length; i++) {
                BigInteger result = divide(n, new BigInteger(bits[i]));
                newLine += result.toString();
                if (i < bits.length - 1)
                    newLine += ";";
            }
            return innerWrite(count++, newLine);
        }            
        return true;
     
    }
    BigInteger divide(long n, BigInteger rep) {
        if (rep.compareTo(BigInteger.ZERO) < 0) {
            return  new BigInteger("-1");
        }                
        if (byN)
            divisor = BigInteger.valueOf(n);
        if (!rep.remainder(divisor).equals(BigInteger.ZERO)) {
            System.err.println("cannot divide " + rep + " by " + divisor + " at " + n);
            System.err.println("(" + rep.divide(divisor) + ")");
            System.exit(1);
        }
        return  rep.divide(divisor);        
    }
    public void close() {
        
    }      
}
