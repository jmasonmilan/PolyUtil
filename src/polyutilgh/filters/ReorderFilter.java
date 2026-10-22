/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import java.util.*;
import java.math.BigInteger;
import polyutilgh.ParameterAnalyzer;
import polyutilgh.polyforms.*;
import java.io.*;
/**
 *
 * @author jmason
 * sorts polyforms according to size 
 * writes numbers
 * does not eliminate duplicates
 */
public class ReorderFilter extends OnlyPolyFilter {
    FileWriter array[];
    String key;
    ParameterAnalyzer pa;
    public ReorderFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"key"};
        pa = new ParameterAnalyzer(params , rules);  
        key = pa.getStringValue("key", true);
        array = new FileWriter[99]; // inelegant but sufficient
       // fw = new FileWriter(fileName);
    }
    public boolean write(long n, Polyform poly) {
        int size = poly.size();
        try {
            if (array[size] == null)
                array[size] = new FileWriter(filename(size));
        
            array[size].write(poly.getRep().toString() + System.lineSeparator());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return true;
    }
    String filename(int size) {
        return "various" + File.separator + key + "-tmp-" + size + ".txt";
    }
    public void close() {
        int count = 0;
        
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null)
                    continue;
                try {
                    array[i].close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
            }
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null)
                    continue;
                String s = filename(i);
                BufferedReader br = null;
                try {
                     br = new BufferedReader(new InputStreamReader(new FileInputStream(s)));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
                while (true)  {
                    String line = null;
                    try {
                        line = br.readLine();
                        if (line == null)
                            break;
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        System.exit(1);
                    }
                    innerWrite(count++, new BigInteger(line));
                }
                try {
                    br.close();
                } catch (Exception e) {
                        System.err.println(e.getMessage());
                        System.exit(1);
                }
                
            }
            
        
    }           
  
}

    
 
