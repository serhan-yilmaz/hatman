/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

import hatman.game.ResourceManager;
import hatman.ui.GUI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class HatmanMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ResourceManager.initialize();
        
        GUI gui = new GUI();
        gui.setVisible();
        
        ScheduledExecutorService scheduler = 
                Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(gui::cycle, 
                20, 20, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(HatmanMain::printHeapSize, 5, 5, TimeUnit.SECONDS);
        
        future.get();
    }
    
    public static void printHeapSize(){
       long heapsize = Runtime.getRuntime().totalMemory();
       long heapsize_kib = (heapsize / 1024);
       long heapsize_mib = (heapsize_kib / 1024);
       System.out.println("Heap size is: " + heapsize_mib + " MiB");   
    }
    
}
