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
