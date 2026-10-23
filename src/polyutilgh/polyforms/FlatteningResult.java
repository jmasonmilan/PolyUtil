package polyutilgh.polyforms;

/**
 *
 * @author mason
 */
public class FlatteningResult {
    public final static int OK = 0, CONTAINSLOOP = 1, THRREFACESMEETATEDGE = 2, THREEFACESMEETATCORNER = 4, OVERLAP = 5, CANNOTDO = -1;
    int error;
    Polyomino result;
    public FlatteningResult(int error) {
        this.error = error;
        result = null;
    }
    public FlatteningResult(Polyomino result) {
        this.result = result;
        error = 0;
    }
}
