/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh.functions;
import java.math.BigInteger;
import java.util.*;
import polyutilgh.Runner;
/**
 *
 * @author mason
 */
public abstract class Term {
    public String comment;
    public static int termCount ;
    public abstract BigInteger value(int n) throws FunctionError;
    public abstract BigInteger value(int n, int h) throws FunctionError ;
    public abstract String dump(int n) throws FunctionError ;
    public Term() {
        
    }
    public  ArrayList<GenericFunction> getDependentFunctions(boolean goDeeper) {
        return new ArrayList<GenericFunction>();
    }
    public String expression() {
        return toString();
    }
    public String expressionH() {
        return toStringH();
    }
    public  String toStringH() {
       
        Runner.notImplemented(this.getClass().getSimpleName() + ".toStringH");
        return null;
    }
    public  String toStringH(String n, String h) {
       
        Runner.notImplemented(this.getClass().getSimpleName() + ".toStringH");
        return null;
    }
    //public void addToDependencies(ArrayList<Term> list) { }
}
