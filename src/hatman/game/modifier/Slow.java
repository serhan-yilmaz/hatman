/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.modifier;

import java.awt.image.BufferedImage;
import sygfx.ScaledGraphics;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class Slow extends AbstractModifier{
    private double slow_amount;
    
    public Slow(double d, BufferedImage img){
        super(img);
        slow_amount = d;
    }
    
    
    public double getMovementModifier(){
        return isEnabled()? 1 - slow_amount: 1;
    }
    
}
