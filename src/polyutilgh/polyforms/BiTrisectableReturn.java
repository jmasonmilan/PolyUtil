
package polyutilgh.polyforms;
import polyutilgh.*;
/**
 *
 * @author mason
 */
public class BiTrisectableReturn {
    private long count;
    PolySet ps;
    public BiTrisectableReturn() {
        ps = new PolySet();
    }
    public BiTrisectableReturn(boolean newWay) {
        ps = new PolySet(newWay);
    }
    public BiTrisectableReturn(PolySet ps) {
        this.ps = ps;
    }
    public void increment(int add) {
        count += add;
    }
    public PolySet getPS() {
        return ps;
    }
    public long getCount() {
        return count;
    }
}
