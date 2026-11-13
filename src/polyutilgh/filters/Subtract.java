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
public class Subtract extends OnlyRepFilter {
    BufferedReader br;
    BigInteger mult, value;
    int nOver;
    ParameterAnalyzer pa;
    /**
     * 
     * @param nextOneParam: next filter down the line
     * @param params : parameters file=xxxx\nnnnnn.txt || value=k
     */
    public Subtract(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"file", "mult", "value", "nover"};
        pa = new ParameterAnalyzer(params , rules); 
        nOver = pa.getIntValue("nover", 0);
        int v = pa.getIntValue("value", 0);
        if (v > 0)
            value = BigInteger.valueOf(v);
        else {
            String compareFileParam = pa.getStringValue("file") ;
            String smult = pa.getStringValue("mult");
            if (smult != null)
                mult =  new BigInteger(smult);
            try {
                br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(
                                        new File(compareFileParam))));

            } catch (IOException e) {            
                e.printStackTrace();
                System.exit(1);
            }  
        }
        count = 1;
    }
    public boolean write(long n, BigInteger rep) {
        if (value != null) {
            return innerWrite(n, rep.subtract(value));
        }
            
        if (n >= 1) {
            try {
                long startLine = n;
                if (nOver > 0) {
                    if (n % nOver != 0)
                        return innerWrite(count++, rep);;
                    startLine = n / nOver;
                }                
                String s = null ;
                while (true) {
                    s = br.readLine();
                    if (s == null)
                        break;
                    if (s.startsWith("#"))
                        continue;
                    if (s.startsWith("0 "))
                        continue;
                    break;
                }
                if (s == null) {
                    System.err.println("terminated " + pa.getStringValue("file") );
                    return false;
                    
                }

                

                if (!s.startsWith(startLine +  " ")) {
                    System.err.println("subtract failed at " + count);
                    System.err.println("subtract file : " + s);
                    
                    System.exit(1);                

                }
                
                int p = s.indexOf(" ") + 1;
                BigInteger subtracter = new BigInteger(s.substring(p));
                if (subtracter.compareTo(BigInteger.ZERO) < 0 || rep.compareTo(BigInteger.ZERO) < 0) {
                    return innerWrite(count++, new BigInteger("-1"));
                }                
                if (mult != null)
                    subtracter = subtracter.multiply(mult);
                return innerWrite(count++, rep.subtract(subtracter));
            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);
            }              
        }
        
        return true;
        
        
    }
    public boolean write(long n, String line) {
        if (value != null) {
            int qq = 1 / 0; // not implemented yet
        }
            
        if (n >= 1) {
            try {

                String s = null ;
                while (true) {
                    s = br.readLine();
                    if (s == null)
                        break;
                    if (s.startsWith("#"))
                        continue;
                    if (s.startsWith("0 "))
                        continue;
                    break;
                }
                if (s == null) {
                    System.err.println("terminated " + pa.getStringValue("file") );
                    return false;
                    
                }

                

                if (!s.startsWith(n +  " ")) {
                    System.err.println("subtract failed at " + count);
                    System.err.println("subtract file : " + s);
                    
                    System.exit(1);                

                }
                
                int p = s.indexOf(" ") + 1;
                String subtracterLine = s.substring(p);
                String subtracters[] = subtracterLine.split(";");
                String current[] = line.split(";");
                String newLine = "";
                for (int i = 0; i < current.length; i++) {
                    BigInteger subtracter = new BigInteger(subtracters[i]);
                    BigInteger rep = new BigInteger(current[i]);
                    if (mult != null)
                        subtracter = subtracter.multiply(mult);
                    BigInteger result = rep.subtract(subtracter);
                    newLine += result.toString();
                    if (i < current.length - 1)
                        newLine += ";";
                }
                return innerWrite(count++, newLine); 
                /*
                BigInteger subtracter = new BigInteger(s.substring(p));
                if (subtracter.compareTo(BigInteger.ZERO) < 0 || rep.compareTo(BigInteger.ZERO) < 0) {
                    return innerWrite(count++, new BigInteger("-1"));
                }                
                if (mult != null)
                    subtracter = subtracter.multiply(mult);
                return innerWrite(count++, rep.subtract(subtracter)); */ 
            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);
            }              
        }
        
        return true;
        
        
    }
    
    public void close() {
        
    }      
}
