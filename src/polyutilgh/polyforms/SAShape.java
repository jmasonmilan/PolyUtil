/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;
import java.math.BigInteger;
import polyutilgh.GraphicsParameters;
import polyutilgh.PolySet;
/**
 *
 * @author mason
 * bracelet: sequence of black and white beads, with 4 more black than white
 * Represented by string of 1(B) and 2(W)
 */
public class SAShape extends Polyform {
    String srep;
    //int array[][];
    //int height, width;
    public SAShape(BigInteger inRep) {
        rep = inRep;
        srep = rep.toString();
        sealed = true;
       
    }
    public SAShape(String inRep) {
        //this.rep = inRep;
        srep = inRep;
        rep = new BigInteger(srep);
        //calcHW();
        sealed = true;
    }
    
    public String getSRep() { return srep; }
    public SAShape maker(String rep) {
        return new SAShape(rep);
    }     
    public SAShape maker() {
        return null;
    }     
    void build(BigInteger inValue) {}
    public void seal() {}
    public String getUnique() {
        return innerGetUnique(srep);
    }
    public String innerGetUnique(String in) {
        
        
        return getUnique(in);
    }
    public static String getUnique(String in) {
        String ret , srep1 = minimise(in);
        ret = srep1;
        
        srep1 = reflectV(srep1); if (srep1.compareTo(ret) < 0) ret = srep1;
        
        return ret;
    }
    public static String reflectV(String in) {
        String ret = "";
        for (int i = in.length() - 1 ; i  >= 0; i--) {
            ret += in.substring(i, i + 1);
        }
        return minimise(ret);
    }
    public static String minimise(String in) {
        String ret = in;
        String cs = in + in;
        int len = in.length();
        for (int i = 1 ; i < len ; i++) {
            String s = cs.substring(i, i + len);
            if (s.compareTo(ret) < 0)
                ret = s;
        }
        return ret;
    }
   
    public int size() {
        return perimeter() / 2;
    }
    public int perimeter() {
        return srep.length();
    }
    public PolySet genNew(int limit) {
        PolySet ret = new PolySet();
        for (int i = 0 ; i <= srep.length() ; i++) {
            String left1 = srep.substring(0, i);
            String right1 = srep.substring(i);
            String s1 = left1 + BLACK + right1;
            for (int j = 0 ; j <= s1.length() ; j++) {
                String left2 = s1.substring(0, j);
                String right2 = s1.substring(j);
                String s2 = left2 + WHITE + right2;
                ret.addUniques(new SAShape(s2));
            }            
        }
        return ret;
    }
    
    
    public String drawing() {
        
        String ret = srep;
        
            ret += "\r\n";
        
        return ret;   
    }
    public int getWidth() { int i = 1 / 0; return i; }
    public int getHeight() { int i = 1 / 0; return i; }
    public void displayOnJpeg(GraphicsParameters gp) { int i = 1 / 0 ; }
    public static String BLACK = "1";
    public static String WHITE = "2";
    
}
