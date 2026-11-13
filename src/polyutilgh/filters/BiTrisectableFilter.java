package polyutilgh.filters;
import polyutilgh.polyforms.*;
import java.math.*;
import polyutilgh.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
/**
 *
 * @author jmason
 * reads input polyforms (polyominoes, polyiamonds, polyhexes and polycubes) and outputs new versions composed
 * of 2 or more copies
 * 
 * Command line example
 * 
 * StdinToRep RepToForm:form=Polyomino Only:from=1,to=10 BiTrisectable:num=2 Sort RepToForm:form=Polyomino UnorderedCounter Stdout various\free1-14.txt 
 *
 * Parameters:
 * 
 * num=n - the number of copies to be made, e.g. 2 or 3
 * new=true - will work for all supported polyforms (but without the performance improvements
 * for polyominoes described below)
 * new=false - only polyominoes and polycubes
 * unbuildable - only for polyominoes - will output polyominoes of size 2n that cannot be built from
 * 2 equally sized polyominoes
 * 
 * Only for new=false, polyominoes and num = 2 (use usefixed=true for num>2):
 * 
 * usedfixed=true/false (default false)
 * sym=true/false (default false)
 * asym=true/false (default false)
 * optim=true/false (default false)
 * optimfile=xxx\yyy.txt (obligatory if optim=true)
 * 
 * 
 * usefixed=true: The program takes in input each polyomino and places it on the lattice.
 * Then it builds each possible corresponding fixed polyomino (of which there are 8 for an
 * input asymmetrical polyomino, ...).
 * Then it tries to position each fixed polyomino in all possible positions adjacent to the original.
 * It outputs all of the results in a canonical format (A246521) to be sorted and have duplicates removed, 
 * and then counted.
 * 
 * usefixed=false: The program optimises the positioning of the oriented polyominoes
 * 
 * sym=true/asym=true (useful if optim=true): the program assumes that the input contains
 * only symmetric or asymmetric polyominoes and so avoids testing.
 * 
 * optim=true: The program considers the specific case of asymmetrical input polyominoes; if we
 * add an orientation of the original polyomino to the original one such that there is only 1 edge in common,
 * we can be sure that this polyomino will be produced only from this input polyomino.
 * Therefore we can count these as we produce them and avoid outputting them. 
 * Note that outputting a polyomino in canonical format is costly and 
 * so runtime is reduced if we can avoid it.
 * 
 * optimfile=file path name: The program will write here the counts of singly connected 
 * polyominoes that are not output due to optim=true.
 * 
 * Important note:
 * For how the program works, the optim algorithm will tend to count most orientations twice,
 * and so the optimfile counts need dividing by 2.
 * To make this work precisely, some adjustment must be made according to the orientation.
 * In the diagrams below, the symbol O represents a square of the original polyomino, the symbol C
 * for a square of the copy.
 * 1. Default: for a default orientation, the program will count the same combined polyomino twice.
 * 
 * O  C   O
 * OOOC   OOO
 *    CC   C
 *         C
 *         CC
 * 
 * 2. The added polyomino is a 180 degree rotation of the original: the program will count each combination
 * just once, and so we must add 2 to the count in the optimfile.
 *    
 * O CCC
 * OOO C
 * 
 * 3. The added polyomino is a horizontal or vertical mirror image of the original: the program will 
 * count each combination twice, EXCEPT for the aligned combination which will be counted just once.
 * These specific combinations must be detected so as to add 2 to the optimfile.
 * 
 *   C
 * CCCO         O
 *    OOO      COOO
 *           CCC
 * 
 * But:
 *   CO
 * CCCOOO
 * 
 * Example use (may take 8 hours):
java -jar "PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=15 biTrisectable:num=2,optim=false,usefixed=false,sym=true stdout:nolineno=true various\sym1-15.txt > various\bitrisects.txt
java -jar "PolyUtilGH.jar" stdinToRep repToForm:form=Polyomino only:from=1,to=15 biTrisectable:num=2,optim=true,usefixed=true,optimfile=various\bitritmp.txt,asym=true stdout:nolineno=true various\asym1-15.txt > various\bitrisecta.txt

type  various\bitrisecta.txt various\bitrisects.txt | sort /o various\bitrisectu.txt
java  -jar "PolyUtilGH.jar" stdinToRep:nolineno=true eliminateOrderedDuplicates repToForm:form=Polyomino UnorderedCounter everyInterval:interval=2 stdout various\bitrisectu.txt > various\bitritmp2.txt
java  -jar "PolyUtilGH.jar" stdinToRep divide:divisor=2 add:file=various\bitritmp2.txt stdout various\bitritmp.txt
 */
public class BiTrisectableFilter extends OnlyPolyFilter {
    int number;
    boolean optim, useFixed, sym, asym, newFlag, unbuildable, globalHashFlag, ambiguous, 
            debug, orient;
    String optimFile;
    ParameterAnalyzer pa;
    long counts[];
    int max;
    Hashtable<String, Polyomino>[] free;
    Hashtable<String, Polyomino>[] impossible;
    ArrayList<Polyomino>[] fixed;
    Hashtable<String, WCCommon> globalHash, globalHash2;
    Color[] colourTable = {Color.RED, Color.ORANGE, Color.BLUE};
    public BiTrisectableFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"num", "optim", "optimfile", "usefixed", "asym", "sym", "new",
            "unbuildable", "globalhash", "ambiguous", "orient", "debug"};
        pa = new ParameterAnalyzer(params , rules);  
        number = pa.getIntValue("num");
        optim = pa.getBooleanValue("optim", false);
        sym = pa.getBooleanValue("sym", false);
        asym = pa.getBooleanValue("asym", false);
        newFlag = pa.getBooleanValue("new", false);
        useFixed = pa.getBooleanValue("usefixed", false);
        optimFile = pa.getStringValue("optimfile", optim);
        unbuildable = pa.getBooleanValue("unbuildable", false);
        globalHashFlag = pa.getBooleanValue("globalhash", false);
        ambiguous = pa.getBooleanValue("ambiguous", false);
        orient = pa.getBooleanValue("orient", false);
        debug = pa.getBooleanValue("debug", false);
        counts = new long[1000];
        // useFixed = false optim = true FAILS
        if (useFixed == false && optim == true) {
            //System.err.println("illegal parameter combination: useFixed = false optim = true");
            //System.exit(1);
        }
        if (newFlag == true && optim == true) {
            System.err.println("illegal parameter combination: newFlag = true optim = true");
            System.exit(1);
        }
        if (unbuildable) {
            free = new Hashtable[100];
            impossible = new Hashtable[100];
            fixed = new ArrayList[50];
            
        }
        if (globalHashFlag) {
            globalHash = new Hashtable<>();
            globalHash2 = new Hashtable<>();
        }
    }

    public boolean write(long n, Polyform inPoly) {
        WCCommon poly = (WCCommon) inPoly;
        
        //    return innerWrite(count++, inPoly);
        int size = poly.size();
        if (size > max)
            max = size;
        if (unbuildable) {
            Polyomino p = (Polyomino)poly;
            if (free[size] == null)
                free[size] = new Hashtable<>();
            String s = p.getRep().toString();
            free[size].put(s, p);
            if (size % 2 == 0) {
                if (impossible[size] == null)
                    impossible[size] = new Hashtable<>();
                impossible[size].put(s, p);
            }
            return true;
        }
        if (useFixed)
            ((Polyomino)poly).setUseFixed();
        if (sym)
            ((Polyomino)poly).setSym();
        if (asym)
            ((Polyomino)poly).setAsym();
        if (debug)
            ((Polyomino)poly).setDebug();
        BiTrisectableReturn btr = null;
        if (newFlag)
            btr = getBiTrisect(number, poly);
        else
            btr = inPoly.getBiTrisect(number, optim);
        PolySet ps = btr.getPS();
        if (!globalHashFlag) {
            // orible
            if (newFlag) {
                for (String k : ps.getNewHash().keySet()) {
                    innerWrite(count++, new BigInteger(k));
                }
            } else
                for (int i = 0; i < ps.size(); i++) {
                    String s = ps.gets(i);
                    //innerWrite(count++, new BigInteger(s));
                    innerWrite(count++, s);
                }
        }
        counts[size] += btr.getCount();
        return true;
          
    }    
    public BiTrisectableReturn getBiTrisect(int number, WCCommon p) {
        int depth = 0;
        BiTrisectableReturn btr = new BiTrisectableReturn(true);
        PolySet ps = btr.getPS();
        ArrayList<WCCommon> fixed = p.getFixedList();
        for (WCCommon f : fixed)
            getBiTrisect(p, ps, f, 0);
        
        for (int n = 3; n <= number; n++) {
            depth++;
            PolySet nps = new PolySet(true);
           
            for (String k : ps.getNewHash().keySet()) {
                WCCommon pn = (WCCommon)(ps.getNewHash().get(k));
                for (WCCommon f : fixed)
                    getBiTrisect(pn, nps, f, depth);
            }
            btr = new BiTrisectableReturn(nps);
        }
        
        return btr;
    }
    void getBiTrisect(WCCommon p, PolySet ps, WCCommon f, int depth) {
        ArrayList<Coord> first = new ArrayList<>();
        for (int j = 0; j < p.size(); j++) {
            Coord cp = p.getC(j);
            for (int i = 0; i < p.getNumberOfAdjacentCells(); i++)
                getBiTrisect(p, ps, cp, i, f, first,  depth);
        }
    }
   
    void getBiTrisect(WCCommon p, PolySet ps, Coord cp, int i, WCCommon f, ArrayList<Coord> first, int depth) {
        Coord ncp = p.getNewCoord(cp, i);
        if (p.contains(ncp))
            return;
        
        getBiTrisect(p, ps, ncp, f, first,  depth);
        
        
    }
    void getBiTrisect(WCCommon p, PolySet ps, Coord ncp, WCCommon f, ArrayList<Coord> first, int depth) {
        for (int i = 0; i < f.size(); i++)
            getBiTrisect(p, ps, ncp, f, f.getC(i), first,  depth);
        
    }
    void getBiTrisect(WCCommon p, PolySet ps, Coord ncp, WCCommon f, Coord fscp, ArrayList<Coord> first, int depth) {
        if (!p.occupyable(ncp, fscp))
            return;
        WCCommon np = (WCCommon)(p.copy());
        for (int i = 0; i < f.size(); i++) {
            Coord fcp = f.getC(i);
            
            Coord nncp = ncp.add(fcp).subtract(fscp); 
            if (i == 0) {
                if (first.contains(nncp))
                    return;
                first.add(nncp);
            }
            if (np.contains(nncp))
                return;
            nncp.setDisplayColour(colourTable[depth]); // this effort is only useful if globalHash is used
            np.insert(nncp);
        }
        WCCommon nnp = np.align(true);
        
        boolean added = ps.addUniques(nnp);
        if (added && globalHashFlag && depth == number - 2) {
            String s = nnp.getUnique();
            if (globalHash.get(s) == null) {
                globalHash.put(s, nnp);
                if (ambiguous) {
                    /*
                    noop
                    */
                }
                else
                    innerWrite(count++, nnp);
            } else if (ambiguous) {
                if (globalHash2.get(s) == null) {
                    globalHash2.put(s, nnp);
                    WCCommon o = globalHash.get(s);
                    innerWrite(count++, o);
                    if (orient) {
                        ArrayList<WCCommon> list = nnp.getFixedList();
                        boolean flag = false;
                        for (WCCommon w : list) {
                            if (w.getRep().equals(o.getRep())) {
                                innerWrite(count++, w);
                                flag = true;
                                break;
                            }
                            
                        }
                        if (!flag) {
                            System.err.println(o.drawing());
                            System.err.println(nnp.drawing());
                            for (WCCommon q : list) {
                                System.err.println(q.drawing());
                            }
                            int qq = 1 / 0;
                        }
                    } else
                        innerWrite(count++, nnp);
                }
            }
        }
    }
    public void close() {
        if (optim) {
            try {
                FileWriter fw = new FileWriter(optimFile);
                for (int i = 1; i <= max; i++) {
                    long c = counts[i];
                    
                    if (optim && !useFixed)
                        c = div(c, 2);

                    fw.write(i + " " + counts[i] + "\r\n");
                }
                fw.close();
            } catch(IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        if (unbuildable) {
            System.err.println("Unbuildable phase 2");
            int limit = max / 2;
            for (int i = 1; i <= limit; i++) {
                for (String s : free[i].keySet()) {
                    ArrayList<WCCommon> f = free[i].get(s).getFixedList();
                    for (WCCommon w : f) {
                        Polyomino p = (Polyomino)w;
                        if (fixed[i] == null)
                            fixed[i] = new ArrayList<>();
                        fixed[i].add(p);
                    }
                }
            }
            System.err.println("Unbuildable phase 3");
            for (int i = 1; i <= limit; i++) {
                for (String s : free[i].keySet()) {
                    Polyomino p = free[i].get(s);
                    BiTrisectableReturn btr = new BiTrisectableReturn();
                    PolySet ps = btr.getPS();
                    
                    for (WCCommon f : fixed[p.size()])
                        getBiTrisect(p, ps, f, 0);
                    for (int j = 0; j < ps.size(); j++) {
                        Hashtable<String, Polyomino> h = impossible[p.size() * 2];
                        h.remove(ps.gets(j));
                    }
                }
                // innerWrite(count++, new BigInteger(s));
                for (String s : impossible[2 * i].keySet()) {
                    Polyomino p = impossible[2 * i].get(s);
                    innerWrite(count++, p);
                }
            }
        }
    }           
    long div(long a, int b) {
        if (a % b != 0) {
            int qq = 1  / 0;
        }
        return a / b;
    }
}

    
 
