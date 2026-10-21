/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
