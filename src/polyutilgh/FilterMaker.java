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
        String first = filter.substring(0, 1);
        /*
        if (!first.equals(first.toUpperCase())) {
            System.err.println("First letter not uppercase : " + filter);
            System.exit(1);
        } */
        filter = first.toUpperCase() + filter.substring(1);
        String name = "polyutilgh.filters." + filter + "Filter";
        try {
            
            Class<?> c = Class.forName(name);
            Constructor<?> cons;
           
            cons = c.getConstructor(Filter.class, String.class);
            object = cons.newInstance(nextOneParam,  params); 
          
              
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Could not create instance of " + filter + " " + name);
            System.exit(1);
        }
        Filter p = (Filter)object;   
        return p;
    }
}
