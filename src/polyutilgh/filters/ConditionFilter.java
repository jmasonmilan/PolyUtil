package polyutilgh.filters;
import polyutilgh.polyforms.Polyomino;
import polyutilgh.polyforms.PolyformWC3;
import polyutilgh.polyforms.BasePolyomino;
import polyutilgh.polyforms.*;
import polyutilgh.polyforms.SAPolygon;
import polyutilgh.polyforms.Polyform;
import java.io.*;
import java.math.BigInteger;
import polyutilgh.PolySet;
import polyutilgh.*;
import java.util.*;
/**
 *
 * @author jmason
 * note that condition:strip=true will select strips
 * condition:strip=false will select non-strips
 * 
 * Test various conditions on an input polyform
 */
public class ConditionFilter extends OnlyPolyFilter {
    String condition;
    PolySet ps ;
    ArrayList<String> whiteList, blackList;
    ParameterAnalyzer pa;
    boolean both;
    public ConditionFilter(Filter nextOneParam, String condParam) {
        super(nextOneParam);
        String[] rules = new String[] {"unique", "board", "both", "strip", "pile", "square", "evenwidth", "blunt", "fillsmec", "kissing", "para", 
            "segregated", "simplyconnected", "intact", "loopborder", "minvertices", "armfull", "fertility", "even", "perfecthalo",
            "sym", "sym3", "convex", "selfavoiding", "staircase", "wellconnected", "smooth", "treelike", "same", "hexletter", "symcube", 
            "unbiased", "maxbiased", "minperimeter", "almostsquare", "perimeter", "convextreelike", "loop", "letter", "w2category",  "w3category", 
            "w4category", "w3categoryh", "textreme", "borderlength", "braceletlength","bracelet", "smallholes", "minimallyreallifeconcave",
            "distancefromstrip", "width", "height", "encloses", "hexagons", "adjacencies", "surrounded", "hexadjacencies", "typek", "tcp", "noz", "noy"};
        pa = new ParameterAnalyzer(condParam , rules);  

        this.condition = condParam;
        
        if (pa.getBooleanValue("unique"))
            ps = new PolySet();
        else if (pa.getStringValue("sym") != null) {
            parseSym(pa.getStringValue("sym"));
        }        else if (pa.getStringValue("sym3") != null) {
            parseSym(pa.getStringValue("sym3"));
            
        }       else if (pa.getStringValue("symcube") != null) {
            parseSym(pa.getStringValue("symcube"));
            
        }
        both = pa.getBooleanValue("both", false);
    }

    public boolean write(long n, Polyform inPoly) {
        Boolean tp;
        
        tp = pa.getTrooleanValue("board");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.board() == tp.booleanValue()) {
                if (both) {
                    if (poly.rotate90().board())
                        return innerWrite(count++, poly);
                } else
                    return innerWrite(count++, poly);
            }
            return true;       
        }
        
        tp = pa.getTrooleanValue("tcp");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.isTCP() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("noz");
        if (tp != null) {             
            Polyominoid poly = (Polyominoid)inPoly;
            if (poly.noz() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("noy");
        if (tp != null) {             
            Polyominoid poly = (Polyominoid)inPoly;
            if (poly.noy() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("minimallyreallifeconcave");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.minimallyRealLifeConcave() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("almostsquare");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.almostSquare() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("textreme");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.tExtreme() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("kissing");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.kissing() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("even");
        if (tp != null) {             
            Polyiamond poly = (Polyiamond)inPoly;
            if (poly.even() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("perfecthalo");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.hasPerfectHalo() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        
        
        tp = pa.getTrooleanValue("fillsmec");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.fillsMEC() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        tp = pa.getTrooleanValue("armfull");
        if (tp != null) {             
            PolyformWC poly = (PolyformWC)inPoly;
            if (poly.armfull() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        tp = pa.getTrooleanValue("smallholes");
        if (tp != null) {             
            PolyformWC poly = (PolyformWC)inPoly;
            if (poly.onlySmallHoles() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        tp = pa.getTrooleanValue("blunt");
        if (tp != null) {             
            Polytan poly = (Polytan)inPoly;
            if (poly.blunt() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        tp = pa.getTrooleanValue("convex");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.convex() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("convextreelike");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.convexTreelike() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }    
        
        int distance = pa.getIntValue("distancefromstrip", 0);
        if (distance != 0) {      
            Polyomino poly = (Polyomino)inPoly;
            Polyomino straightOne = new Polyomino();
            for (int i = 1 ; i <= poly.size(); i++)
                straightOne.insert(1, i);
            straightOne.seal();                        
            if (poly.getDistance(straightOne) == distance)
                return innerWrite(count++, poly);
            return true;       
        }    
        
        int hexagons = pa.getIntValue("hexagons", 0);
        if (hexagons != 0) {      
            
                                   
            if (inPoly.countHexagons() == hexagons)
                return innerWrite(count++, inPoly);
            return true;       
        }  
        
        int borderLength = pa.getIntValue("borderlength", 0);
        if (borderLength != 0) {      
            
            PolyformWC pwc = (PolyformWC)inPoly;    
            String s = pwc.calcBorder().toString();
            if (s.length() == borderLength)
                return innerWrite(count++, inPoly);
            return true;       
        }    
        
        int para = pa.getIntValue("para", -1);
        if (para >= 0) {      
            
            Polyominoid pwc = (Polyominoid)inPoly;    
            int cp = pwc.paraCount();
            if (para == cp)
                return innerWrite(count++, inPoly);
            return true;       
        }   
        
        int typeK = pa.getIntValue("typek", 0);
        if (typeK != 0) {      
            
            Polycube pwc = (Polycube)inPoly;    
            
            if (pwc.getTypeK() == typeK)
                return innerWrite(count++, inPoly);
            return true;       
        }    
        int braceletLength = pa.getIntValue("braceletlength", 0);
        if (braceletLength != 0) {      
            
            Polyomino pwc = (Polyomino)inPoly;    
            SAPolygon sap = new SAPolygon(new SAPolygon(pwc.calcBorder()).getUnique());
            String s = sap.getShape();
            if (s.length() == braceletLength)
                return innerWrite(count++, inPoly);
            return true;       
        }    
        
        String bracelet = pa.getStringValue("bracelet", false);
        if (bracelet != null) {      
            
            Polyomino pwc = (Polyomino)inPoly;    
            SAPolygon sap = new SAPolygon(new SAPolygon(pwc.calcBorder()).getUnique());
            String s = sap.getShape();
            if (s.equals(bracelet))
                return innerWrite(count++, inPoly);
            return true;       
        }    
        
        
        
        if (pa.getStringValue("surrounded") != null) {      
            
            int surrounded = pa.getIntValue("surrounded", 0);                       
            if (inPoly.surroundedTriangles() == surrounded)
                return innerWrite(count++, inPoly);
            return true;       
        }    
        
        int adjacencies = pa.getIntValue("adjacencies", 0);
        if (adjacencies != 0) {      
            
                                   
            if (inPoly.countAdjacencies() == adjacencies)
                return innerWrite(count++, inPoly);
            return true;       
        }    
        
        int hexAdjacencies = pa.getIntValue("hexadjacencies", 0);
        if (hexAdjacencies != 0) {      
            
                                   
            if (inPoly.maxHexagonAdjacencies() == hexAdjacencies)
                return innerWrite(count++, inPoly);
            return true;       
        }    
        
        tp = pa.getTrooleanValue("evenwidth");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if ((poly.getWidth() % 2 == 0) == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        tp = pa.getTrooleanValue("ferrers");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.ferrers() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }    
        
        tp = pa.getTrooleanValue("intact");
        if (tp != null) {             
            PolyformWC poly = (PolyformWC)inPoly;
            if (poly.intact() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("loopborder");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.loopBorder() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        tp = pa.getTrooleanValue("loop");
        if (tp != null) {             
            PolyformWC poly = (PolyformWC)inPoly;
            if (poly.loop() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }    
        
        tp = pa.getTrooleanValue("maxbiased");
        if (tp != null) {             
            PolyformWC poly = (PolyformWC)inPoly;
            if (poly.maxbiased() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }     
        
        tp = pa.getTrooleanValue("minvertices");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.countVertices() == poly.minVertices())
                return innerWrite(count++, poly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("same");
        if (tp != null) {             
            TruncatedSquarePolyform poly = (TruncatedSquarePolyform)inPoly;
            if (poly.same() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("minperimeter");
        if (tp != null) {             
            WCCommon poly = (WCCommon)inPoly;
            if (poly.minimalPerimeter() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }    
        
        if (pa.getStringValue("perimeter") != null) { 
            BasePolyomino poly = (BasePolyomino)inPoly;
            if (poly.perimeterOrSurface() == pa.getIntValue("perimeter", 0))                 
                return innerWrite(count++, inPoly);
            return true;       
        }
        
        if (pa.getStringValue("encloses") != null) { 
            Polyomino poly = (Polyomino)inPoly;
            if (poly.freeEncloses(new Polyomino(pa.getStringValue("encloses"))))                 
                return innerWrite(count++, inPoly);
            return true;       
        }
        
        if (pa.getStringValue("width") != null && pa.getStringValue("height") != null) { 
            BasePolyomino poly = (BasePolyomino)inPoly;
            int reqW = pa.getIntValue("width", 0);
            int reqH = pa.getIntValue("height", 0);
            int currW = poly.getWidth();
            int currH = poly.getHeight();
            if ((currW >= reqW && currH >= reqH) || (currW >= reqH && currH >= reqW))                 
                return innerWrite(count++, inPoly);
            return true;       
        }
        
        if (pa.getStringValue("fertility") != null) { 
            BasePolyomino poly = (BasePolyomino)inPoly;
            if (poly.stripFertility() == pa.getIntValue("fertility", 0))                 
                return innerWrite(count++, inPoly);
            return true;       
        }
        
        if (pa.getStringValue("letter") != null) { 
            BasePolyomino poly = (BasePolyomino)inPoly;
            String found = poly.getCategoryLetter();
            String seeking = pa.getStringValue("letter");
            if (found.equals(seeking))          
                return innerWrite(count++, inPoly);
            return true;       
        }
        if (pa.getStringValue("hexletter") != null) { 
            PolyformWC3 poly = (PolyformWC3)inPoly;
            String found = poly.getCategoryLetter();
            String seeking = pa.getStringValue("hexletter");
            if (found.equals(seeking))          
                return innerWrite(count++, inPoly);
            return true;       
        }
        if (pa.getStringValue("w2category") != null) { 
            BasePolyomino poly = (BasePolyomino)inPoly;
            if (poly.getW2Category().equals(pa.getStringValue("w2category")) )         
                return innerWrite(count++, inPoly);
            return true;       
        }
        if (pa.getStringValue("w3category") != null) { 
            BasePolyomino poly = (BasePolyomino)inPoly;
            if (poly.getW3CategoryABC().equals(pa.getStringValue("w3category")) )         
                return innerWrite(count++, inPoly);
            return true;       
        }
        if (pa.getStringValue("w3categoryh") != null) { 
            BasePolyomino poly = (BasePolyomino)inPoly;
            if (poly.getW3CategoryABCh().equals(pa.getStringValue("w3categoryh")) )         
                return innerWrite(count++, inPoly);
            return true;       
        }
        if (pa.getStringValue("w4category") != null) { 
            BasePolyomino poly = (BasePolyomino)inPoly;
            if (poly.getW4Category().equals(pa.getStringValue("w4category")) )         
                return innerWrite(count++, inPoly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("pile");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.pile() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        tp = pa.getTrooleanValue("segregated");
        if (tp != null) {             
            SAPolygon poly = (SAPolygon)inPoly;
            if (poly.segregated() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("smooth");
        if (tp != null) {             
            BasePolyomino poly = (BasePolyomino)inPoly;
            if (poly.smooth() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("selfavoiding");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.selfavoiding() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }    
        
        tp = pa.getTrooleanValue("simplyconnected");
        if (tp != null) {             
            PolyformWC poly = (PolyformWC)inPoly;
            if (poly.simplyConnected() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("square");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if ((poly.getHeight() == poly.getWidth()) == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        tp = pa.getTrooleanValue("staircase");
        if (tp != null) {             
            Polyomino poly = (Polyomino)inPoly;
            if (poly.staircase() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }    
        
        tp = pa.getTrooleanValue("strip");
        if (tp != null) {             
            WCCommon poly = (WCCommon)inPoly;
            if (poly.strip() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        if (pa.getStringValue("sym") != null) { 
            Polyomino poly = (Polyomino)inPoly;
            if (testSym(poly))       
                return innerWrite(count++, poly);
            return true;       
        }
        
        if (pa.getStringValue("symcube") != null) { 
            Polycube poly = (Polycube)inPoly;
            if (testSymCube(poly))       
                return innerWrite(count++, poly);
            return true;       
        }
        
        if (pa.getStringValue("sym3") != null) { 
            PolyformWC3 poly = (PolyformWC3)inPoly;
            if (testSym3(poly))       
                return innerWrite(count++, poly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("treelike");
        if (tp != null) {             
            WCCommon poly = (WCCommon)inPoly;
            if (poly.treelike() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }   
        
        tp = pa.getTrooleanValue("unbiased");
        if (tp != null) {             
            PolyformWC poly = (PolyformWC)inPoly;
            if (poly.unbiased() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }    
        
        if (pa.getBooleanValue("unique")) { 
            //Polyomino poly = (Polyomino)inPoly;
            if (ps.adds(inPoly.getRep().toString()))       
                return innerWrite(count++, inPoly);
            return true;       
        }
        
        tp = pa.getTrooleanValue("wellconnected");
        if (tp != null) {             
            PolyformWC poly = (PolyformWC)inPoly;
            if (poly.wellconnected() == tp.booleanValue())
                return innerWrite(count++, poly);
            return true;       
        }
        
        
        int i = 1 / 0;  
        return true;
    }  
    public void close() {
        
    }      
    private void parseSym(String list) {
        whiteList = new ArrayList<String>();
        blackList = new ArrayList<String>();
        while (list.length() > 0) {
            int bitLength = findEnd(list);
            if (list.startsWith("+"))
                whiteList.add(list.substring(1, bitLength));
            else if (list.startsWith("-"))
                blackList.add(list.substring(1, bitLength));
            else {
                int i = 1 / 0;
            }
            list = list.substring(bitLength );
        }
    }
    int findEnd(String list) {
        for (int i = 1; i < list.length(); i++) {
            if (list.substring(i, i + 1).equals("+") || list.substring(i, i + 1).equals("-"))
                return i;
        }
        return list.length();
    }
    /*
    sym=+A+B means A or B
    sym=+A sym=+B means A and B
    sym=-A-B means not A or not B
    sym=-A sym=-B means not A and not B
    */
    private boolean testSym(Polyomino poly) {
        return testSym(whiteList, poly, true) && testSym(blackList, poly, false);
    }
    private boolean testSym(ArrayList<String> list, Polyomino poly, boolean listType) {
        if (list.size() == 0)
            return true;
        for (int i = 0 ; i < list.size(); i++) {
            if (testSym(list.get(i), poly))
                return listType;
        }
        return !listType;
    }
    private boolean testSym(String symmetry, Polyomino poly) {
        if (symmetry.equals("M90"))
            return poly.testMirror90() ;
        else if (symmetry.equals("M90A"))
            return poly.testMirror90A();
        else if (symmetry.equals("M45"))
            return poly.testMirror45();
        else if (symmetry.equals("MH"))
            return poly.testMirrorH() ;
        else if (symmetry.equals("MV"))
            return poly.testMirrorV(); 
        else if (symmetry.equals("MSENW"))
            return poly.testMirrorSENW() ;
        else if (symmetry.equals("MSWNE"))
            return poly.testMirrorSWNE() ;
        else if (symmetry.equals("R90"))
            return poly.testRotate90() ;
        else if (symmetry.equals("R180"))
            return poly.testRotate180() ;
        else if (symmetry.equals("R90V"))
            return poly.testRotate90Vertex() ;
        else if (symmetry.equals("R180C"))
            return poly.testRotate180Centre() ;
        else if (symmetry.equals("R180ME"))
            return poly.testRotate180MidEdge() ;
        System.err.println("unknown sym " + symmetry);
        System.exit(1);
        return true;
        
    }        
    private boolean testSymCube(Polycube poly) {
        PolycubeSymmetryTester pst = new PolycubeSymmetryTester(poly);
        int sym = pst.getSymmetry();
        return testSymCube(whiteList, sym);
    }
    private boolean testSymCube(ArrayList<String> list, int sym) {
        if (list.size() == 0)
            return true;
        for (int i = 0 ; i < list.size(); i++) {
            if (testSymCube(list.get(i), sym))
                return true;
        }
        return false;
    }
    private boolean testSymCube(String symmetry, int sym) {
        if (symmetry.equals(sym + ""))
            return true;
        if (symmetry.equals(PolycubeSymmetryTester.getName(sym)))
            return true;
        return false;
        
    }        
    private boolean testSym3(PolyformWC3 poly) {
        return testSym3(whiteList, poly, true) && testSym3(blackList, poly, false);
    }
    private boolean testSym3(ArrayList<String> list, PolyformWC3 poly, boolean listType) {
        if (list.size() == 0)
            return true;
        for (int i = 0 ; i < list.size(); i++) {
            if (testSym3(list.get(i), poly))
                return listType;
        }
        return !listType;
    }
    private boolean testSym3(String symmetry, PolyformWC3 poly) {
        if (symmetry.equals("M90"))
            return poly.testMV() ;
        else if (symmetry.equals("M60"))
            return poly.testM60();
        else if (symmetry.equals("M30"))
            return poly.testM30();
        else if (symmetry.equals("M0"))
            return poly.testMH() ;
        else if (symmetry.equals("Mminus30"))
            return poly.testMminus30(); 
        else if (symmetry.equals("Mminus60"))
            return poly.testMminus60() ;
        else if (symmetry.equals("MA"))
            return poly.testMA() ;
        else if (symmetry.equals("MU"))
            return poly.testMU() ;
        else if (symmetry.equals("MC0"))
            return poly.testM() == 0;
        else if (symmetry.equals("MC1"))
            return poly.testM() == 1;
        else if (symmetry.equals("MC2"))
            return poly.testM() == 2;
        else if (symmetry.equals("MC3"))
            return poly.testM() == 3;
        else if (symmetry.equals("MC6"))
            return poly.testM() == 6;
        
        else if (symmetry.equals("R60"))
            return poly.testR60() ;
        else if (symmetry.equals("R120"))
            return poly.testR120() ;
        else if (symmetry.equals("R180"))
            return poly.testR180() ;

        System.err.println("bad symmetry " + symmetry);
        System.exit(1);
        return true;
        
    }          
        
    
}
