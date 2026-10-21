/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh;

import java.lang.reflect.Constructor;

import polyutilgh.filters.Filter;
import polyutilgh.readers.InReader;


/**
 *
 * @author mason
 */
public class ReaderMaker {
    public ReaderMaker() {
        
    }
    public InReader make(String reader, Filter nextOneParam, String params) {
        Object object = null;
        try {
            Class<?> c = Class.forName("polyutilgh.readers." + reader + "Reader");
            Constructor<?> cons;
           
            cons = c.getConstructor(Filter.class, String.class);
            object = cons.newInstance(nextOneParam,  params); 
          
              
        } catch (Exception e) {
            System.err.println("Could not create instance of " + reader);
            System.exit(1);
        }
        InReader p = (InReader)object;   
        return p;
    }
}
