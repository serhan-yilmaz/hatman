/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.util;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Timer {
    private double cycle_count = 0;
    private double numCycles;
    
    public Timer(double numCycles){
        this.numCycles = numCycles;
    }
    
    public void setPeriod(double numCycles){
        this.numCycles = numCycles;
        if(cycle_count > numCycles){
            cycle_count = numCycles;
        }
    }
    
    public boolean cycle(){
        cycle_count++;
        if(cycle_count >= numCycles){
            cycle_count -= numCycles;
            return true;
        }
        return false;
    }
    
    
    
}
