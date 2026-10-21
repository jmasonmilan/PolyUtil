 package polyutilgh;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import polyutilgh.readers.*;
import polyutilgh.filters.*;
import polyutilgh.functions.*;
import java.io.*;
import java.math.BigInteger;
import java.util.*;
/**
 *
 * @author mason
 */
public class Runner {
    ArrayList<Filter> filterList ;
    InReader firstOne = null;
    /**
     * @param args the command line arguments
     
     */
    public  Runner(String[] args)  {
        //Runner.printArgs("Runner", args);
        if (args.length < 2)
            usage("too few arguments");
        
        run(args);
    }
    void run(String args[])  {
        InputStream ins = null;
        boolean closed = false;
        Filter lastOne = new DevNullFilter(null);
        Filter nextOne;
        String fileArray[] = null;
        int currentFile = 0;
        int n;        
        filterList = new ArrayList<Filter>();
        String fileList = args[args.length - 1];
        if (!fileList.equals("none")) {       
            fileArray = fileList.split("\\+");
            try {
                ins = new FileInputStream(fileArray[0]);
            } catch (IOException e) {
                System.err.println("An error occurred opening input " + fileArray[0]);
                //e.printStackTrace();
                System.exit(1);
            }         
        }
        
        for (int i = args.length - 2 ; i > 0 ; i--) {
            String[] bits = args[i].split(":");
            if (bits.length > 2) {
                System.err.println("bad filter " + args[i]);
                System.exit(1);
            }
            String params;
            if (bits.length == 1)
                params = "";
            else 
                params = bits[1];
            FilterMaker fm = new FilterMaker();
            nextOne = fm.make(bits[0], lastOne, params);
            filterList.add(nextOne);
            lastOne = nextOne;
            /*
            if ((n = testCMD(args[i],"bitrisect")) > 0) {
                String in = args[i].substring(n);
                nextOne = new BiTrisectableFilter(lastOne, in);
                filterList.add(nextOne);
                lastOne = nextOne;
            } */ 

            // 
        }
        String[] bits = args[0].split(":");
        if (bits.length > 2) {
            System.err.println("bad reader " + args[0]);
            System.exit(1);
        }
        ReaderMaker fm = new ReaderMaker();
        String params;
        if (bits.length == 1)
            params = "";
        else 
            params = bits[1];
        firstOne = fm.make(bits[0], lastOne, params);
        
             
        if (ins != null)
            out: while (true) {
                try {
                    BufferedReader b = new BufferedReader(new  InputStreamReader(ins));
                    for(String s = null; (s = b.readLine()) != null;){

                        boolean continueFlag = firstOne.write(s);
                        if (!continueFlag) {
                            close();
                            closed = true;
                            break out;
                        }  

                    }
                    
                    currentFile++;
                    if (currentFile >= fileArray.length)
                        break out;
                    ins.close();
                    ins = new FileInputStream(fileArray[currentFile]);
                    
                } catch (IOException e) {
                    System.err.println("An error occurred opening input " + fileArray[currentFile]);
                    //e.printStackTrace();
                    System.exit(1);
                }  
            }
        if (!closed)
            close();
    }
    int testCMD(String arg, String cmd) {
        if (arg.equals(cmd))
            return arg.length();
        if (arg.startsWith(cmd + ":"))
            return cmd.length() + 1;
        return 0;
    }
    void usage(String error) {
        System.err.println(error);
       
        System.exit(1);
    }
    void close() {
        if (firstOne != null)
            firstOne.close();
        for (int i = filterList.size() - 1; i >= 0; i--) 
            filterList.get(i).close();        
        
    }    
    public static void printArgs(String place, String args[]) {
        String cmd = "";
        for (int i = 0 ; i < args.length ; i++) 
            cmd += args[i] + " ";
        System.err.println(place + " : " + cmd);
    }
    public static void notImplemented(String place) {
        System.err.println(place + " not implemented");
        System.exit(1);
    }
}
