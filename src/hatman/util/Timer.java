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
    
    public void reset(){
        this.cycle_count = 0;
    }
    
    public double getPeriod(){
        return numCycles;
    }
    
    public void setPeriod(double numCycles){
        this.numCycles = numCycles;
        if(cycle_count > numCycles){
            cycle_count = numCycles;
        }
    }
    
    public void setRemainingTime(double numCycles){
        this.cycle_count = this.getPeriod() - numCycles;
    }
    
    public int getRemainingTime(){
        return (int) Math.ceil(this.getPeriod() - cycle_count);
    }
    
    public boolean cycle(){
        return cycle(1);
    }
    
    public boolean cycle(double d){
        cycle_count += d;
        if(cycle_count >= numCycles){
            cycle_count -= numCycles;
            return true;
        }
        return false;
    }
    
}
