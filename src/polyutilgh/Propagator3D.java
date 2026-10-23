package polyutilgh;
import polyutilgh.polyforms.*;
import java.util.*;

/**
 *
 * @author John Mason
 * 
 * 3D version of propagator
 */
public class Propagator3D {
    int flags[][][];
    int volume;
    Polycube poly;
    int maxX, maxY, maxZ;
    
    public Propagator3D(Polycube polyParam, int polyColour, int restColour) {
        poly = polyParam;
//        System.err.println(poly.getRep().toString());
        
        maxX = poly.getWidth();
        maxY = poly.getHeight();
        maxZ = poly.getDepth();
        
        flags = new int[maxX + 1][maxY + 1][maxZ + 1];
        
        volume = maxX * maxY * maxZ;
        
        
        for (int x = 1 ; x <= maxX; x++)
            for (int y = 1 ; y <= maxY ; y++)     
                for (int z = 1 ; z <= maxZ ; z++)     
                    flags[x][y][z] = restColour;
                
        for (int i = 0 ; i < poly.size(); i++) 
            flags[poly.getCoords(i).getX()][poly.getCoords(i).getY()][poly.getCoords(i).getZ()] = polyColour;
        //dump();
    }
    public int getVolume() {
        return volume;
    }
    public int outside(int from, int to) {
        int ret = this.poly.outside(flags, from, to);

        ret+= propagate(from, to);
        //dump();
        return ret;
    }
    public CoordTriplet find(int colour) {         
        for (int x = 1 ; x <= maxX; x++)
            for (int y = 1 ; y <= maxY ; y++)  {
                for (int z = 1 ; z <= maxZ ; z++) 
                    if (flags[x][y][z] == colour) {
                        return new CoordTriplet(x, y, z);
                    }
            } 
        System.err.println(poly.getRep().toString());
        int i = 1 / 0;
        return null;       
    }
    public int set(CoordTriplet cp, int from, int to) {
        int ret = 1;
        flags[cp.getX()][cp.getY()][cp.getZ()] = to;
        ret += propagate(from, to);
        return ret;
    }
    int propagate(int from, int to) {
        int ret = 0;
        boolean found = false;
        while (true) {
            found = false;
            for (int x = 1 ; x <= maxX; x++)
                for (int y = 1 ; y <= maxY ; y++)  
                    for (int z = 1 ; z <= maxZ ; z++) {
                        if (flags[x][y][z] == to ) {
                            int partial = propagate(x, y, z, from, to);
                            if (partial > 0) {
                                ret += partial;
                                found = true;
                            }
                        }
                }  
            if (!found)
                break;
        }
        return ret;
    }    
    int propagate(int x, int y, int z, int from, int to) {
      
        ArrayList<CoordTriplet> a = this.poly.getClose(new CoordTriplet(x, y, z), true);
        int ret = 0;
        for (int i = 0 ; i < a.size() ; i++) {
            ret += propagate2(a.get(i).getX(), a.get(i).getY(), a.get(i).getZ(), from, to);
        }
        return ret;
    }
    int propagate2(int x, int y, int z, int from, int to) {
        //if (x < 1 || x > maxX || y < 1 || y > maxY)
        //    return 0;
        if (flags[x][y][z] == from) {
            flags[x][y][z] = to;
            return 1;
        }
        return 0;
    }
    /*
    void dump() {
        for (int y = maxY; y >= 1 ; y--) {
            String line="";
            for (int x = 1 ; x <= maxX ; x++) {
                int v = flags[x][y];
                if (v == -1) 
                    line += "x";
                else if (v == -2) 
                    line += ".";
                else
                    line += v;
            }
            System.out.println(line);
        }
        System.out.println("\r\n");
    }
*/
}
