package polyutilgh.filters;
import polyutilgh.polyforms.*;

import polyutilgh.ParameterAnalyzer;
/**
 *
 * @author jmason
 * passes polyominoes satisfying size restrictions
 */
public class OnlyFilter extends OnlyPolyFilter {
    int from, to, white;
    boolean perimeter, noStop;
    ParameterAnalyzer pa;
    public OnlyFilter(Filter nextOneParam, String params) {
        super(nextOneParam);
        String[] rules = new String[] {"from", "to", "perimeter", "nostop", "white"};
        pa = new ParameterAnalyzer(params , rules);  
        to = pa.getIntValue("to", 0);
        from = pa.getIntValue("from");
        white = pa.getIntValue("white", -1);
        perimeter = pa.getBooleanValue("perimeter");
        noStop = pa.getBooleanValue("nostop"); 
        
    }

    public boolean write(long n, Polyform inPoly) {
        //PolyformWC poly = (PolyformWC) inPoly;
        if (perimeter) {
            int value = ((PolyformWC)(inPoly)).perimeterOrSurface();
            if (value >= from && value <= to)
                return innerWrite(count++, inPoly);
            return true;
        }
        if (white >= 0 && inPoly.size() > 1) {
            PartiallyTransparentPolyomino ptp = (PartiallyTransparentPolyomino) inPoly;
            int pBlack = ptp.getBlackSquares();
            if (pBlack == 0) {
                int qq = 1 / 0;
            }
            int pWhite = ptp.getWhiteSquares();
            if (pBlack + pWhite != ptp.size()) {
                int qq = 1 / 0;
            }
            if (pWhite != white)
                return inPoly.logicalSize() <= to;
        }
        if (inPoly.logicalSize() >= from && inPoly.logicalSize() <= to)
            return innerWrite(count++, inPoly);
        if (noStop)
            return true;
        return inPoly.logicalSize() <= to;
          
    }     
    public void close() {
        
    }           
  
}

    
 
