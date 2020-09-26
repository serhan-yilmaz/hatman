/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survivalgame;

/**
 *
 * @author Serhan
 */

public class PaintThread extends Thread{
    private final SurviveBoard canvas;
    private int repaint_period = 20;
    
    public PaintThread(SurviveBoard canvas){
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

