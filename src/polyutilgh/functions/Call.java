package polyutilgh.functions;
import java.math.BigInteger;
import java.util.ArrayList;
/**
 *
 * @author mason
 */
public class Call extends Term {
    Function f;
    int divisor, subtractor, preDecrement;
    public Call(Function f, int divisor, int subtractor) {
        this.f = f;
        this.divisor = divisor;
        this.subtractor = subtractor;
    }
    public Call(Function f, int preDecrement, int divisor, int subtractor) {
        this.f = f;
        this.divisor = divisor;
        this.subtractor = subtractor;
        this.preDecrement = preDecrement;
    }
    public BigInteger value(int n) throws FunctionError {
        return f.value(((n - preDecrement) / divisor) - subtractor);
    }
    public BigInteger value(int n, int h) throws FunctionError  {
        return f.value(((n - preDecrement) / divisor) - subtractor, h);
    }
    public String  toString() {
        String ret = f.getName();
        String n = "n";
        if (preDecrement > 0)
            n = "(n - " + preDecrement + ")";
        if (divisor == 1 && subtractor == 0)
            ret +=  "(" + n + ")";
        else if (divisor == 1) 
            ret += "((" + n + ") - " + subtractor + ")";
        else if (subtractor == 0)
            ret += "((" + n + ") / " + divisor + ")";
        else
            ret += "(((" + n + ") / " + divisor + ") - " + subtractor + ")";
        if (comment == null)
            return ret;
        return ret + " (" + comment + ")";
    }
    public String  toStringH() {
        return toStringH("n", "h");
    }
    public String  toStringH(String n , String h) {
        String ret = f.getName();
        
        if (preDecrement > 0)
            n = "(n - " + preDecrement + ")";
        if (divisor == 1 && subtractor == 0)
            ret +=  "(" + n + ", " + h + ")";
        else if (divisor == 1) 
            ret += "((" + n + ") - " + subtractor + ", " + h + ")";
        else if (subtractor == 0)
            ret += "((" + n + ") / " + divisor + ", " + h + ")";
        else
            ret += "(((" + n + ") / " + divisor + ") - " + subtractor + ", " + h + ")";
        return ret;
    }
    public String dump(int n) {
        String coeff = "m";
        if (subtractor > 0)
            coeff += " - " + subtractor;
        return f.getName() + "(" + coeff + ")" + comment;
    }
    public  ArrayList<GenericFunction> getDependentFunctions(boolean goDeeper) {
        ArrayList<GenericFunction> ret = new ArrayList<>();
        f.addToDependencies(ret);
        return ret;
    }       
}
