/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.game.modifier;

import java.util.ArrayList;
import sygfx.ScaledGraphics;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class StatusEffects {
    private ArrayList<Modifier> permanent_modifiers = new ArrayList<>();

    private Slow water_slow = new Slow(0.1);
    private Slow wave_slow = new Slow(0.4);

    public StatusEffects(){
        permanent_modifiers.add(water_slow);
        permanent_modifiers.add(wave_slow);
    }
    
    public void refreshWaterSlow(double duration){
        water_slow.refresh(duration);
    }
    
    public void refreshWaveSlow(double duration){
        wave_slow.refresh(duration);
    }
    
    public void draw(ScaledGraphics g){
        
    }
    
    public void reset(){
        for(Modifier m: permanent_modifiers){
            m.reset();
        }
    }
    
    public void cycle(){
        for(Modifier m: permanent_modifiers){
            m.cycle();
        }
    }
    
    public double getMovementModifier(){
        return water_slow.getMovementModifier() * wave_slow.getMovementModifier();
    }
    
}
