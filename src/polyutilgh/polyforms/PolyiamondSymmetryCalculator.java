/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;

import polyutilgh.CoordPair;

/**
 *
 * @author mason
 */
public class PolyiamondSymmetryCalculator {
    
    private int  verticalAxisLocationSixths, horizontalAxisLocationHalves;
    private String axisType;
    private int verticalAxisLocationC, horizontalAxisLocationC;
    private int verticalAxisLocationV, horizontalAxisLocationV;
    private int approxVerticalAxisLocation, approxHorizontalAxisLocation;
    private CoordPair approxCentre;
    public PolyiamondSymmetryCalculator(String axisType, int horizontalAxisLocationHalves, int  verticalAxisLocationSixths) {
        this.axisType = axisType;
        this.verticalAxisLocationSixths = verticalAxisLocationSixths;
        this.horizontalAxisLocationHalves = horizontalAxisLocationHalves;
        if (axisType.equals("V")) {
            horizontalAxisLocationV   =  div(horizontalAxisLocationHalves, 2) ;
            verticalAxisLocationV = div(verticalAxisLocationSixths + 600 , 6 ) - 100;                  
        } else if (axisType.equals("C")) {
            horizontalAxisLocationC   =  div(horizontalAxisLocationHalves, 2) ;
            verticalAxisLocationC = (600 + verticalAxisLocationSixths) / 6 - 100 + 1;                      
        } 
        approxHorizontalAxisLocation   =  horizontalAxisLocationHalves / 2 ;
        approxVerticalAxisLocation = (verticalAxisLocationSixths + 600)  / 6 - 100 + 1;    
        approxCentre = new CoordPair(approxHorizontalAxisLocation, approxVerticalAxisLocation);
    }
    public String getAxisType() {
        return axisType;
    }
    public CoordPair getApproxCentre() {
        return approxCentre;
    }
    public int getVerticalAxisLocationSixths() {
        return verticalAxisLocationSixths;
    }
    public int getHorizontalAxisLocationHalves() {
        return horizontalAxisLocationHalves;
    }
    public int getHorizontalAxisLocationC() {
        return horizontalAxisLocationC;
    }
    public int getVerticalAxisLocationC() {
        return verticalAxisLocationC;
    }
    private int div(int a, int b) {
        if (a % b == 0) {
            return a / b;
        }
        return 1 / 0;
    }   
    public CoordPair rotate180(CoordPair cp) {
       
            
        int x = cp.getX(), y = cp.getY();
        return rotate180(x, y);
    }
   
    public CoordPair rotate180(int x, int y) {
       
        if (!axisType.equals("M") && !axisType.equals("V"))         {
            int qq = 1 / 0;
        }     
        
        int ny = (2 * this.verticalAxisLocationSixths - 6 * y) / 6 + 1;
        if (verticalAxisLocationSixths % 6 == 0) {
            int nx = (2 * horizontalAxisLocationHalves - 2 * x) / 2 ;
            
            return new CoordPair(nx, ny);
        } else {
            int nx = (2 * horizontalAxisLocationHalves - 2 * x) / 2 ;
            
            return new CoordPair(nx, ny);
        }
    }
    public CoordPair rotate120anticlockwise(CoordPair cp) {
        
        
        if (axisType.equals("V"))         {
            return rotate120anticlockwiseV(cp);
        }
        if (axisType.equals("C"))         {
            return rotate120anticlockwiseC(cp);
        }
        
        
        int qq = 1 / 0;
        return null;
    }
    public CoordPair rotate120clockwise(CoordPair cp) {
        
        
        return rotate120anticlockwise(rotate120anticlockwise(cp));
    }
    public CoordPair rotate120anticlockwiseV(CoordPair cp) {
        return this.rotate60anticlockwise(this.rotate60anticlockwise(cp));
      
    }
    public CoordPair rotate120anticlockwiseC(CoordPair cp) {
        
        
        if (!axisType.equals("C"))         {
            int qq = 1 / 0;
        }
        
        
        if ((horizontalAxisLocationC + verticalAxisLocationC) % 2 == 0)
            return rotate120anticlockwiseCaboutUpPointingCentre(cp);
        else 
            return rotate120anticlockwiseCaboutDownPointingCentre(cp);
    }
    public CoordPair rotate60anticlockwise(CoordPair cp) {
        int x = cp.getX(), y = cp.getY();
        return rotate60anticlockwise(x, y);
    }
    public CoordPair rotate60anticlockwise(int x , int y) {
        
        
        if (!axisType.equals("V"))         {
            int qq = 1 / 0;
        }
       
       
        
        x = x - horizontalAxisLocationV + 3;        
        y = y - verticalAxisLocationV + 1;
        // now x,y are relative to centre at tip of 1,1
        int nx, ny;
        int preX , preY , postX , postY ;
        if ((x + y) % 2 != 0) { // (x + y) % 2 != 0  cp.parity() != 0
            if (x % 2 != 0) { // odd
                 preX = 0;
                 preY = 1;
                 postX = 1;
                 postY = 0;       
                
            } else { // even 
                 preX = -1;
                 preY = 0;
                 postX = 0;
                 postY = 1;       
            }
        } else { // up
            if (x % 2 != 0) { // odd 
                 preX = 0;
                 preY = 0;
                 postX = 0;
                 postY = 0;                                  
            } else { // even 
                 preX = -1;
                 preY = 1;
                 postX = 2;
                 postY = 0;      
            }
        }
        int a = (x + preX - 3) / 2;
        nx = -3 * (y + preY + 1) / 2 + 7 + a + postX;  
        ny = (y + preY + 1) / 2 +  a + postY; 
        
        nx = nx + horizontalAxisLocationV - 3;
        ny = ny + verticalAxisLocationV - 1;
        return new CoordPair(nx, ny);
        
    }
   
   
    CoordPair rotate120anticlockwiseCaboutUpPointingCentre(CoordPair cp) {
        
        
        if (!axisType.equals("C"))         {
            int qq = 1 / 0;
        }
        int x = cp.getX(), y = cp.getY();
        
        x = x - horizontalAxisLocationC + 1;
        
        y = y - verticalAxisLocationC + 1;
        // now x,y are relative to centre at 1,1
        int nx, ny;
        if ((x + y) % 2 != 0) {
            if (x % 2 != 0) { // odd
                int a = (x - 1) / 2;
                nx = -3 * y / 2 + 2 - a;
                ny = -y / 2 + 1 + a;
                
            } else {
                int a = (x - 2) / 2;
                nx = -3 * (y - 1) / 2  - a;
                ny = -(y - 1) / 2 + 1 + a;                
            }
        } else {
            if (x % 2 != 0) { // odd
                int a = (x - 1) / 2;
                nx = -3 * (y - 1) / 2 + 1 - a;
                ny = -(y - 1) / 2 + 1 + a;                
            } else {
                int a = (x - 2) / 2;
                nx = -3 * y / 2 + 2 - a;
                ny = -y / 2 + 2 + a;                
            }
        }
        nx = nx + horizontalAxisLocationC - 1;
        ny = ny + verticalAxisLocationC - 1;
        return new CoordPair(nx, ny);
    }
    CoordPair rotate120anticlockwiseCaboutDownPointingCentre(CoordPair cp) {
        
        
        if (!axisType.equals("C"))         {
            int qq = 1 / 0;
        }
        int x = cp.getX(), y = cp.getY();
       
        x = x - horizontalAxisLocationC + 2;        
        y = y - verticalAxisLocationC + 1;
        // now x,y are relative to centre at 2,1
        int nx, ny;
        int preX = 0, preY = 0, postX = 0, postY = 0;
        if ((x + y) % 2 != 0) {
            if (x % 2 != 0) { // odd
                 preX = -1;
                 preY = 0;
                 postX = -1;
                 postY = 0;       
                
            } else { // even done
                 preX = 0;
                 preY = -1;
                 postX = -2;
                 postY = -1;       
            }
        } else { // up
            if (x % 2 != 0) { // odd done
                 preX = -1;
                 preY = 1;
                 postX = 1;
                 postY = 1;                                  
            } else { // even done
                // noop
            }
        }
        int a = (x + preX - 2) / 2;
        nx = -3 * (y + preY) / 2 + 4 - a + postX;  
        ny = -(y + preY) / 2 + 2 + a + postY; 
        
        nx = nx + horizontalAxisLocationC - 2;
        ny = ny + verticalAxisLocationC - 1;
        return new CoordPair(nx, ny);
    }    
    public CoordPair mirrorV(CoordPair cp) {
        int x = cp.getX() * 2, y = cp.getY();
        int nx = (2 * this.horizontalAxisLocationHalves - x) / 2;
        return new CoordPair(nx, y);
    }
    public CoordPair mirrorH(CoordPair cp) {
        int x = cp.getX() , y = cp.getY() * 6;
        int ny = ((2 * this.verticalAxisLocationSixths - y) / 6) + 1;
        return new CoordPair(x, ny);
    }
    
}
