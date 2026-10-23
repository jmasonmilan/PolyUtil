package polyutilgh.functions;

import java.util.ArrayList;

/**
 *
 * @author mason
 */
public abstract class Function extends Term {
    String name;
    boolean hasBeenDefined;
    Function(String inName) {
        super();
        name = inName;
    }
    public String getName() {
        return name;
    }  
    public void addToDependencies(ArrayList<GenericFunction> list) { }
    public Call call(int divisor, int subtractor) {
        return new Call(this, divisor, subtractor);
    }
}
