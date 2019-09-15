/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

import hatman.ui.GUI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        
        ScheduledExecutorService scheduler = 
                Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(gui::cycle, 20, 20, TimeUnit.MILLISECONDS);
    }
    
}
