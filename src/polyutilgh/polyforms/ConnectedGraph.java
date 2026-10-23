package polyutilgh.polyforms;

import java.math.BigInteger;
import java.util.*;
import polyutilgh.GraphicsParameters;
import polyutilgh.*;
/**
 *
 * @author mason
 */
public class ConnectedGraph extends Polyform {
    ArrayList<GraphNode> array;
    public ConnectedGraph(BigInteger inValue) {
        array = build(inValue);
    }
    public ConnectedGraph() {
        array = new ArrayList<>();
    }
    public void seal() {
        rep = buildRep();
        sealed = true;
    }
    public void displayOnJpeg(GraphicsParameters gp) {
        
    } 
    public int getHeight() {
        return 1 / 0;
    }
    public int getWidth() {
        return 1 / 0;
    }
    public BigInteger buildRep(Hashtable<String, String> h, String best) {
        BigInteger ret = null;
        int[][] arrangements = GenerateArrangements.generate(size());
        for (int[] arrangement : arrangements) {
            BigInteger rep = buildRep(arrangement);
            if (h != null)
                h.put(rep.toString(), best);
            if (ret == null || rep.compareTo(ret) < 0)
                ret = rep;
        }
        return ret;
    }
    public String getUnique() {
        
        return rep.toString();
    }
    public void loadReps(Hashtable<String, String> h, String s) {
        
        buildRep(h, s);
    }
    public BigInteger buildRep() {
        return buildRep(null, null);
    }
    public BigInteger buildRep(int[] arrangement) {
        BigInteger ret = BigInteger.ZERO;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                ret = ret.shiftLeft(1);
                int xi = arrangement[i],  xj = arrangement[j];
                if (array.get(xi).get(xj))
                    ret = ret.add(BigInteger.ONE);
            }
        }
        return ret;
    }
    public  Polyform maker(String in) {
        return new ConnectedGraph(new BigInteger(in));
    }
    public  Polyform maker() {
        return new ConnectedGraph();
    }
    public  int size() {
        return array.size();
    }
    public  String drawing() {
        String ret = "";
        for (GraphNode n : array)
            ret += n.toString() + "-";
        return ret;
    }
    public  PolySet genNew(int limit) {
        PolySet ret = new PolySet();
        
        for (int bits = 1 ; bits < Math.pow(2, size()); bits++) {
            ConnectedGraph g = add(bits);
            ret.addUniques(g);
        }
        return ret;
    }
    ConnectedGraph add(int bits) {
        ConnectedGraph ret = grow();
        for (int i = 0; i < size() ; i++)
            if ((bits & (1 << i)) != 0) {
                ret.array.get(i).set(size());
                ret.array.get(size()).set(i);
            }
        ret.seal();
        return ret;
    }
    public void init(int size) {
        for (int i = 0; i < size; i++)
            array.add(new GraphNode(this, size));
    }
    ConnectedGraph grow() {
        ConnectedGraph ret = (ConnectedGraph)maker();
        for (int i = 0; i < size() ; i++)
            ret.array.add(array.get(i).grow(ret));
        ret.array.add(new GraphNode(ret, size() + 1));
        return ret;
    }
    ArrayList<GraphNode> build(BigInteger in) {
        ArrayList<GraphNode> ret = new ArrayList<>();
        if (in.compareTo(BigInteger.ZERO) == 0) {
            ret.add(new GraphNode(this, 1));
        } else {
            int len = in.bitLength();
            int s = (int)(Math.sqrt(len));
            if (s * s < len)
                s++;
            for (int nn = 0; nn < s; nn++)
                ret.add(new GraphNode(this, in, nn * s, s));
        }
        rep = in;
        sealed = true;
        return ret;
    }
    
}
