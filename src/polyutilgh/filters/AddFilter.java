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
public class AddFilter extends OnlyRepFilter {
    BufferedReader br;
    BigInteger mult, divisor;
    ParameterAnalyzer pa;
    int nOver;
    int skip;
    String compareFileParam;
    /**
     * 
     * @param nextOneParam: next filter down the line
     * @param params : parameters file=xxxx\nnnnnn.txt[,mult=k][,nover=d]
     */
    public AddFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"file", "mult", "nover", "divisor"};
        pa = new ParameterAnalyzer(params , rules); 
        nOver = pa.getIntValue("nover", 0);
        compareFileParam = pa.getStringValue("file") ;
        String smult = pa.getStringValue("mult");
        if (smult != null)
            mult =  new BigInteger(smult);
        String sdivisor = pa.getStringValue("divisor");
        if (sdivisor != null)
            divisor =  new BigInteger(sdivisor);
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(compareFileParam))));
                        
        } catch (IOException e) {            
            System.err.println(e.getMessage());
            System.exit(1);
        }  
        count = 1;
        
    }
    public boolean write(long n, BigInteger rep) {
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
                    System.err.println("add failed at " + count);
                    System.err.println("add file : " + s);
                    
                    System.exit(1);                

                }
                
                int p = s.indexOf(" ") + 1;
                BigInteger adder = new BigInteger(s.substring(p));
                if (adder.compareTo(BigInteger.ZERO) < 0 || rep.compareTo(BigInteger.ZERO) < 0) {
                    return innerWrite(count++, new BigInteger("-1"));
                }
                BigInteger orig = adder;
                if (mult != null)
                    adder = adder.multiply(mult);
                if (divisor != null) {
                    BigInteger oadder = adder;
                    adder = adder.divide(divisor);
                    if (!orig.equals(adder.multiply(divisor))) {
                        System.err.println("bad divide");
                        System.err.println("diding " + oadder + " by " + divisor);
                        System.exit(1);
                    }
                }
                if (DebugFilter.add)
                    System.err.println(compareFileParam + " line " + (count) + " was " + rep + " becomes " + adder.add(rep) + " because of " + orig + " times " + mult);
                return innerWrite(count++, adder.add(rep));
            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);
            }              
        }
        
        return true;
        
        
    }
    public boolean write(long n, String line) {
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
                    System.err.println("add failed at " + count);
                    System.err.println("add file : " + s);
                    
                    System.exit(1);                

                }
                
                int p = s.indexOf(" ") + 1;
                String oline = s.substring(p);
                String newLine = add(line, oline);
                return innerWrite(count++, newLine);
                /*
                BigInteger adder = new BigInteger(s.substring(p));
                if (adder.compareTo(BigInteger.ZERO) < 0 || rep.compareTo(BigInteger.ZERO) < 0) {
                    return innerWrite(count++, new BigInteger("-1"));
                }
                if (mult != null)
                    adder = adder.multiply(mult);
                return innerWrite(count++, adder.add(rep));
*/
            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);
            }              
        }
        
        return true;
        
        
    }
    String  add(String line,  String oline) {
        String ret = "";
        String bits[] = line.split(";");
        String obits[] = oline.split(";");
        for (int i = 0; i < bits.length; i++) {
            BigInteger rep = new BigInteger(bits[i]);
            BigInteger adder = new BigInteger(obits[i]);
            if (adder.compareTo(BigInteger.ZERO) < 0 || rep.compareTo(BigInteger.ZERO) < 0) {
                    return new BigInteger("-1").toString();
            }
            if (mult != null)
                adder = adder.multiply(mult);
            ret += adder.add(rep).toString();
            if (i < bits.length - 1)
                ret += ";";
        }
        return ret;
    }
    public void close() {
        
    }      
}
