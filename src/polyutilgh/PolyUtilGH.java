package polyutilgh;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author mason
 */
public class PolyUtilGH {
    
    /**
     * @param args the command line arguments: see Runner class
 
     */
    static String command = "";
    public static void main(String[] args)  {
        for (int i = 0; i < args.length; i++) 
            command += " " + args[i];        
        long startTime = System.currentTimeMillis();
        //new Runner(args);
        new Runner(args);
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime) / 1000;
        System.err.println("Elapsed " + elapsedTime);
        

        try {
            Runtime rt = Runtime.getRuntime();
            long total_mem = rt.totalMemory();

            long free_mem = rt.freeMemory();

            long used_mem = total_mem - free_mem;
            used_mem /= 1000000;
            FileWriter fw = new FileWriter("times.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(elapsedTime + " " + used_mem + "M " + command);
            bw.newLine();
            bw.close();         
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
    }
    public static void logOutput(String s) {
        System.out.println(s);
        try {
            FileWriter fw = new FileWriter("outputlog.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(command + " : " + s );
            bw.newLine();
            bw.close();         
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }        
    }
    public static boolean debug;
}
