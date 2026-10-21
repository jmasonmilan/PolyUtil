/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh;
import polyutilgh.polyforms.*;
/**
 *
 * @author mason
 * Input is a loop.
 * The class finds the next and previous squares of each square facilitating navigation
 */
public class Chain {
    CoordPair[] previousArray, nextArray;
    int size;
    PolyformWC loop;
    public Chain(PolyominoR loop) {
        if (!loop.isALoop()) {
            int qq = 1 / 0;
        }
        this.loop = loop;
        size = loop.size();
        previousArray = new CoordPair[size];
        nextArray = new CoordPair[size];
        int h = loop.getHeight();
        int x = loop.leftmostTop();
        
        int current = loop.locate(x, h);
        int next = loop.locate(x + 1, h);
        int previous = loop.locate(x, h - 1);
        nextArray[previous] = loop.getCoords(current);
        previousArray[current] = loop.getCoords(previous);
        nextArray[current] = loop.getCoords(next);
        previousArray[next] = loop.getCoords(current);
        while (true) {
            int cx = loop.getCoords(next).getX(), cy = loop.getCoords(next).getY();
            previous = current;
            current = next;
            next = loop.locate(cx + 1,  cy);
            if (next == -1 || next == previous) {
                next = loop.locate(cx,  cy + 1);
                if (next == -1 || next == previous) {
                    next = loop.locate(cx - 1,  cy);
                    if (next == -1 || next == previous) {
                        next = loop.locate(cx,  cy - 1);
                    }
                }
            }
            nextArray[current] =  loop.getCoords(next);
            previousArray[next] =  loop.getCoords(current);
            if (nextArray[next] != null)
                break;
        }        
    }
    public Chain(PolyhexLoop loop) {
        
        this.loop = loop;
        size = loop.size();
        previousArray = new CoordPair[size];
        nextArray = new CoordPair[size];
        int h = loop.getHeight();
        int x = loop.leftmostTop();
        
        int current = loop.locate(x, h);
        int next = loop.locate(x + 1, h - 1);
        int previous = loop.locate(x - 1, h - 1);
        nextArray[previous] = loop.getCoords(current);
        previousArray[current] = loop.getCoords(previous);
        nextArray[current] = loop.getCoords(next);
        previousArray[next] = loop.getCoords(current);
        while (true) {
            int cx = loop.getCoords(next).getX(), cy = loop.getCoords(next).getY();
            previous = current;
            current = next;
            next = loop.locate(cx + 1,  cy + 1);
            if (next == -1 || next == previous) {
                next = loop.locate(cx + 1,  cy - 1);
                if (next == -1 || next == previous) {
                    next = loop.locate(cx - 1,  cy + 1);
                    if (next == -1 || next == previous) {
                        next = loop.locate(cx - 1,  cy - 1);
                        if (next == -1 || next == previous) {
                            next = loop.locate(cx ,  cy + 2);
                            if (next == -1 || next == previous) {
                                next = loop.locate(cx ,  cy - 2);
                            }
                        }
                    }
                }
            }
            nextArray[current] =  loop.getCoords(next);
            previousArray[next] =  loop.getCoords(current);
            if (nextArray[next] != null)
                break;
        }        
    }
    public CoordPair getNext(int i) {
        return nextArray[i];
    }
    public CoordPair getNext(CoordPair cp) {
        return nextArray[loop.locate(cp)];
    }
    public CoordPair getPrevious(int i) {
        return previousArray[i];
    }
    public CoordPair getPrevious(CoordPair cp) {
        return previousArray[loop.locate(cp)];
    }
    
}
