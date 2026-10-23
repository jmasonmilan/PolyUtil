package polyutilgh.filters;

import polyutilgh.polyforms.Polyform;
import java.math.BigInteger;
import polyutilgh.*;

/**
 *
 * @author Sony
 * writes data to stdout
 */
public class StdoutFilter extends Filter {
    boolean lineno = true;
    public StdoutFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"nolineno", "first"};
        ParameterAnalyzer pa = new ParameterAnalyzer(params , rules);
        if (pa.getBooleanValue("nolineno"))
            lineno = false;
        count = pa.getIntValue("first", 0);
    }
 
    public boolean write(long n, BigInteger rep) {
        String line  = "";
        if (lineno)
            line += n  + " " ;
        line += rep.toString();
        System.out.println(line);
        count++;
        return true;
    }
    public boolean write(long n, String in) {
        String line  = "";
        if (lineno)
            line += n  + " " ;
        line += in;
        System.out.println(line);
        count++;
        return true;
    }
    public boolean write(long n, Polyform poly) {
        write(count, poly.getRep());
        return true;
    }
    public void close() {
        System.err.println("write " + (count) + " lines");
    }
}
