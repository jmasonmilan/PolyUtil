/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;
import polyutilgh.polyforms.Polyform;
import java.math.BigInteger;
import polyutilgh.*;
/**
 *
 * @author Sony
 * abstract filter
 */
public abstract class Filter {
    Filter nextOne;
    long count;
    boolean dbg;
    public  Filter(Filter nextOneParam) {

        this.nextOne = nextOneParam;
    }
   
    public abstract boolean write(long n, BigInteger rep);
    public abstract boolean write(long n, Polyform poly);
    public  boolean write(long n, String line) {
        int qq = 1 / 0;
        return false;
    }
    public abstract void close();
    public void setOffset(int n) {
        count = n;
    }
    protected boolean innerWrite(long n, Polyform poly) {
        //if (poly.size() == 0) if (dbg) { int i = 1 / 0; } else dbg = true;
        return nextOne.write(n,  poly);
    }    
    protected boolean innerWrite(Filter alternativeNextOne, long n, Polyform poly) {
        //if (poly.size() == 0) if (dbg) { int i = 1 / 0; } else dbg = true;
        return alternativeNextOne.write(n,  poly);
    }    
    protected boolean innerWrite(long n, BigInteger rep) {
        
        return nextOne.write(n,  rep);
    }
    protected boolean innerWrite(long n, String line) {
        
        return nextOne.write(n,  line);
    }
    protected void usage() {
        System.err.println("should not be here " + this.getClass().getSimpleName());
        System.exit(1);
    }
    protected void usage(String param) {
        System.err.println("should not be here " + this.getClass().getSimpleName() + " " + param);
        System.exit(1);
    }
    protected void memoryFlush() {
        this.nextOne.memoryFlush();
    }
}
