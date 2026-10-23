package polyutilgh.readers;
import polyutilgh.filters.*;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
import polyutilgh.*;
/**
 *
 * @author jmason
 * for each line of the input, this filter will add the value of the corresponding line in the named file and output the sum.
 * optionally it will multiply the input file value first
 * 
 * file=xxxx\nnnnnn.txt
 */
public class SequenceReader  {
    BufferedReader br;
    
    String filename;
    /**
     * 
     * 
     */
    public SequenceReader(String sequence) {
       
        filename = sequence;
        
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(filename))));
                        
        } catch (IOException e) {            
            e.printStackTrace();
            System.exit(1);
        }  
   
        
    }
    public String getLine() {
        try {

            String s = null ;
            while (true) {
                s = br.readLine();
                if (s == null)
                    break;
                if (s.startsWith("#"))
                    continue;
                
                if (s.equals(""))
                    continue;
                return s;
                
                
            }
           
            
        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);
        }   
        return null;
    }    
    public BigInteger getRep() {
        String s = getLine();
        if (s == null)
            return null;
        int p = s.indexOf(" ") ;
        String first = s.substring(p + 1);
        BigInteger n = new BigInteger(first);       
        return n;
    }
    public void close() {
        try {
            br.close();
           
           
            
        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);
        }      
    }
}
