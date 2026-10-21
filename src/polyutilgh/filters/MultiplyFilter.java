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
 * 
 */
public class MultiplyFilter extends OnlyRepFilter {
    
    BigInteger mult;
    boolean byN;
    ParameterAnalyzer pa;
    /**
     * 
     * @param nextOneParam: next filter down the line
     * @param params : parameters mult=n
     */
    public MultiplyFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] { "mult"};
        pa = new ParameterAnalyzer(params , rules); 
        
       
        String smult = pa.getStringValue("mult", true);
        if (smult.equals("n"))
            byN = true;
        else
            mult =  new BigInteger(smult);
        
        count = 1;
    }
    public boolean write(long n, BigInteger rep) {
        if (n >= 1) {
            if (byN) {
                rep = rep.multiply(BigInteger.valueOf(count));
                return innerWrite(count++, rep);
            }
            else
                return innerWrite(count++, mult.multiply(rep));
        }            
        return true;
     
    }
    public boolean write(long n, String line) {
        if (n >= 1) {
            String newLine = "";
            String bits[] = line.split(";");
            for (int i = 0; i < bits.length; i++) {
                newLine += multiply(n, new BigInteger(bits[i])).toString();
                if (i < bits.length - 1)
                    newLine += ";";
            }
            return innerWrite(count++, newLine);
        }            
        return true;
     
    }
    BigInteger multiply(long n, BigInteger rep) {
        if (byN) {
            rep = rep.multiply(BigInteger.valueOf(count));
            return  rep;
        }
        else
            return  mult.multiply(rep);        
    }
    public void close() {
        
    }      
}
