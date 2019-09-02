/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class HatmanMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible();
        double realtime = 0;
        double heaptimer = 0;
        while(true){
            realtime += 20e-3;
            heaptimer += 20e-3;
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(MapSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
            gui.cycle();
            if (heaptimer >= 5) {
                heaptimer = 0;
                long heapsize = Runtime.getRuntime().totalMemory();
                long heapsize_kib = (heapsize / 1024);
                long heapsize_mib = (heapsize_kib / 1024);
                System.out.println("Heap size is : " + heapsize_mib + " MiB");
            }
        }
    }
    
}
