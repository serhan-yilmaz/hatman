/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.modifier;

import sygfx.ScaledGraphics;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public abstract class Modifier {
    private double remaining_duration = 0;
    
    public Modifier(){
        
    }
    
    public abstract void draw(ScaledGraphics g);
    
    public final void reset(){
        remaining_duration = 0;
    }
    
    public void cycle(){
        remaining_duration--;
        if(remaining_duration <= 0){
            remaining_duration = 0;
        }
    }
    
    public final void refresh(double duration){
        if(duration < 0){
            throw new IllegalArgumentException("Invalid modifier duration!");
        }
        if(duration > remaining_duration){
            remaining_duration = duration;
        }
    }
    
    public final boolean isEnabled(){
        return remaining_duration > 0;
    }
    
}
