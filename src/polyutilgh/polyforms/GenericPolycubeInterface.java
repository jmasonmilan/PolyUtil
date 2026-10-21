/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;
import polyutilgh.CoordTriplet;
/**
 *
 * @author mason
 */
public interface GenericPolycubeInterface {
    public CoordTriplet getCoords(int i);
    public int getX(int i);
    public int getY(int i);
    public int getZ(int i);
    public int getCoord(int i, int c);
    public boolean contains(CoordTriplet cp);
    public boolean contains(int x, int y, int z);
    public int getMin(int c);
    public int getMax(int c);
    public int size();
}
