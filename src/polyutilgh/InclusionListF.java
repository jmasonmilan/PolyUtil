package polyutilgh;
import java.util.*;
import polyutilgh.polyforms.*;
/**
 *
 * @author mason
 * used in more recent counting filters; lists possible new squares.
 * offers exclusion from list
 * may have a position with respect to the origin distinct from associated polyomino
 */
public class InclusionListF {
    public int[][] coordArray;
    //public int[] valueArray;
    int board[][];
    int baseX , baseY ;
    boolean debug;
    public InclusionListF(int board[][], int baseX , int baseY ) {
       
        this.board = board;
        coordArray = new int[1024][2];
        //valueArray = new int[128];
        this.baseX = baseX;
        this.baseY = baseY;
        
    }
    public void setDebug() {
        debug = true;
    }
    public int initialise(InclusionList list) {
        int ret = 0;
        for (int i = 0 ; i < list.size(); i++) {
            ret = this.add(list.get(i).getX(), list.get(i).getY(), ret) ;
        }
        return ret;
    }    
    public int initialise(ArrayList<CoordPair> list) {
        int ret = 0;
        for (int i = 0 ; i < list.size(); i++) {
            if (debug)
                System.err.println("init " + list.get(i).getX() + " " +  list.get(i).getY());
            ret = this.add(list.get(i).getX(), list.get(i).getY(), ret) ;
        }
        return ret;
    }    
    public int add(int x, int y, int currSize) {
        
             
        this.coordArray[currSize][0] = x;
        this.coordArray[currSize][1] = y;
        if (debug) 
            System.err.println("add " + x + " " + y + " -1" );
        board[baseX + x][baseY + y] = -1;
        
        return currSize + 1;
        
    }
    public int addDummy(int currSize) {
        
             
        this.coordArray[currSize][0] = 0;
        this.coordArray[currSize][1] = 0;
        //System.err.println("add " + x + " " + y + " -1" );
       
        
        return currSize + 1;
        
    }
    /*
    public int add(int x, int y, int value, int currSize) {
        
             
        this.coordArray[currSize][0] = x;
        this.coordArray[currSize][1] = y;
        this.valueArray[currSize] = value;
        //System.err.println("add " + x + " " + y + " -1" );
        board[baseX + x][baseY + y] = -1;
        
        return currSize + 1;
        
    }
    */
   
    
    
    public int getX(int i) {
        
        return coordArray[i][0];
    }
    public int getY(int i) {
        
        return coordArray[i][1];
    }
    /*
    public int getValue(int i) {
        
        return valueArray[i];
    }
    */
    
    
   /*
    public boolean contains(int x, int y, int currSize) {
        
        for (int i = 0 ; i < currSize; i++)
            if (coordArray[i][0] == x && coordArray[i][1] == y)
                return true;
        return false;
    }
*/
    public int indexOf(int x, int y, int currSize) {
      
        for (int i = 0 ; i <   currSize; i++)
            if (coordArray[i][0] == x && coordArray[i][1] == y)
                return i;
        return -1;
    }
    public boolean containsInRange(int x, int y, int from, int to) {
      
        for (int i = from ; i <   to; i++)
            if (coordArray[i][0] == x && coordArray[i][1] == y)
                return true;
        return false;
    }
    
   
    public String toString(int currSize) {
        String ret = // history + 
                "";
        for (int i = 0; i < currSize ; i++) {
            
            ret += coordArray[i][0] + "/" + coordArray[i][1] + " ";
        }
        return ret;
    }
    public void verifDupl(int size) {
        /*
        for (int i = 0; i < size - 1; i++) 
            for (int j = i + 1; j < size; j++) {
                if (coordArray[i][0] == coordArray[j][0] && coordArray[i][1] == coordArray[j][1]) {
                    int qq = 1 / 0;
                }
            }
*/
        
    }
    public InclusionListF sort(int start, int end) {
        InclusionListF newIncl = new InclusionListF(board, baseX, baseY);
        int[] tmpx, tmpy, tmpc;
                //tmpx = new int[end];
                //tmpy = new int[end];   
                tmpc = new int[end];         
        for (int i = 0; i < end ; i++) {
            newIncl.coordArray[i][0] = this.coordArray[i][0];
            newIncl.coordArray[i][1] = this.coordArray[i][1];
            
        }
            boolean flag = true;
           for (int k = start; k < end - 1; k++) {
               tmpc[k] = countNear(coordArray[k][0], coordArray[k][1]);
           }
            while (flag) {
                flag = false;
                for (int k = start; k < end - 1; k++) {
                    if (tmpc[k] < tmpc[k + 1]) {
                        int tmpx1 = coordArray[k][0];
                        int tmpy1 = coordArray[k][1];
                        int tmpc1 = tmpc[k];
                        coordArray[k][0] = coordArray[k + 1][0];
                        coordArray[k][1] = coordArray[k + 1][1];
                        tmpc[k] = tmpc[k + 1];
                        coordArray[k + 1][0] = tmpx1;
                        coordArray[k + 1][1] = tmpy1;
                        tmpc[k + 1] = tmpc1;
                        flag = true;
                    }
                }  
            }        
        return newIncl;
    }
    int countNear(int x, int y) {
        int ret = 0;
        ret += countNear(x, y, 1, 0);
        ret += countNear(x, y, -1, 0);        
        ret += countNear(x, y, 0, 1);
        ret += countNear(x, y, 0, -1);
        return ret;
    }
   
    int countNear(int x, int y, int dx, int dy) {
        if (content(x + dx,  y + dy) >= 1)
            return 1;
        else
            return 0;
    }        
    int content( int x, int y) {
        
        
        return board[baseX + x][baseY + y];
            
    }      
}
