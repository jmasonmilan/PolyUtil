package polyutilgh.filters;

import polyutilgh.polyforms.Polyform;
import java.math.BigInteger;
import polyutilgh.*;
import java.io.*;
/**
 *
 * @author Mason
 * Divides data into 100 files, with or without line numbers
 */
public class HundredFilter extends Filter {
    boolean lineno = true;
    String prefix;
    FileWriter[] fw;
    public HundredFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"nolineno", "prefix"};
        ParameterAnalyzer pa = new ParameterAnalyzer(params , rules);
        if (pa.getBooleanValue("nolineno"))
            lineno = false;
        prefix = pa.getStringValue("prefix", true);

    }
    boolean initialised;
    void init() {
        if (initialised)
            return;
        initialised= true;
        fw = new FileWriter[100];
        for (int i = 0; i < 100; i++) {
            String f = prefix + "-" + i + ".txt";
            
            try {
                fw[i] = new FileWriter(f);
            } catch (IOException e) {
                System.err.println(f + " " + e.getMessage());
                System.exit(1);
            }
        }        
    }
    public boolean write(long n, BigInteger rep) {
        init();
        handle(n, rep.toString());
        return true;
    }
    public boolean write(long n, String in) {
        init();
        handle(n, in);
        return true;
    }
    public boolean write(long n, Polyform poly) {
        init();
        write(count, poly.getRep());
        return true;
    }
    void handle(long n, String in) {
        int len = in.length();
        String s;
        if (len == 1)
            s = in;
        else
            s = in.substring(len - 2);
        int t = Integer.parseInt(s);
        if (lineno)
            in = n + " " + in;
        try {
            fw[t].write(in + "\r\n");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    public void close() {
        for (int i = 0; i < 100; i++) {
            
            try {
                fw[i].close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}
