/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;
import java.util.*;
import java.math.BigInteger;
/**
 *
 * @author mason
 */
public class GraphNode {
    boolean[] connectedNodes;
    public int displayX, displayY, fromX, fromY;
    public boolean coordsFixed, sonsScanned;
    public String letter;
    ConnectedGraph g;
    public GraphNode(ConnectedGraph g, BigInteger in, int from, int s) {
        connectedNodes = new boolean[s];
        for (int i = 0; i < s; i++)
            connectedNodes[i] = getBit(in, from + i, s);
        this.g = g;
    }
    public GraphNode(ConnectedGraph g, int size) {
        connectedNodes = new boolean[size];
        this.g = g;
    }
    public GraphNode grow(ConnectedGraph g) {
        GraphNode ret = new GraphNode(g, connectedNodes.length + 1);
        for (int i = 0; i < connectedNodes.length; i++)
            ret.connectedNodes[i] = connectedNodes[i];
        return ret;
    }
    boolean getBit(BigInteger in, int pos, int s) {
        int len = in.bitLength();
        //if (pos < s * s - len)
        //    return false;
        boolean ret = in.testBit(s * s - pos - 1);
        return ret;
    }
    public boolean get(int i) {
        return connectedNodes[i];
    }
    public void set(int i) {
        connectedNodes[i] = true;
    }
    public String toString() {
        String ret = "";
        for (boolean b : this.connectedNodes)
            if (b)
                ret += "1";
            else
                ret += "0";
        return ret;
    }
    public int getOrder() {
        int ret = 0;
        for (boolean b : connectedNodes)
            if (b)
                ret++;
        return ret;
    }
    public void setCoordsOfSons() {
        int o = getOrder();
        if (displayX == 0 && displayY == 0) {
            if (!setCoords(0, 1, 0))
                return;
            if (!setCoords(1, -1, 0))
                return;
            if (!setCoords(2, 0, 1))
                return;
            if (!setCoords(3, 0, -1))
                return;
            if (!setCoords(4, 1, 1))
                return;
            if (!setCoords(5, 1, -1))
                return;
            if (!setCoords(6, -1, 1))
                return;
            if (!setCoords(7, -1, -1))
                return;
            int qq = 1 / 0; // too many
        } else {
            int dx = displayX - fromX, dy = displayY - fromY;
            if (!setCoords(0, dx + 1, dy + 2))
                return;
            if (!setCoords(1, dy, dx))
                return;
            if (!setCoords(2, -dy, -dx))
                return;
            if (!setCoords(3, dx, dy + 1))
                return;              
            if (!setCoords(4, dx, dy - 2))
                return;
            if (!setCoords(5, dx + 1, dy))
                return;
            if (!setCoords(6, dx - 1, dy))
                return;
            
            int qq2 = 1 / 0; // too many            
        }
    }
    boolean setCoords(int index, int dx, int dy) {
        int pos = findNth(index);
        if (pos < 0)
            return false;
        GraphNode node = g.array.get(pos);
        if (node.coordsFixed)
            return true;
        node.displayX = displayX + dx;
        node.displayY = displayY + dy;
        node.fromX = displayX;
        node.fromY = displayY;
        System.err.println("Setting " + index + " from " + displayX + "," + displayY + " : " + node.displayX + "," + node.displayY
        + " (" + dx + "," + dy + ")");
        node.coordsFixed = true;
        return true;
    }
    int findNth(int index) {
        for (int i = 0; i < connectedNodes.length; i++)
            if (connectedNodes[i] == true) {
                if (index == 0)
                    return i;
                index--;
            }
        return -1;
    }
    int findNth(int[] reverse, int index) {
        for (int i = 0; i < connectedNodes.length; i++)
            if (connectedNodes[reverse[i]] == true) {
                if (index == 0)
                    return reverse[i];
                index--;
            }
        return -1;
    }
    public boolean sameCoords(GraphNode o) {
        return displayX == o.displayX && displayY == o.displayY;
    }
}
