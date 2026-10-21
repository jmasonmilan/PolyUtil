 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;

import java.awt.Color;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import polyutilgh.CoordTriplet;
import polyutilgh.GraphicsParameters;
import polyutilgh.Mu;
import polyutilgh.*;

/**
 *
 * @author mason
 * x even, y even, z odd - flat, z = 0
 * x odd, y even, z even - vertical, x = 0
 * x even, y odd, z even - vertical, y = 0
 */
public class Polyominoid extends BasePolycube {
    int error;
    public Polyominoid() {
        super();
    }
    public Polyominoid(BigInteger inValue) {
        super(inValue);  
    } 
    /*
    x1.y1.z1+x2.y2.z2+...
    */
    public Polyominoid(String coords) {
        super();
        String ca[] = coords.split("\\+");
        for (String c : ca) {
            String va[] = c.split("\\.");
            if (va.length != 3) {
                System.err.println("bad coordinate");
                System.exit(1);
            
            }
            int x = Integer.parseInt(va[0]);
            int y = Integer.parseInt(va[1]);
            int z = Integer.parseInt(va[2]);
            if (even(x) && even(y) && even(z)) {
                System.err.println("bad coordinate");
                System.exit(1);
            }
            if ((even(x) != even(y)) && !even(z)) {
                System.err.println("bad coordinate");
                System.exit(1);
            }
            insert(new CoordTriplet(x, y, z));
        }
        seal();
    }
    public void seal() {
        super.seal();
        check();
    }
    boolean even(int x) {
        return (x % 2) == 0;
    }
    boolean odd(int x) {
        return (x % 2) != 0;
    }
    public Polyominoid maker(String srep) {
        return new Polyominoid(new BigInteger(srep));
    }      
    public Polyominoid maker() {
        return new Polyominoid();
    }  
    public Polyominoid maker(BigInteger rep) {
        return new Polyominoid(rep);
    }     
    public void check() {
        for (CoordTriplet ct : array) {
            ct.check();
        }
    }
    protected void calcMax() {
        for (int i = 0 ; i < array.size(); i++) {
            if (array.get(i).getX() > maxX)
                maxX = array.get(i).getX();
            if (array.get(i).getY() > maxY)
                maxY = array.get(i).getY();       
            if (array.get(i).getZ() > maxZ)
                maxZ = array.get(i).getZ();             
        }
        if (maxX % 2 == 0)
            maxX++;
        if (maxY % 2 == 0)
            maxY++;
        if (maxZ % 2 == 0)
            maxZ++;
        
    }
    public void setError(int n) {
        error = n;
    }
    
    // under test
    public PolySet getFixed() {
        
        PolySet ps = new PolySet();
        
        getFixedMirror(ps);
       
        return ps;
    }   
    private void getFixedMirror(PolySet ps) {
        Polyominoid p;
        
        getFixedRotate(ps);
        p = (Polyominoid)(this.mirrorxy());
        p.getFixedRotate(ps);
        p = (Polyominoid)(p.mirrorxz());
        p.getFixedRotate(ps);
        p = (Polyominoid)(p.mirrorxy());
        p.getFixedRotate(ps);
        p = (Polyominoid)(p.mirrorxz());
        p.getFixedRotate(ps);
        p = (Polyominoid)(p.mirrorxy());
        p.getFixedRotate(ps);
        
    }  
    
    void getFixedRotate(PolySet ps) {
        ps.adds(this.getRep().toString());
        Polyominoid p  = (Polyominoid)(rotxy());
        ps.adds(p.getRep().toString());
        p = (Polyominoid)(p.rotxy());
        ps.adds(p.getRep().toString());
        p = (Polyominoid)(p.rotxy());
        ps.adds(p.getRep().toString());
        p = (Polyominoid)(p.mirrorz());
        ps.adds(p.getRep().toString());
        p = (Polyominoid)(p.rotxy());
        ps.adds(p.getRep().toString());
        p = (Polyominoid)(p.rotxy());
        ps.adds(p.getRep().toString());
        p = (Polyominoid)(p.rotxy());
        ps.adds(p.getRep().toString());

       
    }   
    public boolean noz() {
        for (CoordTriplet ct : array) {
            if (ct.getZ() % 2 != 0)
                return false;
        }
        return true;
    }
    public boolean noy() {
        for (CoordTriplet ct : array) {
            if (ct.getY() % 2 != 0)
                return false;
        }
        return true;
    }
    
    public Polyominoid addHinge(CoordTriplet other) {
        Polyominoid np = new Polyominoid();
        for (CoordTriplet cp : array)
            np.insert(cp);
        np.insert(other);
        np.seal();
        return np;
    }
    public Polyominoid addHinge(CoordTriplet other, CoordTriplet delta) {
        Polyominoid np = new Polyominoid();
        for (CoordTriplet cp : array)
            np.insert(cp);
        np.insert(other.add(delta));
        np.seal();
        return np;
    }
    public ArrayList<CoordTriplet> getArray() {
        return array;
    }
    public Polyedge getXorientedPolyedge() {
        int x = 0;
        Polyedge ret = new Polyedge();
        for (CoordTriplet ct : array) {
            CoordPair cp = new CoordPair(ct.getY(), ct.getZ());
            if (ct.isPolyominoidHinge()) {
                if (x == 0)
                    x = ct.getX();
                else if (x != ct.getX()) {
                    int qq = 1 / 0;
                }
                
                cp.setFlag();
            } else {
                
                if (ct.getFlag())
                    cp.setFlag();
            }
            ret.insert(cp);
        }
        ret.seal();
        return ret;
    }
    // implemented for Width 1 polyominoids parallel to X=0
    public int stripness() {
        /*
        int ret = 0;
        for (CoordTriplet ct : array) {
            ArrayList<CoordTriplet> elementSet = this.getAdjacentIn(ct);
            if (elementSet.size() == 2) {
                if (!areAdjacent(elementSet.get(0), elementSet.get(1)))
                    ret++;
            }
        }
        return ret;
            */
        ArrayList<List> listOfLists = getListOfLists();
        StripSet ss = findAllStrips(listOfLists);
        int ret = 0;
        for (Strip s : ss.strips)
            ret += s.score;
        return ret;
        /*
        if (s == null)
            return 0;
        s.conclude();
        CoordTriplet n3 = null;
        if (s.freeEnd || s.freeStart)
            n3 = s.findNearest3Node(listOfLists);
        return s.score(n3);
        */
    }
    
    ArrayList<List>  getListOfLists() {
        ArrayList<List> ret = new ArrayList<>();
    
        for (CoordTriplet ct : array) {
            ret.add(new List(ct));
        }
        return ret;
    }
    class List {
        CoordTriplet ct;
        ArrayList<CoordTriplet> list;
        List(CoordTriplet ct) {
            this.ct = ct;
            list = getAdjacent(ct);
        }
    }
    StripSet findAllStrips(ArrayList<List> listOfLists) {
       
        StripSet ss = new StripSet();
        for (List list : listOfLists) {
            if (ss.contains(list.ct))
                continue;
            
            if (list.list.size() != 2) {
                if (list.list.size() < 3)
                    continue;
                CoordTriplet adj = findAdjacentSingleton(list.ct);
                if (adj == null)
                    continue;
                Strip s = new Strip(list.ct);
                ss.strips.add(s);
                
                s.propagate(adj, false);
                s.conclude();
                CoordTriplet n3 = null;
                //if (s.freeEnd || s.freeStart)
                    n3 = s.findNearest3Node(listOfLists);
                s.score(n3);       
                continue;
            }
                
            if (areAdjacent(list.list.get(0), list.list.get(1)))
                continue;
            Strip s = new Strip(list.ct);
            ss.strips.add(s);
            s.propagate(list.list.get(0), true);
            s.propagate(list.list.get(1), false);
            s.conclude();
            CoordTriplet n3 = null;
            if (s.freeEnd || s.freeStart)
                n3 = s.findFurthest3Node(listOfLists);
            s.score(n3);            
        }
        /*
        Strip ret = ss.strips.get(0);
        for (int i = 1 ; i < ss.strips.size(); i++)
            if (ss.strips.get(i).elementSet.size() > ret.elementSet.size())
                ret = ss.strips.get(i);
        */
        return ss;
    }
    CoordTriplet findAdjacentSingleton(CoordTriplet ct) {
        ArrayList<CoordTriplet> list = getAdjacent(ct);
        for (CoordTriplet possible : list) {
            if (!adjInList(ct, list))
                return possible;
        }
        return null;
    }
    boolean adjInList(CoordTriplet ct, ArrayList<CoordTriplet> list) {
        for (CoordTriplet other : list) {
            if (ct.equals(other))
                continue;
            if (areAdjacent(ct, other))
                return true;
        }
        return false;
    }
    class Strip {
        boolean freeStart, freeEnd;
        int score;
        ArrayList<CoordTriplet> elementSet;
        Strip(CoordTriplet ct) {
            elementSet = new ArrayList<>();
            elementSet.add(ct);
        }
        CoordTriplet findNearest3Node(ArrayList<List> listOfLists) {
            CoordTriplet ret = null;
            int dist = 9999;
            for (List list : listOfLists) {
                if (elementSet.contains(list.ct))
                    continue;
                if (list.list.size() < 2)
                    continue;
                if (list.list.size() > 2 || areAdjacent(list.list.get(0), list.list.get(1))) {
                    if (distance(this.freeNode(), list.ct) < dist) {
                        dist = distance(this.freeNode(), list.ct);
                        ret = list.ct;
                    }
                }
            }
            return ret;
        }
        CoordTriplet findFurthest3Node(ArrayList<List> listOfLists) {
            CoordTriplet ret = null;
            int dist = 0;
            for (List list : listOfLists) {
                if (elementSet.contains(list.ct))
                    continue;
                if (list.list.size() < 2)
                    continue;
                if (list.list.size() > 2 || areAdjacent(list.list.get(0), list.list.get(1))) {
                    if (distance(this.freeNode(), list.ct) > dist) {
                        dist = distance(this.freeNode(), list.ct);
                        ret = list.ct;
                    }
                }
            }
            return ret;
        }
        void propagate(CoordTriplet ct, boolean insert) {
            
            while(true) {
                boolean loop = false;
                if (insert) {
                    elementSet.add(0,ct);
                } else {
                    elementSet.add(ct);
                }
                ArrayList<CoordTriplet> list = getAdjacent(ct);
                if (list.size() > 2)
                    return;
                if (list.size() == 1)
                    return;
                for (int i = 0; i < 2; i++) {
                    CoordTriplet ctNext = list.get(i);
                    if (list.contains(ctNext))
                        continue;

                    ct = ctNext;
                    loop = true;
                    break;
                }
                if (!loop)
                    break;
            }
        }
        void conclude() {
            ArrayList<CoordTriplet> startAdjList = getAdjacent(elementSet.get(0));
            if (startAdjList.size() == 1)
                freeStart = true;
            ArrayList<CoordTriplet> endAdjList = getAdjacent(elementSet.get(elementSet.size() - 1));
            if (endAdjList.size() == 1)
                freeEnd = true;
            
        }
        void score(CoordTriplet n3) {
            int ret = 2 * elementSet.size() * elementSet.size();
            if (freeEnd || freeStart)
                ret--;
            ret *= 100;
            if (n3 != null)
                ret -= distance(n3, freeNode());
            score = ret;
        }
        CoordTriplet freeNode() {
            if (freeStart)
                return elementSet.get(0);
            else
                return elementSet.get(elementSet.size() - 1);
        }
        int distance(CoordTriplet node1, CoordTriplet node2) {
            return Math.abs(node2.getX() - node1.getX()) + Math.abs(node2.getY() - node1.getY()) + Math.abs(node2.getZ() - node1.getZ()) ;
        }
    }
    class StripSet {
        ArrayList<Strip> strips;
        StripSet() {
            strips = new ArrayList<>();
        }
        boolean contains(CoordTriplet ct) {
            for (Strip strip : strips) {
                if (strip.elementSet.contains(ct))
                    return true;
            }
            return false;
        }
    }
    /*
    public Polyominoid reorientate(Polyominoid from, Polyominoid to) {
        Polyominoid np;
        np = try4(from, to);
        if (np != null)
            return np;
        np = ((Polyominoid)(this.mirrorxz())).try4((Polyominoid)(from.mirrorxz()), to);
        if (np != null)
            return np;
        np = ((Polyominoid)(this.rotxy().mirrorxz())).try4((Polyominoid)(from.rotxy().mirrorxz()), to);
        if (np != null)
            return np;
        int qq = 1 / 0;
        return null;
    }
    Polyominoid try4(Polyominoid from, Polyominoid to) {
        if (from.equals(to))
            return this;
        Polyominoid p = (Polyominoid)(this.rotxy());
        from = (Polyominoid)(from.rotxy());
        if (from.equals(to))
            return this;
        p = (Polyominoid)(p.rotxy());
        from = (Polyominoid)(from.rotxy());
        if (from.equals(to))
            return this;
        p = (Polyominoid)(p.rotxy());
        from = (Polyominoid)(from.rotxy());
        if (from.equals(to))
            return this;
        return null;
    }
    */
    /*
    make a elementSet of polyominoids like this one such every element is horizontal precisely 4 times, one for each orientation
    */
    public ArrayList<Polyominoid> get12List() {
        ArrayList<Polyominoid> ret = new ArrayList<>();
        add4(ret, this);
        add4(ret, (Polyominoid)(this.mirrorxz()));
        add4(ret, (Polyominoid)(this.rotxy().mirrorxz()));
        
        return ret;
    }
  
    void add4(ArrayList<Polyominoid> list, Polyominoid p) {
        list.add(p);
        p = (Polyominoid)(p.rotxy());
        list.add(p);
        p = (Polyominoid)(p.rotxy());
        list.add(p);
        p = (Polyominoid)(p.rotxy());
        list.add(p);
        
    }
    public Polyominoid removeHinge() {
        Polyominoid np = new Polyominoid();
        for (CoordTriplet ct : array)
            if (!ct.isPolyominoidHinge())
                np.insert(ct);
        np.seal();
        return np;
    }
    public PolySet genNew(int limit) {
        PolySet ps = new PolySet();
        if (this.array.size() == 0) {
            ps.adds("32");
            return ps;
        }
        for (int i = 0 ; i < this.array.size() ; i++) {
            CoordTriplet cp = this.array.get(i);
           
            tryOne(cp, ps, 2, 0, 0);
            tryOne(cp, ps, -2, 0, 0);
            tryOne(cp, ps, 0, 2, 0);
            tryOne(cp, ps, 0, -2, 0);

            tryOne(cp, ps, 1, 0, 1);
            tryOne(cp, ps, 1, 0, -1);
            tryOne(cp, ps, -1, 0, 1);
            tryOne(cp, ps, -1, 0, -1);
            tryOne(cp, ps, 0, 1, 1);
            tryOne(cp, ps, 0, 1, -1);
            tryOne(cp, ps, 0, -1, 1);
            tryOne(cp, ps, 0, -1, -1);                
           
        }
        return ps;
    } 
    void tryOne(CoordTriplet cp, PolySet ps, int d1, int d2, int d3) {
        int dx, dy, dz;
        if (cp.isHoriz()) {
            dx = d1;
            dy = d2;
            dz = d3;
        } else if (cp.isVertParaYE0()) {
            dx = d3;
            dy = d1;
            dz = d2;            
        } else {
            dx = d1;
            dy = d3;
            dz = d2;            
        }
        int x = cp.getX() + dx, y = cp.getY() + dy, z = cp.getZ() + dz;
        CoordTriplet ncp = new CoordTriplet(x, y, z); // dbg
        if (ncp.isHoriz()) ; // dbg
        Polyominoid p = addOneCoords(x, y, z );
        if (p != null) 
            ps.addUniques(p);
    }  
    Polyominoid addOneCoords(int x, int y, int z) {
        int dx = 0, dy = 0, dz = 0;
        Polyominoid p1 = this;
        if (x < 1) {
            p1 = (Polyominoid)(p1.shift(2, 0, 0));
            dx = 2;
        } 
        if (y < 1) {
            p1 = (Polyominoid)(p1.shift(0, 2, 0));
            dy = 2;
        } 
        if (z < 1) {
            p1 = (Polyominoid)(p1.shift(0, 0, 2));
            dz = 2;
        } 
        CoordTriplet cp = new CoordTriplet(x + dx, y + dy, z + dz);

        if (p1.canAdd(cp))
            return new Polyominoid(p1.getRep().setBit(getBit(x + dx, y + dy, z + dz)));
        else 
            return null;
        
    }      
    boolean areAdjacent(CoordTriplet ct1, CoordTriplet ct2) {
        if (areAdjacentInPlane(ct1, ct2))
            return true;
        if (areAdjacentOrthogonal(ct1, ct2))
            return true;
        return false;
    }
    boolean areAdjacentOrthogonal(CoordTriplet ct1, CoordTriplet ct2) {
        return areAdjacentOrthogonal(ct1, ct2, 0, 1, 2) || areAdjacentOrthogonal(ct1, ct2, 1, 0, 2) || areAdjacentOrthogonal(ct1, ct2, 2, 0, 1);
    }
    boolean areAdjacentInPlane(CoordTriplet ct1, CoordTriplet ct2) {
        return areAdjacentInPlane(ct1, ct2, 0, 1, 2) || areAdjacentInPlane(ct1, ct2, 1, 0, 2) || areAdjacentInPlane(ct1, ct2, 2, 0, 1);
    }
    boolean areAdjacentInPlane(CoordTriplet ct1, CoordTriplet ct2, int c1, int c2, int c3) {
        if (ct1.getCoord(c2) == ct2.getCoord(c2) && ct1.getCoord(c3) == ct2.getCoord(c3)) {
            return Math.abs(ct1.getCoord(c1) - ct2.getCoord(c1)) == 2;
        } else
            return false;
    }
    boolean areAdjacentOrthogonal(CoordTriplet ct1, CoordTriplet ct2, int c1, int c2, int c3) {
        if (ct1.getCoord(c1) == ct2.getCoord(c1)) {
            return (Math.abs(ct1.getCoord(c1) - ct2.getCoord(c1)) == 1)  && (Math.abs(ct1.getCoord(c3) - ct2.getCoord(c3)) == 1);
        } else
            return false;
    }
    public ArrayList<CoordTriplet> getAdjacent(CoordTriplet cp) {
        ArrayList<CoordTriplet>  ret = new ArrayList<>();
        if (cp.getZ() % 2 != 0) {
            addAdjacentElement(ret, cp, 0, 1, 1);
            addAdjacentElement(ret, cp, 0, 2, 0);
            addAdjacentElement(ret, cp, 0, 1, -1);
            addAdjacentElement(ret, cp, 0, -1, 1);
            addAdjacentElement(ret, cp, 0, -2, 0);
            addAdjacentElement(ret, cp, 0, -1, -1);
            addAdjacentElement(ret, cp, 1, 0, 1);
            addAdjacentElement(ret, cp, 2, 0, 0);
            addAdjacentElement(ret, cp, 1, 0, -1);
            addAdjacentElement(ret, cp, -1, 0, 1);
            addAdjacentElement(ret, cp, -2, 0, 0);
            addAdjacentElement(ret, cp, -1, 0, -1);            
        } else if (cp.getX() % 2 != 0) {
            addAdjacentElement(ret, cp, 1, 0, -1);
            addAdjacentElement(ret, cp, -1, 0, -1);
            addAdjacentElement(ret, cp, 0, 0, -2);
            addAdjacentElement(ret, cp, 1, 0, 1);
            addAdjacentElement(ret, cp, -1, 0, 1);
            addAdjacentElement(ret, cp, 0, 0, 2);
            addAdjacentElement(ret, cp, 1, 1, 0);
            addAdjacentElement(ret, cp, -1, 1, 0);
            addAdjacentElement(ret, cp, 0, 2, 0);
            addAdjacentElement(ret, cp, 1, -1, 0);
            addAdjacentElement(ret, cp, -1, -1, 0);
            addAdjacentElement(ret, cp, 0, -2, 0);       
        } else  { 
            addAdjacentElement(ret, cp, 0, 1, -1);
            addAdjacentElement(ret, cp, 0, -1, -1);
            addAdjacentElement(ret, cp, 0, 0, -2);
            addAdjacentElement(ret, cp, 0, 1, 1);
            addAdjacentElement(ret, cp, 0, -1, 1);
            addAdjacentElement(ret, cp, 0, 0, 2);
            addAdjacentElement(ret, cp, -1, -1, 0);
            addAdjacentElement(ret, cp, -1, 1, 0);
            addAdjacentElement(ret, cp, -2, 0, 0);
            addAdjacentElement(ret, cp, 1, -1, 0);
            addAdjacentElement(ret, cp, 1, 1, 0);
            addAdjacentElement(ret, cp, 2, 0, 0);            
        }
        return ret;
    }
    public void addAdjacentElement( ArrayList<CoordTriplet> list, CoordTriplet cp, int dx, int dy, int dz) {
        CoordTriplet ncp = new CoordTriplet(cp.getX() + dx, cp.getY() + dy, cp.getZ() + dz);
        if (contains(ncp) && !list.contains(ncp))
            list.add(ncp);
    }    
    public boolean sameDirectionAdjacencies() {
        for (CoordTriplet ct : array) 
            if (sameDirectionAdjacencies(ct) == false)
                return false;
        return true;
        
    }
    boolean sameDirectionAdjacencies(CoordTriplet ct) {
        int ret = 0;
        if (ct.getZ() % 2 != 0) {
            ret |= testAdjacentElement(1, ct, 0, 1, 1);
            ret |= testAdjacentElement(1, ct, 0, 2, 0);
            ret |= testAdjacentElement(1, ct, 0, 1, -1);
            ret |= testAdjacentElement(1, ct, 0, -1, 1);
            ret |= testAdjacentElement(1, ct, 0, -2, 0);
            ret |= testAdjacentElement(1, ct, 0, -1, -1);
            ret |= testAdjacentElement(2, ct, 1, 0, 1);
            ret |= testAdjacentElement(2, ct, 2, 0, 0);
            ret |= testAdjacentElement(2, ct, 1, 0, -1);
            ret |= testAdjacentElement(2, ct, -1, 0, 1);
            ret |= testAdjacentElement(2, ct, -2, 0, 0);
            ret |= testAdjacentElement(2, ct, -1, 0, -1);            
        } else if (ct.getX() % 2 != 0) {
            ret |= testAdjacentElement(1, ct, 1, 0, -1);
            ret |= testAdjacentElement(1, ct, -1, 0, -1);
            ret |= testAdjacentElement(1, ct, 0, 0, -2);
            ret |= testAdjacentElement(1, ct, 1, 0, 1);
            ret |= testAdjacentElement(1, ct, -1, 0, 1);
            ret |= testAdjacentElement(1, ct, 0, 0, 2);
            ret |= testAdjacentElement(2, ct, 1, 1, 0);
            ret |= testAdjacentElement(2, ct, -1, 1, 0);
            ret |= testAdjacentElement(2, ct, 0, 2, 0);
            ret |= testAdjacentElement(2, ct, 1, -1, 0);
            ret |= testAdjacentElement(2, ct, -1, -1, 0);
            ret |= testAdjacentElement(2, ct, 0, -2, 0);       
        } else  { 
            ret |= testAdjacentElement(1, ct, 0, 1, -1);
            ret |= testAdjacentElement(1, ct, 0, -1, -1);
            ret |= testAdjacentElement(1, ct, 0, 0, -2);
            ret |= testAdjacentElement(1, ct, 0, 1, 1);
            ret |= testAdjacentElement(1, ct, 0, -1, 1);
            ret |= testAdjacentElement(1, ct, 0, 0, 2);
            ret |= testAdjacentElement(2, ct, -1, -1, 0);
            ret |= testAdjacentElement(2, ct, -1, 1, 0);
            ret |= testAdjacentElement(2, ct, -2, 0, 0);
            ret |= testAdjacentElement(2, ct, 1, -1, 0);
            ret |= testAdjacentElement(2, ct, 1, 1, 0);
            ret |= testAdjacentElement(2, ct, 2, 0, 0);            
        }        
        boolean retb = (ret == 0) || (ret == 1) || (ret == 2) ;
        return retb;
    }
    public int testAdjacentElement( int ret, CoordTriplet cp, int dx, int dy, int dz) {
        CoordTriplet ncp = new CoordTriplet(cp.getX() + dx, cp.getY() + dy, cp.getZ() + dz);
        if (contains(ncp) )
            return ret;
        return 0;
    }      
    public int maxAdjacencies() {
        int ret = 0;
        for (CoordTriplet ct : array) {
            ArrayList<CoordTriplet> list = this.getAdjacent(ct);
            if (list.size() > ret)
                ret = list.size();
        }
        return ret;
    }
    public boolean intact() {
        ArrayList<CoordTriplet> build = new ArrayList<>();
        build.add(this.getCoords(0));
        for (int i = 0; i < build.size(); i++) {
            ArrayList<CoordTriplet>  list = getAdjacent( build.get(i));
            for (CoordTriplet ct : list)
                if (!build.contains(ct))
                    build.add(ct);
        }
        boolean ret = (build.size() == this.size());
        return ret;
    }    

    /*
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
    */
    public  Color getGraphicsColour(GraphicsParameters gp, CoordTriplet cp) {
        return gp.green;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordTriplet cp, boolean applyCB) {
        if (applyCB) {
            if (cp.parity() == fcp.parity())
                return gp.green;
            else
                return gp.yellow;
        }
        return gp.green;
    }    
    private ArrayList<GraphicsCoords> gArray;
    
    public void displayOnJpeg(GraphicsParameters gp) {
        prepare(gp);
        /*
        int maxSum = 3;
        for (CoordTriplet cp : array) {
            int sum = cp.getX() + cp.getY() + cp.getZ();
            if (sum > maxSum)
                maxSum = sum;
        } */
        /*
        for (int s = 3; s <= maxSum ; s++)
            for (int x = 1; x <= maxX ; x++)
                for (int i = 0; i < array.size(); i++) {
                    CoordTriplet cp = array.get(i);
                    int sum = cp.getX() + cp.getY() + cp.getZ();
                    if (sum != s || cp.getX() != x)
                        continue;
                    displayOnJpeg(gp, cp, gArray.get(i));
                }
        */
        for (int z = 1; z <= maxZ ; z++)
            for (int y = maxY; y >= 1 ; y--)
                for (int x = 1; x <= maxX ; x++) {
                    for (int i = 0; i < array.size(); i++) {
                        CoordTriplet cp = array.get(i);

                        if (cp.getX() != x || cp.getY() != y || cp.getZ() != z)
                            continue;
                        displayOnJpeg(gp, cp, gArray.get(i));
                    }                    
                }
    }
    void displayOnJpeg(GraphicsParameters gp, CoordTriplet cp, GraphicsCoords gc) {
        if (cp.marked())
            return;
        drawPolygon(gp, gc.a, gc.b, gc.c, gc.d, gc.colour, cp);
        
        
    }
    void check(int[] xa, int w) {
        for (int x : xa) {
            if (x > w) {
                int qq = 1 / 0;
            }
        }
    }
    void drawPolygon(GraphicsParameters gp, GraphicsCoordPair p1, GraphicsCoordPair p2, 
            GraphicsCoordPair p3, GraphicsCoordPair p4, Color c, CoordTriplet cp) {
        double posX = gp.px * gp.colWidth - left + gp.horizMargin + 60; 
        double posY = gp.vertPixelPosition + high;
        int xa[] = {(int)(p1.x + posX), (int)(p2.x + posX), (int)(p3.x + posX), (int)(p4.x + posX) };
        int ya[] = {(int)(posY - p1.y), (int)(posY - p2.y), (int)(posY - p3.y), (int)(posY - p4.y) };
        
        check(xa, gp.imageWidth);
        
        gp.g.setColor(c);
        if (gp.filling)
            gp.g.fillPolygon(xa, ya, 4);
        gp.g.setColor( Color.BLACK ); 
        if (gp.caption)
            gp.g.drawString(cp.getX() + "," + cp.getY() + "," + cp.getZ(), (xa[0] + xa[2])/2, (ya[0] + ya[2])/2);
        if (error != 0)
            gp.g.drawString("error = " + error + "", (xa[0] + xa[2])/2, (ya[0] + ya[2])/2);
        gp.g.drawPolygon(xa, ya, 4);   
        if (cp.getFlag()) {
            gp.g.drawLine(xa[0], ya[0], xa[2], ya[2]);
            gp.g.drawLine(xa[1], ya[1], xa[3], ya[3]);
            
        }
        
    }
     void prepare(GraphicsParameters gp) {
        horizLongBit = (gp.edgeLength * 89) / 90;
        horizShortBit = (gp.edgeLength * 67) / 90;
        vertLongBit = (gp.edgeLength * 66) / 90;
        vertShortBit = (gp.edgeLength * 44) / 90;
        gArray = new ArrayList<>();
        for (CoordTriplet cp : array)
            calcCoords(gp,  cp);
        
        high = gArray.get(0).a.y;
        low = gArray.get(0).d.y;
        left = gArray.get(0).a.x;
        right = gArray.get(0).b.x;
        
        for (GraphicsCoords gc : gArray) {
            /*
            if (gc.a.y > high) 
                high = gc.a.y;
            if (gc.c.y < low) 
                low = gc.c.y;
            if (gc.a.y < low) 
                low = gc.a.y;
            if (gc.c.y > high) 
                high = gc.c.y;
            if (gc.b.x > right) 
                right = gc.b.x;
            if (gc.d.x < left) 
                left = gc.d.x;
            */
            if (gc.getMaxY() > high)
                high = gc.getMaxY();
            if (gc.getMinY() < low)
                low = gc.getMinY();
            if (gc.getMaxX() > right)
                right = gc.getMaxX();
            if (gc.getMinX() < left)
                left = gc.getMinX();
            
        }
        high += 30;
    }

    void calcCoords(GraphicsParameters gp, CoordTriplet cp) {
        double delta = 0;
        if (gp.gap)
            delta = 5;
        GraphicsCoords gc = new GraphicsCoords();
        gArray.add(gc);
        
        GraphicsCoordPair q = new GraphicsCoordPair();
        q.y = (cp.getZ() - 1) * gp.edgeLength;
        q.y -= (cp.getX() - 1) * vertLongBit;
        q.y += (cp.getY() - 1) * vertShortBit;
        q.x = 0 ;
        q.x += (cp.getX() - 1) * horizShortBit;
        q.x += (cp.getY() - 1) * horizLongBit;
        if (cp.isPolyominoidHingeParallelToX()) {
            q.y = (cp.getZ() - 1) * gp.edgeLength;
            q.y -= (cp.getX() - 1) * vertLongBit;
            q.y += (cp.getY() - 2) * vertShortBit;
            q.x = 0 ;
            q.x += (cp.getX() - 1) * horizShortBit;
            q.x += (cp.getY() - 2) * horizLongBit;       
            
            gc.colour = gp.red;
            gc.a.x = q.x;
            gc.a.y = q.y - gp.edgeLength;

            gc.b.x = gc.a.x + 2 * horizShortBit;
            gc.b.y = gc.a.y - 2 * vertLongBit;

            gc.c.x = gc.b.x - 2 * horizLongBit;
            gc.c.y = gc.b.y - 2 * vertShortBit;

            gc.d.x = gc.c.x - 2 * horizShortBit;
            gc.d.y = gc.c.y + 2 * vertLongBit;         
            
            gc.a.x += 2;
            gc.a.y -= 3;
            gc.b.x += 2;
            gc.b.y -= 3;
            gc.c.x = gc.b.x - 5;
            gc.c.y = gc.b.y - 5;
            gc.d.x = gc.a.x - 5;
            gc.d.y = gc.a.y - 5;
            
        } else if (cp.isPolyominoidHingeParallelToY()) {
           // int qq = 1 / 0;
        } else if (cp.isPolyominoidHingeParallelToZ()) {
          //  int qq = 1 / 0;
        } else {
            if (cp.isHoriz()) {
                gc.colour = gp.green;
                gc.a.x = q.x;
                gc.a.y = q.y - gp.edgeLength;

                gc.b.x = gc.a.x + 2 * horizShortBit;
                gc.b.y = gc.a.y - 2 * vertLongBit;

                gc.c.x = gc.b.x - 2 * horizLongBit;
                gc.c.y = gc.b.y - 2 * vertShortBit;

                gc.d.x = gc.c.x - 2 * horizShortBit;
                gc.d.y = gc.c.y + 2 * vertLongBit;

            }
            else if (cp.isVertParaXE0()) {
                gc.colour = gp.blue;
                gc.a.x = q.x - horizLongBit;
                gc.a.y = q.y - vertShortBit;

                gc.b.x = gc.a.x + 2 * horizShortBit;
                gc.b.y = gc.a.y - 2 * vertLongBit;

                gc.c.x = gc.b.x ;
                gc.c.y = gc.b.y - 2 * gp.edgeLength;

                gc.d.x = gc.a.x ;
                gc.d.y = gc.a.y - 2 * gp.edgeLength;
                
            } else if (cp.isVertParaYE0()) {
                gc.colour = gp.yellow;
                gc.a.x = q.x + horizShortBit;
                gc.a.y = q.y - vertLongBit;

                gc.b.x = gc.a.x ;
                gc.b.y = gc.a.y - 2 * gp.edgeLength;

                gc.c.x = gc.b.x - 2 * horizLongBit;
                gc.c.y = gc.b.y - 2 * vertShortBit;

                gc.d.x = gc.c.x ;
                gc.d.y = gc.c.y + 2 * gp.edgeLength;
            }
            if (gp.gap) {
                double dax = delta * ((2 * gc.a.x) - (gc.d.x + gc.b.x)) / 100D;
                double day = delta * ((2 * gc.a.y) - (gc.d.y + gc.b.y)) / 100D;
                double dbx = delta * ((2 * gc.b.x) - (gc.c.x + gc.a.x)) / 100D;
                double dby = delta * ((2 * gc.b.y) - (gc.c.y + gc.a.y)) / 100D;
                double dcx = delta * ((2 * gc.c.x) - (gc.d.x + gc.b.x)) / 100D;
                double dcy = delta * ((2 * gc.c.y) - (gc.d.y + gc.b.y)) / 100D;
                double ddx = delta * ((2 * gc.d.x) - (gc.a.x + gc.c.x)) / 100D;
                double ddy = delta * ((2 * gc.d.y) - (gc.a.y + gc.c.y)) / 100D;
                gc.a.x -= dax;
                gc.a.y -= day;
                gc.b.x -= dbx;
                gc.b.y -= dby;
                gc.c.x -= dcx;
                gc.c.y -= dcy;
                gc.d.x -= ddx;
                gc.d.y -= ddy;

            }
        }
              
    }
    public  int getPixelHeight(GraphicsParameters gp) { 
        prepare(gp);
        int hh = (int)(high - low);

        return hh + gp.edgeLength * 2 + 100 ; 
    }
    public int getPixelWidth(GraphicsParameters gp) { 
        prepare(gp);
        int width = (int)(right - left); 
        width += gp.edgeLength * 2 + 80;
        return width;
    }    
    class GraphicsCoords {
        GraphicsCoordPair a, b, c, d;
        Color colour;
        GraphicsCoords() {
            a = new GraphicsCoordPair();
            b = new GraphicsCoordPair();
            c = new GraphicsCoordPair();
            d = new GraphicsCoordPair();
           
            
        }
        double getMinX() {
            double ret = a.x;
            if (b.x < ret)
                ret = b.x;
            if (c.x < ret)
                ret = c.x;
            if (d.x < ret)
                ret = d.x;
            return ret;
        }
        double getMinY() {
            double ret = a.y;
            if (b.y < ret)
                ret = b.y;
            if (c.y < ret)
                ret = c.y;
            if (d.y < ret)
                ret = d.y;
            return ret;
        }
        double getMaxX() {
            double ret = a.x;
            if (b.x > ret)
                ret = b.x;
            if (c.x > ret)
                ret = c.x;
            if (d.x > ret)
                ret = d.x;
            return ret;
        }
        double getMaxY() {
            double ret = a.y;
            if (b.y > ret)
                ret = b.y;
            if (c.y > ret)
                ret = c.y;
            if (d.y > ret)
                ret = d.y;
            return ret;
        }
        
    }
    private class GraphicsCoordPair {
        double x, y;
    }   
    /*
    mirror symmetry in x = y
   
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
    }   */
    public FlatteningResult flatten() {
        if (threeFacesMeetAtEdge())
            return new FlatteningResult(FlatteningResult.THRREFACESMEETATEDGE);
        if (threeFacesMeetAtCorner())
            return new FlatteningResult(FlatteningResult.THREEFACESMEETATCORNER);
        if (containsLoop())
            return new FlatteningResult(FlatteningResult.CONTAINSLOOP);
        return attemptFlatten(); 
    }
    /*
 * x even, y even, z odd - flat, z=0
 * x odd, y even, z even - vertical, x = 0
 * x even, y odd, z even - vertical, y = 0
    
    So edges are
    eoo vary y and z
    oeo vary x and z
    ooe vary x and y
    */
    boolean threeFacesMeetAtEdge() {
        for (int x = 1; x <= this.maxX; x++) {
            for (int y = 1; y <= this.maxY; y++) {
                for (int z = 1; z <= this.maxZ; z++) {
                    int a = 0;
                    if (even(x) && odd(y) && odd(z)) {
                        a = oneIfContainsDelta(x, y, z, 0, 1, 0) +
                                oneIfContainsDelta(x, y, z, 0, -1, 0) +
                                oneIfContainsDelta(x, y, z, 0, 0, 1) +
                                oneIfContainsDelta(x, y, z, 0, 0, -1);                                
                    } else if (odd(x) && even(y) && odd(z)) {
                        a = oneIfContainsDelta(x, y, z, 1, 0, 0) +
                                oneIfContainsDelta(x, y, z, -1, 0, 0) +
                                oneIfContainsDelta(x, y, z, 0, 0, 1) +
                                oneIfContainsDelta(x, y, z, 0, 0, -1);                        
                    } else if (odd(x) && odd(y) && even(z)) {
                        a = oneIfContainsDelta(x, y, z, 0, 1, 0) +
                                oneIfContainsDelta(x, y, z, 0, -1, 0) +
                                oneIfContainsDelta(x, y, z, 1, 0, 0) +
                                oneIfContainsDelta(x, y, z, -1, 0, 0);                        
                    } 
                    if (a > 2)
                        return false;
                }
            }
                
        }
        return false;
    }
    /*
 * x even, y even, z odd - flat, z=0
 * x odd, y even, z even - vertical, x = 0
 * x even, y odd, z even - vertical, y = 0
    
    So corners are
    ooo
    */
    boolean threeFacesMeetAtCorner() {
        for (int x = 1; x <= this.maxX; x += 2) {
            for (int y = 1; y <= this.maxY; y += 2) {
                for (int z = 1; z <= this.maxZ; z += 2) {
                    int a = 0;
                    a = oneIfContainsDelta(x, y, z, 1, 1, 0) +
                           oneIfContainsDelta(x, y, z, 1, -1, 0) +
                           oneIfContainsDelta(x, y, z, -1, 1, 0) +
                           oneIfContainsDelta(x, y, z, 1, -1, 0) +
                           oneIfContainsDelta(x, y, z, 1, 0, 1) +
                           oneIfContainsDelta(x, y, z, 1, 0, -1) +
                           oneIfContainsDelta(x, y, z, -1, 0, 1) +
                           oneIfContainsDelta(x, y, z, -1, 0, -1) +
                           oneIfContainsDelta(x, y, z, 0, 1, 1) +
                           oneIfContainsDelta(x, y, z, 0, 1, -1) +
                           oneIfContainsDelta(x, y, z, 0, -1, 1) +
                           oneIfContainsDelta(x, y, z, 0, -1, -1);
                            
                    if (a > 2)
                        return false;
                }
            }
                
        }
        return false;
    }
    boolean containsLoop() {
        for (CoordTriplet ct : array) {
            ArrayList<CoordTriplet> route = new ArrayList<>();
            route.add(ct);
            ArrayList<CoordTriplet> adj = this.getAdjacent(ct);
            for (CoordTriplet act : adj)
                if (containsLoop(ct, act, route, 
                        false, false, false, false, 
                        false, false, false, false, 
                        false, false, false, false)) {
                    return true;
            }
        }
        return false;
    }
    boolean containsLoop(CoordTriplet starter, CoordTriplet current, ArrayList<CoordTriplet> route,
            boolean xnn, boolean xnp, boolean xpn, boolean xpp, 
            boolean ynn, boolean ynp, boolean ypn, boolean ypp, 
            boolean znn, boolean znp, boolean zpn, boolean zpp) {
        if (route.contains(current))
            return false;
        route.add(current);
        int dx = current.getX() - starter.getX();
        int dy = current.getY() - starter.getY();
        int dz = current.getZ() - starter.getZ();
        xnn |= dx == 0 && dy < 0 && dz < 0;
        xnp |= dx == 0 && dy < 0 && dz > 0;
        xpn |= dx == 0 && dy > 0 && dz < 0;
        xpp |= dx == 0 && dy > 0 && dz > 0;
        
        ynn |= dy == 0 && dx < 0 && dz < 0;
        ynp |= dy == 0 && dx < 0 && dz > 0;
        ypn |= dy == 0 && dx > 0 && dz < 0;
        ypp |= dy == 0 && dx > 0 && dz > 0;
        
        znn |= dz == 0 && dx < 0 && dy < 0;
        znp |= dz == 0 && dx < 0 && dy > 0;
        zpn |= dz == 0 && dx > 0 && dy < 0;
        zpp |= dz == 0 && dx > 0 && dy > 0;
        
        if (current.equals(starter)) {
            if (xnn && xnp && xpn && xpp)
                return true;
            if (ynn && ynp && ypn && ypp)
                return true;
            if (znn && znp && zpn && zpp)
                return true;
            return false;
        }
        ArrayList<CoordTriplet> adj = this.getAdjacent(current);
        for (CoordTriplet act : adj)
            if (containsLoop(starter, act, route, 
                    xnn,  xnp,  xpn,  xpp, 
                    ynn,  ynp,  ypn,  ypp, 
                    znn,  znp,  zpn,  zpp)) {
                return true;
        }       
        return false;
    }
    
    int    oneIfContainsDelta(int x, int y, int z, int dx, int dy, int dz) {
        if (contains(x + dx, y + dy, z + dz))
            return 1;
        return 0;
    }
    public boolean isFlat() {
        return isFlat(0) || isFlat(1) || isFlat(2);
    }
    boolean isFlat(int coord) {
        for (int i = 1; i < size() ; i++)
            if (array.get(i).getCoord(coord) != array.get(0).getCoord(coord))
                return false;
        return true;
    }
    int whichFlat() {
        for (int i = 0; i <= 2; i++)
            if (isFlat(i))
                return i;
        return -1;
    }
    FlatteningResult attemptFlatten() {

        Polyominoid current = this;
        while (true) {
            int w = current.whichFlat();
            if (w >= 0)
                return new FlatteningResult(current.convert(w));            
            IntermediateFlatteningResult ifr = current.attemptFlatten1();
            if (ifr.error != 0)
                return new FlatteningResult(ifr.error);
            current = ifr.result;
        }
    }
    IntermediateFlatteningResult attemptFlatten1() {
        IntermediateFlatteningResult attempt = attemptFlatten(0, 1, 2);
        /*
        if (attempt != null)    {
            Polyomino p = attempt.attemptFlatten();
            if (p != null)
                return p;
        }
            
        attempt = attemptFlatten(1, 0, 2);
        if (attempt != null)    {
            Polyomino p = attempt.attemptFlatten();
            if (p != null)
                return p;
        }
        attempt = attemptFlatten(2, 0, 1);
        if (attempt != null)    {
            Polyomino p = attempt.attemptFlatten();
            if (p != null)
                return p;
        }
*/
        return null;
    }
    IntermediateFlatteningResult attemptFlatten(int para, int other1, int other2) {
        /*
        for (CoordTriplet ct : array) {
            if (ct.parallelTo() != para)
                continue;
            ArrayList<CoordTriplet> plate = propagate(ct, para);
            ArrayList<CoordTriplet> adj = getAdjacentIn(plate);
            if (consistent(adj)) {
                Polyominoid result = rotate(plate);
                if (result != null)
                    return result;
                if ()
            }
        } */ return null;
    }
    Polyomino convert(int w) {
        Polyomino p = new Polyomino();
        for (CoordTriplet ct : array)
            p.insert(ct.flatten(w));
        int minX = p.getMinX();
        int minY = p.getMinY();
        if (minX > 1)
            p = p.shiftRight(1 - minX);
        if (minY > 1)
            p = p.shiftUp((1 - minY));
        p.seal();
        return p;
    }
    public int paraCount() {
        int x = 0, y = 0, z = 0;
        for (CoordTriplet ct : array) {
            int p = ct.parallelTo();
            if (p == 0 && x == 0)
                x = 1;
            else if (p == 1 && y == 0)
                y = 1;
            else if (p == 2 && z == 0)
                z = 1;
            
        }
        return x + y + z;
    }
    int countPlanes() {
        return countPlanes(0) + countPlanes(1) + countPlanes(2);
    }
    
    int countPlanes(int c) {
        
        ArrayList<Integer> a = new ArrayList<>();
        for (CoordTriplet ct : array) {
            if (ct.parallelTo() == c) {
                Integer v = new Integer(ct.getX());
                if (!a.contains(v))
                    a.add(v);
            }
        }
        return a.size();
    }
    public int weight() {
        int ret = 0;
        
        // This version acheives goal of allowing convergence to snake for a polyominoid of width 1 that looks like this (the rectangle is width 2 and height 1 
        /*
         |   |
        - - - -
         |   |
        - - - -
         |   |
        
        */
        for (CoordTriplet ct : array) {
            int n = this.getAdjacent(ct).size();
            if (n > 2)
                ret += n;
        }

        /* This version doesn't
        int has3 = 0, has2 = 0;
        for (CoordTriplet ct : array) {
            int n = this.getAdjacentIn(ct).size();
            if (n == 3)
                has3++;
            else if (n == 2)
                has2++;
        }        
        ret = has2 + has3;
*/
        return ret;
    }
}
