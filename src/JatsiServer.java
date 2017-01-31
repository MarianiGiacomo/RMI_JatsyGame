

import java.rmi.Naming;

/**
 *
 * @author Giacomo Mariani
 * This class creates the Jatsi implementation object and a SharedData object
 * to store the game instances
 */
public class JatsiServer {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SharedData sd = new SharedData();
        try{
           JatsiImplem jatsi = new JatsiImplem(sd); 
           Naming.rebind("jatsi", jatsi);
           System.err.println("Server ready");
       } catch (Exception e){e.printStackTrace();}
    }
    
}
