/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.ui;

import sygfx.ui.JCanvas;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class PaintThread extends Thread{
    private final JCanvas canvas;
    private int repaint_period = 20;
    
    public PaintThread(JCanvas canvas){
        this.canvas = canvas;
    }
    
    public void setRepaintPeriod(int p){
        this.repaint_period = p;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(repaint_period);
            } catch (InterruptedException ex) {
                
            }
            canvas.repaint();
        }
    }
    
}
