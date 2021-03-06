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
public abstract class AbstractModifier implements Modifier{
    private double remaining_duration = 0;
    private final BufferedImage img;
    
    public AbstractModifier(BufferedImage img){
        this.img = img;
    }
    
    @Override
    public int draw(ScaledGraphics g, int x, int y){
        if(isEnabled()){
            g.drawImage(img, x, y, 25, 25, null);
            return 1;
        }
        return 0;
    }
    
    @Override
    public final void reset(){
        remaining_duration = 0;
    }
    
    @Override
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
