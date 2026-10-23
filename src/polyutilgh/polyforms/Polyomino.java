package polyutilgh.polyforms;
import java.awt.Color;
import java.util.*;
import java.math.BigInteger;
import polyutilgh.*;
import polyutilgh.Edge;
import polyutilgh.GraphicsParameters;
import polyutilgh.PolySet;
import polyutilgh.polygons.*;
/**
 *
 * @author jmason
 */
public class Polyomino extends BasePolyomino {
    int[] dxtab = {0, 0, 1, -1}, dytab = {1, -1, 0, 0};
    public Polyomino(BigInteger inValue) {
        rep = inValue;
        build(inValue);
        
        seal();  
    }    
    public Polyomino(String inValue) {
        array = new ArrayList<CoordPair>();
        int y = 1, x = 1;
        for (int i = 0; i < inValue.length() ; i++) {
            String t = inValue.substring(i, i + 1);
            if (t.equals( "/")) {
                x = 1; 
                y++;
                continue;
            }
            if (t.equals("O"))
                this.insert(new CoordPair(x, y));
            x++;
        }
        
        seal();  
    }    
   
    public Polyomino() {
        array = new ArrayList<CoordPair>();
    }  
    public Polyomino(SAPolygon sap) {
        int x = 1, y = 1;
        array = new ArrayList<CoordPair>();
        String sapRep = sap.getRep().toString();
        for (int i = 0; i < sapRep.length(); i++) {
            this.insert(new CoordPair(x, y));
            String dir = sapRep.substring(i, i + 1);
            if (dir.equals(NORTHS))
                y++;
            else if (dir.equals(EASTS))
                x++;
            else if (dir.equals(SOUTHS))
                y--;
            else // if (dir.equals(WESTS))
                x--;
            if (x < 1) {
                x++;
                for (CoordPair cp : array)
                    cp.incrementX(1);
            } 
            if (y < 1) {
                y++;
                for (CoordPair cp : array)
                    cp.incrementY(1);
            } 
            
        }
        seal();
    }
    public Polyomino maker(BigInteger rep) {
        return new Polyomino(rep);
    }
    public Polyomino maker(String srep) {
        return new Polyomino(new BigInteger(srep));
    }
    public Polyomino maker() {
        return new Polyomino();
    }
    
    public void seal() {
        setFirstSquare();   // seems repreated in nonVBSeal 
        nonCBSeal();
        /*
        if (rep == null) {
            rep = new BigInteger("0");
            for (int i = 0 ; i < array.size() ; i++) {
                int n = getBit(array.get(i).getX(), array.get(i).getY());
                //System.out.println(ret);
                rep = rep.setBit(n);
                //System.out.println(ret);
            }
        }
     
        calcMax();
        sealed = true;    
        */
    }
    public PolySet getBiTrisect(int number) {
        PolySet ps = new PolySet();
        ArrayList<Polyomino> fixed = getFixedList();
        for (Polyomino f : fixed)
            getBiTrisect(ps, f);
        
        for (int n = 3; n <= number; n++) {
            PolySet nps = new PolySet();
            for (int i = 0; i < ps.size(); i++) {
                String s = ps.gets(i);
                Polyomino p = new Polyomino(new BigInteger(s));
                for (Polyomino f : fixed)
                    p.getBiTrisect(nps, f);
            }
            ps = nps;
        }
        
        return ps;
    }
    void getBiTrisect(PolySet ps, Polyomino f) {
        ArrayList<CoordPair> first = new ArrayList<>();
        for (CoordPair cp : array)
            for (int i = 0; i < 4; i++)
                getBiTrisect(ps, cp, i, f, first);
        
    }
    /*
    public PolySet getBiTrisect(int number) {
        PolySet ps = new PolySet();
        ArrayList<Polyomino> fixed = getFixedList();
        for (CoordPair cp : array)
            getBiTrisect(ps, cp, fixed);*/
        /*
        for (int n = 3; n <= number; n++) {
            PolySet nps = new PolySet();
            for (int i = 0; i < ps.size(); i++) {
                String s = ps.gets(i);
                Polyomino p = new Polyomino(new BigInteger(s));
                for (CoordPair cp : p.array)
                    p.getBiTrisect(nps, cp, fixed);
            }
            ps = nps;
        }
        */
        /* return ps;
    } 
    void getBiTrisect(PolySet ps, CoordPair cp, ArrayList<Polyomino> fixed) {
        for (int i = 0; i < 4; i++)
            getBiTrisect(ps, cp, i, fixed);
    }
    void getBiTrisect(PolySet ps, CoordPair cp, int i, ArrayList<Polyomino> fixed) {
        int nx = cp.getX() + deltaX[i];
        int ny = cp.getY() + deltaY[i];
        if (contains(nx, ny))
            return;
        for (Polyomino f : fixed) {
            getBiTrisect(ps, nx, ny, f);
        }
        
    } */
    void getBiTrisect(PolySet ps, CoordPair cp, int i, Polyomino f, ArrayList<CoordPair> first) {
        int nx = cp.getX() + deltaX[i];
        int ny = cp.getY() + deltaY[i];
        if (contains(nx, ny))
            return;
        
        getBiTrisect(ps, nx, ny, f, first);
        
        
    }
    void getBiTrisect(PolySet ps, int nx, int ny, Polyomino f, ArrayList<CoordPair> first) {
        for (int i = 0; i < f.size(); i++)
            getBiTrisect(ps, nx, ny, f, f.array.get(i).getX(), f.array.get(i).getY(), first);
        
    }
    void getBiTrisect(PolySet ps, int nx, int ny, Polyomino f, int fx, int fy, ArrayList<CoordPair> first) {
        Polyomino np = new Polyomino();
        np = (Polyomino)(this.copy());
        for (int i = 0; i < f.size(); i++) {
            CoordPair fcp = f.array.get(i);
            int x = fcp.getX(), y = fcp.getY();
            int nnx = nx + x - fx, nny = ny + y - fy;
            CoordPair ncp = new CoordPair(nnx, nny);
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
    void build(BigInteger rep) {
        innerBuild(rep);
    }
    public boolean areAdjacent(CoordPair cp1, CoordPair cp2) {
        return (cp1.add(1, 0).equals(cp2) ||
            cp1.add(-1, 0).equals(cp2) ||
            cp1.add(0, 1).equals(cp2) ||
            cp1.add(0, -1).equals(cp2) 
                );
    }
    public int maxDiagonalCells() {
        int ret = 0;
        for (int dx = 1; dx < getWidth(); dx++)
            for (int dy = 1; dy  < getHeight(); dy++) {
                int v = maxDiagonalCells(dx, dy);
                if (v > ret)
                    ret = v;
                v = maxDiagonalCells(dx, -dy);
                if (v > ret)
                    ret = v;
            }
        return ret;
    }
    public int boundingBoxRegions() {
        int ret = 0;
        Propagator prop = new Propagator(this, 0, 1);
        int last = 1;
        while (true) {
            CoordPair cp = prop.findIf(1);
            if (cp == null)
                return last - 1;
            prop.set(cp  ,   1, last + 1);
            last++;
        }
    }
    public int debugMax() {
        return Math.max(debugMaxX(), debugMaxY());
    }
    int debugMaxX() {
        int ret = 0;
        for (int y = 1; y <= getHeight(); y++) {
            int v = debugMaxX(y);
            if (v > ret)
                ret = v;
        }
        return ret;
    }
    int debugMaxX(int y) {
        int ret = 0;
        for (int x = 1; x <= getWidth(); x++)
            if (contains(x, y))
                ret++;
        return ret;
    }
    int debugMaxY() {
        int ret = 0;
        for (int x = 1; x <= getWidth(); x++) {
            int v = debugMaxY(x);
            if (v > ret)
                ret = v;
        }
        return ret;
    }
    int debugMaxY(int x) {
        int ret = 0;
        for (int y = 1; y <= getHeight(); y++)
            if (contains(x, y))
                ret++;
        return ret;
    }
    public int maxCellsInLine() {
        return Math.max(maxDiagonalCells(), maxOrthogonalCells());
    }
    int maxDiagonalCells(int dx, int dy) {
        int ret = 0;
        for (CoordPair cp : array) {
            int v =  maxDiagonalCells(dx, dy, cp);
            if (v > ret)
                    ret = v;
        }
        return ret;
    }
    int maxDiagonalCells(int dx, int dy, CoordPair cp) {
        int ret = 1;
        for (int x = cp.getX() + dx, y = cp.getY() + dy; x <= getWidth() && y >= 1 && y <= getHeight(); x += dx, y += dy) {
            if (contains(x, y))
                ret++;
        }
        return ret;
    }
    public int maxOrthogonalCells() {
        int maxXcells = maxCells(0);
        int maxYcells = maxCells(1);
        return Math.max(maxXcells, maxYcells);
    }
    /*
    if c == 0, get maximum number of cells in a row, ...
    */
    int maxCells(int c) {
        int ret = 0;
        for (int z = 1; z <= this.getMax(1 - c); z++) {
            int v = getMax(c, z);
            if (v > ret)
                ret = v;
        }
        return ret;
    }
    public int getMaxDimension() {
        return Math.max(getWidth(), getHeight());
    }
    public int getMinDimension() {
        return Math.min(getWidth(), getHeight());
    }
    int getMax(int c) {
        if (c == 0)
            return getMaxX();
        else
            return getMaxY();
    }
    /*
    if c = 0, get number of cells in row(y) z, ...
    */
    int getMax(int c, int z) {
        int ret = 0;
        for (CoordPair cp : array) {
            if (cp.getCoord(1 - c) == z)
                ret++;           
        }
        return ret;
    }
    public boolean kissing() {
        return kissing(1) || kissing(-1);
    }
    boolean kissing(int dir) {
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (contains(x + 1, y + dir) && !contains(x + 1, y) && !contains(x, y + dir))
                return true;
        }
        return false;
    }

    public PolyformWC getPointed(int i) {
    
        Polyomino ret = new Polyomino();
        for (int j = 0; j < this.size(); j++) {
            CoordPair cp = this.getCoords(j);
            int x = cp.getX(), y = cp.getY();
            ret.insert(3 * x - 2, 3 * y - 2);
            ret.insert(3 * x - 1, 3 * y - 2);
            ret.insert(3 * x - 0, 3 * y - 2);
            ret.insert(3 * x - 2, 3 * y - 0);
            ret.insert(3 * x - 1, 3 * y - 0);
            ret.insert(3 * x - 0, 3 * y - 0);
            ret.insert(3 * x - 2, 3 * y - 1);
            ret.insert(3 * x - 0, 3 * y - 1);
            
        }
        CoordPair icp = this.getCoords(i);
        int x = icp.getX(), y = icp.getY();
        ret.insert(3 * x - 1, 3 * y - 1);
        ret.seal();
        return ret;
           
    }
    int[] deltaXpp = {0, 0, 1, -1, 1, 1, -1, -1};
    int[] deltaYpp = {1, -1, 0, 0, 1, -1, 1, -1};
    public boolean isPerfectHalo() {
        //System.err.println(drawing());
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            int adj = 0;
            for (int j = 0; j < 8; j++) {
                int nx = x + deltaXpp[j], ny = y + deltaYpp[j];
                if (contains(nx, ny) ) {
                    adj++;
    
                }
            }
            if (adj != 2)
                return false;
            
        }
        return true;
    } 
    public boolean hasPerfectHalo() {
        Polyomino halo = getHalo();
        return halo.isPerfectHalo();
    }
    int deltaX[] =  {0, 0, 1, -1};
    int deltaY[] =  {1, -1, 0, 0};    
    public Polyomino getHalo() {
        Polyomino halo = new Polyomino();
        for (int i = 0; i < size(); i++) {
            CoordPair cp = getCoords(i);
            int x = cp.getX(), y = cp.getY();
            for (int j = 0; j < 4; j++) {
                int nx = x + deltaX[j], ny = y + deltaY[j];
                if (contains(nx, ny))
                    continue;
                nx++;
                ny++;
                if (halo.contains(nx, ny))
                    continue;
                halo.insert(nx, ny);
            }
            
        }
        halo.seal();    
        return halo;
    }
    public boolean lShaped() {
        for (int i = 0; i < size() - 1; i++)
            for (int j = i + 1; j < size() ; j++)
                if (!lPath(array.get(i), array.get(j)))
                    return false;
        return true;
    }
    boolean lPath(CoordPair from, CoordPair to) {
        int fX = from.getX(), fY = from.getY(), tX = to.getX(), tY = to.getY();
        int dx = 1, dy = 1;
        if (tX < fX)
            dx = -1;
        if (tY < fY)
            dy = -1;
        if (allX(fX, dx, tX, fY) && allY(fY, dy, tY, tX))
            return true;
        if (allX(fX, dx, tX, tY) && allY(fY, dy, tY, fX))
            return true;
        return false;
    }
    boolean allX(int fX, int dx, int tX, int y) {
        for (int x = fX; ; x += dx) {
            if (!contains(x, y))
                return false;
            if (x == tX)
                return true;
        }
    }
    boolean allY(int fY, int dy, int tY, int x) {
        for (int y = fY; ; y += dy) {
            if (!contains(x, y))
                return false;
            if (y == tY)
                return true;
        }
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp) {
        return gp.green;
    }
    public  Color getGraphicsColour(GraphicsParameters gp, CoordPair cp, boolean applyCB) {
        if (applyCB) {
            if (cp.parity() == fcp.parity())
                return gp.green;
            else
                return gp.yellow;
        }
        return gp.green;
    }
    
    public Polyomino rotate90() {
        Polyomino ret = maker();
        int h = this.getHeight();
        for (int i = 0 ; i < array.size(); i++) {
            ret.insert(array.get(i).newValues(h + 1 - array.get(i).getY(), array.get(i).getX()));
        }
        ret.seal();
        return ret;
    }
    public Polyomino reflectH() {
        Polyomino ret = maker();
        int h = this.getHeight();
        for (int i = 0 ; i < array.size(); i++) {
            ret.insert(array.get(i).newValues(array.get(i).getX(), h + 1 - array.get(i).getY()));
        }
        ret.seal();
        return ret;
    }
    public Polyomino reflectV() {
        Polyomino ret = maker();
        int w = this.getWidth();
        for (int i = 0 ; i < array.size(); i++) {
            ret.insert(array.get(i).newValues(w + 1 - array.get(i).getX(), array.get(i).getY()));
        }
        ret.seal();
        return ret;
    }
    public Polyomino reflectD() {
        Polyomino ret = maker();
        
        for (int i = 0 ; i < array.size(); i++) {
            ret.insert(array.get(i).newValues(array.get(i).getY(), array.get(i).getX()));
        }
        ret.seal();
        return ret;
    }
   

   
    public boolean pile() {
        for (int i = 0 ; i < array.size() ; i++) {
            if (array.get(i).getY() == 1)
                continue;
            if (this.contains(array.get(i).getX(), array.get(i).getY() - 1))
                continue;
            return false;
        }
        return true;
    }
    public boolean board() {
        int maxX = this.getWidth();
        int maxY = this.getHeight();
        for (int y = 1 ; y <= maxY ; y++) {
            int flag = 0;
            for (int x = 1 ; x <= maxX ; x++) {
               if (this.contains(x, y)) {
                   if (flag == 0)
                       flag = 1;
                   if (flag == 2)
                       return false;
               } else {
                   if (flag == 1)
                       flag = 2;
               }
            }
        }
        return true;
    
    }

    public PolySet genSA() {
        PolySet ps = new PolySet();
        if (array.size() == 0) {
            
            ps.adds("1");
            return ps;
        }
        for (int i = 0; i < array.size(); i++) {
            CoordPair cp = array.get(i);
            for (int j = 1; j <= 4; j++) {
                Edge e = new Edge(cp, j);
                if (!isPerimetral(e))
                    continue;
                CoordPair adj = e.getAdjacentCoordPair();
                if (!addable(adj, e))
                    continue;
                ArrayList<Edge> list = new ArrayList<>();
                list.add(e);
                CoordPair ncp = cp;
                while (true) {
                    ncp = ncp.nextAlong(e.getSide());
                    if (!this.contains(ncp))
                        break;
                    e = new Edge(ncp, e.getSide());
                    if (!this.isPerimetral(e))
                        break;
                    CoordPair adj2 = e.getAdjacentCoordPair(); 
                    if (!addable(adj2, e))
                        break;
                    list.add(e);
                }

                for (int m = 0; m < list.size() ; m++) {
                    if (adj.getX() < 1) {
                        Polyomino np = shiftRight(1);
                        BigInteger rep = np.getRep();
                        for (int t = 0; t <= m; t++) {
                            rep = rep.setBit(getBit(1, list.get(t).getCoordPair().getY()));
                        }
                        /*
                        if (new Polyomino(rep).countHoles() > 0) {
                            System.err.println(this.drawing());
                        }
                        */
                        ps.addUniques(new Polyomino(rep));
                        /*
                    if (new Polyomino(rep).perimeterOrSurface() != this.perimeterOrSurface() + 2) {                                             
                        System.err.println(this.drawing());
                        System.err.println(new Polyomino(rep).drawing());                      
                    }               
                        */
                        continue;
                    }
                    if (adj.getY() < 1) {
                        Polyomino np = shiftUp(1);
                        BigInteger rep = np.getRep();
                        for (int t = 0; t <= m; t++) {
                            rep = rep.setBit(getBit(list.get(t).getCoordPair().getX(), 1));
                        }
                       /*
                        if (new Polyomino(rep).countHoles() > 0) {
                            System.err.println(this.drawing());
                        }    
                       */
                        ps.addUniques(new Polyomino(rep));
                        /*
                    if (new Polyomino(rep).perimeterOrSurface() != this.perimeterOrSurface() + 2) {                                             
                        System.err.println(this.drawing());
                        System.err.println(new Polyomino(rep).drawing());                      
                    }            
                        */
                        continue;
                    }   
                    BigInteger rep = getRep();
                    for (int t = 0; t <= m; t++) {
                        CoordPair acp = list.get(t).getAdjacentCoordPair();
                        rep = rep.setBit(getBit(acp.getX(), acp.getY()));
                    }
                    /*
                    if (new Polyomino(rep).countHoles() > 0) {                                             
                        System.err.println(this.drawing());
                        System.err.println(new Polyomino(rep).drawing());                      
                    }   
                    if (new Polyomino(rep).perimeterOrSurface() != this.perimeterOrSurface() + 2) {                                             
                        System.err.println(this.drawing());
                        System.err.println(new Polyomino(rep).drawing());                      
                    }   
                    */
                    ps.addUniques(new Polyomino(rep));                    
                }
                
            }
        }
        return ps;
    }
    boolean addable(CoordPair ncp, Edge e) {
        CoordPair[] list = genNear(ncp, e);
        for (int i = 0; i < 5; i++) {
            if (this.contains(list[i]))
                return false;
        }
        return true;
    }
    /*
    boolean addable(CoordPair cpNew, CoordPair cpOld) {
        return  addable(cpNew, cpOld, 1, 0) && 
                addable(cpNew, cpOld, 0, 1) && 
                addable(cpNew, cpOld, -1, 0) && 
                addable(cpNew, cpOld, 0, -1);
    }
    boolean addable(CoordPair cpNew, CoordPair cpOld, int dx, int dy) {
        CoordPair adj = new CoordPair(cpNew.getX() + dx, cpNew.getY() + dy);
        if (adj.equals(cpOld))
            return true;
        return !this.contains(adj);
    }
    */
    CoordPair[] genNear(CoordPair ncp, Edge e) {
        CoordPair[] ret = new CoordPair[5];
        if (e.getSide() == 1) {
            ret[0] = new CoordPair(ncp.getX() - 1, ncp.getY());
            ret[1] = new CoordPair(ncp.getX() - 1, ncp.getY() + 1);
            ret[2] = new CoordPair(ncp.getX() ,    ncp.getY() + 1);
            ret[3] = new CoordPair(ncp.getX() + 1, ncp.getY() + 1);
            ret[4] = new CoordPair(ncp.getX() + 1, ncp.getY());
            
        } else if (e.getSide() == 2) {
            ret[0] = new CoordPair(ncp.getX()    , ncp.getY() + 1);
            ret[1] = new CoordPair(ncp.getX() + 1, ncp.getY() + 1);
            ret[2] = new CoordPair(ncp.getX() + 1, ncp.getY() );
            ret[3] = new CoordPair(ncp.getX() + 1, ncp.getY() - 1);
            ret[4] = new CoordPair(ncp.getX()    , ncp.getY() - 1);
            
        } else if (e.getSide() == 3) {
            ret[0] = new CoordPair(ncp.getX() - 1, ncp.getY());
            ret[1] = new CoordPair(ncp.getX() - 1, ncp.getY() - 1);
            ret[2] = new CoordPair(ncp.getX() ,    ncp.getY() - 1);
            ret[3] = new CoordPair(ncp.getX() + 1, ncp.getY() - 1);
            ret[4] = new CoordPair(ncp.getX() + 1, ncp.getY());
            
        } else  { // if (e.getSide() == 4)
            ret[0] = new CoordPair(ncp.getX()    , ncp.getY() + 1);
            ret[1] = new CoordPair(ncp.getX() - 1, ncp.getY() + 1);
            ret[2] = new CoordPair(ncp.getX() - 1, ncp.getY() );
            ret[3] = new CoordPair(ncp.getX() - 1, ncp.getY() - 1);
            ret[4] = new CoordPair(ncp.getX()    , ncp.getY() - 1);
            
        } 
        return ret;
    }
    
    public void correct() {
        Polyomino ret = this;
        for (int i = 0 ; i < array.size() ; i++) {
            CoordPair cp = array.get(i);
            if (cp.getX() < 1) {
                System.err.println("x < 1");
                System.exit(1);
            }
            if (cp.getY() < 1) {
                System.err.println("x < 1");
                System.exit(1);
            }
        }
        
    }
    public ArrayList<Polyomino> getFixedList() {
        ArrayList<Polyomino> ret = new ArrayList<>();
        PolySet ps = this.getFixed();
        for (int i = 0; i < ps.size(); i++)
            ret.add(new Polyomino(new BigInteger(ps.gets(i))));
        return ret;
    }
    public boolean hasFactor(Polyomino divisor) {
        ArrayList<Polyomino> dList = divisor.getFixedList();
        return hasFixedFactor(dList);
       
    }
    public boolean hasFixedFactor(ArrayList<Polyomino> dList) {
        mainloop: for (Polyomino divisor : dList) {
            CoordPair thisTopLeft = this.findTopLeft();
            CoordPair divisorTopLeft = divisor.findTopLeft();
            int tx = thisTopLeft.getX(), ty = thisTopLeft.getY();
            int dx = divisorTopLeft.getX(), dy = divisorTopLeft.getY();
            for (CoordPair cp : divisor.array) {
                int x = cp.getX(), y = cp.getY();
                if (!contains(tx + x - dx, ty + y - dy))
                    continue mainloop;
            }
            if (this.size() == divisor.size())
                return true;
            Polyomino remainder = this.subtract(divisor, tx, ty, dx, dy);
            if (remainder.hasFixedFactor(dList))
                return true;
        }
        return false;
    }
    Polyomino subtract(Polyomino divisor, int tx, int ty, int dx, int dy) {
        Polyomino ret = new Polyomino();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (!divisor.contains(x - tx + dx, y - ty + dy))
                ret.insert(cp);
        }
        ret = (Polyomino)(ret.align());
        return ret;
    }
    public CoordPair findTopLeft() {
        int y = getHeight();
        int x = 99999999;
        for (CoordPair cp : array) {
            if (cp.getY() == y)
                if (cp.getX() < x)
                    x = cp.getX();
            
        }
        return new CoordPair(x, y);
    }
    int twoPower(int n) {
        int ret = 1;
        while (n > 0) {
            ret *=2;
            n--;
        }
        return ret;
    }

    

/*
    public boolean strip() {
        if (size() <= 1)
            return true;
        
        int adj1 = 0;
        for (int i = 0 ; i < size(); i++) {
            int adj = adjacentCount(array.get(i));
            if (adj > 2)
                return false;
            if (adj == 1)
                adj1++;
        }
        return adj1 == 2;
    }
    int adjacentCount(CoordPair cp) {
        return adjacentCount(cp, 0, 1) + 
                adjacentCount(cp, 0, -1) + 
                adjacentCount(cp, 1, 0) + 
                adjacentCount(cp, -1, 0);
    }
    int adjacentCount(CoordPair cp, int dx, int dy) {
        int x = cp.getX() + dx;
        int y = cp.getY() + dy;
        if (x < 0 || y < 0)
            return 0;
        if (this.contains(x, y))
            return 1;
        else
            return 0;
    }
*/


    
    // return high value of predominant
    /*
Size of polyomino	predominant squares	minor squares	bias
4n                      3n                      n               2n
4n+1                    3n+1                    n               2n+1
4n+2                    3n+1                    n+1             2n
4n+3                    3n+2                    n+1             2n+1
4n+4                    3n+3                    n+1             2n+2
    
    */

    public String getUnique() {
        
        BigInteger rep1 = this.getRep();
        // for debugging
        
        Polyomino x1 = maker(rep1);
        Polyomino y1 = x1.rotate90();
        BigInteger z2 = y1.getRep();
        Polyomino x2 = maker(z2);
        Polyomino y2 = x2.rotate90();
        BigInteger z3 = y2.getRep();
        
        
        BigInteger rep2 = maker(rep1).rotate90().getRep();
        BigInteger rep3 = maker(rep2).rotate90().getRep();
        BigInteger rep4 = maker(rep3).rotate90().getRep();
        BigInteger rep5 = maker(rep4).reflectH().getRep();
        BigInteger rep6 = maker(rep5).rotate90().getRep();
        BigInteger rep7 = maker(rep6).rotate90().getRep();
        BigInteger rep8 = maker(rep7).rotate90().getRep();
        
        if (rep1.compareTo(rep2) > 0)
            rep1 = rep2;
        if (rep1.compareTo(rep3) > 0)
            rep1 = rep3;
        if (rep1.compareTo(rep4) > 0)
            rep1 = rep4;
        if (rep1.compareTo(rep5) > 0)
            rep1 = rep5;
        if (rep1.compareTo(rep6) > 0)
            rep1 = rep6;
        if (rep1.compareTo(rep7) > 0)
            rep1 = rep7;
        if (rep1.compareTo(rep8) > 0)
            rep1 = rep8;
        
        return rep1.toString();
    }   
    public String getVUnique() {
        
        BigInteger rep1 = this.getRep();
        BigInteger rep2 = new Polyomino(rep1).reflectV().getRep();
        
        
        if (rep1.compareTo(rep2) > 0)
            rep1 = rep2;
        
        
        return rep1.toString();
    } 
    public boolean tExtreme() {
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (y == 1 && x == 1)
                return false;
            if (y == 1 && x == getWidth())
                return  false;
        }
        for (int x = 1; x <= getWidth(); x++)
            if (!contains(x, getHeight()))
                return false;
        return true;
    }
    public String drawing() {
        int h = this.getHeight();
        int w = this.getWidth();
        String ret = "\r\n";
        for (int y = h ; y >= 1 ; y--) {
            String buf = "";
            for (int x = 1; x <= w ; x++) {
                CoordPair cp = this.find(x, y);
                if (this.contains(x, y))
                    buf += cp.getLetter();
                else 
                    buf += "_";
            }
            ret += buf + "\r\n";
        }
               
        return ret;
               
          
    } 
    public String drawing(int showX, int showY) {
        int h = this.getHeight();
        int w = this.getWidth();
        String ret = "\r\n";
        for (int y = h ; y >= 1 ; y--) {
            String buf = "";
            for (int x = 1; x <= w ; x++)
                if (this.contains(x, y)) {
                    if (x == showX && y == showY)
                        buf += "X";
                    else
                        buf += "O";
                }
                else 
                    buf += "_";
            ret += buf + "\r\n";
        }
               
        return ret;
               
          
    } 
    public String drawing(int minX, int minY, int maxX, int maxY) {
       
        String ret = "\r\n";
        for (int y = maxY ; y >= minY ; y--) {
            String buf = "";
            for (int x = minX; x <= maxX ; x++)
                if (this.contains(x, y))
                    buf += "O";
                else 
                    buf += "_";
            ret += buf + "\r\n";
        }
               
        return ret;
               
          
    } 
    
  
    public PolySet getFixed() {
        
        PolySet ps = new PolySet();
        Polyomino poly;
        ps.adds(this.getRep().toString());
        poly = this.rotate90(); ps.adds(poly.getRep().toString());
        poly = poly.rotate90(); ps.adds(poly.getRep().toString());
        poly = poly.rotate90(); ps.adds(poly.getRep().toString());
        poly = poly.reflectH(); ps.adds(poly.getRep().toString());
        poly = poly.rotate90(); ps.adds(poly.getRep().toString());
        poly = poly.rotate90(); ps.adds(poly.getRep().toString());
        poly = poly.rotate90(); ps.adds(poly.getRep().toString());      
        return ps;
    } 
    public PolySet getRotations() {
        
        PolySet ps = new PolySet();
        Polyomino poly;
        ps.adds(this.getRep().toString());
        poly = this.rotate90(); ps.adds(poly.getRep().toString());
        poly = poly.rotate90(); ps.adds(poly.getRep().toString());
        poly = poly.rotate90(); ps.adds(poly.getRep().toString());
        
        return ps;
    } 
    
 
    public PolySet getOnesided() {
        
        PolySet ps = new PolySet();
        ps.adds(this.getRep().toString());
        Polyomino polyH = this.reflectH(); 
        Polyomino polyV = this.reflectV(); 
        Polyomino polyD1 = this.reflectD(); 
        Polyomino polyD2 = this.rotate90().rotate90().reflectD(); 
        if (this.equals(polyH))
            return ps;
        if (this.equals(polyV))
            return ps;
        if (this.equals(polyD1))
            return ps;
        if (this.equals(polyD2))
            return ps;
        ps.adds(polyH.getRep().toString());
        return ps;
    }    
    public Polyomino spread() {
        Polyomino np = new Polyomino();
        for (CoordPair cp : array) {
            int x = cp.getX() + 1, y = cp.getY() + 1;
            np.conditionalInsert(x, y);
            np.conditionalInsert(x + 1, y);
            np.conditionalInsert(x - 1, y);
            np.conditionalInsert(x, y + 1);
            np.conditionalInsert(x, y - 1);
            
        }
        np.seal();
        return np;
    }
    public boolean conditionalInsert(int x, int y) {
        if (contains(x, y))
            return false;
        this.strictInsert(x, y);
        return true;
    }
    public Polyomino makeBorderPoly() {
        return makeBorderPoly(1);
    }
    public Polyomino makeBorderPoly(int delta) {
        BigInteger rep = BigInteger.ZERO;
        for (CoordPair cp : array) {
            rep = makeBorder(rep, cp, delta);
        }
        return new Polyomino(rep);
    }
    BigInteger makeBorder(BigInteger rep, CoordPair cp, int delta) {
        int x = cp.getX(), y = cp.getY();
        for (int dx = -1; dx <= 1; dx++)
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0)
                    continue;
                int nx = x + dx, ny = y + dy;
                if (contains(nx, ny))
                    continue;
                rep = rep.setBit(getBit(nx + delta, ny + delta));
            }
        return rep;
    }
    public boolean loopBorder() {
        return this.makeBorderPoly().loop();
    }
    // only for 2M90 polyominoes
    public boolean dominoCentered() {
        int width = getWidth();
       
        int height = getHeight();
        
        int x1, y1, x2 , y2;
        if (width % 2 == 1) {
            if (height % 2 == 1)
                return false;
            x1 = (width + 1) / 2;
            x2 = x1;
            y1 = height / 2;
            y2 = y1 + 1;
        } else        if (height % 2 == 1) {
            if (width % 2 == 1)
                return false;
            y1 = (height + 1) / 2;
            y2 = y1;
            x1 = width / 2;
            x2 = x1 + 1;
        } else
            return false;
        return (contains(x1, y1) && contains(x2, y2));
        
    }    
    public int getDistance(Polyomino other) {
        int ret = size();
        PolySet ps = other.getFixed();
        for (int i = 0 ; i < ps.size(); i++) {
            String s = ps.gets(i);
            Polyomino p = new Polyomino(new BigInteger(s));
            int d = getInnerDistance(p);
            if (d < ret)
                ret = d;
        }
        return ret;
    }
    int getInnerDistance(Polyomino other) {
        RectangleAboutPolyomino thisRectangle = new RectangleAboutPolyomino(this), otherRectangle = new RectangleAboutPolyomino(other);
        return size() - thisRectangle.countMatch(otherRectangle);
    }
    public Polyomino quadruple() {
        Polyomino np = new Polyomino();
        int w = getWidth(), h = getHeight();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            np.insert(x + w, y + h);
            np.insert(x + w, h - y + 1);
            np.insert(w - x + 1, y + h);
            np.insert(w - x + 1, h - y + 1);
            
        }
        np.seal();
        return np;
    }
    public Polyomino duplicateBelow() {
        Polyomino np = new Polyomino();
        int  h = getHeight();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            np.insert(x , y + h);
            np.insert(x , h - y + 1);
            
            
        }
        np.seal();
        return np;
    }
    public PolySet genDistinguished(int number) {
        PolySet ps = new PolySet();
        //System.out.println("gd " + number + this.drawing());
        Polyomino base = new Polyomino();
        ArrayList<Vertex> list = new ArrayList<>();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            int cx = 3 * x - 1, cy = 3 * y - 1;
            base.insert(cx, cy);
            base.insert(cx + 1, cy);
            base.insert(cx, cy + 1);
            base.insert(cx + 1, cy + 1);
            add(list, cx - 1, cy - 1);
            add(list, cx + 2, cy - 1);
            add(list, cx - 1, cy + 2);
            add(list, cx + 2, cy + 2);            
        }
        if (number == 0) {
                
            for (CoordPair cp : base.array)
                cp.incrementX(-1);

            for (CoordPair cp : base.array)
                cp.incrementY(-1); 
            base.seal();
            ps.addUniques(base);
            return ps;
        }        
        genDistinguished(ps, base, list, 0, 0, number, false, false);
        return ps;
    
    }
    private void genDistinguished(PolySet ps, Polyomino base, ArrayList<Vertex> list, int starter, int inserted, int number, boolean hitX, boolean hitY) {
        //System.out.println("gd begins " + starter + " " + number + base.drawing());
        /*
        for (Vertex vv : list) {
            System.out.println("vertex " + vv.x + " " + vv.y);
        }
        */
        for (int i = starter; i < list.size(); i++) {
            //System.out.println("gd i=" + i);
            Polyomino newBase = (Polyomino)(base.copy(0, 0));
            Vertex v = list.get(i);
            newBase.insert(v.x, v.y);
            boolean newHitX = hitX, newHitY = hitY;
            if (v.x == 1)
                newHitX = true;
            if (v.y == 1)
                newHitY = true;
            if (inserted + 1 == number) {
                if (!newHitX)
                    for (CoordPair cp : newBase.array)
                        cp.incrementX(-1);
                if (!newHitY)
                    for (CoordPair cp : newBase.array)
                        cp.incrementY(-1);
                newBase.seal();
                if (ps.addUniques(newBase)) {
                    //System.out.println("added " + newBase.drawing());
                    DistinguishedPolyomino d = new DistinguishedPolyomino(newBase.getRep());
                    if (d.countVertices() != number) {
                        int qq = 1 / 0;
                    }
                }
            } else {
                genDistinguished( ps, newBase,  list,  i + 1,  inserted + 1, number, newHitX, newHitY);
            }
        }
    }
    
    Hashtable<String, String> equiv;
    String substitute(String in) {
        String out = equiv.get(in);
        if (out == null)
            return in;
        else
            return out;
    }       
    public String getW3Build() {
        String curr;
        equiv = new Hashtable<>();
       
        curr = "w3_c_cr"; equiv.put("w3_c_lc", curr); equiv.put("w3_cr_c", curr); equiv.put("w3_lc_c", curr);
        curr = "w3_c_lcr_slr"; equiv.put("w3_lcr_c_slr", curr);
        curr = "w3_c_lr"; equiv.put("w3_lr_c", curr);
        curr = "w3_lr_c_slr"; equiv.put("w3_c_lr_slr", curr);
        curr = "w3_c_r"; equiv.put("w3_r_c", curr); equiv.put("w3_c_l", curr); equiv.put("w3_l_c", curr);
        curr = "w3_cr_cr"; equiv.put("w3_lc_lc", curr);
        curr = "w3_cr_cr_sud"; equiv.put("w3_lc_lc_sud", curr);
        curr = "w3_cr_l"; equiv.put("w3_l_cr", curr); equiv.put("w3_r_lc", curr); equiv.put("w3_lc_r", curr);
        curr = "w3_cr_lc_srot"; equiv.put("w3_lc_cr_srot", curr);
        curr = "w3_cr_lcr"; equiv.put("w3_lcr_cr", curr); equiv.put("w3_lc_lcr", curr); equiv.put("w3_lcr_lc", curr);
        curr = "w3_lr_cr"; equiv.put("w3_cr_lr", curr); equiv.put("w3_lc_lr", curr); equiv.put("w3_lr_lc", curr);
        curr = "w3_l_l"; equiv.put("w3_r_r", curr);
        curr = "w3_l_r"; equiv.put("w3_r_l", curr);
        curr = "w3_l_r_srot"; equiv.put("w3_r_l_srot", curr);
        curr = "w3_lc_cr"; equiv.put("w3_cr_lc", curr);
        curr = "w3_lcr_c"; equiv.put("w3_c_lcr", curr);
        curr = "w3_lcr_lr"; equiv.put("w3_lr_lcr", curr);
        curr = "w3_lcr_lr_slr"; equiv.put("w3_lr_lcr_slr", curr);
        curr = "w3_lcr_r"; equiv.put("w3_lcr_l", curr); equiv.put("w3_l_lcr", curr); equiv.put("w3_r_lcr", curr);
        curr = "w3_lr_r"; equiv.put("w3_lr_l", curr); equiv.put("w3_r_lr", curr); equiv.put("w3_l_lr", curr);
        curr = "w3_r_cr"; equiv.put("w3_l_lc", curr); equiv.put("w3_cr_r", curr); equiv.put("w3_lc_l", curr);
        curr = "w3_r_r_sud"; equiv.put("w3_l_l_sud", curr);              
        if (getWidth() != 3) {
            System.err.println("non width 3");
            System.exit(1);
        }
        int size = size();
        int accum = 0, sizeBelow = 0, sizeAbove;
        int half = size / 2;
        String row = "", coeffAbove, coeffBelow, ret , abovePart, belowPart, caseNumber = "";
        int yRow, lenRow = 0;
        for ( yRow = 1; ; yRow++) {
            accum += numberOnY3(yRow);
            if (accum > half)
                break;
            sizeBelow = accum;
            if (accum == half && (size % 2 == 0))
                break;
        }
        if (accum != half) {
            row = getRow3(yRow);
            lenRow = row.length();
        }
        //int restNotRow = size - lenRow;
        sizeAbove = size - sizeBelow - lenRow;
        coeffAbove = calcCoeff(sizeAbove, half);
        coeffBelow = calcCoeff(sizeBelow, half);
        Polyomino polyAbove;
        Polyomino polyBelow;
        if (lenRow == 0) {
            polyBelow = extractRows(1, yRow);
            polyAbove = extractRows(yRow + 1, getHeight());
        } else {
            polyBelow = extractRows(1, yRow - 1);
            polyAbove = extractRows(yRow + 1, getHeight());            
        }
        if (size % 2 == 0) {
            switch(row) {
                case "":
                    caseNumber = "i";
                    break;
                case "lcr":
                    caseNumber = "ii";
                    break;
                case "lc":
                case "cr":
                    caseNumber = "iii row_" + row + "_" + row;
                    break;
                case "lr":
                    caseNumber = "iv";
                    break;
            }
            
        } else {
            switch(row) {
                
                case "lcr":
                    caseNumber = "v";
                    break;
                case "lc":
                case "cr":
                    caseNumber = "vi row_" + row + "_" + row;
                    break;
                case "lr":
                    caseNumber = "vii";
                    break;
                case "l":
                case "r":
                    caseNumber = "viii";
                    break;
                case "c":
                    caseNumber = "ix";
                    break;
            }        

            
        }
        int aboveMinX = polyAbove.getMinX(), aboveMaxX = polyAbove.getWidth();
        int belowMinX = polyBelow.getMinX(), belowMaxX = polyBelow.getWidth();  
        
        String aboveTop = polyAbove.getRow3(polyAbove.getHeight());
        String aboveBottom = polyAbove.getRow3(1);
        String belowTop = polyBelow.getRow3(polyBelow.getHeight());
        String belowBottom = polyBelow.getRow3(1);
        
        if (polyAbove.intact()) {
            if (aboveMinX == 1 && aboveMaxX == 3) {
                abovePart = "w3_" + aboveTop + "_" + aboveBottom;
                if (polyAbove.testMirrorV())
                    abovePart += "_slr";
                if (polyAbove.testMirrorH())
                    abovePart += "_sud";
                if (polyAbove.testRotate180())
                    abovePart += "_srot";
                abovePart = substitute(abovePart);
                
            } else if (aboveMinX == aboveMaxX ) {
                abovePart = "w1_" + aboveTop + "_" + aboveBottom;
                if (aboveTop.equals("c"))
                    abovePart += "_slr";
                if (polyAbove.testMirrorV())
                    abovePart += "_slr";                
            } else {
                if (aboveMinX == 1) {
                    abovePart = "w2l_" + aboveTop + "_" + aboveBottom;
                } else {
                    abovePart = "w2r_" + aboveTop + "_" + aboveBottom;
                }
                if (polyAbove.testMirrorV())
                    abovePart += "_slr";     
            }
        } else {
            if (polyAbove.countX(2) == 0) {
                if (polyAbove.contains(1, polyAbove.getHeight()) && polyAbove.contains(3, polyAbove.getHeight())) {
                    abovePart = "ncolcol_" + aboveTop + "_" + aboveBottom + "_slr";
                } else {
                    abovePart = "ncolcol_" + aboveTop + "_" + aboveBottom ;
                }
            } else {
                if (polyAbove.isCol(1, 2, 1, 1)) {
                    if (polyAbove.beyondCol(1, 2, 1, 1)) {
                        abovePart = "loop_above_r_w3_" + aboveTop + "_r"; // doubts
                    } else {
                        abovePart = "loop_above_r_w2r_" + aboveTop + "_r";
                    }
                } else {
                    if (polyAbove.beyondCol(3, 2, 1, 1)) {
                        abovePart = "loop_above_l_w3_" + aboveTop + "_l"; // doubts
                    } else {
                        abovePart = "loop_above_l_w2l_" + aboveTop + "_l";
                    }                    
                }
            }
        }
        if (polyBelow.intact()) {
            if (belowMinX == 1 && belowMaxX == 3) {
                belowPart = "w3_" + belowTop + "_" + belowBottom;
                if (polyBelow.testMirrorV())
                    belowPart += "_slr";
                if (polyBelow.testMirrorH())
                    belowPart += "_sud";
                if (polyBelow.testRotate180())
                    belowPart += "_srot";
                belowPart = substitute(belowPart);
                
            } else if (belowMinX == belowMaxX ) {
                belowPart = "w1_" + belowTop + "_" + belowBottom;
                if (belowTop.equals("c"))
                    belowPart += "_slr";
                      
            } else {
                if (belowMinX == 1) {
                    belowPart = "w2l_" + belowTop + "_" + belowBottom;
                } else {
                    belowPart = "w2r_" + belowTop + "_" + belowBottom;
                }
                if (polyBelow.testMirrorV())
                    belowPart += "_slr";     
            }
        } else {
            if (polyBelow.countX(2) == 0) {
                if (polyBelow.contains(1, 1) && polyBelow.contains(3, 1)) {
                    belowPart = "ncolcol_" + belowTop + "_" + belowBottom + "_slr";
                } else {
                    belowPart = "ncolcol_" + belowTop + "_" + belowBottom ;
                }
            } else {
                if (polyBelow.isCol(1, 2, polyBelow.getHeight(), -1)) {
                    if (polyBelow.beyondCol(1, 2, polyBelow.getHeight(), -1)) {
                        belowPart = "loop_below_r_w3_" + "r" + "_" + belowBottom; // doubts
                    } else {
                        belowPart = "loop_below_r_w2r_" + "r" + "_" + belowBottom;
                    }
                } else {
                    if (polyBelow.beyondCol(3, 2, polyBelow.getHeight(), -1)) {
                        belowPart = "loop_below_l_w3_" + "l" + "_" + belowBottom; // doubts
                    } else {
                        belowPart = "loop_below_l_w2l_"  + "l" + "_" + belowBottom;
                    }                    
                }
            }
        }
        ret = "prod(" + abovePart + "(" + coeffAbove + "), " + belowPart + "(" + coeffBelow + "))/* case " + caseNumber + " */ : 1"; 
        return ret;
    }
    public String getW3BuildABC() {
        String curr;
        equiv = new Hashtable<>();
       
             
        if (getWidth() != 3) {
            System.err.println("non width 3");
            System.exit(1);
        }
        int size = size();
        int accum = 0, sizeBelow = 0, sizeAbove;
        int half = size / 2;
        String row = "", coeffAbove, coeffBelow, ret , abovePart, belowPart, caseNumber = "";
        int yRow, lenRow = 0;
        for ( yRow = 1; ; yRow++) {
            accum += numberOnY3(yRow);
            if (accum > half)
                break;
            sizeBelow = accum;
            if (accum == half && (size % 2 == 0))
                break;
        }
        if (accum != half) {
            row = getRow3ABC(yRow);
            lenRow = row.length();
        }
        //int restNotRow = size - lenRow;
        sizeAbove = size - sizeBelow - lenRow;
        coeffAbove = calcCoeff(sizeAbove, half);
        coeffBelow = calcCoeff(sizeBelow, half);
        Polyomino polyAbove;
        Polyomino polyBelow;
        if (lenRow == 0) {
            polyBelow = extractRows(1, yRow);
            polyAbove = extractRows(yRow + 1, getHeight());
        } else {
            polyBelow = extractRows(1, yRow - 1);
            polyAbove = extractRows(yRow + 1, getHeight());            
        }
        if (size % 2 == 0) {
            switch(row) {
                case "":
                    caseNumber = "i";
                    break;
                case "abc":
                    caseNumber = "ii";
                    break;
                case "ab":
                case "bc":
                    caseNumber = "iii row_" + row + "_" + row;
                    break;
                case "ac":
                    caseNumber = "iv";
                    break;
            }
            
        } else {
            switch(row) {
                
                case "abc":
                    caseNumber = "v";
                    break;
                case "ab":
                case "bc":
                    caseNumber = "vi row_" + row + "_" + row;
                    break;
                case "ac":
                    caseNumber = "vii";
                    break;
                case "a":
                case "c":
                    caseNumber = "viii";
                    break;
                case "b":
                    caseNumber = "ix";
                    break;
            }        

            
        }
        int aboveMinX = polyAbove.getMinX(), aboveMaxX = polyAbove.getWidth();
        int belowMinX = polyBelow.getMinX(), belowMaxX = polyBelow.getWidth();  
        
        String aboveTop = polyAbove.getRow3ABC(polyAbove.getHeight());
        String aboveBottom = polyAbove.getRow3ABC(1);
        String belowTop = polyBelow.getRow3ABC(polyBelow.getHeight());
        String belowBottom = polyBelow.getRow3ABC(1);
        
        if (polyAbove.intact()) {
            if (aboveMinX == 1 && aboveMaxX == 3) {
                abovePart = "w3_" + aboveTop + "_" + aboveBottom;
                if (polyAbove.testMirrorV())
                    abovePart += "_slr";
                if (polyAbove.testMirrorH())
                    abovePart += "_sud";
                if (polyAbove.testRotate180())
                    abovePart += "_srot";
                abovePart = substitute(abovePart);
                
            } else if (aboveMinX == aboveMaxX ) {
                abovePart = "w1_" + aboveTop + "_" + aboveBottom;
                if (aboveTop.equals("b"))
                    abovePart += "_slr";
                if (polyAbove.testMirrorV())
                    abovePart += "_slr";                
            } else {
                if (aboveMinX == 1) {
                    abovePart = "w2a_" + aboveTop + "_" + aboveBottom;
                } else {
                    abovePart = "w2b_" + aboveTop + "_" + aboveBottom;
                }
                if (polyAbove.testMirrorV())
                    abovePart += "_slr";     
            }
        } else {
            if (polyAbove.countX(2) == 0) {
                if (polyAbove.contains(1, polyAbove.getHeight()) && polyAbove.contains(3, polyAbove.getHeight())) {
                    abovePart = "2pu-abc-w1-w1_" + aboveTop + "_" + aboveBottom + "_slr";
                } else {
                    abovePart = "2pu-abc-w1-w1_" + aboveTop + "_" + aboveBottom ;
                }
            } else {
                if (polyAbove.isCol(1, 2, 1, 1)) {
                    if (polyAbove.beyondCol(1, 2, 1, 1)) {
                        abovePart = "2pu-abc-w1-w1w3_" + aboveTop + "_r"; // doubts
                    } else {
                        abovePart = "2pu-abc-w1-w1w2_" + aboveTop + "_r";
                    }
                } else {
                    if (polyAbove.beyondCol(3, 2, 1, 1)) {
                        abovePart = "2pu-abc-w1-w1w3_" + aboveTop + "_l"; // doubts
                    } else {
                        abovePart = "2pu-abc-w1-w1w2_" + aboveTop + "_l";
                    }                    
                }
            }
        }
        if (polyBelow.intact()) {
            if (belowMinX == 1 && belowMaxX == 3) {
                belowPart = "w3_" + belowTop + "_" + belowBottom;
                if (polyBelow.testMirrorV())
                    belowPart += "_slr";
                if (polyBelow.testMirrorH())
                    belowPart += "_sud";
                if (polyBelow.testRotate180())
                    belowPart += "_srot";
                belowPart = substitute(belowPart);
                
            } else if (belowMinX == belowMaxX ) {
                belowPart = "w1_" + belowTop + "_" + belowBottom;
                if (belowTop.equals("c"))
                    belowPart += "_slr";
                      
            } else {
                if (belowMinX == 1) {
                    belowPart = "w2a_" + belowTop + "_" + belowBottom;
                } else {
                    belowPart = "w2b_" + belowTop + "_" + belowBottom;
                }
                if (polyBelow.testMirrorV())
                    belowPart += "_slr";     
            }
        } else {
            if (polyBelow.countX(2) == 0) {
                if (polyBelow.contains(1, 1) && polyBelow.contains(3, 1)) {
                    belowPart = "2pd-abc-w1-w1_" + belowTop + "_" + belowBottom + "_slr";
                } else {
                    belowPart = "2pd-abc-w1-w1_" + belowTop + "_" + belowBottom ;
                }
            } else {
                if (polyBelow.isCol(1, 2, polyBelow.getHeight(), -1)) {
                    if (polyBelow.beyondCol(1, 2, polyBelow.getHeight(), -1)) {
                        belowPart = "2pd-abc-w1-w1w3_" + "r" + "_" + belowBottom; // doubts
                    } else {
                        belowPart = "2pd-abc-w1-w1w2_" + "r" + "_" + belowBottom;
                    }
                } else {
                    if (polyBelow.beyondCol(3, 2, polyBelow.getHeight(), -1)) {
                        belowPart = "2pd-abc-w1-w1w3_" + "l" + "_" + belowBottom; // doubts
                    } else {
                        belowPart = "2pd-abc-w1w2-w1_"  + "l" + "_" + belowBottom;
                    }                    
                }
            }
        }
        ret = "prod(" + abovePart + "(" + coeffAbove + "), " + belowPart + "(" + coeffBelow + "))/* case " + caseNumber + " */ : 1"; 
        return ret;
    }
    public String getW4Build() {
       
        equiv = new Hashtable<>();
       
                
        if (getWidth() != 4) {
            System.err.println("non width 4");
            System.exit(1);
        }
        int size = size();
        int accum = 0, sizeBelow = 0, sizeAbove;
        int half = size / 2;
        String row = "", coeffAbove, coeffBelow, ret , abovePart = "", belowPart = "", caseNumber = "";
        int yRow, lenRow = 0;
        for ( yRow = 1; ; yRow++) {
            accum += numberOnY4(yRow);
            if (accum > half)
                break;
            sizeBelow = accum;
            if (accum == half && (size % 2 == 0))
                break;
        }
        if (accum != half) {
            row = getRow4(yRow);
            lenRow = row.length();
        }
        //int restNotRow = size - lenRow;
        sizeAbove = size - sizeBelow - lenRow;
        coeffAbove = calcCoeff(sizeAbove, half);
        coeffBelow = calcCoeff(sizeBelow, half);
        Polyomino polyAbove;
        Polyomino polyBelow;
        if (lenRow == 0) {
            polyBelow = extractRows(1, yRow);
            polyAbove = extractRows(yRow + 1, getHeight());
        } else {
            polyBelow = extractRows(1, yRow - 1);
            polyAbove = extractRows(yRow + 1, getHeight());            
        }
        
        int aboveMinX = polyAbove.getMinX(), aboveMaxX = polyAbove.getWidth();
        int belowMinX = polyBelow.getMinX(), belowMaxX = polyBelow.getWidth();  
        
        String aboveTop = polyAbove.getRow4(polyAbove.getHeight());
        String aboveBottom = polyAbove.getRow4(1);
        String belowTop = polyBelow.getRow4(polyBelow.getHeight());
        String belowBottom = polyBelow.getRow4(1);
        
        if (polyAbove.intact()) {
            switch (aboveMaxX - aboveMinX + 1) {
                case 4:
                    abovePart = "w4_" + aboveTop + "_" + aboveBottom ;
                    break;
                case 3:
                    abovePart = "w3_" + aboveTop + "_" + aboveBottom ;
                    break;
                case 2:
                    abovePart = "w2_" + aboveTop + "_" + aboveBottom ;
                    break;
                case 1:
                    abovePart = "w1_" + aboveTop + "_" + aboveBottom ;
                    break;
                    
            }
            Polyomino polyAboveAligned = (Polyomino)(polyAbove.align());
            if (polyAboveAligned.testMirrorV())
                abovePart += "_slr";
            if (polyAboveAligned.testMirrorH())
                abovePart += "_sud";
            if (polyAboveAligned.testRotate180())
                abovePart += "_srot";
            
        } else {
            
            abovePart = "2pu_" + aboveTop + "_" + aboveBottom ;
                
        }
        if (polyBelow.intact()) {
            switch (belowMaxX - belowMinX + 1) {
                case 4:
                    belowPart = "w4_" + belowTop + "_" + belowBottom ;
                    break;
                case 3:
                    belowPart = "w3_" + belowTop + "_" + belowBottom ;
                    break;
                case 2:
                    belowPart = "w2_" + belowTop + "_" + belowBottom ;
                    break;
                case 1:
                    belowPart = "w1_" + belowTop + "_" + belowBottom ;
                    break;
                    
            }
        } else {
            belowPart = "2pd_" + belowTop + "_" + belowBottom ;
               
        }
        
        ret = "prod(" + abovePart + "(" + coeffAbove + "), " + belowPart + "(" + coeffBelow + ")) 1 (" + aboveBottom + "/" + row + "/" + belowTop + ")"; 
        return ret;
    }
    boolean beyondCol(int x, int otherX, int startY, int deltaY) {
        startY = endOfCol(x, otherX, startY, deltaY) + 2 * deltaY;
        for (int y = startY; y >= 1 && y <= getHeight(); y += deltaY) {
            if (contains(x, y))
                return true;
        }
        return false;
    }
    boolean isCol(int x, int otherX, int startY, int deltaY) {
        int y = endOfCol(x,  otherX,  startY,  deltaY);
        return y != 0;
    }
    int endOfCol(int x, int otherX, int startY, int deltaY) {
        for (int y = startY; y >= 1 && y <= getHeight(); y += deltaY) {
            if (contains(x, y)) {
                if (contains(otherX, y))
                    return 0;
            } else {
                return y - 1;
            }
        }
        int qq = 1 / 0;
        return getHeight();
    }
    public int countX(int x) {
        int ret = 0;
        for (int y = 1; y <= getHeight(); y++)
            if (contains(x, y))
                ret++;
        return ret;
    }
    public Polyomino extractRows(int startY, int endY) {
        Polyomino ret = new Polyomino();
        for (int y = startY; y <= endY; y++) {
            for (int x = 1; x <= getWidth(); x++) {
                if (contains(x, y)) {
                    ret.insert(x, y - startY + 1);
                }
            }
        }
        ret.seal();
        return ret;
    }
    String calcCoeff(int partial, int half) {
        if (partial > half) {
            int qq = 1 / 0;
        }
        if (partial == half)
            return "m";
        return "m - " + (half - partial);
    }
    int numberOnY3(int y) {
        int sum = 0;
        for (int x = 1; x <= 3; x++)
            if (contains(x, y))
                sum++;
        return sum;
    }
    int numberOnY4(int y) {
        int sum = 0;
        for (int x = 1; x <= 4; x++)
            if (contains(x, y))
                sum++;
        return sum;
    }
    String getRow3(int y) {
        String ret = "";
        if (contains(1, y))
            ret += "l";
        if (contains(2, y))
            ret += "c";
        if (contains(3, y))
            ret += "r";
        return ret;
    }
    String getRow3ABC(int y) {
        String ret = "";
        if (contains(1, y))
            ret += "a";
        if (contains(2, y))
            ret += "b";
        if (contains(3, y))
            ret += "c";
        return ret;
    }
    String getRow4(int y) {
        String ret = "";
        if (contains(1, y))
            ret += "a";
        if (contains(2, y))
            ret += "b";
        if (contains(3, y))
            ret += "c";
        if (contains(4, y))
            ret += "d";
        return ret;
    }
    private void add(ArrayList<Vertex> list, int x, int y) {
        Vertex v = new Vertex(x, y);
        /*
        if (!list.contains(v))
            list.add(v);*/
        for (Vertex vv : list) 
            if (vv.equals(v))
                return;
        list.add(v);
    }
    public String top() {
        return footprint4(maxY);
    }
    public String bottom() {
        return footprint4(1);
    }
    public String footprint4( int row) {
        String ret = "";
        String letters = " abcd";
        for (int i = 1 ; i <= 4; i++)
            if (this.contains(i, row))
                ret += letters.substring(i, i + 1);
        return ret;
    }    
    public String rowToLetters(int row) {
        String ret = "";
        String letters = " abcd";
        for (int i = 1 ; i <= getWidth(); i++)
            if (this.contains(i, row))
                ret += letters.substring(i, i + 1);
        return ret;
    }    
    private class Vertex { // could be CoordPair ?
        int x, y;
        Vertex(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean equals(Vertex other) {
            return x == other.x && y == other.y;
        }
    }
    public int empty() {
        int ret = getWidth() * getHeight() - size();
        return ret;
    }
    public int topHeight() {
        int ret = 0;
        for (int y = getHeight(); y >= 1; y--)
            if (completeRow(y))
                ret++;
            else
                break;
        return ret;
    }
    public boolean completeRow(int y) {
        for (int x = 1 ; x <= getWidth() ; x++)
            if (!contains(x, y))
                return false;
        return true;
    }
    /*
    public Polyomino extract(int bottom, int top) {
        Polyomino ret = new Polyomino();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (y >= bottom && y <= top)
                ret.insert(x, y - bottom + 1);
        }
        ret.seal();
        return ret;
    } */
    public Circle getMEC() {
        ArrayList<DCoord> extremes = getExtremeCoordinates();
        ArrayList<DCoord> all = getAllCoordinates();
        Circle mec = getMEC2(extremes, all);
        if (mec != null)
            return mec;
        return getMEC3(extremes, all);
    }
    Circle getMEC2(ArrayList<DCoord> extremes, ArrayList<DCoord> all) {
        int size = extremes.size();
        Circle ret = null;
        
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                DCoord c1 = extremes.get(i);
                DCoord c2 = extremes.get(j);
                Circle c = makeCircle(c1, c2);
                if (c.allInDisc(all)) {
                    if (ret == null)
                        ret = c;
                    else if (Comparison.lt(c.getRadius(), ret.getRadius()))
                        ret = c;
                }
                    
                
            }
        }
        return ret;
    }
    Circle makeCircle(DCoord d1, DCoord d2) {
        LineSegment s = new LineSegment(d1, d2);
        return new Circle(s.halfway(), s.length() / 2);
    }
    Circle getMEC3(ArrayList<DCoord> extremes, ArrayList<DCoord> all) {
        int size = extremes.size();
        Circle ret = null;
        for (int i = 0; i < size - 2; i++) {
            for (int j = i + 1; j < size - 1; j++) {
                for (int k = j + 1; k < size; k++) {
                    DCoord c1 = extremes.get(i);
                    DCoord c2 = extremes.get(j);
                    DCoord c3 = extremes.get(k);
                    //System.err.println(c1+ "/"+c2+"/"+c3);
                    Circle c = makeCircle(c1, c2, c3);
                    if (c.allInDisc(all)) {
                        if (ret == null)
                            ret = c;
                        else if (Comparison.lt(c.getRadius(), ret.getRadius()))
                            ret = c;
                    }
                
                }
            }
        }
        return ret;
    }
    Circle makeCircle(DCoord d1, DCoord d2, DCoord d3) {
        LineSegment s1 = new LineSegment(d1, d2);
        LineSegment s2 = new LineSegment(d1, d3);
        Formula f1 = new Formula(s1.bisector());
        Formula f2 = new Formula(s2.bisector());
        DCoord dc = f1.intersect(f2);
        if (dc == null) {
            System.err.println(this.drawing());
            this.getExtremeCoordinates();
            int dbd = 1;
            
        }
        return new Circle(dc, LineSegment.distance(d1, dc));
    }    
    DCoord debugCoord;
    public Circle getMECFailed() {
        //System.err.println(drawing());
        ArrayList<DCoord> extremes = getExtremeCoordinates();
        DCoord[] ta = this.furthestAway(extremes);
        LineSegment tls = new LineSegment(ta[0], ta[1]);
        DCoord tmph = tls.halfway();
        Circle ttmpc = new Circle(tmph, tls.length() / 2);
        if (this.testSolution(extremes, ttmpc))
            return ttmpc;
        DCoord centre = this.midPoint();
        DCoord furthest = findFurthest(extremes, centre);
        LineSegment lRadius = new LineSegment(furthest, centre);
        /*
                -
              -   -
              | c-| lRadius is a line segment from the circle (furthest point from centre) to the centre
              -   -
                -
        */
        double distNewCentreFromCentre = getReduction(lRadius, extremes);
        /*
        we can move the centre a bit towards said point on the circle so as to intersect some other point
        */
        centre = lRadius.lenghtenEndBy(-distNewCentreFromCentre); // find new centre
        
        double radius = lRadius.length() - distNewCentreFromCentre; // new radius
        Circle mec = new Circle(centre, radius); // first circle
        
        
        int count = 0;
        while (!testSolution(extremes, mec)) {
            count++;
            //System.err.println("start while " + mec.contains(extremes).size());
            //System.err.println("radius mec " + mec.getRadius());
            DCoord[] fa = furthestAway(mec.contains(extremes)); // find the two points (of the polyomino) that are on the circle
                                                        // and are the furthest apart
            DCoord m1 = fa[0], m2 = fa[1];    
            LineSegment chord = new LineSegment(m1, m2);    // draw a chord
           
            DCoord tc = chord.halfway();
            double tr = chord.length() / 2;
            Circle tmp = new Circle(tc, tr);
            if (testSolution(extremes, tmp)) {
                return tmp;
            }
           
        /*
            m1  -
              -|  -
              ||  | 
              -|  -
            m2  -
        */            
            LineSegment bisector = chord.bisector();
            
            //DCoord pointOnCircle = bisector.lenghtenStartBy(mec.getRadius() - bisector.length());
            //bisector = new LineSegment(pointOnCircle, centre);
            //System.err.println("length bisector " + bisector.length());
            Circle nmec = getNewCentre(bisector, mec, m1, extremes,  m2);
            // getNewCentre(LineSegment ls, Circle c, DCoord point, ArrayList<DCoord> list, DCoord except1)
//            if (Comparison.equals(mec.getRadius(), nmec.getRadius()))
//                break;
            mec = nmec;
           
            if (!mec.contains(m1)) {
                int qq = 1 / 0;
            }
            if (!mec.contains(m2)) {
                int qq = 1 / 0;
            }
            if (count == 8) {
                int ddd = 1;
            }
            if (count > 10) {
                int qq = 1 / 0;
            }
        }
        return mec;
    }
    /*
    we have a line going from a point on the circle to the centre.
    And so of length radius
    Certainly every point of the polyomino is withing radius of the centre.
    How far along the line can we move away from the centre, so that the new circle meets at least one other point?
    */
    double getReduction(LineSegment ls, ArrayList<DCoord> list) {
        Formula fRadius = new Formula(ls);
        //DCoord newCentre = centre;
        double distNewCentreFromCentre = 1000;
        for (DCoord other : list) {
            if (other == ls.getS())
                continue;
            if (fRadius.passesThrough(other)) {
                double a = ls.length(), b = LineSegment.distance(ls.getE(), other);
                double c = a - (a + b) / 2;
                if (c < distNewCentreFromCentre) {
                    distNewCentreFromCentre = c;
                    debugCoord = other;
                }
            } else {
                LineSegment t = new LineSegment(other, ls.getS());
                LineSegment o = t.bisector();
                Formula f1 = new Formula(o);
                DCoord d = f1.intersect(fRadius);
                double c = LineSegment.distance(ls.getE(), d);
                if (c < distNewCentreFromCentre) {
                    distNewCentreFromCentre = c;   
                    debugCoord = other;
                }
            }
        }   
        return distNewCentreFromCentre;
    }
    /*
    we have a line going from a point on the circle to the centre.
    And so of length radius
    Certainly every point of the polyomino is withing radius of the centre.
    How far along the line can we move away from the centre, so that the new circle meets at least one other point as well as point?
    */    
    Circle getNewCentre(LineSegment ls, Circle c, DCoord point, ArrayList<DCoord> list, DCoord except1) {
        Formula fRadius = new Formula(ls);
        //DCoord newCentre = centre;
        
        
        Circle newCircle = c;
        for (DCoord other : list) {
            if (other == except1 || other == point)
                continue;
            
            LineSegment t = new LineSegment(other, point);
            LineSegment o = t.bisector();
            Formula f1 = new Formula(o);
            DCoord d = f1.intersect(fRadius);
            Circle tmpCircle = new Circle(d, LineSegment.distance(d, point));
            if (!tmpCircle.allInDisc(list))
                continue;
            if (!Comparison.le(tmpCircle.getRadius(), newCircle.getRadius()))
                continue;

            debugCoord = other;
            //System.err.println("here");
            //System.err.println("m1-centre " + point.toString() + " " + LineSegment.distance(point, d) + " " + d);
            //System.err.println("m2-centre " + except1.toString() + " " + LineSegment.distance(except1, d) + " " + d);
            //System.err.println("m3-centre " + debugCoord.toString() + " " + LineSegment.distance(debugCoord, d) + " " + d);                    
            newCircle = tmpCircle;
            
        }   
        return newCircle;
    }
    DCoord[] furthestAway(ArrayList<DCoord> list) {
        if (list.size() < 2) {
            int qq = 1 / 0;
        }
            
        double max = 0;
        DCoord m1 = null, m2 = null;
        for (int i = 0; i < list.size() - 1; i++) {
            DCoord d1 = list.get(i);
            
            for (int j = i + 1; j < list.size(); j++) {
                DCoord d2 = list.get(j);

                double d = LineSegment.distance(d1, d2);
                if (d > max) {
                    m1 = d1;
                    m2 = d2;
                }

            }
        }
        DCoord[] ret = new DCoord[2];
        ret[0] = m1;
        ret[1] = m2;
        if (m1 == null) {
            int qq = 1  / 0;
        }
        return ret;
    }
    boolean testSolution(ArrayList<DCoord> list, Circle c) {
        if (!c.allInDisc(list))
            return false;
        ArrayList<DCoord> onCircle = c.contains(list);
        if (onCircle.size() < 2) {
            int qq = 1 / 0;
        }        
        DCoord[] f = furthestAway(onCircle);
        DCoord m1 = f[0], m2 = f[1];
        double max = LineSegment.distance(m1, m2);
        if (Comparison.equals(max, c.getDiameter()))
            return true;
        //if (list.size() == 2)
        //    return false;
        LineSegment chord = new LineSegment(m1, m2);
        for (DCoord other : onCircle) {
            
            if (other == m1 || other == m2)
                continue;
            if (chord.sameSide(other, c.getCentre()))
                return true;
        }
        return false;     
    }
    DCoord findFurthest(ArrayList<DCoord> list, DCoord point) {
        DCoord ret = null;
        double maximum = 0;
        for (DCoord c : list) {
            double d = LineSegment.distance(c, point);
            if (d > maximum) {
                ret = c;
                maximum = d;
            }
        }
        return ret;
    }
    ArrayList<DCoord> getAllCoordinates() {
        ArrayList<DCoord> ret = new ArrayList<>();
        for (CoordPair cp : array) {
            ret.add(topLeft(cp));
            ret.add(topRight(cp));
            ret.add(bottomLeft(cp));
            ret.add(bottomRight(cp));
            
            
        }
        return ret;
    }
    ArrayList<DCoord> getExtremeCoordinates() {
        CoordPair leftmostHigh = null, rightmostHigh = null, highestLeft = null, lowestLeft = null, 
                leftmostLow = null, rightmostLow = null, highestRight = null, lowestRight = null;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (x == 1) {
                if (highestLeft == null || y > highestLeft.getY())
                    highestLeft = cp;
                if (lowestLeft == null || y < lowestLeft.getY())
                    lowestLeft = cp;
                
            }
            if (y == 1) {
                if (rightmostLow == null || x > rightmostLow.getX())
                    rightmostLow = cp;
                if (leftmostLow == null || x < leftmostLow.getX())
                    leftmostLow = cp;
                
            }
            if (x == maxX) {
                if (highestRight == null || y > highestRight.getY())
                    highestRight = cp;
                if (lowestRight == null || y < lowestRight.getY())
                    lowestRight = cp;
                
            }
            if (y == maxY) {
                if (rightmostHigh == null || x > rightmostHigh.getX())
                    rightmostHigh = cp;
                if (leftmostHigh == null || x < leftmostHigh.getX())
                    leftmostHigh = cp;
                
            }            
        }
        ArrayList<DCoord> ret = new ArrayList<>();
        if (leftmostHigh == null)
            System.err.println(drawing());
        ret.add(topLeft(leftmostHigh));
        ret.add(topRight(rightmostHigh));
        if (!highestRight.equals(rightmostHigh))
            ret.add(topRight(highestRight));
        ret.add(bottomRight(lowestRight));
        if (!rightmostLow.equals(lowestRight))
            ret.add(bottomRight(rightmostLow));
        ret.add(bottomLeft(leftmostLow));
        if (!lowestLeft.equals(leftmostLow))
            ret.add(bottomLeft(lowestLeft));
        if (!highestLeft.equals(leftmostHigh))
            ret.add(topLeft(highestLeft));
        
        ArrayList<DCoord> tmp = new ArrayList<>();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (!contains(x - 1, y) && !contains(x, y - 1) && !cp.equals(leftmostLow) && !cp.equals(lowestLeft)) 
                tmp.add(bottomLeft(cp));
        }
        addAny(ret, tmp, topRight(rightmostHigh), bottomLeft(leftmostLow), bottomLeft(lowestLeft));
        
        tmp = new ArrayList<>();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (!contains(x + 1, y) && !contains(x, y - 1) && !cp.equals(rightmostLow) && !cp.equals(lowestRight)) 
                tmp.add(bottomRight(cp));
        }
        addAny(ret, tmp, topLeft(leftmostHigh), bottomRight(rightmostLow), bottomRight(lowestRight));
        
        tmp = new ArrayList<>();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (!contains(x - 1, y) && !contains(x, y + 1) && !cp.equals(leftmostHigh) && !cp.equals(highestLeft)) 
                tmp.add(topLeft(cp));
        }
        addAny(ret, tmp, bottomRight(rightmostLow), topLeft(leftmostHigh), topLeft(highestLeft));
        
        tmp = new ArrayList<>();
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (!contains(x + 1, y) && !contains(x, y + 1) && !cp.equals(rightmostHigh) && !cp.equals(highestRight)) 
                tmp.add(topRight(cp));
        }
        addAny(ret, tmp, bottomLeft(leftmostLow), topRight(rightmostHigh), topRight(highestRight));
        
        return ret;
        
    }
    void addAny(ArrayList<DCoord> ret, ArrayList<DCoord> tmp, DCoord opp, DCoord extreme1, DCoord extreme2) {
        tmp.add(extreme1);
        tmp.add(extreme2);
        if (tmp.size() <= 2)
            return;
        for (int i = 0; i < tmp.size(); i++) {
            addAny(i, ret,  tmp, opp,  extreme1,  extreme2);
        }
    }
    void addAny(int i, ArrayList<DCoord> ret, ArrayList<DCoord> tmp, DCoord opp, DCoord extreme1, DCoord extreme2) {
        DCoord cp = tmp.get(i);
        if (cp == extreme1 || cp == extreme2)
            return;
        double x = cp.getX(), y = cp.getY();
        for (int j = 0; j < tmp.size() - 1; j++) {
            if (j == i)
                continue;
            for (int k = j + 1; k < tmp.size(); k++) {
                if (k == i)
                    continue;
                DCoord cpe1 = tmp.get(j), cpe2 = tmp.get(k);
                if ((x - cpe1.getX()) * (x - cpe2.getX()) > 0) // suppose they are 3,2,4; then we have 2-3 * 2-4. Suppose 1,2,3 then 2-1 * 2-3
                    continue;
                LineSegment s = new LineSegment(cpe1, cpe2);
                if (s.totallyContains(cp))
                    return;
                if (s.sameSide(cp, opp))
                    return;
                
            }
        }
        ret.add(cp);
    }
    DCoord topLeft(CoordPair cp) {
        return new DCoord(cp.getX() - 1, cp.getY());
    }
    DCoord topRight(CoordPair cp) {
        return new DCoord(cp.getX() , cp.getY());
    }
    DCoord bottomLeft(CoordPair cp) {
        return new DCoord(cp.getX() - 1, cp.getY() - 1);
    }
    DCoord bottomRight(CoordPair cp) {
        return new DCoord(cp.getX() , cp.getY() - 1);
    }
    DCoord midPoint() {
        return new DCoord((double)maxX / 2, (double)maxY / 2);
    }
    public boolean fillsMEC() {
        return !canGrowInMEC();
    }
    public boolean canGrowInMEC() {
        Circle mec = this.getMEC();
        PolySet ps = genNew(0);
        for (int i = 0; i < ps.size(); i++) {
            BigInteger bi = new BigInteger(ps.gets(i));
            Polyomino p = new Polyomino(bi);
            if (!p.convex())
                continue;
            Circle t = p.getMEC();
            if (t == null) {
                System.err.println(p.drawing());
                t = p.getMEC();
            }
            if (Comparison.lt(t.getRadius()   , mec.getRadius())) {
                System.err.println(mec.getRadius() + drawing());
                System.err.println(t.getRadius() + p.drawing());
                return true;
            }
            if (Comparison.equals(mec.getRadius()   , t.getRadius()))
                return true;
        }
        return false;
    }
    /*
    Assumes convexity
    */
    public boolean minimallyRealLifeConcave() {
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            if (contains(x, y - 1) && !contains(x + 1, y) && contains(x + 1, y + 1) && contains(x + 2, y + 1))
                return false;
            if (contains(x, y + 1) && !contains(x + 1, y) && contains(x + 1, y - 1) && contains(x + 2, y - 1))
                return false;
            if (contains(x, y - 1) && !contains(x - 1, y) && contains(x - 1, y + 1) && contains(x - 2, y + 1))
                return false;
            if (contains(x, y + 1) && !contains(x - 1, y) && contains(x - 1, y - 1) && contains(x - 2, y - 1))
                return false;
            
        }
        return true;
    }
    public int internalPoints() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            
            if (y < 2)
                continue;
            if (contains(x + 1, y)  && contains(x , y - 1) && contains(x + 1, y - 1) )
                ret++;
        }
        return ret;
    }   
    public  int internalEdges() {
        int ret = 0;
        for (CoordPair cp : array) {
            int x = cp.getX(), y = cp.getY();
            
                ret += internalEdges(x - 1, y);
                ret += internalEdges(x + 1, y);
                ret += internalEdges(x , y + 1);                
                ret += internalEdges(x , y - 1);                
                
        }
        return ret / 2;
    }    
    public int findTransformation(Polyomino other) {
        ArrayList<Polyomino> list = listAllTransformations();
        for (int i = 0; i < list.size() ; i++)
            if (other.equals(list.get(i)))
                return i;
        System.err.println("cannot transform " + other.drawing() + " to " + this.drawing());
        return 1 / 0;
            
    }
    public Polyomino getTransformation(int n) {
        ArrayList<Polyomino> list = listAllTransformations();
        return list.get(n);
    }
    ArrayList<Polyomino> listAllTransformations() {
        ArrayList<Polyomino> ret = new ArrayList<>();
        Polyomino p = this;
        ret.addAll(p.listAllRotations());
        p = p.reflectH();
        ret.addAll(p.listAllRotations());
        for (Polyomino q : ret) {
            if (q.size() != size()) {
                int qq = 1 / 0;
            }
        }
        return ret;
    }
    ArrayList<Polyomino> listAllRotations() {
        ArrayList<Polyomino> ret = new ArrayList<>();
        Polyomino p = this;
        ret.add(p);
        p = p.rotate90();
        ret.add(p);
        p = p.rotate90();
        ret.add(p);
        p = p.rotate90();
        ret.add(p);
       
        return ret;
    }    
    public boolean isTCP() {
        
        outer: for (int x = getMinX() ; x <= getMaxX(); x++) {
            int state = 0;
            /* state 1 : found at least one cell
               state 2 : after cell, found space
            */
            for (int y = getMinY(); y <= getMaxY() ; y++) {
                if (contains(x, y)) {
                    switch (state) {
                        case 0: state = 1; break;
                        case 2: continue outer;
                    }
                } else {
                    switch (state) {
                        case 1: state = 2; break;
                    }                    
                }
            }
            return false;
        }
        outer: for (int y = getMinY() ; y <= getMaxY(); y++) {
            int state = 0;
            /* state 1 : found at least one cell
               state 2 : after cell, found space
            */
            for (int x = getMinX(); x <= getMaxX() ; x++) {
                if (contains(x, y)) {
                    switch (state) {
                        case 0: state = 1; break;
                        case 2: continue outer;
                    }
                } else {
                    switch (state) {
                        case 1: state = 2; break;
                    }                    
                }
            }
            return false;
        }        
        return true;
    }       
}
