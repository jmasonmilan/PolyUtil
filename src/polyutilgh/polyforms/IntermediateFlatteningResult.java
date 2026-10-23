package polyutilgh.polyforms;

/**
 *
 * @author mason
 */
public class IntermediateFlatteningResult {
    public final static int OK = 0,  OVERLAP = 5, CANNOTDO = -1;
    int error;
    Polyominoid result;
    public IntermediateFlatteningResult(int error) {
        this.error = error;
        result = null;
    }
    public IntermediateFlatteningResult(Polyominoid result) {
        this.result = result;
        error = 0;
    }
}
