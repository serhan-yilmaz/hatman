/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman;

/**
 *
 * @author Serhan
 */
public class FpsCounter {    
    private int cycle_count = 0;
    private int fps_count = 0;
    private int fps = 0;
    private int numCycles;
    
    public FpsCounter(int numCycles){
        this.numCycles = numCycles;
    }
    
    public void count(){
        fps_count++;
    }
    
    public void cycle(){
        cycle_count++;
        if(cycle_count >= numCycles){
            fps = fps_count;
            fps_count = 0;
            cycle_count = 0;
        }
    }
    
    public int getFPS(){
        return fps;
    }
    
}
