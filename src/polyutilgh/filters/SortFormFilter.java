/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import java.util.*;
import java.math.BigInteger;
import polyutilgh.polyforms.*;
/**
 *
 * @author jmason
 * sorts polyforms according to size and to representation
 * writes numbers
 * does not eliminate duplicates
 */
public class SortFormFilter extends OnlyPolyFilter {
    ArrayList<BigInteger> array[];
    public SortFormFilter(Filter nextOneParam) {
        super(nextOneParam);
        array = new ArrayList[99]; // inelegant but sufficient
       
    }
    public boolean write(long n, Polyform poly) {
        int size = poly.size();
        if (array[size] == null)
            array[size] = new ArrayList<>();
        array[size].add(poly.getRep());
        return true;
    }
   
    public void close() {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null)
                continue;
            Collections.sort(array[i]);
            for (int j = 0; j < array[i].size(); j++)
                innerWrite(count++, array[i].get(j));
        }
    }           
  
}

    
 
