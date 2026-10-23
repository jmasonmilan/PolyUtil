package polyutilgh.polyforms;

import java.awt.Color;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import polyutilgh.CoordPair;
import polyutilgh.CoordTriplet;
import polyutilgh.GraphicsParameters;
import polyutilgh.Mu;
import polyutilgh.PolySet;
import polyutilgh.*;

/**
 *
 * @author mason
 */
public class Polycube extends BasePolycube implements GenericPolycubeInterface {
    public Polycube() {
        super();
    }
    public Polycube(BigInteger inValue) {
        super(inValue);  
    } 
    public Polycube maker(String srep) {
        return new Polycube(new BigInteger(srep));
    }   
    public Polycube maker(BigInteger rep) {
        return new Polycube(rep);
    }       
    public Polycube maker() {
        return new Polycube();
    }     
    public Polycube align(boolean seal) {
        int dx = this.getMin(0) - 1;
        int dy = this.getMin(1) - 1;
        int dz = this.getMin(2) - 1;
        Polycube p = (Polycube)maker();
        for (CoordTriplet cp : array) {
            CoordTriplet ncp = cp.add(-dx, -dy, -dz);
            p.insert(ncp);
        }
        if (seal)
            p.seal();
        return p;
    }    
    public int getMin(int c) {
        int ret = 9999;
        for (CoordTriplet cp : array)
            if (cp.getCoord(c) < ret)
                ret = cp.getCoord(c);
        return ret;
    }
    public int getTypeK() {
        int o = 0;
        if (getHeight() % 2 == 1)
            o++;   
        if (getWidth() % 2 == 1)
            o++;   
        if (getDepth() % 2 == 1)
            o++;   
        switch(o) {
            case 0: return 1;
            case 1 : return 4;
            case 2: return 3;
            case 3: return 2;
        }
        return -1;
    }
    public PolySet getBiTrisect(int number) {
        PolySet ps = new PolySet();
        ArrayList<Polycube> fixed = getFixedList();
        for (Polycube f : fixed)
            getBiTrisect(ps, f);
        
        for (int n = 3; n <= number; n++) {
            PolySet nps = new PolySet();
            for (int i = 0; i < ps.size(); i++) {
                String s = ps.gets(i);
                Polycube p = new Polycube(new BigInteger(s));
                for (Polycube f : fixed)
                    p.getBiTrisect(nps, f);
            }
            ps = nps;
        }
        
        return ps;
    }
    void getBiTrisect(PolySet ps, Polycube f) {
        ArrayList<CoordTriplet> first = new ArrayList<>();
        for (CoordTriplet cp : array)
            for (int i = 0; i < 6; i++)
                getBiTrisect(ps, cp, i, f, first);
        
    }
   
    void getBiTrisect(PolySet ps, CoordTriplet cp, int i, Polycube f, ArrayList<CoordTriplet> first) {
        int nx = cp.getX() + deltaX[i];
        int ny = cp.getY() + deltaY[i];
        int nz = cp.getZ() + deltaZ[i];
        if (contains(nx, ny, nz))
            return;
        
        getBiTrisect(ps, nx, ny, nz, f, first);
        
        
    }
    void getBiTrisect(PolySet ps, int nx, int ny, int nz, Polycube f, ArrayList<CoordTriplet> first) {
        for (int i = 0; i < f.size(); i++)
            getBiTrisect(ps, nx, ny, nz, f, f.array.get(i).getX(), f.array.get(i).getY(), f.array.get(i).getZ(), first);
        
    }
    void getBiTrisect(PolySet ps, int nx, int ny, int nz, Polycube f, int fx, int fy, int fz, ArrayList<CoordTriplet> first) {
        Polycube np = (Polycube)(this.copy());
        for (int i = 0; i < f.size(); i++) {
            CoordTriplet fcp = f.array.get(i);
            int x = fcp.getX(), y = fcp.getY(), z = fcp.getZ();
            int nnx = nx + x - fx, nny = ny + y - fy, nnz = nz + z - fz;
            CoordTriplet ncp = new CoordTriplet(nnx, nny, nnz);
            if (i == 0) {
                if (first.contains(ncp))
                    return;
                first.add(ncp);
            }
            if (np.contains(ncp))
                return;
            np.insert(ncp);
        }
        Polyform nnp = np.align(true);
        
        ps.addUniques(nnp);
    }
    public PolySet genNew(int limit) {
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("1");
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            tryOne(i, ps, 1, 0, 0);
            tryOne(i, ps, -1, 0, 0);
            tryOne(i, ps, 0, 1, 0);
            tryOne(i, ps, 0, -1, 0);     
            tryOne(i, ps, 0, 0, 1);     
            tryOne(i, ps, 0, 0, -1);     
            
        }
        return ps;
    } 
    void tryOne(int i, PolySet ps, int dx, int dy, int dz) {
        int x = array.get(i).getX() + dx, y = array.get(i).getY() + dy, z = array.get(i).getZ() + dz;
        BasePolycube p = addOneCoords(x, y, z );
        if (p != null) 
            ps.addUniques(p);
    }  
    Polycube addOneCoords(int x, int y, int z) {
        BasePolycube p1 = this;
        if (x < 1) {
            p1 = (BasePolycube)(p1.shift(1, 0, 0));
            x++;
        } 
        if (y < 1) {
            p1 = (BasePolycube)(p1.shift(0, 1, 0));
            y++;
        } 
        if (z < 1) {
            p1 = (BasePolycube)(p1.shift(0, 0, 1));
            z++;
        } 
        CoordTriplet cp = new CoordTriplet(x, y, z);

        if (p1.canAdd(cp))
            return maker(p1.getRep().setBit(getBit(x, y, z)));
        else 
            return null;
         
    }   
    public boolean testAsym() {
        return getFixedList().size() == 48;
    }
    public WCCommon getPointed(int i) {
        Polycube ret = new Polycube();
        for (int j = 0; j < this.size(); j++) {
            CoordTriplet cp = this.getCoords(j);
            int x = cp.getX(), y = cp.getY(), z = cp.getZ();
            int bx, by, bz;
            
            bx = 3 * x - 2;
            by = 3 * y - 2;
            bz = 3 * z - 2;
                
            for (int dx = 0; dx < 3; dx += 2)
                for (int dy = 0; dy < 3; dy += 2)
                    for (int dz = 0; dz < 3; dz += 2) {
                        
                            ret.unconditionallyInsert(bx + dx, by + dy, bz + dz);
                    }
            
                if (i == j)
                                ret.unconditionallyInsert(bx + 1, by + 1, bz + 1);
                       
            
        }
        
        ret.seal();
        return ret;        
    }
    
    public ArrayList<Polycube> getFixedList() {
        ArrayList<Polycube> ret = new ArrayList<>();
        PolySet ps = this.getFixed();
        for (int i = 0; i < ps.size(); i++)
            ret.add(new Polycube(new BigInteger(ps.gets(i))));
        return ret;
    }
    public PolySet getFixed() {
        
        PolySet ps = new PolySet();
        
        getFixedMirror(ps);
       
        return ps;
    }   
    private void getFixedMirror(PolySet ps) {
        Polycube p;
        
        getFixedRotate(ps);
        p = (Polycube)(this.mirrorxy());
        p.getFixedRotate(ps);
        p = (Polycube)(p.mirrorxz());
        p.getFixedRotate(ps);
        p = (Polycube)(p.mirrorxy());
        p.getFixedRotate(ps);
        p = (Polycube)(p.mirrorxz());
        p.getFixedRotate(ps);
        p = (Polycube)(p.mirrorxy());
        p.getFixedRotate(ps);
        
    }  
    
    void getFixedRotate(PolySet ps) {
        ps.adds(this.getRep().toString());
        Polycube p  = (Polycube)(rotxy());
        ps.adds(p.getRep().toString());
        p = (Polycube)(p.rotxy());
        ps.adds(p.getRep().toString());
        p = (Polycube)(p.rotxy());
        ps.adds(p.getRep().toString());
        p = (Polycube)(p.mirrorz());
        ps.adds(p.getRep().toString());
        p = (Polycube)(p.rotxy());
        ps.adds(p.getRep().toString());
        p = (Polycube)(p.rotxy());
        ps.adds(p.getRep().toString());
        p = (Polycube)(p.rotxy());
        ps.adds(p.getRep().toString());

       
    }   
    public CoordTriplet findLeaf() {
        for (CoordTriplet cp : array) {
            if (adjacent(cp) == 1)
                return cp;
        }
        return null;
    }
    public int adjacent(CoordTriplet cp) {
        int ret = 0;
        int x = cp.getX(), y = cp.getY(), z = cp.getZ();
        ret += adjacent(x, y, z, 1, 0, 0);
        ret += adjacent(x, y, z, -1, 0, 0);
        ret += adjacent(x, y, z, 0, 1, 0);
        ret += adjacent(x, y, z, 0, -1, 0);
        ret += adjacent(x, y, z, 0, 0, 1);
        ret += adjacent(x, y, z, 0, 0, -1);
        return ret;
    }
    int adjacent(int x, int y, int z, int dx, int dy, int dz) {
        if (contains(x + dx, y + dy, z + dz))
            return 1;
        else
            return 0;
    }
    public Polycube copyExcept(CoordTriplet cp) {
        Polycube np = new Polycube();
        for (CoordTriplet tcp : array) {
            if (!cp.equals(tcp))
                np.insert(tcp);
        }
        np.seal();
        return np;
    }
    Hashtable forIntact;
    void checkIntact() {
        if (array.size() == 0)
            return;
        forIntact = new Hashtable();
        checkIntact(array.get(0));
        if (forIntact.size() == array.size())
            return;
        int qq = 1 / 0;
        return;
    }
    void checkIntact(CoordTriplet cp) {
        String s = cp.toString();
        if (forIntact.get(s) != null)
            return;
        forIntact.put(s, s);
        checkIntact(cp, 1, 0, 0);
        checkIntact(cp, -1, 0, 0);
        checkIntact(cp, 0, 1, 0);
        checkIntact(cp, 0, -1, 0);
        checkIntact(cp, 0, 0, 1);
        checkIntact(cp, 0, 0, -1);
        
    }
    void checkIntact(CoordTriplet cp, int dx, int dy, int dz) {
        CoordTriplet ncp = new CoordTriplet(cp.getX() + dx, cp.getY() + dy, cp.getZ() + dz);
        if (this.contains(ncp))
            checkIntact(ncp);
    }  
    public int getX(int i) {
        return array.get(i).getCoord(0);
    }
    public int getY(int i) {
        return array.get(i).getCoord(1);
    }
    public int getZ(int i) {
        return array.get(i).getCoord(2);
    }
    public int getCoord(int i, int c) {
        return array.get(i).getCoord(c);
    }
    
       
    public int countHoles() {
        Propagator3D prop = new Propagator3D(this, -1, 0);
        int total = prop.getVolume() - this.size();
        int holes = 0;
        total -= prop.outside(0, -2); // set outside edges and propagate
        
        /*
        0 unknown
        -2 outside
        -1 body
        1 first hole
        2 second hole
        ...
        */
        
        
        while (total > 0) {
            holes++;
            CoordTriplet cp = prop.find(0);
            total -= prop.set(cp, 0, holes);
                     
        }
        return holes;
    }      
    public int outside(int[][][] flags, int from, int to) {
        int ret = 0;
        for (int x = 1 ; x <= maxX; x++)
            for (int z = 1 ; z <= maxZ; z++) {            
                if (flags[x][1][z] == from) {
                    flags[x][1][z] = to;
                    ret++;
                }
            
                if (flags[x][maxY][z] == from) {
                    flags[x][maxY][z] = to;
                    ret++;
                }              
        }
        for (int y = 1 ; y <= maxY; y++)
            for (int z = 1 ; z <= maxZ; z++) {            
                if (flags[1][y][z] == from) {
                    flags[1][y][z] = to;
                    ret++;
                }
            
                if (flags[maxX][y][z] == from) {
                    flags[maxX][y][z] = to;
                    ret++;
                }              
        }
        for (int x = 1 ; x <= maxX; x++)
            for (int y = 1 ; y <= maxY; y++) {            
                if (flags[x][y][1] == from) {
                    flags[x][y][1] = to;
                    ret++;
                }
            
                if (flags[x][y][maxZ] == from) {
                    flags[x][y][maxZ] = to;
                    ret++;
                }              
        }
        


        
        return ret;
    } 
    public ArrayList<CoordTriplet> getClose(CoordTriplet cp, boolean within) {
        ArrayList<CoordTriplet> a = new ArrayList<>();
        getClose(a, cp, 1, 0, 0, within);
        getClose(a, cp, -1, 0, 0, within);
        getClose(a, cp, 0, 1, 0, within);
        getClose(a, cp, 0, -1, 0, within);
        getClose(a, cp, 0, 0, 1, within);
        getClose(a, cp, 0, 0, -1, within);
        return a;
    }   
    protected void getClose(ArrayList<CoordTriplet> a, CoordTriplet cp, int dx, int dy, int dz, boolean within) {
        int x = cp.getX() + dx;
        int y = cp.getY() + dy;
        int z = cp.getZ() + dz;
        if (within && (x < 1 || x > maxX || y < 1 || y > maxY || z < 1 || z > maxZ))
            return;
        a.add(new CoordTriplet(x, y, z));
    }      

    /*
    mirror symmetry in x = y
    */
    public boolean testMirrorXEY() {
        return testMirrorDiag(2);
    }
    public boolean testMirrorXEZ() {
        return testMirrorDiag(1);
    }
    public boolean testMirrorYEZ() {
        return testMirrorDiag(0);
    }
    boolean testMirrorDiag(int c) {
        for (CoordTriplet cp : array) {
            CoordTriplet mcp = cp.swap2(c);
            if (!contains(mcp))
                return false;
        }
        return true;        
    }
    public boolean testMirrorXEMY() {
        return testMirrorOppDiag(2);
    }
    public boolean testMirrorXEMZ() {
        return testMirrorOppDiag(1);
    }
    public boolean testMirrorYEMZ() {
        return testMirrorOppDiag(0);
    }
    boolean testMirrorOppDiag(int c) {
        int max1 = getMax((c + 1) % 3);
        int max2 = getMax((c + 2) % 3);
        if (max1 != max2) {
            return false;
        }
        for (CoordTriplet cp : array) {
            CoordTriplet mcp = mirrorDiag(cp, c, max1);
            if (!contains(mcp))
                return false;
        }
        return true;        
    }
    CoordTriplet mirrorDiag(CoordTriplet cp, int c, int max) {
        int x = cp.getX(), y = cp.getY(), z = cp.getZ();
        if (c == 0) {
            y = max - cp.getZ() + 1;
            z = max - cp.getY() + 1;
        } else if (c == 1) {
            x = max - cp.getZ() + 1;
            z = max - cp.getX() + 1;
        } else {
            x = max - cp.getY() + 1;
            y = max - cp.getX() + 1;
        } 
        return new CoordTriplet(x, y, z);
    }
    /*
    mirror symmetry in z = 0
    */
    public boolean testMirrorZE0() {
        return testMirrorCE0(2);
    }
    public boolean testMirrorYE0() {
        return testMirrorCE0(1);
    }
    public boolean testMirrorXE0() {
        return testMirrorCE0(0);
    }
    boolean testMirrorCE0(int c) {
        for (CoordTriplet cp : array) {
            CoordTriplet mcp = mirrorCE0(cp, c);
            if (!contains(mcp))
                return false;
        }
        return true;
    }
    CoordTriplet mirrorCE0(CoordTriplet cp, int c) {
        int max = getMax(c);
        int x = cp.getX(), y = cp.getY(), z = cp.getZ();
        if (c == 0)
            x = max - x + 1;
        else if (c == 1)
            y = max - y + 1;
        else
            z = max - z + 1;
        return new CoordTriplet(x, y, z);
    }  
    public ArrayList<Polycube> getDuplicates() {
        ArrayList<Polycube> ret = new ArrayList<>();
        getDuplicates(ret, 0, 1, 2);
        getDuplicates(ret, 1, 0, 2);
        getDuplicates(ret, 2, 0, 1);
        return ret;
    }
    void getDuplicates(ArrayList<Polycube> ret, int c1, int c2, int c3) {
        int max = getMax(c1);
        Polycube p = new Polycube();
        for (CoordTriplet ct : array) {
            p.add(ct);
            int c = ct.getCoord(c1);
            int nc = max * 2 - c + 1;
            CoordTriplet nct = new CoordTriplet(c1, nc, c2, ct.getCoord(c2), c3, ct.getCoord(c3));
            p.add(nct);
        }
        p.seal();
        Polycube q = p.getUniquePolycube();
        if (!ret.contains(q))
            ret.add(q);
        
        p = new Polycube();
        for (CoordTriplet ct : array) {
            
            int c = ct.getCoord(c1);
            int nc = max + c;
            CoordTriplet nct = new CoordTriplet(c1, nc, c2, ct.getCoord(c2), c3, ct.getCoord(c3));
            p.add(nct);
            nc = max - c + 1;
            nct = new CoordTriplet(c1, nc, c2, ct.getCoord(c2), c3, ct.getCoord(c3));
            p.add(nct);
        }
        p.seal();
        q = p.getUniquePolycube();
        if (!ret.contains(q))
            ret.add(q);
            
    }
    public Polycube getUniquePolycube() {
        Polycube q = new Polycube(new BigInteger(this.getUnique()));
        return q;
    }
    public boolean intact() {
        ArrayList<CoordTriplet> a = new ArrayList<>();
        a.add(array.get(0));
       
        for (int i = 0 ; i < a.size(); i++) {
            CoordTriplet cp = a.get(i);
            
            addForIntact(a, cp, 1, 0, 0);
            addForIntact(a, cp, -1, 0, 0);
            addForIntact(a, cp, 0, 1, 0);
            addForIntact(a, cp, 0, -1, 0);
            addForIntact(a, cp, 0, 0, 1);
            addForIntact(a, cp, 0, 0, -1);
                
        }       
        return size() == a.size();
        
    }
    void addForIntact(ArrayList<CoordTriplet> a, CoordTriplet cp, int dx, int dy, int dz) {
        int x = cp.getX() + dx, y = cp.getY() + dy, z = cp.getZ() + dz;
        CoordTriplet ncp = new CoordTriplet(x, y, z);
        if (contains(x, y, z) && !a.contains(ncp)) {
            a.add(ncp);
            
        }
        
           
    }
    public boolean fixedEncloses(Polycube in) {
        CoordTriplet cp = findEnclosurePosition(in);
        return (cp != null);
    }
    public CoordTriplet findEnclosurePosition(Polycube in) {
        int thisWidth = getWidth(), thisHeight = getHeight(), otherWidth = in.getWidth(), otherHeight = in.getHeight(), thisDepth = getDepth(), otherDepth = in.getDepth();
        for (int x = 0; x <= thisWidth - otherWidth; x++)
            for (int y = 0; y <= thisHeight - otherHeight; y++) {
                for (int z = 0; z <= thisDepth - otherDepth; z++) {
                    if (this.fixedEncloses(in, x, y, z))
                        return new CoordTriplet(x, y, z);
                }
            }
        return null;
    }
    public boolean fixedEncloses(Polycube in, int x, int y, int z) {
        for (CoordTriplet cp : in.array) {
            if (!this.contains(cp.getX() + x, cp.getY() + y, cp.getZ() + z))
                return false;
        }
        return true;
    }
    public Polycube remove(Polycube in, int x, int y, int z) {
        Polycube np = (Polycube)(this.maker());
        for (CoordTriplet cp : array) {
            if (!in.contains(cp.getX() - x, cp.getY() - y, cp.getZ() - z)) {
                np.insert(cp);
            }
        }
        np.seal();
        return np;
    }    
}
