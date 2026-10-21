/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.polygons;

/**
 *
 * @author mason
 */
public class Comparison {
    private static double nextToNothing = 0.00000001;
    public static boolean equals(double v1, double v2) {
        return Math.abs(v1 - v2) < nextToNothing;
    }
    public static boolean isZero(double v) {
        return Math.abs(v) < nextToNothing;
    }
    public static boolean le(double v1, double v2) {
        return v1 < v2 + nextToNothing;
    }
    public static boolean lt(double v1, double v2) {
        return v1 < v2 - nextToNothing;
    }
    
}
