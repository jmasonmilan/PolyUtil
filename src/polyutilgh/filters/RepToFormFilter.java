/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.filters;

import polyutilgh.polyforms.*;
import java.io.*;
import java.math.BigInteger;
import polyutilgh.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
/**
 *
 * @author jmason
 * convert representation to polyform
 */
public  class RepToFormFilter extends RepFilter {
    String form;
    boolean multi;
    int[] ss;
    public RepToFormFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"form", "sizeset", "multi"};
        ParameterAnalyzer pa = new ParameterAnalyzer(params , rules);
        this.form = pa.getStringValue("form", true);
        this.multi = pa.getBooleanValue("multi");
        String sss = pa.getStringValue("sizeset");
        if (sss != null) {
            String ssss[] = sss.split("/");
            ss = new int[ssss.length];
            for (int i = 0; i < ssss.length; i++) {
                ss[i] = Integer.parseInt(ssss[i]);
            }
        }
    }
    public boolean write(long n, BigInteger rep) {
        Polyform poly = null;
        if (rep.toString().equals("1144056238563172736")) {
            int dbgg = 1;
        }
        /*
        if (form.equals("Polyomino")) poly = new Polyomino(rep); else
        if (form.equals("SAPolygon")) poly = new SAPolygon(rep); else
        if (form.equals("CBPolyomino")) poly = new CBPolyomino(rep); else
        if (form.equals("PerimeterPolyomino")) poly = new PerimeterPolyomino(rep); else
        if (form.equals("PerimeterPolyiamond")) poly = new PerimeterPolyiamond(rep); else
        if (form.equals("Polyiamond")) poly = new Polyiamond(rep); else
        if (form.equals("CBPolyhex")) poly = new CBPolyhex(rep); else
        if (form.equals("Polyhex")) poly = new Polyhex(rep); else
        if (form.equals("Polyrect")) poly = new Polyrect(rep); else
        if (form.equals("Polycombo")) poly = new Polycombo(rep, ss); else
        if (form.equals("SideView")) poly = new SideView(rep); else
        if (form.equals("CompleteView")) poly = new CompleteView(rep); else
        if (form.equals("SAP")) poly = new SAPolygon(rep); else
        if (form.equals("CBPolyiamond")) poly = new CBPolyiamond(rep); else {
            System.err.println("bad form " + form);
            System.exit(1);
        }
*/
        //String myClassName = CompleteView.class.getName();
        Object object = null;
        try {
            Class<?> c = Class.forName("polyutilgh.polyforms." + form);
            Constructor<?> cons;
            if (ss == null) {
                cons = c.getConstructor(BigInteger.class);
                object = cons.newInstance(rep); 
            }
            else {
                cons = c.getConstructor(BigInteger.class, int[].class);
                object = cons.newInstance(rep, ss); 
            }
              
        } catch (Exception e) {
            System.err.println("Could not create instance of " + form);
            System.exit(1);
        }
        Polyform p = (Polyform)object;
        if (multi) {
            p = p.multiMaker();
        }
        if (rep.toString().equals("1144056238563172736")) {
            int dbg = 1;
        }
        return nextOne.write(count++, p);
          
    }     
             
    public void close() {
        
    }    
}

    
 
