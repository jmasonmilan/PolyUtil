/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polyforms;

/**
 *
 * @author mason
 */
public abstract class WCCommon extends Polyform {
    public abstract boolean strip();
    public abstract boolean treelike();
    public abstract int perimeterOrSurface();
    public abstract int minPerimeterOrSurface();
    public boolean minimalPerimeter() {
        return this.perimeterOrSurface() == this.minPerimeterOrSurface();
    }
    public WCCommon getPointed(int i) {
        return null;
    }   
    public boolean testAsym() {
        int qq = 1 / 0;
        return false;
    }
    protected Integer pointedCell;
    public void setPointedCell(int i) {
        pointedCell = new Integer(i);
    }
}
