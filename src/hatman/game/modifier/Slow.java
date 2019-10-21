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
public class Slow extends Modifier{
    private double slow_amount;
    
    public Slow(double d){
        slow_amount = d;
    }
    
    @Override
    public void draw(ScaledGraphics g) {
        
    }
    
    public double getMovementModifier(){
        return isEnabled()? 1 - slow_amount: 1;
    }
    
}
