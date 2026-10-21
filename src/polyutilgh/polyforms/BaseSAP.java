/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;

/**
 *
 * @author mason
 */
public abstract class BaseSAP extends Polyform {
    String srep; // internal
    //String crep; // external
    int array[][];
    int height, width;
    int minv = 0, maxv = 0, minh = 0, maxh = 0;    
    String minimise(String in) {
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
    String getSRep() {
        return srep;
    }
    public int getWidth() { return width; }
    public int getMaxV() { return maxv; }
    public int getMaxH() { return maxh; }
    public int getMinV() { return minv; }
    public int getMinH() { return minh; }
    public int getHeight() { return height; }    
    public int size() {
        return perimeter() / 2;
    }
    public int perimeter() {
        return rep.toString().length();
    }    
}
