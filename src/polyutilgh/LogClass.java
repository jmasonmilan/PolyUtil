/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polyutilgh;

import java.io.FileWriter;

/**
 *
 * @author mason
 */
public class LogClass {
        private static FileWriter fw;
        private static boolean doLog;
        private static boolean doSync;
        private static final String FILENAME = 
                "C:\\Documents and Settings\\jmason\\Documenti\\NetBeansProjects\\tour\\pippo.txt";
        //private Hashtable hash;
    
        private int rn;
        public LogClass() 
        {
            
           if (fw == null)
               try
               {
                   fw = new FileWriter(FILENAME);
        
               } catch(Exception e)
               {
                    int i = 0;
               }
}
        public void add(String text) 
        {
            if (doLog)
                synchronized(fw)
                {
                   try
                   {
                       fw.write(text + "\r\n");
                       if (doSync)
                       {
                           fw.close();
                           fw = new FileWriter(FILENAME, true);
                       }
                   } catch(Exception e)
                   {

                   }
                }   
        }
 
  
        public void close() 
        {
            try
               {
                   fw.close();
               } catch(Exception e)
               {
     
               }
            
        }
        public void on()
        {
            doLog = true;
        }
        public void sync()
        {
            doSync = true;
        }
        public void off()
        {
            doLog = false;
        }    
}
