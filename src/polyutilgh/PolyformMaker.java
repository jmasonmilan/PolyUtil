/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import polyutilgh.polyforms.Polyform;

/**
 *
 * @author mason
 */
public class PolyformMaker {
    public PolyformMaker() {
        
    }
    public Polyform make(String form, BigInteger rep) {
        Object object = null;
        try {
            Class<?> c = Class.forName("polyutil.polyforms." + form);
            Constructor<?> cons;
           
            cons = c.getConstructor(BigInteger.class);
            object = cons.newInstance(rep); 
          
              
        } catch (Exception e) {
            System.err.println("Could not create instance of " + form);
            System.exit(1);
        }
        Polyform p = (Polyform)object;   
        return p;
    }
}
