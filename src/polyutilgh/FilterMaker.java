/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh;

import java.lang.reflect.Constructor;

import polyutilgh.filters.Filter;


/**
 *
 * @author mason
 */
public class FilterMaker {
    public FilterMaker() {
        
    }
    public Filter make(String filter, Filter nextOneParam, String params) {
        Object object = null;
        try {
            Class<?> c = Class.forName("polyutilgh.filters." + filter + "Filter");
            Constructor<?> cons;
           
            cons = c.getConstructor(Filter.class, String.class);
            object = cons.newInstance(nextOneParam,  params); 
          
              
        } catch (Exception e) {
            System.err.println("Could not create instance of " + filter);
            System.exit(1);
        }
        Filter p = (Filter)object;   
        return p;
    }
}
