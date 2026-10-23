package polyutilgh;
import polyutilgh.polyforms.Polyform;
import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author jmason
 * used to maintain a list of unique polyforms
 */
public class PolySet {
//    ArrayList<BigInteger> array ;
    ArrayList<String> arrays ;
    ArrayList<Polyform> before , after;
    Hashtable ht;
    Hashtable<String, Polyform> nh;
    
    int mustBeSize;
    boolean debug;
    long in, out;
    public PolySet() {

        standardInit();
    }
    public PolySet(boolean newWay) {
        if (!newWay)
            standardInit();
        else {
            nh = new Hashtable<>();
        }
    }
    public PolySet(int mustBeSize) {

        standardInit();
        this.mustBeSize = mustBeSize;
    }
    void standardInit() {
        arrays = new ArrayList<>();
        ht = new Hashtable();
    }
    public void setDebug() {
        debug = true;
        before = new ArrayList<>();
        after = new ArrayList<>();
        
    }
    public ArrayList<Polyform> getList() {
        return before;
    }
    public boolean contains(String in) {
        if (ht.get(in) == null)
            return false;
        else
            return true;
    }
    public boolean adds(String s) {    
       
        in++;
        
        return innerAdds(s);
    }
    boolean innerAdds(String s) {    
      
        
        if (ht.get(s) != null)
            return false;
        arrays.add(s);
        ht.put(s, s);
        out++;
        return true;
    }
    public int size() {
        if (nh != null)
            return nh.size();
        return arrays.size();
    }
    /*
    public BigInteger get(int i) {
        return array.get(i);
    }
*/
    public String gets(int i) {
        return arrays.get(i);
    }
    /*
    public boolean addUnique(Polyform poly) {
        return add(poly.getUnique());
    }
    */
    public Hashtable<String, Polyform> getNewHash() {
        return nh;
    }
    public boolean addUniques(Polyform poly) {
        in++;
        if (debug) {
            before.add(poly);
        }
        if (nh != null) {
            String s = poly.getUnique();
            /*
            if (s.contains("0.6")) {
                poly.getUnique();
            } */
            if (nh.get(s) != null)
                return false;
            nh.put(s, poly);
            return true;
        }
        if (ht.get(poly.getRep().toString()) != null) {
            
            return false;
        }
        String s = poly.getUnique();
        
        if (mustBeSize != 0 && mustBeSize != poly.size()) {
            System.err.println("expected " + mustBeSize + " but found " + poly.size());
            int qq = 1 / 0;
        }
        boolean added = innerAdds(s);
        if (added && debug)
            after.add(poly);
        return added;
    }
    // for debugging
    public boolean addUniques(Polyform poly, Polyform father, int call) {
        in++;
        if (debug) {
            before.add(poly);
        }
        if (ht.get(poly.getRep().toString()) != null) {
            
            return false;
        }
        String s = poly.getUnique();
        
        
        if (s.equals("8857199")) {
            
            System.err.println("ps " + call + " " + s.toString());
            System.err.println("father " + father.getRep().toString());
            int dbg = 1;
            
        }

        if (mustBeSize != 0 && mustBeSize != poly.size()) {
            System.err.println("expected " + mustBeSize + " but found " + poly.size());
            int qq = 1 / 0;
        }
        boolean added = innerAdds(s);
        if (added && debug)
            after.add(poly);
        return added;
    }
    public void sort() {
        Collections.sort(arrays);
    }
    public void add(PolySet other) {
        arrays.addAll(other.arrays);
    }
    public void dump() {
        System.err.println("in");
        dump(before);
        System.err.println("out");
        dump(after);
        
    }
    void dump(ArrayList<Polyform> list) {
        for (Polyform p : list) {
            System.err.println(p.drawing());
        }
    }
    public long getInSize() {
        return in;
    }
    public long getOutSize() {
        return out;
    }
    
}
